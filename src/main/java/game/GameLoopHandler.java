/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import main.Variables;
import sprite.Enemy;
import sprite.Dog;
import sprite.Item;
import sprite.LivingSprite;
import sprite.Element;
import managers.SaveFileManager;
import managers.ItemsManager;
import managers.EnemyManager;
import managers.Floor;
import managers.ElementManager;
import managers.Inventory;
import ids.ElementId;
import static main.Variables.*;
import static main.Utils.*;
import static ids.SpriteId.DOG;
import runnable.AdjustingRunnable;
import sprite.floor.FloorLine;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Point2D;

/**
 *
 * @author dariatunina
 */
public class GameLoopHandler {

    private final GameCanvas gameCanvas;
    private final EnemyManager enemyManager;
    private final Floor floor;
    private final Map<Variables, Boolean> gameVariables;
    private final Dog dog;
    private final ItemsManager itemManager;
    private final ElementManager elementManager;
    private String textMessage;
    private static final Logger LOG
            = Logger.getLogger(GameLoopHandler.class.getName());
    private final SaveFileManager fileManager;
    private File file;
    long startTime;
    long elapsedTime;
    boolean first;
    int count;

    /**
     * Creates a new instance of GameLoopHandler
     *
     * @param gameCanvas canvas of main stage of the game
     * @param enemyManager manager that has all Enemy sprites
     * @param gameVariables variables that can help indicate stage that game in
     * @param floor the ground
     * @param dog the main character of the game
     * @param itemManager manager that has all Item sprites
     * @param elementManager manager that has all Element sprites
     * @param fileManager manager that has all save files
     */
    public GameLoopHandler(GameCanvas gameCanvas,
            EnemyManager enemyManager,
            Map<Variables, Boolean> gameVariables,
            Floor floor, Dog dog,
            ItemsManager itemManager,
            ElementManager elementManager, SaveFileManager fileManager) {
        this.gameCanvas = gameCanvas;
        this.enemyManager = enemyManager;
        this.floor = floor;
        this.gameVariables = gameVariables;
        this.dog = dog;
        this.itemManager = itemManager;
        this.elementManager = elementManager;
        this.fileManager = fileManager;
        setGameVarToInit();
        gameVariables.put(PLAYING, false);
        gameVariables.put(FIRST_ATTEMPT, true);
        gameVariables.put(STATIONARY_SCREEN, false);
        gameVariables.put(GAME_STARTED, false);
        file = fileManager.getFile();
        startTime = System.currentTimeMillis();
        elapsedTime = 0L;
        first = true;
        count = 0;
    }

    /**
     * Everything returns to started positions. If current saving file exist,
     * parameters will load from that file. *
     */
    public void setInitSprites() {
        dog.resetToInit();
        floor.resetToInit();
        elementManager.getSprite(ElementId.DOOR).changeImg("Closed");
        if (file.length() != 0 && !gameVariables.get(DOOR_OPENED)) {
            spritesFromFile();
        } else {
            LOG.info("Set init parameters");
            enemyManager.resetToInit();
            itemManager.resetToInit();
        }
    }

