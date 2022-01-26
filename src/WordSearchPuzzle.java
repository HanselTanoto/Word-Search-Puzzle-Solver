// Nama         : Hansel Valentino Tanoto
// NIM          : 13520046
// Deskripsi    : Program untuk menyelesaikan word search puzzle dengan algoritma brute force

import java.io.*;
import java.util.Scanner;
import java.util.Arrays;

public class WordSearchPuzzle {

    // MAIN PROGRAM 
    public static void main (String[] args) throws Exception{
        
        Scanner sc = new Scanner(System.in);
        System.out.printf("Enter file path: ");
        String path = sc.nextLine();
        
        // Dimensi "puzzle board" (row & column) dan jumlah kata yang dicari
        int[] dim = dimension(path);
        int row = dim[0];
        int col = dim[1];
        int wCount = dim[2];

        char[][] puzzle = new char[row][col];   // papan puzzle
        String[] words = new String[wCount];    // himpunan kata yang dicari
        int[][] solved = new int[row][col];     // hasil pemecahan puzzle
        long[] solvingTime = new long[wCount];  // waktu pemecahan puzzle
        int[] comparison = new int[wCount];     // jumlah perbandingan

        // Pembacaan file puzzle (.txt) dan menampilkannya ke layar
        readFile(path, puzzle, words);
        writePuzzle(puzzle);
        writeWords(words);

        // Pemecahan puzzle (string matching)
        for (int i = 0; i < wCount; i++) {
            int colorId = i%11 + 1;
            int[] compareCounter = {0};
            long startTime = System.nanoTime();
            letterTransversal:
            for (int j = 0; j < row; j++) {
                for (int k = 0; k < col; k++) {
                    if (down(puzzle,words[i],j,k,solved,colorId,compareCounter)) {
                        break letterTransversal;
                    }
                    else if (right(puzzle,words[i],j,k,solved,colorId,compareCounter)) {
                        break letterTransversal;
                    }
                    else if (up(puzzle,words[i],j,k,solved,colorId,compareCounter)) {
                        break letterTransversal;
                    }
                    else if (left(puzzle,words[i],j,k,solved,colorId,compareCounter)) {
                        break letterTransversal;
                    }
                    else if (downright(puzzle,words[i],j,k,solved,colorId,compareCounter)) {
                        break letterTransversal;
                    }
                    else if (downleft(puzzle,words[i],j,k,solved,colorId,compareCounter)) {
                        break letterTransversal;
                    }   
                    else if (upleft(puzzle,words[i],j,k,solved,colorId,compareCounter)) {
                        break letterTransversal;
                    }
                    else if (upright(puzzle,words[i],j,k,solved,colorId,compareCounter)) {
                        break letterTransversal;
                    }
                }
            }
            solvingTime[i] = System.nanoTime() - startTime;
            comparison[i] = compareCounter[0];
        }
        // Menuliskan hasilnya
        writeSolved(puzzle, solved);
        writeStat(words, solvingTime, comparison);
        System.out.println();

        sc.close();
        return;
    }

    
    // DIMENSION
    public static int[] dimension (String path) throws Exception {
        // Menghitung dimensi papan puzzle dan jumlah kata yang dicari
        // Keterangan: result[0] = baris, result[1] = kolom, result[2] = jumlah kata
        int[] result = new int[3]; 
        int separator = 0;  // baris yang memisahkan papan puzzle dan list kata pada file .txt
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
        // Membaca file puzzle (.txt) dan mengkonnversinya ke matriks atau array
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
        // Menuliskan papan puzzle ke layar
        System.out.println("\n" + "=".repeat(puzzle[0].length-13) + " WORD SEARCH PUZZLE (" + puzzle.length + "x" + puzzle[0].length +") " + "=".repeat(puzzle[0].length-12));
        for (int i = 0; i < puzzle.length; i++) {
            System.out.printf("| ");
            for (int j = 0; j < puzzle[0].length; j++) {
                System.out.printf("%c ", puzzle[i][j]);
            }
            System.out.printf("|\n");
        }
        System.out.println("=".repeat(puzzle[0].length*2+3));
    }
    
    public static void writeWords (String[] words) {
        // Menuliskan list kata yang dicari ke layar
        System.out.println("\nWORDS LIST (" + words.length + " words):");
        for (int i = 0; i < words.length; i++) {
            System.out.println("- " + words[i]);
        }
    }
    
    public static void writeSolved (char[][] puzzle, int[][] solved) {
        // Menuliskan papan puzzle yang sudah berhasil diselesaikan
        System.out.println("\n" + "=".repeat(puzzle[0].length-4) + " SOLUTION " + "=".repeat(puzzle[0].length-3));
        for (int i = 0; i < puzzle.length; i++) {
            System.out.printf("| ");
            for (int j = 0; j < puzzle[0].length; j++) {
                System.out.printf(ANSI_COLOR[solved[i][j]]);
                if (solved[i][j] != 0) {
                    System.out.printf(ITALIC);
                    System.out.printf(UNDERLINE);
                }
                System.out.printf("%c ", puzzle[i][j]);
                System.out.printf(ANSI_RESET);
            }
            System.out.printf("|\n");
        }
        System.out.println("=".repeat(puzzle[0].length*2+3));
    }

