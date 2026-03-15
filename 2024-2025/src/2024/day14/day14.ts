import path from "node:path";
import {readInputLineByLine} from "@utils/io";
import {Coord} from "@utils/grid";

export async function part1(inputFile: string, hiX: number, hiY: number) {
    return await day14(inputFile, hiX, hiY, calcSafetyFactor);
}

export async function part2(inputFile: string) {
    return await day14(inputFile, undefined, undefined, keepMovingUntilChristmasTreeIsFound);
}

export type Robot = {
    position: Coord;
    velocity: Coord;
}

async function day14(inputFile: string, hiX?: number, hiY?: number, calcFn?: (robots: Robot[], hiX?: number, hiY?: number) => number | void) {
    const inputPath = path.join(__dirname, inputFile);
    const lines = await readInputLineByLine(inputPath);

    const robots: Robot[] = [];

    lines.forEach(line => {
        const nums = [...line.match(/-?\d+/g)!].map(s => Number.parseInt(s));
        robots.push({ position: new Coord(nums[0], nums[1]), velocity: new Coord(nums[2], nums[3])});
    })
    return calcFn?.(robots, hiX, hiY);
}

function calcSafetyFactor(robots: Robot[], hiX?: number, hiY?: number) {
    const midX = Math.round(hiX! / 2) - 1;
    const midY = Math.round(hiY! / 2) - 1;

    const quadrantCounts = { q1: 0, q2: 0, q3: 0, q4: 0 };

    moveAllRobots(robots, hiX, hiY).forEach(robot => {
        const { x, y } = robot.position;

        if (x < midX && y < midY) {
            quadrantCounts.q1++;
        } else if (x > midX && y < midY) {
            quadrantCounts.q2++;
        } else if (x < midX && y > midY) {
            quadrantCounts.q3++;
        } else if (x > midX && y > midY) {
            quadrantCounts.q4++;
        }
    });

    return Object.values(quadrantCounts)
        .reduce((product, count) => product * count, 1);
}

function keepMovingUntilChristmasTreeIsFound(robots: Robot[]) {
    const hiX = 101;
    const hiY = 103;

    let seconds = 1;
    let movedRobots = moveAllRobots(robots, hiX, hiY, 1);

    main: while (true) {
        const robotPositions = new Set(
            movedRobots.map(robot => `${robot.position.x},${robot.position.y}`)
        );

        for (let y = 0; y < hiY; y++) {
            let line = '';
            for (let x = 0; x < hiX; x++) {
                line += robotPositions.has(`${x},${y}`) ? '#' : '.';
            }

            if (line.includes("##############################")) {
                console.log(`Christmas tree found after ${seconds} seconds!`);
                break main;
            }
        }
        if (seconds >= hiX * hiY) {
            break;
        }
        movedRobots = moveAllRobots(movedRobots, hiX, hiY, 1);
        seconds++;
    }

    return seconds;
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

function moveAllRobots(robots: Robot[], hiX: number | undefined, hiY: number | undefined, seconds = 100) {
    const newRobotsPositions: Robot[] = [];

    robots.forEach(robot => {
        const finalPosition = moveRobot(robot, seconds, hiX!, hiY!);
        newRobotsPositions.push({position: finalPosition, velocity: robot.velocity});
    });
    return newRobotsPositions;
}