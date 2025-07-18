/* TRABAJO REALIZADO POR:
 * - Nicolas Verdugo Barrera
 * - Diego Mercado Herrera
 * - Ariel Bobadilla Barriga
 */

package vista;

import exceptions.GrafoException;
import modelo.GrafoDirigidoPonderado;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.IllegalFormatException;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

// todo permitir editar los valores de las personas

public class VistaLoader {

    public Scanner sc = new Scanner(System.in);
    public GrafoDirigidoPonderado g = new GrafoDirigidoPonderado();
    boolean arranque = true;

    public void printMenu(){
        while (true) {
            if (arranque) { // El sistema no tiene nodos con los que operar. Ofrecer al usuario solo crear personas...
                int opcion = -1;

                System.out.print("""
                    ╔══════════════════════════════════════╗
                    ║          GESTOR DE AMISTADES         ║
                    ╠══════════════════════════════════════╣
                    ║      .:::: MENU PRINCIPAL ::::.      ║
                    ╠══════════════════════════════════════╣
                    ║  1. Insertar persona                 ║
                    ║  2. Generar datos de testeo          ║
                    ║  3. Salir                            ║
                    ╚══════════════════════════════════════╝
                    """);

                try {
                    opcion = aux_getInputInteger("");
                } catch (InputMismatchException e) {
                    System.out.println("[ERROR] Ingrese un numero válido");
                    sc.next();
                } catch (NumberFormatException e){
                    System.out.println(e.getMessage());
                } catch (GrafoException e) {
                    System.out.println(e.getMessage());
                }

                if (opcion != -1) {
                    if (opcion == 2) arranque = false;
                    switch (opcion) {
                        case 1 -> createPersona();
                        case 2 -> debugFunc();
                        case 3 -> {
                            System.out.println("Saliendo del programa...");
                            return;
                        }
                        default ->
                                System.out.println("[ERROR] Opcion seleccionada no válida, intente nuevamente.");
                    }
                }
            } else {
                int opcion = -1; // todo implementar punto 7
                System.out.print("""
                    ╔══════════════════════════════════════╗
                    ║          GESTOR DE AMISTADES         ║
                    ╠══════════════════════════════════════╣
                    ║      .:::: MENU PRINCIPAL ::::.      ║
                    ╠══════════════════════════════════════╣
                    ║  1. Insertar persona                 ║
                    ║  2. Agregar relación de amistad      ║
                    ║  3. Bloquear a un amigo              ║
                    ║  4. Cumpleaños próximos              ║
                    ║  5. Nivel de amistad entre amigos    ║
                    ║  6. Visualizar sistema               ║
                    ║  7. Editar datos                     ║
                    ║  8. Salir                            ║
                    ╚══════════════════════════════════════╝
                    """);

                try {
                    opcion = aux_getInputInteger("");
                } catch (InputMismatchException e) {
                    System.out.println("[ERROR] Por favor, ingrese un número válido.");
                    sc.next();
                } catch (NumberFormatException e){
                    System.out.println(e.getMessage());
                } catch (GrafoException e) {
                    System.out.println(e.getMessage());
                }

                if (opcion != -1) {
                    switch (opcion) {
                        case 1 -> createPersona();
                        case 2 -> linkFriendship();
                        case 3 -> blockFriendship();
                        case 4 -> findBirthdayInNextNDays();
                        case 5 -> obtainFriendshipLevel();
                        case 6 -> visualizer();
                        case 7 -> editor();
                        case 8 -> {
                            System.out.println("Saliendo del programa...");
                            return;
                        }
                        default ->
                                System.out.println("[ERROR] Opcion seleccionada no válida, intente nuevamente.");
                    }
                }
            }
        }
    }

    private void createPersona() {
        try {
            System.out.print("""                            
                    ╔══════════════════════════════════════╗
                    ║         CREAR NUEVA PERSONA          ║
                    ╚══════════════════════════════════════╝
                    """);
            System.out.println("Registrar a una persona en el sistema. Ingrese '-1' si desea cancelar la operación");
            String nombre = getInputCancelableString("Nombre: ");
            if (nombre == null) return;
            LocalDate nac = aux_getDate(1);
            // todo si nac esta mal, se debe manejar la situación
            String profesion = getInputCancelableString("Profesión: ");
            if (profesion == null) return;
            String email = aux_getEmail();
            if (email.equals("-1")) return;
            g.func_createPersona(nombre, nac, profesion, email);
            arranque = false;
        } catch (GrafoException e) {
            System.out.println(e.getMessage());
        }
    }

