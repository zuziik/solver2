package cnf_generators;

import grids.Sudoku;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

/**
 * Trieda reprezentuje Generator formul v CNF ako vstup do SAT solvera. Podla roznych typov sudoku a ich kombinacii
 * existuje viacero potomkov - kazdy z nich pridava k celkovym formulam svoje podmienky.
 */
public abstract class Generator {
    String outputFile = "files/formulas.txt";
    //formula obsahuje true alebo false pre kazdu z 729 premennych
    //formulas[i] == true prave vtedy, ked sudoku[i/81][(i % 81)/9] == i % 9
    //interne ide o sudoku s R0-8, C0-8, cislami 0-8, len pri vypise sa prida +1
    //pozor na parne, resp. neparne sudoku (parne obsahuje "cisla" 1,3,5,7)
    ArrayList<ArrayList<Integer>> formulas = new ArrayList<>();

    /** Nastavenia pred generovanim */
    private String inputFile = "files/formulas.txt";               // vstupny subor s CNF vo formate DIMACS
    private int timeLimit = 5;                  // casovy limit, ako dlho ma SAT solver bezat
    private char mode = '1'; //sposob generovania: 1 = lubovolne 1 riesenie, a = vsetky riesenia, c = pocet rieseni
    //TODO pouzivatel si moze sam nastavit svoj casovy limit (kolko je ochotny cakat na vystup generatora)
    private ArrayList<String> SAToutput = new ArrayList<>();    // cely vystup generatora
    private boolean timeLimitExpired;
    public Generator(){
    }

    /** Funkcia vygeneruje CNF podla typu sudoku - generatora a prida ich do zoznamu formulas */
    public abstract void generateCNF();

    /** Funkcia pre kazde dve policka zo zadaneho regionu zabezpeci, ze budu obsahovat rozne cisla.
     * Funkcia predpoklada, ze zadany region bude obsahovat kazde policko LEN RAZ.*/
    void generateRegion(ArrayList<ArrayList<Integer>> cells){
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



    /** Funkcia vrati poziciu policka v zozname 729 premennych (1-729) pre CNF
     * Pozor, pri indexacii do pola (napriklad possibles) by sme chceli cislo o 1 mensie*/
    int variableNo(int x, int y, int z){
        return 81*x + 9*y + z + 1;
    }

    /** Funkcia vrati textovu reprezentaciu jednej formuly v normovanom tvare: pre kazdu z premennych vypise kladne
     * alebo zaporne cislo podla toho, ci sa premenna vo formule nachadza v pozitivnom alebo negovanom zmysle a na
     * koniec formuly zapise 0 */
    String printFormula(ArrayList<Integer> f){
        StringBuffer s = new StringBuffer();
        for ( Integer i : f ) {
            s.append(i);
            s.append(' ');
        }
        s.append("0");
        return new String(s);
    }

    /** Funkcia vrati zoznam formul pre aktualne sudoku */
    public ArrayList<ArrayList<Integer>> getCNFFormulas() {
        return this.formulas;
    }

    /** Funkcia vypise vsetky formuly do suboru, ktory je vstupom pre SAT solver */
    public void printToFile() throws IOException{
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

    /** Funkcia vygeneruje CNF pre aktualne sudoku a vytvori subor, do ktoreho ich vypise */
    public void createFileWithCNF() throws IOException{
        this.generateCNF();
        this.printToFile();
        // len docasna ladiaca hlaska
        System.out.println("CNF generated");
    }

    private void generate() {
        try {
            /** Vytvorenie a spustenie procesu pre generovanie CNF pomocou SAT solvera relsat */
            Process p = Runtime.getRuntime().exec("cmd /C relsat.exe" + " -#" + mode + " -t" + timeLimit + " " + inputFile);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));

            /** Vypis vystupu SAT solvera na vystup a ulozenie do premennej SAToutput*/
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
                if (line.charAt(0) != 'c'){
                    SAToutput.add(line);
                }
            }
            this.timeLimitExpired = SAToutput.get(SAToutput.size()-1).equals("TIME LIMIT EXPIRED");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Funkcia vypise vysledok SAT solvera do suboru */
    public void printToFile(String outputFile){
        try{
            PrintWriter out = new PrintWriter(new FileOutputStream(new File(outputFile)));
            for (String line : SAToutput){
                out.println(line);
            }
            out.close();
        } catch (FileNotFoundException e) {
            System.err.println("Could not export CNF formulas: File not found!");
        }
    }

    /** Funkcia vrati true, ak su vygenerovane CNF splnitelne, teda ak existuje riesenie */
    public Boolean isSatisfiable(){
        this.mode = '1';
        generate();
        String result = SAToutput.get(SAToutput.size()-1);
        if (result.equals("SAT")) {
            return new Boolean(true);
        }
        else if (result.equals("UNSAT")){
            return new Boolean(false);
        }
        return null;
    }

    /** Funkcia vrati lubovolne riesenie ulohy alebo null, ak ziadne neexistuje */
    public Solution getOneSolution() {
        this.mode = '1';
        generate();
        String sol = SAToutput.get(0);
        return new Solution(sol);
    }

    public ArrayList<Solution> getAllSolutions() {
        this.mode = 'a';
        generate();
        ArrayList<Solution> allSolutions = new ArrayList<>();
        for (String solution : SAToutput ) {
            allSolutions.add(new Solution(solution));
        }
        return allSolutions;
    }

    /** Funkcia vrati pocet rieseni ulohy */
    public int getSolutionCount(){
        this.mode = 'c';
        generate();
        String solCount = SAToutput.get(0);
        String[] array = solCount.split(" ");
        if (timeLimitExpired) return 1000;
        return Integer.parseInt(array[array.length-1]);
    }

    /** Funkcia vrati vsetky cisla a moznosti, ktore su pripustne pre zadanie, ak ma max 1000 rieseni, inak vrati null */
    public TreeSet<Integer>[][] getProgress(){
        if (this.getSolutionCount() > 1000){
            return null;
        }
        TreeSet<Integer>[][] progress = new TreeSet[9][9];
        for ( int i = 0; i < 9; i++ ) {
            for ( int j = 0; j < 9; j++ ) {
                progress[i][j] = new TreeSet<>();
            }
        }

        return progress;
    }

    public void setMode(char mode) {
        this.mode = mode;
    }

    public char getMode() {
        return this.mode;
    }
}
