/*******************************************************************
 ∗ @file: Proj1.java
 ∗ @description: This program implements the main driver for the program which checks if the argument in the terminal
                    has length 1. If not, then it prints out that it is an invalid argument.
 ∗ @author: Aidan Broadhead
 ∗ @date: September 26, 2025
 ********************************************************************/

import java.io.FileNotFoundException;

public class Proj1 {
    public static void main(String[] args) throws FileNotFoundException{
        if(args.length != 1){
            System.err.println("Argument count is invalid: " + args.length);
            System.exit(0);
        }
        new Parser(args[0]);
    }
}