    private void linkFriendship() {
        try {
            System.out.print("""                            
                    ╔═══════════════════════════════════════╗
                    ║       AGREGAR RELACIÓN DE AMISTAD     ║
                    ╚═══════════════════════════════════════╝
                    """);
            System.out.println("Dadas dos personas se establece una amistad entre ellas. Ingrese '-1' si desea cancelar la operación");
            int id1 = aux_getIdPersona();
            if (id1 == -1) return;
            int id2 = aux_getIdPersona();
            if (id2 == -1) return;
            String fecha = aux_getDate(0).format(DateTimeFormatter.ofPattern("ddMMyyyy"));
            if (fecha.equals("-1")) return;
            g.func_friendsPersona(id1, id2, fecha);
        } catch (GrafoException e) {
            System.out.println(e.getMessage());
        }
    }


    private void blockFriendship() {
        try {
            System.out.print("""                            
                    ╔══════════════════════════════════════╗
                    ║          BLOQUEAR AMIGO              ║
                    ╚══════════════════════════════════════╝
                    """);
            System.out.println("Dadas dos personas el primero bloquea al segundo. Ingrese '-1' si desea cancelar la operación");
            int id1 = aux_getIdPersona();
            if (id1 == -1) return;
            int id2 = aux_getIdPersona();
            if (id2 == -1) return;
            String fecha = aux_getDate(0).format(DateTimeFormatter.ofPattern("ddMMyyyy"));
            if (fecha.equals("-1")) return;
            g.func_blockFriend(id1, id2, fecha);
        } catch (GrafoException e) {
            System.out.println(e.getMessage());
        }
    }

    private void findBirthdayInNextNDays() {
        try {
            System.out.print("""                            
                    ╔══════════════════════════════════════╗
                    ║      BUSCAR CUMPLEAÑOS PRÓXIMOS      ║
                    ╚══════════════════════════════════════╝
                    """);
            System.out.println("Obtener los próximos cumpleaños dentro de k días. Ingrese '-1' si desea cancelar la operación");
            LocalDate fechaInicio = aux_getDate(0);
            int n = getInputCancelableInt("Rango de días: ");
            if (n == -1) return;
            g.func_birthdayDayFind(n, fechaInicio);
        } catch (GrafoException e) {
            System.out.println(e.getMessage());
        }
    }

    private void obtainFriendshipLevel() {
        try {
            System.out.print("""                            
                    ╔══════════════════════════════════════╗
                    ║       OBTENER NIVEL DE AMISTAD       ║
                    ╚══════════════════════════════════════╝
                    """);
            System.out.println("Dadas dos personas obtener su nivel de amistad. Ingrese '-1' si desea cancelar la operación");
            int id1 = aux_getIdPersona();
            if (id1 == -1) return;
            int id2 = aux_getIdPersona();
            if (id2 == -1) return;
            System.out.println("Nivel de amistad: "+g.func_friendshipLevel(id1, id2));
        } catch (GrafoException e) {
            System.out.println(e.getMessage());
        }
    }

    private void visualizer(){
        g.debug_graphVisualizer();
    }

    private void editor(){
        System.out.println("De las siguientes opciones eliga una");
        System.out.print("""                            
                    ╔══════════════════════════════════════╗
                    ║       .:::: EDITAR DATOS ::::.       ║
                    ╠══════════════════════════════════════╣
                    ║           .- PERSONA -.              ║
                    ║  1. Nombre                           ║
                    ║  2. Fecha de nacimiento              ║
                    ║  3. Ocupación                        ║
                    ║  4. Correo electrónico               ║
                    ║          .- RELACIONES -.            ║
                    ║  5. Eliminar amistad                 ║
                    ║  6. Eliminar bloqueo                 ║
                    ║  7. Editar fecha de amistad          ║
                    ║  8. Editar fecha de bloqueo          ║
                    ║  9. Salir                            ║
                    ╚══════════════════════════════════════╝
                    """);
        int op = aux_getInputInteger("");
        switch (op) { // todo implementar las funciones
            case 1 -> edit_name();
            case 2 -> edit_nac();
            case 3 -> edit_ocup();
            case 4 -> edit_email();
            case 5 -> edit_deleteAmistad();
            case 6 -> edit_deleteBloqueo();
            case 7 -> edit_fechaAmistad();
            case 8 -> edit_fechaBloqueo();
            case 9 -> // salir
            default:
        }
        // Editar en Persona: nombre, dia, mes, ocupación, correo Amistad/Bloqueo: eliminar, cambiar fecha

    }

