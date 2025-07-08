package ed2TDAS.grafos.pesados;

import ed2TDAS.grafos.excepciones.ExcepcionAristaNoExiste;
import ed2TDAS.grafos.excepciones.ExcepcionAristaYaExiste;
import ed2TDAS.grafos.pesados.utils.AdyacenteConPeso;

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

    @Override
    public String mostrarPesos() {
        StringBuilder sb = new StringBuilder();

        // Mostrar lista de aristas con pesos
        sb.append("\n📋 LISTA DE ARISTAS (GRAFO DIRIGIDO):\n");
        sb.append("─".repeat(35)).append("\n");

        try {
            for (int i = 0; i < listaDeVertices.size(); i++) {
                T verticeOrigen = listaDeVertices.get(i);
                List<AdyacenteConPeso> adyacentes = listasDeAdyacencias.get(i);

                for (AdyacenteConPeso ady : adyacentes) {
                    T verticeDestino = listaDeVertices.get(ady.getIndiceVertice());
                    double peso = ady.getPeso();

                    if (i == ady.getIndiceVertice()) {
                        // Lazo
                        sb.append(String.format("  %s ⟲ %s  [peso: %.2f]\n", verticeOrigen, verticeDestino, peso));
                    } else {
                        // Arista dirigida
                        sb.append(String.format("  %s ➝ %s  [peso: %.2f]\n", verticeOrigen, verticeDestino, peso));
                    }
                }
            }
        } catch (Exception e) {
            sb.append("  Error al mostrar aristas\n");
        }

        return sb.toString();
    }

}
