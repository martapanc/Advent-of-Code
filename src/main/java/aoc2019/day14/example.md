## Day 14

##### Part 1 
- Start from the list that produces 1 FUEL
- For every chemical, find the list that produces it
- Divide the needed amount by the quantity produced; if remainder != 0, take ceil(result).
- Save ceil(result) - needed amount as a "storage" for the current element
- For the following chem reductions, check if current chem is in storage. If so, subtract the stored value from the needed amount and continue
- Continue until the "producers list" only contains ORE

##### Part 2 
- do the process that produces 1 FUEL. 
- Run again without resetting the storage. This way, obtaining 1 FUEL should gradually require fewer ORE
- Every time 1 FUEL is produced, sum the ORE needed. Repeat and keep track of FUEL produced until ORE reaches 1 Trillion. 
- Return last number of FUEL obtained.

-----

#### Example step by step

    44 XJWVT    = 154 DCFZ, 154 PSHF
    5 KHKGT     = 3 DCFZ, 7 NZVS, 5 HKGWZ, 10 PSHF
    1 QDVJ      = 12 HKGWZ, 1 GPVTF, 8 PSHF
    29 NZVS     = 942 ORE
    9 GPVTF     = 825 ORE
    48 HKGWZ    = 1770 ORE
    
    154 DCFZ    = 4290 ORE
    154 PSHF    = 3938 ORE
    
    3 DCFZ      = 165 ORE
    7 NZVS      = 314 ORE
    5 HKGWZ     = 177 ORE
    10 PSHF     = 358 ORE
    
    12 HKGWZ    = 354 ORE (2 already in storage
    1 GPVTF     = 0 ORE (1 already in storage)
    8 PSHF      = 179 ORE (4 in storage, 7 still needed, so 3 new waste)
    
    157 ORE => 5 NZVS
    165 ORE => 6 DCFZ
    165 ORE => 2 GPVTF
    177 ORE => 5 HKGWZ
    179 ORE => 7 PSHF
    7 DCFZ, 7 PSHF => 2 XJWVT
    12 HKGWZ, 1 GPVTF, 8 PSHF => 9 QDVJ
    3 DCFZ, 7 NZVS, 5 HKGWZ, 10 PSHF => 8 KHKGT
    44 XJWVT, 5 KHKGT, 1 QDVJ, 29 NZVS, 9 GPVTF, 48 HKGWZ => 1 FUEL
    
    * STORAGE
    KHKGT 3
    QDVJ 8
    NZVS 1 + 3
    GPVTF 1/
    HKGWZ 2/
    DCFZ 2 + 3
    PSHF 4/ 3
    
    TOT = 13312 ORE