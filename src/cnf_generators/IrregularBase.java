package cnf_generators;

import cells.Cell;
import grids.Sudoku;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Trieda reprezentuje Irregularne jadro: Cisla sa nesmu opakovat v riadkoch, stlpcoch a deviatich nepravidelnych
 * oblastiach
 */
public class IrregularBase extends BaseGenerator {
    /** zoznam regionov - zoznamov policok, v ktorom sa cisla nesmu opakovat */
    ArrayList<ArrayList< Cell >> regions;

    public IrregularBase(Sudoku sudoku, ArrayList<ArrayList<Cell>> regions) {
        super(sudoku);
        this.regions = regions;
    }

    /** Funkcia vygeneruje formuly splnajuce pravidla irregularneho sudoku*/
    @Override
    public void generateBase() {
        for (ArrayList<Cell> group : regions){
            ArrayList<ArrayList<Integer>> region = new ArrayList<>();
            ArrayList<Integer> list = new ArrayList<>();
            for (Cell cell : group) {
                region.add(new ArrayList<>(Arrays.asList(cell.getRow(), cell.getRow())));
            }
            generateRegion(region);
        }
    }
}
