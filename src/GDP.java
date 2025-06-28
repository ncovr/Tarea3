// Grafo no dirigido ponderado

import java.util.Arrays;
import java.util.LinkedList;

public class GDP {
    class Arista {
        int id;
        String serial;

        public Arista(int id, String serial) {
            this.id = id;
            this.serial = serial;
        }

        public String toString() {
            return "(" + id + ", " + serial + ")";
        }
    }

    /**
     * Esta es la clase que representa un grafo dirigido ponderado, el cual funciona como un grafo común y corriente
     * pero al aplicar operaciones se interpretan los nodos como personas, usando 'personas' a modo de diccionario
     */

    private Persona[] personas = new Persona[5]; // Diccionario de personas
    private LinkedList<Arista>[] grafo = new LinkedList[5]; // Lista de adyacencia para representar el grafo
    private int idCounter = 1; // Ayuda a generar Id's en orden correlativo según se van creando nuevas personas

    /**
     * Por ejemplo, supongamos que existe una conexión entre '1' y '2', donde el peso (serial) finaliza en 0.
     * Esto significa, usando el diccionario de personas, que la persona con Id = 1 y persona con Id = 2 son amigos
     * en una fecha determinada
     *
     * El peso de la arista es una serial generada automáticamente según los parámetros otorgados al crear la persona.
     * La estructura de la serial sería que los primeros 8 carácteres representan la fecha tipo DíaMesAño y el noveno
     * carácter, representa la relación entre las personas, donde si es 0 indica amistad; si es 1 indica que las
     * personas se bloquearon
     */

    /**
     * Lo anterior, viéndolo a partir de la estructura del código: si tenemos grafo[1] = {(2, 010120190), (3, 010120231)}
     * significaria que la persona id = 1 se hizo amiga de persona id = 2 el 01012019 y que bloqueó a la persona id = 3 el
     * 01012023
     */

    /**
     * Dado que el grafo es dirigido, cuando se inicia una amistad, se crea una conexión bidireccional, pero cuando p1
     * bloquea a p2, se crea esa conexión unidireccional, y luego p2 ya no está conectado con p1
     */

    public GDP() {
    }

    public void crearPersona(String nombre, int dia, int mes, String ocupacion, String email) {
        Persona p = new Persona(idCounter, nombre, dia, mes, ocupacion, email);
        personas[idCounter] = p;
        idCounter++;
        if (idCounter >= grafo.length) {
            grafo = Arrays.copyOf(grafo, grafo.length * 2);
            personas = Arrays.copyOf(personas, personas.length * 2);
        }
    }

    /**
     * Para que p1 y p2 sean amigos, se debe establecer la relación en el grafo, creando una arista bidireccional con
     * peso, donde el peso es la serial (fecha más digito de estado: amistad/bloqueo)
     */
    public void amigar(int p1, int p2, String fecha) {
        fecha = fecha+"0";
    }

    /**
     * Establecer que p1 y p2 están bloqueados mutuamente (p1 bloqueó a p2 y p2 bloqueó a p1). Mi duda es, si es que
     * es un estado bidireccional o solo poner que p1 bloquea a p2 y que p2 ya no es amigo de p1
     */
    public void bloquear(int p1, int p2, String fecha) {
        fecha = fecha+"1";

    }

    /**
     * Encontrar las personas que estarán de cumpleaños dentro de los próximos k días. Enviar correo a sus amigos directos
     */
    public void cumple(int k) {

    }

    /**
     * Encontrar el 'na' entre p1 y p2
     */
    public void nivelDeAmistad(int p1, int p2) {

    }

    //  Métodos para testear
    public void getPersonas() {
        for (int i = 0; i < personas.length; i++) {
            if (personas[i] != null) System.out.println(personas[i]);
        }
    }
}
