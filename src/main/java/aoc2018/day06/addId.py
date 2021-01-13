import re

with open("in1", "r") as fp:
    line = fp.readline()
    i = 1
    while line:
        line = line.replace("\n", "")
        vals = line.split(", ")
        print(str(i) + "," + vals[0] + "," + vals[1])
        i+=1
        line = fp.readline()