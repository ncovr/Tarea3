import vista.VistaLoader;

public class Main {
    public static void main(String[] args) {
        debugGeneralInformation();
        VistaLoader vistaLoader = new VistaLoader();
        vistaLoader.printMenu();
    }

    private static void debugGeneralInformation() {
        System.out.println("Pre-alpha version");
//        System.out.print("""
//                COMPILACIÓN 3, 12 DE JUNIO DE 2025
//                HORA: 16:54
//                PROGRAMA EN ESTADO SEMI-FUNCIONAL! REVISAR LISTA DE PENDIENTES (todo)
//                """);
    }
}
