package cnf_generators;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Trieda reprezentuje Diagonalne sudoku: Kazda z hlavnych diagonal (z laveho horneho do praveho dolneho rohu a z laveho
 * dolneho do praveho horneho rohu) obsahuje kazde z cisel 1-9 presne raz
 */
public class Diagonal extends VariantGenerator {
    public Diagonal(Generator wrapped){
        super(wrapped);
    }

    /** Funkcia prida do formul podmienku Diagonalneho sudoku: Kazda z hlavnych diagonal obsahuje cisla 1-9 presne raz*/
    private void generateDiagonal(){
        ArrayList<ArrayList<Integer>> region = new ArrayList<>();
        for (int i=0; i<9; i++){
            region.add(new ArrayList<>(Arrays.asList(i, i)));
        }
        generateRegion(region);
    }

    /** Funkcia vygeneruje CNF zabalenej varianty a prida svoje podmienky */
    @Override
    public void generateCNF(){
        super.generateCNF();
        generateDiagonal();
    }
}
