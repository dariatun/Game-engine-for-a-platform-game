/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;

/**
 *
 * @author dariatunina
 */
public class Utils {

    /**
     * Width of the screen
     */
    public static final double APP_WIDTH = 1024;

    /**
     * Height of the screen
     */
    public static final double APP_HEIGHT = 768;

    // images
    public static final String GFX_DIR = "/gfx/";
    public static final String LEVEL_DIR = "level_1/";
    public static final String IMAGE_DIR = "imageFiles/";
    public static final int NUM_OF_LEVELS = 2;

    public static final String BACKGROUND_IMG = "cave.png";

    public static final Image FIREWORKS_1 = new Image(GFX_DIR + "firework1.png");
    public static final Image FIREWORKS_2 = new Image(GFX_DIR + "firework2.png");

    public static final Image DIALOGBOX_IMG = new Image(GFX_DIR + "whiteDB.png");

    public static final String LIST_OF_IMG = "imgFiles.txt";
    
    //buttons
    public static final String START_BUTTON_IMG = "startButton.png";
    public static final String CREDITS_BUTTON_IMG = "creditsButton.png";
    public static final String BACK_BUTTON_IMG = "backButton.png";
    public static final String CONT_BUTTON_IMG = "continue.png";
    public static final String EXIT_IMG = "exit.png";

    //life bars
    /**
     * Image of filled life bar
     */
    public static final Image LIFE_BAR_FULL_IMG
            = new Image(GFX_DIR + "LifeBarFull.png");

    /**
     * Image of filled life bar in red (for enemies)
     */
    public static final Image LIFE_BAR_FULL_RED_IMG
            = new Image(GFX_DIR + "LifeBarFullRed.png");

    /**
     * Image of empty life bar
     */
    public static final Image LIFE_BAR_BLANK
            = new Image(GFX_DIR + "LifeBar.png");

    /**
     * Width of the life bar for enemies
     */
    public static final double LIFE_BAR_WIDTH = 170;

    /**
     * Height of the life bar for enemies
     */
    public static final double LIFE_BAR_HEIGHT = 15;

    //floor
    /**
     * Length of the side of square floor tile
     */
    public static final double FLR_SQR_SIZE = 20;

    /**
     * Image for floor tiles
     */
    public static final Image STONES_IMG = new Image(GFX_DIR + "tile.png");

    /**
     * Image for floor tiles that can be digged
     */
    public static final Image DIG_STONES_IMG
            = new Image(GFX_DIR + "diggingTile.png");

    //dog
    /**
     * Image of dog's attack
     */
    public static final Image ATTACK = new Image(GFX_DIR + "waves.png");

    public static final String DOG_IMG = IMAGE_DIR + "dogImg.txt";

    public static final double DOG_WIDTH = 36;
    public static final double DOG_HEIGHT = 24;

    public static final int DOG_STANDING_LINE = 5;
    public static final int DOG_WALKING_LINE = 2;

    /**
     * The main character will move up, if the specific key is pressed
     */
    public static final double JUMP = 10;

    /**
     * The amount on which the main character will move down, when he is not
     * standing on the ground
     */
    public static final double GRAVITATION = 1;

    /**
     * The amount on which the main character will move left or right, if the
     * specific key is pressed
     */
    public static final double MOVE_SPEED = 5;

    public static final double DOG_FORCE = 0.4;

    /**
     * The range of dog's attack on x coordinates
     */
    public static final double DOG_ATTACK_WIDTH = 90;

    /**
     * The range of dog's attack on y coordinates
     */
    public static final double DOG_ATTACK_HEIGHT
            = DOG_ATTACK_WIDTH * ATTACK.getHeight() / ATTACK.getWidth();

    /**
     * The value of maximum dog's health
     */
    public static final double DOG_LIFE = 100;

    /**
     * The value by which dog's health increases after using item that increases
     * health
     */
    public static double INCREASE_HEALTH = 10;

    /**
     * The value by which dog's health will decrease, if it falls
     */
    public static double LOSE_HEALTH = 30;

    //enemy
    /**
     * The image that appears above enemy after he "noticed" the main character
     */
    public static final Image ATTENTION_IMG = new Image(GFX_DIR + "!.png");

    /**
     * The images of all possible kinds of enemies
     */
    public static final String[] ENEMIES_IMG = {
        IMAGE_DIR + "batImg.txt",
        IMAGE_DIR + "slimeImg.txt"};

    /**
     * The width of all possible kinds of enemies
     */
    public static final double[] ENEMIES_WIDTH = {17, 25};

    /**
     * The height of all possible kinds of enemies
     */
    public static final double[] ENEMIES_HEIGHT = {21, 13};

    /**
     * The speed of all possible kinds of enemies
     */
    public static final double[] ENEMIES_SPEED = {0.6, 0.9};

    /**
     * The values by which enemies can decrease dog's life
     */
    public static final double[] ENEMIES_FORCE = {1.2, 0.5};

