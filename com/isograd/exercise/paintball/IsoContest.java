/*******
 * Read input from System.in
 * Use: System.out.println to ouput your result to STDOUT.
 * Use: System.err.println to ouput debugging information to STDERR.
 * ***/
package com.isograd.exercise.paintball;

import java.util.Scanner;

public class IsoContest {
    public static void main(String[] args) throws Exception {
        String line;
        Scanner sc = new Scanner(System.in);
        StringBuilder res = new StringBuilder();

        while (sc.hasNextLine()) {
            line = sc.nextLine();
            if (line.equalsIgnoreCase("exit0")) {
                break;
            }
            /* Lisez les données et effectuez votre traitement */
            if (!isInteger(line) && line.equals(reverseString(line))) {
                res.append(line).append(" ");
            }
        }
        System.out.println(res);
        /* Vous pouvez aussi effectuer votre traitement une fois que vous avez lu toutes les données.*/
    }

    public static String reverseString(String str) {
        String reversedStr = "";
        for (int i = str.length() - 1; i >= 0; i--) {
            reversedStr += str.charAt(i);
        }
        return reversedStr;
    }

    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}