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
    
    public static boolean hayZombies(int fila){
        int z;
        for (z=0; z<Z; z++){
            if (Zombies.get(z).fila == fila && Zombies.get(z).vida >0){
                
                return true;
            }
        }
        System.out.println("Ganarron las plantas");        
        return false;
    }
    
    //Se puede hacer como puso monascal en el pdf, Los zombies ganan si logran llegar a cualquier coordenada entre (1,1) y (1,H).
    public static boolean zombiesWin(int fila){
        int i;
        for (i=0; i<Z; i++){
           if (Zombies.get(i).fila == fila && Zombies.get(i).columna == -1){
            System.out.println("Ganarron los zombies");
             return true;
            } 
        }
 
        return false;
    }
    
    public static boolean canShootRepeater(int ind, int planta){
        int i;
        for (i=planta+1; i<4; i++)
            if (Poblacion.get(ind).cromosoma[i] == 2 || Poblacion.get(ind).cromosoma[i] == 3)
                return false;
        return true;
    }
    
    public static void simulacion(int ind, int fila, List<Zombie> ZombiesFila){
        int i,z;
        //Individuo que estamos probando de la poblacion
        for (i=0; i<4; i++)
            System.out.print(Poblacion.get(ind).cromosoma[i]+ " ");
        System.out.print("\n");
        
        while(hayZombies(fila) && !zombiesWin(fila)){
             System.out.println("Continuar simulacion");
            //Plantas disparan y aumentar contador(aptitud) de daño causado de planta
            for (i=0; i<4; i++){
                for (z=0; z<ZombiesFila.size(); z++)
                    if (ZombiesFila.get(z).fila == fila && ZombiesFila.get(z).columna != -1 && ZombiesFila.get(z).vida > 0){
                        switch (Poblacion.get(ind).cromosoma[i]) {
                            case 2:
                               if (ZombiesFila.get(z).columna != i){        
                                    ZombiesFila.get(z).setVida(1);
                                    Poblacion.get(ind).setAptitud(1);
                               }
                               else
                                   //Zombie ataca
                                   Poblacion.get(ind).killPlanta(i);
                                break;
                            case 3:
                                if (ZombiesFila.get(z).columna != i){
                                    if (canShootRepeater(ind,i)){
                                        ZombiesFila.get(z).setVida(2);
                                        Poblacion.get(ind).setAptitud(2);   
                                    }
                                }
                                else
                                    //Zombie ataca
                                   Poblacion.get(ind).killPlanta(i);
                                break;
                            case 4:
                                if (ZombiesFila.get(z).columna == i){
                                    ZombiesFila.get(z).setVida(ZombiesFila.get(z).vida);
                                    Poblacion.get(ind).setAptitud(ZombiesFila.get(z).vida);
                                }
                                break;
                            default:
                                break;
                        }
                        break;
                    }
            }
            //Zombies caminan
            for (z=0; z<ZombiesFila.size(); z++)
                if (ZombiesFila.get(z).fila == fila && ZombiesFila.get(z).columna != W  && ZombiesFila.get(z).columna != -1 && ZombiesFila.get(z).vida > 0){
                    ZombiesFila.get(z).moverse();
                }
   
            //Nuevo zombie entra
            for (z=0; z<ZombiesFila.size(); z++)
                if (ZombiesFila.get(z).fila == fila && ZombiesFila.get(z).columna == W){
                    ZombiesFila.get(z).setColumna(W-1);
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
            zombito = new Zombie(ZF,ZV,W); 
            Zombies.add(zombito); //Añadimos el zombie número i 
        } 
        
        inicializarPoblacion();
        List<Zombie> ZombiesFila = new ArrayList<Zombie>();
        for (int j=0; j<Z; j++){
            if(Zombies.get(j).fila == 2){
                ZombiesFila.add(Zombies.get(j));   
            }
        }
        ZombiesFila.get(0).columna --; //El primer Zombie se mete en el tablero 
        for (int j=0; j<ZombiesFila.size(); j++){
            System.out.println("Zombie id: "+ZombiesFila.get(j).id  +" Zombie FIla: "+ZombiesFila.get(j).fila  + " Zombie columna: "+ZombiesFila.get(j).columna  );   

        }
        simulacion(0,2, ZombiesFila);
        Poblacion.get(0).imprimirEstadisticas();
        System.out.print("todo fine");
        //algoritmoGenetico();
       
    }    
}

class Zombie{
    public int fila;
    public int columna;
    public int vida; 
    public int id;
    Zombie(){
        this.fila = 0;
        this.vida = 0;
        this.columna =-1;
        this.id = (int) Math.round(IA_AlgoritmoGenetico.RamdonNumber(1000));
    }
    public Zombie(int fila, int vida){
        this.fila = fila;
        this.vida = vida;
        this.id = (int) Math.round(IA_AlgoritmoGenetico.RamdonNumber(1000));
    }
    public Zombie(int fila, int vida, int columna){
        this.fila = fila;
        this.vida = vida;
        this.columna = columna;
        this.id = (int) Math.round(IA_AlgoritmoGenetico.RamdonNumber(1000));
    }
    
    public void setVida(int cant){
        this.vida -= cant;
    }
    public void moverse(){
        this.columna -= 1;
        System.out.println("el zombie "+this.id +" se movio a la columna: "+this.columna );
       
        
    }
    public void setColumna(int columna){
        this.columna = columna;
         System.out.println("entro el zombie en la columna: "+this.columna );
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
        this.cromosoma[i] = 0;
        this.aptitud = 0;
        System.out.println("matamos una planta");
    }
    public void imprimirEstadisticas (){
        for (int i =0; i<4; i++){
           System.out.println("la planta: "+ this.cromosoma[i]);
           System.out.println("tiene una aptitud: "+this.aptitud );
        }   
    }
    
}
