package ed2.grafos.excepciones;

public class ExcepcionAristaNoExiste extends Exception {

    public ExcepcionAristaNoExiste() {
        super("Arista no existe en su grafo");
    }
    public ExcepcionAristaNoExiste(String message) {
        super(message);
    }
}
