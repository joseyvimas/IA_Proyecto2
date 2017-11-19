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
        //evaluarPoblacion();
        while(!solucion()){
            seleccionar();
            alterar();
            //evaluarPoblacion();
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
   
    public static void evaluarPoblacion (int fila){
        List<Zombie> ZombiesFila = new ArrayList<Zombie>();
        List<Zombie> ZombiesCopy = new ArrayList<Zombie>(); 
        int i,j;
        for (i=0; i<5; i++){
            ZombiesFila.clear();
            for (j=0; j<Z; j++){
                if(Zombies.get(j).fila == fila){
                    ZombiesFila.add(Zombies.get(j)); 
                }
            }
            if (ZombiesFila.size() == 0) return;  //Si no hay zombies en dicha fila, retornamos
            
            //Copiamos el arreglo de los zombies de la fila determinada para que no sea haga referencia a la misma lista ZombiesFila
            ZombiesCopy.clear();
            ZombiesCopy = cloneList(ZombiesFila);
            
            ZombiesCopy.get(0).columna--; //El primer Zombie se mete en el tablero

            /*for (j=0; j<ZombiesCopy.size(); j++){
                System.out.println("Zombie id: "+ZombiesCopy.get(j).id  +" Zombie FIla: "+ZombiesCopy.get(j).fila  + " Zombie columna: "+ZombiesCopy.get(j).columna  );   

            }*/
            simulacion(i, ZombiesCopy);
        }
    }
    public static boolean solucion (){
        return false;
    }
    
    public static void seleccionar (){
        
    }
    
    public static void alterar (){
        
    }
    
    public static void infoZombies(List<Zombie> Zombies){
        int z; 
        for (z=0; z<Zombies.size(); z++){
            System.out.println("ID: " + Zombies.get(z).id);
            System.out.println("Fila: " + Zombies.get(z).fila);
            System.out.println("Columna: " + Zombies.get(z).columna);
            System.out.println("Vida: " + Zombies.get(z).vida);
        }
    }
    
    public static boolean hayZombies(List<Zombie> ZombiesFila){
        int z;
        for (z=0; z<ZombiesFila.size(); z++){
            if (ZombiesFila.get(z).vida > 0){
                return true;
            }
        }
        System.out.println("Ganaron las plantas");        
        return false;
    }
    
    //Se puede hacer como puso monascal en el pdf, Los zombies ganan si logran llegar a cualquier coordenada entre (1,1) y (1,H).
    public static boolean zombiesWin(List<Zombie> ZombiesFila){
        int i;
        for (i=0; i<ZombiesFila.size(); i++){
           if (ZombiesFila.get(i).columna == -1){
            System.out.println("Ganaron los zombies");
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
    
    public static void simulacion(int ind, List<Zombie> ZombiesFila){
        int i,z;
        //Individuo que estamos probando de la poblacion
        for (i=0; i<4; i++)
            System.out.print(Poblacion.get(ind).cromosoma[i]+ " ");
        System.out.print("\n");
        
        while(hayZombies(ZombiesFila) && !zombiesWin(ZombiesFila)){
             //System.out.println("Continuar simulacion");
            //Plantas disparan y aumentar contador(aptitud) de daño causado de planta
            for (i=0; i<4; i++){
                if (Poblacion.get(ind).alive[i])
                    for (z=0; z<ZombiesFila.size(); z++)
                        if (ZombiesFila.get(z).columna != W && ZombiesFila.get(z).vida > 0){
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
                                        Poblacion.get(ind).setAptitud(ZombiesFila.get(z).vida);
                                        ZombiesFila.get(z).setVida(ZombiesFila.get(z).vida);
                                        Poblacion.get(ind).killPlanta(i); //La planta muere
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
                if (ZombiesFila.get(z).columna != W  && ZombiesFila.get(z).vida > 0){
                    ZombiesFila.get(z).moverse();
                }
   
            //Nuevo zombie entra
            for (z=0; z<ZombiesFila.size(); z++)
                if (ZombiesFila.get(z).columna == W){
                    ZombiesFila.get(z).setColumna(W-1);
                    break;
                }
            
        }
    }
    
    public static List<Zombie> cloneList(List<Zombie> Original) {
        List<Zombie> Copia = new ArrayList<Zombie>(Original.size());
        for (Zombie z : Original)
            Copia.add(new Zombie(z));
        return Copia;
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
        evaluarPoblacion(2);
        
        Poblacion.get(0).imprimirEstadisticas();
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
   
    public Zombie(int fila, int vida, int columna){
        this.fila = fila;
        this.vida = vida;
        this.columna = columna;
        this.id = (int) Math.round(IA_AlgoritmoGenetico.RamdonNumber(1000));
    }

    public Zombie(Zombie z) {
        this.fila = z.fila;
        this.vida = z.vida;
        this.columna = z.columna;
        this.id = z.id;
    }
    
    public void setVida(int cant){
        this.vida -= cant;
    }
    public void moverse(){
        this.columna -= 1;
        //System.out.println("el zombie "+this.id +" se movio a la columna: "+this.columna );
       
        
    }
    public void setColumna(int columna){
        this.columna = columna;
        //System.out.println("entro el zombie en la columna: "+this.columna );
    }
    
}

class Individuo{
    public int cromosoma[];
    public boolean alive[];
    public int aptitud;
    
    Individuo(){
        this.cromosoma = new int[4]; 
        this.alive = new boolean[4];
        this.aptitud = 0;
    }
    Individuo(int fila[]){
        this.cromosoma = new int[4]; 
        this.alive = new boolean[4];
        this.aptitud = 0;
        
        int x;
        for (x=0;x<4;x++){
            this.cromosoma[x] = fila[x];
            if (cromosoma[x] != 1)
                this.alive[x] = true;
            else
                this.alive[x] = false;
        }
        
        
    }
    
    public void setAptitud(int cant){
        this.aptitud += cant;
    }
    
    public void killPlanta(int i){
        this.alive[i] = false;
        //System.out.println("matamos una planta");
    }
    public void imprimirEstadisticas (){
        for (int i =0; i<4; i++){
           
           if (this.alive[i]){
               System.out.println("la planta: "+ this.cromosoma[i] + " esta viva");
           }
           else
           {
               System.out.println("la planta: "+ this.cromosoma[i] + " esta muerta");
               
           }
           System.out.println("tiene una aptitud: "+this.aptitud );
        }   
    }
    
}
