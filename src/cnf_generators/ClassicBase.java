package cnf_generators;

import grids.Sudoku;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Trieda predstavuje jadro klasickeho sudoku: Cisla sa nesmu opakovat v riadkoch, stlpcoch ani deviatich stvorcoch 3x3
 */
public class ClassicBase extends BaseGenerator {

    public ClassicBase(Sudoku sudoku) {
        super(sudoku);
    }

    @Override
    /** Funkcia vygeneruje formuly splnajuce pravidla klasickeho sudoku*/
    public void generateBase() {
        // kazde policko obsahuje aspon jedno cislo
        min1Number();
        // kazde policko obsahuje maximalne jedno cislo
        max1Number();
        // ziadne dve cisla v riadku sa neopakuju
        noRowRepetition();
        // ziadne dve cisla v stlpci sa neopakuju
        noColRepetition();
        // ziadne dve cisla v stvorci 3x3 sa neopakuju
        noBoxRepetition();
    }

    /** Funkcia zabezpeci, ze cisla v stvorcoch 3x3 sa nebudu opakovat*/
    private void noBoxRepetition(){
        // pre vsetky x1, x2, y1, y2, z, (x1,y1)<>(x2,y2), x1/3 == x2/3, y1/3 == y2/3: ^Px1y1z v ^Px2y2z
        for ( int i = 0; i < 3; i++ ) {
            for ( int j = 0; j < 3; j++ ) {
                ArrayList<ArrayList<Integer>> region = new ArrayList<>();
                for ( int x = 0; x < 3; x++ ) {
                    for ( int y = 0; y < 3; y++ ) {
                        region.add(new ArrayList<>(Arrays.asList(3*i+x, 3*j+y)));
                    }
                }
                generateRegion(region);
            }
        }
    }

}
