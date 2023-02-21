
/*******
 * Read input from System.in
 * Use: System.out.println to ouput your result to STDOUT.
 * Use: System.err.println to ouput debugging information to STDERR.
 * ***/
package com.isograd.exercise.cocktail;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
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

    int n = 8;
    void process() {
        debug("##### INIT #####");
        readStringToInt();

    }
    int[] readStringToInt() {
        return Arrays.stream(sc.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
    }

    void debug(String msg) {
        err.println(msg);
    }
}

