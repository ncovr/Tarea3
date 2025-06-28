import java.io.Console;

public class Main {
    public static void main(String[] args) {
        System.setProperty("org.graphstream.ui", "swing");

        /**Cuidado, porque Matias no es lo mismo que Matías*/
        GDP g = new GDP();
        g.crearPersona("Lucas", 9, 8, "Bombero", "luks09@gmail.com");
        g.crearPersona("Ema", 1, 3, "Estudiante", "emmma3@gmail.com");
        g.crearPersona("Carlos", 15, 12, "Doctor", "carlos.md@gmail.com");
        g.crearPersona("Sofía", 27, 6, "Ingeniera", "sofia27@gmail.com");
        g.crearPersona("Matías", 3, 10, "Profesor", "matias.profe@gmail.com");
        g.crearPersona("Valentina", 20, 5, "Diseñadora", "valen.design@gmail.com");
        g.crearPersona("Tomás", 8, 1, "Mecánico", "tomas.auto@gmail.com");
        g.crearPersona("Isidora", 14, 2, "Periodista", "isidora.news@gmail.com");
        g.crearPersona("Diego", 25, 11, "Panadero", "diego.pan@gmail.com");
        g.crearPersona("Fernanda", 30, 4, "Veterinaria", "fernanda.vet@gmail.com");

        g.amigar("Tomás", "Diego", "01012019");
        g.amigar("Valentina", "Isidora", "14072020");
        g.amigar("Matías", "Tomás", "03032021");
        g.amigar("Isidora", "Matías", "23082022");
        g.amigar("Carlos", "Matías", "10122022");
        g.amigar("Lucas", "Carlos", "01052023");
        g.amigar("Sofía", "Valentina", "05062023");
        g.amigar("Lucas", "Fernanda", "01112023");
        g.amigar("Lucas", "Ema", "28062025");

        g.bloquear("Matías", "Tomás", "01072025");

        g.amigar("Tomás", "Isidora", "14072025");

        g.getListaAdyacencia();
        g.visualizar();
    }
}
