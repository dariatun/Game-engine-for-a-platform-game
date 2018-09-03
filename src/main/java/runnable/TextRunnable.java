/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package runnable;

import main.GameCanvas;
import main.GameVariables;
import static main.GameVariables.*;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dariatunina
 */
public class TextRunnable implements Runnable {

    private final Map<GameVariables, Integer> gameVariables;
    private final GameCanvas gc;

    /**
     * Creates a new instance of TextRunnable
     *
     * @param gameVariables variables that can help indicate stage that game in
     * @param gc canvas of main stage of the game
     */
    public TextRunnable(Map<GameVariables, Integer> gameVariables,
            GameCanvas gc) {
        this.gameVariables = gameVariables;
        this.gc = gc;
    }

    @Override
    public void run() {
        try {
            gameVariables.put(PAUSE, 1);
            int sleepTime = gameVariables.get(INVENTORY) == 1 ? 700 : 1000;
            Thread.sleep(sleepTime);
            gameVariables.put(TEXT_MESSAGE, 0);
            if (gameVariables.get(INVENTORY) == 1) {
                gc.clearTextMessage();
            }
            gameVariables.put(PAUSE, 0);
        } catch (InterruptedException ex) {
            Logger.getLogger(TextRunnable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
