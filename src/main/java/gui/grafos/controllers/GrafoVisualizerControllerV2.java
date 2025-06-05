package gui.grafos.controllers;

import ed2TDAS.grafos.excepciones.ExcepcionAristaYaExiste;
import gui.grafos.model.GrafoModel;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.util.Pair;

import java.util.List;
import java.util.Map;


public class GrafoVisualizerControllerV2 <T extends  Comparable<T>> {
    @FXML
    private Canvas canvasGrafo;
    @FXML
    private ComboBox<String> origenCombo;
    @FXML
    private ComboBox<String> destinoCombo;
    @FXML
    private Button btnAddArista;
    @FXML
    private TextField textVertice;
    @FXML
    private Button btnAddVertice;

    private GrafoModel<String> grafoVisualController;

    public void setGrafoVisualizerController(GrafoModel<String> controller) {
        this.grafoVisualController = controller;
        dibujarGrafo();
    }

    private void dibujarGrafo() {
        if (grafoVisualController == null) return;
        GraphicsContext gc = canvasGrafo.getGraphicsContext2D();
        gc.clearRect(0, 0, canvasGrafo.getWidth(), canvasGrafo.getHeight());

        gc.setStroke(Color.GRAY);
        gc.setLineWidth(2);

        for (Pair<String, String> arista : grafoVisualController.getAristas()) {
            Point2D p1 = grafoVisualController.getPosiciones().get(arista.getKey());
            Point2D p2 = grafoVisualController.getPosiciones().get(arista.getValue());
            if (arista.getKey().equals(arista.getValue())) {
                // Es un bucle, dibujamos un óvalo pequeño encima del nodo
                double x = p1.getX();
                double y = p1.getY();
                double loopRadius = 15;
                gc.strokeOval(x - loopRadius, y + loopRadius/2, loopRadius*2.5, loopRadius*3);
            } else {
                gc.strokeLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
            }
        }

        int radio = 15;

        for (Map.Entry<String, Point2D>  entry : grafoVisualController.getPosiciones().entrySet()) {
            Point2D p = entry.getValue();
            gc.setFill(Color.CORNFLOWERBLUE);
            gc.fillOval(p.getX() - radio, p.getY() - radio, radio*2, radio*2);

            gc.setStroke(Color.DARKBLUE);
            gc.setLineWidth(3);
            gc.strokeOval(p.getX() - radio, p.getY() - radio, radio*2, radio*2);

            gc.setFill(Color.WHITE);
            gc.setTextAlign(TextAlignment.CENTER);
            gc.setTextBaseline(VPos.CENTER);
            String texto = entry.getKey().toString();
            gc.fillText(texto, p.getX(), p.getY());

            //gc.fillText(texto, p.getX() - gc.getFont().getSize()/2, p.getY() + 4);
        }

    }

    @FXML
    void addArista(ActionEvent event) throws ExcepcionAristaYaExiste {
        String origen = origenCombo.getValue();
        String destino = destinoCombo.getValue();

        if (origen == null || destino == null ) {
            throw new IllegalArgumentException("Debes seleccionar ambos nodos.");
        }
        grafoVisualController.insertarArista(origen, destino);
        dibujarGrafo();
    }

    @FXML
    void addVertice(ActionEvent event) {
        String verticeNuevo = textVertice.getText();

        if (verticeNuevo == null || verticeNuevo.trim().isEmpty()) {
            mostrarError("El nombre del vértice no puede estar vacío.");
            return;
        }
        grafoVisualController.insertarVertice(verticeNuevo);
        textVertice.clear();
        actualizarOpcionesNodos();
        dibujarGrafo();
    }

    private void actualizarOpcionesNodos() {
        List<String> ids = (List<String>) grafoVisualController.getVertices(); // e.g. ["A", "B", "C"]
        origenCombo.setItems(FXCollections.observableArrayList(ids));
        destinoCombo.setItems(FXCollections.observableArrayList(ids));
    }


    private void mostrarError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}