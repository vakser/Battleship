package battleship;

import java.util.Arrays;
import java.util.Scanner;

public class Game {
    private static final GameField player1Field = new GameField();
    private static final GameField player2Field = new GameField();

    public void playGame() {
        buildStartArrays();
        placeShipsOnField(player1Field);
        placeShipsOnField(player2Field);
        while (player1Field.shipsNumber != 0 || player2Field.shipsNumber != 0) {
            makeMove(player1Field);
            makeMove(player2Field);
        }
    }

    public void makeMove(GameField field) {
        field.printGameField(field.gameShotsArray);
        System.out.println("---------------------");
        field.printGameField(field.gameStateArray);
        if (field.equals(player1Field)) {
            System.out.println("\nPlayer 1, it's your turn:\n");
        } else {
            System.out.println("\nPlayer 2, it's your turn:\n");
        }
        field.getShotCoordinate();
        if (field.equals(player1Field)) {
            checkIfHit(player1Field, player2Field);
        } else {
            checkIfHit(player2Field, player1Field);
        }
    }

    public void placeShipsOnField(GameField field) {
        if (field.equals(player1Field)) {
            System.out.println("Player 1, place your ships on the game field\n");
        } else {
            System.out.println("Player 2, place your ships on the game field\n");
        }
        field.printGameField(field.gameStateArray);
        for (Ship ship : Ship.values()) {
            field.placeShip(ship);
        }
        field.copyGameStateArrayBeforeFirstShot();
        System.out.println();
        changePlayer();
    }

    public void buildStartArrays() {
        player1Field.buildStartArray(player1Field.gameStateArray);
        player1Field.buildStartArray(player1Field.gameShotsArray);
        player2Field.buildStartArray(player2Field.gameStateArray);
        player2Field.buildStartArray(player2Field.gameShotsArray);
    }

    public void checkIfHit(GameField shooting, GameField underFire) {
        updateGameArray(shooting.gameShotsArray);
        updateGameArray(shooting.gameStateArray);
        updateGameArray(underFire.gameShotsArray);
        updateGameArray(underFire.gameStateArray);
        if ("X".equals(underFire.gameStateArray[shooting.firstPartOfShotCoordinate - 65][shooting.secondPartOfShotCoordinate - 1])) {
            for (int i = 0; i < 5; i++) {
                if (underFire.ships[i].contains(shooting.shotCoordinate)) {
                    underFire.ships[i] = underFire.ships[i].replace(shooting.shotCoordinate, "");
                    if (underFire.ships[i].length() == 0) {
                        underFire.shipsNumber--;
                        if (underFire.shipsNumber == 0) {
                            System.out.println("You sank the last ship. You won. Congratulations!");
                            System.exit(0);
                        }
                        System.out.println("You sank a ship!");
                    } else {
                        System.out.println("You hit a ship!");
                    }
                }
            }
        } else {
            System.out.println("You missed!");
        }
        changePlayer();
    }

    public void updateGameArray(String[][] array) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (Arrays.deepEquals(array, player1Field.gameStateArray) && i == player2Field.firstPartOfShotCoordinate - 65 && j == player2Field.secondPartOfShotCoordinate - 1) {
                    if ("O".equals(player1Field.beforeShootingArray[i][j])) {
                        player1Field.gameStateArray[i][j] = "X";
                    } else {
                        player1Field.gameStateArray[i][j] = "M";
                    }
                }
                if (Arrays.deepEquals(array, player2Field.gameStateArray) && i == player1Field.firstPartOfShotCoordinate - 65 && j == player1Field.secondPartOfShotCoordinate - 1) {
                    if ("O".equals(player2Field.beforeShootingArray[i][j])) {
                        player2Field.gameStateArray[i][j] = "X";
                    } else {
                        player2Field.gameStateArray[i][j] = "M";
                    }
                }
                if (Arrays.deepEquals(array, player1Field.gameShotsArray) && i == player1Field.firstPartOfShotCoordinate - 65 && j == player1Field.secondPartOfShotCoordinate - 1) {
                    if ("O".equals(player2Field.beforeShootingArray[i][j])) {
                        player1Field.gameShotsArray[i][j] = "X";
                    } else {
                        player1Field.gameShotsArray[i][j] = "M";
                    }
                }
                if (Arrays.deepEquals(array, player2Field.gameShotsArray) && i == player2Field.firstPartOfShotCoordinate - 65 && j == player2Field.secondPartOfShotCoordinate - 1) {
                    if ("O".equals(player1Field.beforeShootingArray[i][j])) {
                        player2Field.gameShotsArray[i][j] = "X";
                    } else {
                        player2Field.gameShotsArray[i][j] = "M";
                    }
                }
            }
        }
    }

    public static void changePlayer() {
        System.out.println("Press Enter and pass the move to another player");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }
}
