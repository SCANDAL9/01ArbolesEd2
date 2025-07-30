package ed2TDAS.grafos.nopesados.utils.recorridos;

import ed2TDAS.grafos.nopesados.Grafo;
import ed2TDAS.grafos.nopesados.utils.ControlMarcados;

import java.util.ArrayList;
import java.util.List;

public abstract class RecorridoEnGrafo <T extends Comparable<T>> {
    protected final Grafo<T> elGrafo;
    protected final ControlMarcados controlMarcados;
    protected final List<T> elRecorrido;

    public RecorridoEnGrafo(Grafo<T> unGrafo, T verticeDePartida) {
        elGrafo = unGrafo;
        controlMarcados = new ControlMarcados(elGrafo.cantidadDeVertices());
        elRecorrido = new ArrayList<T>();
        ejecutarRecorrido(verticeDePartida);
    }

    public abstract void ejecutarRecorrido(T verticeDePartida);

    public List<T> getElRecorrido() {
        return elRecorrido;
    }

    public boolean seVisitoElVertice(T unVertice) {
        elGrafo.validarVertice(unVertice);
        int nroVertice = elGrafo.getNroVertice(unVertice);
        return controlMarcados.estaMarcadoElVertice(nroVertice);
    }

    public boolean seVisitoTodosLosVertices() {
        return controlMarcados.estanTodosLosVerticesMarcados();
    }
}
