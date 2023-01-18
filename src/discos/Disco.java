package discos;
import java.util.Scanner;
import java.util.GregorianCalendar;

/**
 * Disco digital para su uso por una companhia de 
 * "streamming". Se usa como ejemplo introductorio a 
 * algunos aspectos de Java.
 *
 * @author Elisa Viso.
 * @version 1.0, 09/11/15.
 */                           
public class Disco implements ServiciosDisco  {
  private static final short CD = 1,
                             DVD = 2,
                             BR = 3;
  private static final int   ANHO1 = 1900, // Primer anho valido
                             ANHOU = 2023; // Ultimo anho valido
  private static final int MAX_PERMITIDAS =  9999;
  private static final int LUG_TD = 1,
                           LUG_NOMBRE = 40,
                           LUG_ANHO = 4,
                           LUG_PERMITIDAS = 4,
                           LUG_ACTIVAS = 4;
  private static final String DIAS = "         " + "domingo  "
                                     + "lunes    " + "martes   "
                                     + "miercoles" + "jueves   "
                                     + "viernes  " + "sabado   " ;
  private static final int TAM_DIA = 9;
  private static final String ESPACIOS = "                    " 
                                       + "                    ";
  /* atributos de la clase */
  private final short TIPO_DISCO; // 1:CD, 2:DVD, 3:BR
  private final String NOMBRE;    // artista o pelicula
  private final int ANHO;        // fecha de grabacion
  private int permitidas;         // Maximo transmisiones permitidas
  private int activas;            // Transmisiones activas
  
  /** 
   * Constructor por omision. Interacciona con el usuario 
   * para pedirle los datos de inicializacion del disco.
   */
  public Disco()  {
    Scanner sc = new Scanner(System.in);  // Construccion del Scanner
    System.out.println("Que deseas grabar: "
                         + "(1) CD, (2) DVD, (3) BlueRay");
    System.out.print("Elige tipo de disco (1,2,3):--> ");
    this.TIPO_DISCO = checaTipo(sc.nextShort(),CD,BR);
    sc.nextLine();
    System.out.print("Ahora dame " +
                       ( this.TIPO_DISCO == CD
                        ? "el nombre del cantante"
                        : this.TIPO_DISCO == DVD
                          ? "el nombre de la pelicula"
                          : "el nombre de la serie" ) 
                       + "-->");
    this.NOMBRE = checaCadena(sc.nextLine());
    System.out.print("Ahora toca el anho en que fue grabado "
                       + "(" + ANHO1 + "-"+ ANHOU + "): -->");
    this.ANHO = checaFecha(sc.nextInt(),ANHO1,ANHOU);
    sc.nextLine();
    System.out.print("Dame ahora el numero de "
                       + "transmisiones permitidas (1-"+ MAX_PERMITIDAS +") -->");
    this.permitidas = checaRangos(sc.nextInt(),1,MAX_PERMITIDAS);
    sc.nextLine();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
    System.out.println("Gracias");
    //sc.close(); //FIX-Cierra el Scanner del metodo main 
  }
  
  /**
   * Constructor a partir del tipo de disco, nombre y fecha
   * 
   * @param tipo si es CD, DVD o BR.
   * @param nombre del artista o pelicula.
   * @param fecha de grabacion.
   */
  public Disco ( short tipo, String nombre, int fecha)  {
    this.TIPO_DISCO = checaTipo(tipo,CD,BR);
    this.NOMBRE = nombre;
    this.ANHO = checaFecha(fecha,ANHO1,ANHOU);
  }
  
  /**
   * Constructor a partir del tipo de disco, nombre, fecha
   * y numero de transmisiones permitidas.
   * 
   * @param tipo si es CD, DVD o BR.
   * @param nombre del artista o pelicula.
   * @param fecha de grabacion.
   * @param permitidas el numero de transmisiones.
   */
  public Disco ( short tipo, String nombre, int fecha, int permitidas)  {
    this.TIPO_DISCO = checaTipo(tipo,CD,BR);
    this.NOMBRE = nombre;
    this.ANHO = checaFecha(fecha,ANHO1,ANHOU);
    this.permitidas = checaRangos(permitidas,1, MAX_PERMITIDAS);
  }
  
