package discos;

import java.util.Scanner;
import java.util.GregorianCalendar;

/**
 * Simula el uso de un catalogo de discos.
 * No se implementa alguna interfaz, y en cada sesion se puede  agregar discos, 
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
        this.catalogo = new Disco[ Disco.checaRangos (numDscs,1,MAX_DISCOS)]; //verifica lim sup e inf mediante el metodo estatico checaRangos de Disco 
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
        numDscs = Disco.checaRangos(numDscs, 1, MAX_DISCOS); //verificar que este en el rango permitido, este el tamanho del catalogo
        numIniciales = Math.min(numIniciales, numDscs); // Numero de discos a copiar (arregloLength), son el minimo entre los que se le pasaron y el tamanho del catalogo.
        
        this.catalogo = new Disco[numDscs];
        this.numDscsRegistrados = 0; 
        this.fechasTxActivas = new GregorianCalendar[catalogo.length][];
        this.historico = new GregorianCalendar[catalogo.length][2][]; 
        this.numHist = new int[catalogo.length]; //cada disco tiene su propio numero de tx inciadas y terminadas

        for (int i = 0; i< numDscs; i++){
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
     * Regresa un entero que corresponde al numero de discos registrados.
     * @return un entero, numDscsRegistrados
     */
    public int getNumDscsRegistrados(){
        return this.numDscsRegistrados;
    }
    /**
     * Regresa un arreglo de dos dimensiones de elementos GregorianCalendar. 
     * @return fechasTxActivas, un arreglo de dos dimensiones.
     */
    public GregorianCalendar[][] getFechasTxActivas(){
        return this.fechasTxActivas;
    }
    /**
     * Regresa un arreglo de tres dimensiones que representa el historico de cada disco.
     * @return arreglo de tres dimensiones, historico.
     */
    public GregorianCalendar[][][] getHistorico(){
        return this.historico;
    }
    /**
     * regresa un arreglo de enteros que representa el numero de registros por cada disco en un catalogo.
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
     * @return true si es que pudo agregar el nuevo disco  o false si no fue posible
     */
    public boolean addDisco (Disco nuevoDisco){
        if(nuevoDisco == null || numDscsRegistrados >= catalogo.length ){ 
            return false;
        }
        this.catalogo[numDscsRegistrados] = nuevoDisco;
        int numPrest = catalogo[numDscsRegistrados].getPermitidas();
        fechasTxActivas[numDscsRegistrados] = new GregorianCalendar[numPrest]; 
        historico[numDscsRegistrados][0] = new GregorianCalendar[numPrest*2];
        historico[numDscsRegistrados][1] = new GregorianCalendar[numPrest*2];
        this.numDscsRegistrados++; //se actualiza el numero de discos registrados
        return true;
    }
    /**
     * da transmision al disco que esta en la posicion que se indica. 
     * @param cualDisco entero que indica la posicion del disco al que se le dara la tx.
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
            System.out.println("El disco " + catalogo[cualDisco].getNOMBRE() + " no tiene mas Tx permitdas");
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
            texto += "\nDisco no. "+ i + catalogo[i];
        }
        texto += "\n";
        return texto;
    }
    /**
     * Muestra los discos activos junto con sus fechas asociadas. 
     * @param texto cadena de texto que hace las veces de encabezado.
     * @return cadena de texto con los discos activos, así como sus fechas de 
     * transmisiones activas.
     */
    public String muestraActivos(String texto){
        if (catalogo == null || numDscsRegistrados <= 0){
            return texto + "\n No hay discos en el catalogo";
        }
        texto = texto == null ? ""+"\n" : texto+"\n";
        for(int i=0; i<numDscsRegistrados; i++){
            if(catalogo[i] == null){continue;}
            if (catalogo[i].getActivas() <= 0){continue;}
            texto += "\n Disco no. "+ i + catalogo[i]+ "\n";
            for (int j=0; j<catalogo[i].getActivas(); j++){ 
               texto += "\n"+ j + daCalendario(fechasTxActivas[i][j]); 
            }
        }
        texto += "\n";
        return texto;
    }
    private String daCalendario ( GregorianCalendar fecha){
        if (fecha == null){
            return "fecha invalida";
        }
        String fechaString = Disco.extraeFecha(fecha) + (fecha.get(fecha.HOUR) == 1 ? " A la" : "A las") + Disco.extraeHora(fecha) ;
        return fechaString;
    }

}