    // Métodos y funciones auxiliares
    private void debugFunc() {
        System.setProperty("org.graphstream.ui", "swing");
        System.out.print("""
                ╔══════════════════════════════════════╗
                ║            FUNCIONES DEBUG           ║
                ╚══════════════════════════════════════╝
                """);
        g.func_createPersona("Lucas", 9, 8, "Bombero", "luks09@gmail.com");
        g.func_createPersona("Lucas", 9, 8, "Bombero", "otroluks@gmail.com");

        g.func_createPersona("Ema", 1, 3, "Estudiante", "emmma3@gmail.com");
        g.func_createPersona("Carlos", 15, 12, "Doctor", "carlos.md@gmail.com");
        g.func_createPersona("Sofía", 27, 6, "Ingeniera", "sofia27@gmail.com");
        g.func_createPersona("Matías", 3, 10, "Profesor", "matias.profe@gmail.com");
        g.func_createPersona("Valentina", 20, 5, "Diseñadora", "valen.design@gmail.com");
        g.func_createPersona("Tomás", 8, 1, "Mecánico", "tomas.auto@gmail.com");
        g.func_createPersona("Isidora", 14, 2, "Periodista", "isidora.news@gmail.com");
        g.func_createPersona("Diego", 25, 11, "Panadero", "diego.pan@gmail.com");
        g.func_createPersona("Fernanda", 30, 4, "Veterinaria", "fernanda.vet@gmail.com");

        g.func_friendsPersona("Tomás", "Diego", "01012019");
        g.func_friendsPersona("Valentina", "Isidora", "14072020");
        g.func_friendsPersona("Matías", "Tomás", "03032021");
        g.func_friendsPersona("Isidora", "Matías", "23082022");
        g.func_friendsPersona("Carlos", "Matías", "10122022");
        g.func_friendsPersona("Lucas", "Carlos", "01052023");
        g.func_friendsPersona("Sofía", "Valentina", "05062023");
        g.func_friendsPersona("Lucas", "Fernanda", "01112023");
        g.func_friendsPersona("Lucas", "Ema", "28062025");

        g.func_blockFriend("Matías", "Tomás", "01072025");

        g.func_friendsPersona("Tomás", "Isidora", "14072025");

        g.getListaAdyacencia();
        g.debug_graphVisualizer();
    }

    private String getInputCancelableString(String prompt) {
        String input = aux_getInputString(prompt);
        if (input.equals("-1")) {
            System.out.println("Operación cancelada. Volviendo al menú principal...");
            return null;
        }
        return input;
    }

    private Integer getInputCancelableInt(String prompt) {
        int input = aux_getInputInteger(prompt);
        if (input == -1) {
            System.out.println("Operación cancelada. Volviendo al menú principal...");
            return -1;
        }
        return input;
    }

    private String aux_getEmail() {
        String email;
        int intentos = 0;

        do {
            if(intentos > 0) System.out.println("El correo ingresado ya está en el sistema. Ingrese uno distinto");
            email = aux_getInputString("Correo electrónico: ");
            intentos++;

            if (intentos > 2 || email.isEmpty()) {
                email = randomEmail();
                System.out.println("Lo ha intentado muchas veces. Se asignará "+email+" como correo");
                break;
            }
        } while (!g.verificarEmail(email));
        return email;
    }

    private String randomEmail() {
        String caracteres = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        int longitud = 10 + random.nextInt(6); // Genera un correo entre 10 a 15 caracteres

        for (int i = 0; i < longitud; i++) {
            sb.append(caracteres.charAt(random.nextInt(caracteres.length())));
        }
        return sb + "@gmail.com";
    }

