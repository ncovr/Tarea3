package modelo;

public class Persona {
    int id;
    String nombre;
    int dia;
    int mes;
    String ocupacion;
    String email;

    public Persona(int id, String nombre, int dia, int mes, String ocupacion, String email) {
        this.id = id;
        this.nombre = nombre;
        this.dia = dia;
        this.mes = mes;
        this.ocupacion = ocupacion;
        this.email = email;
    }

    @Override
    public String toString() {
        return nombre;
    }

    public String getFechaCumple() {
        return dia + "/" + mes;
    }

    public String getCorreosElectronicos(){
        return email;
    }
}
