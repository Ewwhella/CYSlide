package com.example.javaproject;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

/**
 * The controller class for the JavaFX application.
 */
public class Controller {

    private int nbMove = 0;
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Label menuLabel;
    @FXML
    private GridPane gridMenu;
    @FXML
    private GridPane gridGame;
    @FXML
    private Label gameCurrentScore;
    private int currentScore = 0;
    @FXML
    private Label GameTitle;
    @FXML
    private Label gameHighScore;

    private int firstClickX = -1;
    private int firstClickY = -1;

    private Grid gridData;

    private static int chosenLevel;



    /**
     * Sets the stage for the controller.
     *
     * @param stage The stage to set.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Switches to the menu scene.
     *
     * @param event The action event.
     */
    public void switchToMenu(ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("menu.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("menu.css").toExternalForm());
            this.stage.setScene(scene);
            this.stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Switches to the welcome scene.
     *
     * @param event The action event.
     */
    public void switchToWelcome(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("welcome.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("welcome.css").toExternalForm());
            this.stage.setScene(scene);
            this.stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Switches to the game scene.
     *
     * @param event The mouse event.
     * @param i     The level.
     */
    public void switchToGame(MouseEvent event, int i) {
        setChosenLevel(i);
        try {
            Parent root = FXMLLoader.load(getClass().getResource("game.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("game.css").toExternalForm());
            this.stage.setScene(scene);
            this.stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * Switches to the win scene.
     */
    public void switchToWin() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("win.fxml"));
            stage = (Stage) gameCurrentScore.getScene().getWindow();
            scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("win.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the controller.
     *
     * @throws IOException if an I/O error occurs.
     */
    public void initialize() throws IOException {
        if (menuLabel != null) {
            int columnCount = 0;
            int rowCount = 1;
            int nbHighScoreNull = 0;
            HighScoreCSV nb = new HighScoreCSV("src/HighScoreCSV.csv");
            int nbLevel = nb.getNumberOfLevels();
            menuLabel.setText("Nombre de niveaux disponibles : " + nbLevel);

            for (int i = 1; i <= nbLevel; i++) {
                int level = i;

                Label levelLabel = new Label("Niveau " + i);
                levelLabel.setId("Menulevel");

                HighScoreCSV n = new HighScoreCSV("src/HighScoreCSV.csv");
                int record = n.getHighScore(i-1);
                if (record == 0) {
                    nbHighScoreNull++;
                }
                if (record == 0 && nbHighScoreNull > 1) {
                    Image image = new Image(getClass().getResource("img/lock.png").toExternalForm());
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(30);
                    imageView.setFitHeight(30);

                    StackPane stackPane = new StackPane();
                    stackPane.getChildren().addAll(levelLabel, imageView);
                    stackPane.setAlignment(Pos.CENTER);

                    GridPane.setRowIndex(stackPane, rowCount);
                    GridPane.setColumnIndex(stackPane, columnCount);


                    gridMenu.getChildren().add(stackPane);


                    columnCount++;
                    if (columnCount == 3) {
                        columnCount = 0;
                        rowCount++;
                    }
                } else {

                    GridPane.setRowIndex(levelLabel, rowCount);
                    GridPane.setColumnIndex(levelLabel, columnCount);

                    levelLabel.setOnMouseClicked(event -> switchToGame(event, level));


                    gridMenu.getChildren().add(levelLabel);

                    columnCount++;
                    if (columnCount == 3) {
                        columnCount = 0;
                        rowCount++;
                    }
                }
            }
        }


        if (gridGame != null) {
            if (gridGame != null) {
                if (this.chosenLevel != 0) {
                    initializeGameGrid(this.chosenLevel);
                }
            }
        }

        if (gameHighScore != null) {
            HighScoreCSV n = new HighScoreCSV("src/HighScoreCSV.csv");
            int record = n.getHighScore(chosenLevel -1);
            gameHighScore.setText(String.valueOf(record));
        }

        if (GameTitle != null) {
            GameTitle.setText("Niveau " + this.chosenLevel);
        }
        if (gameCurrentScore != null) {
            gameCurrentScore.setText((String.valueOf(nbMove)));
        }
    }

    /**
     * Sets the chosen level.
     *
     * @param level The chosen level.
     */
    public void setChosenLevel(int level) {
        chosenLevel = level;
    }



    /**
     * Initializes the game grid.
     *
     * @param chosenLevel The chosen level.
     */
    private void initializeGameGrid(int chosenLevel) {
        ReadLevelGridCSV test = new ReadLevelGridCSV("src/ReadLevelGridCSV.csv", chosenLevel);

        gridData = new Grid(test);
        displayGrid(gridData);
    }

    /**
     * Displays the game grid.
     *
     * @param gridData The grid data to display.
     */
    public void displayGrid(Grid gridData){
        gameCurrentScore.setText((String.valueOf(currentScore)));
        int length = gridData.getLengthSide();

        gridGame.getChildren().clear();
        gridGame.getColumnConstraints().clear();
        gridGame.getRowConstraints().clear();


        gridGame.setPadding(new Insets(70));
        gridGame.setHgap(0);
        gridGame.setVgap(0);

        for (int i = 0; i < length; i++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setHgrow(Priority.SOMETIMES);
            colConstraints.setMinWidth(10.0);
            colConstraints.setPrefWidth(100.0);
            gridGame.getColumnConstraints().add(colConstraints);

            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setMinHeight(10.0);
            rowConstraints.setPrefHeight(30.0);
            rowConstraints.setVgrow(Priority.SOMETIMES);
            gridGame.getRowConstraints().add(rowConstraints);
        }

        for (int line = 0; line < length; line++) {
            for (int col = 0; col < length; col++) {
                int x = line;
                int y = col;
                int token = gridData.GetArrayGrid(line, col);

                Label label = new Label();
                label.setPrefSize(130, 130);
                label.setAlignment(Pos.CENTER);
                label.setOnMouseClicked(event -> move(x, y));
                if (token == -1) {
                    label.setId("caseWhite");
                } else if (token > 0 && token <= 100) {
                    label.setId("case");
                    label.setText(String.valueOf(token));
                }else if (token == 0){
                    label.setId("caseGrey");
                }


                gridGame.add(label, col, line);
            }
        }
    }



    /**
     * Resets the game.
     *
     * @param event The event.
     */
    public void reset(ActionEvent event) {
        initializeGameGrid(chosenLevel);
    }


    /**
     * Performs a random mix of the game grid.
     *
     * @param event The event.
     */
    public void randomMix(ActionEvent event) {
        gridData.randomGrid();
        displayGrid(gridData);
        boolean isSolvable = ResolveGame.isGridSolvable(gridData);
        System.out.println(isSolvable);
        if (isSolvable == false){
            JPanel panel = new JPanel();
            JLabel label = new JLabel("Il est possible que la grille n'est pas de solution.");
            panel.add(label);
        }
    }


    /**
     * Performs a random move of the game grid.
     *
     * @param event The event.
     */
    public void randomMoveMix(ActionEvent event){
        gridData.randomMoveGrid();
        displayGrid(gridData);
    }


    /**
     * Moves a token on the game grid.
     *
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    public void move(int x, int y){
        if (firstClickX == -1 && firstClickY == -1) {
            firstClickX = x;
            firstClickY = y;
        } else {
            int secondClickX = x;
            int secondClickY = y;

            if(gridData.move(firstClickX, firstClickY, secondClickX, secondClickY)) {
                currentScore++;
            }

            gameCurrentScore.setText(String.valueOf(currentScore));
            displayGrid(gridData);
            firstClickX = -1;
            firstClickY = -1;
        }
        boolean win = checkWin(gridData);
        if(win){
            initializeGameGrid(chosenLevel);
            HighScoreCSV fileHighScore = new HighScoreCSV("src/HighScoreCSV.csv");
            fileHighScore.changeHighScore(chosenLevel-1, currentScore);

            switchToWin();
        }

    }



    /**
     * Checks if the player has won the game.
     *
     * @param grid The grid data.
     * @return True if the player has won, false otherwise.
     */
    public boolean checkWin(Grid grid) {
        boolean hasEncounteredNonZero = false;
        int previousValue = -1;

        for (int i = 0; i < grid.getLengthSide(); i++) {
            for (int j = 0; j < grid.getLengthSide(); j++) {
                int value = grid.GetArrayGrid(i, j);

                if (value == -1) {
                    continue;
                }

                if (value != 0) {
                    if (hasEncounteredNonZero && value < previousValue) {
                        return false;
                    }

                    previousValue = value;
                    hasEncounteredNonZero = true;
                }
            }
        }
        return true;
    }



    public void solve() {

            Task<Void> solvingTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    ResolveGame resolution = new ResolveGame(gridData);
                    int nbMove = resolution.getMoves();




                    if (nbMove != 0) {
                        JPanel panel = new JPanel();
                        JLabel label = new JLabel("Solution trouvée en " + resolution.getMoves()+ " mouvements. Voulez-vous continuer ?");

                        panel.add(label);

                        int option = JOptionPane.showConfirmDialog(
                                null,
                                panel,
                                "Confirmation",
                                JOptionPane.OK_CANCEL_OPTION,
                                JOptionPane.QUESTION_MESSAGE
                        );
                        if (option == JOptionPane.OK_OPTION) {


                            int moves = resolution.getMoves();
                            for (int i = 0; i <= moves; i++) {
                                ReadLevelGridCSV ArrayGridCSV = new ReadLevelGridCSV("src/Temporary.csv", i + 1);
                                Grid grid = new Grid(ArrayGridCSV);
                                javafx.application.Platform.runLater(() -> displayGrid(grid));

                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            return null;
                        }
                        else{
                            return null;
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Il n'y pas de solution à cette grille, ou elle n'a pas été trouvée.");
                        return null;
                    }
                }
            };


            solvingTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                }
            });

            Thread solvingThread = new Thread(solvingTask);
            solvingThread.start();
        }
    }








