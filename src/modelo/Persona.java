package modelo;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.temporal.ChronoUnit;

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

    public String get(){
        String sid = "";
        if (id < 10) sid = "00"+id;
        if (id < 100) sid = "0"+id;
        return "("+sid+") "+nombre+" "+email;
    }

    public String getNombreYid(){
        return nombre+" ("+id+")";
    }

    public String getFechaCumple() {
        return dia + "/" + mes;
    }

    public String getSerial(){
        String serial = "";
        if (this.dia < 10) serial += "0"+dia;
        else serial += ""+dia;
        if (this.mes < 10) serial += "0"+mes;
        else serial += ""+mes;
        return serial+="0000";
    }

    public String getCuentaRegresiva() {
        LocalDate hoy = LocalDate.now();

        // Cumpleaños este año
        MonthDay cumpleMesDia = MonthDay.of(mes, dia);
        LocalDate proximoCumple = cumpleMesDia.atYear(hoy.getYear());

        // Si ya pasó este año, se toma el siguiente
        if (proximoCumple.isBefore(hoy) || proximoCumple.isEqual(hoy) == false && cumpleMesDia.isBefore(MonthDay.from(hoy))) {
            proximoCumple = proximoCumple.plusYears(1);
        }

        long dias = ChronoUnit.DAYS.between(hoy, proximoCumple);

        String response;
        if (dias == 0) {
            response = "hoy";
        } else if (dias == 1) {
            response = "mañana";
        } else {
            response = "en " + dias + " días";
        }

        return response;
    }

    public String getCorreosElectronicos(){
        return email;
    }
}
