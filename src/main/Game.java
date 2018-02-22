package main;



class Game {
    //initialisation de la map

    public static void main(String[] args) {
        int weigh = 10;
        int height = 10;
        GameMap map = new GameMap(weigh, height);
        int tourCount = 0;
        int fin = 0;
        System.out.println("Wumpus Game");


        //affichage(map);
        map.generate();
        map.display();

        //affichage(joueur);
        //boucle de jeu principale
        //peut être mise dans un classe jeu du genre jeu.gameloop();
        while (fin == 0) {
            //action = joueur.action(map);
            //ComputedDecision action = new ComputedDecision();

            //check condition de sortie
            fin = checkEnd();

            //map.update(action);
            //affichage(map);
            //affichage(joueur);
            if (fin == 1) {
                //affichage.gameEndAnimation();
            }
        }
    }

    public static int checkEnd(){
        return 0;
    }
}