package modelo;

/*
Para evitar las confusiones respecto a la arista, se ha decidido que la arista es una relación entre dos personas, donde el id
es el id de la persona destino de la arista, y el serial es una cadena que representa la fecha de la
relación más un dígito que indica el estado de la relación (amistad o bloqueo).

NO ES UN NODOD, PARA ESO SE TIENE LA CLASE "Persona", QUE REPRESENTA A UNA PERSONA EN EL GRAFO.

 */

public class Arista {
    int id;
    String serial;

    public Arista(int id, String serial) {
        this.id = id;
        this.serial = serial;

        /*
        El id y serial siguen el siguiente ejemplo: (1, 100320250)

        (destino de la arista en este caso la persona con id 1, fecha de la relación más el ultimo digito que representa amistad o bloqueo)
        El serial se compone de la fecha en formato ddmmaaaa, y el último dígito indica el estado de la relación
         */

    }

    @Override
    public String toString() {
        System.out.println("(" + id + ";;;" + serial + ")");
        return "(" + id + ";;;" + serial + ")";
    }
}
