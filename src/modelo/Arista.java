package modelo;

public class Arista {
    int id;
    String serial;

    public Arista(int id, String serial) {
        this.id = id;
        this.serial = serial;
    }

    @Override
    public String toString() {
        return "(" + id + ", " + serial + ")";
    }
}
