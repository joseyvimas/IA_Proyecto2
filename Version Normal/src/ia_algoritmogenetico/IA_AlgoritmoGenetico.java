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
    public static int Jardin [][];  //Matriz que representa el Jardin
    public static List<Individuo> Poblacion = new ArrayList<Individuo>();   
    public static List<Zombie> Zombies = new ArrayList<Zombie>();
    public static int TamPoblacion = 6;
    public static double ProbCruce = 0.7;
    public static double ProbMuta = 0.01;
    public static boolean MalaSolucion =false;
    
    public static double RamdonNumber(int n){
        return (int) (Math.random() * n) + 1;
        
    }
    public static void algoritmoGenetico (int ZombieVidaFila, int nrofila){
        int iteracion =0;
        inicializarPoblacion();
        evaluarPoblacion(nrofila);
        while(!solucion(ZombieVidaFila, nrofila, iteracion)){
            seleccionar();
            alterar();
            evaluarPoblacion(nrofila);
            iteracion++;
        }
    }
    public static int[] generarIndividuo (){
        int i;
        int fila [] = new int[4];
        for(i=0; i<4; i++)
            fila[i] = (int) Math.round(RamdonNumber(4))-1;
       
        return fila;
    }
    
    public static void inicializarPoblacion (){
        //Población de 6 individuos
        int i;
        Poblacion.clear(); //Limpiamos la poblacion
        for (i=0; i< TamPoblacion; i++){
            Individuo ind = new Individuo();
            ind = new Individuo(generarIndividuo());
            Poblacion.add(ind);
        }
        
        for (i=0; i< TamPoblacion; i++){
            for(int j=0; j<4;j++){
                if(Poblacion.get(i).cromosoma[j] !=0){
                    return;
                }
            }     
        }
        Poblacion.clear(); //Limpiamos la poblacion
        inicializarPoblacion();
    }
   
    public static void evaluarPoblacion (int fila){
        List<Zombie> ZombiesFila = new ArrayList<Zombie>();
        int i,j;
        for (i=0; i< TamPoblacion; i++){
            ZombiesFila.clear();
            for (j=0; j<Z; j++){
                if(Zombies.get(j).fila == fila){
                    ZombiesFila.add(new Zombie(Zombies.get(j))); 
                }
            }
            if (ZombiesFila.isEmpty()) return;  //Si no hay zombies en dicha fila, retornamos
            
            ZombiesFila.get(0).columna--; //El primer Zombie se mete en el tablero
            
            simulacion(i, ZombiesFila);
        }
    }
    public static boolean solucion (int ZombieVidaFila, int nrofila,int iteracion){
        //ZombieVidaFila tiene la suma de las vidas de todos los zombiez en la fila
        int i,j;
        nrofila -= 1;
        for (i=0; i<TamPoblacion;i++){
            if(Poblacion.get(i).aptitud == ZombieVidaFila){
                for(j=0; j<4; j++)
                    Jardin[nrofila][j] = Poblacion.get(i).cromosoma[j];
                
                return true;
            }
        }
        if(iteracion==200) {
            //Caso limite se pone el "mejor" individuo  como solución y prende el flag
            int mejor = -1;
            for (i=0; i<TamPoblacion;i++){
                if (Poblacion.get(i).aptitud > mejor){
                    for(j=0; j<4; j++)
                        Jardin[nrofila][j] = Poblacion.get(i).cromosoma[j];
                    
                    mejor = Poblacion.get(i).aptitud;
                }
            }
            MalaSolucion =true;
            return true;
        }
        
        return false;
    }
    
    public static void seleccionar (){
        double q0,q1,q2,q3,q4,q5;
        //Calcular probabilidad total
        int AptitudTotal =0, i;
        
        for (i=0; i<Poblacion.size();i++ ){
            AptitudTotal += Poblacion.get(i).aptitud;
        }
        //Calcular pi de cada individuo
        for (i=0; i<Poblacion.size();i++ ){
            Poblacion.get(i).pi = (float) Poblacion.get(i).aptitud / AptitudTotal;
        }
        
        //Obtener los qi
        q0 = Poblacion.get(0).pi;
        q1 = q0 +Poblacion.get(1).pi;
        q2 = q1 + Poblacion.get(2).pi;
        q3 = q2 + Poblacion.get(3).pi;
        q4 = q3 + Poblacion.get(4).pi;
        q5 = q4 + Poblacion.get(5).pi;
        
        
        //Obtener los valores ramdon del a y seleccionar que individuos escojer
        List<Individuo> PoblacionSeleccionada = new ArrayList<Individuo>();

        for (i=0; i< TamPoblacion; i++){
            //ramdon a
            double a = Math.random();
            //System.out.println("Salio el a: " + a);
            if(a>=0 && a<q0){
                //individuo 0
                PoblacionSeleccionada.add(new Individuo(Poblacion.get(0)));
            }
            else if(a>=q0 && a<q1){
                //individuo 1
                PoblacionSeleccionada.add(new Individuo(Poblacion.get(1)));
            }
            else if(a>=q1 && a<q2){
                //individuo 2
                PoblacionSeleccionada.add(new Individuo(Poblacion.get(2)));
            }
            else if(a>=q2 && a<q3){
                //individuo 3
                PoblacionSeleccionada.add(new Individuo(Poblacion.get(3)));
            }
            else if(a>=q3 && a<q4){
                //individuo 4
                PoblacionSeleccionada.add(new Individuo(Poblacion.get(4)));
            }
            
            else if(a>=q4 && a<q5){
                //individuo 5
                PoblacionSeleccionada.add(new Individuo(Poblacion.get(5)));
            }
            
        }
        //Ya en poblacion seleccionada estan los seleccionados y limpiamos la poblacion vieja
        Poblacion.clear();
        //Agregar Poblacion seleccionada a Poblacion
        for(i=0; i<TamPoblacion; i++){
            Poblacion.add(PoblacionSeleccionada.get(i));
        }
        //borrar variable auxiliar
        PoblacionSeleccionada.clear();
        
    }
 
    public static void alterar (){
        //Primero ver cruce
        int i=0, cont=0;
        double pc, pm;
        List<Individuo> PoblacionParaCruzar = new ArrayList<Individuo>(); 
        //Obtener los valores ramdon de cruce y seleccionar que individuos cruzar
        while (cont<TamPoblacion){
            cont++;
            pc= Math.random();
            if(pc <= ProbCruce){
                //Agregar a lista de poblaciona cruzar
                PoblacionParaCruzar.add(Poblacion.get(i));
                Poblacion.remove(i);
                continue;
            }
            i++;
        }
        
        //Verificar paridad de individuos en caso de impar uno al azar no se cruza
        if(PoblacionParaCruzar.size()%2 == 1){
            int ind = (int) Math.round(RamdonNumber(PoblacionParaCruzar.size()))-1;
            Poblacion.add(PoblacionParaCruzar.get(ind));
            PoblacionParaCruzar.remove(ind);
        }
        
        //Ahora aplicar cruces 
        int puntoCruce,j,aux, ind1,ind2;
       
        //verificar que tamaño sea mayor a 0
        while( PoblacionParaCruzar.size()!=0){
            puntoCruce = (int) Math.round(RamdonNumber(3)); //Punto de cruce
            
            ind1 = (int) Math.round(RamdonNumber(PoblacionParaCruzar.size()))-1;
            ind2 = (int) Math.round(RamdonNumber(PoblacionParaCruzar.size()))-1;
            //Se verifica que no sea el mismo individuo
            while(ind1 == ind2){
                ind1 = (int) Math.round(RamdonNumber(PoblacionParaCruzar.size()))-1;
                ind2 = (int) Math.round(RamdonNumber(PoblacionParaCruzar.size()))-1;
            }
               
            for (j=0; j<puntoCruce; j++){ //Cruce
                aux = PoblacionParaCruzar.get(ind1).cromosoma[j];
                PoblacionParaCruzar.get(ind1).cromosoma[j] = PoblacionParaCruzar.get(ind2).cromosoma[j];
                PoblacionParaCruzar.get(ind2).cromosoma[j] = aux;
            }
           
            
            Poblacion.add(PoblacionParaCruzar.get(ind1));
            Poblacion.add(PoblacionParaCruzar.get(ind2));
            if (ind1<ind2){
                PoblacionParaCruzar.remove(ind2);
                PoblacionParaCruzar.remove(ind1);
            }
            else{
                PoblacionParaCruzar.remove(ind1);
                PoblacionParaCruzar.remove(ind2);
            }
        }
        
        //Ahora intentar mutacion
        int plantaMutada;
        for (i=0;i<TamPoblacion;i++){
            for(j=0; j<4;j++){
                pm= Math.random();
                if(pm <= ProbMuta){
                    plantaMutada = (int) Math.round(RamdonNumber(4))-1;
                    //Verificar que mutacion no sea la misma que ya esta
                    while(plantaMutada == Poblacion.get(i).cromosoma[j]){
                        plantaMutada = (int) Math.round(RamdonNumber(4))-1;
                    }
                    Poblacion.get(i).cromosoma[j]=plantaMutada;
                }    
            }
        }
    }
    
    public static void infoZombies(List<Zombie> Zombies){
        int z; 
        for (z=0; z<Zombies.size(); z++){
            System.out.println("ID: " + Zombies.get(z).id);
            System.out.println("Fila: " + Zombies.get(z).fila);
            System.out.println("Columna: " + Zombies.get(z).columna);
            System.out.println("Vida: " + Zombies.get(z).vida);
            System.out.println("Turno: " + Zombies.get(z).turno);
        }
    }
    
    public static boolean hayZombies(List<Zombie> ZombiesFila){
        int z;
        for (z=0; z<ZombiesFila.size(); z++)
            if (ZombiesFila.get(z).vida > 0) return true;
          
        return false;
    }
    
    public static boolean zombiesWin(List<Zombie> ZombiesFila){
        int i;
        for (i=0; i<ZombiesFila.size(); i++)
           if (ZombiesFila.get(i).columna == 0) return true;
 
        return false;
    }
    
    public static boolean canShootRepeater(int ind, int planta){
        int i;
        for (i=planta+1; i<4; i++)
            if (Poblacion.get(ind).cromosoma[i] == 1 || Poblacion.get(ind).cromosoma[i] == 2)
                return false;
        return true;
    }
    
    public static void simulacion(int ind, List<Zombie> ZombiesFila){
        int i,z;
         
        while(hayZombies(ZombiesFila) && !zombiesWin(ZombiesFila)){
            //Verificar si los zombies pueden atacar
            int columna;
            for (z=0; z<ZombiesFila.size(); z++){
                columna = ZombiesFila.get(z).columna;
                if ((columna >= 0 && columna < 4) && (Poblacion.get(ind).cromosoma[columna] == 1 || Poblacion.get(ind).cromosoma[columna] == 2)){
                    Poblacion.get(ind).killPlanta(columna); //Zombie ataca
                }
                if(columna == 0 && Poblacion.get(ind).cromosoma[columna] == 3 ){
                    Poblacion.get(ind).killPlanta(columna); //Zombie ataca la mina en la posicion 0,0 y gana
                }    
                
            }
            
            //Verificar si las plantas pueden disparar y aumentar contador(aptitud) de daño causado de planta
            for (i=3; i>=0; i--){
                if (Poblacion.get(ind).alive[i])
                    for (z=0; z<ZombiesFila.size(); z++)
                        if (ZombiesFila.get(z).columna != W && ZombiesFila.get(z).vida > 0){
                            switch (Poblacion.get(ind).cromosoma[i]) {
                                case 1:
                                   if (ZombiesFila.get(z).columna != i){        
                                        ZombiesFila.get(z).setVida(1);
                                        Poblacion.get(ind).setAptitud(1);
                                   }
                                   break;
                                case 2:
                                    if (ZombiesFila.get(z).columna != i){
                                        if (canShootRepeater(ind,i)){
                                            if(ZombiesFila.get(z).vida==1){
                                                ZombiesFila.get(z).setVida(1);
                                                Poblacion.get(ind).setAptitud(1);
                                                for (int r=0; r<ZombiesFila.size(); r++){
                                                    if (ZombiesFila.get(r).columna != W && ZombiesFila.get(r).vida > 0){
                                                        ZombiesFila.get(r).setVida(1);
                                                        Poblacion.get(ind).setAptitud(1);
                                                        break;
                                                    }
                                                }
                                            }else{
                                                ZombiesFila.get(z).setVida(2);
                                                Poblacion.get(ind).setAptitud(2);  
                                            }
                                             
                                        }
                                    }
                                    break;
                                case 3:
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
            for (z=0; z<ZombiesFila.size(); z++){
                if (ZombiesFila.get(z).columna == W && ZombiesFila.get(z).turno == 0){
                    ZombiesFila.get(z).setColumna(W-1);
                    break;
                }
                ZombiesFila.get(z).turno--;
            }  
        }
    }
  
    public static int lifeZombie(int nrofila){
        int i, vidatotal;
        vidatotal = 0;
        for (i=0; i<Z; i++)
            if (Zombies.get(i).fila == nrofila)
                vidatotal += Zombies.get(i).vida;
        
        return vidatotal;
    }
    
    
    public static void main(String[] args) {
        //Leyendo la entrada
        Scanner consola = new Scanner(System.in);
        W = consola.nextInt(); //Columnas del jardín
        H = consola.nextInt(); //Filas del jardín
        Z = consola.nextInt(); //Cantidad de zombies
        
        Jardin = new int[H][W]; //Le damos dimensiones al jardin
        
        
        int ZF, ZV;
        int i;
        for (i=0; i<Z; i++){
            Zombie zombito = new Zombie();
            ZF = consola.nextInt(); //fila en la que aparece
            ZV = consola.nextInt(); //vida que tiene
            zombito = new Zombie(ZF,ZV,W,i-1); 
            Zombies.add(zombito); //Añadimos el zombie número i 
        } 
        
        int vidaZombies = 0;
        for (i=0; i<H; i++){
            vidaZombies = lifeZombie(i+1);
            algoritmoGenetico (vidaZombies,i+1);  //vida de todos los zombies de la fila i+1, y la fila i+1
        }
        
        //IMPRIMIR SOLUCION
        if(MalaSolucion) System.out.println("No se encontró una solución al problema inicial, pero esta es una aproximación a la solución");
        int j;
        for (i=0; i<H; i++){
            for (j=0; j<4; j++)
                System.out.print(Jardin[i][j]+ " ");
            System.out.print("\n");
        }    
    }    
}

class Zombie{
    public int fila;
    public int columna;
    public int vida;
    public int turno;
    public int id;
    Zombie(){
        this.fila = 0;
        this.vida = 0;
        this.columna =-1;
        this.id = (int) Math.round(IA_AlgoritmoGenetico.RamdonNumber(1000));
        this.turno = 0;
    }
   
    public Zombie(int fila, int vida, int columna, int turno){
        this.fila = fila;
        this.vida = vida;
        this.columna = columna;
        this.id = (int) Math.round(IA_AlgoritmoGenetico.RamdonNumber(1000));
        this.turno = turno;
    }

    public Zombie(Zombie z) {
        this.fila = z.fila;
        this.vida = z.vida;
        this.columna = z.columna;
        this.id = z.id;
        this.turno=z.turno;
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
    public boolean alive[];
    public int aptitud;
    public float pi;
    
    Individuo(){
        this.cromosoma = new int[4]; 
        this.alive = new boolean[4];
        this.aptitud = 0;
        this.pi = 0;
    }
    Individuo(int fila[]){
        this.cromosoma = new int[4]; 
        this.alive = new boolean[4];
        this.aptitud = 0;
        this.pi = 0;
        
        int x;
        for (x=0;x<4;x++){
            this.cromosoma[x] = fila[x];
            this.alive[x] = this.cromosoma[x] != 0;
        }   
    }
    
    
    Individuo(Individuo i){
        this.cromosoma = new int[4]; 
        this.alive = new boolean[4];
        this.aptitud = 0;
        this.pi = 0;
        
        int x;
        for (x=0;x<4;x++){
            this.cromosoma[x] = i.cromosoma[x];
            this.alive[x] = this.cromosoma[x] != 0;
        }  
        
    }
  
    public void setAptitud(int cant){
        this.aptitud += cant;
    }
    
    public void killPlanta(int i){
        this.alive[i] = false;
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
