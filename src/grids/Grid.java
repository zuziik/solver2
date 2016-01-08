package grids;

import cells.Cell;
import javafx.scene.layout.GridPane;
import GUI.Main;

/**
 * Created by Zuzka on 23.11.2015.
 */
public abstract class Grid extends GridPane{
    public Main start;
    private Cell[][] grid;

    public void createGrid(Cell[][] grid){
        //TODO: farbenie stvorcov by sa malo robit inak pre irregularne sudoku
        this.grid = grid;
        /*Nastavime polickam susedov*/
        for (int i=0; i<9; i++){
            for (int j=0; j<9; j++){
                Cell cell = grid[i][j];
                cell.setUp(grid[(i+8)%9][j]);
                cell.setDown(grid[(i+1)%9][j]);
                cell.setLeft(grid[i][(j+8)%9]);
                cell.setRight(grid[i][(j+1)%9]);
            }
        }

    }

    public Cell getXY(int x, int y){
        return grid[x][y];
    }

    public Cell[][] getGrid(){
        return this.grid;
    }

    public void setGrid(Cell[][] grid){
        this.grid = grid;
    }

    public void update(){
        filterText();
        for (Cell[] row : grid){
            for (Cell cell : row){
                cell.update();
            }
        }
    }

    public void showPencilmarks(){
        for (Cell[] row : grid){
            for (Cell cell : row){
                cell.showPencilmarks();
            }
        }
    }

    public void hidePencilmarks(){
        for (Cell[] row : grid){
            for (Cell cell : row){
                cell.hidePencilmarks();
            }
        }
    }

    public void filterText(){
    }
}
