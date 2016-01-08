package cnf_generators;

import grids.Sudoku;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Trieda reprezentuje jadro ulohy, ktore definuje zakladne podmienky (napriklad neopakovanie cisel v riadkoch a stlpcoch),
 * ktore mozu byt neskor rozsirovane obalovanim do inych typov variant.
 * Trieda obsahuje funkcie, ktore mozu potrebovat viacere bazy - napriklad ze v kazdom policku ma byt prave jedno cislo
 */
public abstract class BaseGenerator extends Generator {
    Sudoku sudoku;

    public BaseGenerator(Sudoku sudoku){
        super();
        this.sudoku = sudoku;
    }

    /** Funkcia vygeneruje zakladne CNF z bazy a zahrnie CNF podla aktualne zadanych cisel */
    @Override
    public void generateCNF() {
        generateBase();
        generateGivens();
    }

    /** Funkcia vygeneruje zakladne CNF pre konkretnu bazu */
    public abstract void generateBase();

    /** Funkcia prida formuly pre kazde policko podla vpisiek, ktore obsahuje */
    void generateGivens(){
        ArrayList<ArrayList<HashMap<Integer,Boolean>>> notes = this.sudoku.getPossibles();
        System.out.println(notes.size()+" "+notes.get(0).get(0).size());
        for ( int x = 0; x < 9; x++ ){
            for ( int y = 0; y < 9; y++ ) {
                HashMap<Integer,Boolean> map = notes.get(x).get(y);
                ArrayList<Integer> list = new ArrayList<>();
                for ( Integer k : map.keySet() ) {
                    if (map.get(k)){
                        list.add(variableNo(x, y, k));
                    }
                }
                System.out.println("*"+list);
                if (list.size() > 0) formulas.add(list);
            }
        }
    }

    /** Funkcia zabezpeci, ze cisla v riadkoch sa nebudu opakovat*/
    void noRowRepetition(){
        // pre vsetky x, y1, y2, z, y1<>y2: ^Pxy1z v ^Pxy2z
        for ( int x = 0; x < 9; x++ ) {
            for ( int z = 0; z < 9; z++ ) {
                for ( int y1 = 0; y1 < 9; y1++ ) {
                    for ( int y2 = y1+1; y2 < 9; y2++ ) {
                        ArrayList<Integer> list = new ArrayList<>();
                        list.add(-variableNo(x, y1, z));
                        list.add(-variableNo(x, y2, z));
                        formulas.add(list);
                    }
                }

            }
        }
    }

    /** Funkcia zabezpeci, ze cisla v stlpcoch sa nebudu opakovat*/
    void noColRepetition(){
        // pre vsetky x1, x2, y, z, x1<>x2: ^Px1yz v ^Px2yz
        for ( int x1 = 0; x1 < 9; x1++ ) {
            for ( int x2 = x1+1; x2 < 9; x2++ ) {
                for ( int y = 0; y < 9; y++ ) {
                    for ( int z = 0; z < 9; z++ ) {
                        ArrayList<Integer> list = new ArrayList<>();
                        list.add(-variableNo(x1, y, z));
                        list.add(-variableNo(x2, y, z));
                        formulas.add(list);
                    }
                }

            }
        }
    }

    /** Funkcia zabezpeci, ze kazde policko bude obsahovat nejake cislo 0-8 */
    void min1Number(){
        // pre vsetky x, y: Pxy1 v Pxy2 v ... v Pxy9
        for ( int x = 0; x < 9; x++ ) {
            for ( int y = 0; y < 9; y++ ) {
                ArrayList<Integer> list = new ArrayList<>();
                for ( int z = 0; z < 9; z++ ) {
                    list.add(variableNo(x, y, z));
                }
                formulas.add(list);
            }
        }
    }

    /** Funkcia zabezpeci, ze kazde policko bude obsahovat maximalne jedno cislo */
    void max1Number(){
        // pre vsetky x, y, z1, z2, z1<>z2: ^Pxzy1 v ^Pxzy2
        for ( int x = 0; x < 9; x++ ) {
            for ( int y = 0; y < 9; y++ ) {
                for ( int z1 = 0; z1 < 9; z1++ ) {
                    for ( int z2 = z1+1; z2 < 9; z2++ ) {
                        ArrayList<Integer> list = new ArrayList<>();
                        list.add(-variableNo(x, y, z1));
                        list.add(-variableNo(x, y, z2));
                        formulas.add(list);
                    }
                }

            }
        }
    }


}
