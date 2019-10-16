/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tareaavl;

/**
 *
 * @author sofía albert 181767
 */
public class NodoAVL <T extends Comparable<T>> {
    private T element;
    private NodoAVL<T> izq, der, papa;
    private int fe, altura;
    
    NodoAVL(T elem){
        element = elem;
        izq = null;
        der = null;
        papa = null;
        fe = 0;
    }
    //gets
    public T getElement() {
        return element;
    }

    public int getFe() {
        return fe;
    }
    public NodoAVL<T> getIzq() {
        return izq;
    }
    public NodoAVL<T> getDer() {
        return der;
    }
    
    public NodoAVL<T> getPapa() {
        return papa;
    }
    //sets
    public void setFe(int fe) {
        this.fe = fe;
    }
    
    public void setElement(T element) {
        this.element = element;
    }
    public void setPapa(NodoAVL<T> papa){
        this.papa= papa;
    }    
    public void setIzq(NodoAVL<T> izq) {
        this.izq = izq;
    }
    public void setDer(NodoAVL<T> der) {
        this.der = der;
    }    
    /**
     * este método sirve para vincular un nodo y su hijo. 
     * @param n nodo que se quiere direccionar
     */
    public void cuelga(NodoAVL<T> n){
        if(n!=null){
            if(n.getElement().compareTo(element)<0)
                izq = n;
            else
                der = n;
            n.setPapa(this);
        }
        
    }
    
    public String toString(){
        StringBuilder resp = new StringBuilder();
        if(element!=null)
            resp.append("elemento: " + element.toString() + " factor de equilibrio : " + fe);
        return resp.toString();
    }
    /**
     * revisa y actualiza el factor de equilibrio del nodo
     */
    public void actualizaFe(){
        int altDer = 0, altIzq = 0;
        if (der != null) 
            altDer = der.getAltura();
        if (izq != null) 
            altIzq = izq.getAltura();
        fe = altDer - altIzq;
    }
    
    //este método genera un stack overflow no sé porqué
      
//    public int getAltura() {
//        altura = 0;
//        altura(this, 1);
//        return altura;
//    }

//    private void altura(NodoAVL<T> act, int nivel) {
//        if (act != null) {
//            altura(act.getIzq(), nivel + 1);
//            if (nivel > altura) {
//                altura = nivel;
//            }
//            altura(act.getDer(), nivel + 1);
//        }
//    }
    /**
     * Nota: esta altura en realidad está mal porque no considera todas las posibilidades, si no que solo viaja a la derecha o a la izquierda :(
     * @param actual dice el nodo del que se quiere la altura
     * @return 
     */
    public int getAltura(){
            if(this == null)
                return 0;
            return Math.max(hijosIzq(), hijosDer());
    }
    public int hijosIzq(){
        int resp=0;
        if(izq!=null)
            resp=izq.hijosIzq()+1;
        return resp;
    }    
    public int hijosDer(){
        int resp=0;
        if(der!=null)
            resp=der.hijosDer()+1;
        return resp;
    }

}
