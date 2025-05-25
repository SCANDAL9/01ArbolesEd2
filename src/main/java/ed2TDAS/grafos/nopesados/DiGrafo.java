package ed2TDAS.grafos.nopesados;

import ed2TDAS.grafos.excepciones.ExcepcionAristaYaExiste;

import java.util.Collections;
import java.util.List;

public class DiGrafo <T extends Comparable<T>>
    extends Grafo <T> {

    public DiGrafo() {}

    public DiGrafo(Iterable<T> vertices) {
        super(vertices);
    }

    @Override
    public void insertarArista(T verticeOrigen, T verticeDestino)
            throws ExcepcionAristaYaExiste {
        validarVertice(verticeOrigen);
        validarVertice(verticeDestino);
        if (existeAdyacencia(verticeOrigen, verticeDestino)) {
            throw new ExcepcionAristaYaExiste();
        }
        int nroDelVerticeOrigen = getNroVertice(verticeOrigen);
        int nroDelVerticeDestino = getNroVertice(verticeDestino);
        List<Integer> adyacentesDelOrigen = listasDeAdyacencias.get(nroDelVerticeOrigen);
        adyacentesDelOrigen.add(nroDelVerticeDestino);
        Collections.sort(adyacentesDelOrigen);
    }
}
