import fs from 'fs';
import path from 'path';
import axios from 'axios';
import commandLineArgs from 'command-line-args';

const optionDefinitions = [
    { name: 'year', alias: 'y', type: Number, defaultValue: new Date().getFullYear() },
    { name: 'day', alias: 'd', type: Number, defaultValue: new Date().getDate() },
];

const fetchInput = async () => {
    const session = process.env.AOC_SESSION;
    if (!session) {
        throw new Error('Session cookie not found - make sure it is set for the environment as AOC_SESSION');
    }

    const options = commandLineArgs(optionDefinitions);

    const day = options.day;
    if (day < 1 || day > 25) {
        throw new Error('Invalid day');
    }
    const formattedDay = `day${day.toString().padStart(2, '0')}`;

    const year = options.year;
    if (year < 2015 || year > new Date().getFullYear()) {
        throw new Error('Invalid year');
    }

    const dirName = `src/${year}/${formattedDay}`;

    try {
        await fs.promises.access(dirName);
        console.info(`Dir ${dirName} already exists`);
        process.exit(1); // Exit if directory already exists
    } catch (err) {
        if (err.code !== 'ENOENT') {
            console.error(`Error checking directory: ${err.message}`);
            process.exit(1);
        }

        await fs.promises.mkdir(dirName, { recursive: true });
        console.log(`Directory ${dirName} created`);

        const url = `https://adventofcode.com/${year}/day/${day}/input`;

        const solutionPath = path.join(`${dirName}/${formattedDay}.ts`);
        let ws = await fs.createWriteStream(solutionPath);
        let content = `import path from "node:path";
import {readInputLineByLine} from "@utils/io";

export async function part1(inputFile: string) {
    return await day${day}(inputFile);
}

export async function part2(inputFile: string) {
    return await day${day}(inputFile);
}

async function day${day}(inputFile: string, calcFn?: (lines: string[]) => number) {
    const inputPath = path.join(__dirname, inputFile);
    const lines = await readInputLineByLine(inputPath);

    return calcFn?.();
}`;
        ws.write(content);
        ws.end();

        const testPath = path.join(`${dirName}/${formattedDay}.test.ts`);
        ws = await fs.createWriteStream(testPath);
        content = `import {part1, part2} from "./${formattedDay}";

describe('${year} Day ${day}', () => {
    test('Part 1', async () => {
        expect(await part1('testInput1')).toEqual(11);
        expect(await part1('input')).toEqual(3246517);
    });

    test('Part 2', async () => {
        expect(await part2('testInput1')).toEqual(31);
        expect(await part2('input')).toEqual(29379307);
    });
});`;
        ws.write(content);
        ws.end();

        const inputPath = path.join(`${dirName}/input`);

        try {
            const response = await axios.get(url, { headers: { Cookie: `session=${session}` } });
            fs.writeFileSync(inputPath, response.data.trim());
            console.log(`Input for ${year} Day ${day} saved!`);
        } catch (fetchError) {
            console.error(`Failed to fetch input: ${fetchError.message}`);
            process.exit(1);
        }
    }
};

await fetchInput();
