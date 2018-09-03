/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import static main.GameVariables.*;
import sprite.Dog;
import java.util.Map;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author dariatunina
 */
public class UserActionHandler implements EventHandler<KeyEvent> {

    private final Map<GameVariables, Integer> gameVariables;
    private final Dog dog;

    /**
     * Creates a new instance of UserActionHandler
     *
     * @param gameVariables variables that can help indicate stage that game in
     * @param dog the main character of the game
     */
    public UserActionHandler(
            Map gameVariables, Dog dog) {
        this.gameVariables = gameVariables;
        this.dog = dog;
    }

    private void left_key_managing() {
        if (gameVariables.get(INVENTORY) == 1) {
            dog.getInventory().changeCurrX(-1);
        } else {
            dog.moveLeft();
        }
    }

    private void right_key_managing() {
        if (gameVariables.get(INVENTORY) == 1) {
            dog.getInventory().changeCurrX(1);
        } else {
            dog.moveRight();
        }

    }

    private void space_managing() {
        if (!dog.isJumping()) {
            dog.jumpUp();
            dog.setStanding(false);
            dog.setJumping(true);
        }
    }

    private void enter_managing() {
        gameVariables.put(FIRST_ATTEMPT, 0);
        if (gameVariables.get(GAME_OVER) == 0) {
            gameVariables.put(PLAYING, 1);
        } else {
            gameVariables.put(GAME_OVER, 0);
        }
        gameVariables.put(STATIONARY_SCREEN, 0);
    }

    private void inventory_managing() {
        if (gameVariables.get(INVENTORY) == 1) {
            gameVariables.put(INVENTORY, 0);
            dog.getInventory().reset();
        } else {
            gameVariables.put(INVENTORY, 1);
        }
    }

    @Override
    public void handle(KeyEvent event) {
        if (event.getEventType() == KeyEvent.KEY_PRESSED
                && gameVariables.get(GAME_STARTED) == 1) {
            if (gameVariables.get(PLAYING) == 1) {
                if (event.getCode() == KeyCode.LEFT) {
                    left_key_managing();
                }
                if (event.getCode() == KeyCode.RIGHT) {
                    right_key_managing();
                }
                if (event.getCode() == KeyCode.SPACE) {
                    space_managing();
                }
                if (event.getCode() == KeyCode.X) {
                    dog.setAttacking(true);
                }
                if (event.getCode() == KeyCode.Z) {
                    dog.setUsing(true);
                }
                if (event.getCode() == KeyCode.C) {
                    dog.setDigging(true);
                }
                if (event.getCode() == KeyCode.I) {
                    inventory_managing();
                }
            } else { // start game by pressing enter
                if (event.getCode() == KeyCode.ENTER) {
                    enter_managing();
                }
            }
        } else {
            if (event.getEventType() == KeyEvent.KEY_RELEASED) {
                if (gameVariables.get(PLAYING) == 1) {
                    dog.stop();
                }
            }
        }
    }

}
