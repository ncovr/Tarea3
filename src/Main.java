import vista.VistaLoader;

public class Main {
    public static void main(String[] args) {
        debugGeneralInformation();
        VistaLoader vistaLoader = new VistaLoader();
        vistaLoader.menu();
    }

    private static void debugGeneralInformation() {
        System.out.print("""
                COMPILACIÓN 1, 28 DE JUNIO DE 2025
                HORA: 9:40 PM
                PROGRAMA EN ESTADO NO FUNCIONAL! REVISAR LISTA DE PENDIENTES (todo)
                
                PRESIONE CUALQUIER TECLA PARA CONTINUAR CON LA EJECUCIÓN DEL PROGRAMA...
                """);

        System.out.println("CHECK CODE!");

        try {
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
