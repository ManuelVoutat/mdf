
/*******
 * Read input from System.in
 * Use: System.out.println to ouput your result to STDOUT.
 * Use: System.err.println to ouput debugging information to STDERR.
 * ***/
package com.isograd.exercise.towerDefense;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

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
        AtomicReference<Coordonnee> roi = new AtomicReference<>();
        List<Coordonnee> tours = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            AtomicInteger j = new AtomicInteger();
            int finalI = i;
            var str = readString().chars()
                    .mapToObj(c -> String.valueOf((char) c))
                    .toList();
            str.forEach(
                    c -> {
                        if (Objects.equals(c.toString(), "T")) {
                            tours.add(new Coordonnee(finalI, j.get()));
                        } else if (Objects.equals(c.toString(), "R")){
                            roi.set(new Coordonnee(finalI, j.get()));
                        }
                        j.getAndIncrement();
                    }
            );
        }

        if (
                isCheckByAnyTower(roi.get().x, roi.get().y, tours) &&
                isCheckByAnyTower(roi.get().x + 1, roi.get().y, tours) &&
                isCheckByAnyTower(roi.get().x, roi.get().y + 1, tours) &&
                isCheckByAnyTower(roi.get().x + 1, roi.get().y + 1, tours) &&
                isCheckByAnyTower(roi.get().x -1, roi.get().y, tours) &&
                isCheckByAnyTower(roi.get().x, roi.get().y -1, tours) &&
                isCheckByAnyTower(roi.get().x-1, roi.get().y-1, tours) &&
                isCheckByAnyTower(roi.get().x+1, roi.get().y-1, tours) &&
                isCheckByAnyTower(roi.get().x-1, roi.get().y+1, tours)

        ) {
            output.println("check-mat");
        } else if (!isCheckByAnyTower(roi.get().x, roi.get().y, tours) &&
                isCheckByAnyTower(roi.get().x + 1, roi.get().y, tours) &&
                isCheckByAnyTower(roi.get().x, roi.get().y + 1, tours) &&
                isCheckByAnyTower(roi.get().x + 1, roi.get().y + 1, tours) &&
                isCheckByAnyTower(roi.get().x -1, roi.get().y, tours) &&
                isCheckByAnyTower(roi.get().x, roi.get().y -1, tours) &&
                isCheckByAnyTower(roi.get().x-1, roi.get().y-1, tours) &&
                isCheckByAnyTower(roi.get().x+1, roi.get().y-1, tours) &&
                isCheckByAnyTower(roi.get().x-1, roi.get().y+1, tours)
        ) {
            output.println("pat");
        } else {
            output.println("still-in-game");
        }


    }

    void debug(String msg) {
        err.println(msg);
    }

    boolean isCheck(int xR, int yR, int xT, int yT) {
        return xR == xT || yR == yT;
    }

    boolean isCheckByAnyTower(int xR, int yR, List<Coordonnee> tow) {
        return tow.stream()
                // Mapper les booléens en 0 et 1
                .anyMatch(coordonnee -> xR < 0 || xR > 7 || yR < 0 || yR > 7 ||
                        isCheck(xR, yR, coordonnee.x, coordonnee.y));
    }
    class Coordonnee {

        Coordonnee(int x, int y) {
            this.x = x;
            this.y = y;
        }
        int x;
        int y;
    }
    int readInt() {
        return Integer.parseInt(sc.nextLine());
    }

    int[] readStringToInt() {
        return Arrays.stream(sc.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
    }
    String readString() {
        return sc.nextLine();
    }
}

