/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import managers.SaveFileManager;
import managers.ItemsManager;
import managers.EnemyManager;
import managers.Floor;
import managers.ElementManager;
import ids.ElementId;
import ids.EnemyId;
import ids.SpriteId;
import ids.ItemId;
import static main.Utils.*;
import sprite.Dog;
import java.util.HashMap;
import java.util.Map;
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
    private UserActionHandler userActionHandler;
    private Map<GameVariables, Integer> gameVariables;
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
        gameVariables = new HashMap<>();
        floor = new Floor(STONES_IMG, LEVEL_DIR);
        fileMan = new SaveFileManager();
        gameCanvas = new GameCanvas(enemyManager, gameVariables, floor, dog,
                itemManager, elementManager);
        gameLoopHandler = new GameLoopHandler(gameCanvas, enemyManager,
                gameVariables, floor, dog, itemManager, elementManager, fileMan);
        userActionHandler = new UserActionHandler(gameVariables, dog);
    }

    @Override
    public void start(Stage stage) {
        BorderPane startPane = new BorderPane();
        StackPane mainPane = createMainPane(stage, startPane);
        setBackground(mainPane, startPane);
        setStartPane(startPane, mainPane, stage);
        
        Scene scene = createScene(stage, startPane);
        final KeyFrame oneFrame = new KeyFrame(
                Duration.millis(1000 / 40), 
                gameLoopHandler
        );
        final Timeline tl = new Timeline(oneFrame);
        tl.setCycleCount(Animation.INDEFINITE);
        tl.play();

        stage.show();
    }
    
    private VBox createMiddlePane(StackPane mainPane, Stage stage) {
        Button startButton = createStartButton(stage, mainPane);
        Button continueButton = createContinueButton(stage, mainPane);
        VBox middlePane = new VBox(15);
        middlePane.setAlignment(Pos.CENTER);
        middlePane.getChildren().addAll(startButton, continueButton);
        return middlePane;
    }

    private void setStartPane(BorderPane pane, StackPane mainPane, Stage stage) {
        VBox middlePane = createMiddlePane(mainPane, stage);
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

    private Button createStartButton(Stage stage, StackPane mainPane) {
        Button startButton = new Button();
        setButton(startButton, START_BUTTON_IMG);
        startButton.setOnAction(e -> {
            fileMan.deleteMainFile();
            stage.getScene().setRoot(mainPane);
            gameLoopHandler.setInitSprites();
            gameVariables.put(GameVariables.GAME_STARTED, 1);
        });
        return startButton;
    }

    private Button createContinueButton(Stage stage, StackPane mainPane) {
        Button continueButton = new Button();
        setButton(continueButton, CONT_BUTTON_IMG);
        continueButton.setOnAction(e -> {
            if (fileMan.currFileExist()) {
                gameLoopHandler.setInitSprites();
                fileMan.setCurrFile("main");
                gameVariables.put(GameVariables.GAME_STARTED, 1);
                stage.getScene().setRoot(mainPane);
            }
        });
        return continueButton;
    }

    private StackPane createMainPane(Stage stage, BorderPane startPane) {
        StackPane pane = new StackPane();
        Button exitBtn = createExitButton(stage, startPane);
        pane.getChildren().addAll(gameCanvas, exitBtn);
        gameCanvas.fixAspectRatio();
        return pane;
    }

    private Button createExitButton(Stage stage, BorderPane startPane) {
        Button button = new Button();
        setExitButton(button);
        button.setOnMouseClicked(e -> {
            fileMan.setCurrFile("temporary");
            gameLoopHandler.save_positions(dog.getCurrPos());
            gameVariables.put(GameVariables.GAME_STARTED, 0);
            stage.getScene().setRoot(startPane);
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
