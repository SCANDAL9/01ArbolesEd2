package ed2TDAS.grafos.nopesados.utils.matrices;

import ed2TDAS.grafos.nopesados.Grafo;

import java.util.ArrayList;
import java.util.List;

public class MatrizDeAdyacencia <T extends Comparable<T>> {
    protected boolean[][] matrizDeAdyacencia;

    public MatrizDeAdyacencia(Grafo grafo) {
        int cantDeVertices = grafo.cantidadDeVertices();
        matrizDeAdyacencia = new boolean[cantDeVertices][cantDeVertices];
        List<T> vertices = (List<T>) grafo.getVertices();
        for (int i = 0; i < cantDeVertices; i++) {
            List<Integer> adyacencias = grafo.getPosAdyacentesDelVertice(vertices.get(i));
            for (int j = 0; j < cantDeVertices; j++) {
                matrizDeAdyacencia[i][j] = adyacencias.contains(j) ? true : false;
            }
        }
    }

    public boolean[][] getMatrizDeAdyacencia() {
        return matrizDeAdyacencia;
    }
    private void validarNroDeVertice(int nroDelVertice) {
        if (nroDelVertice < 0 || nroDelVertice >= matrizDeAdyacencia.length) {
            throw new IllegalArgumentException("El valor: " + nroDelVertice +
                    " no pertenece a su grafo");
        }
    }
    public List<Integer> getListaDeAdyacencias(int nroDelVertice) {
        validarNroDeVertice(nroDelVertice);
        List<Integer> listaDeAdyacencias = new ArrayList<>();
        for (int i = 0; i < matrizDeAdyacencia.length; i++) {
            if (matrizDeAdyacencia[nroDelVertice][i] == true) {
                listaDeAdyacencias.add(i);
            }
        }
        return listaDeAdyacencias;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int n = matrizDeAdyacencia.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(matrizDeAdyacencia[i][j] ? "1" : "0").append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}
