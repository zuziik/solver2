package grids;

import GUI.Mode;
import cells.Cell;
import cells.InputCell;
import javafx.scene.paint.Color;
import GUI.Main;
import javafx.scene.control.TextField;


/**
 * Created by Zuzka on 23.11.2015.
 */
public class InputGrid extends Grid  {
    private InputCell[][] grid;
    private TextField[][] texts;

    public InputGrid(Main start){
        this.start = start;
        grid = new InputCell[9][9];
        texts = new TextField[9][9];
        createGrid(grid);
    }

    @Override
    public void createGrid(Cell[][] grid){
        for (int i=0; i<9; i++){
            for (int j=0; j<9; j++){
                //parne boxy budu biele, neparne bledoruzove
                Color col = (((i / 3) + (j / 3)) % 2 == 0) ? Color.WHITE : Color.ANTIQUEWHITE;
                texts[i][j] = new TextField();
                grid[i][j] = new InputCell(this,i,j,col,texts[i][j]);

                //POZOR: moja reprezentacia je grid[x][y] je x.riadok a y.stlpec, ale zobrazuje sa to naopak!!
                super.add(grid[i][j], j, i);    //??
                super.add(texts[i][j],j,i);     //do gridu to davame naopak?
            }
        }
        super.createGrid(grid);
    }

    public void setTexts(TextField[][] texts){
        this.texts = texts;
    }

    public TextField[][] getTexts(){
        return this.texts;
    }


    public String toFile(Mode mode){
        if (mode.equals(Mode.PENCILMARKS)){
            return toFileWithPencilmarks();
        }
        else{
            return toFileWithGivens();
        }
    }

    private String toFileWithPencilmarks(){
        StringBuffer s = new StringBuffer();
        s.append("PENCILMARKS\n");
        for (int i=0; i<9; i++){
            for (int j=0; j<9; j++){
                s.append(grid[i][j].getOptions().toString()+"\n");
            }
        }
        return new String(s);
    }

    private String toFileWithGivens(){
        StringBuffer s = new StringBuffer();
        s.append("GIVENS\n");
        for (int i=0; i<9; i++){
            for (int j=0; j<9; j++){
                if (grid[i][j].oneOption()){
                    s.append(grid[i][j].getOnlyOption());
                }
                else{
                    s.append(".");
                }
            }
            s.append("\n");
        }
        return new String(s);
    }

    @Override
    public void filterText(){
        for (Cell[] row : grid){
            for (Cell cell : row){
                cell.filterText();
            }
        }
    }

}
