package ed2TDAS.grafos.pesados;

import ed2TDAS.grafos.excepciones.ExcepcionAristaNoExiste;
import ed2TDAS.grafos.excepciones.ExcepcionAristaYaExiste;
import ed2TDAS.grafos.nopesados.DiGrafo;
import ed2TDAS.grafos.nopesados.utils.matrices.MatrizDeAdyacencia;
import ed2TDAS.grafos.nopesados.utils.recorridos.DFSModificadoV1;

public class TestGrafosPesados {
    public static void main(String[] args) throws ExcepcionAristaYaExiste, ExcepcionAristaNoExiste {
        GrafoPesado<String> unGrafo = new GrafoPesado<String>();
        //DiGrafoPesado<String> unGrafo = new DiGrafoPesado<String>();
        unGrafo.insertarVertice("A");
        unGrafo.insertarVertice("E");
        unGrafo.insertarVertice("H");
        unGrafo.insertarVertice("K");
        unGrafo.insertarVertice("M");
        unGrafo.insertarVertice("T");



        unGrafo.insertarArista("A","K", 20);
        unGrafo.insertarArista("A","M",80);
        unGrafo.insertarArista("E","K",20);
        unGrafo.insertarArista("H","A",50);
        unGrafo.insertarArista("H","E",15);
        unGrafo.insertarArista("K","H",40);
        unGrafo.insertarArista("K","T",70);
        unGrafo.insertarArista("M","K", 100);
        unGrafo.insertarArista("M","E",60);
        unGrafo.insertarArista("T","H",5);

        System.out.println(unGrafo.mostrarPesos());
        System.out.println(unGrafo.getVertices());

        unGrafo.eliminarArista("M","K");
        unGrafo.eliminarArista("H","A");
        unGrafo.eliminarArista("T","H");

        unGrafo.eliminarVertice("T");
        unGrafo.eliminarVertice("A");
        /*unGrafo.insertarVertice("0");
        unGrafo.insertarVertice("1");
        unGrafo.insertarVertice("2");
        unGrafo.insertarVertice("3");
        /*unGrafo.insertarVertice("4");
        unGrafo.insertarVertice("5");
        unGrafo.insertarVertice("6");
        unGrafo.insertarVertice("7");
        unGrafo.insertarVertice("8");

        unGrafo.insertarArista("0","3",30);
        unGrafo.insertarArista("0","2",20);
        unGrafo.insertarArista("2","3",100);
        unGrafo.insertarArista("2","1",5);
        unGrafo.insertarArista("1","2",40);
        unGrafo.insertarArista("0","8");
        unGrafo.insertarArista("0","1");
        unGrafo.insertarArista("3","1");
        unGrafo.insertarArista("3","4");
        unGrafo.insertarArista("3","0");

        unGrafo.insertarArista("2","5");
        unGrafo.insertarArista("7","6");
        unGrafo.insertarArista("2","7");
        unGrafo.insertarArista("6","2");*/

        System.out.println(unGrafo.mostrarPesos());
        System.out.println(unGrafo.getVertices());
    }
}
