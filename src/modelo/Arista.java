package modelo;

public class Arista {
    int id;
    String serial;

    public Arista(int id, String serial) {
        this.id = id;
        this.serial = serial;

        //todo PREGUNTAR PREFERIBLEMENTE CUAL ES LA DIFERENCIA ENTRE ID Y SERIAL?
    }

    @Override
    public String toString() {
        return "(" + id + ", " + serial + ")";
    }
}
