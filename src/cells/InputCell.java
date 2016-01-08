package cells;
import GUI.*;
import grids.Grid;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


/**
 * Trieda reprezentuje policko vstupnej mriezky.
 */
public class InputCell extends Cell {
    InputCell cell = this;
    TextField text; //textove pole na zadavanie moznosti pre policko

    /** Konstruktor dostane mriezku, poziciu policka, jeho farbu a textove pole na zadavanie moznosti */
    public InputCell(Grid grid, int row, int col, Color color, TextField text) {
        super(grid, row, col, color);

        //oproti Cell nastavujeme este vstupne textove pole a naplnime ho cislami z Options
        this.text = text;
        this.setupText();
    }

    /** Funkcia nastavi vstupne policko moznostami z Options a nastavi jeho vyzor*/
    private void setupText(){
        text.setFont(new Font(10));
        //text.setText(this.getOptions().toString());
        //velkostou bude textove pole presne suhlasit s polickom, pricom bude mat priesvitne pozadie, teda neprekryje farbu
        text.setPrefWidth(this.getSize());
        text.setPrefHeight(this.getSize());
        text.setBackground(Background.EMPTY);
        text.setAlignment(Pos.CENTER);
        text.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                filterText();
            }
        });
        text.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                if (event.getCode().equals(KeyCode.DOWN)) {
                    cell.getDown().getTextField().requestFocus();
                    filterText();
                } else if (event.getCode().equals(KeyCode.UP)) {
                    cell.getUp().getTextField().requestFocus();
                    filterText();
                } else if (event.getCode().equals(KeyCode.LEFT)) {
                    cell.getLeft().getTextField().requestFocus();
                    filterText();
                } else if (event.getCode().equals(KeyCode.RIGHT)) {
                    cell.getRight().getTextField().requestFocus();
                    filterText();
                }
            }
        });
    }

    /** Funkcia prefiltruje vstupny text - do policka vpusti len cisla 1-9, kazde max. raz a vo vzostupnom poradi */
    @Override
    public void filterText(){
        StringBuffer s = new StringBuffer();
        for (Integer i = 1; i <= 9; i++) {
            if (text.getText().contains(i.toString())) {
                s.append(i.toString());
            }
        }
        if ((getGrid().start.getMode().equals(Mode.GIVENS)) && (s.length()) > 1){
            text.setText("");
        } else {
            text.setText(new String(s));
        }

    }

    /** Funkcia vrati prislusne vystupne policko k tomuto policku (policko z vystupnej mriezky na tej istej pozicii)*/
    @Override
    public Cell getMyOutCell(){
        return this.getGrid().start.outGrid.getXY(this.getRow(), this.getCol());
    }

    /** Funkcia zmeni Options policka podla obsahu textoveho pola*/
    @Override
    public void update(){
        if (this.getGrid().start.getMode().equals(Mode.PENCILMARKS)){
            if (this.text.getText().equals("")){
                this.getOptions().refresh();
            }
            else{
                this.getOptions().changeOptions(this.text.getText());
            }
        }
        else{
            this.getOptions().changeOptions(this.text.getText());
        }

    }

    /** Funkcia vrati true, ak ma policko len jedinu moznost*/
    public boolean onlyOption(){
        return this.getOptions().oneOption();
    }

    /** Funkcia vrati jedine cislo, ktore moze byt v policku, aj tam moze byt len jedina moznost - inak vracia null*/
    public Integer getOnlyOption(){
        return this.getOptions().getOnlyOption();
    }

    /** Funkcia vrati textovu reprezentaciu policka v tvare Input Cell at {x, y} : {options}*/
    @Override
    public String toString(){
        return "Input Cell at "+this.getRow()+", "+this.getCol()+": "+this.getOptions().toString();
    }

    /** Funkcia zmeni mod zobrazovania policka - zobrazia sa vsetky mozne cisla, ktore v nom mozu byt*/
    @Override
    public void showPencilmarks(){
        this.update();
        this.text.setText(this.getOptions().toString());
    }

    /** Funkcia zmeni mod zobrazovania policka - ak moze byt v policku viacero cisel, zostane prazdne*/
    @Override
    public void hidePencilmarks(){
        this.update();
        if (this.getOptions().oneOption()){
            this.text.setText(this.getOptions().getOnlyOption().toString());
        }
        else{
            this.text.setText("");
        }
    }

    /** Funkcia vrati vstupne textove pole policka. Pre OutputCell vracia null */
    @Override
    public TextField getTextField(){
        return this.text;
    }

    @Override
    public void insert(Integer number){
        this.getOptions().changeOptions(number.toString());
        text.setFont(new Font(19));
    }
}