    private void spritesFromFile() {
        LOG.info("Set saved parameters from file");
        try {
            file = fileManager.getFile();
            Scanner sc = new Scanner(file);
            sc.useLocale(Locale.ENGLISH);
            if (sc.hasNext() && sc.next().equals("Level")) {
                int lvl = sc.nextInt();
                floor.changeLevel(lvl);
                elementManager.changeLevel(lvl);
                count = lvl - 1;
            }
            if (sc.hasNext() && sc.next().equals("Dog")) {
                dog.setPosition(sc.nextDouble(), sc.nextDouble());
            }
            enemyManager.readFromSaveFile(sc, "Enemies");
            itemManager.readFromSaveFile(sc, "Items");
            dog.getInventory().readFromFile(sc);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GameLoopHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Saves current position of dog and kinds of existing enemies and items in
     * current saving file.
     *
     * @param position current position of the dog or position of save point,
     * that dog currently using
     */
    public void save_positions(Point2D position) {
        file = fileManager.getFile();
        try (FileWriter fw = new FileWriter(file)) {
            fw.write("Level\n" + String.valueOf(count + 1) + "\n");
            fw.write("Dog\n" + position.getX()
                    + " " + position.getY() + "\n");
            enemyManager.writeInFile(fw, "Enemies\n");
            itemManager.writeInFile(fw, "Items\n");
            dog.getInventory().writeInFile(fw);
        } catch (IOException ex) {
            Logger.getLogger(GameLoopHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void updateDog() {
        if (dog.isMovingLeft()) {
            dog.moveLeft();
            dog.getSprAnim().changeToWalk();
        } else {
            if (dog.isMovingRight()) {
                dog.moveRight();
                dog.getSprAnim().changeToWalk();
            } else {
                dog.setMoveX(0);
                dog.getSprAnim().changeToStand();
            }
        }
        enemyCollisions();
        itemCollisions();
        floorColissions();
        screenLimitCheck(dog);
        if (dog.isAttacking()) {
            attackCheck();
        }
        if (dog.isUsing()) {
            checkIfUsable();
        }
        if (dog.isDigging()) {
            diggingCheck();
        }
        dog.updtFacingSide();
        if (dog.isStanding()) {
            if (dog.getLastStandPos().distance(dog.getCurrPos()) > 100) {
                LOG.log(Level.INFO, "Dog stands on {0}", dog.getCurrPos());
            }
            dog.setLastStandPos(dog.getCurrPos());
        }
        dog.setPosition(dog.getX() + dog.getMoveX(), dog.getY() + dog.getMoveY());
        dog.gravityForce();
    }

    private void updateEnemies() {
        for (Enemy enemy : enemyManager.getSprites()) {
            enemy.controllingTerr(dog);
            floorColissions(enemy);
            screenLimitCheck(enemy);
            enemy.updtFacingSide();
            enemy.setPosition(enemy.getX() + enemy.getMoveX(),
                    enemy.getY() + enemy.getMoveY());
        }
    }

    private void inventoryManipulation() {
        Inventory inventory = dog.getInventory();
        if (dog.isUsing()) {
            // Dog can use item from inventory only if..
            //..it wasn't using something not so long ago
            if (!gameVariables.get(PAUSE)) {
                addTextMessage(inventory.useItem(dog));
                dog.setUsing(false);
            }
        }
    }

    private void enemyCollisions() {
        for (Enemy enemy : enemyManager.getSprites()) {
            //check if there is colision with enemy
            if (enemy.getBounds().intersects(dog.getBounds())) {
                //if there is enemy, dog loses life
                dog.changeLife(-enemy.getForce());
                enemy.setMoveX(0);
                enemy.setMoveY(0);
                checkIfIsDead();
            }
        }
    }

    private void itemCollisions() {
        for (Item item : itemManager.getSprites()) {
            if (item.getBounds().intersects(dog.getBounds())) {
                LOG.log(Level.INFO, "Try to add {0} to inventory", item);
                if (dog.getInventory().addItem(item)) {
                    LOG.log(Level.INFO, "Delete {0}", item);
                    itemManager.deleteSprite(item.getName());
                    LOG.log(Level.INFO, "Left {0}", itemManager);
                } else {
                    LOG.log(Level.WARNING, "Unsuccessful: inventory is full");
                    addTextMessage("Inventory is full");
                }
            }
        }
    }

    private void screenLimitCheck(LivingSprite sprite) {
        if ((sprite.getMoveY() + sprite.getY()) < 0) {
            sprite.setMoveY(GRAVITATION);
            sprite.setY(0.1);
        }
        if (sprite.getSpriteId() == DOG) {
            screenLimitCheckDog();
        } else {
            screenLimitCheckEnemy((Enemy) sprite);
        }
    }

    private void screenLimitCheckDog() {
        if ((dog.getMoveX() + dog.getX()) <= 0
                || (dog.getMoveX() + dog.getX() + dog.getWidth()) >= APP_WIDTH) {
            // dog can't go behind seen screen
            dog.setX(dog.getMoveX() > 0
                    ? APP_WIDTH - dog.getWidth() - 0.1 : 0.1);
            dog.setMoveX(0);
        }

        if (dog.getMoveY() + dog.getY() + dog.getHeight() > APP_HEIGHT) {
            //if dog fell, it'll lose life
            LOG.log(Level.INFO, "Dog fell on {0}, {1}",
                    new Object[]{dog.getX(), dog.getY()});
            dog.changeLife(-30);
            dog.setMoveX(0);
            dog.setMoveY(0);
            //dog reaperes in last position it was standing
            dog.setPosition(dog.getLastStandPos());
            if (!checkIfIsDead()) {
                Thread th = new Thread(new AdjustingRunnable(dog));
                th.start();
            }
        }
    }

    private boolean checkIfIsDead() {
        //if life is too low - game over
        boolean ret = false;
        if (dog.getLife() <= 0) {
            ret = true;
            LOG.log(Level.INFO,
                    "Dog has 0 health. Last alive position: {0}",
                    dog.getCurrPos());
            stopGame();
        }
        return ret;
    }

    private void screenLimitCheckEnemy(Enemy enemy) {
        //if there is the end of the screen..
        if ((enemy.getMoveX() + enemy.getX()) <= 0
                || (enemy.getMoveX() + enemy.getX() + enemy.getWidth()) >= APP_WIDTH) {
            //..enemy changes direction
            enemy.changeDirectionX();
            enemy.changeDirectionY();
        }
    }

    private void addTextMessage(String msg) {
        gameVariables.put(TEXT_MESSAGE, true);
        textMessage = msg;
    }

    private void floorColissions() {
        boolean inTheAir = true;
        for (FloorLine line : floor.getFloor()) {
            dog.fallingCheck(line, inTheAir);
            inTheAir = dog.checkCollissionsOnY(line, inTheAir);
            dog.checkCollisionsOnX(line);
        }
        if (inTheAir) {
            dog.setStanding(false);
        }
    }

    private void floorColissions(Enemy enemy) {
        for (FloorLine line : floor.getFloor()) {
            enemy.checkCollisionsOnY(line);
            enemy.checkCollisionsOnX(line);
        }
    }

    private void attackCheck() {
        for (Enemy enemy : enemyManager.getSprites()) {
            //if there is enemy in attack range..
            if (enemy.getBounds().intersects(dog.getAttackBounds())) {
                //..enemy loses points of life ..
                enemy.changeLife(-DOG_FORCE);
                //..or disapeares if his life is too low
                if (enemy.getLife() <= 0) {
                    LOG.log(Level.INFO, "Delete {0}", enemy);
                    enemyManager.deleteSprite(enemy.getName());
                    LOG.log(Level.INFO, "Left {0}", enemyManager);
                }
            }
        }
    }

    private void checkIfUsable() {
        for (Element element : elementManager.getSprites()) {
            if (element.getBounds().intersects(dog.getBounds())) {
                //if dog interracts with save point..
                if (element.getFunc().equals("SAVE")) {
                    //..current state of game writes in file
                    save_positions(element.getCurrPos());
                    addTextMessage("Saved");
                    //..and dog recover full health
                    dog.setLife(DOG_LIFE);
                } else {
                    // try to find item that can be used with this element
                    if (dog.getInventory().findItemForElement(element)) {
                        gameVariables.put(DOOR_OPENED, true);
                    } else {
                        /* if there isn't item that can be used with this element
                          player is told about it by pop up message */
                        addTextMessage("CAN'T " + element.getFunc()
                                + " THE " + element.getSpecificId());
                    }
                }
            }
        }
    }

    private void diggingCheck() {
        for (FloorLine line : floor.getDiggableLines()) {
            //remove line of floor if dog is "digging" it and line can be digged
            if (line != null && line.getBounds().intersects(
                    dog.getBounds(0, FLR_SQR_SIZE / 2))) {
                floor.removeLine(line.getPosition());
            }
        }
    }

    private void checkEnd() {
        //Opening door means winning the level
        if (gameVariables.get(DOOR_OPENED)) {
            //end screen pops up, if dog moves after oppening the door
            if (elementManager.getSprite(ElementId.DOOR).
                    getBounds().intersects(dog.getBounds())
                    && (dog.isMovingLeft()
                    || dog.isMovingRight()
                    || dog.getMoveY() < 0)) {
                count++;
                file.delete();
                gameCanvas.endScreen(!(count < NUM_OF_LEVELS));
                
            } else {
                //..if not pops up message that tells player about opportunity to end the game
                if (count + 1 < NUM_OF_LEVELS) {
                    addTextMessage(
                            "You can leave through the door and end this level!");
                } else {
                    addTextMessage(
                            "You can leave through the door and end the game!");
                }
            }
        }
    }

    private void stopGame() {
        gameVariables.put(GAME_OVER, true);
        gameVariables.put(PLAYING, false);
        gameCanvas.gameOverScreen();
    }

    private void changeLevel(int level) {
        dog.changeLevel(level);
        floor.changeLevel(level);
        enemyManager.changeLevel(level);
        elementManager.changeLevel(level);
        itemManager.changeLevel(level);
    }

    private void restart() {
        if (count == NUM_OF_LEVELS) {
            count = 0;
        }
        changeLevel(count + 1);
        //Returns init or saved positions
        setInitSprites();
        setGameVarToInit();
        //Door closes
        elementManager.getSprite(ElementId.DOOR).changeImg("Closed");
        gameCanvas.redraw();
    }

    private void setGameVarToInit() {
        gameVariables.put(INVENTORY, false);
        gameVariables.put(GAME_OVER, false);
        gameVariables.put(PAUSE, false);
        gameVariables.put(PLAYING, true);
        gameVariables.put(DOOR_OPENED, false);
        gameVariables.put(TEXT_MESSAGE, false);
    }

    /*
    * Changes frame of image if nessesary
     */
    private void updateSpritesImg() {
        if (elapsedTime > 200) {
            dog.updateImg();
            for (Enemy enemy : enemyManager.getSprites()) {
                enemy.updateImg();
            }
            startTime = System.currentTimeMillis();
            elapsedTime = 0L;
        }
        elapsedTime = (new Date()).getTime() - startTime;
    }

    private void updateStationaryScreen() {
        if (!gameVariables.get(GAME_OVER)) {
            if (gameVariables.get(FIRST_ATTEMPT)) {
                gameCanvas.startScreen();
            } else {
                restart();
            }
        } else {
            //if door is open, than it's not a game over
            if (!gameVariables.get(DOOR_OPENED)) {
                stopGame();
            }
        }
        gameVariables.put(STATIONARY_SCREEN, true);
    }

    private void updateMovingScreen() {
        gameVariables.put(STATIONARY_SCREEN, false);
        if (gameVariables.get(INVENTORY)) {
            inventoryManipulation();
            gameCanvas.renderInventory();
        } else {
            updateSpritesImg();
            updateDog();
            updateEnemies();
            gameCanvas.redraw();
            checkEnd();
        }
        //Pop up message if there is one
        if (gameVariables.get(TEXT_MESSAGE)) {
            gameCanvas.renderTextMessage(textMessage);
        }
    }

    public void handle() {
        if (gameVariables.get(GAME_STARTED)) {
            if (gameVariables.get(PLAYING)) {
                updateMovingScreen();
            } else {
                if (!gameVariables.get(STATIONARY_SCREEN)) {
                    updateStationaryScreen();
                }
            }
        }
    }
}
