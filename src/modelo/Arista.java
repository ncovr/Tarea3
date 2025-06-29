package modelo;

public class Arista {
    int id;
    String serial;

    public Arista(int id, String serial) {
        this.id = id;
        this.serial = serial;
    }

    public String toString() {
        return "(" + id + ", " + serial + ")";
    }
}
