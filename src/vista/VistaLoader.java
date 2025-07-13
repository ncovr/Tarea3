package vista;

import exceptions.GrafoException;
import modelo.GrafoDirigidoPonderado;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.IllegalFormatException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class VistaLoader {

    public Scanner sc = new Scanner(System.in);
    public GrafoDirigidoPonderado g = new GrafoDirigidoPonderado();

    public void menu() {
        while (true) {
            int opcion = -1;

            System.out.print("""
                    ╔══════════════════════════════════════╗
                    ║         GESTIÓN DE AMISTADES         ║
                    ╠══════════════════════════════════════╣
                    ║      .:::: MENU PRINCIPAL ::::.      ║
                    ╠══════════════════════════════════════╣
                    ║  1. Insertar persona                 ║
                    ║  2. Agregar una relación de amistad  ║
                    ║  3. Bloquear a un amigo              ║
                    ║  4. Encontrar cumpleaños próximos    ║
                    ║  5. Nivel de amistad entre amigos    ║
                    ║  6. Ejecutar funciones debug         ║
                    ║  7. Salir                            ║
                    ╚══════════════════════════════════════╝
                    """);
            System.out.print("Ingrese una opción: ");

            try {
                opcion = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("[ERROR] Por favor, ingrese un número válido.");
                sc.next();
            }

            if (opcion != -1) {
                switch (opcion) {
                    case 1 -> createPersona();
                    case 2 -> linkFriendship();
                    case 3 -> blockFriendship();
                    case 4 -> findBirthdayInNextNDays();
                    case 5 -> obtainFriendshipLevel();
                    case 6 -> debugFunc();
                    case 7 -> {
                        System.out.println("Saliendo del programa...");
                        return;
                    }
                    default ->
                            System.out.println("[ERROR] Opcion seleccionada no válida, por favor intente nuevamente.");
                }
            }
        } // Fin del bucle while
    }

    private void createPersona() {
        try {
            System.out.print("""                            
                    ╔══════════════════════════════════════╗
                    ║         CREAR NUEVA PERSONA          ║
                    ╚══════════════════════════════════════╝
                    """);
            String nombre = aux_getInputString("Nombre de la persona? ");
            int diaCumple = aux_getInputInteger("Día de cumpleaños? ");
            int mesCumple = aux_getInputInteger("Mes de cumpleaños? ");
            String profesion = aux_getInputString("Profesión? ");
            String email = aux_getInputString("Email? ");

            g.func_createPersona(nombre, diaCumple, mesCumple, profesion, email);

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
            String nombre1 = aux_getInputString("Nombre de la primera persona? ");
            String nombre2 = aux_getInputString("Nombre de la segunda persona? ");
            String fecha = aux_getInputString("Fecha de inicio de amistad (DDMMYYYY)? "); //todo Arreglar entrada del formato de fecha
            g.func_friendsPersona(nombre1, nombre2, fecha);
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
            String nombre1 = aux_getInputString("Nombre de la primera persona? ");
            String nombre2 = aux_getInputString("Nombre de la segunda persona? ");
            String fecha = aux_getInputString("Fecha de termino de la amistad (DDMMYYYY)? "); //todo Arreglar entrada del formato de fecha
            g.func_blockFriend(nombre1, nombre2, fecha);
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
            LocalDate fechaInicio = aux_getDate();
            int n = aux_getInputInteger("Dentro de cuantos dias? ");
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
            String p1 = aux_getInputString("Ingrese el nombre de la primera persona? ");
            String p2 = aux_getInputString("Ingrese el nombre de la segunda persona? ");
            g.func_friendshipLevel(p1, p2);
        } catch (GrafoException e) {
            System.out.println(e.getMessage());
        }
    }

    // CLASES AUXILIARES, CREADAS PARA AGILIZAR EL CÓDIGO PRINCIPAL

    private void debugFunc() {
        System.setProperty("org.graphstream.ui", "swing");
        System.out.println("""
                ╔══════════════════════════════════════╗
                ║             FUNCIONES DEBUG          ║
                ╚══════════════════════════════════════╝
                """);
        // Cuidado, porque Matias no es lo mismo que Matías //todo Revisar casos de acentos y caracteres especiales
        g.func_createPersona("Lucas", 9, 8, "Bombero", "luks09@gmail.com");
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

    private String aux_getInputString(String texto) {
        System.out.print(texto);
        return sc.next();
    }

    private int aux_getInputInteger(String texto) throws GrafoException {
        System.out.print(texto);

        try {
            return Integer.parseInt(sc.next());
        } catch (NumberFormatException e) {
            throw new GrafoException("[ERROR] Entrada inválida, por favor ingrese un número entero.");
        }
    }

    private LocalDate aux_getDate() throws GrafoException {
        while (true) {
            String val = aux_getInputString("Desea usar la fecha actual como fecha de inicio [SI / NO]? ");

            switch (val.toUpperCase()) {
                case "SI" -> {
                    return LocalDate.now();
                }
                case "NO" -> {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                    try {
                        int dia = aux_getInputInteger("Ingrese día? ");
                        int mes = aux_getInputInteger("Ingrese mes? ");
                        int anio = aux_getInputInteger("Ingrese año? ");
                        String fechaStr = String.format("%02d/%02d/%04d", dia, mes, anio);
                        return LocalDate.parse(fechaStr, formatter);

                    } catch (GrafoException e) {
                        throw new GrafoException(e.getMessage());
                    } catch (IllegalFormatException e) {
                        throw new GrafoException("[ERROR] Formato de fecha inválido. Por favor, use el formato DD/MM/YYYY.");
                    } catch (InputMismatchException e) {
                        throw new GrafoException("[ERROR] Entrada inválida. Por favor, ingrese números enteros para día, mes y año.");
                    } catch (DateTimeParseException e) {
                        throw new GrafoException("[ERROR] Error serio al retornar fecha. Avisar al desarrollador.");
                    }
                }
                default -> System.out.println("[ERROR] Valor invalido ingresado. Por favor, ingrese 'SI' o 'NO'.");

            }
        }
    }
}