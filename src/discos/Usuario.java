package discos;

public class Usuario {
    public static void main(String[] args) {
        String saludo = "Bienvenido al sistema de streaming";
        System.out.println(saludo);

        
        Catalogo miCatalogo =   new Catalogo(6, new Disco []{
            new Disco( (short )2, "Descifrando enigma", 2015, 10 ),
            new Disco( (short )1, "Ahora lo ves ahora no", 2014, 10 ),
            new Disco( (short )2, "Billions", 2015, 1 ),
            new Disco( (short )1, "Frank sinatra", 2021, 6 )
        }); 
        miCatalogo.conectaCatalogo();
        
    }
}
