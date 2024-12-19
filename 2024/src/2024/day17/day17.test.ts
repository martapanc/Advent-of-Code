import {part1, part2} from "./day17";

describe('2024 Day 17', () => {
    test('Part 1', async () => {
        expect(await part1({ regA: 10, regB: 0, regC: 0, program: "5,0,5,1,5,4"})).toEqual("0,1,2");
        expect(await part1({ regA: 2024, regB: 0, regC: 0, program: "0,1,5,4,3,0"})).toEqual("4,2,5,6,7,7,7,7,3,1,0");
        expect(await part1({ regA: 729, regB: 0, regC: 0, program: "0,1,5,4,3,0"})).toEqual("4,6,3,5,6,3,5,2,1,0");
        expect(await part1({ regA: 64012472, regB: 0, regC: 0, program: "2,4,1,7,7,5,0,3,1,7,4,1,5,5,3,0"})).toEqual("1,0,2,0,5,7,2,1,3");
    });

    test('Part 2', async () => {
        // expect(await part1({ regA: 117440, regB: 0, regC: 0, program: "0,3,5,4,3,0"})).toEqual("0,3,5,4,3,0");

        // expect(await part2({ regA: 0, regB: 0, regC: 0, program: "0,3,5,4,3,0"})).toEqual(117440);
        expect(await part2({ regA: 64012472, regB: 0, regC: 0, program: "2,4,1,7,7,5,0,3,1,7,4,1,5,5,3,0"})).toEqual(117440);
    });
});