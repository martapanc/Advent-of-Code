package aoc2019.day23;

import java.util.ArrayList;
import java.util.Scanner;

public class A {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<String> list = new ArrayList<>();
        while(sc.hasNext()) {
            list.add(sc.nextLine());
        }
        Inst[] program = new Inst[list.size()];
        int eggs = Integer.parseInt(args[0]);
        int[] reg = new int[]{12, 0, 0, 0};
        String cpy = "cpy", jnz = "jnz", inc = "inc", dec = "dec", tgl = "tgl";

        for(int i = 0; i<program.length; i++) {
            String[] a = list.get(i).split(" ");
            if(a[0].equals(cpy)) {
                int x = ch0(a[1]);
                boolean valx = false;
                if(!(x>= 0 && x <= 3)) {
                     x = Integer.parseInt(a[1]);
                     valx = true;
                }
                program[i] = new cpy(x, valx, ch0(a[2]), false);

            } else if(a[0].equals(inc))
                program[i] = new inc(ch0(a[1]));

            else if(a[0].equals(dec))
                program[i] = new dec(ch0(a[1]));

            else if(a[0].equals(jnz)) {
                int x = ch0(a[1]);
                int y = ch0(a[2]);
                boolean valx = false;
                boolean valy = false;
                if(!(x<=3 && x>=0)) {
                    x = Integer.parseInt(a[1]);
                    valx = true;
                }
                if(!(y<=3 && y>=0)){
                    y = Integer.parseInt(a[2]);
                    valy = true;
                }
                program[i] = new jnz(x, valx, y, valy);

            }else
                program[i] = new tgl(ch0(a[1]));
        }
        int i = 0;
        while(i<program.length) {
            i = program[i].exec(reg, program, i);
        }
        System.out.println(reg[0]);
    }
    public static int ch0(String s) {
        return s.charAt(0) - 'a';
    }
}
abstract class Inst {
    abstract int exec(int[] reg, Inst[] prog, int id);
    abstract Inst toggle();
}

class tgl extends Inst{
    int x;
    public tgl(int x) {
        this.x = x;
    }
    int exec(int reg[], Inst[] prog, int id) {
        int place = id + reg[x];
        if(place>= 0 && place < prog.length) {
            prog[place] = prog[place].toggle();
        }
        return id + 1;
    }
    Inst toggle() {
        return new inc(x);
    }
}
class cpy extends Inst{
    int x;
    boolean valx;
    int y;
    boolean valy;
    public cpy(int x, boolean valx, int y, boolean valy) {
        this.x = x; this.valx = valx;
        this.y = y; this.valy = valy;
    }
    int exec(int reg[], Inst[] prog, int id) {
        if(valy) return id + 1;
        
        if(valx) {
            reg[y] = x;
        } else {
            reg[y] = reg[x]; 
        }
        return id + 1;
    }
    Inst toggle() {
        return new jnz(x, valx, y, valy);
    }
}
class inc extends Inst{
    int x;
    public inc(int x) {
        this.x = x;
    }
    int exec(int reg[], Inst[] prog, int id) {
        reg[x]++;
        return id + 1;
    }
    Inst toggle() {
        return new dec(x);
    }
}
class dec extends Inst{
    int x;
    public dec(int x) {
        this.x = x;
    }
    int exec(int reg[], Inst[] prog, int id) {
        reg[x]--;
        return id + 1;
    }
    Inst toggle() {
        return new inc(x);
    }
}
class jnz extends Inst{
    int x;
    boolean valx;
    int y;
    boolean valy;
    public jnz(int x, boolean valx, int y, boolean valy) {
        this.x = x; this.valx = valx;
        this.y = y; this.valy = valy;
    }
    int exec(int reg[], Inst[] prog, int id) {
        if((valx && x!= 0) || (!valx && reg[x] != 0)) {
            if(valy) {
                return id + y;
            } else {
                return id + reg[y];
            }
        }
        return id + 1;
    }
    Inst toggle() {
        return new cpy(x, valx, y, valy);
    }
}