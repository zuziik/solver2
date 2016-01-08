package cnf_generators;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Trieda reprezentuje Disjoint Groups sudoku: Cisla 1-9 sa nesmu opakovat na rovnakych relativnych poziciach v ramci
 * mensich 3x3 stvorcov
 */
public class DisjointGroups extends VariantGenerator {

    public DisjointGroups(Generator wrapped){
        super(wrapped);
    }

    /** Funkcia vygeneruje formuly pre disjoint region na zadanej relativnej pozicii R i, C j*/
    private void generateDisjointRegion(int i, int j) {
        ArrayList<ArrayList<Integer>> region = new ArrayList<>();
        for ( int x = 0; x < 3; x++ ) {
            for ( int y = 0; y < 3; y++ ) {
                region.add(new ArrayList<>(Arrays.asList(3 * x + i, 3 * y + j)));
            }
        }
        generateRegion(region);
    }

    /** Funkcia vygeneruje CNF pre podmienku Disjoint Groups sudoku: Cisla na rovnakych relativnych poziciach v ramci
     * stvorcov 3x3 sa neopakuju */
    private void generateDisjointGroups() {
        for ( int i = 0; i < 3; i++ ) {
            for ( int j = 0; j < 3; j++ ) {
                generateDisjointRegion(i,j);
            }
        }
    }

    /** Funkcia vygeneruje CNF zabalenej varianty a prida svoje podmienky */
    @Override
    public void generateCNF(){
        super.generateCNF();
        generateDisjointGroups();
    }

}
