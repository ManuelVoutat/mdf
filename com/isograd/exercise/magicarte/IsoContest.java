/*******
 * Read input from System.in
 * Use: System.out.println to ouput your result to STDOUT.
 * Use: System.err.println to ouput debugging information to STDERR.
 * ***/
package com.isograd.exercise.magicarte;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class IsoContest {
    public static void main(String[] args) throws Exception {
        String line;
        Scanner sc = new Scanner(System.in);
        StringBuilder res = new StringBuilder();
        int[][] matrix = new int[3][3];
        var i = 0;

        while (sc.hasNextLine()) {
            line = sc.nextLine();
            if (line.equalsIgnoreCase("exit0")) {
                break;
            }
            matrix[i] = Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).toArray();
            i++;
        }
        var nbCartes = matrix[0][0];
        List<Integer> sacha = new ArrayList<>(Arrays.stream(matrix[1]).boxed().toList());
        List<Integer> moi = new ArrayList<>(Arrays.stream(matrix[2]).boxed().toList());
        while (sacha.size() > 0 && moi.size() > 0) {
            for (int j = nbCartes - 1; j >= 0; j--) {
                if (sacha.size() < j || moi.size() < j ) break;
                if (sacha.get(j) > moi.get(j)) {
                    moi.remove(j);
                } else if (sacha.get(j) < moi.get(j)) {
                    sacha.remove(j);
                } else {
                    moi.remove(j);
                    sacha.remove(j);
                }
            }
            nbCartes = Math.max(sacha.size(), moi.size());
        }
        System.out.println(sacha.size() < moi.size() ? moi.size() == 0 ? 'N' : 'G' : 'P');
        /* Vous pouvez aussi effectuer votre traitement une fois que vous avez lu toutes les données.*/
    }
}