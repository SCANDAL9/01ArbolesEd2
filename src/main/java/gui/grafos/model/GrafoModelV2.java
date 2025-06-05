package gui.grafos.model;

import ed2TDAS.grafos.excepciones.ExcepcionAristaYaExiste;
import ed2TDAS.grafos.nopesados.Grafo;
import javafx.geometry.Point2D;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GrafoModelV2<T extends Comparable<T>>  {

    private Grafo<T> grafo;
    private Map<T, Point2D> posiciones;

    public GrafoModelV2(Grafo<T> grafo) {
        this.grafo = grafo;
        this.posiciones = new HashMap<>();
        generarCoordenadasCirculares();
    }

    private void generarCoordenadasCirculares() {
        int radio = 180;
        int centroX = 250;
        int centroY = 250;

        int cantidadDeVertices = grafo.cantidadDeVertices();
        List<T> vertices = (List<T>) grafo.getVertices();

        for (int i = 0; i < cantidadDeVertices; i++) {
            double angulo = 2 * Math.PI * i / cantidadDeVertices;
            double x = centroX + radio * Math.cos(angulo);
            double y = centroY + radio * Math.sin(angulo);

            posiciones.put(vertices.get(i), new Point2D(x, y));
        }
    }

    public List<Pair<T, T>> getAristas() {
        List<Pair<T, T>> aristas = new ArrayList<>();
        for (T vertice : grafo.getVertices()) {
            for (T adyacente : grafo.getAdyacentesDelVertice(vertice)) {
                if (!aristas.contains(new Pair<> (adyacente, vertice))) {
                    aristas.add(new Pair<>(vertice, adyacente));
                }
            }
        }
        return aristas;
    }

    public void insertarVertice(T vertice) {
        grafo.insertarVertice(vertice);
        generarCoordenadasCirculares();
    }

    public void insertarArista(T origen, T destino) throws ExcepcionAristaYaExiste {
        grafo.insertarArista(origen, destino);
    }

    public Map<T, Point2D> getPosiciones() {
        return posiciones;
    }

    public List<T> getVertices() {
        return (List<T>) grafo.getVertices();
    }
}

