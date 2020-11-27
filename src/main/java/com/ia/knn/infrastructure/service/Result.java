package com.ia.knn.infrastructure.service;

import java.util.stream.IntStream;

public class Result {

    /*
     * Complete the 'fizzBuzz' function below.
     *
     * The function accepts INTEGER n as parameter.
     */

    public static void fizzBuzz(int n) {
        // Write your code here
        IntStream.rangeClosed(1, n)
                .forEach(i -> printFizzBuzz(i));
    }

    private static void printFizzBuzz(int n) {
        boolean multiple3 = (n % 3) == 0;
        boolean multiple5 = (n % 5) == 0;
        if (!(multiple3 & multiple5)) {
            System.out.println(n);
        } else if(multiple3 & multiple5){
            System.out.println("FizzBuzz");
        } else {
            System.out.println(multiple3?"Fizz":"Buzz");
        }
    }
}
