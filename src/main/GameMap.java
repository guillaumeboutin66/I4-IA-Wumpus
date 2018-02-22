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

    public Agent getAgent(){
        return null;
    }

    public Wumpus getWumpus(){
        return null;
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
                if(mycells[i][j].getDanger() == 0){
                    celluleGame.setBackground(Color.WHITE);
                    cellulePlayer.setBackground(Color.BLACK);
                }else if(mycells[i][j].getDanger() == 1){
                    celluleGame.setBackground(Color.GREEN);
                    JLabel jlabelGame = new JLabel("A");
                    celluleGame.add(jlabelGame);
                    cellulePlayer.setBackground(Color.GREEN);
                    JLabel jlabelPlayer = new JLabel("A");
                    cellulePlayer.add(jlabelPlayer);
                }else if(mycells[i][j].getDanger() == 100){
                    celluleGame.setBackground(Color.YELLOW);
                    JLabel jlabelCelluleGame = new JLabel("P");
                    celluleGame.add(jlabelCelluleGame);
                    cellulePlayer.setBackground(Color.BLACK);
                    JLabel jlabelCellulePlayer = new JLabel("P");
                    cellulePlayer.add(jlabelCellulePlayer);
                }else if(mycells[i][j].getDanger() == 1000){
                    celluleGame.setBackground(Color.RED);
                    JLabel jlabelCelluleGame = new JLabel("W");
                    celluleGame.add(jlabelCelluleGame);
                    cellulePlayer.setBackground(Color.BLACK);
                    JLabel jlabelCellulePlayer = new JLabel("W");
                    cellulePlayer.add(jlabelCellulePlayer);
                }

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
                // Ajout du player
                if(mycells[i][j].getDanger() == 0){
                    System.out.print("|     ");
                }else if(mycells[i][j].getDanger() == 1){
                    System.out.print("|  A  ");
                }else if(mycells[i][j].getDanger() == 100){
                    System.out.print("|  P  ");
                }else if(mycells[i][j].getDanger() == 1000){
                    System.out.print("|  W  ");
                }
            }
            System.out.println("| \n");
        }
    }
}
