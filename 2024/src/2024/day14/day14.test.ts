import {moveRobot, part1, part2, Robot} from "./day14";
import {Coord} from "@utils/grid";

describe('2024 Day 14', () => {
    test('Part 1', async () => {
        expect(await part1('testInput1', 11, 7)).toEqual(12);
        expect(await part1('input', 101, 103)).toEqual(225648864);
    });

    test('Part 2', async () => {
        expect(await part2('input')).toEqual(7847);
    });
});

test('move robot', () => {
    const robot: Robot = { position: new Coord(2, 4), velocity: new Coord(2, -3) }
    expect(moveRobot(robot, 1, 11, 7).serialize()).toEqual("{4,1}")
    expect(moveRobot(robot, 2, 11, 7).serialize()).toEqual("{6,5}")
    expect(moveRobot(robot, 5, 11, 7).serialize()).toEqual("{1,3}")
});