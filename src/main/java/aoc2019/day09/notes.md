
    ** INTCODES **
    1: sum values at addresses (1) and (2), stores result at address (3)
    2: multiply values at addresses (1) and (2), stores result at address (3)
    3: read input and save it at address (1)
    4: output value of address (1)
    5: (1) != 0 set the pointer to the value of (2)
    6: (1) == 0 set the pointer to the value of (2)
    7: ((1) < (2) ? store 1 : store 0 ) at value of (3)
    8: ((1) == (2) ? store 1 : store 0 ) at value of (3)
    9: add the value of (1) to the relative base (starting from 0)

    ** MODES **
    0: position mode    =   parameter is an address
    1: immediate mode   =   parameter is used immediately
    2: relative mode    =   parameter + relative base is an address

    Parameters that an instruction writes to will never be in immediate mode.

    PARAMETER MODE DETAILS:
    - Immediate mode for 4 (104) should output the value of the parameter (e.g. 104,0 -> out: 0). Otherwise it should output the
         value at index (e.g. 3,...,104,0 -> out: 3)
    - 5,6: 105 - (1) and (2) can be in immediate mode
    - 7,8: 1107 - only (1) and (2) can be in immediate mode
    - all IntCodes can be in relative mode

    The pointer should increment based on the number of parameters (e.g. 4 for Codes 1 and 2, 2 for Codes 3 and 4),
    except for 5 and 6 where the pointer is updated as described

    V3 updates:
    - addresses can be beyond the initial code, if positive, and their initial parameter(s) is 0
    - support for large numbers