export function isNumeric(str: string) {
    if (!str) {
        return false;
    }
    return !isNaN(+str);
}

export function generateBinaryStrings(length: number): string[] {
    if (length === 0) {
        return [""];
    }

    const smallerCombos = generateBinaryStrings(length - 1);
    return smallerCombos.flatMap((combo) => [
        "0" + combo,
        "1" + combo
    ]);
}

export function generateTernaryStrings(length: number): string[] {
    if (length === 0) {
        return [""];
    }

    const smallerCombos = generateTernaryStrings(length - 1);
    return smallerCombos.flatMap((combo) => [
        "0" + combo,
        "1" + combo,
        "2" + combo
    ]);
}

export function generatePairs(array: string[], start = 0, result: string[][] = []) {
    if (start >= array.length - 1) {
        return result;
    }

    for (let i = start + 1; i < array.length; i++) {
        result.push([array[start], array[i]]);
    }

    return generatePairs(array, start + 1, result);
}