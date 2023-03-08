
/*******
 * Read input from System.in
 * Use: System.out.println to ouput your result to STDOUT.
 * Use: System.err.println to ouput debugging information to STDERR.
 * ***/
package com.isograd.exercise.cocktail;

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
        var first = readStringToInt();
        var nbIngredient = first.get(0);
        var nbContrainte = first.get(1);
        var list = new ArrayList<List<String>>();
        var map = new HashMap<String, Set<String>>();
        for (int i = 0; i < nbContrainte; i++) {
            list.add(readString());
        }
        list.forEach(contraint -> {
            if (!map.containsKey(contraint.get(0))) {
                map.put(contraint.get(0), new HashSet<>());
            }
            if (!map.containsKey(contraint.get(1))) {
                map.put(contraint.get(1), new HashSet<>());
            }
            var acc = map.get(contraint.get(1));
            acc.add(contraint.get(0));
            acc.addAll(map.get(contraint.get(0)));
            map.replace(contraint.get(1), acc);
            map.forEach((x,y) -> {
                if (y.contains(contraint.get(1))) {
                    y.addAll(map.get(contraint.get(1)));
                    map.replace(x, y);
                }
            });
            var ko =0;
        });

        LinkedHashMap<String, Set<String>> sortedMap = new LinkedHashMap<>();
        map.entrySet()
                .stream()
                .sorted(Comparator.comparing(entry -> entry.getValue().size()))
                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));

        final String[] result = {""};
        sortedMap.forEach((x, y) -> {
            result[0] += x;
            result[0] += " < ";
        });
        output.println(result[0].substring(0, result[0].length() - 3));;
    }

    List<Integer> readStringToInt() {
        return Arrays.stream(sc.nextLine().split(" ")).map(Integer::parseInt).collect(Collectors.toList());
    }

    List<String> readString() {
        return Arrays.stream(sc.nextLine().split(" ")).collect(Collectors.toList());
    }

    void debug(String msg) {
        err.println(msg);
    }
}

