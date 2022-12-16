package e2;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;

public class Temp implements Observer {
    @Override
    public void update(Subject s) {
        Random rndMethod = new Random();
        LocalTime actTime = LocalTime.now();
        Tanque tanque = (Tanque) s;
        //Si se pasa por encima del rango normal
        if (tanque.getTemp() >= Rangos.WARNHIGH_TEMP.rangeValue || tanque.getTemp() <= Rangos.WARNLOW_TEMP.rangeValue) {
            if (tanque.getTemp() >= Rangos.MAX_TEMP.rangeValue || tanque.getTemp() <= Rangos.MIN_TEMP.rangeValue) {
                //Añadir alerta roja a la cola y estabilizar el nivel
                tanque.report.add("\nAlerta ROJA:\n" + tanque.getTankName() + ", " + tanque.getTankUbication() + "\nControl de temperatura : parametro temperatura, nivel " + tanque.getTemp() + "ºC " +
                        actTime.getHour() + ":" + actTime.getMinute() + ":" + actTime.getSecond() + " " + LocalDate.now());
            } else {
                //Añador aleerta naranja a la cola de prioridad y estabilizar el nivel de oxigeno
                tanque.report.add("\nAlerta NARANJA:\n" + tanque.getTankName() + ", " + tanque.getTankUbication() + "\nControl de temperatura : parametro temperatura, nivel " + tanque.getTemp() + "ºC " +
                        actTime.getHour() + ":" + actTime.getMinute() + ":" + actTime.getSecond() + " " + LocalDate.now());
            }
        }
        //Para estabilizar el nivel
        tanque.temp = rndMethod.nextDouble(Rangos.WARNLOW_TEMP.rangeValue, Rangos.WARNHIGH_TEMP.rangeValue);
    }
}
