export async function part1(inputString: string) {
    return day11(inputString, 25);
}

export async function part2(inputString: string) {
    return day11(inputString, 75);
}

async function day11(inputString: string, times: number) {
    return calcBlinkOutput(inputString.split(" "), times);
}

function calcBlinkOutput(stones: string[], times: number) {
    let stoneCounts: Map<string, number> = new Map();

    stones.forEach(stone => {
        stoneCounts.set(stone, (stoneCounts.get(stone) || 0) + 1);
    });

    for (let i = 0; i < times; i++) {
        stoneCounts = blink(stoneCounts);
    }

    return Array.from(stoneCounts.values())
        .reduce((sum, count) => sum + count, 0);
}

function blink(stoneCounts: Map<string, number>): Map<string, number> {
    const newStoneCounts: Map<string, number> = new Map();

    stoneCounts.forEach((count, stone) => {
        if (stone === '0') {
            newStoneCounts.set('1', (newStoneCounts.get('1') || 0) + count);
        } else if (stone.length % 2 === 0) {
            const half = stone.length / 2;
            const first = Number.parseInt(stone.slice(0, half)) + '';
            const second = Number.parseInt(stone.slice(half)) + '';
            newStoneCounts.set(first, (newStoneCounts.get(first) || 0) + count);
            newStoneCounts.set(second, (newStoneCounts.get(second) || 0) + count);
        } else {
            const multiplied = (Number.parseInt(stone) * 2024) + '';
            newStoneCounts.set(multiplied, (newStoneCounts.get(multiplied) || 0) + count);
        }
    });

    return newStoneCounts;
}

// Every time you blink, the stones each simultaneously change according to the _first applicable_ rule in this list:
//
// 1. If the stone is engraved with the number 0, it is replaced by a stone engraved with the number 1.
// 2. If the stone is engraved with a number that has an even number of digits, it is replaced by two stones.
//    The left half of the digits are engraved on the new left stone, and the right half of the digits are engraved on the new right stone.
// 3. If none of the other rules apply, the stone is replaced by a new stone;
//    the old stone's number multiplied by 2024 is engraved on the new stone.
export function blinkV1(stones: string[] | string): string[] {
    const inputStones = typeof stones === 'string' ? stones.split(" ") : stones;
    const updatedStones: string[] = [];

    inputStones.forEach((stone, index) => {
        if (stone === '0') {
            updatedStones.push("1");
        } else if (stone.length % 2 === 0) {
            const half = stone.length / 2;
            const asList = stone.split("");
            const first = Number.parseInt(asList.slice(0, half).join("")) + '';
            const second = Number.parseInt(asList.slice(half).join("")) + '';
            updatedStones.push(first, second);
        } else {
            const asNum = Number.parseInt(stone);
            updatedStones.push((asNum * 2024) + '');
        }
    });
    return updatedStones
}