// Nama         : Hansel Valentino Tanoto
// NIM          : 13520046
// Deskripsi    : Program untuk menyelesaikan word search puzzle dengan algoritma brute force

import java.io.*;
import java.util.Scanner;
import java.util.Arrays;

/* ALGORITMA PROGRAM UTAMA */
public class WordSearchPuzzle {
    
    public static void main (String[] args) throws Exception{
        
        Scanner sc = new Scanner(System.in);                 // Create a Scanner object
        System.out.printf("Enter file path: ");   
        String path = sc.nextLine();                         // Read user input
        System.out.println("The file path is: " + path);     // Output user input
        
        int[] dim = dimension(path);
        int row = dim[0];
        int col = dim[1];
        int wCount = dim[2];

        char[][] puzzle = new char[row][col];
        String[] words = new String[wCount];
        int[][] solved = new int[row][col];

        readFile(path, puzzle, words);
        writePuzzle(puzzle);
        writeWords(words);
        
        /*
        System.out.println(row);
        System.out.println(col);
        System.out.println(wCount);
        */

        for (int i = 0; i < wCount; i++) {
            int colorId = i%11 + 1;
            down(puzzle,words[i],solved,colorId);
            up(puzzle,words[i],solved,colorId);
            right(puzzle,words[i],solved,colorId);
            left(puzzle,words[i],solved,colorId);
        }
        writeSolved(puzzle, solved);
 
        
        sc.close();
        return;
    }

    // Realisasi Prosedur
    
    public static int[] dimension (String path) throws Exception {
        // result[0] = row, result[1] = col, result[2] = wCount
        int[] result = new int[3]; 
        int separator = 0;
        Scanner sc = new Scanner(new BufferedReader(new FileReader(path)));
        
        result[1] = sc.nextLine().length()/2 + 1;
        int lineCount = 1;
        while (sc.hasNextLine()) {
            lineCount += 1;
            String line = sc.nextLine();
            if (line.equals("")) {
                separator = lineCount;
            }
        }
        result[0] = separator - 1;
        result[2] = lineCount - separator;
        
        sc.close();
        return result;
    }
    

    // COLOR
    public static final String ANSI_RESET   = "\u001B[0m";
    public static final String ITALIC		= "\u001B[3m";
    public static final String UNDERLINE	= "\u001B[4m";
    public static final String ANSI_COLOR[] = {"\u001B[37m", "\u001B[32m", "\u001B[33m", "\u001B[34m", "\u001B[35m", "\u001B[36m", "\u001B[91m", "\u001B[92m", "\u001B[93m", "\u001B[94m", "\u001B[95m", "\u001B[96m"};


    // READ AND WRITE
    public static void readFile(String path, char[][] puzzle, String[] words) throws Exception {
        String line;
        Scanner sc = new Scanner(new BufferedReader(new FileReader(path)));
        for (int i = 0; i < puzzle.length; i++) {
            line = sc.nextLine();
            for (int j = 0; j < line.length(); j++) {
                if (line.toCharArray()[j] != ' ') {
                    puzzle[i][j/2] = line.toCharArray()[j];
                }
            }
        }
        line = sc.nextLine();
        for (int k = 0; k < words.length; k++) {
            line = sc.nextLine();
            words[k] = line;
        }
    }
    
    public static void writePuzzle (char[][] puzzle) {
        System.out.println("\nWORD SEARCH PUZZLE");
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[0].length; j++) {
                //System.out.println(puzzle[i][j]);
                System.out.printf("%c ", puzzle[i][j]);
            }
            System.out.printf("\n");
        }
    }
    
    public static void writeWords (String[] words) {
        System.out.println("\nWord List:");
        for (int i = 0; i < words.length; i++) {
            //System.out.println(puzzle[i][j]);
            System.out.println(words[i]);
        }
    }
    
    public static void writeSolved (char[][] puzzle, int[][] solved) {
        System.out.println("\nSolved:");
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[0].length; j++) {
                //System.out.println(puzzle[i][j]);
                //System.out.printf(ANSI_COLOR[solved[i][j]]);
                System.out.printf(ANSI_COLOR[solved[i][j]]);
                if (solved[i][j] != 0) {
                    System.out.printf(ITALIC);
                    System.out.printf(UNDERLINE);
                }
                System.out.printf("%c ", puzzle[i][j]);
                System.out.printf(ANSI_RESET);
            }
            System.out.printf("\n");
        }
    }


    // PROSEDUR STRING MATCHING
    public static void down (char[][] puzzle, String word, int[][] solved, int colorId) {
        int row = puzzle.length;
        int col = puzzle[0].length;
        int wordLen = word.length();
        for (int h = 0; h < col; h++) {
            for (int i = 0; i < row-wordLen; i++) {
                int j = 0;
                while ((j < wordLen) && (puzzle[i+j][h] == word.charAt(j))) {
                    j++;
                }
                if (j == wordLen) {
                    for (int k = i; k < i+wordLen; k++) {
                        solved[k][h] = colorId;
                    }
                    return;
                }
            }
        }
    }

    public static void up (char[][] puzzle, String word, int[][] solved, int colorId) {
        int row = puzzle.length;
        int col = puzzle[0].length;
        int wordLen = word.length();
        for (int h = 0; h < col; h++) {
            for (int i = row-1; i >= wordLen; i--) {
                int j = 0;
                while ((j < wordLen) && (puzzle[i-j][h] == word.charAt(j))) {
                    j++;
                }
                if (j == wordLen) {
                    for (int k = i; k > i-wordLen; k--) {
                        solved[k][h] = colorId;
                    }
                    return;
                }
            }
        }
    }

    public static void right (char[][] puzzle, String word, int[][] solved, int colorId) {
        int row = puzzle.length;
        int col = puzzle[0].length;
        int wordLen = word.length();
        for (int h = 0; h < row; h++) {
            for (int i = 0; i < col-wordLen; i++) {
                int j = 0;
                while ((j < wordLen) && (puzzle[h][i+j] == word.charAt(j))) {
                    j++;
                }
                if (j == wordLen) {
                    for (int k = i; k < i+wordLen; k++) {
                        solved[h][k] = colorId;
                    }
                    return;
                }
            }
        }
    }

    public static void left (char[][] puzzle, String word, int[][] solved, int colorId) {
        int row = puzzle.length;
        int col = puzzle[0].length;
        int wordLen = word.length();
        
        for (int h = 0; h < row; h++) {
            for (int i = col-1; i >= wordLen; i--) {
                int j = 0;
                while ((j < wordLen) && (puzzle[h][i-j] == word.charAt(j))) {
                    j++;
                }
                if (j == wordLen) {
                    for (int k = i; k > i-wordLen; k--) {
                        solved[h][k] = colorId;
                    }
                    return;
                }
            }
        }
    }

}