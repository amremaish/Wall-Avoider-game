/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import static game.Game.Endtxt;
import static game.Game.Height;
import static game.Game.OnPlaying;
import static game.Game.OnResume;
import static game.Game.StartTxt;
import static game.Game.Width;
import static game.GameLoop.GameLoopTime;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author Amr
 */
public class Menu extends Cell {

    private Pane GameRoot;
    private Pane root;

    public Menu(Pane root, Pane GameRoot) {
        this.root = root;
        this.GameRoot = GameRoot;
        CreatedText();
    }

    public void addMenuCell() {
        // ------------- menu -------
        toBack();
        setOpacity(.8);
        setTranslateX(25);
        setTranslateY(100);
        getRect().setWidth(Width - 50);
        getRect().setHeight(Height - 200);
        setImage("res/control.png");
        getImage().setY(390);
        setColor(Color.BURLYWOOD);
        // ---------------------------------
        StartButton(90, 50);
        EndButton(90, 150);
        ScoreboardButton(90, 250);
        root.getChildren().addAll(this);

    }

    public void StartButton(int x, int y) {
        CustomButton start = new CustomButton(x, y);
        StartTxt = new Text("Start Game");
        StartTxt.setFont(Font.font(null, FontWeight.BOLD, 20));
        StartTxt.setTranslateX(x + 40);
        StartTxt.setTranslateY(y + (start.getHeight() / 2.0) + 5);
        getChildren().addAll(start, StartTxt);
        start.setOnMouseClicked(e -> {
            if (OnResume) {
                toBack();
                OnPlaying = true;
                GameLoopTime.play();
                OnResume = false;
            } else {
                toBack();
                Game.game.restart();
                OnPlaying = true;
            }
            GameRoot.setEffect(null);
        });

    }

    public void EndButton(int x, int y) {
        CustomButton start = new CustomButton(x, y);
        Endtxt = new Text("End Game");
        Endtxt.setFont(Font.font(null, FontWeight.BOLD, 20));
        Endtxt.setTranslateX(x + 40);
        Endtxt.setTranslateY(y + (start.getHeight() / 2.0) + 5);
        getChildren().addAll(start, Endtxt);
        Endtxt.setOnMouseClicked(e -> {
            System.exit(0);
        });

    }

    public void ScoreboardButton(int x, int y) {
        CustomButton start = new CustomButton(x, y);
        Endtxt = new Text("Scoreboard");
        Endtxt.setFont(Font.font(null, FontWeight.BOLD, 20));
        Endtxt.setTranslateX(x + 30);
        Endtxt.setTranslateY(y + (start.getHeight() / 2.0) + 5);
        getChildren().addAll(start, Endtxt);
        Endtxt.setOnMouseClicked(e -> {
            root.getChildren().remove(this);
            root.getChildren().add(new ScoreBoard(this, root));
        });
    }

    private void CreatedText() {
        Text CreatedTxt = new Text("Created By Amr Emaish");
        CreatedTxt.setTranslateX(70);
        CreatedTxt.setTranslateY(root.getScene().getHeight() - 150);
        CreatedTxt.setFont(Font.font(null, FontWeight.BOLD, 18));
        this.getChildren().add(CreatedTxt);
    }
}
