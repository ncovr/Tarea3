/* TRABAJO REALIZADO POR:
 * - Nicolas Verdugo Barrera
 * - Diego Mercado Herrera
 * - Ariel Bobadilla Barriga
 */

package vista;

import exceptions.GrafoException;
import modelo.GrafoDirigidoPonderado;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.IllegalFormatException;
import java.util.InputMismatchException;
import java.util.Random;
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
                    ║  7. Visualizar sistema               ║
                    ║  8. Salir                            ║
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
                    case 7 -> visualizer();
                    case 8 -> {
                        System.out.println("Saliendo del programa...");
                        return;
                    }
                    default ->
                            System.out.println("[ERROR] Opcion seleccionada no válida, intente nuevamente.");
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
            System.out.println("Registrar a una persona en el sistema");
            String nombre = aux_getInputString("Nombre: ");
            int diaCumple = aux_getInputInteger("Día de cumpleaños: ");
            int mesCumple = aux_getInputInteger("Mes de cumpleaños: ");
            String profesion = aux_getInputString("Profesión: ");
            String email = aux_getEmail();
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
            System.out.println("Dadas dos personas se establece una amistad entre ellas");
            String nombre1 = g.getNombre(aux_getIdPersona());
            String nombre2 = aux_getInputString("Nombre: ");
            g.exists(nombre2);
            String fecha = aux_getDate().format(DateTimeFormatter.ofPattern("ddMMyyyy"));

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
            System.out.println("Dadas dos personas el primero bloquea al segundo");
            String nombre1 = aux_getInputString("Nombre: ");
            g.exists(nombre1);
            String nombre2 = aux_getInputString("Nombre: ");
            g.exists(nombre2);
            String fecha = aux_getDate().format(DateTimeFormatter.ofPattern("ddMMyyyy"));

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
            System.out.println("Obtener los próximos cumpleaños dentro de k días");
            LocalDate fechaInicio = aux_getDate();
            int n = aux_getInputInteger("Rango de días: ");
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
            System.out.println("Dadas dos personas obtener su nivel de amistad");
            String p1 = aux_getInputString("Nombre: ");
            g.exists(p1);
            String p2 = aux_getInputString("Nombre: ");
            g.exists(p2);
            g.func_friendshipLevel(p1, p2);
        } catch (GrafoException e) {
            System.out.println(e.getMessage());
        }
    }

    private void visualizer(){
        g.debug_graphVisualizer();
    }

    // CLASES AUXILIARES, CREADAS PARA AGILIZAR EL CÓDIGO PRINCIPAL

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
        g.systemInfo();
    }

    private String aux_getInputString(String texto) {
        System.out.print("> "+texto);
        return sc.next();
    }

    private String aux_getEmail() {
        String email;
        int intentos = 0;

        do {
            email = aux_getInputString("Correo electrónico: ");
            intentos++;

            if (intentos > 2 || email.isEmpty()) {
                email = generarCorreoRandom();
                System.out.println("Lo ha intentado muchas veces. Se asignará "+email+" como correo");
                break;
            }

        } while (!g.verificarEmail(email));  // Cambiado el signo
        return email;
    }


    private String generarCorreoRandom() {
        String caracteres = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        int longitud = 10 + random.nextInt(6); // Genera un correo entre 10 a 15 caracteres

        for (int i = 0; i < longitud; i++) {
            sb.append(caracteres.charAt(random.nextInt(caracteres.length())));
        }

        return sb.toString() + "@gmail.com";
    }


    private int aux_getInputInteger(String texto) throws GrafoException {
        System.out.print("> "+texto);

        try {
            return Integer.parseInt(sc.next());
        } catch (NumberFormatException e) {
            throw new GrafoException("[ERROR] Entrada no válida; digite un número válido.");
        }
    }

    private LocalDate aux_getDate() throws GrafoException {
        while (true) {
            String val = aux_getInputString("Desea usar la fecha actual como fecha de inicio [s / n]? ");

            switch (val.toUpperCase()) {
                case "S" -> {
                    return LocalDate.now();
                }
                case "N" -> {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                    try {
                        int dia = aux_getInputInteger("> Día: ");
                        int mes = aux_getInputInteger("> Mes: ");
                        int anio = aux_getInputInteger("> Año: ");
                        String fechaStr = String.format("%02d/%02d/%04d", dia, mes, anio);
                        return LocalDate.parse(fechaStr, formatter);

                    } catch (GrafoException e) {
                        throw new GrafoException(e.getMessage());
                    } catch (IllegalFormatException e) {
                        throw new GrafoException("[ERROR] Formato de fecha no válido. Por favor, use el formato DD/MM/YYYY.");
                    } catch (InputMismatchException e) {
                        throw new GrafoException("[ERROR] Entrada no válida. Por favor, ingrese números enteros para día, mes y año.");
                    } catch (DateTimeParseException e) {
                        throw new GrafoException("[ERROR] Error serio al retornar fecha. Contactar con desarrollador.");
                    }
                }
                default -> System.out.println("[ERROR] Valor no válido ingresado. Por favor, ingrese 's' o 'n'.");
            }
        }
    }

    private int aux_getIdPersona(){
        // Funcion pa pedir el nombre
        // Obtener el nombre en una variable
        // Preguntar si existe más de una persona con el mismo nombre en el sistema
        // Si no es asi, retornar el nombre
        // Si hay mas de una persona con ese nombre, enlistarlas y pedir elección de cual quiere

        String nombre = aux_getInputString("Nombre: ");
        String lista = g.getListaInstancias(nombre);
        if (!lista.isBlank()){
            // Pedir la instancia correcta
            System.out.println(lista);
            nombre = g.getNombre(aux_getInputInteger("Id: "));
        }
        return g.getId(nombre);
    }
}

