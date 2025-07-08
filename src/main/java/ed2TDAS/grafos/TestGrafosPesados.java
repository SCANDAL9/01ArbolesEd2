package ed2TDAS.grafos;

import ed2TDAS.grafos.excepciones.ExcepcionAristaNoExiste;
import ed2TDAS.grafos.excepciones.ExcepcionAristaYaExiste;
import ed2TDAS.grafos.pesados.GrafoPesado;
import ed2TDAS.grafos.pesados.utils.CostoMinimo;

public class TestGrafosPesados {
    public static <T> void main(String[] args) throws ExcepcionAristaYaExiste, ExcepcionAristaNoExiste {
        GrafoPesado<String> unGrafo = new GrafoPesado<String>();
        //DiGrafoPesado<String> unGrafo = new DiGrafoPesado<String>();
        unGrafo.insertarVertice("1");
        unGrafo.insertarVertice("2");
        unGrafo.insertarVertice("3");
        unGrafo.insertarVertice("4");
        unGrafo.insertarVertice("5");
        unGrafo.insertarVertice("6");
        unGrafo.insertarVertice("7");
        unGrafo.insertarVertice("8");
        unGrafo.insertarVertice("9");
        unGrafo.insertarVertice("10");



        unGrafo.insertarArista("1","2", 5);
        unGrafo.insertarArista("1","4",8);
        unGrafo.insertarArista("1","3",10);
        unGrafo.insertarArista("2","6",5);
        unGrafo.insertarArista("2","4",6);
        unGrafo.insertarArista("3","4",7);
        unGrafo.insertarArista("3","5",8);
        unGrafo.insertarArista("3","8", 15);
        unGrafo.insertarArista("4","6",11);
        unGrafo.insertarArista("4","5",5);
        unGrafo.insertarArista("5","7",4);
        unGrafo.insertarArista("5","8",3);
        unGrafo.insertarArista("6","9",7);
        unGrafo.insertarArista("6","7",9);
        unGrafo.insertarArista("7","9",4);
        unGrafo.insertarArista("7","10",6);
        unGrafo.insertarArista("7","8",12);
        unGrafo.insertarArista("8","10",12);
        unGrafo.insertarArista("9","10",7);

        System.out.println(unGrafo.mostrarPesos());
        System.out.println(unGrafo.getVertices());

        CostoMinimo<String> prueba = new CostoMinimo<>(unGrafo);
        GrafoPesado<String> grafo2 = prueba.algoritmoDeKruskal();
        GrafoPesado<String> grafo3 = prueba.algoritmoDePrim();

        System.out.println(grafo2.mostrarPesos());
        System.out.println(grafo3.mostrarPesos());


    }
}
