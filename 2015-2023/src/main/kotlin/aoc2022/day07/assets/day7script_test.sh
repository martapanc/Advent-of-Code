#!/bin/bash

mkdir root_test
cd root_test
mkdir a
truncate -s 14848514 b.txt
truncate -s 8504156 c.dat
mkdir d
cd a
mkdir e
truncate -s 29116 f
truncate -s 2557 g
truncate -s 62596 h.lst
cd e
truncate -s 584 i
cd ..
cd ..
cd d
truncate -s 4060174 j
truncate -s 8033020 d.log
truncate -s 5626152 d.ext
truncate -s 7214296 k

