import fs from 'fs';
import path from 'path';
import axios from 'axios';
import commandLineArgs from 'command-line-args'

const optionDefinitions = [
    { name: 'year', alias: 'y', type: Number, defaultValue: new Date().getFullYear() },
    { name: 'day', alias: 'd', type: Number, defaultValue: new Date().getDate() },
]

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
    const formattedDay = day.toString().padStart(2, '0');

    const year = options.year;
    if (year < 2015 || year > new Date().getFullYear()) {
        throw new Error('Invalid year');
    }

    const dirName = `src/${year}/day${formattedDay}`;

    try {
        await fs.promises.access(dirName);
        throw new Error(`Directory ${dirName} already exists, exiting program.`);
    } catch (err) {
        if (err.code === 'ENOENT') { // Directory doesn't exist
            await fs.promises.mkdir(dirName, { recursive: true });
        } else {
            console.error(err.message);
            process.exit(1);
        }
    }

    const url = `https://adventofcode.com/${year}/day/${day}/input`;
    const inputPath = path.join(`${dirName}/input`);

    const response = await axios.get(url, { headers: { Cookie: `session=${session}` } });
    fs.writeFileSync(inputPath, response.data.trim());

    console.log(`Input for ${year} Day ${day} saved!`);
};

await fetchInput();
