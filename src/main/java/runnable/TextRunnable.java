/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package runnable;

import game.GameCanvas;
import main.Variables;
import static main.Variables.*;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dariatunina
 */
public class TextRunnable implements Runnable {

    private final Map<Variables, Boolean> gameVariables;
    private final GameCanvas gc;

    /**
     * Creates a new instance of TextRunnable
     *
     * @param gameVariables variables that can help indicate stage that game in
     * @param gc canvas of main stage of the game
     */
    public TextRunnable(Map<Variables, Boolean> gameVariables,
            GameCanvas gc) {
        this.gameVariables = gameVariables;
        this.gc = gc;
    }

    @Override
    public void run() {
        try {
            gameVariables.put(PAUSE, true);
            int sleepTime = gameVariables.get(INVENTORY) ? 700 : 1000;
            Thread.sleep(sleepTime);
            gameVariables.put(TEXT_MESSAGE, false);
            if (gameVariables.get(INVENTORY)) {
                gc.clearTextMessage();
            }
            gameVariables.put(PAUSE, false);
        } catch (InterruptedException ex) {
            Logger.getLogger(TextRunnable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
