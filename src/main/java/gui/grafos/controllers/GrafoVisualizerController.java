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
import javafx.scene.control.*;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Scale;
import javafx.util.Pair;

import java.util.List;
import java.util.Map;


public class GrafoVisualizerController <T extends  Comparable<T>> {
    @FXML private Canvas canvasGrafo;
    @FXML private ComboBox<String> origenCombo;
    @FXML private ComboBox<String> destinoCombo;
    @FXML private Button btnAddArista;
    @FXML private TextField textVertice;
    @FXML private Button btnAddVertice;
    @FXML private ScrollPane scrollPane1;
    @FXML private Pane pane1;
    private Scale scale = new Scale(1, 1, 0, 0);


    private GrafoModel<String> grafoModel;

    @FXML
    public void initialize() {
        scrollPane1.setPannable(true);
        canvasGrafo.setWidth(2000); // o el tamaño deseado
        canvasGrafo.setHeight(2000);

        pane1.getTransforms().add(scale);

        scrollPane1.addEventFilter(ScrollEvent.SCROLL, event -> {
            if (event.isControlDown()) {
                double zoomSensivility = 0.01;
                double zoomFactor = 1 + zoomSensivility;
                if (event.getDeltaY() < 0) {
                    zoomFactor = 1 / (1 + zoomSensivility);
                }

                // Obtener posición del cursor dentro del contenido
                double mouseX = event.getX();
                double mouseY = event.getY();

                // Coordenadas en el contenido antes de escalar
                double innerX = (mouseX + scrollPane1.getHvalue() * (pane1.getBoundsInParent().getWidth() - scrollPane1.getViewportBounds().getWidth())) / scale.getX();
                double innerY = (mouseY + scrollPane1.getVvalue() * (pane1.getBoundsInParent().getHeight() - scrollPane1.getViewportBounds().getHeight())) / scale.getY();

                // Aplicar nueva escala
                scale.setX(scale.getX() * zoomFactor);
                scale.setY(scale.getY() * zoomFactor);

                pane1.setMinWidth(canvasGrafo.getWidth() * scale.getX());
                pane1.setMinHeight(canvasGrafo.getHeight() * scale.getY());

                // Calcular nueva posición del viewport después del zoom
                scrollPane1.layout(); // Forzar actualización de layout

                double newInnerX = innerX * scale.getX();
                double newInnerY = innerY * scale.getY();

                // Calcular la proporción de scroll después del zoom
                double scrollH = (newInnerX - scrollPane1.getViewportBounds().getWidth() / 2) /
                        (pane1.getBoundsInParent().getWidth() - scrollPane1.getViewportBounds().getWidth());
                double scrollV = (newInnerY - scrollPane1.getViewportBounds().getHeight() / 2) /
                        (pane1.getBoundsInParent().getHeight() - scrollPane1.getViewportBounds().getHeight());

                // Limitar entre 0 y 1
                scrollPane1.setHvalue(Math.max(0, Math.min(scrollH, 1)));
                scrollPane1.setVvalue(Math.max(0, Math.min(scrollV, 1)));

                event.consume();
            }
        });
    }

    public void setGrafoVisualizerController(GrafoModel<String> controller) {
        this.grafoModel = controller;

        double maxGrafoWidth = grafoModel.calcularAnchoDelGrafo();
        double maxGrafoHeight = grafoModel.calcularAltoDelGrafo();

        //scrollPane1.viewportBoundsProperty().addListener((obs, oldBounds, newBounds) -> {
            //double finalWidth = Math.max(maxGrafoWidth, newBounds.getWidth());
            //double finalHeight = Math.max(maxGrafoHeight, newBounds.getHeight());

            //canvasGrafo.setWidth(maxGrafoWidth);
            //canvasGrafo.setHeight(maxGrafoHeight);
       // });

        dibujarGrafo();
    }

