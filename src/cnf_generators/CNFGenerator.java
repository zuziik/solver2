package cnf_generators;

import cells.Cell;
import grids.Sudoku;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Stara neaktualna zalozna trieda s mnohymi funkciami na generovanie CNF. Zahodit, hned ako bude jasne, ze Decorator
 * Pattern je pouzity spravne a generatory funguju
 */
/* Pxyz <-> sudoku[x][y] == z <-> content[81*x + 9*y + z] == true */

public class CNFGenerator {
    String outputFile;
    Sudoku sudoku;
    //formula obsahuje true alebo false pre kazdu z 729 premennych
    //formulas[i] == true prave vtedy, ked sudoku[i/81][(i % 81)/9] == i % 9
    //interne ide o sudoku s R0-8, C0-8, cislami 0-8, len pri vypise sa prida +1
    //pozor na parne, resp. neparne sudoku (parne obsahuje "cisla" 1,3,5,7)
    ArrayList<ArrayList<Integer>> formulas;

    public CNFGenerator(String outputFile, Sudoku sudoku){
        this.outputFile = outputFile;
        this.sudoku = sudoku;
        this.formulas = new ArrayList<>();
    }

    public void generateCNF() throws IOException {
        generateClassic();
        generateGivens();
        printToFile();
    }

    /** Funkcia prida formuly pre kazde policko podla vpisiek, ktore obsahuje */
    private void generateGivens(){
        ArrayList<ArrayList<HashMap<Integer,Boolean>>> notes = this.sudoku.getPossibles();
        for ( int x = 0; x < 9; x++ ){
            for ( int y = 0; y < 9; y++ ) {
                HashMap<Integer,Boolean> map = notes.get(x).get(y);
                ArrayList<Integer> list = new ArrayList<>();
                for ( Integer k : map.keySet() ) {
                    if (map.get(k)){
                        list.add(variableNo(x, y, k));
                    }
                }
                formulas.add(list);
            }
        }
    }

    /** Funkcia vrati poziciu policka v zozname 729 premennych (1-729) pre CNF
     * Pozor, pri indexacii do pola (napriklad possibles) by sme chceli cislo o 1 mensie*/
    private int variableNo(int x, int y, int z){
        return 81*x + 9*y + z + 1;
    }

