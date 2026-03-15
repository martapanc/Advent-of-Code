import {Coord, getNeighborCoords, Grid} from "@utils/grid";

type State = {
    curr: Coord,
    length: number
}

function calcRacetrackLength(grid: Grid, start: Coord, end: Coord, maxLength?: number) {
    let curr = new Coord(start.x, start.y);
    const visited = new Set<string>();

    const queue: State[] = [{ curr, length: 0}];

    while (queue.length > 0) {
        const { curr, length } = queue.shift()!;

        if (maxLength && length > maxLength) {
            return -1
        }

        if (curr.equals(end)) {
            return length;
        }

        if (visited.has(curr.serialize()))
            continue;

        visited.add(curr.serialize());

        const neighbors = getNeighborCoords(curr).filter(c => grid.get(c.serialize()) === '.' && !visited.has(c.serialize()))!;
        neighbors.forEach(n => {
            queue.push({ curr: n, length: length + 1 });
        });
    }

    return -1;
}


function calcNormalPathLength(grid: Grid, start: Coord, end: Coord) {
    const visited = new Set<string>();
    const queue: { curr: Coord, length: number, path: string}[] = [{ curr: start, length: 0, path: start.serialize()}];
    while (queue.length > 0) {
        const { curr, length, path } = queue.shift()!;

        const visitedKey = `${curr.serialize()}`

        if (curr.equals(end)) {
            return length + 1;
        } else {
            if (visited.has(visitedKey))
                continue;

            visited.add(visitedKey);
        }
        const neighbors = getNeighborCoords(curr).filter(c => grid.get(c.serialize()) === '.' && !visited.has(c.serialize()))!;
        neighbors.forEach(n => {
            queue.push({ curr: n, length: length + 1, path: path + n.serialize() });
        });
    }
}

function findPathsThroughWalls(grid: Grid, start: Coord, maxDistance: number) {
    const validCheats: Coord[][] = [];
    const visited = new Set<string>();
    const queue: { coord: Coord, path: Coord[], distance: number }[] = [
        { coord: start, path: [start], distance: 0 }
    ];

    while (queue.length > 0) {
        const { coord, path, distance } = queue.shift()!;

        if (distance > maxDistance) continue;
        if (visited.has(coord.serialize())) continue;

        visited.add(coord.serialize());

        if (!coord.equals(start) && grid.get(coord.serialize()) === '.') {
            validCheats.push(path);
            continue;
        }

        for (const n of getNeighborCoords(coord)) {
            if (!visited.has(n.serialize())) {
                queue.push({
                    coord: n,
                    path: [...path, n],
                    distance: distance + 1
                })
            }
        }
    }

    return validCheats;
}