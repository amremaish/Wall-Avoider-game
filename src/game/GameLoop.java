/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import static game.Game.Height;
import static game.Game.OnPlaying;
import static game.Game.ScoreTxt;
import static game.Game.StartTxt;
import static game.Game.Width;
import java.util.Random;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javax.swing.JOptionPane;
import static game.Game.numInRow;
import static game.Game.numInCol;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Amr
 */
class GameLoop {

    private final float RectWidth = Width / numInCol;
    private final float RectHeight = Height / numInRow;
    private final int divNum = 25; // Smoothing move 

    private Cell MenuCell;
    private Pane root;
    private Scene Scene;
    //-----------------------------------
    public static Timeline GameLoopTime;
    private KeyFrame KeyFrame;
    private float millis = 4.5f;
    private boolean isEscape;
    private Cell wall[]; // Wall
    private Cell Player; // Player Object
    private boolean build; // wall already built
    private int YCount = 0; // count y of wall (changing)
    private float incVal = 1.0f / divNum;  // increasing value
    private float accumVal = 0;
    private int LastY = 0;
    private int scoreInt = 0;
    private int TextPadding = 0; // for put text in right place

    public GameLoop(Scene Scene, Pane root, Cell MenuCell) {
        wall = new Cell[numInCol];
        this.Scene = Scene;
        this.MenuCell = MenuCell;
        this.root = root;
        InializePlayer();
        GamePlay();

    }

    public void InializePlayer() {
        Player = new Cell();
        Player.setX(numInCol / 2);
        Player.setY(numInRow - 1);
        Player.setTranslateX((numInCol / 2) * (Width / numInCol));
        Player.setTranslateY((numInRow - 1) * (Height / numInRow));

        Player.setImage("res/plane.png");
        root.getChildren().add(Player);
        KeyAction();

    }

    private void KeyAction() {
        Scene.setOnKeyPressed((KeyEvent e) -> {
            KeyCode key = e.getCode();
            // ------- Key escape action
            if (!OnPlaying) {
                return; // if the game paused
            }
            if (key == KeyCode.ESCAPE) {
                if (isEscape) { // if your relase escape
                    root.setEffect(null);
                    GameLoopTime.play();
                    MenuCell.toBack();
                    OnPlaying = true;
                } else { // if your click escape
                    StartTxt.setText("  Resume ");
                    Game.OnResume = true;
                    root.setEffect(new BoxBlur(5, 5, 5));
                    MenuCell.toFront();
                    OnPlaying = false;
                    GameLoopTime.pause();
                }
                isEscape = !isEscape;
            }
            MenuCell.setOpacity(0.8); // set opacity 

            // Move left - right 
            if (key == KeyCode.RIGHT && Player.getX() < numInCol - 1) {
                Player.setX(Player.getX() + 1);
                Player.setTranslateX(Player.getTranslateX() + RectWidth);
            } else if (key == KeyCode.LEFT && Player.getX() > 0) {
                Player.setTranslateX(Player.getTranslateX() - RectWidth);
                Player.setX(Player.getX() - 1);
            }

        });
    }

    private void GamePlay() {
        GameLoopTime = new Timeline();
        GameLoopTime.setCycleCount(Animation.INDEFINITE);
        KeyFrame = new KeyFrame(Duration.millis(millis), (ActionEvent event) -> {
            if (YCount == numInRow + 1) { // if wall reach to the end
                for (int i = 0; i < wall.length; i++) {
                    if (wall[i] != null) {
                        root.getChildren().remove(wall[i]);
                    }
                }
                wall = new Cell[numInCol];
                build = false;
                YCount = 0;
                accumVal = 0;
                if (OnPlaying) {
                    scoreInt += 10;
                    ScoreTxt.setText(scoreInt + "");

                    if (scoreInt == 100 || scoreInt == 1000) { // for text Padding 
                        TextPadding += 20;
                    }
                    ScoreTxt.setTranslateX(220 + TextPadding - ScoreTxt.getLayoutBounds().getWidth());
                }
                if (scoreInt >= 800) { // Level 4
                    Stage stage = (Stage) Scene.getWindow();
                    stage.setTitle("Level 4");
                    GameLoopTime.stop();
                    GameLoopTime = null;
                    KeyFrame = null;
                    millis = 3.0f; // new Level Of speed 
                    GamePlay(); // recursive to start again with new value
                }

                if (scoreInt >= 500) { // Level 3
                    Stage stage = (Stage) Scene.getWindow();
                    stage.setTitle("Level 3");
                    GameLoopTime.stop();
                    GameLoopTime = null;
                    KeyFrame = null;
                    millis = 3.3f; // new Level Of speed 
                    GamePlay(); // recursive to start again with new value
                }

            }
            if (!build) {   // if Wall is disappeared
                BuildBooleanWall();
                build = true;
            }
            if (checkCollision()) {  // if Collision
                JOptionPane.showMessageDialog(null, "Your score is " + scoreInt);
                SaveScore();
                clear();

                reload();
                return;
            }

            // Accumulation Wall path
            for (int i = 0; i < wall.length; i++) {
                if (wall[i] != null) {
                    wall[i].setTranslateY(wall[i].getTranslateY() + (float) (RectHeight) / divNum);
                }
            }
            LastY = (int) accumVal;
            accumVal += incVal;
            if (LastY != (int) accumVal) {
                YCount++;
                if (!OnPlaying) {
                    AI();
                }
            }
        });
        GameLoopTime.getKeyFrames().add(KeyFrame);
        GameLoopTime.play();
    }

