/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;

import static main.Utils.*;
import ids.ItemId;
import ids.SpriteId;
import sprite.Dog;
import sprite.Element;
import sprite.Item;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author dariatunina
 */
public class Inventory {

    private final Map<Double, Item> inventory;
    private double currX;
    private double currY;
    private Rectangle2D itemBounds;
    private int numOfItems;
    private static final Logger LOG = Logger.getLogger(Inventory.class.getName());

    /**
     * Creates a new instance of Inventory
     */
    public Inventory() {
        inventory = new HashMap<>();
        currX = FIRST_ITEM_X;
        currY = FIRST_ITEM_Y;
        itemBounds = new Rectangle2D(currX, currY,
                ITEM_BOUNDS_SIZE, ITEM_BOUNDS_SIZE);
        numOfItems = 0;
    }

    /**
     *
     * @return the number of items in the inventory
     */
    public int getNumOfItems() {
        return numOfItems;
    }

    /**
     *
     * @return the x coordinate of upper-left corner of the cursor, that shows
     * current item
     */
    public double getCurrX() {
        return currX;
    }

    /**
     * Changes the x coordinate of upper-left corner of the cursor, that shows
     * current item.
     *
     * @param side value that set the direction
     */
    public void changeCurrX(int side) {
        if ((currX == FIRST_ITEM_X && side == 1)
                || (currX == LAST_ITEM_X && side == -1)
                || (currX > FIRST_ITEM_X && LAST_ITEM_X > currX)) {
            currX += side * NEXT_ITEM;
            itemBounds = new Rectangle2D(currX, currY,
                    ITEM_BOUNDS_SIZE, ITEM_BOUNDS_SIZE);
        }
    }

    /**
     * Adds item to the inventory
     *
     * @param item specific item to add to inventory
     * @return True if item has been added to inventory, false otherwise
     */
    public boolean addItem(Item item) {
        boolean ret = false;
        if (numOfItems <= MAX_INVENTORY_LENGTH) {
            numOfItems++;
            item.setX(NEXT_ITEM * (numOfItems - 1) + FIRST_ITEM_X);
            inventory.put(item.getX(), item);
            LOG.log(Level.INFO, "Inventory after adding new item. {0}", toString());
            ret = true;
        }
        return ret;
    }

    @Override
    public String toString() {
        if (numOfItems > 0) {
            String ret = "";
            for (double x = FIRST_ITEM_X;
                    x < numOfItems * NEXT_ITEM + FIRST_ITEM_X; x += NEXT_ITEM) {
                ret += inventory.get(x) + "\n";
            }
            return "Inventory has " + numOfItems + " items:\n" + ret;
        } else {
            return "Inventory is empty\n";
        }
    }

    /**
     *
     * @param x the x coordinate of upper-left corner of the item
     * @return required item
     */
    public Item getItem(double x) {
        return inventory.get(x);
    }

    /**
     *
     * @return the array of items, that are in the inventory
     */
    public Item[] getItems() {
        return inventory.values().toArray(new Item[inventory.size()]);
    }

    /**
     * Paints inventory
     *
     * @param gc graphic context of GameCanvas
     */
    public void render(GraphicsContext gc) {
        for (Item item : getItems()) {
            gc.drawImage(item.getImage(), item.getX() + 9, FIRST_ITEM_Y + 9,
                    ITEM_BOUNDS_SIZE - 18, ITEM_BOUNDS_SIZE - 18);
        }
        renderCurrBounds(gc);
    }

    /**
     * Remove specific item from the inventory. If there is a hole in the
     * inventory, items will be moved to hide it.
     *
     * @param itemToDelete item that should be deleted
     */
    public void removeItem(Item itemToDelete) {
        double itemX = itemToDelete.getX();
        LOG.log(Level.INFO, "Delete {0}", itemToDelete);
        numOfItems--;
        inventory.remove(itemX);
        for (double x = itemX; x <= LAST_ITEM_X; x += NEXT_ITEM) {
            if (inventory.get(x + NEXT_ITEM) != null) {
                Item item = inventory.get(x + NEXT_ITEM);
                inventory.remove(x + NEXT_ITEM);
                inventory.put(x, item);
                inventory.get(x).setX(x);
            } else {
                inventory.remove(x);
            }
        }
        LOG.log(Level.INFO, "Left: {0}", toString());

    }

