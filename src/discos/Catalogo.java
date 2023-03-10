package discos;

import java.util.Scanner;
import java.util.GregorianCalendar;

/**
 * Simula el uso de un catalogo de discos; 
 * no se implementa alguna interfaz, y en cada sesion se puede  agregar discos, 
 * consultar el catalogo, iniciar transmisiones o terminar transmisiones. Toda
 * actividad hecha se registra en el catalogo. 
 * @author Coursera 
 * @version 2023
 */

public class Catalogo {
    //Constantes de clase
    /** Numero maximo de discos en el catalogo */
    private static final int MAX_DISCOS = 10;
    private static final String[] MENU_CATALOGO = {
        "Salir",                                     //0
        "Agregar discos",                            //1
        "Mostrar discos",                            //2
        "Mostrar discos activos",                    //3
        "Pedir transmision",                         //4
        "Terminar transmision",                      //5
        "Mostrar disco",                             //6
        "Mostrar historico de un disco",             //7
        "Mostrar historico de todos los discos",};   //8
    /** Accion para salir del menu */
    public static final int SALIR = 0,
    /** Accion para agregar un disco al catalogo*/
                            AGREGA = 1,
    /** Accion de mostrar discos del catalogo*/
                            MUESTRA_DISCOS = 2,
    /** Accion de mostrar discos activos*/
                             MUESTRA_ACTIVOS = 3,
    /** Pedir una transmision*/
                            PEDIR_TX = 4,
    /** Terminar una transmision*/
                            TERMINAR_TX = 5,
    /** Accion de mostrar un disco*/
                            MUESTRA_UN_DISCO = 6,
    /** Accion de mostrar el historico de un disco*/
                            MUESTRA_HIST = 7,
    /** Accion de mostrar el historico de todos los discos*/
                            MUESTRA_TODOS_HIST = 8;


    //Atributos de la clase
    private Disco[] catalogo; //catalogo de discos
    private int numDscsRegistrados =0; //numero de discos en el catalogo
    // arreglos paralelos que se pueden simplificar con uso de otras clases como Registro que consiste en dos fechas 
    private GregorianCalendar[][] fechasTxActivas;
                            /**
                             * Un renglon para cada disco, una columna por transmision.
                             * Cada renglon tiene un numero de columnas dado por el disco en
                             * ese renglon. El numero de fechas registradas esta dado por el 
                             * atributo activas  del disco
                             */
    private GregorianCalendar [][][] historico; 
                            /** pareja de renglones para cada disco
                             * [Disco] [fecha_inicio][fecha fin] */
    private int[] numHist;
                            /**  Da, para cada disco, el numero de transmisiones 
                             * inciadas y  terminadas.
                             */

