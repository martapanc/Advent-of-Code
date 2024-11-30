#### Day 15

    north (1), south (2), west (3), east (4)

    0: The repair droid hit a wall. Its position has not changed.
    1: moved one step in the requested direction
    2: moved one step in the requested direction; new position is the location of the oxygen system.
    
Map<Point, Cell> : Cell can be of type "wall" or "path".   
Cell has a Direction : boolean map that keeps track of the neighbours already visited

Initial position is (0,0). Start from NORTH:  
- output 1: new point, based on direction, is a PATH; continue on same direction
- output 0: new point, based on direction, is a WALL: change direction

When visiting a cell already present in map, choose a direction that has not been already tried