    /**
     * The value of health of all possible kinds of enemies
     */
    public static final double[] ENEMIES_LIFE = {20, 30};

    /**
     * Indicates half of the width of enemy's controlling territory
     */
    public static final double[] CONTR_TERR_WIDTH = {140, 100};

    /**
     * Indicates half of the height of enemy's controlling territory
     */
    public static final double[] CONTR_TERR_HEIGHT = {140, 0.0005};

    //items
    public static final Image KEY_IMG = new Image(GFX_DIR + "key.png");
    public static final Image BONE_IMG = new Image(GFX_DIR + "bone.png");
    public static final Image FOOD_IMG = new Image(GFX_DIR + "food.png");
    public static final WritableImage APPLE_IMG
            = new WritableImage(FOOD_IMG.getPixelReader(), 1, 0, 14, 16);
    public static final WritableImage PIZZA_IMG
            = new WritableImage(FOOD_IMG.getPixelReader(), 32, 0, 16, 16);
    public static final WritableImage ICE_CREAM_IMG
            = new WritableImage(FOOD_IMG.getPixelReader(), 51, 0, 12, 16);

    /**
     * The images of all possible kinds of items
     */
    public static final Image[] ITEMS_IMG = {
        KEY_IMG, PIZZA_IMG, BONE_IMG, APPLE_IMG, ICE_CREAM_IMG};

    /**
     * The width and height of the item
     */
    public static final double ITEM_SIZE = 20;

    /**
     * The specific function of the key
     */
    public static final String KEY_FUNC = "OPEN DOOR";

    /**
     * The specific function of "food" (items that can increase dog's health)
     */
    public static final String FOOD_FUNC = "INCREASE HEALTH";

    //inventory
    /**
     * The x coordinate of upper-left point of the first place, where item can
     * be in inventory
     */
    public static final double FIRST_ITEM_X = (int) (APP_WIDTH / 6) + 28;

    /**
     * The y coordinate of upper-left point of the first place, where item can
     * be in inventory
     */
    public static final double FIRST_ITEM_Y = 220;

    /**
     * The x coordinate of upper-left point of the last position, where item can
     * be in inventory
     */
    public static final double LAST_ITEM_X = 768;

    /**
     * The size of item's bounds in inventory
     */
    public static final double ITEM_BOUNDS_SIZE = 64;

    /**
     * The value by which cursor moving from one item to another
     */
    public static final double NEXT_ITEM = ITEM_BOUNDS_SIZE + 50;

    /**
     * The maximum amount of items in inventory
     */
    public static final int MAX_INVENTORY_LENGTH = 6;

    //elements
    /**
     * The specific image for the door
     */
    public static final Image DOOR_IMG = new Image(GFX_DIR + "doorAnim.png");
    public static final Image SAVE_POINT_IMG = new Image(GFX_DIR + "savePoint.png");

    /**
     * The images of all possible kinds of elements
     */
    public static final Image[] ELEMENTS_IMG = {DOOR_IMG, SAVE_POINT_IMG};

    /**
     * The values of width of all possible kinds of elements
     */
    public static final double[] ELEMENTS_WIDTH = {64, 34};

    /**
     * The values of height of all possible kinds of elements
     */
    public static final double[] ELEMENTS_HEIGHT = {81, 42};

    /**
     * The specific function for the door
     */
    public static final String DOOR_FUNC = "OPEN";

    /**
     * The specific function for the save point
     */
    public static final String SAVE_POINT_FUNC = "SAVE";

    //text and font
    public static final String RULES = "INSTRUCTIONS\n"
            + "[Left, right arrows] - Move\n"
            + "[SPACE] - Jump\n"
            + "[I] - Open inventory\n"
            + "[X] - Attack\n"
            + "[Z] - Use\n"
            + "[C] - Dig\n"
            + "[ENTER] - Begin game";
    public static final String GAME_OVER_TEXT = "GAME OVER\n"
            + "Press [ENTER] to restart\n";
    public static final String START_TEXT = "Press enter to start the game";

    /**
     * The messages that pops up, when item that increases health have been used
     */
    public static final String[] HEALTH_UP_MESSEGE = {
        "YOU ARE HEALTHIER NOW!",
        "THAT WAS DELICIOUS!"};

    /**
     * The messages that pops up, when player try to use an empty space in
     * inventory
     */
    public static final String[] NOTHING_MESSEGE = {
        "THERE IS NOTHING HERE",
        "YOU CAN'T USE EMPTINESS"
    };
    public static final String END_MESSAGE = "CONGRATULATIONS! YOU WON!\n"
            + "Start again by pressing [ENTER]";
    public static final String END_LVL_MESSAGE = "CONGRATULATIONS!\n"
            + "YOU COMPLETE THIS LEVEL!\n"
            + "Start next one by pressing [ENTER]";
    public static final Font FONT = Font.font(
            "Baskerville", FontPosture.REGULAR, 30);
    
}
