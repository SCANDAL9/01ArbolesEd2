package ed2TDAS.grafos.nopesados;

import ed2TDAS.grafos.excepciones.ExcepcionAristaNoExiste;
import ed2TDAS.grafos.excepciones.ExcepcionAristaYaExiste;

import java.util.Collections;
import java.util.List;

public class DiGrafo <T extends Comparable<T>> extends Grafo <T> {

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

    @Override
    public void eliminarArista(T verticeOrigen, T verticeDestino)
            throws ExcepcionAristaNoExiste {
        if (!existeAdyacencia(verticeOrigen, verticeDestino)) {
            throw new ExcepcionAristaNoExiste();
        } else {
            int nroDelVerticeOrigen = getNroVertice(verticeOrigen);
            int nroDelVerticeDestino = getNroVertice(verticeDestino);
            List<Integer> adyacentesDelOrigen = listasDeAdyacencias.get(nroDelVerticeOrigen);
            adyacentesDelOrigen.remove((Integer) nroDelVerticeDestino);
        }
    }

    @Override
    public int gradoDelVertice(T unVertice) {
        //throw new UnsupportedOperationException("Método no soportado en esta clase");
        int gradoDeEntrada = gradoDeEntradaDelVertice(unVertice);
        int gradoDeSalida = gradoDeSalidaDelVertice(unVertice);
        return gradoDeEntrada + gradoDeSalida;
    }

    public int gradoDeSalidaDelVertice(T unVertice) {
        validarVertice(unVertice);
        int nroDelVertice = getNroVertice(unVertice);
        List<Integer> adyacentesDelVertice = listasDeAdyacencias.get(nroDelVertice);
        return adyacentesDelVertice.size();
    }
    public int gradoDeEntradaDelVertice(T unVertice) {
        validarVertice(unVertice);
        int nroDelVertice = getNroVertice(unVertice);
        int gradoDeEntrada = 0;
        for (List<Integer> adyacentesDeUnVertice : listasDeAdyacencias) {
            if (adyacentesDeUnVertice.contains(nroDelVertice)) {
                gradoDeEntrada++;
            }
        }
        return gradoDeEntrada;
    }
    @Override
    public int cantidadDeAristas() {
        int cantAristas = 0;
        for (T unVertice : listaDeVertices) {
            int nroVertice = getNroVertice(unVertice);
            List<Integer> adyacentesDelVertice = listasDeAdyacencias.get(nroVertice);
            cantAristas += adyacentesDelVertice.size();
        }
        return cantAristas;
    }
}
