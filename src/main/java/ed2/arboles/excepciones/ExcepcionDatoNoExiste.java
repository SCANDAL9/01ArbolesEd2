/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ed2.arboles.excepciones;

/**
 *
 * @author Flia Maldonado
 */
public class ExcepcionDatoNoExiste extends Exception {
    
    public ExcepcionDatoNoExiste() {
        super("Dato no existe en el árbol");
    }
    
    public ExcepcionDatoNoExiste(String msg) {
        super(msg);
    }
}
