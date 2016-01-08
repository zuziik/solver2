package cnf_generators;

import java.util.TreeSet;

/**
 * Created by Zuzka on 11.12.2015.
 */
public class Solution {
    String solution;    // riesenie ako vystup generatora

    public Solution(String solution){
        this.solution = solution;
    }

//    public Integer[] toArray() {
//
//    }

    public TreeSet<Integer> toSet() {
        TreeSet<Integer> set = new TreeSet<>();
        String[] array = solution.split(" ");
        for (int i = 2; i < array.length; i++ ) {
            set.add(Integer.parseInt(array[i]));
        }
        return set;
    }
}
