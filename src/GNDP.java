// Grafo no dirigido ponderado

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class GNDP {
    class Arista {
        int id;
        String serial;

        public Arista(int id, String serial) {
            this.id = id;
            this.serial = serial;
        }

        public String toString() {
            return "("+id+", "+serial+")";
        }
    }

    /**
     * Esta es la clase que representa el grafo no dirigido; ponderado, el cual funciona como un grafo común y corriente,
     * pero para operaciones en donde debemos interpretar los nodos como personas, se utiliza 'personas' a modo de diccionario
     */

    private Persona[] personas = new Persona[5]; // Diccionario de personas
    private LinkedList<Arista>[] grafo = new LinkedList[5]; // Lista de adyacencia para representar el grafo
    private int idCounter = 1; // Ayuda a generar Id's en orden correlativo según se van creando nuevas personas

    /**
     * Por ejemplo, en el grafo existe una conexión entre '1' y '2'. Esto significa, usando el diccionario de personas,
     * que la persona con Id = 1 se relaciona con la persona con Id = 2. El peso de la arista es una serial generada
     * automáticamente según los parámetros otorgados al crear la persona. La estructura de la serial sería que los primeros
     * 8 carácteres representan la fecha tipo DíaMesAño y el noveno carácter, representa la relación entre las personas,
     * donde si es 0 indica amistad; si es 1 indica que las personas se bloquearon
     */

    /**
     * Lo anterior, viéndolo a partir de la estructura en código: si tenemos grafo[1] = {(2, 010120190), (3, 010120231)}
     * significaria que la persona id = 1 se hizo amiga de persona id = 2 el 01012019 y bloqueó a la persona id = 3 el
     * 01012023
     */

    public GNDP() {}

    public void insertarPersona(String nombre, int dia, int mes, String ocupacion, String email) {
        Persona p = new Persona(idCounter, nombre, dia, mes, ocupacion, email);
        personas[idCounter] = p;
        idCounter++;
        if (idCounter >= grafo.length) {
            grafo = Arrays.copyOf(grafo, grafo.length * 2);
            personas = Arrays.copyOf(personas, personas.length * 2);
        }
    }


    //  Métodos para testear
    public void getPersonas() {
        for (int i = 0; i < personas.length; i++) {
            if (personas[i] != null) System.out.println(personas[i]);
        }
    }
}
