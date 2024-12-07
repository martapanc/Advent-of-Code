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