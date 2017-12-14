

public class GameMap {

    private int width;
    private int length;
    private Cell[][] cells;

    GameMap(int w, int l){
        setWidth(w);
        setLength(l);

        this.cells = new Cell[getWidth()][getLength()];

        //Wumpus
        int xWumpus = generateRandom(getWidth());
        int yWumpus = generateRandom(getLength());
        Cell wumpus = new Cell(xWumpus,yWumpus,"wumpus");
        this.cells[xWumpus][yWumpus] = wumpus;

    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    private int generateRandom(int Max){
        return (int) Math.round(1 + (Math.random() * (Max - 1)));
    }
}
