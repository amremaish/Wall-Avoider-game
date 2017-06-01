/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Amr
 */
public class util {
       public static void Sort(ArrayList<String> NameList, ArrayList<Integer> ScoreList) {
        for (int i = 0; i < ScoreList.size(); i++) {
            int idx = i;
            for (int j = i; j < ScoreList.size(); j++) {
                if (ScoreList.get(j) > ScoreList.get(idx)) {
                    idx = j;
                }
            }
            Collections.swap(ScoreList, idx, i);
            Collections.swap(NameList, idx, i);
        }
    }
}
