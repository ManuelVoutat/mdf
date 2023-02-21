
/*******
 * Read input from System.in
 * Use: System.out.println to ouput your result to STDOUT.
 * Use: System.err.println to ouput debugging information to STDERR.
 * ***/
package com.isograd.exercise.magicarte;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.*;
import java.util.stream.Collectors;

public class IsoContest {

    final InputStream input;
    final PrintStream output;
    final PrintStream err;
    final Scanner sc;

    IsoContest(InputStream input, PrintStream output, PrintStream err) {
        this.input = input;
        this.output = output;
        this.err = err;
        sc = new Scanner(input);
    }

    public static void main(String[] argv) {
        new IsoContest(System.in, System.out, System.err).process();
    }

    void process() {
        debug("##### INIT #####");
        var nbCarte = readInt();
        List<Integer> sacha = readStringToInt();
        List<Integer> me = readStringToInt();
        var i = 0;
        while( i < sacha.size() && i < me.size()) {
            if (sacha.get(i) > me.get(i)) {
                sacha.add(sacha.get(i));
            } else if (sacha.get(i) < me.get(i)) {
                me.add(me.get(i));
            }
            i++;
        }
        if (sacha.size() == me.size()) {
            output.println("N");
        } else if (sacha.size() > me.size()) {
            output.println("P");
        } else {
            output.println("G");
        }

    }


    int readInt() {
        return Integer.parseInt(sc.nextLine());
    }
    List<Integer> readStringToInt() {
        return Arrays.stream(sc.nextLine().split(" ")).map(Integer::parseInt).collect(Collectors.toList());
    }


    void debug(String msg) {
        err.println(msg);
    }
}

