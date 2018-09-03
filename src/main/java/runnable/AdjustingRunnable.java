/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package runnable;

import sprite.Dog;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dariatunina
 */
public class AdjustingRunnable implements Runnable {

    private final Dog dog;

    /**
     * Creates a new instance of AdjustingRunnable
     *
     * @param dog the main character of the game
     */
    public AdjustingRunnable(Dog dog) {
        this.dog = dog;
    }

    @Override
    public void run() {
        try {
            int count = 0;
            while (count < 70) {
                dog.setVisable(false);
                Thread.sleep(10);
                dog.setVisable(true);
                Thread.sleep(10);
                count++;
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(AdjustingRunnable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
