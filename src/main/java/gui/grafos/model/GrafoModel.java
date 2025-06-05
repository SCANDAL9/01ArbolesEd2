package gui.grafos.model;

import ed2TDAS.grafos.excepciones.ExcepcionAristaYaExiste;
import ed2TDAS.grafos.nopesados.Grafo;
import javafx.geometry.Point2D;
import javafx.util.Pair;

import java.util.*;


public class GrafoModel<T extends Comparable<T>>  {


    private Grafo<T> grafo;
    private Map<T, Point2D> posiciones;
    private Map<T, Integer> niveles; // Nivel de profundidad de cada nodo

    public GrafoModel(Grafo<T> grafo) {
        this.grafo = grafo;
        this.posiciones = new HashMap<>();
        this.niveles = new HashMap<>();
        generarCoordenadasJerarquicas();
    }

    private void generarCoordenadasJerarquicas() {
        posiciones.clear();
        niveles.clear();

        int centroX = 400;
        int centroY = 400;
        int separacion = 120;

        Set<T> visitados = new HashSet<>();
        Queue<T> cola = new LinkedList<>();

        List<T> vertices = (List<T>) grafo.getVertices();
        if (vertices.isEmpty()) return;

        T raiz = vertices.get(0);
        posiciones.put(raiz, new Point2D(centroX, centroY));
        niveles.put(raiz, 0);
        visitados.add(raiz);
        cola.add(raiz);

        while (!cola.isEmpty()) {
            T actual = cola.poll();
            Point2D posPadre = posiciones.get(actual);
            int nivelPadre = niveles.get(actual);

            List<T> adyacentes = (List<T>) grafo.getAdyacentesDelVertice(actual);
            int hijos = (int) adyacentes.stream().filter(h -> !visitados.contains(h)).count();
            if (hijos == 0) continue;

            double anguloBase = 0;
            double incremento = 2 * Math.PI / hijos;
            double radio = separacion * (nivelPadre + 1);

            int contador = 0;
            for (T hijo : adyacentes) {
                if (visitados.contains(hijo)) continue;

                double angulo = anguloBase + contador * incremento;
                double x = posPadre.getX() + radio * Math.cos(angulo);
                double y = posPadre.getY() + radio * Math.sin(angulo);

                posiciones.put(hijo, new Point2D(x, y));
                niveles.put(hijo, nivelPadre + 1);
                visitados.add(hijo);
                cola.add(hijo);
                contador++;
            }
        }
        // Agregar posiciones a vértices aislados (no visitados)
        int xBase = 50; // margen izquierdo
        int yBase = 50; // margen superior
        int spacing = 50;
        int contadorAislados = 0;
        for (T v : vertices) {
            if (!visitados.contains(v)) {
                posiciones.put(v, new Point2D(xBase + contadorAislados * spacing, yBase));
                niveles.put(v, 0);
                contadorAislados++;
            }
        }
    }

    public List<Pair<T, T>> getAristas() {
        List<Pair<T, T>> aristas = new ArrayList<>();
        for (T vertice : grafo.getVertices()) {
            for (T adyacente : grafo.getAdyacentesDelVertice(vertice)) {
                if (!aristas.contains(new Pair<>(adyacente, vertice))) {
                    aristas.add(new Pair<>(vertice, adyacente));
                }
            }
        }
        return aristas;
    }

    public List<Curva> getCurvasAristas() {
        List<Curva> curvas = new ArrayList<>();
        for (Pair<T, T> arista : getAristas()) {
            Point2D p1 = posiciones.get(arista.getKey());
            Point2D p2 = posiciones.get(arista.getValue());

            double dx = p2.getX() - p1.getX();
            double dy = p2.getY() - p1.getY();
            double distancia = Math.hypot(dx, dy);
            double umbral = 180; // distancia mínima para no usar curva

            if (distancia < umbral) {
                curvas.add(new Curva(p1, p2, null));
            } else {
                double mx = (p1.getX() + p2.getX()) / 2;
                double my = (p1.getY() + p2.getY()) / 2;
                double offsetX = -dy / distancia * 40;
                double offsetY = dx / distancia * 40;
                Point2D control = new Point2D(mx + offsetX, my + offsetY);
                curvas.add(new Curva(p1, p2, control));
            }
        }
        return curvas;
    }

    public void insertarVertice(T vertice) {
        grafo.insertarVertice(vertice);
        generarCoordenadasJerarquicas();
    }

    public void insertarArista(T origen, T destino) throws ExcepcionAristaYaExiste {
        grafo.insertarArista(origen, destino);
        generarCoordenadasJerarquicas();
    }

    public Map<T, Point2D> getPosiciones() {
        return posiciones;
    }

    public List<T> getVertices() {
        return (List<T>) grafo.getVertices();
    }

    public double getDistanciaIdeal(String origen, String destino) {
        Point2D p1 = posiciones.get(origen);
        Point2D p2 = posiciones.get(destino);

        if (p1 == null || p2 == null) {
            throw new IllegalArgumentException("Uno o ambos vértices no tienen posición definida.");
        }

        return p1.distance(p2);
    }

    public double calcularAnchoDelGrafo() {
        double maxX = Double.NEGATIVE_INFINITY;
        for (Point2D p : posiciones.values()) {
            if (p.getX() > maxX) {
                maxX = p.getX();
            }
        }
        return maxX + 100; // margen extra opcional
    }

    public double calcularAltoDelGrafo() {
        double maxY = Double.NEGATIVE_INFINITY;
        for (Point2D p : posiciones.values()) {
            if (p.getY() > maxY) {
                maxY = p.getY();
            }
        }
        return maxY + 100; // margen extra opcional
    }


    public static class Curva {
        public Point2D inicio;
        public Point2D fin;
        public Point2D control; // null si es recta

        public Curva(Point2D inicio, Point2D fin, Point2D control) {
            this.inicio = inicio;
            this.fin = fin;
            this.control = control;
        }
    }
}