  /**
   * Proporciona el tipo del disco.
   *
   * @return el tipo de disco entre 1 y 3
   */
  @Override
  public short getTIPO_DISCO()   {
    return this.TIPO_DISCO;
  }
  /**
   * Proporciona el nombre que tenga asociado el disco. Puede ser el
   * musico si se trata de un CD o la pelicula si se trata de un DVD
   * o Blue-Ray.
   *
   * @return nombre del musico si se trata de un CD o de la pelicula
   * si se trata de un DVD o Blue-Ray.
   */
  @Override
  public String getNOMBRE()   {
    return this.NOMBRE;
  }
  
  /**
   * Proporciona el anho en que fue grabado el disco.
   *
   * @return fecha de grabacion.
   */ 
  @Override
  public int getANHO()   {
    return this.ANHO;
  }
  
  /**
   * Proporciona el numero de transmisiones permitidas.
   *
   * @return numero de transmisiones permitidas.
   */
  @Override
  public int getPermitidas()   {
    return this.permitidas;
  }
  
  /**
   * Proporciona el numero de transmisiones activas.
   *
   * @return numero de transmisiones activas.
   */
  @Override
  public int getActivas()   {
    return this.activas;
  }
  
  /**
   * Modifica el valor de transmisiones permitidas.
   *
   * @param permisos valor que se desea establecer
   */
  @Override
  public void setPermitidas(int permisos) {
    this.permitidas = checaRangos(permisos,1, MAX_PERMITIDAS);
  }
  
  /**
   * Modifica el valor de transmisiones activas.
   *
   * @param activ valor que se desea establecer
   */
  @Override
  public void setActivas(int activ)  {
    this.activas= checaRangos(activ,1,this.permitidas);
} 
  /**
   * Muestra de forma estatica el contenido de este disco.
   * @param encabezado Texto antes del formato en plano del disco
   * @return una cadena con la informacion y que contiene saltos de
   *         linea.
   */
  @Override
  public String muestraDisco(String encabezado)  {
    return ( encabezado + ( this.TIPO_DISCO == CD
            ? "CD"
            : this.TIPO_DISCO == DVD
            ? "DVD"
            : "BLUERAY" ) 
            + " " + this.NOMBRE + ", " + this.ANHO 
            + ",  " + this.permitidas + " tx permitidas." );
  }
  
  /**
   * Otorga una transmision dada una fecha y una hora, contestando con la fecha y hora en que
   * la esta dando. Si no la puede dar, responde
   * negativamente. Actualiza el numero de transmisiones activas.
   *
   * @return Un mensaje diciendo si pudo o no otorgar la transmision.
   */
  public String daTransmision( GregorianCalendar calendar)   {
    boolean siHay = activas < permitidas ? true : false;
    activas += siHay ? 1: 0;
    return siHay ? ("Tx a las: " + daHora(calendar) + 
                " del " + daFecha(calendar)) : "No hay Tx disponibles";
  } 
 /**
   * Otorga una transmision, contestando con la fecha y hora en que
   * la realizo, si no la puede dar, responde
   * negativamente, actualiza el numero de transmisiones activas.
   * @return Un mensaje diciendo si pudo o no otorgar la transmision.
   */
  @Override
  public String daTransmision( )   {
    GregorianCalendar cal = new GregorianCalendar(); 
    boolean siHay = activas < permitidas ? true : false;
    activas += siHay ? 1: 0;
    return siHay ? ("Tx a las: " + daHora(cal) + 
                " del " + daFecha(cal)) : "No hay Tx disponibles";
  } 
  /**
   * Obtiene la fecha con formato a partir de un objeto de la clase GregorianCaledar
   * @param calendar calendario de la clase GregorianCalendar
   * @return la hora con formato
   */
  public static String daHora( GregorianCalendar calendar){
    int hora = calendar.get(calendar.HOUR);
    int minutos = calendar.get(calendar.MINUTE);
    int segundos = calendar.get(calendar.SECOND);
    String ampm = calendar.get(calendar.AM_PM) == calendar.AM
                  ? "AM"
                  : "PM";
    hora = (hora%12 == 0 && calendar.get(calendar.AM_PM) == calendar.PM)
          ? 12 
          : hora;
    String minutosString =  minutos<10
                  ? "0"+minutos 
                  : "" + minutos;  
    String segundosString =  segundos<10
                  ? "0"+segundos 
                  : "" + segundos;        
    return "" + hora + ":" + minutosString + ":" + segundosString + " " + ampm;
  }
/**
 * Obtiene el nombre de la semana correspondiente a su posicion
 * @param numDia
 * @return
 */
  private static String nombreDia (int numDia){
    return DIAS.substring(TAM_DIA*numDia, TAM_DIA*(numDia+1)).trim();
  }
/**
 * Obtiene la fecha con formato de un objeto GregorianCalendar.
 * @param calendar calendario de la clase GregorianCalendar.
 * @return fecha con formato.
 */
  public static String daFecha( GregorianCalendar calendar){
    int dia = calendar.get(calendar.DAY_OF_MONTH);
    int mes = calendar.get(calendar.MONTH)+1; //0 corresponde a enero
    int anho = calendar.get(calendar.YEAR);
    int diaSemana = calendar.get(calendar.DAY_OF_WEEK);
    return "" + nombreDia(diaSemana) + " " + dia+ "/" + mes + "/" + anho;
  }

