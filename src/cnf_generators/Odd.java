package cnf_generators;

import cells.Cell;

import java.util.ArrayList;

/**
 * Trieda reprezentuje neparne sudoku - v oznacenych polickach sa smu nachadzat len neparne cisla.
 */
public class Odd extends VariantGenerator{
    /** zoznam policok, v ktorych sa maju nachadzat neparne cisla */
    private ArrayList<Cell> cells;

    public Odd( Generator wrapped, ArrayList<Cell> cells ) {
        super(wrapped);
        this.cells = cells;
    }

    /** Funkcia vygeneruje CNF pre neparne sudoku: pre kazde policko zo zoznamu cells zabezpeci, ze moze obsahovat
     * len neparne cisla */
    private void generateOdd(){
        for (Cell cell : cells){
            ArrayList<Integer> list = new ArrayList<>();
            for (int i = 0; i < 9; i+=2 ) {
                list.add(variableNo(cell.getRow(),cell.getCol(),i));
            }
            formulas.add(list);
        }
    }

    /** Funkcia vygeneruje CNF zabalenej varianty a prida svoje podmienky */
    @Override
    public void generateCNF(){
        super.generateCNF();
        generateOdd();
    }
}
