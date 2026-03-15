import {Gate} from "../day24";

function generateMermaidDiagram(wires: Map<string, number>, gates: Map<string, Gate>, solvedGates: Map<string, number>) {
    let diagram = "graph TD\n";

    // Add input wires (x and y)
    for (const [key, value] of wires) {
        diagram += `    ${key}[<b>${key}: ${value}</b>] --> ${key}_node\n`;
    }

    // Add gates and their connections
    for (const [key, gate] of gates) {
        const { wire1, wire2, op } = gate;
        const result = solvedGates.has(key) ? `\\n${solvedGates.get(key)}` : "";

        // Define the gate node
        diagram += `    ${key}_node[${op}] --> ${key}_result\n`;
        diagram += `    ${wire1} --> ${key}_node\n`;
        diagram += `    ${wire2} --> ${key}_node\n`;
    }

    // Add output wires (z)
    for (const [key, value] of solvedGates) {
        if (key.startsWith("z")) {
            diagram += `    ${key}_result[<b>${key}: ${value}</b>]\n`;
        }
    }

    return diagram;
}