package ed2TDAS.grafos.nopesados;

import ed2TDAS.grafos.excepciones.ExcepcionAristaNoExiste;
import ed2TDAS.grafos.excepciones.ExcepcionAristaYaExiste;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Grafo <T extends Comparable<T>> {
    protected List<T> listaDeVertices;
    protected List<List<Integer>> listasDeAdyacencias;
    protected static final int NRO_DE_VERTICE_INVALIDO = -1;

    public Grafo() {
        listaDeVertices = new ArrayList<>();
        listasDeAdyacencias = new ArrayList<>();
    }

    public Grafo(Iterable<T> vertices) {
        this();
        for(T unVertice : vertices) {
            insertarVertice(unVertice);
        }
    }
    //valido por grafos  y digrafos
    public void insertarVertice(T nuevoVertice) {
        int nroVertice = getNroVertice(nuevoVertice);
        if (nroVertice != NRO_DE_VERTICE_INVALIDO) {
            throw new IllegalArgumentException("El vértice: " + nuevoVertice +
                    " ya pertenece a su grafo");
        }
        listaDeVertices.add(nuevoVertice);
        List<Integer> adyacenciasDelNuevoVertice = new ArrayList<>();
        listasDeAdyacencias.add(adyacenciasDelNuevoVertice);
    }
    //valido por grafos  y digrafos
    public void eliminarVertice(T unVertice) {
        validarVertice(unVertice);
        int nroVertice = getNroVertice(unVertice);
        listaDeVertices.remove(nroVertice);
        listasDeAdyacencias.remove(nroVertice);
        for (List<Integer> adyacentesDeUnVertice : listasDeAdyacencias) {
            adyacentesDeUnVertice.remove((Integer)nroVertice);
            for (int i = 0; i < adyacentesDeUnVertice.size(); i++) {
                int nroDeVerticeAdyacente = adyacentesDeUnVertice.get(i);
                if (nroDeVerticeAdyacente > nroVertice) {
                    nroDeVerticeAdyacente--;
                    adyacentesDeUnVertice.set(i, nroDeVerticeAdyacente);
                }
            }
        }
    }

    //valido por grafos
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
        if (nroDelVerticeOrigen != nroDelVerticeDestino) {
            List<Integer> adyacentesDelDestino = listasDeAdyacencias.get(nroDelVerticeDestino);
            adyacentesDelDestino.add(nroDelVerticeOrigen);
            Collections.sort(adyacentesDelDestino);
        }
    }

    //valido por grafos
    public void eliminarArista(T verticeOrigen, T verticeDestino)
        throws ExcepcionAristaNoExiste {
        if (!existeAdyacencia(verticeOrigen, verticeDestino)) {
            throw new ExcepcionAristaNoExiste();
        } else {
            int nroDelVerticeOrigen = getNroVertice(verticeOrigen);
            int nroDelVerticeDestino = getNroVertice(verticeDestino);
            List<Integer> adyacentesDelOrigen = listasDeAdyacencias.get(nroDelVerticeOrigen);
            adyacentesDelOrigen.remove((Integer) nroDelVerticeDestino);
            if (nroDelVerticeOrigen != nroDelVerticeDestino) {
                List<Integer> adyacentesDelDestino = listasDeAdyacencias.get(nroDelVerticeDestino);
                adyacentesDelDestino.remove((Integer) nroDelVerticeOrigen);
            }
        }
    }

    public void validarVertice(T unVertice) {
        int nroVertice = getNroVertice(unVertice);
        if (nroVertice == NRO_DE_VERTICE_INVALIDO) {
            throw new IllegalArgumentException("El vértice: " + unVertice +
                    " no pertenece a su grafo");
        }
    }

    public boolean existeAdyacencia(T verticeOrigen, T verticeDestino) {
        validarVertice(verticeOrigen);
        validarVertice(verticeDestino);
        int nroDelVerticeOrigen = getNroVertice(verticeOrigen);
        int nroDelVerticeDestino = getNroVertice(verticeDestino);
        List<Integer> adyacentesDelOrigen = listasDeAdyacencias.get(nroDelVerticeOrigen);
        return adyacentesDelOrigen.contains(nroDelVerticeDestino);
    }

    public int gradoDelVertice(T unVertice) {
        validarVertice(unVertice);
        int nroVertice = getNroVertice(unVertice);
        List<Integer> adyacentesDelVertice = listasDeAdyacencias.get(nroVertice);
        return adyacentesDelVertice.size();
    }

    public T getVerticePorIndice(int nroDelVertice) {
        if (nroDelVertice >= listaDeVertices.size()) {
            throw new IllegalArgumentException("Posicion de Vertice Invalida");
        }
        return listaDeVertices.get(nroDelVertice);
    }

    public Iterable<T> getAdyacentesDelVertice(T unVertice) {
        validarVertice((unVertice));
        int nroDelVertice = getNroVertice(unVertice);
        List<Integer> adyacentesDelVertice = listasDeAdyacencias.get(nroDelVertice);
        List<T> listaDeVerticesAdyacentes = new ArrayList<>();
        for (Integer nroDeVerticeAdyacente : adyacentesDelVertice) {
            T verticeAdyacente = listaDeVertices.get(nroDeVerticeAdyacente);
            listaDeVerticesAdyacentes.add(verticeAdyacente);
        }
        return listaDeVerticesAdyacentes;
    }

    public List<Integer> getPosAdyacentesDelVertice(T unVertice) {
        validarVertice(unVertice);
        int nroDelVertice = getNroVertice(unVertice);
        return listasDeAdyacencias.get(nroDelVertice);
    }

    public int getNroVertice(T unVertice) {
        for (int i = 0; i < listaDeVertices.size(); i++) {
            T verticeEnTurno = listaDeVertices.get(i);
            if (verticeEnTurno.compareTo(unVertice) == 0) {
                return i;
            }
        }
        return NRO_DE_VERTICE_INVALIDO;
    }

    public T getVertice(int indice) {
        if (indice >= listaDeVertices.size()) {
            throw new IllegalArgumentException("Posicion de Vertice Invalida");
        }
        return listaDeVertices.get(indice);
    }

    public int cantidadDeVertices() {
        return listasDeAdyacencias.size();
    }

    public Iterable<T> getVertices() {
        return listaDeVertices;
    }

    public int cantidadDeAristas2() {
        int cantAristas = 0;
        int indexVertice = 0;
        for (List<Integer> adyacentesDeUnVertice : listasDeAdyacencias) {
            cantAristas += adyacentesDeUnVertice.size();
            if (adyacentesDeUnVertice.contains((Integer) indexVertice)) {
                cantAristas += 1;
            }
            indexVertice++;
        }
        return cantAristas/2;
    }
    public int cantidadDeAristas() {
        int cantAristas = 0;
        int bucles = 0;
        for (T unVertice : listaDeVertices) {
            int nroVertice = getNroVertice(unVertice);
            List<Integer> adyacentesDelVertice = listasDeAdyacencias.get(nroVertice);
            if (adyacentesDelVertice.contains(nroVertice)) {
                bucles += 1;
            }
            cantAristas += adyacentesDelVertice.size();
        }
        return (bucles + cantAristas) / 2;
    }
}
