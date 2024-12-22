import {encodeFirstLevel, encodeSecondLevel, part1, part2 } from "./day21";
import {readInputLineByLine} from "@utils/io";

describe('2024 Day 21', () => {
    beforeEach(() => {
        global.console = require('console');
    });

    test('Part 1', async () => {
        expect(await part1('testInput1')).toEqual(126384);
        expect(await part1('input')).toEqual(163920);
    });

    test('Part 2', async () => {
        expect(await part2('testInput1')).toEqual(154115708116294);
        expect(await part2("input")).toEqual(204040805018350);
    });

    test('encode first level', () => {
        expect(encodeFirstLevel("029A")).toEqual(["<A^A>^^AvvvA", "<A^A^>^AvvvA", "<A^A^^>AvvvA"]);
        expect(encodeFirstLevel("179A")).toEqual(["<A^A>^^AvvvA", "<A^A^>^AvvvA", "<A^A^^>AvvvA"]);
    });
    test('encode second level', () => {
        expect(encodeSecondLevel("<A^A>^^AvvvA")).toContain("v<<A>>^A<A>AvA<^AA>A<vAAA>^A");
    });

    test('encode third level', () => {
        expect(encodeSecondLevel("v<<A>>^A<A>AvA<^AA>A<vAAA>^A")).toContain("<vA<AA>>^AvAA<^A>A<v<A>>^AvA^A<vA>^A<v<A>^A>AAvA^A<v<A>A>^AAAvA<^A>A");
    });
});