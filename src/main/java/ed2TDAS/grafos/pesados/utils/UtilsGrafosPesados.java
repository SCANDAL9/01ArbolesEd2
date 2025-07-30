package ed2TDAS.grafos.pesados.utils;

import ed2TDAS.grafos.excepciones.ExcepcionAristaNoExiste;
import ed2TDAS.grafos.excepciones.ExcepcionAristaYaExiste;
import ed2TDAS.grafos.nopesados.utils.ControlMarcados;
import ed2TDAS.grafos.pesados.GrafoPesado;

import java.util.List;

public class UtilsGrafosPesados<T extends Comparable<T>> {

    public UtilsGrafosPesados() {
    }

    public boolean existeCicloEnGrafo(GrafoPesado<T> elGrafo) throws ExcepcionAristaYaExiste, ExcepcionAristaNoExiste {
        if (!(elGrafo instanceof GrafoPesado<T>)) {
            throw new IllegalArgumentException("Se requiere un Grafo No Dirigido");
        }
        ControlMarcados marcados = new ControlMarcados(elGrafo.cantidadDeVertices());
        GrafoPesado<T> grafoAux = new GrafoPesado<>(elGrafo.getVertices());
        for (T vertice : elGrafo.getVertices()) {
            int nrovertice = elGrafo.getNroVertice(vertice);
            if (!seVisitoElVertice(marcados, nrovertice)) {
                if (buscarCicloEnGrafo(vertice,
                        elGrafo, grafoAux, marcados)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean buscarCicloEnGrafo(T verticeAProcesar,
                                       GrafoPesado<T> elGrafo,
                                       GrafoPesado<T> grafoAux,
                                       ControlMarcados controlMarcados) throws ExcepcionAristaYaExiste, ExcepcionAristaNoExiste {
        elGrafo.validarVertice(verticeAProcesar);
        int nroDelVerticeEnTurno = elGrafo.getNroVertice(verticeAProcesar);
        controlMarcados.marcarVertices(nroDelVerticeEnTurno);
        Iterable<T> adyacentesAProcesar = elGrafo.getAdyacentesDelVertice(verticeAProcesar);

        for (T adyacente : adyacentesAProcesar) {
            int nroDelAdyacente = elGrafo.getNroVertice(adyacente);
            if (!controlMarcados.estaMarcadoElVertice(nroDelAdyacente)) {
                double peso = elGrafo.peso(verticeAProcesar, adyacente);
                grafoAux.insertarArista(verticeAProcesar, adyacente, peso);
                if (buscarCicloEnGrafo(adyacente,
                        elGrafo, grafoAux, controlMarcados)) return true;
            } else {
                if (!grafoAux.existeAdyacencia(verticeAProcesar, adyacente)) {
                    return true;
                }
            }
        }
        return false;
    }

        /*
        public boolean existeCicloEnDiGrafo() throws ExcepcionAristaYaExiste {
            MatrizDeAdyacencia<T> matrizDeAdy = new MatrizDeAdyacencia<>(elGrafo);
            return matrizDeAdy.existeCicloEnDiGrafo();
        }
        */

    public boolean seVisitoTodosLosVertices(ControlMarcados controlMarcados) {
        return controlMarcados.estanTodosLosVerticesMarcados();
    }
    public boolean seVisitoElVertice(ControlMarcados controlMarcados, int nroVertice) {
        return controlMarcados.estaMarcadoElVertice(nroVertice);
    }

    public double[][] armarMatrizConPeso(GrafoPesado<T> unGrafo) throws ExcepcionAristaNoExiste {
        int n = unGrafo.cantidadDeVertices();
        double[][] matrizConPeso = new double[n][n];
        List<T> vertices = (List<T>) unGrafo.getVertices();
        for (int i = 0; i < n; i++) {
            T verticeOrigen = vertices.get(i);
            for (int j = 0; j < n; j++) {
                T verticeDestino = vertices.get(j);
                if (i == j) {
                    matrizConPeso[i][j] = 0;
                } else if (unGrafo.existeAdyacencia(verticeOrigen, verticeDestino)) {
                    double peso = unGrafo.peso(verticeOrigen, verticeDestino);
                    matrizConPeso[i][j] = peso;
                } else {
                    matrizConPeso[i][j] = Double.POSITIVE_INFINITY;
                }

            }
        }
        return matrizConPeso;
    }

}