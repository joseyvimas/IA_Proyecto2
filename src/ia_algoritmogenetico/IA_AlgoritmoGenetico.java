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
    public static Individuo Jardin [][];  //Matriz que representa el Jardin
    public static List<Zombie> Zombies = new ArrayList<Zombie>();  

    public static double RamdonNumber(int max, int min){
        int range = (max - min) ;     
        return (Math.random() * range) + min;
    }
    public static int binaryToDecimal(int number []) {
        int decimal = 0;
        int tam = number.length;
        int power = tam-1;
        for (int i =0; i<tam ; i++){
            decimal += number[i]* Math.pow(2, power);
            power--;
        }
        return decimal;
    }
    public static void algoritmoGenetico (){
        inicializarPoblacion();
        evaluarPoblacion();
        while(! solucion()){
            seleccionar();
            alterar();
            evaluarPoblacion();
        }
        return;
    }
    public static void inicializarPoblacion (){
        int i,j;
        for (i=0; i< H; i++){
            for(j=0; j<4; j++){
                Jardin[i][j] = GeneradorIndividuo();
            }
        }
    }
    public static Individuo GeneradorIndividuo(){
        Individuo planta = new Individuo();
        return planta;
    }
    public static void evaluarPoblacion (){
        
    }
    public static boolean solucion (){
        
    }
    public static void seleccionar (){
        
    }
    public static void alterar (){
        
    }
    public static void simulacion(){
        
        while(hayZombies() && !zombiesWin() ){
            //Plantas disparan
            //aumentar contador de daño causado de planta
            //Nuevo zombie entra
            //Zombies caminan o Zombies atacan
            
        }
    }
    public static void main(String[] args) {
        //Leyendo la entrada
       /* Scanner consola = new Scanner(System.in);
        W = consola.nextInt(); //Tamaño del jardin
        H = consola.nextInt(); //Cantidad máxima de plantas
        Z = consola.nextInt(); //Cantidad de zombies
        
        Jardin = new Individuo[4][H]; //Le damos dimensiones al jardin
        
        
        int ZF, ZV;
        for (int i=0; i<Z; i++){
            Zombie zombito = new Zombie();
            ZF = consola.nextInt(); //fila en la que aparece
            ZV = consola.nextInt(); //vida que tiene
            
            zombito = new Zombie(ZF,ZV,i); 
            
            Zombies.add(zombito); //Añadimos el zombie número i 
        }   */  
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

class Individuo{
    public int cromosoma [] = new int [2]; 
    public int id;
    Individuo(){
        for (int i =0; i<2; i++){
            this.cromosoma[i] = (int) Math.round( IA_AlgoritmoGenetico.RamdonNumber(0,1) ) ; 
        }
        this.id = IA_AlgoritmoGenetico.binaryToDecimal(this.cromosoma);
    }
    
}