    private boolean checkCollision() {
        for (int i = 0; i < wall.length; i++) {
            if (wall[i] != null) {
                if (wall[i].getX() == Player.getX() && YCount + 1 == Player.getY()) {
                    return true;
                }
            }
        }
        return false;
    }

    private void AI() {
        int i;
        for (i = 0; i < wall.length; i++) {
            if (wall[i] == null) {
                break;
            }
        }
        final int avb = i;
        Timeline PlayerTime = new Timeline();
        PlayerTime.setCycleCount(Animation.INDEFINITE);
        KeyFrame AIKeyFrame = new KeyFrame(Duration.millis(200), (ActionEvent event) -> {
            if (!isEscape) {
                if (avb > Player.getX()) {
                    Player.setTranslateX(Player.getTranslateX() + RectWidth);
                    Player.setX(Player.getX() + 1);
                }
                if (avb < Player.getX()) {
                    Player.setTranslateX(Player.getTranslateX() - RectWidth);
                    Player.setX(Player.getX() - 1);
                }
                if (avb == Player.getX()) {
                    PlayerTime.stop();
                }
            }
        });
        PlayerTime.getKeyFrames().add(AIKeyFrame);
        PlayerTime.play();

    }

    private void BuildBooleanWall() {
        boolean list[] = {false, false, true, false};
        boolean take = false;
        for (int i = 0; i < wall.length; i++) {
            int rnd = new Random().nextInt(list.length);
            if ((list[rnd] || i == wall.length - 2) && take == false) {
                if (scoreInt < 300) {
                    if (OnPlaying) {
                        Stage stage = (Stage) Scene.getWindow();
                        stage.setTitle("Level 2");
                    }
                    i++; // Level 2
                }
                take = true;
            } else {
                wall[i] = new Cell();
                wall[i].setX(i);
                wall[i].setImage("res/brickwall.png");
                wall[i].setTranslateX(i * (Width / numInCol));
                root.getChildren().add(wall[i]);
            }

        }
    }

    public void restart() {
        for (int i = 0; i < wall.length; i++) {
            if (wall[i] != null) {
                root.getChildren().remove(wall[i]);
            }
        }
        wall = new Cell[numInCol];
        build = false;
        YCount = 0;
        accumVal = 0;
        ScoreTxt.setText("0");
    }

    private void reload() {
        try {
            Stage stage = (Stage) Scene.getWindow();
            stage.close();
            Platform.runLater(() -> {
                try {
                    new Game().start(new Stage());
                } catch (Exception ex) {
                    Logger.getLogger(GameLoop.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } catch (Exception ex) {
            Logger.getLogger(GameLoop.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void clear() {
        GameLoopTime.stop();
        GameLoopTime = null;
        this.KeyFrame = null;
        millis = 4.5f;
        isEscape = false;
        wall = null;
        Player = null;
        build = false;
        YCount = 0;
        accumVal = 0;
        LastY = 0;
        scoreInt = 0;
        TextPadding = 0;
        Game.OnResume = false;
        Game.ScoreTxt = null;
        Game.OnPlaying = false;
        MenuCell = null;
        root = null;
        GameLoop lp = this;
        lp = null; // clear 

    }

    public void SaveScore() {
        // Open Score 
        boolean exist = false;
        Score s1 = null;
        try {
            File file = new File("score.ser");
            if (file.exists()) {
                exist = true;
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
                s1 = (Score) in.readObject();
                in.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(ScoreBoard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ScoreBoard.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (!exist) {
            s1 = new Score();
        }
        util.Sort(s1.NameList, s1.ScoreList);
        //------------------------------------------------------
        if (s1.ScoreList.size() < 10) {
            String name = JOptionPane.showInputDialog(null, "What's your name?");
            if (name ==null || name.length() == 0) {
                name = "No name";
            }
            s1.NameList.add(name);
            s1.ScoreList.add(scoreInt);
        } else {
            for (int i = 0; i < s1.ScoreList.size(); i++) {
                if (s1.ScoreList.get(i) < scoreInt) {
                    String name = JOptionPane.showInputDialog(null, "What's your name?");
                    if (name ==null || name.length() == 0) {
                        name = "No name";
                    }
                    s1.NameList.add(name);
                    s1.ScoreList.add(scoreInt);
                    break;
                }
            }
        }

        // ---------- save
        FileOutputStream fout = null;
        try {
            fout = new FileOutputStream("score.ser");
            ObjectOutputStream out = new ObjectOutputStream(fout);
            out.writeObject(s1);
            out.flush();
            out.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ScoreBoard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ScoreBoard.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fout.close();
            } catch (IOException ex) {
                Logger.getLogger(ScoreBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
