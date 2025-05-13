package ed2.arboles.excepciones;

public class ExcepcionOrdenInvalido extends Exception {

    public ExcepcionOrdenInvalido() {
        super("El orden de las MVIAS debe ser mayor o igual a 3");
    }
    public ExcepcionOrdenInvalido(String message) {
        super(message);
    }
}
