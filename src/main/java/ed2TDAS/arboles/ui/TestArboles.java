/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ed2TDAS.arboles.ui;

import ed2TDAS.arboles.clases.ArbolBinarioBusqueda;
import ed2TDAS.arboles.clases.IArbolBusqueda;
import ed2TDAS.arboles.examenes.Pregunta01;
import ed2TDAS.arboles.excepciones.ExcepcionDatoNoExiste;
import ed2TDAS.arboles.excepciones.ExcepcionDatoYaExiste;
import ed2TDAS.arboles.excepciones.ExcepcionOrdenInvalido;
import ed2TDAS.arboles.excepciones.ExcepcionRecorridosInvalidos;

/**
 *
 * @author H
 */
public class TestArboles {
    
    public static void main(String[] args) throws ExcepcionDatoYaExiste, ExcepcionRecorridosInvalidos, ExcepcionDatoNoExiste, ExcepcionOrdenInvalido {
    
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


       /* AVL<Integer> arbolAVL;
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
        arbolAVL.insertar(17);*/

        //System.out.println(arbolAVL.toStringVertical());



        //System.out.println(arbolBusqueda.toStringVertical());
        ArbolBinarioBusqueda arbolBinarioBusqueda = (ArbolBinarioBusqueda) arbolBusqueda;
        int valor1 = arbolBinarioBusqueda.nroHijosIzqNoVaciosDesdeNivel(3);
        System.out.println(valor1);


        Pregunta01<Integer> arbolMVIAS;
        arbolMVIAS = new Pregunta01<>(4);

        int[] datos2 = {
                50, 25, 75, 10, 30, 60, 90,
                5, 15, 27, 33, 55, 65, 85, 95,
                1, 8, 12, 20, 28, 35, 58, 62,
                70, 80, 88, 92, 98, 100, 77
        };
        int[] datos = {40, 20, 60, 10, 30, 50, 70};

        for (int dato : datos) {
            arbolMVIAS.insertar(dato);
        }

        System.out.println(arbolMVIAS.toStringVertical());

        Pregunta01<Integer> Pregunta01 = (Pregunta01<Integer>) arbolMVIAS;
        int hola = Pregunta01.nivelEnDondeSeEncuentraUnDato(1);
        System.out.println(hola);

        /*System.out.println(arbolMVIAS.toStringVertical());
        arbolMVIAS.eliminar(75);*/
    }    
}
