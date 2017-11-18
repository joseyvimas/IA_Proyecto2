package ia_algoritmogenetico;

import java.util.*;

/**
 * @authors
 * Josue G. Sanchez 24.757.111
 * José G. Yvimas 23.712.348
 */
public class IA_AlgoritmoGenetico {
    public static int W; //Columnas del jardín
    public static int H; //Filas del jardín
    public static int Z; //Cantidad de zombies
    public static Individuo Jardin [][];  //Matriz que representa el Jardin
    public static List<Individuo> Poblacion = new ArrayList<Individuo>();  
    public static List<Individuo> Seleccion = new ArrayList<Individuo>();  
    public static List<Zombie> Zombies = new ArrayList<Zombie>();  

    public static double RamdonNumber(int n){
        return (int) (Math.random() * n) + 1;
        
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
        while(!solucion()){
            seleccionar();
            alterar();
            evaluarPoblacion();
        }
        return;
    }
    public static int[] generarIndividuo (){
        int i;
        int fila [] = new int[4];
        for(i=0; i<4; i++)
            fila[i] = (int) Math.round(RamdonNumber(4));
       
        return fila;
    }
    
    public static void inicializarPoblacion (){
        //Población de 5 individuos
        int i;
        for (i=0; i<5; i++){
            Individuo ind = new Individuo();
            ind = new Individuo(generarIndividuo());
            Poblacion.add(ind);
        }
    }
   
    public static void evaluarPoblacion (){
    }
    public static boolean solucion (){
        return false;
    }
    
    public static void seleccionar (){
        
    }
    
    public static void alterar (){
        
    }
    
    public static boolean hayZombies(){
        int z;
        for (z=0; z<Z; z++)
            if (Zombies.get(z).vida > 0)
                return true;
        return false;
    }
    
    //Se puede hacer como puso monascal en el pdf, Los zombies ganan si logran llegar a cualquier coordenada entre (1,1) y (1,H).
    public static boolean zombiesWin(int ind){
        int i;
        for (i=0; i<4; i++)
            if (Poblacion.get(ind).cromosoma[i] != 1)
                return false;
        return true;
    }
    
    public static boolean canShootRepeater(int ind, int planta){
        int i;
        for (i=planta+1; i<4; i++)
            if (Poblacion.get(ind).cromosoma[i] == 2 || Poblacion.get(ind).cromosoma[i] == 3)
                return false;
        return true;
    }
    
    public static void simulacion(int ind, int fila){
        int i,z;
        for (i=0; i<4; i++)
            System.out.print(Poblacion.get(ind).cromosoma[i]+ " ");
        System.out.print("\n");
        while(hayZombies() && !zombiesWin(ind)){
            //Plantas disparan y aumentar contador(aptitud) de daño causado de planta
            for (i=0; i<4; i++){
                for (z=0; z<Z; z++)
                    if (Zombies.get(z).fila == fila && Zombies.get(z).columna != -1 && Zombies.get(z).vida > 0){
                        switch (Poblacion.get(ind).cromosoma[i]) {
                            case 2:
                               if (Zombies.get(z).columna != i){        
                                    Zombies.get(z).setVida(1);
                                    Poblacion.get(ind).setAptitud(1);
                               }
                               else
                                   //Zombie ataca
                                   Poblacion.get(ind).killPlanta(i);
                                break;
                            case 3:
                                if (Zombies.get(z).columna != i){
                                    if (canShootRepeater(ind,i)){
                                        Zombies.get(z).setVida(2);
                                        Poblacion.get(ind).setAptitud(2);   
                                    }
                                }
                                else
                                    //Zombie ataca
                                   Poblacion.get(ind).killPlanta(i);
                                break;
                            case 4:
                                if (Zombies.get(z).columna == i){
                                    Zombies.get(z).setVida(Zombies.get(z).vida);
                                    Poblacion.get(ind).setAptitud(Zombies.get(z).vida);
                                }
                                break;
                            default:
                                break;
                        }
                        break;
                    }
            }
            //Zombies caminan
            for (z=0; z<Z; z++)
                if (Zombies.get(z).fila == fila && Zombies.get(z).columna != 0 && Zombies.get(z).columna != -1 && Zombies.get(z).vida > 0)
                    Zombies.get(z).moverse();
                       
            //Nuevo zombie entra
            for (z=0; z<Z; z++)
                if (Zombies.get(z).fila == fila && Zombies.get(z).columna == -1){
                    Zombies.get(z).setColumna(W-1);
                    break;
                }
            
        }
    }
    public static void main(String[] args) {
        //Leyendo la entrada
        Scanner consola = new Scanner(System.in);
        W = consola.nextInt(); //Columnas del jardín
        H = consola.nextInt(); //Filas del jardín
        Z = consola.nextInt(); //Cantidad de zombies
        
        Jardin = new Individuo[H][W]; //Le damos dimensiones al jardin
        
        
        int ZF, ZV;
        int i;
        for (i=0; i<Z; i++){
            Zombie zombito = new Zombie();
            ZF = consola.nextInt(); //fila en la que aparece
            ZV = consola.nextInt(); //vida que tiene
            
            if (i==0)
                zombito = new Zombie(ZF,ZV,W-1);
            else
                zombito = new Zombie(ZF,ZV); 
            
            Zombies.add(zombito); //Añadimos el zombie número i 
        } 
        
        inicializarPoblacion();
        simulacion(0,2);
        System.out.print("todo fine");
        //algoritmoGenetico();
       
    }    
}

class Zombie{
    public int fila;
    public int columna;
    public int vida; 
    
    Zombie(){
        this.fila = 0;
        this.vida = 0;
        this.columna =-1;
    }
    public Zombie(int fila, int vida){
        this.fila = fila;
        this.vida = vida;
    }
    public Zombie(int fila, int vida, int columna){
        this.fila = fila;
        this.vida = vida;
        this.columna = columna;
    }
    
    public void setVida(int cant){
        this.vida -= cant;
    }
    public void moverse(){
        this.columna -= 1;
        
    }
    public void setColumna(int columna){
        this.columna = columna;
    }
    
}

class Individuo{
    public int cromosoma[];
    public int aptitud;
    
    Individuo(){
        this.cromosoma = new int[4]; 
        this.aptitud = 0;
    }
    Individuo(int fila[]){
        this.cromosoma = new int[4]; 
        this.aptitud = 0;
        
        int x;
        for (x=0;x<4;x++)
            this.cromosoma[x] = fila[x];
        
        
    }
    
    public void setAptitud(int cant){
        this.aptitud += cant;
    }
    
    public void killPlanta(int i){
        this.cromosoma[i] = 1;
        this.aptitud = 0;
    }
    
}