    /**
     * Construye un catalogo, da el numero maximo de posible de registros para discos y anota que no
     * tienen ningun disco registrado. Construye los arreglos paralelos historicos y fechas con su 
     * numero de renglones o tablas correspodiente. Las demas dimensiones se iran llenando al registrar cada disco. 
     */
    public Catalogo () {
        this.catalogo = new Disco[ MAX_DISCOS];
        this.numDscsRegistrados = 0; // No hay discos registrados al momento de construir el objeto
        /**
         * El numero de columnas/posiciones para registrar tx activas
         * se da una vez que se construya el objeto.
         */
        this.fechasTxActivas = new GregorianCalendar[catalogo.length][];
        this.historico = new GregorianCalendar[catalogo.length][2][]; //Hay catalogo.legth numero de tablas y 2 renglones (fecha de inicio, fecha de fin)
        this.numHist = new int[catalogo.length]; //cada disco tiene su propio numero de tx inciadas y terminadas
    }
    /**
     * Construye un catalogo con un numero definido de discos.
     * @param numDscs entero con el numero de discos para el catalogo
     */
    public Catalogo ( int numDscs ){
        this.numDscsRegistrados = 0; // No hay discos registrados
        this.fechasTxActivas = new GregorianCalendar[catalogo.length][];
        this.historico = new GregorianCalendar[catalogo.length][2][]; //Hay catalogo.legth numero de tablas y 2 renglones (fecha de inicio, fecha de fin)
        this.numHist = new int[catalogo.length]; //cada disco tiene su propio numero de tx inciadas y terminadas
    }
    /**
     * Construye un catalogo de un numero definido de discos y con un arreglo inicial.  
     * @param numDscs entero con un numero de discos para el catalogo
     * @param arregloInicial arrelgo Inicial con el que se inicia el catalogo
     */
    public Catalogo ( int numDscs, Disco[] arregloInicial){
        int numIniciales =  arregloInicial == null ? 0 : arregloInicial.length; //descartar que sea null
        numDscs = Math.max(numIniciales, numDscs);   //descartar que haya mas elementos en el arregloInicial que numDscs
        //numDscs = Disco.checaRangos(numDscs, 1, MAX_DISCOS); //verificar que este en el rango permitido, este el tamanho del catalogo
        numIniciales = Math.min(numIniciales, numDscs); // Numero de discos a copiar (arregloLength), son el minimo entre los que se le pasaron y el tamanho del catalogo.
        
        this.catalogo = new Disco[numDscs];
        this.numDscsRegistrados = 0; 
        this.fechasTxActivas = new GregorianCalendar[catalogo.length][];
        this.historico = new GregorianCalendar[catalogo.length][2][]; 
        this.numHist = new int[catalogo.length]; //cada disco tiene su propio numero de tx inciadas y terminadas
        for (int i = 0; i< numIniciales; i++){
            if (arregloInicial[i] == null){
                continue;
            }
            this.catalogo[i] = arregloInicial[i];
            int numPrest = catalogo[i].getPermitidas();
            fechasTxActivas[i] = new GregorianCalendar[numPrest];
            historico[i][0] = new GregorianCalendar[numPrest*2];
            historico[i][1] = new GregorianCalendar[numPrest*2];
            this.numDscsRegistrados++; //se actualiza el numero de discos registrados
        }
    }
    /**
     * Regresa el catalogo de discos.
     * @return catalogo, una lista de objetos Disco
     */
    public Disco[] getCatalogo(){
        return this.catalogo;
    } 
    /**
     * Regresa un entero que corresponde al numero 
     * de discos registrados.
     * @return un entero, numDscsRegistrados
     */
    public int getNumDscsRegistrados(){
        return this.numDscsRegistrados;
    }
    /**
     * Regresa un arreglo de dos dimensiones de elementos
     *  GregorianCalendar. 
     * @return fechasTxActivas, un arreglo de dos dimensiones.
     */
    public GregorianCalendar[][] getFechasTxActivas(){
        return this.fechasTxActivas;
    }
    /**
     * Regresa un arreglo de tres dimensiones que representa 
     * el historico de cada disco.
     * @return arreglo de tres dimensiones, historico.
     */
    public GregorianCalendar[][][] getHistorico(){
        return this.historico;
    }
    /**
     * regresa un arreglo de enteros que representa el numero
     *  de registros por cada disco en un catalogo.
     * @return arreglo de una dimension de enteros, numHist.
     */
    public int[] getNumHist(){
        return this.numHist;
    }
    /**
     * Actualiza el catalogo de discos.
     * @param catalogo  una lista de objetos Disco.
     */
    public void setCatalogo( Disco[] newCatalogo ){
        int catalogoLen = newCatalogo == null ? 0 : newCatalogo.length;
        if (catalogoLen == 0){
            this.catalogo = null;
            this.numDscsRegistrados = 0;
            this.fechasTxActivas = null;
            this.historico = null;
            this.numHist = null;
            return; 
        }
        //Inicializacion de atributos en cero y al nuevo tamanho
        this.catalogo = new Disco[catalogoLen];
        this.numDscsRegistrados = 0;
        this.fechasTxActivas = new GregorianCalendar[catalogoLen][];
        this.historico = new GregorianCalendar[catalogoLen][2][];
        this.numHist = new int[catalogoLen];
        //Copiamos elemento a elemento 
        for (int i = 0; i< catalogoLen; i++){
            if (newCatalogo[i] == null){
                continue;
            }
            this.catalogo[i] = newCatalogo[i];
            int numPrest = catalogo[i].getPermitidas();
            catalogo[i].setActivas(0);
            fechasTxActivas[i] = new GregorianCalendar[numPrest]; 
            historico[i][0] = new GregorianCalendar[numPrest*2];
            historico[i][1] = new GregorianCalendar[numPrest*2];
            this.numDscsRegistrados++; //se actualiza el numero de discos registrados
        }
    } 
    
