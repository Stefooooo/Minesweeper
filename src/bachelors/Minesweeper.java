package bachelors;

import java.util.Scanner;
import java.util.Random;

public class Minesweeper {



    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Width: ");
        int width = scanner.nextInt();
        System.out.print("Height: ");
        int height = scanner.nextInt();
        System.out.print("Number of mines: ");
        int numberOfMines = scanner.nextInt();

        char[][] display = createTheDisplay(width, height);
        boolean[][] minefield = createMinefield(width, height, numberOfMines);
        int[][] numbers = calculateNumbersMatrix(width, height, minefield);

        displayGrid(display);
        int moves = 0;
        while (true){

            if (moves == (height*width-numberOfMines)){
                System.out.println("You won!");
                break;
            }

            System.out.println("Select a position: ");
            System.out.print("Row: ");
            int r = scanner.nextInt();
            System.out.print("Column: ");
            int c = scanner.nextInt();

            if (r >=height || c >= width){
                clearConsole();
                displayGrid(display);
                continue;
            }


            if (display[r][c] == 'X') {
                redoTable(r, c, display, numbers);
                moves++;

                if (minefield[r][c]) {
                    System.out.println("You lost!");
                    break;
                }
            }
        }



    }

    private static void redoTable(int row, int col, char[][] display, int[][] numbers) {
        clearConsole();

        display[row][col] = (char) ('0' + numbers[row][col]);
        displayGrid(display);
    }

    private static void clearConsole(){
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    private static int[][] calculateNumbersMatrix(int width, int height, boolean[][] minefield) {
        int[][] numbers = new int[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (minefield[row][col]){
                    numbers[row][col] = -1;
                    continue;
                }

                numbers[row][col] = getValue(row, col, minefield, height, width);
            }
        }

//        for (int[] row : numbers) {
//            for (int cell : row) {
//                System.out.print(cell + " ");
//            }
//            System.out.println();
//        }

        return numbers;
    }

    private static int getValue(int row, int col, boolean[][] minefield, int height, int width) {
        int counter = 0;
        if (row == 0){
            if (col==0){
                if (minefield[row][col+1]){
                    counter++;
                }
                if (minefield[row+1][col]){
                    counter++;
                }
                if (minefield[row+1][col+1]){
                    counter++;
                }
            } else if (col == width-1) {
                if (minefield[row][col-1]){
                    counter++;
                }
                if (minefield[row+1][col]){
                    counter++;
                }
                if (minefield[row+1][col-1]){
                    counter++;
                }
            } else {
                counter = getTopRowNumber(counter, row, col, minefield);
            }
        } else if (row == height-1){
            if (col==0){
                if (minefield[row][col+1]){
                    counter++;
                }
                if (minefield[row-1][col]){
                    counter++;
                }
                if (minefield[row-1][col+1]){
                    counter++;
                }
            } else if (col == width-1) {
                if (minefield[row][col-1]){
                    counter++;
                }
                if (minefield[row-1][col]){
                    counter++;
                }
                if (minefield[row-1][col-1]){
                    counter++;
                }
            } else {
                counter = getBottomRowNumber(counter, row, col, minefield);
            }
        } else if (col == 0) {
            counter = getLeftWallNumber(counter, row, col, minefield);
        } else if (col == width-1) {
            counter = getRightWallNumber(counter, row, col, minefield);
        } else {
            counter = getCenterNumber(counter, row, col, minefield);
        }

        return counter;
    }

    private static int getCenterNumber(int counter, int row, int col, boolean[][] minefield) {

        if (minefield[row][col+1]){
            counter++;
        }
        if (minefield[row][col-1]){
            counter++;
        }
        if (minefield[row-1][col+1]){
            counter++;
        }
        if (minefield[row-1][col]){
            counter++;
        }
        if (minefield[row-1][col-1]){
            counter++;
        }
        if (minefield[row+1][col]){
            counter++;
        }
        if (minefield[row+1][col+1]){
            counter++;
        }
        if (minefield[row+1][col-1]){
            counter++;
        }

        return counter;
    }

    private static int getLeftWallNumber(int counter, int row, int col, boolean[][] minefield) {

        if (minefield[row][col+1]){
            counter++;
        }
        if (minefield[row-1][col]){
            counter++;
        }
        if (minefield[row-1][col+1]){
            counter++;
        }
        if (minefield[row+1][col]){
            counter++;
        }
        if (minefield[row+1][col+1]){
            counter++;
        }

        return counter;
    }

    private static int getRightWallNumber(int counter, int row, int col, boolean[][] minefield) {

        if (minefield[row][col-1]){
            counter++;
        }
        if (minefield[row-1][col]){
            counter++;
        }
        if (minefield[row-1][col-1]){
            counter++;
        }
        if (minefield[row+1][col]){
            counter++;
        }
        if (minefield[row+1][col-1]){
            counter++;
        }

        return counter;
    }

    private static int getBottomRowNumber(int counter, int row, int col, boolean[][] minefield) {

        if (minefield[row][col-1]){
            counter++;
        }
        if (minefield[row][col+1]){
            counter++;
        }
        if (minefield[row-1][col-1]){
            counter++;
        }
        if (minefield[row-1][col]){
            counter++;
        }
        if (minefield[row-1][col+1]){
            counter++;
        }

        return counter;
    }

    private static int getTopRowNumber(int counter, int row, int col, boolean[][] minefield) {

        if (minefield[row][col-1]){
            counter++;
        }
        if (minefield[row][col+1]){
            counter++;
        }
        if (minefield[row+1][col-1]){
            counter++;
        }
        if (minefield[row+1][col]){
            counter++;
        }
        if (minefield[row+1][col+1]){
            counter++;
        }

        return counter;
    }

    private static boolean[][] createMinefield(int width, int height, int numberOfMines) {
        boolean[][] minefield = new boolean[height][width];
        Random random = new Random();

        for (int i = 0; i < numberOfMines; i++) {
            int row = random.nextInt(height);
            int col = random.nextInt(width);

            while (minefield[row][col]) {
                row = random.nextInt(height);
                col = random.nextInt(width);
            }

            minefield[row][col] = true;
        }

//        for (boolean[] row : minefield) {
//            for (boolean cell : row) {
//                System.out.print(cell + " ");
//            }
//            System.out.println();
//        }

        return minefield;
    }

    public static void displayGrid(char[][] matrix){
        for (char[] row : matrix) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    public static char[][] createTheDisplay(int width, int height){
        char[][] display = new char[height][width];

        for (int row = 0; row < height; row++) {

            for (int col = 0; col < width; col++) {
                display[row][col] = 'X';
            }

        }



        return display;
    }
}
