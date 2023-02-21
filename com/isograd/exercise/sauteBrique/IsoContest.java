
/*******
 * Read input from System.in
 * Use: System.out.println to ouput your result to STDOUT.
 * Use: System.err.println to ouput debugging information to STDERR.
 * ***/
package com.isograd.exercise.sauteBrique;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

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

    int n = 0;
    String[] map = null;

    void process() {
        debug("##### INIT #####");

        n = readInt();
        map = new String[n];
        for(int i = 0; i < n; i++) {
            map[i] = readString();
        }

        int max = 0;
        int local = 0;
        for(int i = 0; i < n; i++) {
            if ("B".equals(map[i])) {
                local++;
                if (local > max) {
                    max = local;
                }
            } else {
                local = 0;
            }
        }

        output.println(max - 1);
    }


    void debug(String msg) {
        err.println(msg);
    }

    int readInt() {
        return Integer.parseInt(sc.nextLine());
    }

    String readString() {
        return sc.nextLine();
    }
}

