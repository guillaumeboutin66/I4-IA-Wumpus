package main;

import main.Cell;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class GameMap {

    private int width;
    private int length;
    private Cell[][] cells;
    private ArrayList<Point> lockedPoints = new ArrayList<Point>();

    public GameMap(int w, int l){
        width = w;
        length = l;

        cells = new Cell[width][length];

        //Agent
        Point agentPos = new Point(0,length-1);
        cells[0][length-1] = new Cell(agentPos);
        cells[0][length-1].addEvent(Cell.Event.agent);
        lockedPoints.add(agentPos);

        // Wumpus
        Point posWumpus = generatePoint(width, length, lockedPoints);
        cells[posWumpus.x][posWumpus.y] = new Cell(posWumpus);
        cells[posWumpus.x][posWumpus.y].addEvent(Cell.Event.wumpus);
        lockedPoints.add(posWumpus);

        // Gold
        Point posGold = generatePoint(width, length, lockedPoints);
        cells[posGold.x][posGold.y] = new Cell(posGold);
        cells[posGold.x][posGold.y].addEvent(Cell.Event.gold);
        lockedPoints.add(posGold);

        // Pit
        // RANDOM INT BETWEEN 1 & 20% of the number of cells
        int maxNumberOfPits = (int) Math.round(w*l*0.20);
        int randomNumberOfPits = generateRandom(maxNumberOfPits);
        for(int i = 0; i < randomNumberOfPits; i++){
            Point posPit = generatePoint(width, length, lockedPoints);
            cells[posPit.x][posPit.y] = new Cell(posPit);
            cells[posPit.x][posPit.y].addEvent(Cell.Event.pit);
            lockedPoints.add(posPit);
        }

        // Fill the rest of the Map of normal Cells
        for(int i = 0; i < width; i++){
            for(int j = 0; j < length; j++) {
                if(cells[i][j] == null){
                    cells[i][j] = new Cell(new Point(i,j));
                }
            }
        }

        // Fill the Map of side event Cells
        for(int i = 0; i < width; i++){
            for(int j = 0; j < length; j++) {
                if(cells[i][j].getEvents().contains(Cell.Event.wumpus)){
                    addAdjacentEvent(cells[i][j], Cell.Event.smell);
                }
                if(cells[i][j].getEvents().contains(Cell.Event.pit)){
                    addAdjacentEvent(cells[i][j], Cell.Event.wind);
                }
            }
        }
    }

    private Point generatePoint(int maxWidth, int maxLength, ArrayList<Point> avoidPoints){
        int x = generateRandom(maxWidth);
        int y = generateRandom(maxLength);

        boolean alreadyExist = false;

        for (Point avoidPoint: avoidPoints) {
            if(avoidPoint.x == x && avoidPoint.y == y){
                alreadyExist = true;
            }
        }

        if(alreadyExist){
            return generatePoint(maxWidth,maxLength, avoidPoints);
        } else {
            return new Point(x,y);
        }

    }

    /**
     * Add the event to adjacents cells
     * @param cell
     * @param event
     */
    private void addAdjacentEvent(Cell cell, Cell.Event event){
        if(cell.position.x > 0 && checkIfSideEventExistOnCell(cells[cell.position.x-1][cell.position.y], event)){
            cells[cell.position.x-1][cell.position.y].addEvent(event);
        }
        if(cell.position.y > 0 && checkIfSideEventExistOnCell(cells[cell.position.x][cell.position.y-1], event)){
            cells[cell.position.x][cell.position.y-1].addEvent(event);
        }
        if(cell.position.x < width-1 && checkIfSideEventExistOnCell(cells[cell.position.x+1][cell.position.y], event)){
            cells[cell.position.x+1][cell.position.y].addEvent(event);
        }
        if(cell.position.y < length-1 && checkIfSideEventExistOnCell(cells[cell.position.x][cell.position.y+1], event)){
            cells[cell.position.x][cell.position.y+1].addEvent(event);
        }
    }

    private boolean checkIfSideEventExistOnCell(Cell cell, Cell.Event event){
        //Check pit, because we won't put the gold or the Wumpus on that cell (or event wind or smell)
        return (!cell.getEvents().contains(event) && !cell.getEvents().contains(Cell.Event.pit));
    }

    private int generateRandom(int Max){
        return (int) Math.round((Math.random() * (Max - 1)));
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public ArrayList<Point> getLockedPoints() {
        return lockedPoints;
    }

    public void generate(){

        Cell[][] mycells = getCells();

        JFrame frame = new JFrame("Wumpus Game");
        //frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        // Map découverte
        JPanel panelGame = new JPanel(new GridLayout(getWidth(),getLength())); // on n'a pas besoin de mettre le nombre de lignes si on donne un nombre de colonnes
        JPanel panelPlayer = new JPanel(new GridLayout(getWidth(),getLength())); // on n'a pas besoin de mettre le nombre de lignes si on donne un nombre de colonnes

        for(int i = 0; i < mycells.length; i++){
            for(int j = 0; j < mycells[i].length; j++){
                // Création cellule
                JPanel celluleGame = new JPanel(); // on utilise un simple JPanel pour chaque cellule, donc on adaptera la couleur de fond (background)
                JPanel cellulePlayer = new JPanel(); // on utilise un simple JPanel pour chaque cellule, donc on adaptera la couleur de fond (background)
                celluleGame.setPreferredSize(new Dimension(32,32)); // donne une taille de 32x32 pixels par défaut
                cellulePlayer.setPreferredSize(new Dimension(32,32)); // donne une taille de 32x32 pixels par défaut

                // Definir coordonné de la cellule x,y
                celluleGame.setLocation(i, j);
                cellulePlayer.setLocation(i, j);
                celluleGame.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                cellulePlayer.setBorder(BorderFactory.createLineBorder(Color.WHITE));

                // Generation map
                // Case without events
                String dangers = " ";
                celluleGame.setBackground(Color.WHITE);
                cellulePlayer.setBackground(Color.BLACK);
                for (Cell.Event event :mycells[j][i].getEvents()) {

                        if (mycells[j][i].getEvents().contains(Cell.Event.agent)) {
                            dangers = dangers + "A";
                            celluleGame.setBackground(Color.GREEN);
                            cellulePlayer.setBackground(Color.GREEN);
                        }
                        if (mycells[j][i].getEvents().contains(Cell.Event.gold)) {
                            dangers = dangers + "G";
                            celluleGame.setBackground(Color.YELLOW);
                            cellulePlayer.setBackground(Color.BLACK);
                        }
                        if (mycells[j][i].getEvents().contains(Cell.Event.wumpus)) {
                            dangers = dangers + "W";
                            celluleGame.setBackground(Color.RED);
                            cellulePlayer.setBackground(Color.BLACK);
                        }
                        if (mycells[j][i].getEvents().contains(Cell.Event.smell)) {
                            celluleGame.setBackground(Color.ORANGE);
                            cellulePlayer.setBackground(Color.BLACK);
                        }
                        if (mycells[j][i].getEvents().contains(Cell.Event.pit)) {
                            celluleGame.setBackground(Color.BLUE);
                            cellulePlayer.setBackground(Color.BLACK);
                        }
                        if (mycells[j][i].getEvents().contains(Cell.Event.wind)) {
                            celluleGame.setBackground(Color.CYAN);
                            cellulePlayer.setBackground(Color.BLACK);
                        }
                        JLabel jlabelGame = new JLabel(dangers);
                        celluleGame.add(jlabelGame);
                        JLabel jlabelPlayer = new JLabel(dangers);
                        cellulePlayer.add(jlabelPlayer);

                }
                /*if(mycells[j][i].getEvents().isEmpty()){
                    celluleGame.setBackground(Color.WHITE);
                    cellulePlayer.setBackground(Color.BLACK);
                }else if(mycells[j][i].getEvents().contains(Cell.Event.agent)){
                    celluleGame.setBackground(Color.GREEN);
                    JLabel jlabelGame = new JLabel("A");
                    celluleGame.add(jlabelGame);
                    cellulePlayer.setBackground(Color.GREEN);
                    JLabel jlabelPlayer = new JLabel("A");
                    cellulePlayer.add(jlabelPlayer);
                }else if(mycells[j][i].getEvents().contains(Cell.Event.pit)){
                    celluleGame.setBackground(Color.YELLOW);
                    JLabel jlabelCelluleGame = new JLabel("P");
                    celluleGame.add(jlabelCelluleGame);
                    cellulePlayer.setBackground(Color.BLACK);
                    JLabel jlabelCellulePlayer = new JLabel("P");
                    cellulePlayer.add(jlabelCellulePlayer);
                }else if(mycells[j][i].getEvents().contains(Cell.Event.wumpus)){
                    celluleGame.setBackground(Color.RED);
                    JLabel jlabelCelluleGame = new JLabel("W");
                    celluleGame.add(jlabelCelluleGame);
                    cellulePlayer.setBackground(Color.BLACK);
                    JLabel jlabelCellulePlayer = new JLabel("W");
                    cellulePlayer.add(jlabelCellulePlayer);
                }*/

                // Ajout de la cellule dans une liste de cellules
                panelGame.add(celluleGame);
                panelPlayer.add(cellulePlayer);

            }
        }

        frame.add(panelGame, BorderLayout.LINE_START);
        frame.add(panelPlayer, BorderLayout.LINE_END);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void display(){

        Cell[][] mycells = getCells();

        for(int i = 0; i < mycells.length; i++) {
            for (int j = 0; j < mycells[i].length; j++) {
                String dangers = "";
                for (Cell.Event event :mycells[j][i].getEvents()) {
                    if (mycells[j][i].getEvents().contains(Cell.Event.agent)) {
                        dangers = dangers + "A";
                    }
                    if (mycells[j][i].getEvents().contains(Cell.Event.gold)) {
                        dangers = dangers + "G";
                    }
                    if (mycells[j][i].getEvents().contains(Cell.Event.wumpus)) {
                        dangers = dangers + "W";
                    }
                    if (mycells[j][i].getEvents().contains(Cell.Event.smell)) {
                        dangers = dangers + "S";
                    }
                    if (mycells[j][i].getEvents().contains(Cell.Event.pit)) {
                        dangers = dangers + "P";
                    }
                    if (mycells[j][i].getEvents().contains(Cell.Event.wind)) {
                        dangers = dangers + "I";
                    }
                }
                // Ajout du player
                System.out.print("|  "+dangers+"  ");

            }
            System.out.println("| \n");
        }
    }
}