    /** Funkcia zabezpeci, ze kazde policko bude obsahovat nejake cislo 0-8 */
    private void min1Number(){
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
    private void max1Number(){
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

    /** Funkcia zabezpeci, ze cisla v riadkoch sa nebudu opakovat*/
    private void noRowRepetition(){
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
    private void noColRepetition(){
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

    /** Funkcia zabezpeci, ze cisla v stvorcoch 3x3 sa nebudu opakovat*/
    private void noBoxRepetition(){
        // pre vsetky x1, x2, y1, y2, z, (x1,y1)<>(x2,y2), x1/3 == x2/3, y1/3 == y2/3: ^Px1y1z v ^Px2y2z
        for ( int x1 = 0; x1 < 9; x1++ ) {
            for ( int x2 = 0; x2 < 9; x2++) {
                for ( int y1 = 0; y1 < 9; y1++) {
                    for ( int y2 = 0; y2 < 9; y2++) {
                        for ( int z = 0; z < 9; z++){
                            if ((x1/3 == x2/3) && (y1/3 == y2/3) && ((x1 != x2) || (y1 != y2))) {
                                ArrayList<Integer> list = new ArrayList<>();
                                list.add(-variableNo(x1, y1, z));
                                list.add(-variableNo(x2, y2, z));
                                formulas.add(list);
                            }
                        }
                    }
                }
            }
        }
    }

    /** Funkcia pre kazde dve policka zo zadaneho regionu zabezpeci, ze budu obsahovat rozne cisla.
     * Funkcia predpoklada, ze zadany region bude obsahovat kazde policko LEN RAZ.*/
    private void generateRegion(ArrayList<ArrayList<Integer>> cells){
        //pre vsetky x1, x2, y1, y2, z, (x1,y1)<>(x2,y2): ^Px1y1z v ^Px2y2z
        for (ArrayList<Integer> cell1 : cells){
            for (ArrayList<Integer> cell2 : cells){
                if (cell1 != cell2){
                    for (int z = 0; z < 9; z++){
                        ArrayList<Integer> list = new ArrayList<>();
                        list.add(-variableNo(cell1.get(0),cell1.get(1),z));
                        list.add(-variableNo(cell2.get(0),cell2.get(1),z));
                        formulas.add(list);
                    }
                }
            }
        }
    }

    /** Funkcia vygeneruje formuly splnajuce pravidla klasickeho sudoku*/
    private void generateClassic(){
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

    private void generateWindokuBox(int rowFrom, int rowTo, int colFrom, int colTo){
        ArrayList<ArrayList<Integer>> region = new ArrayList<>();
        for (int i = rowFrom; i <= rowTo; i++ ) {
            for (int j = colFrom; j < colTo; j++ ) {
                region.add(new ArrayList<>(Arrays.asList(i,j)));
            }
        }
        generateRegion(region);
    }

    private void generateWindoku(){
        generateClassic();
        generateWindokuBox(1,3,1,3);    //lavy horny
        generateWindokuBox(1,3,5,7);    //lavy dolny
        generateWindokuBox(5,7,1,3);    //pravy horny
        generateWindokuBox(5, 7, 5, 7);    //pravy dolny
    }

    private void generateDiagonal(){
        generateClassic();
        ArrayList<ArrayList<Integer>> region = new ArrayList<>();
        for (int i=0; i<9; i++){
            region.add(new ArrayList<>(Arrays.asList(i,i)));
        }
        generateRegion(region);
    }

    private void generateOdd(ArrayList<Cell> cells){
        generateClassic();
        for (Cell cell : cells){
            ArrayList<Integer> list = new ArrayList<>();
            for (int i = 0; i < 9; i+=2 ) {
                list.add(variableNo(cell.getRow(),cell.getCol(),i));
            }
            formulas.add(list);
        }
    }

    private void generateEven(ArrayList<Cell> cells){
        generateClassic();
        for (Cell cell : cells){
            ArrayList<Integer> list = new ArrayList<>();
            for (int i = 1; i < 9; i+=2 ) {
                list.add(variableNo(cell.getRow(),cell.getCol(),i));
            }
            formulas.add(list);
        }
    }

    private void generateUntouch(){
        generateClassic();
        for (int x = 0; x < 9; x++ ) {
            for (int y = 0; y < 9; y++ ) {
                if ( (x - 1 >= 0) && (y >= 0) ){
                    ArrayList<Integer> list = new ArrayList<>();
                    for (int i = 0; i < 9; i++ ) {
                        list.add(-variableNo(x,y,i));
                        list.add(-variableNo(x-1,y-1,i));
                    }
                    formulas.add(list);
                }
                if ( (x - 1 >= 0) && (y + 1 <= 8) ) {
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

    private void generateNC(){
        for ( int x = 0; x < 9; x++ ) {
            for ( int y = 0; y < 0; y++ ) {
                if ( x - 1 >= 0 ) {
                    for ( int z1 = 0; z1 < 9; z1++ ) {
                        if ( z1 - 1 >= 0 ) {
                            ArrayList<Integer> list = new ArrayList<>();
                            list.add(-variableNo(x,y,z1));
                            list.add(-variableNo(x-1,y,z1-1));
                            formulas.add(list);
                        }
                        if ( z1 + 1 < 9 ) {
                            ArrayList<Integer> list = new ArrayList<>();
                            list.add(-variableNo(x,y,z1));
                            list.add(-variableNo(x-1,y,z1+1));
                            formulas.add(list);
                        }
                    }
                }
                if ( y - 1 >= 0 ) {
                    for ( int z1 = 0; z1 < 9; z1++ ) {
                        if ( z1 - 1 >= 0 ) {
                            ArrayList<Integer> list = new ArrayList<>();
                            list.add(-variableNo(x,y,z1));
                            list.add(-variableNo(x,y-1,z1-1));
                            formulas.add(list);
                        }
                        if ( z1 + 1 < 9 ) {
                            ArrayList<Integer> list = new ArrayList<>();
                            list.add(-variableNo(x,y,z1));
                            list.add(-variableNo(x,y-1,z1+1));
                            formulas.add(list);
                        }
                    }
                }
            }
        }
    }

    private void generateAK(){
        //TODO
    }

    private void generateDisjointRegion(int i, int j) {
        ArrayList<ArrayList<Integer>> region = new ArrayList<>();
        for ( int x = 0; x < 3; x++ ) {
            for ( int y = 0; y < 3; y++ ) {
                region.add(new ArrayList<>(Arrays.asList(3*x+i,3*y+j)));
            }
        }
        generateRegion(region);
    }

    private void generateDisjointGroups() {
        for ( int i = 0; i < 3; i++ ) {
            for ( int j = 0; j < 3; j++ ) {
                generateDisjointRegion(i,j);
            }
        }
    }

    private void generateIrregular( ArrayList<ArrayList<Cell>> regions ) {
        for (ArrayList<Cell> group : regions){
            ArrayList<ArrayList<Integer>> region = new ArrayList<>();
            ArrayList<Integer> list = new ArrayList<>();
            for (Cell cell : group) {
                region.add(new ArrayList<>(Arrays.asList(cell.getRow(),cell.getRow())));
            }
            generateRegion(region);
        }
    }

    private void generateExtraRegion(ArrayList<ArrayList<Cell>> regions){
        generateClassic();
        for (ArrayList<Cell> group : regions){
            ArrayList<ArrayList<Integer>> region = new ArrayList<>();
            ArrayList<Integer> list = new ArrayList<>();
            for (Cell cell : group) {
                region.add(new ArrayList<>(Arrays.asList(cell.getRow(),cell.getRow())));
            }
            generateRegion(region);
        }
    }

    private String printFormula(ArrayList<Integer> f){
        StringBuffer s = new StringBuffer();
        for ( Integer i : f ) {
            s.append(i);
            s.append(' ');
        }
        s.append("0");
        return new String(s);
    }

    private void printToFile() throws IOException{
        try{
            PrintWriter out = new PrintWriter(new File(outputFile));
            out.println("p cnf 729 " + formulas.size());
            for (ArrayList<Integer> list : formulas){
                out.println(printFormula(list));
            }
            out.close();
        }
        catch(IOException e){
            System.err.println("File not found");
        }
    }
}
