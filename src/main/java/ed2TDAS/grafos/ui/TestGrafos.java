package ed2TDAS.grafos.ui;

import ed2TDAS.grafos.excepciones.ExcepcionAristaNoExiste;
import ed2TDAS.grafos.excepciones.ExcepcionAristaYaExiste;
import ed2TDAS.grafos.nopesados.DiGrafo;
import ed2TDAS.grafos.nopesados.utils.matrices.MatrizDeAdyacencia;
import ed2TDAS.grafos.nopesados.utils.recorridos.DFSModificadoV1;

public class TestGrafos {
    public static void main(String[] args) throws ExcepcionAristaYaExiste, ExcepcionAristaNoExiste {
        //Grafo<String> unGrafo = new Grafo<String>();
        DiGrafo<String> unGrafo = new DiGrafo<String>();
        /*unGrafo.insertarVertice("A");
        unGrafo.insertarVertice("B");
        unGrafo.insertarVertice("C");
        unGrafo.insertarVertice("D");
        unGrafo.insertarVertice("E");
        //unGrafo.insertarVertice("F");
        //unGrafo.insertarVertice("G");


        unGrafo.insertarArista("A","B");
        unGrafo.insertarArista("B","D");
        unGrafo.insertarArista("D","B");
        unGrafo.insertarArista("B","E");
        unGrafo.insertarArista("E","C");
        unGrafo.insertarArista("C","E");
        unGrafo.insertarArista("C","C");*/
        //unGrafo.insertarArista("C","A");
        //unGrafo.insertarArista("F","G");

        unGrafo.insertarVertice("0");
        unGrafo.insertarVertice("1");
        unGrafo.insertarVertice("2");
        unGrafo.insertarVertice("3");
        unGrafo.insertarVertice("4");
        unGrafo.insertarVertice("5");
        unGrafo.insertarVertice("6");
        unGrafo.insertarVertice("7");
        unGrafo.insertarVertice("8");

        unGrafo.insertarArista("0","8");
        unGrafo.insertarArista("0","1");
        unGrafo.insertarArista("3","1");
        unGrafo.insertarArista("3","4");
        unGrafo.insertarArista("3","0");

        unGrafo.insertarArista("2","5");
        unGrafo.insertarArista("7","6");
        unGrafo.insertarArista("2","7");
        unGrafo.insertarArista("6","2");

        MatrizDeAdyacencia<String> matrizDeAdyacencia = new MatrizDeAdyacencia<>(unGrafo);

        System.out.println(matrizDeAdyacencia.toStringMatriz(matrizDeAdyacencia.getMatrizDeAdyacencia()));

        //System.out.println(matrizDeAdyacencia.esFuertementeConexo());

        //System.out.println(matrizDeAdyacencia.cantIslasEnGrafoDirigido());

        //System.out.println(matrizDeAdyacencia.esDebilmenteConexo2());

        //System.out.println(matrizDeAdyacencia.esDebilmenteConexo());

        DFSModificadoV1 dfs = new DFSModificadoV1<>(unGrafo);
        System.out.println(dfs.existeCicloEnDiGrafo());
    }
}
