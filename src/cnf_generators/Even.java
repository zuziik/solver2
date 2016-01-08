package cnf_generators;

import cells.Cell;

import java.util.ArrayList;

/**
 * Trieda reprezentuje parne sudoku - v oznacenych polickach sa smu nachadzat len parne cisla.
 */
public class Even extends VariantGenerator {
    /** zoznam policok, v ktorych sa maju nachadzat parne cisla */
    private ArrayList<Cell> cells;

    public Even( Generator wrapped, ArrayList<Cell> cells ) {
        super(wrapped);
        this.cells = cells;
    }

    /** Funkcia vygeneruje CNF pre parne sudoku: pre kazde policko zo zoznamu cells zabezpeci, ze moze obsahovat
     * len parne cisla */
    private void generateEven(){
        for (Cell cell : cells){
            ArrayList<Integer> list = new ArrayList<>();
            for (int i = 1; i < 9; i += 2 ) {
                list.add(variableNo(cell.getRow(),cell.getCol(),i));
            }
            formulas.add(list);
        }
    }

    /** Funkcia vygeneruje CNF zabalenej varianty a prida svoje podmienky */
    @Override
    public void generateCNF(){
        super.generateCNF();
        generateEven();
    }
}
