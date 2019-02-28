/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import main.Variables;
import static main.Variables.*;
import sprite.Dog;
import java.util.Map;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author dariatunina
 */
public class UserActionHandlerInGame implements EventHandler<KeyEvent> {

    private final Map<Variables, Boolean> gameVariables;
    private final Dog dog;

    /**
     * Creates a new instance of UserActionHandler
     *
     * @param gameVariables variables that can help indicate stage that game in
     * @param dog the main character of the game
     */
    public UserActionHandlerInGame(
            Map gameVariables, Dog dog) {
        this.gameVariables = gameVariables;
        this.dog = dog;
    }

    private void left_key_managing() {
        if (gameVariables.get(INVENTORY)) {
            dog.getInventory().changeCurrX(-1);
        } else {
            dog.moveLeft();
        }
    }

    private void right_key_managing() {
        if (gameVariables.get(INVENTORY)) {
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
        gameVariables.put(FIRST_ATTEMPT, false);
        if (!gameVariables.get(GAME_OVER)) {
            gameVariables.put(PLAYING, true);
        } else {
            gameVariables.put(GAME_OVER, false);
        }
        gameVariables.put(STATIONARY_SCREEN, false);
    }

    private void inventory_managing() {
        if (gameVariables.get(INVENTORY)) {
            gameVariables.put(INVENTORY, false);
            dog.getInventory().reset();
        } else {
            gameVariables.put(INVENTORY, true);
        }
    }

    @Override
    public void handle(KeyEvent event) {
        if (event.getEventType() == KeyEvent.KEY_PRESSED
                && gameVariables.get(GAME_STARTED)) {
            if (gameVariables.get(PLAYING)) {
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
                if (gameVariables.get(PLAYING)) {
                    dog.stop();
                }
            }
        }
    }

}
