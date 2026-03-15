export class PriorityQueue<T> {
    private items: T[] = [];
    private comparator: (a: T, b: T) => number;

    constructor(comparator: (a: T, b: T) => number) {
        this.comparator = comparator;
    }

    enqueue(item: T): void {
        this.items.push(item);
        this.items.sort(this.comparator);
    }

    dequeue(): T | undefined {
        return this.items.shift();
    }

    size(): number {
        return this.items.length;
    }
}