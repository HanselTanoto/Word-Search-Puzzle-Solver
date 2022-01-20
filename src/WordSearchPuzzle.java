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
        readFile(path, puzzle, words);
        writePuzzle(puzzle);
        writeWords(words);

        /*
        System.out.println(row);
        System.out.println(col);
        System.out.println(wCount);
        */
        
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

}