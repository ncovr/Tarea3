package modelo;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import exceptions.GrafoException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedList;

public class GrafoDirigidoPonderado {
    private Persona[] personas = new Persona[2]; // Diccionario de personas
    private LinkedList<Arista>[] grafo = new LinkedList[2]; // Lista de adyacencia para representar el grafo
    private int idCounter = 1; // Ayuda a generar Id's en orden correlativo según se van creando nuevas personas

    /**
     * Dado que el grafo es dirigido, cuando se inicia una amistad se crea una conexión bidireccional, pero cuando p1
     * bloquea a p2, se crea esa conexión unidireccionalmente; luego p2 ya no está conectado con p1 pero se registra
     * el bloqueo efectivamente
     */

    public GrafoDirigidoPonderado() {
    }

    /*
     * Por ejemplo, supongamos que existe una conexión entre '1' y '2', donde el peso (en forma serial) finaliza en 0.
     * Esto significa, usando el diccionario de personas, que la persona con Id = 1 y persona con Id = 2 son amigos
     * en una fecha determinada.
     * El peso de la arista es una serial generada automáticamente según los parámetros otorgados al crear la persona.
     * La estructura de la serial sería que los primeros 8 carácteres representan la fecha tipo DíaMesAño y el noveno
     * carácter, representa la relación entre las personas, donde si es 0 indica amistad; si es 1 indica que las
     * personas se bloquearon
     */
    /*
     * Lo anterior, viéndolo a partir de la estructura del código: si tenemos grafo[1] = {(2, 010120190), (3, 010120231)}
     * significaria que la persona id = 1 se hizo amiga de persona id = 2 el 01012019 y que bloqueó a la persona id = 3 el
     * 01012023
     */

    public void func_createPersona(String nombre, int dia, int mes, String ocupacion, String email) {
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
        System.out.println(".: "+nombre+ " creado exitosamente");
    }

    public void func_createPersona(String nombre, LocalDate nacimiento, String ocupacion, String email) {
        int dia = nacimiento.getDayOfMonth();
        int mes = nacimiento.getMonthValue();
        func_createPersona(nombre, dia, mes, ocupacion, email);
    }

    /**
     * Para que p1 y p2 sean amigos, se debe establecer la relación en el grafo, creando una arista bidireccional con
     * peso, donde el peso es la serial (fecha más digito de estado: amistad/bloqueo)
     * <p>
     * Corregir: es mejor que en vez de usar nombres se usen identificadores porque podriamos tener, por ejemplo,
     * a dos personas con el mismo nombre en el sistema, y esto podía afectar al programa ya que ambos nombres podrían
     * ser dos personas totalmente distintas
     */

    public void func_friendsPersona(String ps1, String ps2, String fecha) throws GrafoException {
        int p1 = getId(ps1);
        if (p1 == -1) throw new GrafoException("No existe \"" + ps1 + "\" en el sistema");
        int p2 = getId(ps2);
        if (p2 == -1) throw new GrafoException("No existe \"" + ps2 + "\" en el sistema");
        fecha += "-0";

        // Relacionar
        grafo[p1].add(new Arista(p2, fecha));
        grafo[p2].add(new Arista(p1, fecha));

        // Enviar correos
        func_sendEmail(p1, p2, fecha);
    }

    /**Para evitar codigo que revise si existen las personas (id), debemos asesorarnos de que la función de por sí reciba
     * valores registrados. Esto se hace averiguando si existe la persona justo despues de ingresar el nombre en la op.*/
    public void func_friendsPersona(int id1, int id2, String date) throws GrafoException {
        date += "-0";
        grafo[id1].add(new Arista(id2, date));
        grafo[id2].add(new Arista(id1, date));
        sucessMessage();
        func_sendEmail(id1, id2, date);
    }

