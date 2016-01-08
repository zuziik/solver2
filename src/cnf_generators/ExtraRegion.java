package cnf_generators;

import cells.Cell;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Trieda reprezentuje sudoku s Extra Regionmi: Cisla sa nesmu opakovat ani vo vyznacenych skupinach policok (nemusi ich
 * byt nutne 9, ani regionov, ani policok v regionoch)
 */
public class ExtraRegion extends VariantGenerator {
    /** zoznamy extra regionov - kazdy z nich predstavuje zoznam policok, v ktorych sa nemaju opakovat cisla */
    private ArrayList<ArrayList<Cell>> regions;

    public ExtraRegion(Generator wrapped, ArrayList<ArrayList<Cell>> regions){
        super(wrapped);
        this.regions = regions;
    }

    /** Funkcia vygeneruje CNF pre extra regionalne sudoku: cisla sa neopakuju ani vo vyznacenych regionoch */
    private void generateExtraRegion(){
        for (ArrayList<Cell> group : regions){
            ArrayList<ArrayList<Integer>> region = new ArrayList<>();
            ArrayList<Integer> list = new ArrayList<>();
            for (Cell cell : group) {
                region.add(new ArrayList<>(Arrays.asList(cell.getRow(), cell.getRow())));
            }
            generateRegion(region);
        }
    }

    /** Funkcia vygeneruje CNF zabalenej varianty a prida svoje podmienky */
    @Override
    public void generateCNF(){
        super.generateCNF();
        generateExtraRegion();
    }
}
