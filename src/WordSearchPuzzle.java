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
        long[] solvingTime = new long[wCount];
        int[] comparison = new int[wCount];

        readFile(path, puzzle, words);
        writePuzzle(puzzle);
        writeWords(words);

        for (int i = 0; i < wCount; i++) {
            int colorId = i%11 + 1;
            boolean found = false;
            int[] compareCounter = {0};
            long startTime = System.nanoTime();    
            if (!found) {
                found = down(puzzle,words[i],solved,colorId,compareCounter);
            }
            if (!found) {
                found = up(puzzle,words[i],solved,colorId,compareCounter);
            }
            if (!found) {
                found = right(puzzle,words[i],solved,colorId,compareCounter);
            }
            if (!found) {
                found = left(puzzle,words[i],solved,colorId,compareCounter);
            }
            if (!found) {
                found = downright(puzzle,words[i],solved,colorId,compareCounter);
            }
            if (!found) {
                found = upleft(puzzle,words[i],solved,colorId,compareCounter);
            }
            if (!found) {
                found = upright(puzzle,words[i],solved,colorId,compareCounter);
            }
            if (!found) {
                found = downleft(puzzle,words[i],solved,colorId,compareCounter);
            }   
            solvingTime[i] = System.nanoTime() - startTime;
            comparison[i] = compareCounter[0];
        }
        writeSolved(puzzle, solved);
        writeStat(words, solvingTime, comparison);
 
        sc.close();
        return;
    }

    
    // DIMENSION
    public static int[] dimension (String path) throws Exception {
        // Keterangan: result[0] = row, result[1] = col, result[2] = wCount
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
                System.out.printf("%c ", puzzle[i][j]);
            }
            System.out.printf("\n");
        }
    }
    
    public static void writeWords (String[] words) {
        System.out.println("\nWord List:");
        for (int i = 0; i < words.length; i++) {
            System.out.println(words[i]);
        }
    }
    
    public static void writeSolved (char[][] puzzle, int[][] solved) {
        System.out.println("\nSolved:");
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[0].length; j++) {
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

    public static void writeStat (String[] words, long[] solvingTime, int[] comparison) {
        System.out.println("\nStatistics:");
        for (int i = 0; i < words.length; i++) {
            System.out.println(words[i] + "   -   Time taken: " + solvingTime[i] + " ns, Comparison count: " + comparison[i]);
        }
        long totalTime = Arrays.stream(solvingTime).sum();
        int totalComparison = Arrays.stream(comparison).sum();
        System.out.println("Total Solving Time = "+ totalTime + " ns");
        System.out.println("Total Comparison = "+ totalComparison);
        System.out.println("Average Solving Time = "+ totalTime/words.length + " ns");
        System.out.println("Average Comparison Count = "+ totalComparison/words.length);
    }


    // PROSEDUR STRING MATCHING
    public static boolean down (char[][] puzzle, String word, int[][] solved, int colorId, int[] count) {
        int row = puzzle.length;
        int col = puzzle[0].length;
        int wordLen = word.length();
        for (int h = 0; h < col; h++) {
            for (int i = 0; i <= row-wordLen; i++) {
                int j = 0;
                while ((j < wordLen) && (puzzle[i+j][h] == word.charAt(j))) {
                    j++;
                    count[0]++;
                }
                if (j == wordLen) {
                    for (int k = i; k < i+wordLen; k++) {
                        solved[k][h] = colorId;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean up (char[][] puzzle, String word, int[][] solved, int colorId, int[] count) {
        int row = puzzle.length;
        int col = puzzle[0].length;
        int wordLen = word.length();
        for (int h = 0; h < col; h++) {
            for (int i = row-1; i >= wordLen-1; i--) {
                int j = 0;
                while ((j < wordLen) && (puzzle[i-j][h] == word.charAt(j))) {
                    j++;
                    count[0]++;
                }
                if (j == wordLen) {
                    for (int k = i; k > i-wordLen; k--) {
                        solved[k][h] = colorId;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean right (char[][] puzzle, String word, int[][] solved, int colorId, int[] count) {
        int row = puzzle.length;
        int col = puzzle[0].length;
        int wordLen = word.length();
        for (int h = 0; h < row; h++) {
            for (int i = 0; i <= col-wordLen; i++) {
                int j = 0;
                while ((j < wordLen) && (puzzle[h][i+j] == word.charAt(j))) {
                    j++;
                    count[0]++;
                }
                if (j == wordLen) {
                    for (int k = i; k < i+wordLen; k++) {
                        solved[h][k] = colorId;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean left (char[][] puzzle, String word, int[][] solved, int colorId, int[] count) {
        int row = puzzle.length;
        int col = puzzle[0].length;
        int wordLen = word.length();
        
        for (int h = 0; h < row; h++) {
            for (int i = col-1; i >= wordLen-1; i--) {
                int j = 0;
                while ((j < wordLen) && (puzzle[h][i-j] == word.charAt(j))) {
                    j++;
                    count[0]++;
                }
                if (j == wordLen) {
                    for (int k = i; k > i-wordLen; k--) {
                        solved[h][k] = colorId;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean downright (char[][] puzzle, String word, int[][] solved, int colorId, int[] count) {
        int row = puzzle.length;
        int col = puzzle[0].length;
        int wordLen = word.length();
        
        for (int h = 0; h <= row-wordLen; h++) {
            for (int i = 0; i <= col-wordLen; i++) {
                int j = 0;
                while ((j < wordLen) && (puzzle[h+j][i+j] == word.charAt(j))) {
                    j++;
                    count[0]++;
                }
                if (j == wordLen) {
                    for (int k = i; k < i+wordLen; k++) {
                        solved[h+k-i][k] = colorId;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean upleft (char[][] puzzle, String word, int[][] solved, int colorId, int[] count) {
        int row = puzzle.length;
        int col = puzzle[0].length;
        int wordLen = word.length();
        
        for (int h = row-1; h >= wordLen-1; h--) {
            for (int i = col-1; i >= wordLen-1; i--) {
                int j = 0;
                while ((j < wordLen) && (puzzle[h-j][i-j] == word.charAt(j))) {
                    j++;
                    count[0]++;
                }
                if (j == wordLen) {
                    for (int k = i; k > i-wordLen; k--) {
                        solved[h-k+i][k] = colorId;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean upright (char[][] puzzle, String word, int[][] solved, int colorId, int[] count) {
        int row = puzzle.length;
        int col = puzzle[0].length;
        int wordLen = word.length();
        
        for (int h = row-1; h >= wordLen-1; h--) {
            for (int i = 0; i <= col-wordLen; i++) {
                int j = 0;
                while ((j < wordLen) && (puzzle[h-j][i+j] == word.charAt(j))) {
                    j++;
                    count[0]++;
                }
                if (j == wordLen) {
                    for (int k = i; k < i+wordLen; k++) {
                        solved[h-k+i][k] = colorId;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean downleft (char[][] puzzle, String word, int[][] solved, int colorId, int[] count) {
        int row = puzzle.length;
        int col = puzzle[0].length;
        int wordLen = word.length();
        
        for (int h = 0; h <= row-wordLen; h++) {
            for (int i = col-1; i >= wordLen-1; i--) {
                int j = 0;
                while ((j < wordLen) && (puzzle[h+j][i-j] == word.charAt(j))) {
                    j++;
                    count[0]++;
                }
                if (j == wordLen) {
                    for (int k = i; k < i+wordLen; k++) {
                        solved[h+k-i][k] = colorId;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    
}