    /**
     * Se debe enviar un correo a los vecinos de p1 diciendo que es posible que conozca a p2, y al revés
     */
    private void func_sendEmail(int p1, int p2, String fecha) {
        String[] fechaf = fecha.split("-");
        fecha = serialToDateFormated(fechaf[0], 2);
        System.out.println(".: Ahora " + getNombre(p1) + " y " + getNombre(p2) + " son amigos (" + fecha + ")");

        Persona pp = personas[p1];
        int correosP1 = (pp != null) ? grafo[p1].size() - 1 : 0; // excluye p2
        if (pp != null && correosP1 > 0) {
            String msgCorreos = correosP1 == 1 ? "correo" : "correos";
            String msgAmigos = correosP1 == 1 ? "el amigo" : "amigos";
            System.out.println("(" + correosP1 + ") " + msgCorreos + " para " + msgAmigos + " de " + pp.nombre);
            for (Arista a : grafo[p1]) {
                if (a.id == p2) continue;
                Persona p = personas[a.id];
                System.out.println(
                        "Para: " + p.email + "\n" +
                                "¡Hola " + p.nombre + "!, es posible que conozcas a " + personas[p2].nombre
                );
            }
        }

        pp = personas[p2];
        int correosP2 = (pp != null) ? grafo[p2].size() - 1 : 0; // excluye p1
        if (pp != null && correosP2 > 0) {
            String msgCorreos = correosP2 == 1 ? "correo" : "correos";
            String msgAmigos = correosP2 == 1 ? "el amigo" : "los amigos";
            System.out.println("(" + correosP2 + ") " + msgCorreos + " para " + msgAmigos + " de " + pp.nombre);
            for (Arista a : grafo[p2]) {
                if (a.id == p1) continue;
                Persona p = personas[a.id];
                System.out.println(
                        "Para: " + p.email + "\n" +
                                "¡Hola " + p.nombre + "!, es posible que conozcas a " + personas[p1].nombre
                );
            }
        }
        System.out.println();
    }


    /**
     * Establecer que p1 y p2 están bloqueados mutuamente (p1 bloqueó a p2 y p2 bloqueó a p1). Mi duda es, si es que
     * es un estado bidireccional o solo poner que p1 bloquea a p2 y que p2 ya no es amigo de p1
     * <p>
     * Consideraremos que si p1 bloquea a p2, se establece una relacion unidireccinal donde queda registro de que
     * p1 bloqueo a p2 y p2 deja de ser amigo de p1
     */

    public void func_blockFriend(String ps1, String ps2, String fecha) {
        exists(ps1);
        exists(ps2);
        int p1 = getId(ps1);
        int p2 = getId(ps2);

        fecha = fecha + "-1";

        // Eliminar la amistad (si existe) en ambos sentidos
        grafo[p1].removeIf(a -> a.id == p2);
        grafo[p2].removeIf(a -> a.id == p1);

        // Establecer el bloqueo desde p1 hacia p2
        grafo[p1].add(new Arista(p2, fecha));

        System.out.println(".: " + ps1 + " ha bloqueado a " + ps2 + " exitosamente");
    }

    public void func_blockFriend(int id1, int id2, String fecha) {
        exists(id1);
        exists(id2);
        fecha = fecha + "-1";

        // Eliminamos la amistad (si existe) en ambos sentidos
        grafo[id1].removeIf(a -> a.id == id2);
        grafo[id2].removeIf(a -> a.id == id1);

        // Establecemos que p1 bloquea a p2
        grafo[id1].add(new Arista(id2, fecha));
        System.out.println("¡" + getNombre(id1) + " ha bloqueado a " + getNombre(id2) + " con éxito!");
    }

    /**
     * Encontrar las personas que estarán de cumpleaños dentro de los próximos k días. Enviar correo a sus amigos directos
     */

