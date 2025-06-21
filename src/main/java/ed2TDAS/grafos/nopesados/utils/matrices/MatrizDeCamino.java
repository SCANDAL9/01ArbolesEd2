package ed2TDAS.grafos.nopesados.utils.matrices;

import ed2TDAS.grafos.nopesados.Grafo;

public class MatrizDeCamino <T extends Comparable<T>> extends MatrizDeAdyacencia<T> {

    public MatrizDeCamino(Grafo<T> grafo) {
        super(grafo);
        matrizDeAdyacencia = getMatrizDeCamino();
    }

    public boolean[][] getMatrizDeCamino() {
        int n = matrizDeAdyacencia.length;
        boolean[][] matrizPotencia = multiplicacionLogica(matrizDeAdyacencia, matrizDeAdyacencia);
        boolean[][] matrizAcumulada = sumaLogica(matrizDeAdyacencia, matrizPotencia);
        for (int i = 1; i < n; i++) {
            matrizPotencia = multiplicacionLogica(matrizPotencia, matrizDeAdyacencia);
            matrizAcumulada = sumaLogica(matrizAcumulada, matrizPotencia);
        }
        return matrizAcumulada;
    }

    private boolean[][] multiplicacionLogica(boolean[][] A, boolean[][] B) {
        int n = A.length;
        boolean[][] producto = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                producto[i][j] = false;
                for (int k = 0; k < n; k++) {
                    producto[i][j] = producto[i][j] || (A[i][k] && B[k][j]);
                    //producto[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return producto;
    }

    private boolean[][] sumaLogica(boolean[][] A, boolean[][] B) {
        int n = A.length;
        boolean[][] resultado = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                resultado[i][j] = A[i][j] || B[i][j];
            }
        }
        return resultado;
    }
}
