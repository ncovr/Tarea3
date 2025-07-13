package modelo;

public enum Mes {
    ENERO(1, "Enero"),
    FEBRERO(2, "Febrero"),
    MARZO(3, "Marzo"),
    ABRIL(4, "Abril"),
    MAYO(5, "Mayo"),
    JUNIO(6, "Junio"),
    JULIO(7, "Julio"),
    AGOSTO(8, "Agosto"),
    SEPTIEMBRE(9, "Septiembre"),
    OCTUBRE(10, "Octubre"),
    NOVIEMBRE(11, "Noviembre"),
    DICIEMBRE(12, "Diciembre"),
    ;

    private int numero;
    private String nombre;

    Mes(int numero, String nombre) {
        this.numero = numero;
        this.nombre = nombre;
    }

    public static String fromNumero(int numero) {
        for (Mes mes : Mes.values()) {
            if (mes.numero == numero) {
                return mes.nombre;
            }
        }
        return "Desconocido";
    }
}
