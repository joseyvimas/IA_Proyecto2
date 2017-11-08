package ia_algoritmogenetico;

import java.util.*;

/**
 * @authors
 * Josue G. Sanchez 24.757.111
 * José G. Yvimas 23.712.348
 */
public class IA_AlgoritmoGenetico {
    public static int W; //Tamaño del jardín
    public static int H; //Cantidad máxima de plantas
    public static int Z; //Cantidad de zombies
    public static char Jardin [][];  //Matriz que representa el Jardin
    public static List<Zombie> Zombies = new ArrayList<Zombie>();  

    public static void main(String[] args) {
        //Leyendo la entrada
        Scanner consola = new Scanner(System.in);
        W = consola.nextInt(); //Tamaño del jardin
        H = consola.nextInt(); //Cantidad máxima de plantas
        Z = consola.nextInt(); //Cantidad de zombies
        
        Jardin = new char[4][H]; //Le damos dimensiones al jardin
        
        
        int ZF, ZV;
        for (int i=0; i<Z; i++){
            Zombie zombito = new Zombie();
            ZF = consola.nextInt(); //fila en la que aparece
            ZV = consola.nextInt(); //vida que tiene
            
            zombito = new Zombie(ZF,ZV,i); 
            
            Zombies.add(zombito); //Añadimos el zombie número i 
        }   
    }    
}

class Zombie{
    public int fila;
    public int vida; 
    public int orden;
    
    Zombie(){
        this.fila = 0;
        this.vida = 0;
        this.orden = 0;
    }
    public Zombie(int fila, int vida, int orden){
        this.fila = fila;
        this.vida = vida;
        this.orden = orden;
    }
}
