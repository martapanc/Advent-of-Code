from functools import cmp_to_key
from json import loads


def is_pair_ordered(first, second):
    first_is_number = type(first) is int
    second_is_number = type(second) is int

    if first_is_number and second_is_number:
        return first - second

    if first_is_number != second_is_number:
        if first_is_number:
            return is_pair_ordered([first], second)
        else:
            return is_pair_ordered(first, [second])

    for a, b in zip(first, second):
        compare = is_pair_ordered(a, b)
        if compare != 0:
            return compare

    return len(first) - len(second)


def solve(path):
    with open(path) as input_file:
        lines = input_file.read().replace("\n\n", "\n").splitlines()

    packets = list(map(loads, lines))
    pairs = []
    for i in range(0, len(packets), 2):
        pairs.append(packets[i:i + 2])

    ordered_count = 0
    for i, (first, second) in enumerate(pairs, 1):
        if is_pair_ordered(first, second) < 0:
            ordered_count += i

    print("Part 1:", ordered_count)

    packets.extend(([[2]], [[6]]))
    packets.sort(key=cmp_to_key(is_pair_ordered))

    result = (packets.index([[2]]) + 1) * (packets.index([[6]]) + 1)
    print('Part 2:', result)


if __name__ == '__main__':
    solve("assets/input0")
    solve("assets/input")
