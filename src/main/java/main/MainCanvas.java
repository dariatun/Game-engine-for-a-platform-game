/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import static main.Utils.APP_HEIGHT;
import static main.Utils.APP_WIDTH;

/**
 *
 * @author dariatunina
 */
public class MainCanvas extends Canvas {

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
    
    public void clearScreen() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, APP_WIDTH, APP_HEIGHT);
    }
}
