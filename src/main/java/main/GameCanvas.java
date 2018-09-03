/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import sprite.Enemy;
import sprite.Dog;
import sprite.Item;
import sprite.Element;
import managers.ItemsManager;
import managers.EnemyManager;
import managers.Floor;
import managers.ElementManager;
import static main.GameVariables.*;
import static main.Utils.*;
import runnable.TextRunnable;
import sprite.floor.FloorLine;
import java.util.Map;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author dariatunina
 */
public class GameCanvas extends Canvas {

    private final EnemyManager enemyManager;
    private final Floor floor;
    private final Map<GameVariables, Integer> gameVariables;
    private final Dog dog;
    private final ItemsManager itemManager;
    private final ElementManager elementManager;
    private double textMessageY;

    /**
     * Creates a new instance of GameCanvas
     *
     * @param enemyManager manager that has all Enemy sprites
     * @param gameVariables variables that can help indicate stage that game in
     * @param floor the ground
     * @param dog the main character of the game
     * @param itemManager that has all Item sprites
     * @param elementManager manager that has all Element sprites
     */
    public GameCanvas(EnemyManager enemyManager,
            Map<GameVariables, Integer> gameVariables,
            Floor floor, Dog dog, ItemsManager itemManager,
            ElementManager elementManager) {
        this.enemyManager = enemyManager;
        this.gameVariables = gameVariables;
        this.floor = floor;
        this.dog = dog;
        this.itemManager = itemManager;
        this.elementManager = elementManager;
    }

    /**
     * Bind canvas size to parent Pane width and height.
     */
    public void fixAspectRatio() {
        Parent parent = getParent();
        if (parent instanceof Pane) {
            widthProperty().bind(((Pane) getParent()).widthProperty());
            heightProperty().bind(((Pane) getParent()).heightProperty());
        }
    }

    private void renderFloor(GraphicsContext gc) {
        for (FloorLine line : floor.getFloor()) {
            line.render(gc);
        }
    }

    /**
     * Paints the dog's inventory on the screen
     */
    public void renderInventory() {
        GraphicsContext gc = getGraphicsContext2D();
        dog.displayLife(gc);
        gc.drawImage(DIALOGBOX_IMG, APP_WIDTH / 6 - 5, 105,
                2 * APP_WIDTH / 3 + 10,
                255);
        gc.setLineWidth(5);
        gc.setFill(Color.BLACK);
        gc.setFont(FONT);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("INVENTORY", APP_WIDTH / 2, APP_HEIGHT / 4);
        dog.getInventory().render(gc);
    }

    /**
     * Paints dialog box with specific text message on the screen
     *
     * @param textMessage the specific text message
     */
    public void renderTextMessage(String textMessage) {
        GraphicsContext gc = getGraphicsContext2D();
        textMessageY = dog.getCenterY() < APP_HEIGHT / 2
                ? APP_HEIGHT - 75 : 5;
        gc.drawImage(DIALOGBOX_IMG, APP_WIDTH / 10,
                textMessageY,
                APP_WIDTH * 4 / 5, 70);
        gc.setFont(FONT);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFill(Color.BLACK);
        gc.fillText(textMessage, APP_WIDTH / 2,
                dog.getCenterY() < APP_HEIGHT / 2 ? APP_HEIGHT - 30 : 50);
        if (gameVariables.get(PAUSE) == 0) {
            Thread th = new Thread(new TextRunnable(gameVariables, this));
            th.start();
        }
        if (textMessage.equals("OPENING THE DOOR")) {
            gameVariables.put(DOOR_OPENED, 1);
        }
    }

    /**
     * Paint white rectangle to hide the text message,
     * but leave dialog box painted
     */
    public void clearTextMessage() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(APP_WIDTH / 4 + 20,
                dog.getCenterY() < APP_HEIGHT / 2 ? APP_HEIGHT - 60 : 15,
                APP_WIDTH / 2 - 35, 40);
    }

    /**
     * Paint new screen with current parameters of everything.
     */
    public void redraw() {
        clearScreen();
        GraphicsContext gc = getGraphicsContext2D();
        renderFloor(gc);
        for (Element element : elementManager.getSprites()) {
            element.render(gc);
        }
        for (Item item : itemManager.getSprites()) {
            item.render(gc);
        }
        if (dog.isVisable()) {
            dog.render(gc);
            if (dog.isAttacking()) {
                dog.drawAttack(gc);
            }
        }
        dog.displayLife(gc);
        for (Enemy enemy : enemyManager.getSprites()) {
            if (!enemy.isDead()) {
                gc.setLineWidth(2);
                enemy.render(gc);
                enemy.displayLife(gc);
                if (enemy.hasNoticed()) {
                    enemy.drawAttentionIcon(gc, gameVariables);
                }
            }
        }
    }

    private void clearScreen() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, APP_WIDTH, APP_HEIGHT);
    }

    private void assignsTextOnScreen(String text, Color color, double height) {
        GraphicsContext gc = getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.setStroke(color);
        gc.setLineWidth(1);
        gc.setFont(FONT);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText(text, APP_WIDTH / 2, height);
        gc.strokeText(text, APP_WIDTH / 2, height);
    }

    /**
     * Displays rules of the game
     */
    public void startScreen() {
        clearScreen();
        assignsTextOnScreen(RULES, Color.DODGERBLUE, APP_HEIGHT / 4);
    }

    /**
     * Displays game over message
     */
    public void gameOverScreen() {
        clearScreen();
        assignsTextOnScreen(GAME_OVER_TEXT, Color.CRIMSON, APP_HEIGHT / 2);
    }

    /**
     * Displays winning message in the dialog box
     *
     * @param theEnd tells if game was completed
     */
    public void endScreen(boolean theEnd) {
        GraphicsContext gc = getGraphicsContext2D();
        gameVariables.put(PLAYING, 0);
        gameVariables.put(GAME_OVER, 1);
        dog.setVisable(false);
        redraw();
        gc.drawImage(DIALOGBOX_IMG, APP_WIDTH / 6 - 5, 105,
                2 * APP_WIDTH / 3 + 10,
                255);
        gc.drawImage(FIREWORKS_2, APP_WIDTH / 6 + 375, 105, 320, 255);
        gc.drawImage(FIREWORKS_1, APP_WIDTH / 6 - 5, 105, 380, 255);
        assignsTextOnScreen(
                theEnd ? END_MESSAGE : END_LVL_MESSAGE, 
                Color.BLACK, APP_HEIGHT / 4);

    }

}
