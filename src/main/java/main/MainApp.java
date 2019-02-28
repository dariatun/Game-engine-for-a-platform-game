/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import game.*;
import managers.*;
import ids.*;
import static main.Utils.*;
import sprite.Dog;
import java.util.HashMap;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;
import javafx.util.Duration;
import lvlCreator.*;
import static main.Variables.*;

/**
 *
 * @author dariatunina
 */
public class MainApp extends Application {

    private GameLoopHandler gameLoopHandler;
    private EnemyManager enemyManager;
    private ItemsManager itemManager;
    private ElementManager elementManager;
    private GameCanvas gameCanvas;
    private LvlCreatorCanvas lvlCreatorCanvas;
    private MainLoopHandler mainLoopHandler;
    private LvlCreatorLoopHandler lvlCreatorLoopHandler;
    private UserActionHandlerInGame userActionHandler;
    private HashMap<Variables, Boolean> mainVariables;
    private SaveFileManager fileMan;
    private Floor floor;
    private Dog dog;

    @Override
    public void init() {
        dog = new Dog(SpriteId.DOG, DOG_IMG, DOG_WIDTH,
                DOG_HEIGHT, DOG_LIFE, DOG_FORCE, LEVEL_DIR);
        enemyManager = new EnemyManager(EnemyId.class, "enemies.txt");
        itemManager = new ItemsManager(ItemId.class, "items.txt");
        elementManager = new ElementManager(ElementId.class, "elements.txt");
        mainVariables = new HashMap<>();
        mainVariables.put(LVL_CREATOR, false);
        floor = new Floor(STONES_IMG, LEVEL_DIR);
        fileMan = new SaveFileManager();
        gameCanvas = new GameCanvas(enemyManager, mainVariables, floor, dog,
                itemManager, elementManager);
        gameLoopHandler = new GameLoopHandler(gameCanvas, enemyManager,
                mainVariables, floor, dog, itemManager, elementManager, fileMan);
        lvlCreatorCanvas = new LvlCreatorCanvas();
        lvlCreatorLoopHandler = new LvlCreatorLoopHandler(lvlCreatorCanvas);
        mainLoopHandler = new MainLoopHandler(
                mainVariables, gameLoopHandler, lvlCreatorLoopHandler);
        userActionHandler = new UserActionHandlerInGame(mainVariables, dog);
    }

    @Override
    public void start(Stage stage) {
        BorderPane startPane = new BorderPane();
        StackPane mainPane = createPane(stage, startPane, gameCanvas);
        StackPane lvlCreatorPane = createPane(stage, startPane, lvlCreatorCanvas);
        setBackground(mainPane, startPane);
        setStartPane(startPane, mainPane, lvlCreatorPane, stage);

        Scene scene = createScene(stage, startPane);
        final KeyFrame oneFrame = new KeyFrame(
                Duration.millis(1000 / 40),
                mainLoopHandler
        );
        final Timeline tl = new Timeline(oneFrame);
        tl.setCycleCount(Animation.INDEFINITE);
        tl.play();

        stage.show();
    }

    private StackPane createPane(Stage stage,
            BorderPane startPane, MainCanvas canvas) {
        StackPane pane = new StackPane();
        Button exitBtn = createExitButton(stage, startPane);
        pane.getChildren().addAll(canvas, exitBtn);
        canvas.fixAspectRatio();
        return pane;
    }

    private VBox createMiddlePane(StackPane mainPane,
            StackPane lvlCreatorPane, Stage stage) {
        Button startButton = createStartButton(stage, mainPane);
        Button continueButton = createContinueButton(stage, mainPane);
       // Button lvlCreatorButton = createLvlCreatorButton(stage, lvlCreatorPane);
        VBox middlePane = new VBox(15);
        middlePane.setAlignment(Pos.CENTER);
        middlePane.getChildren().addAll(
                startButton, continueButton);
        return middlePane;
    }

    private void setStartPane(BorderPane pane, StackPane mainPane,
            StackPane lvlCreatorPane, Stage stage) {
        VBox middlePane = createMiddlePane(mainPane, lvlCreatorPane, stage);
        Text downText = createDownText();
        pane.setCenter(middlePane);
        pane.setBottom(downText);
        BorderPane.setAlignment(downText, Pos.BOTTOM_CENTER);
    }

    private Text createDownText() {
        Text text = new Text("By Daria Tunina");
        text.setFont(FONT);
        text.setBoundsType(TextBoundsType.LOGICAL_VERTICAL_CENTER);
        text.setTextAlignment(TextAlignment.CENTER);
        return text;
    }

    private Scene createScene(Stage stage, BorderPane startPane) {
        Scene scene = new Scene(startPane, APP_WIDTH, APP_HEIGHT);
        stage.setScene(scene);
        stage.setResizable(false);
        scene.setOnKeyPressed(userActionHandler);
        scene.setOnKeyReleased(userActionHandler);
        return scene;
    }

    private Button createLvlCreatorButton(Stage stage, StackPane pane) {
        Button button = new Button();
        setButton(button, START_BUTTON_IMG);
        button.setOnAction(e -> {
            stage.getScene().setRoot(pane);
            mainVariables.put(LVL_CREATOR, true);
        });
        return button;
    }

    private Button createStartButton(Stage stage, StackPane pane) {
        Button button = new Button();
        setButton(button, START_BUTTON_IMG);
        button.setOnAction(e -> {
            fileMan.deleteMainFile();
            stage.getScene().setRoot(pane);
            gameLoopHandler.setInitSprites();
            mainVariables.put(Variables.GAME_STARTED, true);
        });
        return button;
    }

    private Button createContinueButton(Stage stage, StackPane pane) {
        Button button = new Button();
        setButton(button, CONT_BUTTON_IMG);
        button.setOnAction(e -> {
            if (fileMan.currFileExist()) {
                gameLoopHandler.setInitSprites();
                fileMan.setCurrFile("main");
                mainVariables.put(Variables.GAME_STARTED, true);
                stage.getScene().setRoot(pane);
            }
        });
        return button;
    }

    private Button createExitButton(Stage stage, BorderPane startPane) {
        Button button = new Button();
        setExitButton(button);
        button.setOnMouseClicked(e -> {
            if (mainVariables.get(LVL_CREATOR)) {
                
            } else {
                fileMan.setCurrFile("temporary");
                gameLoopHandler.save_positions(dog.getCurrPos());
                mainVariables.put(Variables.GAME_STARTED, false);
                stage.getScene().setRoot(startPane);
            }
        });
        return button;
    }

    private void setExitButton(Button button) {
        ImageView exitIV = new ImageView(GFX_DIR + EXIT_IMG);
        exitIV.setFitWidth(10);
        exitIV.setFitHeight(10);
        button.setGraphic(exitIV);
        button.setTranslateX(APP_WIDTH / 2 - 20);
        button.setTranslateY(-APP_HEIGHT / 2 + 20);
        button.setCancelButton(true);
        button.setFocusTraversable(false);
    }

    private void setButton(Button button, String img) {
        ImageView buttonIV = new ImageView(GFX_DIR + img);
        buttonIV.setFitWidth(200);
        buttonIV.setFitHeight(70);
        button.setGraphic(buttonIV);
        button.setBackground(Background.EMPTY);
    }

    private void setBackground(Pane... panes) {
        BackgroundImage bgImg = new BackgroundImage(
                new Image(GFX_DIR + BACKGROUND_IMG,
                        APP_WIDTH, APP_HEIGHT, false, true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        for (Pane pane : panes) {
            pane.setBackground(new Background(bgImg));
        }
    }

    /**
     * Main function of the project
     *
     * @param args All arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
