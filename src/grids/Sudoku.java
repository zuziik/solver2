package grids;

import cells.Cell;
import cells.Options;
import cnf_generators.CNFGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

/**
 * Created by Zuzka on 17.11.2015.
 */
public class Sudoku {
    HashMap<Variants,Boolean> types;
    // pre kazdy riadok, stlpec a cislo 0-8 budeme udrziavat, ci to cislo moze byt na prislusnej pozicii
    ArrayList<ArrayList<HashMap<Integer,Boolean>>> possibles;
    CNFGenerator generator = new CNFGenerator("files/cnf1.txt",this);


    public Sudoku(HashMap<Variants,Boolean> types){
        this.types = types;
        initContent();
    }

    /** Konstruktor vytvori sudoku k mriezke Grid */
    public Sudoku(InputGrid grid){
        fillPossibles(grid);
    }

    /* Najjednoduchsie sudoku */
    public Sudoku(){
        this.types = new HashMap<Variants,Boolean>();
        for (Variants s : Variants.values()){
            types.put(s,false);
        }
        initContent();
    }

    public ArrayList<ArrayList<HashMap<Integer,Boolean>>> getPossibles(){
        return this.possibles;
    }

    /** Funkcia inicializuje sudoku - v kazdom policku mozu byt vsetky cisla 0-8 */
    private void initContent(){
        initPossibles();
        for (int i=0; i<9; i++){
            for (int j=0; j<9; j++){
                for (int k=0; k<9; k++){
                    this.possibles.get(i).get(j).put(k,true);
                }
            }
        }
    }

    private void initPossibles(){
        this.possibles = new ArrayList<>(9);
        for (int i=0; i<9; i++) this.possibles.add(new ArrayList<>(9));
        for (int i=0; i<9; i++){
            for (int j=0; j<9; j++){
                this.possibles.get(i).add(new HashMap<>());
            }
        }
    }

    /** Funkcia zapise cislo z na poziciu x, y a zrusi vsetky ostatne moznosti*/
    public void setXYZ(int xx, int yy, int zz){
        int x = xx-1;
        int y = yy-1;
        int z = zz-1;
        HashMap<Integer,Boolean> map = possibles.get(x).get(y);
        for (Integer i : map.keySet()){
            if (i == z){
                map.replace(i,false,true);
            }
            else{
                map.replace(i,true,false);
            }
        }
    }

    public boolean onlyOption(int x, int y){
        return possibles.get(x).get(y).size() == 1;
    }

    public Integer getGiven(int x, int y){
        if (onlyOption(x,y)){
            for (int i=0; i<9; i++){
                if (possibles.get(x).get(y).get(i)){
                    return i;
                }
            }
        }
        return null;
    }

    /** Funkcia odstrani cislo z z moznosti pre policko x, y*/
    public void remove(int x, int y, int z){
        possibles.get(x).get(y).put(z,false);
    }

    /** Funkcia prida cislo z do moznosti pre policko x, y*/
    public void add(int x, int y, int z){
        possibles.get(x).get(y).put(z,true);
        System.out.println("possibles: "+possibles.get(x).get(y).size());
    }

    /** Funkcia prekonvertuje stav sudoku (ktore policko moze obsahovat ktore cisla) do jedneho pola 729 booleanov */
    public Boolean[] gridToArray(){
        Boolean array[] = new Boolean[9*9*9];
        int x = 0;
        int y = 0;
        for (ArrayList<HashMap<Integer,Boolean>> row : possibles){
            for (HashMap<Integer,Boolean> col : row){
                for (Integer key : col.keySet()){
                    array[81*x+9*y+key] = col.get(key);
                }
                y++;
            }
            x++;
        }
        return array;
    }

    public void fillPossibles(InputGrid grid){
        initPossibles();
        for (int i=0; i<9; i++){
            for (int j=0; j<9; j++){
                Cell cell = grid.getGrid()[i][j];
                for (Integer x : cell.getOptions().getOptions()){
                    add(i,j,x);
                    System.out.println("Filling possibles: "+i+j+x);
                }
            }
        }
    }

    public void generate() throws IOException {
        this.generator.generateCNF();
    }



    /* nastavit varianty zaskrtnutim radioboxov
    * prepojit s mriezkou v aplikacii a reagovat na to, co sa do nej zapise
    * */
}