  /**
   * Actualiza el numero de transmisiones activas.
   */
  @Override
  public boolean terminaTransmision()   {
    boolean hayActivas = activas > 0;
    activas -= hayActivas ? 1 : 0;
    return hayActivas;
  }
 /** Proporciona una cadena con los distintos campos ocupando un
    * lugar definido.
    *
    * 
    * @return La informacion del disco linealizada en forma de
    *         cadena, todos los discos con la misma informacion.
    */  
    @Override
  public String toString(){
    return ""+ editaNum(TIPO_DISCO, LUG_TD) + editaCad(NOMBRE, LUG_NOMBRE) +
                    editaNum(ANHO, LUG_ANHO) +  editaNum(permitidas, LUG_PERMITIDAS) 
                    + editaNum(activas, LUG_ACTIVAS);
  }
  /**
   * Duplica a este disco, construyendo otro objeto con los mismos
   * valores, pero con identidad distintinta.
   * @return un nuevo disco identico al que se le pide.
   */
  @Override
  public ServiciosDisco copiaDisco( )   {
    return  new Disco(TIPO_DISCO, NOMBRE, ANHO, permitidas);
  }
  
  /**
   * Verifica que el tipo de disco que se guarde sea el numero 
   * correcto.
   * @param tipo un numero entero. 
   * @param limI limite inferior
   * @param limS limite superior
   * @return un 1, 2 o 3, que son tipos correctos.
   */
  private short checaTipo(short tipo, short limI, short limS) {
    return tipo < limI
      ? limI
      : (tipo > limS
           ? limS
           : tipo);
  }
  
  /**
   * Verifica que el nombre de la artista o pelicula no sea 
   * una cadena vacia o una referencia nula.
   * @param cadena a verificar.
   * @return cadena corregida, en su caso.
   */
  private String checaCadena(String cadena) { 
    return cadena == null || cadena.length() == 0
      ? "No identificado"
      : cadena;
  }
  
  /**
   * Verifica que el anho de grabacion cumpla estar 
   * entre primerAnho y ultimoAnho.
   *
   * @param fecha el anho de grabacion.
   * @param primerAnho el limite iferior valido.
   * @param ultimoAnho el limite superior valido.
   * @return un entero tal que primerAnho <= fecha <= ultimoAnho.
   */
  private int checaFecha(int fecha,int primerAnho, int ultimoAnho)  {
    return fecha < primerAnho
      ? primerAnho
      : fecha > ultimoAnho
        ? ultimoAnho
        : fecha;
  }
    
  /**
   * Verifica que el entero dado que se guarde este entre 
   * los rangos dados.
   *
   * @param que un numero entero.
   * @param limI entero menor valido.
   * @param limS entero mayor valido.
   * @return un entero tal que limI <= entero <= limS.
   */
  public static int checaRangos(int que, int limI, int limS)  {
    return que < limI
      ? limI
      : que > limS
        ? limS
        : que;
  }
  /**
   * Edita los numeros en el metodo toString a un numero fijo de caracteres
   * @param valor
   * @param lugares
   * @return
   */
  private String editaNum(int valor, int lugares){
    String conEspacios = ESPACIOS + valor;
    return conEspacios.substring(conEspacios.length()-lugares);
  }
  /**
   * Edita las cadenas de caracteres en el metodo toString a un numero fijo de caracteres
   * @param cadena 
   * @param lugares
   * @return
   */
  private String editaCad(String cadena, int lugares){
    String conEspacios = cadena + ESPACIOS;
    return conEspacios.substring(0, lugares);
  }

}