package GUI;
import cnf_generators.*;
import grids.InputGrid;
import grids.OutputGrid;
import grids.Sudoku;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class Main extends Application {
    Main start = this;
    public BorderPane root;
    public Scene scene;
    public InputGrid inGrid;
    public OutputGrid outGrid;
    public VBox buttons;
    public Button generate;
    public Button transfer;
    public Button save;
    public Button switchMode; //prepina medzi zobrazovanim s a bez vpisiek
    public Button load;
    public Button solutionCount;
    public Button showSolution;
    public Button showProgress;
    public Sudoku sudoku;
    public Mode mode;
    public Label noOfSol;

    @Override
    public void start(Stage primaryStage) throws Exception{
        root = new BorderPane();
        inGrid = new InputGrid(start);
        outGrid = new OutputGrid(start);
        transfer = new Button("-->");
        setTransferAction();
        generate = new Button("Generate CNF");
        setGenerateAction();
        save = new Button("Save");
        setSaveAction();
        switchMode = new Button("Show Pencilmarks");
        mode = Mode.GIVENS;
        setSwitchAction();
//        load = new Button("Load");
//        load.setLoadAction();
        solutionCount = new Button("Solution Count");
        setSolutionCountAction();
        showSolution = new Button("Show Solution");
        setShowSolutionAction();
        showProgress = new Button("Show Progress");
        setShowProgressAction();
        noOfSol = new Label("NUMBER OF SOLUTIONS: 1000+");

        buttons = new VBox();
        buttons.getChildren().addAll(transfer, generate, save, switchMode, solutionCount, showSolution, showProgress);
        root.setLeft(inGrid);
        root.setRight(outGrid);
        root.setCenter(buttons);
        root.setTop(noOfSol);


        scene = new Scene(root);
        primaryStage.setTitle("Sudoku Variants Solution Checker");
        primaryStage.setScene(scene);
        primaryStage.show();


/** Len taka vskuvka na ukazku - funguje, ale takto to predsa robit nechceme :) */
//        Sudoku sudoku2 = new Sudoku();
//        sudoku2.setXYZ(1,2,8);
//        sudoku2.setXYZ(1,3,1);
//        sudoku2.setXYZ(2,4,6);
//        sudoku2.setXYZ(2,5,3);
//        sudoku2.setXYZ(2,9,5);
//        sudoku2.setXYZ(3,6,7);
//        sudoku2.setXYZ(3,9,4);
//        sudoku2.setXYZ(4,3,6);
//        sudoku2.setXYZ(4,4,5);
//        sudoku2.setXYZ(4,6,4);
//        sudoku2.setXYZ(4,8,9);
//        sudoku2.setXYZ(5,2,3);
//        sudoku2.setXYZ(5,8,8);
//        sudoku2.setXYZ(6,2,1);
//        sudoku2.setXYZ(6,4,8);
//        sudoku2.setXYZ(6,6,3);
//        sudoku2.setXYZ(6,7,2);
//        sudoku2.setXYZ(7,1,4);
//        sudoku2.setXYZ(7,4,9);
//        sudoku2.setXYZ(8,1,9);
//        sudoku2.setXYZ(8,5,2);
//        sudoku2.setXYZ(8,6,5);
//        sudoku2.setXYZ(9,7,7);
//        sudoku2.setXYZ(9, 8, 2);
//
//        Generator gen2 = new Untouchable(new ClassicBase(sudoku2));
//        gen2.createFileWithCNF();
//
//
//
//        Generator gen3 = new Diagonal(new Untouchable(new DisjointGroups(new ClassicBase(sudoku2))));
//        gen3.createFileWithCNF();
//
//
//        gen3.generate();

        Sudoku sudokuX = new Sudoku(inGrid);
        Generator gen = new NonConsecutive(new DisjointGroups(new ClassicBase(sudokuX)));
        gen.createFileWithCNF();
        //outGrid.update(new Solution("Solution 1: 7 13 26 33 39 46 63 68 74 87 91 101 113 121 135 142 152 156 167 174 189 196 206 209 220 231 235 247 258 266 272 280 291 305 315 322 333 340 343 359 366 374 381 389 400 413 416 426 441 448 454 465 469 482 487 503 508 516 524 537 545 556 567 569 585 592 598 608 620 622 633 645 651 662 672 676 693 700 704 715 728"));
        //outGrid.update(new Solution("Solution 1: 8 11 24 32 43 49 63 66 73 82 97 103 111 123 135 143 146 158 165 176 189 197 200 208 222 232 238 250 253 269 274 284 290 300 312 324 333 336 344 357 361 376 382 392 404 409 420 428 441 444 458 466 469 479 492 504 511 515 526 536 541 557 561 572 580 586 601 611 615 623 639 645 650 665 669 676 693 699 707 715 727"));
        //outGrid.update(new Solution("Solution 1: 1 11 24 31 43 53 59 66 81 89 94 106 117 120 131 141 145 155 171 174 185 190 204 209 223 229 242 247 258 269 273 284 297 298 308 322 329 342 343 353 368 376 382 393 399 408 421 425 438 445 451 468 476 482 493 496 507 518 524 535 548 558 564 569 581 594 602 604 618 624 637 643 654 665 670 682 693 696 704 716 721"));
        //outGrid.update(new Solution("Solution 1: 7 18 19 32 44 47 60 66 76 84 92 103 114 126 127 140 151 161 167 179 186 193 205 210 218 234 235 245 259 266 279 282 296 301 307 321 330 334 345 358 362 373 386 392 405 413 418 432 433 446 456 462 470 484 495 498 512 515 528 538 541 553 563 568 582 592 597 607 617 630 638 641 652 662 668 683 685 702 709 717 723"));
        //outGrid.update(new Solution("Solution 1: 6 18 23 35 38 49 61 66 73 83 97 102 113 126 127 139 150 161 170 175 181 196 201 213 225 227 239 246 260 265 271 285 297 299 311 322 329 335 349 355 368 372 384 388 405 406 420 432 434 446 457 462 476 481 493 498 510 522 523 536 548 553 560 571 577 593 597 610 614 626 639 645 657 662 668 681 688 701 703 718 723"));
        outGrid.update(new Solution("Solution 1: 9 16 21 33 37 49 62 68 74 85 91 105 110 122 134 138 153 160 170 176 182 196 207 210 222 229 235 246 261 268 271 283 294 299 314 320 330 337 343 356 368 371 385 390 405 407 422 428 441 444 457 460 474 481 493 498 513 517 528 532 545 551 566 568 582 589 602 605 617 630 637 642 653 659 674 678 691 702 706 712 726"));
        //outGrid.update(new Solution("Solution 1: 9 16 21 33 37 49 62 68 74 85 91 105 110 122 134 138 153 160 170 176 182 196 207 210 222 229 235 246 261 268 271 283 294 299 314 320 330 337 343 356 368 371 385 390 405 407 422 428 441 444 457 460 474 481 493 498 513 517 528 532 545 551 566 568 582 589 602 605 617 630 637 642 653 659 674 678 691 702 706 712 726"));
        //outGrid.update(new Solution("Solution 10: 2 18 22 34 39 50 62 69 73 89 91 105 112 126 128 140 147 160 167 178 183 195 199 215 218 234 238 246 257 268 271 287 294 301 308 324 330 341 343 360 362 373 385 392 399 409 416 432 435 446 457 460 476 483 495 499 506 518 529 534 546 550 566 568 582 593 596 607 621 624 637 644 655 660 671 683 690 694 711 715 722"));
    }

    private void setShowProgressAction() {
        //TODO
    }

    private void setShowSolutionAction() {
        showSolution.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Generator generator = createGenerator();
                Solution solution = generator.getOneSolution();
                outGrid.update(solution);
            }
        });
    }

    private void setSolutionCountAction() {
        solutionCount.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Generator generator = createGenerator();
                generator.setMode('c');
                try {
                    generator.createFileWithCNF();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Integer solutionCount = generator.getSolutionCount();
                if (solutionCount >= 1000){
                    noOfSol.setText("NUMBER OF SOLUTIONS: 1000+");
                }
                else{
                    noOfSol.setText("NUMBER OF SOLUTIONS: "+solutionCount.toString());
                }
            }
        });

    }

    private void setLoadAction(){
        load.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                File file = root.fileCreator.openFile(stage, false);
//                try {
//                    Map map = root.mapConvertor.fileToMap(file);
//                    root.game = new Game(root, map);
//                    stage.close();
//                    root.game.run();
//                } catch (Exception e){
//                }
            }
        });
    }

    private void showPencilmarks(){
        outGrid.showPencilmarks();
        inGrid.showPencilmarks();
    }

    private void hidePencilmarks(){
        outGrid.hidePencilmarks();
        inGrid.hidePencilmarks();

    }

    public Mode getMode(){
        return this.mode;
    }

    public void setMode(Mode mode){
        this.mode = mode;
        this.switchMode.setText((mode.equals(Mode.GIVENS) ? "Show Pencilmarks" : "Hide Pencilmarks"));
    }

    private void setTransferAction(){
        transfer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                inGrid.update();
                outGrid.update();
            }
        });
    }

    private void setSwitchAction(){
        switchMode.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (start.getMode().equals(Mode.GIVENS)){
                    switchMode.setText("Hide Pencilmarks");
                    start.setMode(Mode.PENCILMARKS);
                    showPencilmarks();
                }
                else{
                    switchMode.setText("Show Pencilmarks");
                    start.setMode(Mode.GIVENS);
                    hidePencilmarks();
                }
            }
        });
    }

    private void setGenerateAction(){
        generate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                sudoku = new Sudoku(inGrid);
                try {
                    sudoku.generate();
                } catch (IOException e) {
                    System.err.println("Problem occured while generating CNF for sudoku");
                }
                System.out.println("CNF generated");
            }
        });
    }

    //TODO zatial do predvoleneho suboru, prerobit na vyber suboru
    public void setSaveAction(){
        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                inGrid.update();
                String outputFile = "files/sudoku_export1.txt";
                try{
                    PrintWriter out = new PrintWriter(new File(outputFile));
                    System.out.println(start.inGrid.toFile(mode));
                    out.print(start.inGrid.toFile(mode));
                    out.close();
                }
                catch(IOException e){
                    System.err.println("File not found");
                }
            }
        });
    }