    /**
     * Agrega un disco al catalogo siempre que sea posible.
     * @param nuevoDisco  Objeto tipo Disco. 
     * @return true si es que pudo agregar el nuevo disco  
     * o false si no fue posible
     */
    public boolean addDisco (Disco nuevoDisco){
        if(nuevoDisco == null || numDscsRegistrados >= catalogo.length ){ 
            return false;
        }
        catalogo[numDscsRegistrados] = nuevoDisco;
        int numPrest = catalogo[numDscsRegistrados].getPermitidas();
        fechasTxActivas[numDscsRegistrados] = new GregorianCalendar[numPrest]; 
        historico[numDscsRegistrados][0] = new GregorianCalendar[numPrest*2];
        historico[numDscsRegistrados][1] = new GregorianCalendar[numPrest*2];
        this.numDscsRegistrados++; //se actualiza el numero de discos registrados
        return true;
    }
    /**
     * da transmision al disco que esta en la posicion que se indica. 
     * @param cualDisco entero que indica la posicion del disco al que
     *  se le dara la tx.
     * @return true si pudo hacer la tx, false si no pudo hacerla.
     */
    public boolean daTransmision( int cualDisco ){
        if (cualDisco >= this.catalogo.length || cualDisco < 0) {
            return false;
        }
        if (catalogo[cualDisco] == null ){ 
            return false;
        }
        if (catalogo[cualDisco].getActivas() >= catalogo[cualDisco].getPermitidas()){
            System.out.println("\nEl disco " 
                                + catalogo[cualDisco].getNOMBRE() 
                                + " no tiene mas Tx permitidas\n");
            return false;
        }
        GregorianCalendar fechaAhora = new GregorianCalendar();
        fechasTxActivas[cualDisco][catalogo[cualDisco].getActivas()] = fechaAhora;
        System.out.println(catalogo[cualDisco].daTransmision(fechaAhora));
        return true;
    }
    /**
     * Obtiene una cadena de texto con todos los discos de un catalogo.
     * @param texto Un texto plano antes de mostrar el catalogo.
     * @return cadena de texto con todos los discos.
     */
    public String muestraCatalogo(String texto){
        if (catalogo == null ){
            return texto + "\nNo existe el catalogo de discos";
        }
        if(numDscsRegistrados <= 0 ){
            return texto + "\nNo hay registros en el catalogo"; 
        }
        texto = texto == null ? ""+"\n" : texto+"\n";
        for(int i=0; i<numDscsRegistrados; i++){
            if(catalogo[i] == null){
                texto += "No hay disco en esta posicion";
                continue; 
            }
            texto += "\nDisco ["+ i + "] " + catalogo[i];
        }
        texto += "\n";
        return texto;
    }
    /**
     * Muestra los discos activos junto con sus fechas asociadas. 
     * @param texto cadena de texto que hace las veces de encabezado.
     * @return cadena de texto con los discos activos, as?? como
     *  sus fechas de transmisiones activas.
     */
    public String muestraActivos(String texto){
        if (catalogo == null || numDscsRegistrados <= 0){
            return texto + "\n No hay discos en el catalogo";
        }
        String cadena = texto == null ? "\n" : texto+"\n";
        for(int i=0; i<numDscsRegistrados; i++){
            if(catalogo[i] == null){continue;}
            int numActivas = catalogo[i].getActivas();
            if (numActivas <= 0){continue;}
            cadena += "\n  Disco ["+ i + "] " + catalogo[i]+ "\n";
            for (int j=0; j<numActivas; j++){ 
               cadena += "\t["
                    + j + "] "
                    + daCalendario(fechasTxActivas[i][j]) + "\n"; 
            }
        }
        cadena += "\n";
        return cadena;
    }
    private String daCalendario ( GregorianCalendar fecha){
        if (fecha == null){
            return " fecha invalida";
        }
        String fechaString = Disco.daFecha(fecha) 
                            + (fecha.get(fecha.HOUR) == 1 ? " a la" : " a las ")
                            + Disco.daHora(fecha) ;
        return fechaString;
    }
    /**
     * Muestra las fechas de transmision activas de un disco en particular.
     * @param cualDisco un entero con la posicion del disco a mostrar.
     * @return una cadena con el texto solicitado.
     */
    public String muestraActivas (int cualDisco ){
        if(catalogo==null || cualDisco<0 || cualDisco>=catalogo.length){
            System.out.println("No existe el disco dentro del catalogo");
            return null;
        }
        int cuantas = catalogo[cualDisco].getActivas();
        if(cuantas<=0){
            System.out.println("El disco no tiene Tx activas");
            return null;
        }
        String cadena = catalogo[cualDisco].muestraDisco("Tx activas: ") + "\n";
        for (int i=0; i< cuantas; i++){
            if(fechasTxActivas[cualDisco][i] != null ){
                cadena += "    [" + i + "] " 
                        + daCalendario(fechasTxActivas[cualDisco][i]) 
                        + "\n";
            }
            else cadena += "fecha no registrada \n";
        }
        return cadena;
    }
    /**
     * Metodo para terminar la Tx de un disco del catalogo.
     * @param cualDisco entero con la posicion del disco que debe terminar su Tx.
     * @param cons Scanner para seleccionar la Tx a terminar.
     * @return true si pudo terminar la Tx del disco, false en caso contrario.
     */
    public boolean terminaTx(int cualDisco, Scanner cons){
        if( cualDisco<0 || cualDisco>=numDscsRegistrados || catalogo[cualDisco] == null){
            System.out.println("El disco " + cualDisco + " no existe"  );
            return false;
        }
        if (cons == null )  {      // verificar consola
            System.out.println("No es una consola valida");
            return false;
          }
        System.out.println("\n -------------------------\nPara el disco " 
                        + catalogo[cualDisco].muestraDisco( "Disco [" + cualDisco + "] ") );
        System.out.println("Tenemos las siguientes Tx activas: \n");
        String cadena = muestraActivas(cualDisco);
        if (cadena == null){
            System.out.println("No hay Tx activas");
            return false;
        }
        System.out.println(cadena);
        int numActivas = catalogo[cualDisco].getActivas();
        int cualTrans = pideNum(cons, "Elige la Tx a terminar", 0, numActivas-1);
        if(cualTrans == -1){ 
            System.out.println("La Tx solicitada no existe");
            return false;
        }
        GregorianCalendar fechaInicio = fechasTxActivas[cualDisco][cualTrans];
        GregorianCalendar fechaFin = new GregorianCalendar();
        int donde = numHist[cualDisco];
        historico[cualDisco][0][donde] = fechaInicio;
        historico[cualDisco][1][donde] = fechaFin;
        numHist[cualDisco] ++;
        System.out.println("Transmision terminada: " + daCalendario(fechaFin));
        if( eliminaCelda(fechasTxActivas[cualDisco], cualTrans) ) {
            catalogo[cualDisco].terminaTransmision();
            return true;       //Si pudo eliminar la celda solicitada en fechasTxActivas
        }
        return false; //No pudo eliminar la celda solicitada en fechasTxActivas
    }

