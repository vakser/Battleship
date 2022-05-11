package battleship;

public enum Ship {

    AIRCRAFT_CARRIER("Aircraft Carrier", 5), BATTLESHIP("Battleship", 4),
    SUBMARINE("Submarine", 3), CRUISER("Cruiser", 3), DESTROYER("Destroyer", 2);

    final String name;
    final int length;

    Ship(String name, int length) {
        this.name = name;
        this.length = length;
    }
}
