/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ed2.arboles.ui;

import ed2.arboles.clases.AVL;
import ed2.arboles.clases.ArbolBinarioBusqueda;
import ed2.arboles.clases.IArbolBusqueda;
import ed2.arboles.excepciones.ExcepcionDatoYaExiste;
import ed2.arboles.excepciones.ExcepcionRecorridosInvalidos;

/**
 *
 * @author Flia Maldonado
 */
public class TestArboles {
    
    public static void main(String[] args) throws ExcepcionDatoYaExiste, ExcepcionRecorridosInvalidos {
    
        IArbolBusqueda<Integer> arbolBusqueda;
        arbolBusqueda = new ArbolBinarioBusqueda<>();


        
        arbolBusqueda.insertar(60);
        arbolBusqueda.insertar(50);
        arbolBusqueda.insertar(30);
        arbolBusqueda.insertar(56);
        arbolBusqueda.insertar(20);
        arbolBusqueda.insertar(25);
        arbolBusqueda.insertar(22);
        arbolBusqueda.insertar(27);
        arbolBusqueda.insertar(80);
        arbolBusqueda.insertar(70);
        arbolBusqueda.insertar(65);
        arbolBusqueda.insertar(74);
        arbolBusqueda.insertar(72);
        
       System.out.println(arbolBusqueda);
       
       System.out.println("¿El dato 65 esta en el arbol? => " + 
               arbolBusqueda.contiene(65));
       System.out.println("¿El dato 71 esta en el arbol? => " + 
               arbolBusqueda.contiene(71));
       
       System.out.println("Recorrido por niveles: " + 
               arbolBusqueda.recorridoPorNiveles());
       System.out.println("Recorrido en preOrden: " + 
               arbolBusqueda.recorridoEnPreOrden());       
       System.out.println("Recorrido en InOrden: " + 
               arbolBusqueda.recorridoEnInOrden());
       System.out.println("Recorrido en PostOrden: " +
               arbolBusqueda.recorridoEnPostOrden());
       System.out.println("Size: " +
               arbolBusqueda.size());
       System.out.println("Altura: " +
               arbolBusqueda.altura());

       /*if (arbolBusqueda instanceof ArbolBinarioBusqueda) {
           ArbolBinarioBusqueda arbolBinarioBusqueda = (ArbolBinarioBusqueda) arbolBusqueda;
           System.out.println("Altura del arbol binario iterativo: " +
                    arbolBinarioBusqueda.alturaIt());
       }

       //System.out.println(arbolBusqueda.toStringVertical());

        IArbolBusqueda<Integer> arbolBusquedaPost;
        arbolBusquedaPost = new ArbolBinarioBusqueda<>(arbolBusqueda.recorridoEnInOrden(),
                arbolBusqueda.recorridoEnPostOrden(), false);


        IArbolBusqueda<Integer> arbolBusquedaPre;
        arbolBusquedaPre = new ArbolBinarioBusqueda<>(arbolBusqueda.recorridoEnInOrden(),
                arbolBusqueda.recorridoEnPreOrden(), true);
       System.out.println(arbolBusquedaPre.toStringVertical());
       */


        AVL<Integer> arbolAVL;
        arbolAVL = new AVL<>();

        arbolAVL.insertar(5);
        arbolAVL.insertar(2);
        arbolAVL.insertar(1);
        arbolAVL.insertar(3);
        arbolAVL.insertar(7);
        arbolAVL.insertar(20);
        arbolAVL.insertar(6);
        arbolAVL.insertar(30);
        arbolAVL.insertar(15);
        arbolAVL.insertar(17);


        System.out.println(arbolAVL.toStringVertical());
    }    
}