    private String aux_getInputString(String texto) {
        final String GREEN = "\u001B[32m";
        final String RED   = "\u001B[31m";
        final String RESET = "\u001B[0m";

        System.out.print(GREEN + "> ");
        for (char c : texto.toCharArray()) {
            System.out.print(c);
            System.out.flush();
            try {
                Thread.sleep(30); // Ajusta la velocidad aquí
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.print(RESET);
        System.out.flush();

        String input = sc.nextLine().trim();

        if (input.isEmpty()) {
            System.out.println(RED + "[ERROR] Entrada vacía; digite una cadena válida." + RESET);
            return aux_getInputString(texto); // volver a pedir
        }

        return input;
    }

    private int aux_getInputInteger(String texto) {
        final String GREEN = "\u001B[32m";
        final String RED   = "\u001B[31m";
        final String RESET = "\u001B[0m";

        System.out.print(GREEN + "> ");
        for (char c : texto.toCharArray()) {
            System.out.print(c);
            System.out.flush();
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.print(RESET);
        System.out.flush();

        String input = sc.nextLine().trim();

        if (input.isEmpty()) {
            System.out.println(RED + "[ERROR] Entrada vacía; digite un número válido." + RESET);
            return aux_getInputInteger(texto);
        }

        if (input.contains(" ") || input.contains("\n")) {
            System.out.println(RED + "[ERROR] No se permiten espacios ni saltos de línea." + RESET);
            return aux_getInputInteger(texto);
        }

        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println(RED + "[ERROR] Entrada no válida; digite un número válido." + RESET);
            return aux_getInputInteger(texto);
        }
    }

    private LocalDate aux_getDate(int m) throws GrafoException {
        final String RED = "\u001B[31m";
        final String RESET = "\u001B[0m";

        while (true) {
            try {
                if (m == 0) {
                    String val = aux_getInputString("Desea usar la fecha actual como fecha de inicio [s / n]? ");

                    switch (val.toUpperCase()) {
                        case "S" -> {
                            return LocalDate.now();
                        }
                        case "N" -> {
                            int dia = aux_getInputInteger("Día: ");
                            int mes = aux_getInputInteger("Mes: ");
                            int anio = aux_getInputInteger("Año: ");

                            if (mes < 1 || mes > 12)
                                throw new GrafoException(RED + "[ERROR] El mes debe estar entre 1 y 12." + RESET);

                            if (dia < 1 || dia > 31)
                                throw new GrafoException(RED + "[ERROR] El día debe estar entre 1 y 31." + RESET);

                            return LocalDate.of(anio, mes, dia);
                        }
                        default -> {
                            System.out.println(RED + "[ERROR] Valor no válido ingresado. Por favor, ingrese 's' o 'n'." + RESET);
                        }
                    }
                } else {
                    String fechaStr = aux_getInputString("Fecha de nacimiento (formato ddMMaaaa): ");

                    if (!fechaStr.matches("\\d{8}"))
                        throw new GrafoException(RED + "[ERROR] El formato debe ser exactamente 8 dígitos: ddMMaaaa." + RESET);

                    int dia = Integer.parseInt(fechaStr.substring(0, 2));
                    int mes = Integer.parseInt(fechaStr.substring(2, 4));
                    int anio = Integer.parseInt(fechaStr.substring(4, 8));

                    return LocalDate.of(anio, mes, dia);
                }

            } catch (DateTimeException e) {
                System.out.println(RED + "[ERROR] Fecha inválida. Revisa si el día y mes existen realmente." + RESET);
            } catch (NumberFormatException e) {
                System.out.println(RED + "[ERROR] Entrada inválida. Debes ingresar solo números." + RESET);
            } catch (GrafoException e) {
                System.out.println(e.getMessage());
            }
        }
    }



    private int aux_getIdPersona(){
        String nombre = aux_getInputString("Nombre: ");
        int id = 0;
        String lista = g.getListaInstancias(nombre);
        if (!lista.isBlank()){
            // Pedir la instancia correcta
            System.out.println(lista);
            id = aux_getInputInteger("Id: ");
            System.out.println("Se ha seleccionado a "+g.getEmail(id)+" correctamente. Continúe con la operación");
        } else id = g.getId(nombre);
        return id;
    }
}