    public void func_birthdayDayFind(int k, LocalDate day) throws GrafoException {
        if (k <= 0 || day == null) throw new GrafoException("Ingrese datos válidos");
        if (personas.length == 0) throw new GrafoException("No existen registros de personas en el sistema");

        LocalDate endDate = day.plusDays(k); // pesca el dia elegido y le suma k dias para almacenar en una variable la fecha tope

        System.out.println(".:: Personas con cumpleaños en los próximos " + k + " días ::.");
        int c = 0; // Para retornar la cantidad de cumpleaños próximos
        for (Persona persona : personas) { // Recorremos a todas las personas del sistema preguntando por sus cumpleaños
            if (persona == null) continue;
            c++;
            LocalDate birthday = LocalDate.of(day.getYear(), persona.mes, persona.dia); // Obtenemos la fecha de cumpleaños de la persona
            if (birthday.isBefore(day)) {
                birthday = birthday.plusYears(1); // Si el cumpleaños ya pasó este año, lo buscamos para el próximo año, resuelve el problema en diciembre - enero
            }

            if (!birthday.isAfter(endDate)) { // Si el cumpleaños está antes de la fecha límite
                System.out.println(".: " + persona.getNombreYCorreo() + ", el " + serialToDateFormated(persona.getSerial(), 1)); // Imprimimos que la persona está x día

                // Enviar correos a amigos directos
                if (grafo[persona.id] == null || grafo[persona.id].isEmpty()) {
                    System.out.println("Sin amigos a los que avisar cumpleaños; sin hay correos por enviar");
                } else {
                    for (Arista a : grafo[persona.id]) {
                        Persona amigo = personas[a.id];
                        System.out.println("Correo a " + amigo.email + ": ¡Hola " + amigo.nombre + "! " +
                                persona.nombre + " esta de cumpleaños " + persona.getCuentaRegresiva() + "!");
                    }
                }
            }
        }
        System.out.println("Cumpleaños próximos en total: " + c);
        sucessMessage();
    }

    /**
     * Encontrar el 'na' entre p1 y p2
     */

    public String func_friendshipLevel(int p1, int p2) {
        if (p1 == p2) throw new GrafoException("No ingrese dos veces la misma persona");
        exists(p1);
        exists(p2);
        String ps1 = getNombre(p1);
        String ps2 = getNombre(p2);

        // Verificar bloqueo directo
        for (Arista a : grafo[p1]) {
            if (a.id == p2 && a.serial.endsWith("1")) {
                ;
                return "Nivel no disponible porque " + ps1 + " bloqueó a " + ps2
                        + " el " + serialToDateFormated(a.serial, 0);
            }
        }
        for (Arista a : grafo[p2]) {
            if (a.id == p1 && a.serial.endsWith("1")) {
                return "Nivel no disponible porque  " + ps2 + " fué bloqueado por " + ps1
                        + " el " + serialToDateFormated(a.serial, 0);
            }
        }

        // BFS  pero se ignoran todas las aristas bloqueadas
        boolean[] visitado = new boolean[personas.length];
        int[] distancia = new int[personas.length];
        Arrays.fill(distancia, Integer.MAX_VALUE);

        LinkedList<Integer> queue = new LinkedList<>();
        queue.add(p1);
        visitado[p1] = true;
        distancia[p1] = 0;

        while (!queue.isEmpty()) {
            int actual = queue.poll();
            for (Arista a : grafo[actual]) {
                // Ignorar aristas bloqueadas o que no sean de amistad
                if (!a.serial.endsWith("0") || a.serial.endsWith("1")) {
                    continue;
                }
                int vecino = a.id;
                if (!visitado[vecino]) {
                    visitado[vecino] = true;
                    distancia[vecino] = distancia[actual] + 1;
                    queue.add(vecino);
                    // terminar si encontramos p2
                    if (vecino == p2) {
                        queue.clear();
                        break;
                    }
                }
            }
        }
        if (distancia[p2] == Integer.MAX_VALUE) {
            return "infinito (no hay conexión entre ellos)";
        } else {
            return distancia[p2] + "";
        }
    }


    public void debug_graphVisualizer() throws GrafoException {
        if (personas[1] == null) {
            System.out.println("No hay datos para visualizar el grafo...");
            return;
        }
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
                n.setAttribute("ui.label", personas[i].getNombreYid());
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

        System.setProperty("org.graphstream.ui", "swing");
        g.display().setCloseFramePolicy(org.graphstream.ui.view.Viewer.CloseFramePolicy.HIDE_ONLY);
    }

    // ----------------------- FUNCIONES AUXILIARES PARA AYUDAR CON LA EJECUCIÓN DE LOS MÉTODOS ------------------------