    public static void writeStat (String[] words, long[] solvingTime, int[] comparison) {
        // Menuliskan statistik berupa waktu eksekusi dan jumlah komparasi yang terjadi
        System.out.println("\n" + "=".repeat(31) + " STATISTICS " + "=".repeat(31));
        for (int i = 0; i < words.length; i++) {
            System.out.printf("- " + words[i] + " ".repeat(15-words[i].length()) + "||   ");
            String timeStat = "Time taken: " + solvingTime[i] + " ns";
            System.out.println(timeStat + " ".repeat(25-timeStat.length()) + "-    Comparison count: " + comparison[i]);
        }
        long totalTime = Arrays.stream(solvingTime).sum();
        int totalComparison = Arrays.stream(comparison).sum();
        System.out.println("=".repeat(74));
        System.out.println("Total Solving Time          = "+ totalTime + " ns");
        System.out.println("Total Comparison            = "+ totalComparison);
        System.out.println("Average Solving Time        = "+ totalTime/words.length + " ns");
        System.out.println("Average Comparison Count    = "+ totalComparison/words.length);
    }


    // PROSEDUR STRING MATCHING
    public static boolean down (char[][] puzzle, String word, int x, int y, int[][] solved, int colorId, int[] count) {
        // Mencari kata yang arahnya vertikal ke bawah
        int wordLen = word.length();
        if (puzzle.length-x >= wordLen) {
            int j = 0;
            count[0]++;
            while ((j < wordLen) && (puzzle[x+j][y] == word.charAt(j))) {
                j++;
            }
            count[0] += j;
            if (j == wordLen) {
                for (int k = x; k < x+wordLen; k++) {
                    solved[k][y] = colorId;
                }
                return true;
            }
        }
        return false;
    }

    public static boolean up (char[][] puzzle, String word, int x, int y, int[][] solved, int colorId, int[] count) {
        // Mencari kata yang arahnya vertikal ke atas
        int wordLen = word.length();
        if (x+1 >= wordLen) {
            int j = 0;
            count[0]++;
            while ((j < wordLen) && (puzzle[x-j][y] == word.charAt(j))) {
                j++;
            }
            count[0] += j;
            if (j == wordLen) {
                for (int k = x; k > x-wordLen; k--) {
                    solved[k][y] = colorId;
                }
                return true;
            }
        }
        return false;
    }

    public static boolean right (char[][] puzzle, String word, int x, int y, int[][] solved, int colorId, int[] count) {
        // Mencari kata yang arahnya horizontal ke kanan
        int wordLen = word.length();
        if (puzzle[0].length-y >= wordLen) {
            int j = 0;
            count[0]++;
            while ((j < wordLen) && (puzzle[x][y+j] == word.charAt(j))) {
                j++;
            }
            count[0] += j;
            if (j == wordLen) {
                for (int k = y; k < y+wordLen; k++) {
                    solved[x][k] = colorId;
                }
                return true;
            }
        }
        return false;
    }

    public static boolean left (char[][] puzzle, String word, int x, int y, int[][] solved, int colorId, int[] count) {
        // Mencari kata yang arahnya horizontal ke kiri
        int wordLen = word.length();
        if (y+1 >= wordLen) {
            int j = 0;
            count[0]++;
            while ((j < wordLen) && (puzzle[x][y-j] == word.charAt(j))) {
                j++;
            }
            count[0] += j;
            if (j == wordLen) {
                for (int k = y; k > y-wordLen; k--) {
                    solved[x][k] = colorId;
                }
                return true;
            }
        }
        return false;
    }

    public static boolean downright (char[][] puzzle, String word, int x, int y, int[][] solved, int colorId, int[] count) {
        // Mencari kata yang arahnya diagonal ke bawah kanan
        int wordLen = word.length();        
        if (Math.min(puzzle.length-x,puzzle[0].length-y) >= wordLen) {
            int j = 0;
            count[0]++;
            while ((j < wordLen) && (puzzle[x+j][y+j] == word.charAt(j))) {
                j++;
            }
            count[0] += j;
            if (j == wordLen) {
                for (int k = y; k < y+wordLen; k++) {
                    solved[x+k-y][k] = colorId;
                }
                return true;
            }
        }
        return false;
    }

    public static boolean upleft (char[][] puzzle, String word, int x, int y, int[][] solved, int colorId, int[] count) {
        // Mencari kata yang arahnya diagonal ke kiri atas
        int wordLen = word.length();        
        if (Math.min(x+1,y+1) >= wordLen) {
            int j = 0;
            count[0]++;
            while ((j < wordLen) && (puzzle[x-j][y-j] == word.charAt(j))) {
                j++;
            }
            count[0] += j;
            if (j == wordLen) {
                for (int k = y; k > y-wordLen; k--) {
                    solved[x+k-y][k] = colorId;
                }
                return true;
            }
        }
        return false;
    }

    public static boolean upright (char[][] puzzle, String word, int x, int y, int[][] solved, int colorId, int[] count) {
        // Mencari kata yang arahnya diagonal ke kanan atas
        int wordLen = word.length();        
        if (Math.min(x+1,puzzle[0].length-y) >= wordLen) {
            int j = 0;
            count[0]++;
            while ((j < wordLen) && (puzzle[x-j][y+j] == word.charAt(j))) {
                j++;
            }
            count[0] += j;
            if (j == wordLen) {
                for (int k = y; k < y+wordLen; k++) {
                    solved[x-k+y][k] = colorId;
                }
                return true;
            }
        }
        return false;
    }

    public static boolean downleft (char[][] puzzle, String word, int x, int y, int[][] solved, int colorId, int[] count) {
        // Mencari kata yang arahnya diagonal ke kiri bawah
        int wordLen = word.length();        
        if (Math.min(puzzle.length-x,y+1) >= wordLen) {
            int j = 0;
            count[0]++;
            while ((j < wordLen) && (puzzle[x+j][y-j] == word.charAt(j))) {
                j++;
            }
            count[0] += j;
            if (j == wordLen) {
                for (int k = y; k > y-wordLen; k--) {
                    solved[x-k+y][k] = colorId;
                }
                return true;
            }
        }
        return false;
    }

    
}
