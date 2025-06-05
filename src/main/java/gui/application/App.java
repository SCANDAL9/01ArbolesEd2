package gui.application;

import ed2TDAS.grafos.nopesados.Grafo;
import gui.grafos.controllers.GrafoVisualizerController;
import gui.grafos.model.GrafoModel;
import gui.grafos.utilities.Paths;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths.GRAFO_VISUALIZER));
        Scene scene = new Scene(loader.load());

        GrafoVisualizerController<String> grafoViewController = loader.getController();

        Grafo<String> grafo = new Grafo<>();
        GrafoModel<String> grafoLayout = new GrafoModel<>(grafo);
        grafoViewController.setGrafoVisualizerController(grafoLayout);

        stage.setScene(scene);
        stage.setTitle("Visualización de Grafos");
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}
