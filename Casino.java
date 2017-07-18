/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package casino;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author bluswimmer
 */
public class Casino {
    
    static Random r = new Random();
    static int seed = 0;
    static List<Integer> opcodes = new ArrayList<>();
    static Scanner file;
    static Scanner in = new Scanner(System.in);
    static String input;
    static int index = 0;
    static List<Character> cells = new ArrayList<Character>(Collections.nCopies(30000, (char) 0));
    static int pointer = 0;

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        r.setSeed(0);
        System.out.println("Insert file name: ");
        File f = new File(in.nextLine());
        System.out.println("Insert Program Input: ");
        input = in.nextLine();
        file = new Scanner(f);
        if (readFile()) {
            execute();
        }
    }

    public static boolean readFile() {
        while (file.hasNextLine()) {
            String line = file.nextLine();
            if (line.length() == 0) {
                return false;
            }
            for (int i = 0; i < line.length(); i++) {
                switch (line.charAt(i)) {
                    case '$':
                        roll();
                        break;
                    default:
                        return false;
                }
            }
            opcodes.add(seed);
        }
        return true;
    }

    public static void roll() {
        seed = r.nextInt();
    }

    public static void execute() {
        for (int i = 0; i < opcodes.size(); i++) {
            switch (opcodes.get(i) & 0b111) {
                case 0:
                    addShiftRight();
                    break;
                case 1:
                    sub();
                    break;
                case 2:
                    shiftLeft();
                    break;
                case 3:
                    i += jump();
                    break;
                case 4:
                    out();
                    break;
                case 5:
                    in();
                    break;
                default:
                    return;
            }
        }
    }

    public static void addShiftRight() {
        char result = (char) (cells.get(pointer) + 1);
        cells.set(pointer, result);
        pointer++;
    }

    public static void sub() {
        char result = (char) (cells.get(pointer) - 1);
        cells.set(pointer, result);
    }

    public static void shiftLeft() {
        pointer--;
    }

    public static byte jump() {
        if (cells.get(pointer) != 0) {
            return (byte) (int) cells.get(pointer + 1);
        }
        return 0;
    }

    public static void out() {
        System.out.print(cells.get(pointer));
    }

    public static void in() {
        if (index == input.length()) {
            cells.set(pointer, (char) 0);
        } else {
            cells.set(pointer, input.charAt(index));
            index++;
        }
    }
}
