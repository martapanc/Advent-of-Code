import * as fs from 'node:fs'
import {fileURLToPath} from "url";
import path from "node:path";

export async function readInputLineByLine(path: string): Promise<string[]> {
    const content = fs.readFileSync(path, 'utf-8');
    return content.split('\n').filter(line => line.trim() !== '');
}

function inputPath(inputFile: string, metaUrl: string): string {
    const __filename = fileURLToPath(metaUrl);
    const __dirname = path.dirname(__filename);
    return path.join(__dirname, inputFile);
}