    /**
     * Paints cursor, that is on the current item
     *
     * @param gc graphic context of GameCanvas
     */
    public void renderCurrBounds(GraphicsContext gc) {
        gc.setStroke(Color.DODGERBLUE);
        gc.strokeRect(itemBounds.getMinX(), itemBounds.getMinY(),
                itemBounds.getWidth(), itemBounds.getHeight());
    }

    /**
     * Set cursor to the first item in the inventory
     */
    public void reset() {
        currX = FIRST_ITEM_X;
        currY = FIRST_ITEM_Y;
        itemBounds = new Rectangle2D(currX, currY,
                ITEM_BOUNDS_SIZE, ITEM_BOUNDS_SIZE);
    }

    /**
     * Checks if current item can increase health of the dog. If it is, remove
     * it from the inventory and increases dog's health. Set a message that
     * depend on the success of using item.
     *
     * @param dog the main character
     * @return message that depends on the success of using item
     */
    public String useItem(Dog dog) {
        String ret;
        Item item = inventory.get(currX);
        if (item != null) {
            if (item.usableTest()) {
                removeItem(item);
                ret = HEALTH_UP_MESSEGE[new Random().nextInt(HEALTH_UP_MESSEGE.length)];
                if (DOG_LIFE >= INCREASE_HEALTH + dog.getLife()) {
                    dog.changeLife(INCREASE_HEALTH);
                } else {
                    dog.setLife(DOG_LIFE);
                }
            } else {
                ret = "CANNOT USE " + item.getSpecificId();
            }
        } else {
            ret = NOTHING_MESSEGE[new Random().nextInt(NOTHING_MESSEGE.length)];
        }
        return ret;
    }

    /**
     * Removes all items from inventory
     */
    public void empty() {
        for (double x = FIRST_ITEM_X;
                x <= numOfItems * NEXT_ITEM + FIRST_ITEM_X; x += NEXT_ITEM) {
            inventory.remove(x);
        }
        numOfItems = 0;
    }

    /**
     * Put names of all items in the inventory to the file
     *
     * @param fw the specific file to write to
     */
    public void writeInFile(FileWriter fw) {
        try {
            fw.write("Inventory\n");
            fw.write(String.valueOf(numOfItems) + "\n");
            for (double x = FIRST_ITEM_X;
                    x < numOfItems * NEXT_ITEM + FIRST_ITEM_X; x += NEXT_ITEM) {
                Item item = inventory.get(x);
                fw.write(item.getSpecificId() + "\n");
            }
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Goes through items in inventory. If dog have item that can be used with
     * the element, element do what his function says to do. Dog loses used
     * item.
     *
     * @param element element that dog tries to use
     * @return true - if item that can be used exist in inventory, false - if
     * doesn't
     */
    public boolean findItemForElement(Element element) {
        boolean found = false;
        for (Item item : getItems()) {
            LOG.log(Level.INFO, "Try use {0} that can {1} to {2} {3}", new Object[]{
                item.getSpecificId(), item.getFunc(), element.getFunc(), element.getSpecificId()
            });
            if (item.getFunc().equals(
                    element.getFunc() + " " + element.getSpecificId())) {
                element.changeImg("opened");
                removeItem(item);
                found = true;
                break;
            }
        }
        return found;
    }

    /**
     * Puts items which names are given in the file to the inventory
     *
     * @param sc the specific scanner to read information from
     */
    public void readFromFile(Scanner sc) {
        if (sc.hasNextLine() && sc.next().equals("Inventory")) {
            int num = sc.nextInt();
            for (int i = 0; i < num; ++i) {
                ItemId id = ItemId.valueOf(sc.next());
                addItem(new Item(SpriteId.ITEM, id.getImage(),
                        id.getWidth(), id.getHeight(), id, id.getFunc()));
            }
        }
        LOG.log(Level.INFO, "Inventory after items were read from file. {0}", toString());

    }

}
