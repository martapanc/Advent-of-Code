import path from "node:path";
import {readInputLineByLine} from "@utils/io";

export async function part1(inputFile: string) {
    return await day9(inputFile, fragmentAndCalcChecksum);
}

export async function part2(inputFile: string) {
    return await day9(inputFile, fragmentAndCalcChecksum2);
}

async function day9(inputFile: string, calcFn?: (line: string) => number) {
    const inputPath = path.join(__dirname, inputFile);
    const lines = await readInputLineByLine(inputPath);

    return calcFn?.(lines[0]);
}

function fragmentAndCalcChecksum(line: string) {
    const expandedDisk = expandDiskMap(line);

    while (expandedDisk.some((block) => '.' in block)) {
        const firstEmptyIndex = expandedDisk.findIndex((item) => '.' in item);
        let firstEmptyBlock = expandedDisk[firstEmptyIndex];
        const [dot, emptyLength] = Object.entries(firstEmptyBlock)[0];

        const lastBlockIndex = expandedDisk.length - 1;
        let lastBlock = expandedDisk[lastBlockIndex];
        const [blockId, blockLength] = Object.entries(lastBlock)[0];

        if (blockId === dot) {
            expandedDisk.pop();
            continue;
        }

        if (firstEmptyIndex === lastBlockIndex) {
            break;
        }


        if (blockLength > emptyLength) {
            firstEmptyBlock = { [blockId]: emptyLength };
            expandedDisk[firstEmptyIndex] = firstEmptyBlock;

            lastBlock = { [blockId]: blockLength - emptyLength }
            expandedDisk[lastBlockIndex] = lastBlock;
        } else if (blockLength < emptyLength) {
            firstEmptyBlock = { [blockId]: blockLength };
            expandedDisk[firstEmptyIndex] = firstEmptyBlock;

            const remainingEmptyBlock = {[dot]: emptyLength - blockLength };
            expandedDisk.splice(firstEmptyIndex + 1, 0, remainingEmptyBlock);

            expandedDisk.pop();
        } else {
            firstEmptyBlock = { [blockId]: blockLength };
            expandedDisk[firstEmptyIndex] = firstEmptyBlock;

            expandedDisk.pop();
        }
    }

    return calcChecksum(expandedDisk);
}

function fragmentAndCalcChecksum2(line: string) {
    const expandedDisk = expandDiskMap(line);

    const highestBlockIndex = expandedDisk.length - 1;
    const highestBlock = expandedDisk[highestBlockIndex];

    let blockId: number = Number.parseInt(Object.keys(highestBlock)[0]);

    while (blockId > 0) {
        const blockIndex = expandedDisk.findIndex(block => Number(Object.keys(block)[0]) === blockId);
        const block  = expandedDisk[blockIndex];
        const blockLength = block?.[blockId]!;

        const firstLargeEnoughEmptyIndex = expandedDisk.findIndex((item) => '.' in item && item['.'] as number >= blockLength );
        if (firstLargeEnoughEmptyIndex === -1 || firstLargeEnoughEmptyIndex > blockIndex) {
            blockId--;
            continue;
        }
        let firstLargeEnoughEmptyBlock = expandedDisk[firstLargeEnoughEmptyIndex];
        const [dot, emptyLength] = Object.entries(firstLargeEnoughEmptyBlock)[0];

        let newCurrentBlockIndex = blockIndex;

        if (blockLength < emptyLength || blockLength === emptyLength) {

            if (blockLength < emptyLength) {
                firstLargeEnoughEmptyBlock = { [blockId]: blockLength };
                expandedDisk[firstLargeEnoughEmptyIndex] = firstLargeEnoughEmptyBlock;

                const remainingEmptyBlock = {[dot]: emptyLength - blockLength };
                expandedDisk.splice(firstLargeEnoughEmptyIndex + 1, 0, remainingEmptyBlock);

                expandedDisk.splice(blockIndex + 1, 1, {'.': blockLength});

                // Two blocks (one full, one empty) were created, so bumping block index
                newCurrentBlockIndex++;
            } else if (blockLength === emptyLength) {
                firstLargeEnoughEmptyBlock = { [blockId]: blockLength };
                expandedDisk[firstLargeEnoughEmptyIndex] = firstLargeEnoughEmptyBlock;

                expandedDisk.splice(blockIndex, 1, {'.': emptyLength});
            }

            // Merge consecutive empty blocks if newly formed
            const prevBlock = expandedDisk[newCurrentBlockIndex - 1];
            const currentBlock = expandedDisk[newCurrentBlockIndex];
            const nextBlock = expandedDisk[newCurrentBlockIndex + 1];

            if (nextBlock && '.' in currentBlock && '.' in nextBlock) {
                const nextBlockLength = nextBlock['.'];
                expandedDisk.splice(newCurrentBlockIndex, 2, { '.': blockLength + nextBlockLength });
            }
            if ('.' in prevBlock && '.' in currentBlock) {
                const prevBlockLength = prevBlock['.'];
                expandedDisk.splice(newCurrentBlockIndex - 1, 2, { '.': prevBlockLength + expandedDisk[newCurrentBlockIndex]['.'] })
            }
        }

        blockId--;
    }

    return calcChecksum(expandedDisk);
}

type Block = { [key: number | string]: number };

export function expandDiskMap(line: string) {
    const list: Block[] = [];

    let blockIdx = 0;
    line.split('').forEach((item, index) => {
        const count = Number.parseInt(item);

        if (index % 2 == 0) {
            list.push({[blockIdx]: count});
            blockIdx++;
        } else {
            if (count !== 0)
                list.push({'.': count});
        }
    });

    return list;
}

export function calcChecksum(list: object[]) {
    let checksum = 0;
    let currentPosition = 0;

    for (const block of list) {
        const [blockId, length] = Object.entries(block)[0];
        const len = length;
        if (blockId === '.') {
            currentPosition += len;
            continue;
        }
        const id = parseInt(blockId, 10);

        for (let i = 0; i < len; i++) {
            checksum += id * (currentPosition + i);
        }

        currentPosition += len;
    }

    return checksum;
}