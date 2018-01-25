package main;

class Game {
    //initialisation de la map
    GameMap map = new GameMap();
    Agent agent = map.getAgent();
    Wumpus wumpus = map.getWumpus();
    int tourcount = 0;
    int fin = 0;
    public static void main(String[] args) {
        affichage(map);
        affichage(joueur);
        //boucle de jeu principale
        //peut être mise dans un classe jeu du genre jeu.gameloop();
        while (fin == 0) {
            action = joueur.action(map.getCaseJoueur());
            //check condition de sortie
            fin = jeu.checkEnd(action);
            map.update(action);
            affichage(map);
            affichage(joueur);
            if (fin == 1) {
                affichage.gameEndAnimation();
            }
        }
    }
}