import path from "node:path";
import {readInputLineByLine} from "@utils/io";

export async function part1(inputFile: string) {
    return await day9(inputFile, fragmentAndCalcChecksum);
}

export async function part2(inputFile: string) {
    return await day9(inputFile);
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

        if (firstEmptyIndex === lastBlockIndex) {
            break;
        }

        if (blockId === dot) {
            expandedDisk.pop();
            continue;
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

    const checksum = collapseAndCalcChecksum(expandedDisk);

    return checksum;
}

export function expandDiskMap(line: string) {
    const list: object[] = [];

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

export function collapseAndCalcChecksum(list: object[]) {
    let collapsed = "";

    list.forEach((block) => {
        const [blockId, blockLength] = Object.entries(block)[0];

        Array(blockLength).fill(0).forEach(i => {
            collapsed += blockId;
        })
    });

    let checksum = 0;

    collapsed.replace(".", "").split("").forEach((blockId, index) => {
        checksum += Number.parseInt(blockId) * index;
    });

    return checksum;
}