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

    public InterfaceGame(GameMap map) {

        Cell[][] mycells = map.getCells();

        myFrame = new JFrame("Wumpus Game");
        //frame.setSize(800, 600);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panelGame = new JPanel(new GridLayout(map.getWidth(),map.getLength())); // on n'a pas besoin de mettre le nombre de lignes si on donne un nombre de colonnes
        panelPlayer = new JPanel(new GridLayout(map.getWidth(),map.getLength())); // on n'a pas besoin de mettre le nombre de lignes si on donne un nombre de colonnes

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
                        Direction direction = map.getAgent().getDirection();
                        if("up".equals(direction)){
                            dangers = dangers + "^";
                        }else if("down".equals(direction)){
                            dangers = dangers + "v";
                        }else if("left".equals(direction)){
                            dangers = dangers + "<";
                        }else {
                            dangers = dangers + ">";
                        }
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

                // Ajout de la cellule dans une liste de cellules
                panelGame.add(celluleGame);
                listCellules.add(celluleGame);
                listCellulesPanelGame.add(celluleGame);
                panelPlayer.add(cellulePlayer);

            }
        }

        myFrame.add(panelGame, BorderLayout.LINE_START);
        myFrame.add(panelPlayer, BorderLayout.LINE_END);
        myFrame.pack();
        myFrame.setLocationRelativeTo(null);
        myFrame.setVisible(true);
    }

    public void refreshGame(GameMap map){
        Cell[][] celluleMap = map.getCells();

        Component jframeTest = panelGame;
        Component[] test = panelGame.getComponents();

        for (Component t : test){

            int x = t.getX()/32;
            int y = t.getY()/32;
            Component a = t.getComponentAt(0, 9);
           // System.out.println("X : " + x + " - Y : " + y);

            for(int i = 0; i < celluleMap.length; i++) {
                for (int j = 0; j < celluleMap[i].length; j++) {

                    String dangers = " ";

                    if (celluleMap[x][y].getEvents().contains(Cell.Event.agent)) {
                        Direction direction = map.getAgent().getDirection();
                        if("up".equals(direction)){
                            dangers = dangers + "^";
                        }else if("down".equals(direction)){
                            dangers = dangers + "v";
                        }else if("left".equals(direction)){
                            dangers = dangers + "<";
                        }else {
                            dangers = dangers + ">";
                        }
                        t.setBackground(Color.GREEN);
                        //celluleTest.setBackground(Color.GREEN);

                        Component jl = t.getComponentAt(x, y);

                        JLabel jlabelGame = new JLabel(dangers);
                        //System.out.println(jlabelGame);
                    }
                }
            }
            //System.out.println("X : " + t.getX() + " - Y : " + t.getY());
        }
    }

    public void refreshGame2(GameMap map){
        Cell[][] celluleMap = map.getCells();

        Component jframeTest = panelGame;

        for(Component jpanel : panelGame.getComponents()){
            System.out.println(jpanel);
            if ( jpanel instanceof JLabel ) {
                // do something
                System.out.println("YES");
            }else{
                System.out.println("NO");
            }
        }
        Component[] test = panelGame.getComponents();

        // Reset user interface
        for(int i=0; i<test.length; i++)
        {
            test[i].setEnabled(true);

        }


            for(int i = 0; i < celluleMap.length; i++) {
                for (int j = 0; j < celluleMap[i].length; j++) {

                    Component myTest = jframeTest.getComponentAt(i, j);
                    //System.out.println("my test " + myTest);

                    for (Component t : test) {
                        String dangers = " ";
                        int x = t.getX() / 32;
                        int y = t.getY() / 32;
                        Component a = t.getComponentAt(0, 9);
                        //System.out.println("X : " + x + " - Y : " + y);

                        if (celluleMap[i][j].getEvents().contains(Cell.Event.agent)) {
                            Direction direction = map.getAgent().getDirection();
                            if("up".equals(direction)){
                                dangers = dangers + "^";
                            }else if("down".equals(direction)){
                                dangers = dangers + "v";
                            }else if("left".equals(direction)){
                                dangers = dangers + "<";
                            }else {
                                dangers = dangers + ">";
                            }
                            a.setBackground(Color.GREEN);
                            //celluleTest.setBackground(Color.GREEN);

                            Component jl = a.getComponentAt(x, y);

                            JLabel jlabelGame = new JLabel(dangers);
                            //System.out.println(jlabelGame);

                        }

                    }

                        String dangers = " ";
/*
                    if (celluleMap[i][j].getEvents().contains(Cell.Event.agent)) {
                        Direction direction = map.getAgent().getDirection();
                        if("up".equals(direction)){
                            dangers = dangers + "^";
                        }else if("down".equals(direction)){
                            dangers = dangers + "v";
                        }else if("left".equals(direction)){
                            dangers = dangers + "<";
                        }else {
                            dangers = dangers + ">";
                        }
                        a.setBackground(Color.GREEN);
                        //celluleTest.setBackground(Color.GREEN);

                        Component jl = a.getComponentAt(x, y);

                        JLabel jlabelGame = new JLabel(dangers);
                        System.out.println(jlabelGame);
                    }*/
                }
            }
            //System.out.println("X : " + t.getX() + " - Y : " + t.getY());

    }

    public void refreshPlayer(GameMap map){
        Cell[][] celluleMap = map.getCells();
        /*for(JPanel cellule : listCellules) {
            for(Cell[] celluleMap : cellulesMap) {
                if(cellule.getLocation() == celluleMap.){

                }
            }

        }*/
        Component jframeTest = panelPlayer;
        Component[] test = panelPlayer.getComponents();

        for (Component t : test){

            int x = t.getX()/32;
            int y = t.getY()/32;
            Component a = t.getComponentAt(0, 9);
            //System.out.println("X : " + x + " - Y : " + y);
            for(int i = 0; i < celluleMap.length; i++) {
                for (int j = 0; j < celluleMap[i].length; j++) {

                    Component tt = t.getComponentAt(i, j);
                    String dangers = " ";

                    if (celluleMap[x][y].getEvents().contains(Cell.Event.agent)) {
                        Direction direction = map.getAgent().getDirection();
                        if("up".equals(direction)){
                            dangers = dangers + "^";
                        }else if("down".equals(direction)){
                            dangers = dangers + "v";
                        }else if("left".equals(direction)){
                            dangers = dangers + "<";
                        }else {
                            dangers = dangers + ">";
                        }
                        t.setBackground(Color.GREEN);

                        //celluleTest.setBackground(Color.GREEN);
                    }
                }
            }
            //System.out.println("X : " + t.getX() + " - Y : " + t.getY());
        }
    }
}
