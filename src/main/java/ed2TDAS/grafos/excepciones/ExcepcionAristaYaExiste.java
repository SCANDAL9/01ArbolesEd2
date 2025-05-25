package ed2TDAS.grafos.excepciones;

public class ExcepcionAristaYaExiste extends Exception {
    public ExcepcionAristaYaExiste() {
        super("Arista ya existe en su grafo");
    }
    public ExcepcionAristaYaExiste(String message) {
        super(message);
    }
}
