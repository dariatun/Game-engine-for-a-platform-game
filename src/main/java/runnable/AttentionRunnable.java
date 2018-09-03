/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package runnable;

import main.GameVariables;
import static main.GameVariables.PAUSE;
import sprite.Enemy;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dariatunina
 */
public class AttentionRunnable implements Runnable {

    private final Map<GameVariables, Integer> gameVariables;
    private final Enemy enemy;

    /**
     * Creates a new instance of AttentionRunnable
     *
     * @param gameVariables variables that can help indicate stage that game in
     * @param enemy the specific enemy
     */
    public AttentionRunnable(Map<GameVariables, Integer> gameVariables, Enemy enemy) {
        this.gameVariables = gameVariables;
        this.enemy = enemy;
    }

    @Override
    public void run() {
        try {
            gameVariables.put(PAUSE, 1);
            Thread.sleep(100);
            enemy.setNoticed(false);
            gameVariables.put(PAUSE, 0);
        } catch (InterruptedException ex) {
            Logger.getLogger(TextRunnable.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
