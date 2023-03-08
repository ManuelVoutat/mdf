
/*******
 * Read input from System.in
 * Use: System.out.println to ouput your result to STDOUT.
 * Use: System.err.println to ouput debugging information to STDERR.
 * ***/
package com.isograd.exercise.reservoirDogs;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Random;
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

    void process() {
        debug("##### INIT #####");
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        double[][] pts = new double[N][2];
        for (int i = 0; i < N; i++) {
            pts[i][0] = scanner.nextDouble();
            pts[i][1] = scanner.nextDouble();
        }
        double[] circle = makeCircle(pts);
        System.out.println((int)Math.ceil(circle[0]) + " " + (int)Math.ceil(circle[1]));

    }

    void debug(String msg) {
        err.println(msg);
    }
    private static final double MULTIPLICATIVE_EPSILON = 1 + 1e-14;
    public static double[] makeDiameter(double[] a, double[] b) {
        double cx = (a[0] + b[0]) / 2.0;
        double cy = (a[1] + b[1]) / 2.0;
        double r0 = Math.hypot(cx - a[0], cy - a[1]);
        double r1 = Math.hypot(cx - b[0], cy - b[1]);
        return new double[] {cx, cy, Math.max(r0, r1)};
    }
    public static boolean isInCircle(double[] c, double[] p) {
        if (c != null) {
            double distance = Math.hypot(p[0] - c[0], p[1] - c[1]);
            return distance <= c[2] * MULTIPLICATIVE_EPSILON;
        }
        return false;
    }

    public static double[] makeCircle(double[][] points) {
        double[][] shuffled = new double[points.length][2];
        for (int i = 0; i < points.length; i++) {
            shuffled[i][0] = points[i][0];
            shuffled[i][1] = points[i][1];
        }
        shuffleArray(shuffled);
        double[] c = null;
        for (int i = 0; i < shuffled.length; i++) {
            double[] p = shuffled[i];
            if (c == null || !isInCircle(c, p)) {
                c = makeCircleOnePoint(Arrays.copyOfRange(shuffled, 0, i + 1), p);
            }
        }
        return c;
    }

    private static void shuffleArray(double[][] arr) {
        Random rnd = new Random();
        for (int i = arr.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            double[] temp = arr[index];
            arr[index] = arr[i];
            arr[i] = temp;
        }
    }

    public static double[] makeCircleOnePoint(double[][] points, double[] p) {
        double[] c = new double[]{p[0], p[1], 0.0};
        for (int i = 0; i < points.length; i++) {
            double[] q = points[i];
            if (!isInCircle(c, q)) {
                if (c[2] == 0.0) {
                    c = makeDiameter(p, q);
                } else {
                    c = makeCircleTwoPoints(Arrays.copyOfRange(points, 0, i + 1), p, q);
                }
            }
        }
        return c;
    }

    public static double[] makeCircleTwoPoints(double[][] points, double[] p, double[] q) {
        double[] circ = makeDiameter(p, q);
        double[] left = null;
        double[] right = null;
        double px = p[0];
        double py = p[1];
        double qx = q[0];
        double qy = q[1];
        for (double[] r : points) {
            if (isInCircle(circ, r)) {
                continue;
            }
            double cross = crossProduct(px, py, qx, qy, r[0], r[1]);
            double[] c = makeCircumcircle(p, q, r);
            if (c == null) {
                continue;
            } else if (cross > 0.0 && (left == null || crossProduct(px, py, qx, qy, c[0], c[1]) > crossProduct(px, py, qx, qy, left[0], left[1]))) {
                left = c;
            } else if (cross < 0.0 && (right == null || crossProduct(px, py, qx, qy, c[0], c[1]) < crossProduct(px, py, qx, qy, right[0], right[1]))) {
                right = c;
            }
        }
        if (left == null && right == null) {
            return circ;
        } else if (left == null) {
            return right;
        } else if (right == null) {
            return left;
        } else {
            return left[2] <= right[2] ? left : right;
        }
    }

    public static double crossProduct(double x0, double y0, double x1, double y1, double x2, double y2) {
        return (x1 - x0) * (y2 - y0) - (y1 - y0) * (x2 - x0);
    }

    public static double[] makeCircumcircle(double[] a, double[] b, double[] c) {
        double ox = (Math.min(a[0], Math.min(b[0], c[0])) + Math.max(a[0], Math.max(b[0], c[0]))) / 2.0;
        double oy = (Math.min(a[1], Math.min(b[1], c[1])) + Math.max(a[1], Math.max(b[1], c[1]))) / 2.0;
        double ax = a[0] - ox;
        double ay = a[1] - oy;
        double bx = b[0] - ox;
        double by = b[1] - oy;
        double cx = c[0] - ox;
        double cy = c[1] - oy;
        double d = (ax * (by - cy) + bx * (cy - ay) + cx * (ay - by)) * 2.0;
        if (d == 0.0) {
            return null;
        }
        double x = ox + ((ax * ax + ay * ay) * (by - cy) + (bx * bx + by * by) * (cy - ay) + (cx * cx + cy * cy) * (ay - by)) / d;
        double y = oy + ((ax * ax + ay * ay) * (cx - bx) + (bx * bx + by * by) * (ax - cx) + (cx * cx + cy * cy) * (bx - ax)) / d;
        double ra = Math.hypot(x - a[0], y - a[1]);
        double rb = Math.hypot(x - b[0], y - b[1]);
        double rc = Math.hypot(x - c[0], y - c[1]);
        return new double[] {x, y, Math.max(ra, Math.max(rb, rc))};
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

