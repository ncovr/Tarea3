// Grafo no dirigido ponderado

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.LinkedList;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

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

    private Persona[] personas = new Persona[2]; // Diccionario de personas
    private LinkedList<Arista>[] grafo = new LinkedList[2]; // Lista de adyacencia para representar el grafo
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
        if (idCounter >= grafo.length) {
            grafo = Arrays.copyOf(grafo, grafo.length + 1);
            personas = Arrays.copyOf(personas, personas.length + 1);

            for (int i = 0; i < grafo.length; i++) {
                if (grafo[i] == null) {
                    grafo[i] = new LinkedList<>();
                }
            }
        }
        personas[idCounter] = p;
        idCounter++;
    }

    /**
     * Para que p1 y p2 sean amigos, se debe establecer la relación en el grafo, creando una arista bidireccional con
     * peso, donde el peso es la serial (fecha más digito de estado: amistad/bloqueo)
     *
     * Corregir: es mejor que en vez de usar nombres se usen identificadores porque podriamos tener, por ejemplo,
     * a dos personas con el mismo nombre en el sistema, y esto podía afectar al programa ya que ambos nombres podrían
     * ser dos personas totalmente distintas
     */
    public void amigar(String ps1, String ps2, String fecha) {
        int p1 = getId(ps1);
        int p2 = getId(ps2);
        if (p1 == -1 || p2 == -1) return;
        fecha += "/0";

        // Relacionar
        grafo[p1].add(new Arista(p2, fecha));
        grafo[p2].add(new Arista(p1, fecha));

        // Enviar correos
        correos(p1, p2, fecha);
    }

    /**
     * Se debe enviar un correo a los vecinos de p1 diciendo que es posible que conozca a p2, y al revés
     */
    public void correos(int p1, int p2, String fecha) {
        // Para cada vecino, imprimir el mensaje
        System.out.println("¡Ahora "+getNombre(p1)+" y "+getNombre(p2)+" son amigos! ("+fecha+")");
        System.out.println("Correo para los amigos de " + personas[p1].nombre);
        for (Arista a : grafo[p1]) {
            if (a.id == p2) continue;
            Persona p = personas[a.id];
            System.out.println(
                    "Para: " + p.email + "\n" +
                            ">>> ¡Hola " + p.nombre + "!, es posible que conozcas a " + personas[p2].nombre
            );
        }

        System.out.println("Correo para los amigos de " + personas[p2].nombre);
        for (Arista a : grafo[p2]) {
            if (a.id == p1) continue;
            Persona p = personas[a.id];
            System.out.println(
                    "Para: " + p.email + "\n" +
                            "¡Hola " + p.nombre + "!, es posible que conozcas a " + personas[p1].nombre
            );
        }
        System.out.println("\n");
        // Se pordía pulir:
        // - Mostrar la fecha formateada
        // - No imprimir "Correo para los amigos de..." cuando no hayan amigos
    }

    /**
     * Establecer que p1 y p2 están bloqueados mutuamente (p1 bloqueó a p2 y p2 bloqueó a p1). Mi duda es, si es que
     * es un estado bidireccional o solo poner que p1 bloquea a p2 y que p2 ya no es amigo de p1
     *
     * Consideraremos que si p1 bloquea a p2, se establece una relacion unidireccinal donde queda registro de que
     * p1 bloqueo a p2 y p2 deja de ser amigo de p1
     */
    public void bloquear(String ps1, String ps2, String fecha) {
        int p1 = getId(ps1);
        int p2 = getId(ps2);
        if (p1 == -1 || p2 == -1) return;

        fecha = fecha + "/1";

        // Eliminar la amistad (si existe) en ambos sentidos
        grafo[p1].removeIf(a -> a.id == p2);
        grafo[p2].removeIf(a -> a.id == p1);

        // Establecer el bloqueo desde p1 hacia p2
        grafo[p1].add(new Arista(p2, fecha));

        // Se podría pulir:
        // - Mostrar en consola el bloqueo con fecha y nombres
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
        if (!(exists(p1) && exists(p2))) return;

    }

    public void visualizar() {
        Graph g = new SingleGraph("Grafo de Personas");
        g.setStrict(false);
        g.setAutoCreate(true);

        g.setAttribute("ui.stylesheet",
                "node {"
                        + "   fill-color: blue;"
                        + "   size: 60px, 60px;"
                        + "   text-alignment: center;"
                        + "   text-size: 15;"
                        + "   text-color: white;"
                        + "   shape: circle;"
                        + "}"
                        + "edge {"
                        + "   fill-color: gray;"
                        + "   arrow-shape: arrow;"
                        + "   arrow-size: 15px, 10px;"
                        + "   text-size: 10;"
                        + "   text-color: black;"
                        + "}"
        );

        // Agregar nodos
        for (int i = 1; i < personas.length; i++) {
            if (personas[i] != null) {
                Node n = g.addNode(String.valueOf(i));
                n.setAttribute("ui.label", personas[i].nombre);
            }
        }

        // Agregar aristas
        for (int i = 1; i < grafo.length; i++) {
            if (grafo[i] == null) continue;
            for (Arista a : grafo[i]) {
                String id = i + "-" + a.id;
                String reverseId = a.id + "-" + i;

                boolean esAmistad = a.serial.endsWith("0");
                boolean yaExiste = g.getEdge(id) != null || g.getEdge(reverseId) != null;

                if (esAmistad) {
                    // Para amistad, solo agregar una arista no dirigida si aún no existe
                    if (!yaExiste)
                        g.addEdge(id, String.valueOf(i), String.valueOf(a.id), false)
                                .setAttribute("ui.label", a.serial);
                } else {
                    // Para bloqueo, agregar arista dirigida desde i hacia a.id
                    g.addEdge(id, String.valueOf(i), String.valueOf(a.id), true)
                            .setAttribute("ui.label", a.serial);
                }
            }
        }

        g.display();
    }


    // Funciones privadas ----------------------------------------------------------------------------------------------
    private boolean exists(int p) { // Verifica si la persona existe en el sistema
        if (p <= 0 || p >= personas.length) return false;
        for (int i = 1; i < personas.length; i++) {
            if (personas[i] != null && personas[i].id == p) return true;
        }
        return false;
    }

    // Dado un nombre, buscar el índice en la que se encuentra la persona en 'personas'
    private int getId(String p) {
        for (int i = 0; i < personas.length; i++) {
            if (personas[i] != null && personas[i].nombre.equals(p)) return i;
        }
        return -1;
    }

    private String getNombre(int id){
        for (int i = 0; i < personas.length; i++) {
            if (personas[i] != null && personas[i].id == id) return personas[i].nombre;
        }
        return null;
    }

    //  Métodos para testear -------------------------------------------------------------------------------------------
    public void getPersonas() {
        for (int i = 0; i < personas.length; i++) {
            if (personas[i] != null) System.out.println(personas[i]);
        }
    }

    // Para visualizar la lista de adyacencia
    public void getListaAdyacencia(){
        System.out.println("Lista de Adyacencia");
        for (int i = 1; i < grafo.length; i++) {
            System.out.print(personas[i]+": ");
            for (Arista a : grafo[i]) {
                System.out.print(personas[a.id]+"  ");
            }
            System.out.println();
        }
    }
}