    private void dibujarGrafo() {
        if (grafoModel == null) return;

        GraphicsContext gc = canvasGrafo.getGraphicsContext2D();
        gc.clearRect(0, 0, canvasGrafo.getWidth(), canvasGrafo.getHeight());

        gc.setLineWidth(2);
        gc.setStroke(Color.GRAY);

        Map<String, Point2D> posiciones = grafoModel.getPosiciones();

        // Dibujo de aristas
        for (Pair<String, String> arista : grafoModel.getAristas()) {
            Point2D p1 = posiciones.get(arista.getKey());
            Point2D p2 = posiciones.get(arista.getValue());

            if (p1 == null || p2 == null) continue;

            if (arista.getKey().equals(arista.getValue())) {
                // Bucle
                double x = p1.getX();
                double y = p1.getY();
                double loopRadius = 15;
                gc.strokeOval(x - loopRadius, y + loopRadius / 2, loopRadius * 2.5, loopRadius * 3);
            } else {
                double distancia = p1.distance(p2);
                double ideal = grafoModel.getDistanciaIdeal(arista.getKey(), arista.getValue()); // Usamos la nueva propiedad

                if (Math.abs(distancia - ideal) < 5) {
                    // Línea recta
                    gc.strokeLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
                } else {
                    // Curva (parábola)
                    drawCurvedEdge(gc, p1, p2);
                }
            }
        }

        // Dibujo de vértices
        int radio = 15;
        for (Map.Entry<String, Point2D> entry : posiciones.entrySet()) {
            Point2D p = entry.getValue();
            gc.setFill(Color.CORNFLOWERBLUE);
            gc.fillOval(p.getX() - radio, p.getY() - radio, radio * 2, radio * 2);

            gc.setStroke(Color.DARKBLUE);
            gc.setLineWidth(3);
            gc.strokeOval(p.getX() - radio, p.getY() - radio, radio * 2, radio * 2);

            gc.setFill(Color.WHITE);
            gc.setTextAlign(TextAlignment.CENTER);
            gc.setTextBaseline(VPos.CENTER);
            gc.fillText(entry.getKey(), p.getX(), p.getY());
        }
    }

    // Dibujar arista curva (parábola con control point)
    private void drawCurvedEdge(GraphicsContext gc, Point2D p1, Point2D p2) {
        double mx = (p1.getX() + p2.getX()) / 2;
        double my = (p1.getY() + p2.getY()) / 2;

        // Elevamos el punto de control para evitar el centro
        double dx = p2.getX() - p1.getX();
        double dy = p2.getY() - p1.getY();
        double distancia = Math.sqrt(dx * dx + dy * dy);
        double factorAltura = 0.2; // Puedes ajustarlo

        // Vector perpendicular normalizado
        double nx = -dy / distancia;
        double ny = dx / distancia;

        double cx = mx + nx * distancia * factorAltura;
        double cy = my + ny * distancia * factorAltura;

        gc.beginPath();
        gc.moveTo(p1.getX(), p1.getY());
        gc.quadraticCurveTo(cx, cy, p2.getX(), p2.getY());
        gc.stroke();
    }

    @FXML
    void addArista(ActionEvent event) throws ExcepcionAristaYaExiste {
        String origen = origenCombo.getValue();
        String destino = destinoCombo.getValue();

        if (origen == null || destino == null) {
            mostrarError("Debes seleccionar ambos nodos.");
            return;
        }
        try {
            grafoModel.insertarArista(origen, destino);
            dibujarGrafo();
        } catch (ExcepcionAristaYaExiste e) {
            mostrarError("Ya existe una arista entre esos nodos.");
        }
    }

    @FXML
    void addVertice(ActionEvent event) {
        String verticeNuevo = textVertice.getText();
        if (verticeNuevo == null || verticeNuevo.trim().isEmpty()) {
            mostrarError("El nombre del vértice no puede estar vacío.");
            return;
        }
        grafoModel.insertarVertice(verticeNuevo);
        textVertice.clear();
        actualizarOpcionesNodos();
        dibujarGrafo();
    }

    private void actualizarOpcionesNodos() {
        List<String> ids = grafoModel.getVertices();
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
