package ed2TDAS.grafos.nopesados.utils.matrices;

import ed2TDAS.grafos.excepciones.ExcepcionAristaYaExiste;
import ed2TDAS.grafos.nopesados.DiGrafo;
import ed2TDAS.grafos.nopesados.Grafo;
import ed2TDAS.grafos.nopesados.utils.recorridos.DFS;
import ed2TDAS.grafos.nopesados.utils.recorridos.DFSModificadoV1;

import javax.swing.table.TableRowSorter;
import java.util.List;

public class MatrizDeAdyacencia <T extends Comparable<T>> {
    protected List<T> listaDeVertices;
    protected boolean[][] matrizDeAdyacencia;
    protected boolean esDiGrafo;
    protected boolean esGrafo;
    protected Grafo<T> unGrafo;

    public MatrizDeAdyacencia(Grafo<T> grafo) {
        if (grafo instanceof DiGrafo<T>) {
            esDiGrafo = true;
            esGrafo = false;
            System.out.println("DIGRAFO");
        } else {
            esGrafo = true;
            esDiGrafo = false;
            System.out.println("GRAFO");
        }
        this.unGrafo = grafo;
        armarMatrizDeAdyacencia();
    }

    private boolean esDiGrafo() {
        return esDiGrafo;
    }
    private boolean esGrafoNoDirigido() {
        return esGrafo;
    }

    private void armarMatrizDeAdyacencia(){
        int cantDeVertices = unGrafo.cantidadDeVertices();
        matrizDeAdyacencia = new boolean[cantDeVertices][cantDeVertices];

        listaDeVertices = (List<T>) unGrafo.getVertices();
        for (int i = 0; i < cantDeVertices; i++) {
            List<Integer> adyacencias = unGrafo.getPosAdyacentesDelVertice(listaDeVertices.get(i));
            for (int j = 0; j < cantDeVertices; j++) {
                matrizDeAdyacencia[i][j] = adyacencias.contains(j) ? true : false;
            }
        }
    }

    public void prueba() {
        System.out.println(unGrafo.getVertices());
        Grafo<T> prueba = new Grafo<>();


        for (T vertice : listaDeVertices) {
            prueba.insertarVertice(vertice);
        }
        T unT = (T) "Z";
        prueba.insertarVertice(unT);

        System.out.println(prueba.getVertices());
        System.out.println(unGrafo.getVertices());

    }

    public boolean[][] getMatrizDeAdyacencia() {
        return matrizDeAdyacencia;
    }

    private void setMatrizDeAdyacencia(boolean[][] matrizDeAdyacencia) {
        this.matrizDeAdyacencia = matrizDeAdyacencia;
    }

    private void validarNroDeVertice(int nroDelVertice) {
        if (nroDelVertice < 0 || nroDelVertice >= matrizDeAdyacencia.length) {
            throw new IllegalArgumentException("El valor: " + nroDelVertice +
                    " no pertenece a su grafo");
        }
    }

