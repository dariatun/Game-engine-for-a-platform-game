/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lvlCreator;


/**
 *
 * @author dariatunina
 */
public class LvlCreatorLoopHandler {
    
    private LvlCreatorCanvas lvlCreatorCanvas;

    public LvlCreatorLoopHandler(LvlCreatorCanvas lvlCreatorCanvas) {
        this.lvlCreatorCanvas = lvlCreatorCanvas;
    }
    
    
    public void handle() {
        lvlCreatorCanvas.redraw();
    }
    
}