/*
¿Que esperabas encontrar aqui Orvar, la carpeta del git?
Desde hoy dejaré liricas de canciones.
Le quitas lo divertido a la vida, pero bueno, ya que.

https://youtu.be/Soy4jGPHr3g

doushite sugu shitte shimau no
kyoushin de kurushindeshi batou
koushin de furu inseki masshou-ka?
warukunai

kyoumi nee sukui hade kandou
joubi nee kusuri tabe BAD
chou hidee furu mitame angou-ka?
kane ni yoru

joushiki nee kurui ppade rantou
houchi gee suru in kya de bando
soushin de kurushii dame Cancel wa?
kusa haeru

doushite sugu miteshimau no
doushite sugu itte shimau no
doushite sugu koware chau ka na
(papara para para)

kin mirai shika katan
shoppingu mouru no gendai konpyuu
shou uindou shi ka katan
monti houru no keihi de pinbooru

oiru massaaji hyaku-bu kousu ashiyu-tsuki
yuzu o soete

te te te teto teto te te te tetorisu
doushite konna me ni ni ni

kyoumi ga nai koto honki janai mono zenbu atomawashi de
shitteru koto wa shiranpuri watashi wa owatteru

hazukashii kako shitteru yatsu-ra no kioku kesa sete
meiwaku kakete gomen tteba nee darekatasukete

moushin de sugu hika re chau no
kyoushin de sugu hika re chau no
shiritakunai shiritakunai shiritakunai
(ta tara tara tara)

mou minee sugu hika re SAD
joukin de kurui-tate MAD
ikitakunai ikitakunai ikitakunai
baai bai

rinji kyuugyou sentaku booru
naichingeeru no haishin geemu
meishi koukan no mei shiin
shakkin booru de chinchiro haibooru

onsen ryokou sanpaku yokka shokuji-tsuki
hayaku ikitee

te te te teto teto
te te te tetorisu
kyoukan-sei shuuchi chi chi

utsu toka sou toka isogashiku te nemure nai wa kyou mo
dare ka hayaku nagutte kizetsu sa sete kure yo

jinsei kyanseru kyanseru kaiwai
bon'yari to shita fuan
watashi no sei watashi ga warui yo souda yo

kyoumi ga dete mo honki de yatte mo zenbu karamawari de
shiranai kuse ni usotsuki watashi wa owatteru

hazukashii kako shitteru yatsu-ra no kioku kesa sete
meiwaku kakete gomen tteba
nee darekatasukete

meiwaku kakete gomen tteba
nee dare ka yurushite

~AB
 */