    public void exists(int p) throws GrafoException { // Verifica si la persona (id) existe en el sistema
        if (p <= 0 || p >= personas.length)
            throw new GrafoException("No hay registro de \"" + getNombre(p) + "\"  en el sistema");
        boolean b = false;
        for (int i = 1; i < personas.length; i++) {
            if (personas[i] != null && personas[i].id == p) b = true;
        }
        if (!b) throw new GrafoException("No hay registro de \"" + getNombre(p) + "\"  en el sistema");
    }

    private void sucessMessage() {
        System.out.println("Suceso terminado con éxito");
    }

    public void exists(String ps) throws GrafoException { // Verifica si la persona (nombre) existe en el sistema
        int p = getId(ps);
        if (p <= 0 || p >= personas.length) throw new GrafoException("No hay registro de \"" + ps + "\" en el sistema");
        boolean b = false;
        for (int i = 1; i < personas.length; i++) {
            if (personas[i] != null && personas[i].id == p) b = true;
        }
        if (!b) throw new GrafoException("\"" + ps + "\" no encontrad@ en el sistema");
    }

    public int getId(String p) { // Dado un nombre: buscar el índice en la que se encuentra la persona en 'personas'
        for (int i = 0; i < personas.length; i++) {
            if (personas[i] != null && personas[i].nombre.equals(p)) return i;
        }
        return -1;
    }

    public String getNombre(int id) { // Dado un id: retornar el nombre de la persona
        return personas[id] + "";
    }

    public String getEmail(int id) {
        for (Persona p : personas) {
            if (p != null && p.id == id) return p.email;
        }
        return "sin email";
    }

    public String serialToDateFormated(String serial, int mode) { // Transfoma una serie ddMMYYYY en una fecha escrita
        int dia = Integer.parseInt(serial.substring(0, 2));
        int mes = Integer.parseInt(serial.substring(2, 4));
        int anio = Integer.parseInt(serial.substring(4, 8));
        String exit = "";

        switch (mode) {
            case 0: // Fecha con año
                exit = dia + " de " + Mes.fromNumero(mes) + " de " + anio;
                break;
            case 1: // Fecha sin año
                exit = dia + " de " + Mes.fromNumero(mes);
                break;
            case 2: // Mostrar año solo si es distinto al año actual
                int anioActual = java.time.LocalDate.now().getYear();
                if (anio == anioActual) {
                    exit = dia + " de " + Mes.fromNumero(mes);
                } else {
                    exit = dia + " de " + Mes.fromNumero(mes) + " de " + anio;
                }
                break;
        }
        return exit;
    }

    public boolean verificarEmail(String email) throws GrafoException { // Retorna true si no existe el email en el sistema
        boolean e = true;
        for (Persona persona : personas) {
            if (persona != null && persona.email.equals(email)) e = false;
        }
        return e;
    }

    public String getListaInstancias(String nombre) { // Dado un nombre: retorna una lista de personasque comparten el nombre
        exists(nombre);

        // Si hay más de una persona en el sistema, se retorna una lista con todas esas coincidencias
        StringBuilder s = new StringBuilder();
        s.append("Se ha encontrado a más de un(a) \"" + nombre + "\" en el sistema. Seleccione el Id de la persona que desea elegir para la operación\n");
        s.append("id\t\tnombre\temail");
        for (Persona persona : personas) {
            if (persona != null && persona.nombre.equals(nombre)) {
                s.append("\n" + persona.get());
            }
        }
        String[] ps = s.toString().split("\n");
        if (ps.length > 3) return s.toString();
        return " ";
    }

    //  Métodos para testear -------------------------------------------------------------------------------------------
    public void getPersonas() {
        for (Persona persona : personas) {
            if (persona != null) System.out.println(persona);
        }
    }

    // Para visualizar la lista de adyacencia
    public void getListaAdyacencia() {
        System.out.println("Lista de Adyacencia");
        for (int i = 1; i < grafo.length; i++) {
            System.out.print(personas[i] + ": ");
            for (Arista a : grafo[i]) {
                System.out.print(personas[a.id] + "  ");
            }
            System.out.println();
        }
    }
}
