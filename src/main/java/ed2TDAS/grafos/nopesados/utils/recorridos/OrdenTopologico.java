package ed2TDAS.grafos.nopesados.utils.recorridos;

import ed2TDAS.grafos.nopesados.DiGrafo;
import ed2TDAS.grafos.nopesados.Grafo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class OrdenTopologico <T extends Comparable<T>> {
    protected List<T> listaEnOrden;
    protected Grafo<T> unGrafo;

    public OrdenTopologico(DiGrafo<T> grafo) {
        if (!(grafo instanceof DiGrafo<T>)) {
            throw new IllegalArgumentException("Esta aplicacion solo puede aplicarse a DiGrafos");
        }
        listaEnOrden = new ArrayList<>();
        unGrafo = grafo;

    }

    public void ordenamientoTopologico() {

    }

    public List<T> getListaEnOrden() {
        return listaEnOrden;
    }

}
