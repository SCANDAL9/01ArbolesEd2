package ed2TDAS.grafos;

import ed2TDAS.grafos.excepciones.ExcepcionAristaYaExiste;
import ed2TDAS.grafos.nopesados.Grafo;
import ed2TDAS.grafos.nopesados.utils.matrices.MatrizDeAdyacencia;
import ed2TDAS.grafos.nopesados.utils.matrices.MatrizDeCamino;

public class TestGrafos {
    public static void main(String[] args) throws ExcepcionAristaYaExiste {
        Grafo<Integer> unGrafo = new Grafo<Integer>();
        unGrafo.insertarVertice(1);
        unGrafo.insertarVertice(2);
        unGrafo.insertarVertice(3);
        unGrafo.insertarVertice(4);
        unGrafo.insertarVertice(5);
        unGrafo.insertarVertice(6);

        unGrafo.insertarArista(1,2);
        unGrafo.insertarArista(1,3);
        unGrafo.insertarArista(2,3);
        unGrafo.insertarArista(3,4);
        unGrafo.insertarArista(4,5);

        MatrizDeAdyacencia<Integer> matrizDeAdyacencia = new MatrizDeAdyacencia<>(unGrafo);

        System.out.println(matrizDeAdyacencia.toString());

        MatrizDeCamino<Integer> matrizDeCamino = new MatrizDeCamino<>(unGrafo);

        System.out.println(matrizDeCamino.toString());
    }
}
