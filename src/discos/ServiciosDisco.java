package discos;

/*
 * Disco digital para su uso por una compañia de "streaming".
 * Se usa como ejemplo en algunos aspectos introductorios a Java.
 * 
 * @autor: Coursera
 * @version: 1.0, 2023 
 */

 public interface ServiciosDisco{ 
    //Metodos de consulta o acceso 
    
    /**
     * Proporciona el numero de transmisiones activas.
     * @return numero de transmisiones activas.
     */
    public int getActivas ();
    /**
     * Proporciona el anho en que fue grabado el disco.
     * @return fecha de grabacion.
     */
    public int getANHO ();
    /**
     * Proporciona el nombre que tenga grabado el disco.
     * Puede ser el musico si es un CD o nombre de una pelicula si
     * se trata de un DVD o Blueray.
     * @return
     */
    public String getNOMBRE ();
    /**
     * Proporciona el numero de transmisiones permitidas.
     * @return numero de  transmisiones permitidas.
     */
    public int getPermitidas ();
    /**
     * Proporciona el tipo de disco.
     * @return Tipo de disco entre 1 y 3.
     */
    public short getTIPO_DISCO ();


    //Metodos mutantes o de actualizacion

    /**   
     * Modifica el numero de transmisiones activas.
     * @param newActivas valor que se desea establecer.  
     */
    public void setActivas (int newActivas);
    /**
     * Modifica el valor de transmisiones permitidas.
     * @param permisos valor que se desea establecer.
     */
    public void setPermitidas (int permisos);


    //Metodos de  implementacion

    /**
     * Duplica a este disco, construyendo otro objeto con los mismos valores, pero con identidad distinta.
     * @return un nuevo disco identico al que se le pide.
     */
    public ServiciosDisco copiaDisco (); //Aun no existe el objeto tipo Disco, pero Disco es una subclase de ServiciosDisco
    /**
     * Otorga una transmision. contestando con fecha y hora en que la esta dando. Si no la  puede dar responde negativamente.
     * Actualiza el numero de transmisiones activas.
     * @return un mensaje sin pudo o no realizar la transmision. 
     */
    public String daTransmision ();  /* Verifica si tiene o no transmisiones */
    /**
     * Muestra de forma estetica el contenido de este disco.
     * @param encabezado para encabezar lo que se imprima. 
     * @return una cadena con la informacion y saltos de linea. 
     */
    public String muestraDisco (String encabezado);
    /**
     * Actualiza el numero de transmisiones activas. 
     * @return Si pudo (true) y si no (false) una transmision.
     */
    public boolean terminaTransmision ();
    /**
     * Proporciona una cadena con los distintos campos ocupando un lugar definido.
     * @return La informacion del disco linealizada en forma de cadena, todos los discos con la misma  informacion. 
     */
    public String toString  ();

    //override this method in the class
    
    }
