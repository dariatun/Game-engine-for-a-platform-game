/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lvlCreator;

import javafx.scene.canvas.GraphicsContext;
import main.MainCanvas;

/**
 *
 * @author dariatunina
 */
public class LvlCreatorCanvas extends MainCanvas {
    
    public void redraw() {
        GraphicsContext gc = getGraphicsContext2D();
        clearScreen();
        //field.draw(gc);
    }
}
