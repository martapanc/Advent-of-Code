import path from "node:path";
import {readInputLineByLine} from "@utils/io";
import {Coord} from "@utils/grid";

export async function part1(inputFile: string) {
    return await day9(inputFile, findAreaOfLargestRectangle);
}

export async function part2(inputFile: string) {
    return await day9(inputFile, findAreaOfLargestRectanglePart2);
}

async function day9(inputFile: string, calcFn?: (coords: Coord[]) => number) {
    const inputPath = path.join(__dirname, inputFile);
    const lines = await readInputLineByLine(inputPath);
    const coords = lines.map(line => {
        const split = line.split(",").map(Number);
        return new Coord(split[0], split[1]);
    });

    return calcFn?.(coords);
}

function findAreaOfLargestRectangle(coords: Coord[]): number {
    let maxArea = 0;

    coords.forEach((a) => {
        coords.forEach((b) => {
            if (!a.equals(b)) {
                const area = findArea(a, b);
                if (area > maxArea) {
                    maxArea = area;
                }
            }
        });
    });

    return maxArea;
}

function findAreaOfLargestRectanglePart2(coords: Coord[]): number {
    const edges = buildEdges(coords);
    const {xs, ys, xIndex, yIndex} = buildCompressedAxes(coords);
    const prefix = buildInsidePrefix(edges, xs, ys);

    let maxArea = 0;

    for (let i = 0; i < coords.length; i++) {
        const a = coords[i];
        for (let j = i + 1; j < coords.length; j++) {
            const b = coords[j];
            if (a.equals(b)) continue;

            const loX = Math.min(a.x, b.x);
            const hiX = Math.max(a.x, b.x);
            const loY = Math.min(a.y, b.y);
            const hiY = Math.max(a.y, b.y);

            const area = (hiX - loX + 1) * (hiY - loY + 1);
            if (area <= maxArea) continue;

            const x0 = xIndex.get(loX);
            const x1 = xIndex.get(hiX + 1);
            const y0 = yIndex.get(loY);
            const y1 = yIndex.get(hiY + 1);
            if (x0 === undefined || x1 === undefined || y0 === undefined || y1 === undefined) {
                continue;
            }

            const insideSum =
                prefix[y1][x1] -
                prefix[y0][x1] -
                prefix[y1][x0] +
                prefix[y0][x0];

            if (insideSum === area) {
                maxArea = area;
            }
        }
    }

    return maxArea;
}

export function findArea(a: Coord, b: Coord): number {
    const sideA = Math.abs(a.x - b.x) + 1;
    const sideB = Math.abs(a.y - b.y) + 1;
    return sideA * sideB;
}

type VerticalEdge = { x: number; loY: number; hiY: number };
type HorizontalEdge = { y: number; loX: number; hiX: number };
type Edges = { vertical: VerticalEdge[]; horizontal: HorizontalEdge[] };

function buildEdges(coords: Coord[]): Edges {
    const vertical: VerticalEdge[] = [];
    const horizontal: HorizontalEdge[] = [];

    for (let i = 0; i < coords.length; i++) {
        const a = coords[i];
        const b = coords[(i + 1) % coords.length];

        if (a.x === b.x) {
            const loY = Math.min(a.y, b.y);
            const hiY = Math.max(a.y, b.y);
            vertical.push({x: a.x, loY, hiY});
        } else {
            const loX = Math.min(a.x, b.x);
            const hiX = Math.max(a.x, b.x);
            horizontal.push({y: a.y, loX, hiX});
        }
    }

    return {vertical, horizontal};
}

function buildCompressedAxes(coords: Coord[]) {
    const xsSet = new Set<number>();
    const ysSet = new Set<number>();

    for (const c of coords) {
        xsSet.add(c.x);
        xsSet.add(c.x + 1);
        ysSet.add(c.y);
        ysSet.add(c.y + 1);
    }

    const xs = Array.from(xsSet).sort((a, b) => a - b);
    const ys = Array.from(ysSet).sort((a, b) => a - b);

    const xIndex = new Map<number, number>();
    const yIndex = new Map<number, number>();

    xs.forEach((x, i) => xIndex.set(x, i));
    ys.forEach((y, i) => yIndex.set(y, i));

    return {xs, ys, xIndex, yIndex};
}

function buildInsidePrefix(edges: Edges, xs: number[], ys: number[]) {
    const rowCount = ys.length - 1;
    const colCount = xs.length - 1;
    const dx = new Array<number>(colCount);
    for (let i = 0; i < colCount; i++) {
        dx[i] = xs[i + 1] - xs[i];
    }
    const xIndex = new Map<number, number>();
    xs.forEach((x, i) => xIndex.set(x, i));

    const prefix: Float64Array[] = [];
    prefix.push(new Float64Array(colCount + 1));

    for (let row = 0; row < rowCount; row++) {
        const y = ys[row];
        const height = ys[row + 1] - y;

        const crossings: number[] = [];
        for (const edge of edges.vertical) {
            if (y >= edge.loY && y < edge.hiY) {
                crossings.push(edge.x);
            }
        }
        crossings.sort((a, b) => a - b);

        const intervals: Array<[number, number]> = [];
        for (let i = 0; i + 1 < crossings.length; i += 2) {
            const start = crossings[i];
            const endInclusive = crossings[i + 1];
            intervals.push([start, endInclusive + 1]);
        }

        for (const edge of edges.horizontal) {
            if (edge.y === y) {
                intervals.push([edge.loX, edge.hiX + 1]);
            }
        }

        intervals.sort((a, b) => a[0] - b[0] || a[1] - b[1]);

        const merged: Array<[number, number]> = [];
        for (const [start, end] of intervals) {
            if (merged.length === 0) {
                merged.push([start, end]);
                continue;
            }
            const last = merged[merged.length - 1];
            if (start <= last[1]) {
                if (end > last[1]) last[1] = end;
            } else {
                merged.push([start, end]);
            }
        }

        const rowWeights = new Float64Array(colCount);
        for (const [start, end] of merged) {
            const startIndex = xIndex.get(start);
            const endIndex = xIndex.get(end);
            if (startIndex === -1 || endIndex === -1) continue;
            if (startIndex === undefined || endIndex === undefined) continue;
            for (let x = startIndex; x < endIndex; x++) {
                rowWeights[x] = dx[x] * height;
            }
        }

        const prev = prefix[row];
        const current = new Float64Array(colCount + 1);
        for (let x = 0; x < colCount; x++) {
            current[x + 1] = prev[x + 1] + current[x] - prev[x] + rowWeights[x];
        }
        prefix.push(current);
    }

    return prefix;
}
