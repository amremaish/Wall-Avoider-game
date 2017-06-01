/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import static game.Game.Height;
import static game.Game.Width;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import static game.Game.numInRow;
import static game.Game.numInCol;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Amr
 */
public class Cell extends Pane {

    private Rectangle rect;
    private int x, y;
    private Color Color;
    private boolean wall;
    private ImageView imageView;

    public Cell() {
        // Draw Rectangle
        rect = new Rectangle(Width / numInCol, Height / numInRow);
        rect.setStrokeWidth(1);
        rect.setStroke(Color.BLACK);
        rect.setFill(Color.BISQUE);
        getChildren().add(rect);

    }

    public void setImage(String Path) {
        Image image = new Image(Path);
        imageView = new ImageView(image);

        getChildren().add(imageView);

    }

    public ImageView getImage() {
        return imageView;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Color getColor() {
        return Color;
    }

    public void setColor(Color wallColor) {
        rect.setFill(wallColor);
        this.Color = wallColor;
    }

    public boolean isWall() {
        return wall;
    }

    public void setWall(boolean wall) {
        this.wall = wall;
    }

    public Rectangle getRect() {
        return rect;
    }

    public void setRect(Rectangle rect) {
        this.rect = rect;
    }

}
