package cnf_generators;

import java.util.ArrayList;

/**
 * Trieda reprezentuje Bezdotykove sudoku: Ziadne dve rovnake cisla nesmu byt v polickach, ktore susedia rohmi
 */
public class Untouchable extends VariantGenerator {
    public Untouchable(Generator wrapped){
        super(wrapped);
    }

    /** Funkcia vygeneruje CNF pre UT sudoku:  Ziadne dve rovnake cisla nesmu byt v polickach, ktore susedia rohmi */
    private void generateUntouchable(){
        for (int x = 1; x < 9; x++ ) {
            for (int y = 0; y < 9; y++ ) {
                if ( y - 1 >= 0 ){
                    ArrayList<Integer> list = new ArrayList<>();
                    for (int i = 0; i < 9; i++ ) {
                        list.add(-variableNo(x,y,i));
                        list.add(-variableNo(x-1,y-1,i));
                    }
                    formulas.add(list);
                }
                if ( y + 1 <= 8 ) {
                    ArrayList<Integer> list = new ArrayList<>();
                    for (int i = 0; i < 9; i++ ) {
                        list.add(-variableNo(x,y,i));
                        list.add(-variableNo(x-1,y+1,i));
                    }
                    formulas.add(list);
                }
            }
        }
    }

    /** Funkcia vygeneruje CNF zabalenej varianty a prida svoje podmienky */
    @Override
    public void generateCNF(){
        super.generateCNF();
        generateUntouchable();
    }
}
