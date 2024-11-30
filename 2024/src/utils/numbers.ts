export function isNumeric(str: string) {
    if (!str) {
        return false;
    }
    return !isNaN(+str);
}