//    void saveMap(){
//        try {
//            isMapCorrect();
//        } catch (EditorExeption e){
//            ExceptionPrinter.print(e.getMessage());
//            return;
//        }
//        File file = root.fileCreator.openFile(stage, true);
//        try {
//            if(file != null ) root.mapConvertor
//                    .fromMapEditor(map, settings.getRobotsLimits(), settings.getTarget(), file);
//        }
//        catch (FileNotFoundException e) {
//        }
//    }
    /** Pouzit na nacitanie sudoku zo suboru*/
//    Button load = new Button("LOAD SAVED");
//    load.setPrefWidth(width);
//    Style.setButtonStyle(load);
//    load.setOnAction(new EventHandler<ActionEvent>() {
//        @Override
//        public void handle(ActionEvent event) {
//            File file = root.fileCreator.openFile(stage, false);
//            try {
//                Map map = root.mapConvertor.fileToMap(file);
//                root.game = new Game(root, map);
//                stage.close();
//                root.game.run();
//            } catch (Exception e){
//            }
//        }
//    });

    /** Tu to v ziadnom pripade nema byt priamo klasicke sudoku, prave tu sa to bude spravat podla toho, s cim pracujeme*/
    public Generator createGenerator() {
        inGrid.update();
        outGrid.update();
        Sudoku sudoku = new Sudoku(inGrid);

        Generator generator = new ClassicBase(sudoku);
        return generator;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
