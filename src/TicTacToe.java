import java.util.Scanner;
public class TicTacToe {
    private static final int ROWS = 3;
    private static final int COLS = 3;
    private static String board[][] = new String[ROWS][COLS];

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int rowMove;
        int colMove;
        Boolean playAgain = true;
        do {
            String player = "";
            Boolean win = false;
            Boolean tie = false;
            int moveCounter = 1;
            clearBoard();
            do {
                //Player X's turn, check for valid moves
                player = "X";
                do {
                    rowMove = SafeInput.getRangedInt(in, "Player X, enter the row of your move", 1, 3);
                    rowMove--;
                    colMove = SafeInput.getRangedInt(in, "Player X, enter the col of your move", 1, 3);
                    colMove--;
                } while (!isValidMove(rowMove, colMove));
                //Input move for X, then check win and tie condition
                board[rowMove][colMove] = "X";
                //Check for tie
                if (moveCounter >= 7) {
                    tie = isTie();
                    if (tie) {
                        display();
                        System.out.println("Tie");
                        break;
                    }
                }
                moveCounter++;
                display();
                if (moveCounter >= 5) {
                    win = isWin(player);
                    //Player X Wins
                    if (win) {
                        System.out.println("X Wins");
                        break;
                    }
                }
                //Player O's turn, check for valid moves
                player = "O";
                do {
                    rowMove = SafeInput.getRangedInt(in, "Player O, enter the row of your move", 1, 3);
                    rowMove--;
                    colMove = SafeInput.getRangedInt(in, "Player O, enter the col of your move", 1, 3);
                    colMove--;
                } while (!isValidMove(rowMove, colMove));
                board[rowMove][colMove] = "O";

                //Check for tie
                if (moveCounter >= 7) {
                    tie = isTie();
                    if (tie) {
                        display();
                        System.out.println("Tie");
                        break;
                    }
                }
                moveCounter++;
                display();
                if (moveCounter >= 6) {
                    win = isWin(player);
                    //Player O Wins
                    if (win) {
                        System.out.println("O Wins");
                        break;
                    }
                }
                //Continue until moveCounter is 9
            } while (moveCounter <= 9);
            playAgain = SafeInput.getYNConfirm(in, "Would you like to play again (Y or N)?");
        } while (playAgain);

    }

    //Clears out board
    private static void clearBoard() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                board[row][col] = "";
            }
        }
    }

    //Displays current board
    private static void display() {
        for (int row = 0; row < ROWS; row++) {
            System.out.printf("%-2s| %-2s| %-2s%n", board[row][0], board[row][1], board[row][2]);
        }
    }

    //Checks for valid move
    private static boolean isValidMove(int row, int col) {
        return board[row][col].equals("");
    }

    //Checks if Player X or O has won
    private static boolean isWin(String player) {
        if (isColWin(player) || isRowWin(player) || isDiagonalWin(player)) {
            return true;
        }
        return false;
    }

    //Check for column win by X or O
    private static boolean isColWin(String player) {
        for (int col = 0; col < COLS; col++) {
            if (board[0][col].equals(player) && board[1][col].equals(player) && board[2][col].equals(player)) {
                return true;
            }
        }
        return false;
    }

    //Check for row win by X or O
    private static boolean isRowWin(String player) {
        for (int row = 0; row < ROWS; row++) {
            if (board[row][0].equals(player) && board[row][1].equals(player) && board[row][2].equals(player)) {
                return true;
            }
        }
        return false;
    }

    //Check for diagonal win by X or O
    private static boolean isDiagonalWin(String player) {
        //Case 1, Upper Left to Bottom Right
        if (board[0][0].equals(player) && board[1][1].equals(player) && board[2][2].equals(player)) {
            return true;
        }
        //Case 2, Upper Right to Bottom Left
        if (board[0][2].equals(player) && board[1][1].equals(player) && board[2][0].equals(player)) {
            return true;
        }
        return false;
    }

    //Check if tie has or will happen
    private static boolean isTie() {
        // Check if there’s already a winner
        if (isWin("X") || isWin("O")) {
            return false;
        }

        // Check rows
        for (int row = 0; row < 3; row++) {
            if (!(board[row][0].equals("X") && board[row][1].equals("O")) &&
                    !(board[row][0].equals("O") && board[row][1].equals("X")) &&
                    !(board[row][1].equals("X") && board[row][2].equals("O")) &&
                    !(board[row][1].equals("O") && board[row][2].equals("X")) &&
                    !(board[row][0].equals("X") && board[row][2].equals("O")) &&
                    !(board[row][0].equals("O") && board[row][2].equals("X"))) {
                return false; // A win is still possible in a row
            }
        }

        // Check columns
        for (int col = 0; col < 3; col++) {
            if (!(board[0][col].equals("X") && board[1][col].equals("O")) &&
                    !(board[0][col].equals("O") && board[1][col].equals("X")) &&
                    !(board[1][col].equals("X") && board[2][col].equals("O")) &&
                    !(board[1][col].equals("O") && board[2][col].equals("X")) &&
                    !(board[0][col].equals("X") && board[2][col].equals("O")) &&
                    !(board[0][col].equals("O") && board[2][col].equals("X"))) {
                return false; // A win is still possible in a column
            }
        }

        // Check diagonals
        if (!(board[0][0].equals("X") && board[1][1].equals("O")) &&
                !(board[0][0].equals("O") && board[1][1].equals("X")) &&
                !(board[1][1].equals("X") && board[2][2].equals("O")) &&
                !(board[1][1].equals("O") && board[2][2].equals("X")) &&
                !(board[0][0].equals("X") && board[2][2].equals("O")) &&
                !(board[0][0].equals("O") && board[2][2].equals("X"))) {
            return false; // A win is still possible on the upper left to lower right
        }

        if (!(board[0][2].equals("X") && board[1][1].equals("O")) &&
                !(board[0][2].equals("O") && board[1][1].equals("X")) &&
                !(board[1][1].equals("X") && board[2][0].equals("O")) &&
                !(board[1][1].equals("O") && board[2][0].equals("X")) &&
                !(board[0][2].equals("X") && board[2][0].equals("O")) &&
                !(board[0][2].equals("O") && board[2][0].equals("X"))) {
            return false; // A win is still possible on the upper right to lower left
        }
        // Tie is guaranteed
        return true;
    }
}
