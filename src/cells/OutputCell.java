package cells;

import GUI.Mode;
import grids.Grid;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Trieda reprezentuje policko vystupnej mriezky.
 */
public class OutputCell extends Cell {
    Label text;     //label na vypisovanie moznosti policka

    /** Konstruktor dostane mriezku, poziciu policka, jeho farbu a textove pole na vypisovanie moznosti */
    public OutputCell(Grid grid, int row, int col, Color color, Label text) {
        super(grid, row, col, color);
        this.text = text;
        this.setupText();
    }

    /** Funkcia nastavi vstupne policko moznostami z Options a nastavi jeho vyzor*/
    private void setupText(){
        text.setFont(new Font(10));
        //text.setText(this.getOptions().toString());
        text.setPrefWidth(this.getSize());
        text.setPrefHeight(this.getSize());
        text.setBackground(Background.EMPTY);
        text.setAlignment(Pos.CENTER);

    }

    /** Funkcia vrati referenciu na prislusne vstupne policko (policko vstupnej mriezky na rovnakej pozicii) */
    @Override
    public Cell getMyInCell(){
        return this.getGrid().start.inGrid.getXY(this.getRow(), this.getCol());
    }

    /** Funkcia upravi Options tohto policka a text vystupneho textoveho pola podla hodnot prislusneho vstupneho policka */
    @Override
    public void update(){
        System.out.println(getRow() + " " + getCol() + " " + this.getMyInCell().getOptions().toString());
        this.setOptions(this.getMyInCell().getOptions());
        if (getGrid().start.getMode().equals(Mode.PENCILMARKS)){
            if (oneOption()){
                this.text.setText(this.getOptions().toString());
            }
            else{
                this.text.setText("");
            }
        }
        else{
            this.text.setText(this.getOptions().toString());
        }
        if (this.oneOption()){
            text.setFont(new Font(19));
        }
        else{
            text.setFont(new Font(10));
        }
    }

    public void insert(Integer number){
        this.getOptions().changeOptions(number.toString());
        this.text.setFont(new Font(19));
        number++;
        this.text.setText(number.toString());
    }

    /** Funkcia vrati textovu reprezentaciu policka vo formate Output Cell at {x, y}: {options}*/
    @Override
    public String toString(){
        return "Output Cell at "+this.getRow()+", "+this.getCol()+": "+this.getOptions().toString();
    }

    /** Funkcia zmeni mod zobrazovania policka - zobrazia sa vsetky mozne cisla, ktore v nom mozu byt*/
    @Override
    public void showPencilmarks(){
        this.text.setText(this.getOptions().toString());
    }

    /** Funkcia zmeni mod zobrazovania policka - ak moze byt v policku viacero cisel, zostane prazdne*/
    @Override
    public void hidePencilmarks(){
        if (this.getOptions().oneOption()){
            this.text.setText(this.getOptions().getOnlyOption().toString());
        }
        else{
            this.text.setText("");
        }
    }

    /** Funkcia vrati textove pole vystupneho policka. Pre InputCell vracia null */
    @Override
    public Label getLabel(){
        return this.text;
    }

}
