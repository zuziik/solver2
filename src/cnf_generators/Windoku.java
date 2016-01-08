package cnf_generators;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Trieda reprezentuje Windoku: cisla sa nesmu opakovat ani v 4 specialne vyznacenych stvorcoch 3x3
 */
public class Windoku extends VariantGenerator {

    public Windoku( Generator wrapped ) {
        super(wrapped);
    }

    /** Funkcia vygeneruje CNF pre jeden windoku box so zadanym rozmedzim riadkov a stlpcov */
    private void generateWindokuBox(int rowFrom, int rowTo, int colFrom, int colTo){
        ArrayList<ArrayList<Integer>> region = new ArrayList<>();
        for (int i = rowFrom; i <= rowTo; i++ ) {
            for (int j = colFrom; j < colTo; j++ ) {
                region.add(new ArrayList<>(Arrays.asList(i, j)));
            }
        }
        generateRegion(region);
    }

    /** Funkcia prida CNF pre windoku: cisla sa nesmu opakovat ani v 4 specialne vyznacenych stvorcoch 3x3 */
    private void generateWindoku(){
        generateWindokuBox(1,3,1,3);    //lavy horny
        generateWindokuBox(1,3,5,7);    //lavy dolny
        generateWindokuBox(5,7,1,3);    //pravy horny
        generateWindokuBox(5, 7, 5, 7);    //pravy dolny
    }

    /** Funkcia vygeneruje CNF zabalenej varianty a prida svoje podmienky */
    @Override
    public void generateCNF() {
        super.generateCNF();
        generateWindoku();
    }
}
