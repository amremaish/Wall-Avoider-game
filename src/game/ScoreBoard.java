/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import static game.Game.Height;
import static game.Game.StartTxt;
import static game.Game.Width;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author Amr
 */
public class ScoreBoard extends Cell {

    private int startPos = 80;
    private final int gap = 40;
    private Menu menu;
    private Pane root;

    public ScoreBoard(Menu menu, Pane root) {
        this.root = root;
        this.menu = menu;
        toBack();
        setOpacity(.8);
        setTranslateX(25);
        setTranslateY(100);
        getRect().setWidth(Width - 50);
        getRect().setHeight(Height - 200);
        setColor(Color.BURLYWOOD);
        addScoreText();
        OpenAndAddScore();
        BackButton(85, 500);
    }

    public void addScoreText() {
        Text TxtScore = new Text("ScoreBoard");
        TxtScore.setTranslateX(115);
        TxtScore.setTranslateY(30);
        // TxtScore.setFill(Color.web("#B45F04"));
        TxtScore.setFont(Font.font(null, FontWeight.BOLD, 20));
        this.getChildren().add(TxtScore);
    }

    private void AddRowTxt(int Y, String name, int Score) {
        Text NameTxt = new Text(name);
        NameTxt.setTranslateX(50);
        NameTxt.setTranslateY(Y);
        NameTxt.setFill(Color.web("#61210B"));
        NameTxt.setFont(Font.font(null, FontWeight.BOLD, 20));
        Text TxtScore = new Text(Score + "");

        TxtScore.setTranslateX(250);
        TxtScore.setTranslateY(Y);
        TxtScore.setFill(Color.web("#644A01"));
        TxtScore.setFont(Font.font(null, FontWeight.BOLD, 20));
        this.getChildren().addAll(TxtScore, NameTxt);
    }

    public void BackButton(int x, int y) {
        CustomButton start = new CustomButton(x, y);
        StartTxt = new Text("    Back  ");
        StartTxt.setFont(Font.font(null, FontWeight.BOLD, 20));
        StartTxt.setTranslateX(x + 40);
        StartTxt.setTranslateY(y + (start.getHeight() / 2.0) + 5);
        getChildren().addAll(start, StartTxt);
        start.setOnMouseClicked(e -> {
            root.getChildren().remove(this);
            root.getChildren().add(menu);
        });

    }

    public void OpenAndAddScore() {
        Score s1 = null;
        try {
            File file = new File("score.ser");
            if (!file.exists()) {
                return;
            }
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
            s1 = (Score) in.readObject();
        } catch (IOException ex) {
            Logger.getLogger(ScoreBoard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ScoreBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        util.Sort(s1.NameList, s1.ScoreList);

        for (int i = 0; i < s1.NameList.size() && i < 10; i++) {
            AddRowTxt(startPos, s1.NameList.get(i), s1.ScoreList.get(i));
            startPos += gap;
        }
    }

}
