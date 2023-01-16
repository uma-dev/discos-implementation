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
    }

}
