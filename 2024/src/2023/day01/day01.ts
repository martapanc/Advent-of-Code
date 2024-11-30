import {readInputLineByLine} from "@utils/io";
import {isNumeric} from "@utils/numbers";
import path from "node:path";

export async function part1(inputFile: string): Promise<number> {
    const inputPath = path.join(__dirname, inputFile);
    const lines = await readInputLineByLine(inputPath);

    let calibrationChecksum = 0;

    lines.forEach(line => {
        const numbers = line.match(/[1-9]/g);

        if (numbers && numbers.length > 0) {
            const res = `${numbers[0]}${numbers[numbers.length - 1]}`
            calibrationChecksum += Number.parseInt(res);
        }
    });

    return calibrationChecksum;
}

export async function part2(inputFile: string): Promise<number> {
    const inputPath = path.join(__dirname, inputFile);
    const lines = await readInputLineByLine(inputPath);

    let calibrationChecksum = 0;

    lines.forEach(line => {
        const numbers = matchesWithOverlap(line);

        if (numbers && numbers.length > 0) {
            const first = numbers[0];
            const num1 = isNumeric(first) ? first : parseNumberFromString(first);

            const second = numbers[numbers.length - 1];
            const num2 = isNumeric(second) ? second : parseNumberFromString(second);

            const res = `${num1}${num2}`;

            calibrationChecksum += Number.parseInt(res);
        }
    });

    return calibrationChecksum;
}

function parseNumberFromString(numAsString: string): number {
    const map: Record<string, number> = {
        one: 1,
        two: 2,
        three: 3,
        four: 4,
        five: 5,
        six: 6,
        seven: 7,
        eight: 8,
        nine: 9,
    };

    if (numAsString in map) {
        return map[numAsString];
    } else {
        throw new Error(`Invalid number string: "${numAsString}"`);
    }
}

export function matchesWithOverlap(input: string) {
    const regex = /(?=(one|two|three|four|five|six|seven|eight|nine|zero|\d))/g;

    const matches = [];
    let match;

    while ((match = regex.exec(input)) !== null) {
        if (match[1]) {
            matches.push(match[1]);
        }

        regex.lastIndex = match.index + 1;
    }

    return matches;
}