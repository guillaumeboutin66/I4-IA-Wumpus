/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testagent;

/**
 *
 * @author Erik
 */
public class Cell {  
    // todo : abstract
    public int x;
    public int y;
    public int Danger;

    // todo : put a number ?
    public Cell(int x, int y, int danger){
        
        this.x = x;
        this.y = y;
        this.Danger = danger;

    }

    
}
