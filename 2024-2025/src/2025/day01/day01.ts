import path from "node:path";
import {readInputLineByLine} from "@utils/io";

export async function part1(inputFile: string): Promise<number> {
    return await day1(inputFile, countZeroPositions);
}

export async function part2(inputFile: string): Promise<number> {
    return await day1(inputFile, countZeroPassages);
}

type MovementType = { dir: string, value: number };

async function day1(inputFile: string, calcFn: ( movements: MovementType[]) => number): Promise<number> {
    const inputPath = path.join(__dirname, inputFile);
    const lines = await readInputLineByLine(inputPath);

    const movements = lines.map(line => {
        const dir = line.slice(0, 1);
        const value = Number.parseInt(line.slice(1));
        return { dir, value };
    })

    return calcFn(movements);
}


function countZeroPositions(movements: MovementType[]): number {
    let positionsOnZero = 0;
    let position = 50;

    movements.forEach(movement => {
        const newPosition = movement.dir === 'R' ? position + movement.value : position - movement.value;
        position = ((newPosition % 100) + 100) % 100; // handles all cases: positive, negative, multiple wraps

        if (position === 0) {
            positionsOnZero++;
        }
    })

    return positionsOnZero;
}

function countZeroPassages(movements: MovementType[]): number {
    let count = 0;
    let position = 50;

    movements.forEach(({ dir, value }) => {
        // firstK: the number of clicks until we first land on 0
        // If position == 0 we already left it, so next landing is after a full circle
        if (dir === 'R') {
            const firstK = position === 0 ? 100 : 100 - position;
            if (value >= firstK) count += Math.floor((value - firstK) / 100) + 1;
            position = (position + value) % 100;
        } else {
            const firstK = position === 0 ? 100 : position;
            if (value >= firstK) count += Math.floor((value - firstK) / 100) + 1;
            position = ((position - value) % 100 + 100) % 100;
        }
    });

    return count;
}
