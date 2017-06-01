/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Amr
 */
public class Score implements Serializable {

    ArrayList<Integer> ScoreList = new ArrayList<>();
    ArrayList<String> NameList = new ArrayList<>();

    public ArrayList<Integer> getScoreList() {
        return ScoreList;
    }

    public void setScoreList(ArrayList<Integer> ScoreList) {
        this.ScoreList = ScoreList;
    }

    public ArrayList<String> getNameList() {
        return NameList;
    }

    public void setNameList(ArrayList<String> NameList) {
        this.NameList = NameList;
    }

}
