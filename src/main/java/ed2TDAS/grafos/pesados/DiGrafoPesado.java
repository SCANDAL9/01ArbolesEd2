package ed2TDAS.grafos.pesados;

import ed2TDAS.grafos.excepciones.ExcepcionAristaNoExiste;
import ed2TDAS.grafos.excepciones.ExcepcionAristaYaExiste;

import java.util.Collections;
import java.util.List;

public class DiGrafoPesado<T extends Comparable<T>> extends GrafoPesado<T>{
    public DiGrafoPesado() {}

    public DiGrafoPesado(Iterable<T> vertices) {
        super(vertices);
    }

    @Override
    public void insertarArista(T verticeOrigen, T verticeDestino, double peso)
            throws ExcepcionAristaYaExiste {
        validarVertice(verticeOrigen);
        validarVertice(verticeDestino);
        if (existeAdyacencia(verticeOrigen, verticeDestino)) {
            throw new ExcepcionAristaYaExiste();
        }
        int nroDelVerticeOrigen = getNroVertice(verticeOrigen);
        int nroDelVerticeDestino = getNroVertice(verticeDestino);
        List<AdyacenteConPeso> adyacentesDelOrigen = listasDeAdyacencias.get(nroDelVerticeOrigen);
        adyacentesDelOrigen.add(new AdyacenteConPeso(nroDelVerticeDestino, peso));
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
            List<AdyacenteConPeso> adyacentesDelOrigen = listasDeAdyacencias.get(nroDelVerticeOrigen);
            adyacentesDelOrigen.remove(new AdyacenteConPeso(nroDelVerticeDestino));
            //adyacentesDelOrigen.removeIf(adyOrigen -> adyOrigen.getIndiceVertice() == nroDelVerticeDestino);
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
        List<AdyacenteConPeso> adyacentesDelVertice = listasDeAdyacencias.get(nroDelVertice);
        return adyacentesDelVertice.size();
    }
    public int gradoDeEntradaDelVertice(T unVertice) {
        validarVertice(unVertice);
        int nroDelVertice = getNroVertice(unVertice);
        int gradoDeEntrada = 0;
        for (List<AdyacenteConPeso> adyacentesDeUnVertice : listasDeAdyacencias) {
            if (adyacentesDeUnVertice.contains(new AdyacenteConPeso(nroDelVertice))) {
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
            List<AdyacenteConPeso> adyacentesDelVertice = listasDeAdyacencias.get(nroVertice);
            cantAristas += adyacentesDelVertice.size();
        }
        return cantAristas;
    }
}
