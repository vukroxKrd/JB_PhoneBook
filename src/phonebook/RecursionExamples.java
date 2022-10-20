package phonebook;

import java.util.Scanner;

public class RecursionExamples {
    /* Fix this method */
//    public static String method(int n) {
//        if (n == 0 || n == 1) {
//            return String.valueOf(n);
//        }
//        return method(n / 2) + String.valueOf(n % 2);
//    }

    public static long method(long a, long b) {
        if (b == 0) {
            return b;
        }
        return a + method(a, b - 1);
    }

    /* Do not change code below */
    public static void mainCopy(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        printDollars(7);
    }

    public static void printDollars(int n) {
        if (n > 1) {
            printDollars(n - 1);
        }

        for (int i = 0; i < n; i++) {
            System.out.print("$");
        }
    }
}
