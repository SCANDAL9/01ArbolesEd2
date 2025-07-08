package ed2TDAS.grafos.pesados.utils;

import ed2TDAS.grafos.excepciones.ExcepcionAristaNoExiste;
import ed2TDAS.grafos.excepciones.ExcepcionAristaYaExiste;
import ed2TDAS.grafos.pesados.DiGrafoPesado;
import ed2TDAS.grafos.pesados.GrafoPesado;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class CostoMinimo<T extends Comparable<T>> {
    GrafoPesado<T> grafoPesao;
    public CostoMinimo(GrafoPesado<T> grafoPesado) {
        grafoPesao = grafoPesado;
    }

    public GrafoPesado<T> algoritmoDeKruskal() throws ExcepcionAristaNoExiste, ExcepcionAristaYaExiste {
        if (!(grafoPesao instanceof GrafoPesado<T>)) {
            throw new IllegalArgumentException("Se requiere un Grafo No Dirigido");
        }
        GrafoPesado<T> grafoPAux = new GrafoPesado<T>(grafoPesao.getVertices());
        List<AristaConPeso> aristasConPesos = listaDeAristasOrdenadasPorPeso(grafoPesao);
        UtilsGrafosPesados<T> utils = new UtilsGrafosPesados<>();
        for (AristaConPeso arista : aristasConPesos) {
            T verticeOrigen = grafoPesao.getVertice(arista.getOrigen());
            T verticeDestino = grafoPesao.getVertice(arista.getDestino());
            double peso = arista.getPeso();
            grafoPAux.insertarArista(verticeOrigen, verticeDestino, peso);
            if (utils.existeCicloEnGrafo(grafoPAux)) {
                grafoPAux.eliminarArista(verticeOrigen, verticeDestino);
            }
        }
        return grafoPAux;
    }
    private List<AristaConPeso> listaDeAristasOrdenadasPorPeso(GrafoPesado<T> elGrafo) throws ExcepcionAristaNoExiste {
        List<T> vertices = (List<T>) elGrafo.getVertices();
        List<AristaConPeso> listaDeAristas = new ArrayList<>();
        for (T vertice : vertices) {
            int nroVertice = elGrafo.getNroVertice(vertice);
            List<T> adyacencias = (List<T>) elGrafo.getAdyacentesDelVertice(vertice);
            for (T adyacente : adyacencias) {
                int nroAdyacente = elGrafo.getNroVertice(adyacente);
                if (elGrafo instanceof DiGrafoPesado<T> || (nroAdyacente > nroVertice)) {
                    double peso = elGrafo.peso(vertice, adyacente);
                    listaDeAristas.add(new AristaConPeso(nroVertice, nroAdyacente, peso));
                }
            }
        }
        Collections.sort(listaDeAristas);
        return listaDeAristas;
    }

    public GrafoPesado<T> algoritmoDePrim() throws ExcepcionAristaNoExiste, ExcepcionAristaYaExiste {
        if (!(grafoPesao instanceof GrafoPesado<T>)) {
            throw new IllegalArgumentException("Se requiere un Grafo No Dirigido");
        }
        GrafoPesado<T> grafoPAux = new GrafoPesado<T>();
        grafoPAux.insertarVertice(grafoPesao.getVertice(0));
        while (grafoPAux.cantidadDeVertices() < grafoPesao.cantidadDeVertices()) {
            double pesoMenor = Double.POSITIVE_INFINITY;
            T origenMin = null;
            T destinoMin = null;

            List<T> vertices = (List<T>) grafoPAux.getVertices();
            for (T vertice : vertices) {
                List<T> adyacencias = (List<T>) grafoPesao.getAdyacentesDelVertice(vertice);
                for (T unAdyacente : adyacencias) {
                    if (!vertices.contains(unAdyacente)) {
                        double peso = grafoPesao.peso(vertice, unAdyacente);
                        if (peso < pesoMenor) {
                            origenMin = vertice;
                            destinoMin = unAdyacente;
                            pesoMenor = peso;
                        }
                    }
                }
            }
            if (origenMin == null && destinoMin == null) break;
            if (!vertices.contains(destinoMin) || !grafoPAux.existeAdyacencia(origenMin, destinoMin)) {
                grafoPAux.insertarVertice(destinoMin);
                grafoPAux.insertarArista(origenMin, destinoMin, pesoMenor);
            }
        }
        return grafoPAux;
    }


    public String algoritmoDeFloydWarshall(T verticeOrigen, T verticeDestino) throws ExcepcionAristaNoExiste {
        if (!(grafoPesao instanceof DiGrafoPesado<T>)) {
            throw new IllegalArgumentException("Se requiere un Grafo Dirigido");
        }

        int n = grafoPesao.cantidadDeVertices();
        class Matrices {
            int[][] Pred;
            double[][] P;
            public Matrices () throws ExcepcionAristaNoExiste {
                Pred = new int[n][n];
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        Pred[i][j] = -1;
                    }
                }
                UtilsGrafosPesados utils = new UtilsGrafosPesados<>();
                P = utils.armarMatrizConPeso(grafoPesao);
                armarMatrices();
            }

            public void armarMatrices () {
                for (int k = 0; k < n; k++) {
                    for (int i = 0; i < n; i++) {
                        for (int j = 0; j < n; j++) {
                            if (P[i][j] > (P[i][k] + P[k][j])) {
                                P[i][j] = P[i][k] + P[k][j];
                                Pred[i][j] = k;
                            }
                        }
                    }
                }
            }

            public String mostrarCamino(T origen, T destino) {
                grafoPesao.validarVertice(origen);
                grafoPesao.validarVertice(destino);
                int nroOrigen = grafoPesao.getNroVertice(origen);
                int nroDestino = grafoPesao.getNroVertice(destino);
                StringBuilder sb = new StringBuilder();

                if (P[nroOrigen][nroDestino] == Double.POSITIVE_INFINITY) {
                    sb.append("No hay camino");
                    return sb.toString();
                }

                sb.append("Camino de costo minimo de ").append(origen)
                        .append(" a vertice ").append(destino)
                        .append(" es: ").append(P[nroOrigen][nroDestino])
                        .append("\n");

                sb.append("El camino tiene la siguiente secuencia: ");
                sb.append(origen).append(" -> ")
                        .append(imprimirIntermedios(nroOrigen,nroDestino))
                        .append(destino);
                return sb.toString();
            }

            public String imprimirIntermedios(int origen, int destino) {
                int intermedio = Pred[origen][destino];
                if (intermedio == -1) {
                    return "";
                }
                StringBuilder sb = new StringBuilder();

                if (intermedio != destino) {
                    sb.append(imprimirIntermedios(origen, intermedio))
                            .append(grafoPesao.getVertice(intermedio))
                            .append(imprimirIntermedios(intermedio, destino));
                }
                return sb.append(" -> ").toString();
            }

        }

        Matrices floydW = new Matrices();
        return floydW.mostrarCamino(verticeOrigen,verticeDestino);
    }
}
