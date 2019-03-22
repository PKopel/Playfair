package krypto;

import java.io.InputStreamReader;
import java.util.Scanner;


public class Playfair {

    public static void main(String[] args) {
        Scanner com = new Scanner(new InputStreamReader(System.in));
        //String code=com.nextLine();
        //String decode=com.nextLine();
        String input = com.nextLine();
        SquareII s = new SquareII(input);
        input = com.nextLine();
        if (input.equalsIgnoreCase("decode")) {
            input = com.nextLine();
            try {
                System.out.println(s.uncode(input));
            } catch (Exception e) {
                System.out.print(e);
            }
        } else if (input.equalsIgnoreCase("code")) {
            input = com.nextLine();
            try {
                System.out.println(s.code(input));
            } catch (Exception e) {
                System.out.print(e);
            }
        }
    }
}

//yt-kg-mv

