package GUI;

import javafx.scene.control.*;

/**
 * Created by Zuzka on 1.12.2015.
 */
public class ToolBar extends javafx.scene.control.MenuBar {
    Main start;
    Menu menu;
    MenuItem save;
    MenuItem load;
    MenuItem transfer;

    public ToolBar(Main start){
        this.start = start;
        createMenu();
        createSave();
        createLoad();
        createTransfer();
    }

    private void createMenu(){
        menu = new Menu("Menu");
        super.getMenus().add(menu);
    }

    //ukladat budeme podla toho, v akom mode prave sme - ak s vpiskami, tak s vpiskami, ak so zadanymi, tak so zadanymi
    private void createSave(){
        //TODO
    }

    private void createLoad(){
        //TODO
    }

    private void createTransfer(){
        //TODO
    }

//    MenuItem menu11 = new MenuItem("Red");
//    menu11.setOnAction(new EventHandler<ActionEvent>(){
//        @Override public void handle(ActionEvent event) { rect.setFill(Color.RED);}
//    });
//    MenuItem menu12 = new MenuItem("Green");
//    menu12.setOnAction(new EventHandler<ActionEvent>(){
//        @Override public void handle(ActionEvent event) { rect.setFill(Color.GREEN);}
//    });
//    MenuItem menu13 = new MenuItem("Blue");
//    menu13.setOnAction(new EventHandler<ActionEvent>(){
//        @Override public void handle(ActionEvent event) { rect.setFill(Color.BLUE);}
//    });
//    ToolBar menu14=new ToolBar("Others");
//    MenuItem menu141 = new MenuItem("Black");
//    menu141.setOnAction(new EventHandler<ActionEvent>(){
//        @Override public void handle(ActionEvent event) { rect.setFill(Color.BLACK);}
//    });
//    menu14.getItems().
//
//    ToolBar(menu141);
//    menu1.getItems().
//
//    ToolBar(menu11, menu12, menu13, menu14);
//    root.getChildren().
//
//    ToolBar(rect, menuBar);

}
