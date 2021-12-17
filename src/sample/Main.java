package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import sample.models.Agent;
import sample.models.GridObservable;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        int n = 5;
        int m = 5;
        GridObservable grid = new GridObservable(n, m);
        Agent soleil = new Agent('S', new int[]{0, 3}, new int[]{0, 1}, grid);
        Agent sablier = new Agent('x', new int[]{1, 2}, new int[]{1, 2}, grid);
//        sample.models.Agent croix = new sample.models.Agent('c', new int[]{0, 2}, new int[]{0, 3}, grid);
        Agent etoile = new Agent('e', new int[]{3, 3}, new int[]{2, 1}, grid);
        Agent[] agents = {soleil, sablier, etoile};
        grid.setAgents(agents);

        TilePane root = new TilePane();
        View v = new View(root, grid, n, m);
        grid.addPropertyChangeListener(v);
        primaryStage.setTitle("Auto Takin");
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();
        grid.start();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
