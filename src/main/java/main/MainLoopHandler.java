/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import game.GameLoopHandler;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.event.Event;
import javafx.event.EventHandler;
import lvlCreator.LvlCreatorLoopHandler;
import static main.Variables.LVL_CREATOR;


/**
 *
 * @author dariatunina
 */
public class MainLoopHandler implements EventHandler {
    
    private HashMap<Variables, Boolean> mainVariables;
    private GameLoopHandler gameLoopHandler;
    private LvlCreatorLoopHandler lvlCreatorLoopHandler;

    public MainLoopHandler(HashMap<Variables, Boolean> mainVariables,
            GameLoopHandler gameLoopHandler,
            LvlCreatorLoopHandler lvlCreatorLoopHandler) {
        this.mainVariables = mainVariables;
        this.gameLoopHandler = gameLoopHandler;
        this.lvlCreatorLoopHandler = lvlCreatorLoopHandler;
    }
    
    
    @Override
    public void handle(Event event) {
        if (mainVariables.get(LVL_CREATOR)) {
            lvlCreatorLoopHandler.handle();
            
        } else {
            gameLoopHandler.handle();
        }
    }
    
    
}
