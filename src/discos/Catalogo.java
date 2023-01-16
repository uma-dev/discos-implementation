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
    private Disco[] catalogo;
    private int numDscs =0; //numero de discos en el catalogo
    private GregorianCalendar[][] fechas;
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

    

    public Catalogo () {
        this.numDscs = MAX_DISCOS;
        this.catalogo = new Disco[ MAX_DISCOS];
    }
    public Catalogo ( int numDscs ){
        this.numDscs = numDscs;
        this.catalogo = new Disco[ MAX_DISCOS];
    }
    public Catalogo ( int numDscs, int arregloInicial){
        this.numDscs = numDscs;
        this.catalogo = new Disco[ MAX_DISCOS];
    }

}
