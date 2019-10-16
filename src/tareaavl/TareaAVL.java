/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tareaavl;

/**
 *
 * @author imac
 */
public class TareaAVL {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArbolAVL<Integer> arbol = new ArbolAVL();
        arbol.inserta(100);
        System.out.println(arbol.toString());
        arbol.inserta(200);
        System.out.println(arbol.toString());
        arbol.borrar(100);
        System.out.println(arbol.toString());
        arbol.inserta(2);
        System.out.println(arbol.toString());
        arbol.inserta(300);
        System.out.println(arbol.toString());
        arbol.inserta(9);
        System.out.println(arbol.toString());
        arbol.inserta(1);
        System.out.println(arbol.toString());
        System.out.println(arbol.borrar(2));
        
    }
    
}
