/*
 * Wall Avoider
 * Created By Amr Emaish 1/6/2017
 * 3rd Compuer Science Student 
 * 01017029540
 * Egypt
 */
package game;

import static game.GameLoop.GameLoopTime;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.effect.BoxBlur;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import static javafx.application.Application.launch;

/**
 *
 * @author Amr
 */
public class Game extends Application {

    public static int Width = 400;
    public static int Height = 690;
    public static int numInCol = 8;
    public static int numInRow = 13;
    public static boolean OnResume;
    public static boolean OnPlaying;
    public static Cell cell[][];
    private Cell MenuCell;
    public static GameLoop game;
    public static Text StartTxt, Endtxt, ScoreTxt;
    public Pane root, GameRoot, ScorePane;

    private Parent PiantGrid() {
        GameRoot = new Pane();
        GameRoot.setEffect(new BoxBlur(5, 5, 5));
        root = new Pane();
        final int ROW = (int) numInCol;
        final int COL = (int) numInRow;
        cell = new Cell[ROW][COL];
        GameRoot.setPrefSize(Width, Height);
        for (int i = 0; i < (int) numInCol; i++) {
            for (int j = 0; j < (int) numInRow; j++) {
                cell[i][j] = new Cell();
                cell[i][j].setX(i);
                cell[i][j].setY(j);
                cell[i][j].setTranslateX(i * (Width / numInCol));
                cell[i][j].setTranslateY(j * (Height / numInRow));
                GameRoot.getChildren().add(cell[i][j]);
            }
        }
        root.getChildren().add(GameRoot);
        return root;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Scene Scene = new Scene(PiantGrid(), Width, Height);
        stage.setScene(Scene);
        stage.toFront();
        stage.setTitle("Level 0");
        stage.show();
        // add menu Cell 
        Menu menu = new Menu(root, GameRoot);
        menu.addMenuCell();
        // Score 
        ScoreSection();

        game = new GameLoop(Scene, GameRoot, menu);

    }

    public void ScoreSection() {
        ScorePane = new Pane();
        //---------------------- text
        Text TxtScore = new Text("Your Score ");
        TxtScore.setTranslateX(100);
        TxtScore.setTranslateY(80);
        TxtScore.setFill(Color.web("#fac178"));
        addEffect(TxtScore);
        TxtScore.setFont(Font.font(null, FontWeight.BOLD, 40));
        //----------------------- actual socre 
        ScoreTxt = new Text("0");
        System.out.println();
        ScoreTxt.setTranslateX(200 - ScoreTxt.getLayoutBounds().getWidth());
        ScoreTxt.setTranslateY(150);
        ScoreTxt.setFill(Color.web("#fac178"));
        addEffect(ScoreTxt);
        ScoreTxt.setFont(Font.font(null, FontWeight.BOLD, 40));
        ScorePane.getChildren().addAll(TxtScore, ScoreTxt);
        GameRoot.getChildren().add(ScorePane);
    }

    public void addEffect(Text text) {
        Blend blend = new Blend();
        blend.setMode(BlendMode.MULTIPLY);
        DropShadow ds = new DropShadow();
        ds.setColor(Color.web("#AD6800"));
        ds.setOffsetX(6);
        ds.setOffsetY(5);
        ds.setRadius(10);
        ds.setSpread(.59);

        blend.setBottomInput(ds);

        DropShadow ds1 = new DropShadow();
        ds1.setColor(Color.web("#fac178"));
        ds1.setRadius(20);
        ds1.setSpread(0.2);

        Blend blend2 = new Blend();
        blend2.setMode(BlendMode.MULTIPLY);

        InnerShadow is = new InnerShadow();
        is.setColor(Color.web("#a85f00"));
        is.setRadius(20);
        is.setChoke(0.8);
        blend2.setBottomInput(is);

        InnerShadow is1 = new InnerShadow();
        is1.setColor(Color.web("#a85f00"));
        is1.setRadius(5);
        is1.setChoke(0.4);
        blend2.setTopInput(is1);

        Blend blend1 = new Blend();
        blend1.setMode(BlendMode.MULTIPLY);
        blend1.setBottomInput(ds1);
        blend1.setTopInput(blend2);

        blend.setTopInput(blend1);

        text.setEffect(blend);
    }

    public static void main(String[] args) {
        launch();
    }

}
