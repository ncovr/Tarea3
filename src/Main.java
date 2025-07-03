import vista.VistaLoader;

public class Main {
    public static void main(String[] args) {
        debugGeneralInformation();
        VistaLoader vistaLoader = new VistaLoader();
        vistaLoader.menu();
    }

    private static void debugGeneralInformation() {
        System.out.print("""
                COMPILACIÓN 2, 03 DE JUNIO DE 2025
                HORA: 3: 57 AM
                PROGRAMA EN ESTADO NO FUNCIONAL! REVISAR LISTA DE PENDIENTES (todo)
                
                PRESIONE CUALQUIER TECLA PARA CONTINUAR CON LA EJECUCIÓN DEL PROGRAMA...
                """);

        try {
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
