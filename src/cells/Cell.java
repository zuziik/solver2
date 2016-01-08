package cells;

import grids.Grid;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

/**
 * Trieda reprezentuje policko mriezky. Na reprezentaciu vstupneho, resp. vystupneho policka ju rozsiruju triedy Input
 * a Output Cell.
 */
public class Cell extends Rectangle {
    private int row;                //cislo riadku v mriezke (0-8)
    private int col;                //cislo stlpca v mriezke (0-8)
    private Grid grid;              //referencia na mriezku, v ktorej sa policko nachadza
    private Options options;        //moznosti pre toto policko - podmnozina 0-8 (ktore cisla sa tam mozu nachadzat)
    private final int size = 40;    //sirka policka pri vykreslovani
    private Color fill;             //vypln policka pri vykreslovani
    private Color outline = Color.BLACK;    //oramovanie policka pri vykreslovani
    /** Referencie na susedne policka, aby sa dal nastavit pohyb sipkami - sprava sa ako toroid*/
    private Cell up, down, left, right;

    /** Konstruktor dostane mriezku, poziciu v mriezke a farbu policka */
    public Cell(Grid grid, int row, int col, Color color){
        this.fill = color;

        //graficke parametre nastavujeme priamo obdlzniku
        super.setWidth(size);
        super.setHeight(size);
        super.setFill(fill);
        super.setStroke(outline);
        super.setStrokeWidth(0.5);

        //nastavenie mriezky, pozicie a moznosti pre cisla
        this.grid = grid;
        this.row = row;
        this.col = col;
        this.options = new Options();
    }

    /** Funkcia vrati sirku strany policka*/
    public int getSize(){
        return size;
    }

    /** Funkcia vrati (toroidneho) horneho suseda policka */
    public Cell getUp(){
        return this.up;
    }

    /** Funkcia nastavi policku horneho suseda */
    public void setUp(Cell up){
        this.up = up;
    }

    /** Funkcia vrati (toroidneho) dolneho suseda policka */
    public Cell getDown(){
        return this.down;
    }

    /** Funkcia nastavi policku dolneho suseda */
    public void setDown(Cell down){
        this.down = down;
    }
    /** Funkcia vrati (toroidneho) laveho suseda policka */
    public Cell getLeft(){
        return this.left;
    }

    /** Funkcia nastavi policku laveho suseda */
    public void setLeft(Cell left){
        this.left = left;
    }

    /** Funkcia vrati (toroidneho) praveho suseda policka */
    public Cell getRight(){
        return this.right;
    }

    /** Funkcia nastavi policku praveho suseda */
    public void setRight(Cell right){
        this.right = right;
    }

    /**
     * Funkcia vrati xovu poziciu policka v mriezke - cislo stlpca (0-8)
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Funkcia vrati ypsilonovu poziciu policka v mriezke - cislo riadku (0-8)
     */
    public int getCol() {
        return this.col;
    }

    /**
     * Funkcia nastavi policku mriezku
     */
    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    /**
     * Funkcia vrati odkaz na mriezku, ktorej patri policko
     */
    public Grid getGrid() {
        return this.grid;
    }

    public void setOptions(Options options) {
        this.options = options;
    }

    /**
     * Funkcia vrati zoznam vpisiek policka vo formate Options
     */
    public Options getOptions() {
        return this.options;
    }

    /**
     * Funkcia prida cislo i do moznosti policka Cell
     *
     * @param i
     */
    public void addOption(int i) {
        this.options.addOption(i);
    }

    /**
     * Funkcia vrati true, ak ma policko prave jednu moznost
     */
    public boolean oneOption() {
        return this.options.oneOption();
    }

    /** Funkcia vrati referenciu na vstupne policko k tomuto policku (t.j. samotne policko pre vstupne policko, resp.
     * policko na rovnakej pozicii vo vstupnej mriezke pre vystupne policko */
    public Cell getMyInCell(){
        return this;
    }

    /** Funkcia vrati referenciu na vystupne policko k tomuto policku (t.j. samotne policko pre vystupne policko, resp.
     * policko na rovnakej pozicii vo vystupnej mriezke pre vstupne policko */
    public Cell getMyOutCell(){
        return this;
    }

    /** Funkcia updatuje policko - prenesie cisla uvedene v textovom poli prislusneho policka do moznosti policka*/
    public void update(){
    }

    /** Funkcia zmeni mod zobrazovania policka - zobrazia sa vsetky mozne cisla, ktore v nom mozu byt*/
    public void showPencilmarks(){
    }

    /** Funkcia zmeni mod zobrazovania policka - ak moze byt v policku viacero cisel, zostane prazdne*/
    public void hidePencilmarks(){
    }

    /** Funkcia vrati vstupne textove pole policka. Pre OutputCell vracia null */
    public TextField getTextField(){
        return null;
    }

    /** Funkcia vrati textove pole vystupneho policka. Pre InputCell vracia null */
    public Label getLabel(){
        return null;
    }

    /** Funkcia prefiltruje vstupny text - do policka vpusti len cisla 1-9, kazde max. raz a vo vzostupnom poradi */
    public void filterText(){
    }

    /** Funkcia vlozi do policka cislo number */
    public void insert(Integer number){
    }
}
