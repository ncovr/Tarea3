package vista;

import modelo.GDP;

import java.util.InputMismatchException;
import java.util.Scanner;

public class VistaLoader {

    public Scanner sc = new Scanner(System.in);
    public GDP g = new GDP();

    public void menu() {
        int opcion;
        // SI LLEGAN A COLGAR ESTA MIERDA DE CODIGO, LE PLANTARE UNA PATADA EN LA RAJA A TU MAMITA

        do {
            System.out.print("""
                    ..:::: MENU PRINCIPAL ::::..
                    1. Insertar persona
                    2. Agregar una relación de amistad
                    3. Bloquear a un amigo
                    4. Encontrar a personas de cumpleaños en los proximos n dias
                    5. Obtener el nivel de amistad entre dos amigos
                    6. EJECUTAR FUNCIONES DEBUG
                    
                    7. Salir
                    """);
            System.out.print("OPCIÓN? ");

            try {
                opcion = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Por favor, ingrese un número válido.");
                sc.next();
                continue;
            }

            switch (opcion) {
                case 1 -> createPersona();
                case 2 -> linkFriendship();
                case 3 -> blockCrazyBitchLikeYourFuckingExGirlfriend();
                case 4 -> findBirthdayInNextNDays();
                case 5 -> obtainFriendshipLevel();

                // LO SIGUIENTE NO ES FUNDAMENTAL, PERO ES ÚTIL PARA DEBUG
                case 6 -> debugFunc();
                case 7 -> System.exit(0);
                default -> System.out.println("Opcion incorrecta");
            }
        } while (true);
    }

    private void createPersona() {

        try {
            System.out.println("..:: CREAR PERSONA ::..");
            String nombre = aux_getInput("NOMBRE DE LA PERSONA? ");
            int diaCumple = Integer.parseInt(aux_getInput("DÍA DE CUMPLEAÑOS? "));
            int mesCumple = Integer.parseInt(aux_getInput("MES DE CUMPLEAÑOS? "));
            String profesion = aux_getInput("PROFESIÓN? ");
            String email = aux_getInput("EMAIL? ");

            g.func_createPersona(nombre, diaCumple, mesCumple, profesion, email);

        } catch (Exception e) {
            System.out.println("Error al crear la persona. Por favor, verifique los datos ingresados.");
        }
    }

    private void linkFriendship() {
        try {
            System.out.println("..:: AGREGAR RELACIÓN DE AMISTAD ::..");
            String nombre1 = aux_getInput("NOMBRE DE LA PRIMERA PERSONA? ");
            String nombre2 = aux_getInput("NOMBRE DE LA SEGUNDA PERSONA? ");
            String fecha = aux_getInput("FECHA DE INICIO DE LA AMISTAD (DDMMYYYY)? ");

            g.func_friendsPersona(nombre1, nombre2, fecha);

        } catch (Exception e) {
            System.out.println("Error al agregar la relación de amistad. Por favor, verifique los datos ingresados.");
        }
    }

    private void blockCrazyBitchLikeYourFuckingExGirlfriend() {

        try {
            System.out.println("..:: BLOQUEAR A UN AMIGO ::..");
            String nombre1 = aux_getInput("NOMBRE DE LA PRIMERA PERSONA? ");
            String nombre2 = aux_getInput("NOMBRE DE LA SEGUNDA PERSONA? ");
            String fecha = aux_getInput("FECHA DE TERMINO DE LA AMISTAD (DDMMYYYY)? ");

            g.func_blockFriend(nombre1, nombre2, fecha);

        } catch (Exception e) {
            System.out.println("Error al bloquear a un amigo. Por favor, verifique los datos ingresados.");
        }
    }

    private void findBirthdayInNextNDays() {

        System.out.println("FUNCION NO IMPLEMENTADA, PERO AQUÍ ESTÁ EL ESQUELETO");
        //todo TRABAJAR EN ESTA MIERDA DE FUNCION REQLIA, AHHH SON LAS 4 DE LA MAÑANA Y NO PUEDO MÁS, ME QUIERO MATAR A PAJAS

        /*
        try {
            System.out.println("..:: ENCONTRAR CUMPLEAÑOS EN LOS PRÓXIMOS N DÍAS ::..");
            int n = Integer.parseInt(aux_getInput("¿EN CUANTOS DÍAS QUIERES BUSCAR CUMPLEAÑOS? "));
            // g.func_findBirthdayInNextNDays(n);
        } catch (Exception e) {
            System.out.println("Error al buscar cumpleaños. Por favor, verifique los datos ingresados.");
        }*/
    }

    private void obtainFriendshipLevel(){

        System.out.println("FUNCION NO IMPLEMENTADA, PERO AQUÍ ESTÁ EL ESQUELETO");
        //todo ESTA PENDEJADA IGUAL FALTA, PONGALE WENO A LA PALA, Y DESPUES CUELGUENME DE UN ARBOL USANDO LA CORNETA
    }

    private void debugFunc() {
        System.setProperty("org.graphstream.ui", "swing");


        /**
         * Cuidado, porque Matias no es lo mismo que Matías
         */

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

    private String aux_getInput(String texto) {
        System.out.print(texto);
        return sc.nextLine().trim();
    }
}