    /**
     * Pide un mensaje del selector, un objeto Scanner, asi como 
     * un limite inferior y superior, regresa -1 si elige un numero fuera del rango mandado.
     * @param cons objeto Scanner para hacer la lectura de la opcion solicitada.
     * @param msg Cadena de texto para mostrar en el selector.
     * @param min Limite inferior, un entero.
     * @param max Limite superior, un entero.
     * @return el numero seleccionado o regresa -1 si esta fuera del rango.
     */
    private static int pideNum(Scanner cons, String msg, int min, int max){
        int num = -1;
        System.out.println(msg + " (entre el valor " + min + " y "+ max + ") terminado con [Enter]");
        num = cons.nextInt();
        cons.nextLine();
        if(num > max || num < min){
            num = -1;
        }
        return num;
    }
    /**
     * Elimina la celda solicitada del arreglo y hace los 
     * decrementos correspondientes.
     * @param array un arreglo de objetos
     * @param elemento posicion del objeto a eliminar.
     * @return true si pudo eliminar el objeto y false si no pudo.
     */
    private static boolean eliminaCelda (Object[] array, int elemento){
        if (array == null){
            System.out.println("El arreglo no existe");
            return false;
        }
        if (elemento <0 || elemento >= array.length){
            System.out.println(" Indice fuera del rango\n");
            return false;
        }
        for (int i=elemento+1; i<array.length && array[i]!=null; i++){
            array[i-1]=array[i];
            array[i] = null; //El ulimo elemento queda valiendo null, de otra forma estaria repetido
        }
        return true;
    }
    /**
     * Muestra el historico de las transmisiones que
     * solo incluye las inciadas que fueron terminadas en un disco dado.
     * @param cualDisco posicion del disco que queremos eliminar. 
     * @return cadena con el historico en formato libre.
     */
    public String muestraHist (int cualDisco ){
        if(cualDisco<0 || cualDisco>=this.numDscsRegistrados){
            return "Este disco no existe ";
        }
        String cadena = "  Historico del disco " 
        + catalogo[cualDisco].getNOMBRE()+ "\n";
        if(numHist[cualDisco] ==0 ){
            cadena += "\tDisco sin historicos\n";
        }        
        for (int i=0; i<numHist[cualDisco]; i++){
            cadena += "\t[" + i + "]" 
                    + " Tx iniciada: " + daCalendario(historico[cualDisco][0][i]) 
                    + " TX terminada: " + daCalendario(historico[cualDisco][1][i]) 
                    + "\n";
        }
        return cadena += "\n";
    }
    /**
     * Muestra el historico de todos los discos que lo tienen dentro
     * del catalogo, no recibe parametros.
     * @return Una cadena con los historicos en formato libre.
     */
    public String muestraHistoricos (){
        String cadena = "Historico de los discos que lo tienen: \n---------------------------------------\n";
        for(int i= 0; i<this.numDscsRegistrados ;i++ ){
            if(catalogo[i] != null ) {
                cadena += muestraHist(i);
            }
        }
        return cadena;
    }  
    /** 
     * Menu iterativo sobre las funcionalidades
     */
    public void conectaCatalogo(){
        // Saludar al usuario
        // Mostrarle el menu
        // Pedirle su opcion
        // De acuerdo con la opcion elegida, mostrar el bloque correspondiente
        Scanner cons = new Scanner(System.in);
        Disco nuevoDisco;
        Disco elDisco;
        int opcion=0;

        System.out.println("\n---------Bienvenido al sistema----------");
        do{
            System.out.println("Menu de opciones\n"
                                + "======================"); 
            for (int i=0; i<MENU_CATALOGO.length; i++){
                System.out.println( (i<10 ? " ": "") 
                                    +"["  + i + "] " 
                                    + MENU_CATALOGO[i] );
            }

            System.out.println("\n Elige una opcion              ----->");
            opcion = pideNum(cons, " ", 0, MENU_CATALOGO.length );
            int cualDisco;
            int sigDato;
            switch (opcion){
                case SALIR : 
                    System.out.println("Programa terminado, hasta pronto. ");
                    continue;
                case AGREGA:
                    nuevoDisco = new Disco( );
                    if(addDisco(nuevoDisco)){
                        System.out.println( "El disco " + nuevoDisco.getNOMBRE() 
                                             + " ha sido agregado" );
                    }
                    else {
                    System.out.println( "Sin lugar disponible para:  " 
                    + nuevoDisco.getNOMBRE() );
                    }
                    break;
                case MUESTRA_DISCOS:
                    if (catalogo == null || numDscsRegistrados <= 0) {
                        System.out.println("No hay discos registrados "
                        + "en el catalogo");
                        break;
                    }
                    System.out.println(muestraCatalogo("Discos disponibles: ") );
                    break;
                case MUESTRA_ACTIVOS:
                    if(catalogo == null || this.numDscsRegistrados <=0){
                        System.out.println("No hay discos registrados"
                                            + " en el catalogo. ");
                        break;
                    }
                    System.out.println( muestraActivos("Discos activos \n" +
                                        "================") );
                    break;
                case PEDIR_TX:
                    System.out.println( muestraCatalogo("Discos disponibles " +
                    "en el catalogo") );
                    cualDisco = pideNum(cons, "Elige el numero de disco, ", 0, numDscsRegistrados-1);
                    if (cualDisco==-1){
                        System.out.println("El disco elegido no existe");
                        break;
                    }
                    sigDato = catalogo[cualDisco].getActivas();
                    if ( daTransmision(cualDisco) ){
                        System.out.println("Disco [" + cualDisco + "] "
                                            + catalogo[cualDisco].getNOMBRE().trim() 
                                            + ", en transmision " 
                                            + "empezando el " 
                                            + daCalendario(fechasTxActivas[cualDisco][sigDato])
                                            + "\n" );
                    }
                    break;
                case TERMINAR_TX:
                    System.out.println("Elige la transmision que deseas terminar");
                    System.out.println( muestraActivos("Discos con transmisiones activas ") );
                    cualDisco = pideNum(cons, "Elige el numero de disco", 0, numDscsRegistrados-1);
                    if(cualDisco == -1){
                        System.out.println( "El disco seleccionado no existe");
                        break;
                    }
                    if(catalogo[cualDisco].getActivas() <= 0 ){
                        System.out.println( "El disco: " + cualDisco + " no tiene Tx activas");
                        break;
                    }
                    if( terminaTx( cualDisco, cons)){
                        System.out.println("La Tx termino\n");
                    }
                    else System.out.println("No se termino la Tx\n");
                    break;
                case MUESTRA_UN_DISCO:
                    System.out.println( muestraCatalogo("Discos disponibles " +
                                        "en el catalogo") );
                    cualDisco = pideNum(cons, "Elige el numero de disco", 0, numDscsRegistrados-1);
                    if(cualDisco == -1){
                        System.out.println( "El disco elegido: " + cualDisco + " no existe"); 
                        break;
                    }
                    elDisco = catalogo[cualDisco];     
                    if (elDisco == null){
                        System.out.println("El disco elegido no existe");
                        break;
                    } 
                    System.out.println(elDisco.muestraDisco("Disco [" + cualDisco  + "] ") + "\n");
                    break;
                case MUESTRA_HIST:
                    System.out.println( muestraCatalogo("Discos disponibles " +
                                        "en el catalogo") );
                    cualDisco = pideNum(cons, "Elige el numero de disco", 0, numDscsRegistrados-1);
                    if(cualDisco == -1){
                        System.out.println( "El disco elegido: " + cualDisco + " no existe"); 
                        break;
                    }
                    elDisco = catalogo[cualDisco];     
                    if (elDisco == null){
                        System.out.println("El disco elegido no existe");
                        break;
                    } 
                    if (numHist[cualDisco] == 0){
                        System.out.println("El disco [" + cualDisco + "] no tiene historico \n" );
                        break;
                    }
                    System.out.println(muestraHist(cualDisco));
                    break;  
                case MUESTRA_TODOS_HIST:
                    System.out.println(muestraHistoricos());
                    break;
                default:
                    System.out.println("Opcion no implementada");
            }

        }  while (opcion != 0);
        
    }
}
