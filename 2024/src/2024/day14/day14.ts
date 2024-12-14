import path from "node:path";
import {readInputLineByLine} from "@utils/io";
import {Coord} from "@utils/grid";

export async function part1(inputFile: string, hiX: number, hiY: number) {
    return await day14(inputFile, hiX, hiY, calcSafetyFactor);
}

export async function part2(inputFile: string, hiX: number, hiY: number) {
    return await day14(inputFile, hiX, hiY);
}

export type Robot = {
    position: Coord;
    velocity: Coord;
}

async function day14(inputFile: string, hiX: number, hiY: number, calcFn?: (robots: Robot[], hiX: number, hiY: number) => number) {
    const inputPath = path.join(__dirname, inputFile);
    const lines = await readInputLineByLine(inputPath);

    const robots: Robot[] = [];

    lines.forEach(line => {
        const nums = [...line.match(/-?\d+/g)!].map(s => Number.parseInt(s));
        robots.push({ position: new Coord(nums[0], nums[1]), velocity: new Coord(nums[2], nums[3])});
    })
    return calcFn?.(robots, hiX, hiY);
}

function calcSafetyFactor(robots: Robot[], hiX: number, hiY: number) {
    const newRobotsPositions: Robot[] = [];

    robots.forEach(robot => {
        const finalPosition = moveRobot(robot, 100, hiX, hiY);
        newRobotsPositions.push({ position: finalPosition, velocity: robot.velocity });
    });

    let q1Count = 0;
    let q2Count = 0;
    let q3Count = 0;
    let q4Count = 0;
    const midX = Math.round(hiX / 2) - 1;
    const midY = Math.round(hiY / 2) - 1;

    newRobotsPositions.forEach(robot => {
        const { x, y } = robot.position;
        if (x < midX && y < midY) {
            q1Count++;
        } else if (x > midX && y < midY) {
            q2Count++;
        } else if (x < midX && y > midY) {
            q3Count++;
        } else if (x > midX && y > midY) {
            q4Count++;
        }
    });
    return q1Count * q2Count * q3Count * q4Count;
}

export function moveRobot(robot: Robot, seconds: number, hiX: number, hiY: number) {
    const { x: posX, y: posY } = robot.position;
    const { x: vX, y: vY } = robot.velocity;

    // (value % max) => remainder.
    // Adding max ensures the value becomes positive if it was negative.
    // Taking % max again ensures the result stays within the range [0, max - 1].
    const wrap = (value: number, max: number) => ((value % max) + max) % max;

    return new Coord(wrap(posX + vX * seconds, hiX), wrap(posY + vY * seconds, hiY));
}