    public boolean esFuertementeConexo() {
        if (!esDiGrafo) {
            throw new IllegalArgumentException("Debe usarse un Digrafo");
        }
        MatrizDeCamino<T> P = new MatrizDeCamino<>();
        boolean[][] matrizAux = P.getMatrizDeCaminoWarshall(matrizDeAdyacencia);
        System.out.println(toStringMatriz(matrizAux));
        for (int i = 0; i < matrizAux.length; i++) {
            for (int j = 0; j < matrizAux[i].length; j++) {
                if (!matrizAux[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean esDebilmenteConexo() throws ExcepcionAristaYaExiste {
        if (!esDiGrafo) {
            throw new IllegalArgumentException("Debe usarse un Digrafo");
        }
        if (!esFuertementeConexo()) {
            Grafo<T> grafoAux = armarGrafoM(new Grafo<>());
            MatrizDeAdyacencia<T> matrizNoDirigida = new MatrizDeAdyacencia<>(grafoAux);
            if (matrizNoDirigida.esConexo()) {
                return true;
            }
        }
        return false;
    }

    public boolean esDebilmenteConexo2() {
        if (!esDiGrafo) {
            throw new IllegalArgumentException("Debe usarse un Grafo Dirigido");
        }

        DFS<T> dfs = new DFS<>(unGrafo, unGrafo.getVertice(0));
        boolean hallado = true;
        if (!dfs.seVisitoTodosLosVertices()) {
            for (T vertice : listaDeVertices) {
                if (!dfs.seVisitoElVertice(vertice)) {
                    List<T> adyacentes = (List<T>) unGrafo.getAdyacentesDelVertice(vertice);
                    for (T unAdyacente : adyacentes) {
                        if (dfs.seVisitoElVertice(unAdyacente)) {
                            dfs.ejecutarRecorrido(vertice);
                            hallado = true;
                            break;
                        } else {
                            hallado = false;
                        }
                    }
                }
            }
        }
        return hallado;
    }

    private Grafo<T> armarGrafoM(Grafo<T> grafoAux) throws ExcepcionAristaYaExiste {
        if (grafoAux.cantidadDeVertices() > 0) {
            throw new IllegalArgumentException("El grafo debe estar vacio");
        }

        for (T vertice : listaDeVertices) {
            grafoAux.insertarVertice(vertice);
        }

        for (int i = 0; i < unGrafo.cantidadDeVertices(); i++) {
            for (int j = 0; j < unGrafo.cantidadDeVertices(); j++) {
                if (matrizDeAdyacencia[i][j]) {
                    T verticeOrigen = unGrafo.getVertice(i);
                    T verticeDestino = unGrafo.getVertice(j);
                    if (!grafoAux.existeAdyacencia(verticeOrigen, verticeDestino)) {
                        grafoAux.insertarArista(verticeOrigen, verticeDestino);
                    }
                }
            }
        }
        return grafoAux;
    }

    public boolean esConexo() {
        if (!esGrafo) {
            throw new IllegalArgumentException("Debe usarse un Grafo No Dirigido");
        }
        if (listaDeVertices.isEmpty()) {
            throw new IllegalStateException("No existen vértices en el grafo");
        }
        DFS<T> recorridoDFS = new DFS<>(unGrafo, listaDeVertices.get(0));
        return recorridoDFS.seVisitoTodosLosVertices();
    }

    public boolean existeCicloEnDiGrafo() throws ExcepcionAristaYaExiste {
        if (!esDiGrafo) {
            throw new IllegalArgumentException("Debe usarse un Grafo No Dirigido");
        }
        MatrizDeCamino<T> P = new MatrizDeCamino<>();
        boolean[][] matrizAux = P.getMatrizDeCaminoWarshall(matrizDeAdyacencia);
        for (int i = 0; i < matrizAux.length; i++) {
            if (matrizAux[i][i]) {
                return true;
            }
        }
        return false;
        //Y sin matriz de caminos?
    }

    public int cantIslasEnGrafoNoDirigido(){
        if (!esGrafo) {
            throw new IllegalArgumentException("Debe usarse un Grafo No Dirigido");
        }
        if (listaDeVertices.isEmpty()) return 0;

        DFS<T> dfs = new DFS<>(unGrafo, listaDeVertices.get(0));
        int cantIslas = 0;
        for (T vertice : listaDeVertices) {
            if (!dfs.seVisitoElVertice(vertice)) {
                dfs.ejecutarRecorrido(vertice);
                cantIslas++;
            }
        }
        return cantIslas + 1;
    }

    public int cantIslasEnGrafoDirigido(){
        if (!esDiGrafo) {
            throw new IllegalArgumentException("Debe usarse un Grafo Dirigido");
        }
        if (listaDeVertices.isEmpty()) return 0;

        DFS<T> dfs = new DFS<>(unGrafo, unGrafo.getVertice(0));
        int cantIslas = 0;
        while (!dfs.seVisitoTodosLosVertices()) {
            for (T vertice : listaDeVertices) {
                if (!dfs.seVisitoElVertice(vertice)) {
                    List<T> adyacentes = (List<T>) unGrafo.getAdyacentesDelVertice(vertice);
                    for (T unAdyacente : adyacentes) {
                        if (dfs.seVisitoElVertice(unAdyacente)) {
                            dfs.ejecutarRecorrido(vertice);
                            break;
                        }
                    }
                }
            }
            for (T vertice : listaDeVertices) {
                if (!dfs.seVisitoElVertice(vertice)) {
                    dfs.ejecutarRecorrido(vertice);
                    break;
                }
            }
            cantIslas++;
        }
        return cantIslas + 1;
    }

    public String toStringMatriz(boolean[][] matriz) {
        StringBuilder sb = new StringBuilder();
        int n = matriz.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(matriz[i][j] ? "1" : "0").append(" ");
            }
            sb.append(" | ").append(listaDeVertices.get(i));
            sb.append("\n");
        }
        return sb.toString();
    }

}
