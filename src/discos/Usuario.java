package discos;

public class Usuario {
    public static void main(String[] args) {
        String saludo = "Bienvenido al sistema de streaming";
        System.out.println(saludo);
        //Disco elMio = new Disco ();
        Disco elMio = new Disco( (short )2, "Ahora lo ves, ahora no", 2014, 10 );
        System.out.println(elMio);
        elMio.setPermitidas(15);
        Disco elTuyo = new Disco( (short )2, "Descifrando enigma", 2015, 30 ); 
        String mio = elMio.muestraDisco("Se trata del disco el Mio");
        System.out.println(mio);
        System.out.println("Metodo toString() \t" + elMio);

        elTuyo.setActivas( elTuyo.getActivas()+ 5);
        System.out.println(elTuyo.daTransmision());
        System.out.println("elTuyo toString(): \t" + elTuyo);

    }
}
