/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 *
 * @author Amr
 */
public class CustomButton extends Rectangle {

    private Color MouseEnteredColor = Color.web("#DBA901"), MouseExitColor = Color.web("#B45F04");

    private Pane root;
 

    public CustomButton(int x, int y) {
        this.root = root;
        InializeBtn();
        this.setTranslateX(x);
        this.setTranslateY(y);

    }

    public CustomButton() {
        InializeBtn();
    }

    private void InializeBtn() {
     
        this.setWidth(170);
        this.setHeight(50);
        this.setStrokeWidth(1);
        this.setStroke(Color.BLACK);
        this.setFill(MouseExitColor);
        MouseHoverAction();
    }

    private void MouseHoverAction() {

        this.setOnMouseEntered((Event event) -> {
            this.setFill(MouseEnteredColor);
        });
        this.setOnMouseExited((Event event) -> {
            this.setFill(MouseExitColor);
        });

    }

    public Color getMouseEnteredColor() {
        return MouseEnteredColor;
    }

    public void setMouseEnteredColor(Color MouseEnteredColor) {
        this.MouseEnteredColor = MouseEnteredColor;
    }

    public Color getMouseExitColor() {
        return MouseExitColor;
    }

    public void setMouseExitColor(Color MouseExitColor) {
        this.MouseExitColor = MouseExitColor;
    }


}
