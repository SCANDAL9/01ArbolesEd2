package ed2TDAS.grafos.nopesados.utils.recorridos;

import ed2TDAS.grafos.nopesados.Grafo;

public class DFS <T extends Comparable<T>> extends RecorridoEnGrafo<T> {
    public DFS(Grafo<T> unGrafo, T verticeDePartida) {
        super(unGrafo, verticeDePartida);
    }

    public void ejecutarRecorrido(T verticeEnTurno) {
        elGrafo.validarVertice(verticeEnTurno);
        int nroDelVerticeEnTurno = elGrafo.getNroVertice(verticeEnTurno);
        controlMarcados.marcarVertices(nroDelVerticeEnTurno);
        elRecorrido.add(verticeEnTurno);
        Iterable<T> adyacentesAProcesar = elGrafo.getAdyacentesDelVertice(verticeEnTurno);
        for (T adyacente : adyacentesAProcesar) {
            int nroDelVerticeAdyacente = elGrafo.getNroVertice(adyacente);
            if (!controlMarcados.estaMarcadoElVertice(nroDelVerticeAdyacente)) {
                ejecutarRecorrido(adyacente);
            }
        }
    }
}
