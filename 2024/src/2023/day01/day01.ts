import {readInputLineByLine} from "@utils/io";
import path from "node:path";

export async function day1() {
    const inputPath = path.join(__dirname, 'input');

    await readInputLineByLine(inputPath);
}
