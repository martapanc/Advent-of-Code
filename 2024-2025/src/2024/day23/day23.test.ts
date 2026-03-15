import {part1, part2} from "./day23";

describe('2024 Day 23', () => {
    test('Part 1', async () => {
        expect(await part1('testInput1')).toEqual(7);
        expect(await part1('input')).toEqual(1238);
    });

    test('Part 2', async () => {
        expect(await part2('testInput1')).toEqual("co,de,ka,ta");
        expect(await part2('input')).toEqual("bg,bl,ch,fn,fv,gd,jn,kk,lk,pv,rr,tb,vw");
    });
});