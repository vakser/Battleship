package battleship;

import java.util.Scanner;

public class GameField {
    final Scanner scanner = new Scanner(System.in);
    final String[][] gameStateArray = new String[10][10];
    final String[][] gameShotsArray = new String[10][10];
    String[][] beforeShootingArray = new String[10][10];
    String firstCoordinate;
    String secondCoordinate;
    int firstCoordinateDigitalPart;
    int secondCoordinateDigitalPart;
    boolean correctLocation;
    boolean correctPlacement;
    int length;
    String shotCoordinate;
    char firstPartOfShotCoordinate;
    int secondPartOfShotCoordinate;
    StringBuilder shipString;
    final String[] ships = new String[5];
    int shipsNumber = 5;

    public void printGameField(String[][] array) {
        for (int i = 0; i < 11; i++) {
            if (i == 0) {
                System.out.print("  ");
            } else {
                System.out.print(i + " ");
            }
        }
        System.out.println();
        char c = 65;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 11; j++) {
                if (j == 0) {
                    System.out.print(c + " ");
                    c++;
                } else {
                    System.out.print(array[i][j - 1] + " ");
                }
            }
            System.out.println();
        }
    }

    public void buildStartArray(String[][] array) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                array[i][j] = "~";
            }
        }
    }

    public void placeShip(Ship ship) {
        length = ship.length;
        System.out.printf("\nEnter the coordinates of the %s (%d cells):\n\n", ship.name, ship.length);
        getCoordinates();
        System.out.println();
        calculateLengthByCoordinates();
        setLocationState();
        while (length != ship.length || !correctLocation || !correctPlacement) {
            if (!correctLocation) {
                System.out.println("Error! Wrong ship location! Try again:\n");
                getCoordinates();
                setLocationState();
            } else if (length != ship.length) {
                System.out.printf("Error! Wrong length of the %s! Try again:\n\n", ship.name);
                getCoordinates();
                calculateLengthByCoordinates();
            } else {
                checkIfPlacementCorrect(length);
            }
        }
        buildShipCellsString();
        ships[ship.ordinal()] = shipString.toString();
        correctPlacement = false;
        buildShipPositionArray();
        printGameField(gameStateArray);
    }

    public void buildShipPositionArray() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if ("~".equals(gameStateArray[i][j]) && i >= (firstCoordinate.charAt(0) - 65) && j >= (firstCoordinateDigitalPart - 1) && i <= (secondCoordinate.charAt(0) - 65) && j <= (secondCoordinateDigitalPart - 1)) {
                    gameStateArray[i][j] = "O";
                }
            }
        }
    }

    public void copyGameStateArrayBeforeFirstShot() {
        for (int i = 0; i < 10; i++) {
            System.arraycopy(gameStateArray[i], 0, beforeShootingArray[i], 0, 10);
        }
    }

    public void getCoordinates() {
        String[] coordinates = scanner.nextLine().split(" ");
        firstCoordinate = coordinates[0];
        firstCoordinateDigitalPart = Integer.parseInt(firstCoordinate.substring(1));
        secondCoordinate = coordinates[1];
        secondCoordinateDigitalPart = Integer.parseInt(secondCoordinate.substring(1));
    }

    public void swapCoordinatesIfFirstGreater() {
        if (firstCoordinate.charAt(0) > secondCoordinate.charAt(0) || firstCoordinateDigitalPart > secondCoordinateDigitalPart) {
            String temp = firstCoordinate;
            firstCoordinate = secondCoordinate;
            secondCoordinate = temp;
            firstCoordinateDigitalPart = Integer.parseInt(firstCoordinate.substring(1));
            secondCoordinateDigitalPart = Integer.parseInt(secondCoordinate.substring(1));
        }
    }

    public void buildShipCellsString() {
        int length;
        if (firstCoordinate.charAt(0) == secondCoordinate.charAt(0)) {
            length = secondCoordinateDigitalPart - firstCoordinateDigitalPart + 1;
        } else {
            length = secondCoordinate.charAt(0) - firstCoordinate.charAt(0) + 1;
        }
        shipString = new StringBuilder();
        char shipCellCoordinateLetterPart = firstCoordinate.charAt(0);
        int shipCellCoordinateDigitalPart = firstCoordinateDigitalPart;
        for (int i = 0; i < length; i++) {
            if (firstCoordinate.charAt(0) == secondCoordinate.charAt(0)) {
                shipString.append(shipCellCoordinateLetterPart).append(shipCellCoordinateDigitalPart);
                shipCellCoordinateDigitalPart++;
            } else {
                shipString.append(shipCellCoordinateLetterPart).append(shipCellCoordinateDigitalPart);
                shipCellCoordinateLetterPart++;
            }
        }
    }

    public void checkIfPlacementCorrect(int length) {
        swapCoordinatesIfFirstGreater();
        String[] shipArray = new String[length];
        char arrayCoordinateFirstPart = firstCoordinate.charAt(0);
        int arrayCoordinateSecondPart = firstCoordinateDigitalPart;
        for (int i = 0; i < length; i++) {
            if (firstCoordinate.charAt(0) == secondCoordinate.charAt(0)) {
                shipArray[i] = arrayCoordinateFirstPart + String.valueOf(arrayCoordinateSecondPart);
                arrayCoordinateSecondPart++;
            } else {
                shipArray[i] = arrayCoordinateFirstPart + String.valueOf(firstCoordinateDigitalPart);
                arrayCoordinateFirstPart++;
            }
        }
        for (String s : shipArray) {
            char letterPart = s.charAt(0);
            int digitalPart = Integer.parseInt(s.substring(1));
            if (letterPart == 'A') {
                if (digitalPart == 1) {
                    if ("O".equals(gameStateArray[0][1]) || "O".equals(gameStateArray[1][0])) {
                        System.out.println("Error! You placed it too close to another one. Try again:\n");
                        getCoordinates();
                        checkIfPlacementCorrect(length);
                    }
                } else if (digitalPart == 10) {
                    if ("O".equals(gameStateArray[9][1]) || "O".equals(gameStateArray[8][0])) {
                        System.out.println("Error! You placed it too close to another one. Try again:\n");
                        getCoordinates();
                        checkIfPlacementCorrect(length);
                    }
                } else {
                    if ("O".equals(gameStateArray[8][digitalPart - 1])) {
                        System.out.println("Error! You placed it too close to another one. Try again:\n");
                        getCoordinates();
                        checkIfPlacementCorrect(length);
                    }
                }
            } else if (letterPart == 'J') {
                if (digitalPart == 1) {
                    if ("O".equals(gameStateArray[9][1]) || "O".equals(gameStateArray[8][0])) {
                        System.out.println("Error! You placed it too close to another one. Try again:\n");
                        getCoordinates();
                        checkIfPlacementCorrect(length);
                    }
                } else if (digitalPart == 10) {
                    if ("O".equals(gameStateArray[9][8]) || "O".equals(gameStateArray[8][9])) {
                        System.out.println("Error! You placed it too close to another one. Try again:\n");
                        getCoordinates();
                        checkIfPlacementCorrect(length);
                    }
                } else {
                    if ("O".equals(gameStateArray[8][digitalPart - 1]) || "O".equals(gameStateArray[9][digitalPart - 2]) || "O".equals(gameStateArray[9][digitalPart])) {
                        System.out.println("Error! You placed it too close to another one. Try again:\n");
                        getCoordinates();
                        checkIfPlacementCorrect(length);
                    }
                }
            } else {
                if (digitalPart == 1) {
                    if ("O".equals(gameStateArray[letterPart - 66][0]) || "O".equals(gameStateArray[letterPart - 64][0]) || "O".equals(gameStateArray[letterPart - 65][1])) {
                        System.out.println("Error! You placed it too close to another one. Try again:\n");
                        getCoordinates() ;
                        checkIfPlacementCorrect(length);
                    }
                } else if (digitalPart == 10) {
                    if ("O".equals(gameStateArray[letterPart - 66][9]) || "O".equals(gameStateArray[letterPart - 64][9]) || "O".equals(gameStateArray[letterPart - 65][8])) {
                        System.out.println("Error! You placed it too close to another one. Try again:\n");
                        getCoordinates();
                        checkIfPlacementCorrect(length);
                    }
                } else {
                    if ("O".equals(gameStateArray[letterPart - 66][digitalPart - 1]) || "O".equals(gameStateArray[letterPart - 64][digitalPart - 1]) || "O".equals(gameStateArray[letterPart - 65][digitalPart - 2]) || "O".equals(gameStateArray[letterPart - 65][digitalPart])) {
                        System.out.println("Error! You placed it too close to another one. Try again:\n");
                        getCoordinates();
                        checkIfPlacementCorrect(length);
                    }
                }
            }
        }
        correctPlacement = true;
    }

    public void calculateLengthByCoordinates() {
        if (firstCoordinate.charAt(0) == secondCoordinate.charAt(0)) {
            length = Math.abs(firstCoordinateDigitalPart - secondCoordinateDigitalPart) + 1;
        } else {
            length = Math.abs(firstCoordinate.charAt(0) - secondCoordinate.charAt(0)) + 1;
        }
    }

    public void setLocationState() {
        correctLocation  = (firstCoordinate.charAt(0) - secondCoordinate.charAt(0) == 0 && firstCoordinateDigitalPart - secondCoordinateDigitalPart != 0) || (firstCoordinate.charAt(0) - secondCoordinate.charAt(0) != 0 && firstCoordinateDigitalPart - secondCoordinateDigitalPart == 0);
    }

    public void getShotCoordinate() {
        shotCoordinate = scanner.next();
        System.out.println();
        firstPartOfShotCoordinate = shotCoordinate.charAt(0);
        secondPartOfShotCoordinate = Integer.parseInt(shotCoordinate.substring(1));
        if (firstPartOfShotCoordinate > 'J' || secondPartOfShotCoordinate > 10) {
            while (firstPartOfShotCoordinate > 'J' || secondPartOfShotCoordinate > 10) {
                System.out.println("Error! You entered the wrong coordinates! Try again:\n");
                shotCoordinate = scanner.next();
                firstPartOfShotCoordinate = shotCoordinate.charAt(0);
                secondPartOfShotCoordinate = Integer.parseInt(shotCoordinate.substring(1));
            }
        }
    }
}
