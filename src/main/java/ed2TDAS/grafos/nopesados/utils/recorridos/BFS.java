package ed2TDAS.grafos.nopesados.utils.recorridos;

import ed2TDAS.grafos.nopesados.Grafo;

import java.util.LinkedList;
import java.util.Queue;

public class BFS <T extends Comparable<T>> extends RecorridoEnGrafo<T> {
    public BFS(Grafo<T> unGrafo, T verticeDePartida) {
        super(unGrafo, verticeDePartida);
    }

    @Override
    public void ejecutarRecorrido(T verticeEnTurno) {
        elGrafo.validarVertice(verticeEnTurno);
        Queue<Integer> colaDeNroVertices = new LinkedList<>();
        int nroDelVerticeEnTurno = elGrafo.getNroVertice(verticeEnTurno);
        colaDeNroVertices.add(nroDelVerticeEnTurno);
        controlMarcados.marcarVertices(nroDelVerticeEnTurno);
        while (!colaDeNroVertices.isEmpty()) {
            int nroDelVerticeAProcesar = colaDeNroVertices.poll();
            T verticeAProcesar = elGrafo.getVerticePorIndice(nroDelVerticeAProcesar);
            elRecorrido.add(verticeAProcesar);
            Iterable<T> adyacentesAProcesar = elGrafo.getAdyacentesDelVertice(verticeAProcesar);
            for (T adyacente : adyacentesAProcesar) {
                int nroDelVerticeAdyacente = elGrafo.getNroVertice(adyacente);
                if (!controlMarcados.estaMarcadoElVertice(nroDelVerticeAdyacente)) {
                    colaDeNroVertices.add(nroDelVerticeAdyacente);
                    controlMarcados.marcarVertices(nroDelVerticeAdyacente);
                }
            }
        }
    }
}
