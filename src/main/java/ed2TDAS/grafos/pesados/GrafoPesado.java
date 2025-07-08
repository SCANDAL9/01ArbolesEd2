package ed2TDAS.grafos.pesados;

import ed2TDAS.grafos.excepciones.ExcepcionAristaNoExiste;
import ed2TDAS.grafos.excepciones.ExcepcionAristaYaExiste;
import ed2TDAS.grafos.pesados.utils.AdyacenteConPeso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GrafoPesado <T extends Comparable<T>> {
    protected List<T> listaDeVertices;
    protected List<List<AdyacenteConPeso>> listasDeAdyacencias;
    protected static final int NRO_DE_VERTICE_INVALIDO = -1;

    public GrafoPesado() {
        listaDeVertices = new ArrayList<>();
        listasDeAdyacencias = new ArrayList<>();
    }

    public GrafoPesado(Iterable<T> vertices) {
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
        List<AdyacenteConPeso> adyacenciasDelNuevoVertice = new ArrayList<>();
        listasDeAdyacencias.add(adyacenciasDelNuevoVertice);
    }
    //valido por grafos  y digrafos
    public void eliminarVertice(T unVertice) {
        validarVertice(unVertice);
        int nroVertice = getNroVertice(unVertice);
        listaDeVertices.remove(nroVertice);
        listasDeAdyacencias.remove(nroVertice);
        for (List<AdyacenteConPeso> adyacentesDeUnVertice : listasDeAdyacencias) {
            adyacentesDeUnVertice.remove(new AdyacenteConPeso(nroVertice));
            for (int i = 0; i < adyacentesDeUnVertice.size(); i++) {
                AdyacenteConPeso nroDeVerticeAdyacente = adyacentesDeUnVertice.get(i);
                if (nroDeVerticeAdyacente.getIndiceVertice() > nroVertice) {
                    nroDeVerticeAdyacente.setIndiceVertice(
                            nroDeVerticeAdyacente.getIndiceVertice() - 1);
                }
            }
        }
    }

    //valido por grafos
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
        if (nroDelVerticeOrigen != nroDelVerticeDestino) {
            List<AdyacenteConPeso> adyacentesDelDestino = listasDeAdyacencias.get(nroDelVerticeDestino);
            adyacentesDelDestino.add(new AdyacenteConPeso(nroDelVerticeOrigen, peso));
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
            List<AdyacenteConPeso> adyacentesDelOrigen = listasDeAdyacencias.get(nroDelVerticeOrigen);
            //adyacentesDelOrigen.removeIf(adyOrigen -> adyOrigen.getIndiceVertice() == nroDelVerticeDestino);
            adyacentesDelOrigen.remove(new AdyacenteConPeso(nroDelVerticeDestino));
            if (nroDelVerticeOrigen != nroDelVerticeDestino) {
                List<AdyacenteConPeso> adyacentesDelDestino = listasDeAdyacencias.get(nroDelVerticeDestino);
                //adyacentesDelDestino.removeIf(adyDestino -> adyDestino.getIndiceVertice() == nroDelVerticeOrigen);
                adyacentesDelDestino.remove(new AdyacenteConPeso(nroDelVerticeOrigen));
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
        List<AdyacenteConPeso> adyacentesDelOrigen = listasDeAdyacencias.get(nroDelVerticeOrigen);
        return adyacentesDelOrigen.contains(new AdyacenteConPeso(nroDelVerticeDestino));
    }

    public int gradoDelVertice(T unVertice) {
        validarVertice(unVertice);
        int nroVertice = getNroVertice(unVertice);
        List<AdyacenteConPeso> adyacentesDelVertice = listasDeAdyacencias.get(nroVertice);
        return adyacentesDelVertice.size();
    }

    public Iterable<T> getAdyacentesDelVertice(T unVertice) {
        validarVertice((unVertice));
        int nroDelVertice = getNroVertice(unVertice);
        List<AdyacenteConPeso> adyacentesDelVertice = listasDeAdyacencias.get(nroDelVertice);
        List<T> listaDeVerticesAdyacentes = new ArrayList<>();
        for (AdyacenteConPeso adyacenteConPeso : adyacentesDelVertice) {
            T verticeAdyacente = listaDeVertices.get(adyacenteConPeso.getIndiceVertice());
            listaDeVerticesAdyacentes.add(verticeAdyacente);
        }
        return listaDeVerticesAdyacentes;
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
        int cantLazos = 0;
        for (int i = 0; i < this.listasDeAdyacencias.size(); i++) {
            Iterable<AdyacenteConPeso> adyacentesDeUnVertice = this.listasDeAdyacencias.get(i);
            for (AdyacenteConPeso posDeAdyacente : adyacentesDeUnVertice) {
                if (i == posDeAdyacente.getIndiceVertice()) {
                    cantLazos++;
                } else {
                    cantAristas++;
                }
            }
        }
        return cantLazos + (cantAristas/2);
    }
    public int cantidadDeAristas() {
        int cantAristas = 0;
        int bucles = 0;
        for (T unVertice : listaDeVertices) {
            int nroVertice = getNroVertice(unVertice);
            List<AdyacenteConPeso> adyacentesDelVertice = listasDeAdyacencias.get(nroVertice);
            if (adyacentesDelVertice.contains(nroVertice)) {
                bucles += 1;
            }
            cantAristas += adyacentesDelVertice.size();
        }
        return (bucles + cantAristas) / 2;
    }

    public double peso(T verticeOrigen, T verticeDestino) throws ExcepcionAristaNoExiste {
        validarVertice(verticeOrigen);
        validarVertice(verticeDestino);
        if (!existeAdyacencia(verticeOrigen, verticeDestino)) {
            throw new ExcepcionAristaNoExiste();
        }
        int nroDelVerticeOrigen = getNroVertice(verticeOrigen);
        int nroDelVerticeDestino = getNroVertice(verticeDestino);
        List<AdyacenteConPeso> adyacentesDelOrigen = listasDeAdyacencias.get(nroDelVerticeOrigen);
        int posAdyacencia = adyacentesDelOrigen.indexOf(new AdyacenteConPeso(nroDelVerticeDestino));
        AdyacenteConPeso adyacencia = adyacentesDelOrigen.get(posAdyacencia);
        return adyacencia.getPeso();
    }

    public String mostrarPesos2() throws ExcepcionAristaNoExiste {
        StringBuilder sb = new StringBuilder();
        for (T vertice : listaDeVertices) {
            List<T> adyacencias = (List<T>) getAdyacentesDelVertice(vertice);
            for (T adyacente : adyacencias) {
                double peso = peso(vertice, adyacente);
                sb.append("De ").append(vertice)
                        .append(" a ").append(adyacente)
                        .append(" : ").append(peso)
                        .append("\n");
            }
        }
        return sb.toString();
    }

    public String mostrarPesos() {
        StringBuilder sb = new StringBuilder();

        // Mostrar lista de aristas con pesos
        sb.append("\n📋 LISTA DE ARISTAS:\n");
        sb.append("─".repeat(30)).append("\n");

        try {
            for (int i = 0; i < listaDeVertices.size(); i++) {
                T vertice = listaDeVertices.get(i);
                List<AdyacenteConPeso> adyacentes = listasDeAdyacencias.get(i);

                for (AdyacenteConPeso ady : adyacentes) {
                    T destino = listaDeVertices.get(ady.getIndiceVertice());
                    double peso = ady.getPeso();

                    // Solo mostrar cada arista una vez (evitar duplicados)
                    if (i <= ady.getIndiceVertice()) {
                        if (i == ady.getIndiceVertice()) {
                            // Lazo
                            sb.append(String.format("  %s ⟲ %s  [peso: %.2f]\n", vertice, destino, peso));
                        } else {
                            // Arista normal
                            sb.append(String.format("  %s ━━━ %s  [peso: %.2f]\n", vertice, destino, peso));
                        }
                    }
                }
            }
        } catch (Exception e) {
            sb.append("  Error al mostrar aristas\n");
        }

        return sb.toString();
    }


}

