package main;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class InterfaceGame {

    private JFrame myFrame;
    private JPanel panelGame;
    private JPanel panelPlayer;
    private ArrayList<JPanel> listCellules = new ArrayList<JPanel>();
    private ArrayList<JPanel> listCellulesPanelGame = new ArrayList<JPanel>();

    private java.util.List<Point> bestSoluce = new ArrayList<>();;

    public InterfaceGame(GameMap map) {
        this.refresh(map, false);
    }

    public void refresh(GameMap map, boolean refresh){
        if(refresh) {
            myFrame.remove(panelGame);
            myFrame.remove(panelPlayer);
        }else{
            myFrame = new JFrame("Wumpus Game");
            myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }

        Cell[][] mycells = map.getCells();
        panelGame = new JPanel(new GridLayout(map.getWidth(),map.getLength())); // on n'a pas besoin de mettre le nombre de lignes si on donne un nombre de colonnes
        panelPlayer = new JPanel(new GridLayout(map.getWidth(),map.getLength())); // on n'a pas besoin de mettre le nombre de lignes si on donne un nombre de colonnes

        Agent agent = map.getAgent();
        Cell[][] knowsCells = agent.getKnownCells();
        Cell[][] knowsCellsAndSuspicious = agent.getSupposedCells();

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
                if (knowsCells[j][i] != null) {
                    celluleGame.setBackground(Color.PINK);
                    cellulePlayer.setBackground(Color.WHITE);
                    cellulePlayer.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                }else if (knowsCellsAndSuspicious[j][i] != null) {
                    if (knowsCellsAndSuspicious[j][i].getEvents().contains(Cell.Event.smell)) {
                        cellulePlayer.setBackground(Color.ORANGE);
                    }else if (knowsCellsAndSuspicious[j][i].getEvents().contains(Cell.Event.wind)) {
                        cellulePlayer.setBackground(Color.CYAN);
                    }else{
                        cellulePlayer.setBackground(Color.ORANGE);
                    }
                }else{
                    cellulePlayer.setBackground(Color.BLACK);
                }

                if (mycells[j][i].getEvents().contains(Cell.Event.gold)) {
                    dangers = dangers + "G";
                    celluleGame.setBackground(Color.YELLOW);
                }
                if (mycells[j][i].getEvents().contains(Cell.Event.wumpus)) {
                    dangers = dangers + "W";
                    celluleGame.setBackground(Color.RED);
                }
                if (mycells[j][i].getEvents().contains(Cell.Event.smell)) {
                    celluleGame.setBackground(Color.ORANGE);
                }
                if (mycells[j][i].getEvents().contains(Cell.Event.pit)) {
                    celluleGame.setBackground(Color.BLUE);
                }
                if (mycells[j][i].getEvents().contains(Cell.Event.wind)) {
                    celluleGame.setBackground(Color.CYAN);
                }

                if (mycells[j][i].getEvents().contains(Cell.Event.agent)) {
                    Direction direction = map.getAgent().getDirection();
                    if(direction == Direction.up){
                        dangers = dangers + "^";
                    }else if(direction == Direction.down){
                        dangers = dangers + "v";
                    }else if(direction == Direction.left){
                        dangers = dangers + "<";
                    }else{
                        dangers = dangers + ">";
                    }

                    celluleGame.setBackground(Color.PINK);
                    cellulePlayer.setBackground(Color.WHITE);
                    if (knowsCellsAndSuspicious[j][i] != null && knowsCellsAndSuspicious[j][i].getEvents().contains(Cell.Event.gold)) {
                        cellulePlayer.setBackground(Color.YELLOW);
                    }
                }

                JLabel jlabelGame = new JLabel(dangers);
                celluleGame.add(jlabelGame);
                JLabel jlabelPlayer = new JLabel(dangers);
                cellulePlayer.add(jlabelPlayer);

                // Ajout de la cellule dans une liste de cellules
                panelGame.add(celluleGame);
                listCellules.add(celluleGame);
                listCellulesPanelGame.add(celluleGame);
                panelPlayer.add(cellulePlayer);
            }
        }

        /*for(Point soluce : this.bestSoluce){
            JPanel celluleGame = new JPanel(); // on utilise un simple JPanel pour chaque cellule, donc on adaptera la couleur de fond (background)
            celluleGame.setPreferredSize(new Dimension(32,32)); // donne une taille de 32x32 pixels par défaut
            // Definir coordonné de la cellule x,y
            celluleGame.setLocation(soluce.x, soluce.y);
            celluleGame.setBackground(Color.GREEN);
            panelGame.add(celluleGame);
        }*/

        myFrame.add(panelGame, BorderLayout.LINE_START);
        myFrame.add(panelPlayer, BorderLayout.LINE_END);
        myFrame.pack();
        if(refresh) {
            myFrame.invalidate();
            myFrame.repaint();
        }else{
            myFrame.setLocationRelativeTo(null);
            myFrame.setVisible(true);
        }
    }

    public void endGame(GameMap map){
        Cell[][] celluleMap = map.getCells();
        Component[] test = panelPlayer.getComponents();

        for (Component t : test){

            int x = t.getX()/32;
            int y = t.getY()/32;

            for(int i = 0; i < celluleMap.length; i++) {
                for (int j = 0; j < celluleMap[i].length; j++) {
                    if (celluleMap[x][y].getEvents().contains(Cell.Event.agent)) {
                        t.setBackground(Color.RED);
                    }
                }
            }
        }
    }

    public void setBestSoluce(java.util.List<Point> bestSoluce){
        this.bestSoluce = bestSoluce;
    }
}
