import * as fs from 'node:fs'

export async function readInputLineByLine(path: string): Promise<string[]> {
    const content = fs.readFileSync(path, 'utf-8');
    return content.split('\n');
}