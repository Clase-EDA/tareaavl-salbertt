/**
 *
 * @author sofía albert 181767
 */
package tareaavl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class ArbolAVL <T extends Comparable <T>>{
    private NodoAVL<T> raiz, papa;
    private int cont=0;

    public ArbolAVL() {
        raiz = null;
        papa = null;
        cont = 0;
    }
    //gets 
    public NodoAVL<T> getRaiz() {
        return raiz;
    }

    public NodoAVL<T> getPapa() {
        return papa;
    }

    public int getCont() {
        return cont;
    }
    
    
    
    /**
     * inserta el elemento dentro del árbol de busqueda, y actualiza el factor de equilibrio (haciendo rotaciones si es necesario)
     * @param elem el dato que se desea insertar
     */
    public void inserta (T elem){        
        if(elem!=null){ 
            cont++;
            NodoAVL<T> actual = new NodoAVL(elem), temp = null;
            if(raiz==null)
                raiz = actual;
            else{ //hay que recorrer el arbol y ver dónde insertarlo
                NodoAVL<T> aux = raiz;
                while(aux!=null){
                    temp = aux;
                    if(elem.compareTo(aux.getElement())<0){ 
                        aux = aux.getIzq();
                    }
                    else
                        aux = aux.getDer();
                }
                temp.cuelga(actual);
                
                //ahora hay que actualizar el factor de equilibrio
                if(temp.getDer()==actual)
                    actualizaFeInserta(temp, 1);
                else
                    actualizaFeInserta(temp, -1);
            }
        }
    }
    /**
     * método que recorre el árbol desde el nodo insertado hacia la raíz, actualizando el factor de equilibrio y haciendo rotaciones si es necesario
     * @param nodo nuevo que se acaba de insertar
     * @param i que indica si viene de la derecha o la izquierda
     */
    private void actualizaFeInserta(NodoAVL<T> nodo, int i) {
        boolean termine = false;
        while(nodo!=null && !termine){            
           nodo.setFe(nodo.getFe()+i);
           if(nodo.getFe()==0) //entras del 0 entonces ya acabaste
               termine = true;
           else{
              if(Math.abs(nodo.getFe())==2){ //cuando rotas ya acabaste
                  nodo = rotacion(nodo);
                  termine = true;
              }
              else{ //significa que tengo que seguir viajando hacia la raíz
                  if(nodo!=raiz){
                      if(nodo.getElement().compareTo(nodo.getPapa().getElement())<0) 
                          i = -1;
                      else
                          i = 1;                      
                      }
                  else //ya llegaste a la raíz                      
                      termine = true;
              }
           }
           nodo = nodo.getPapa();
       }
    }
    /**
     * 
     * @param actual nodo que tiene factor de equilibrio y se debe de rotar
     * @return nueva raíz del subarbol
     */
    private NodoAVL<T> rotacion(NodoAVL<T> actual) { // analizar lo que hay en la clase arbol avl
        NodoAVL<T> alfa, beta, gamma, A, B, C, D, papa;
        if(actual!=null){
            //iz-izq
            if(actual.getFe() ==-2 &&(actual.getIzq()!=null && actual.getIzq().getFe()<=0)){
                alfa = actual;
                papa = actual.getPapa();
                beta = alfa.getIzq();
                gamma = beta.getIzq();
                A = gamma.getIzq();
                B = gamma.getDer();
                C= beta.getDer();
                D = alfa.getDer();

                gamma.cuelga(A);
                gamma.cuelga(B);
                alfa.cuelga(C);
                alfa.cuelga(D);
                beta.cuelga(alfa);
                beta.cuelga(gamma);

                if(papa!=null){
                    papa.cuelga(beta);
                }
                else{
                    beta.setPapa(null);
                    raiz = beta;
                }
                alfa.actualizaFe();
                beta.actualizaFe();
                gamma.actualizaFe();
                return beta;
            }
            //der-der
            if(actual.getFe()==2 &&(actual.getDer()!=null && actual.getDer().getFe()>=0)){
                alfa = actual;
                beta = alfa.getDer();
                gamma = beta.getDer();
                papa = actual.getPapa();
                A=alfa.getIzq();
                B = beta.getIzq();
                C = gamma.getDer();
                D=gamma.getIzq();

                alfa.cuelga(A);
                alfa.cuelga(B);
                beta.cuelga(alfa);
                beta.cuelga(gamma);
                gamma.cuelga(C);
                gamma.cuelga(D);

                if(papa!=null)
                    papa.cuelga(beta);
                else{
                    beta.setPapa(null);
                    raiz = beta;
                }
                alfa.actualizaFe();
                beta.actualizaFe();
                gamma.actualizaFe();
                return beta;
            }
            //izq-der
            if(actual.getFe()==-2&&(actual.getIzq()!=null && actual.getIzq().getFe()==1)){
                alfa = actual;
                beta = alfa.getIzq();
                gamma = beta.getDer();
                papa=alfa.getPapa();
                A = beta.getIzq();
                B= gamma.getIzq();
                C=gamma.getDer();
                D = alfa.getDer();

                beta.cuelga(A);
                beta.cuelga(B);
                alfa.cuelga(C);
                alfa.cuelga(D);
                gamma.cuelga(alfa);
                gamma.cuelga(beta);

                if(papa!=null)
                    papa.cuelga(gamma);
                else{
                    gamma.setPapa(null);
                    raiz = gamma;
                }
                alfa.actualizaFe();
                beta.actualizaFe();
                gamma.actualizaFe();
                return gamma;
            }
            //der-izq
            if(actual.getFe()==2&&(actual.getDer()!=null && actual.getDer().getFe()==-1)){
                alfa= actual;
                beta = alfa.getDer();
                gamma=beta.getIzq();
                papa = alfa.getPapa();
                A=alfa.getIzq();
                B=gamma.getIzq();
                C = gamma.getDer();
                D = beta.getDer();

                gamma.cuelga(alfa);
                gamma.cuelga(beta);
                alfa.cuelga(A);
                alfa.cuelga(B);
                beta.cuelga(C);
                beta.cuelga(D);

                if(papa!=null)
                    papa.cuelga(gamma);
                else{
                    gamma.setPapa(null);
                    raiz= gamma;
                }
                alfa.actualizaFe();
                beta.actualizaFe();
                gamma.actualizaFe();
                return gamma;
            }
        }
        
        return null;
        
    }
    /**
     * se ecuentra el elemento, se elimina el nodo y se actualiza el factor de equilibrio
     * @param elem dato que se desea eliminar
     * @return 
     */
    public boolean borrar(T elem){ 
        if(elem!= null){
            NodoAVL<T> actual = new NodoAVL(elem);
            actual = busca(actual);
            if(actual==null) //significa que el elemento no está en el árbol
                return false;     
            NodoAVL<T> papa = actual.getPapa();
            cont--;
            
            if(actual.getDer()!=null && actual.getIzq()!=null){//tiene dos hijos
               //encontrar sucesor inorden para ponerlo en la pos de actual
                NodoAVL<T> aux = actual.getDer();
                while(aux.getIzq()!=null)
                    aux=aux.getIzq();
                actual.setElement(aux.getElement());
                if(aux==actual.getDer()){
                    actual.setDer(aux.getDer());
                    actualizaFeBorrar(actual, -1);
                }
                else{
                    aux.getPapa().setIzq(aux.getDer());
                    actualizaFeBorrar(actual, 1);
                }

                aux.setPapa(null);
                aux.setIzq(null);
                aux.setDer(null);
            }
            else{
                if(actual.getDer()==null && actual.getIzq()==null){ //es una hoja. solo la borramos
                    if(actual==raiz)
                        raiz=null;
                    else{

                        if((papa.getDer()!=null) && papa.getDer().getElement().equals(elem)){ 
                            papa.setDer(null);
                            actualizaFeBorrar(papa, -1);
                        }
                        else{
                            papa.setIzq(null);
                            actualizaFeBorrar(papa, 1);
                        }
                        actual.setElement(null);
                        actual.setPapa(null);
                    }
                }
                else{ //solo tiene 1 hijo. lo pasamos al papá

                    actual.setElement(null);

                    if(actual.getDer()!=null){ //hijo derecho (factor de equilibrio -1)
                        if(actual==raiz){
                            raiz = actual.getDer();
                            raiz.setPapa(null);
                        }
                        else{
                            papa.cuelga(actual.getDer());
                            actualizaFeBorrar(papa,-1);
                        }
                        actual.setDer(null);
                    }

                    else{ //hijo izquierdo (factor de equilibrio +1)
                        if(actual==raiz){
                            raiz = actual.getDer();
                            raiz.setPapa(null);
                        }
                        else{
                            papa.cuelga(actual.getIzq());
                            actualizaFeBorrar(papa, 1);
                        }
                        actual.setIzq(null);
                    }
                    actual.setPapa(null);
                }
            }

            return true;
        }
        else
            return false;
    }
    /**
     * 
     * @param nodo desde donde hay que empezar a viajar a la raíz checando el factor de equilibrio
     * @param nuevoFe dice si se borró el hijo izq (1) o der (-1)
     */
   private void actualizaFeBorrar(NodoAVL<T> nodo, int i){
       boolean termine = false;
       while(nodo!=null && !termine){
           nodo.setFe(nodo.getFe()+i);
           if(Math.abs(nodo.getFe())==1) //saliste del 0 entonces ya acabaste
               termine = true;
           else{
              if(Math.abs(nodo.getFe())==2){ //cuando rotas ya acabaste
                  nodo = rotacion(nodo);
                  termine = true;
              }
              else{ //significa que tengo que seguir viajando hacia la raíz
                  if(nodo!=raiz){
                      if(nodo.getElement().compareTo(nodo.getPapa().getElement())<0)//o sea, nodo es el hijo izq y fe +1
                          i = 1;
                      else
                          i = -1;
                      nodo = nodo.getPapa();
                  }
                  else //ya llegaste a la raiz
                      termine= true;
                  
              }
           }
       }
   }
   /**
    * busca, partiendo de la raíz, a un nodo dado. Nota: se considera que dos nodos son iguales si tienen el mismo elemento
    * @param actual
    * @return 
    */
   private NodoAVL<T> busca(NodoAVL<T> actual){ //iterativo
        boolean encontre = false;
        if(actual==null)
            return null;
        T elem=actual.getElement();
        NodoAVL<T> resp= raiz;
        while(!encontre && actual!=null){
            if(elem.compareTo(resp.getElement())<0)
                resp = resp.getIzq();
            else{
                if(elem.compareTo(resp.getElement())>0)
                    resp = resp.getDer();                
                else
                    encontre = true;
            }
        }
        return resp;
            
    }
    
   public String toString(){
       if(raiz!=null){
           ArrayList<NodoAVL<T>> lista = new ArrayList<>();
           NodoAVL<T> aux;
           StringBuilder sb = new StringBuilder();
           lista.add(raiz);
           while(lista.size()>0){               
               aux = lista.remove(0);
               sb.append(aux.toString());
               if (aux.getIzq() != null)
                   lista.add(aux.getIzq());
               if (aux.getDer() != null) 
                lista.add(aux.getDer());
           }
          
          return sb.toString();
       }
       else
           return "";
   }



}
