package e2;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;

public class Oxigeno implements Observer {
    @Override
    public void update(Subject s) {
        //Cada informe de las colas debe tener: tipo de alerta, nombre y ubicación del tanque
        //Nombre de la alerta, nombre del parámetro, nivel del parámetro y fecha y hora
        Random rndMethod = new Random();
        LocalTime actTime = LocalTime.now();
        Tanque tanque = (Tanque) s;
        //Si se pasa por encima del rango normal
        if (tanque.getOxygen() >= Rangos.WARNHIGH_OX.rangeValue || tanque.getOxygen() <= Rangos.WARNLOW_OX.rangeValue) {
            if (tanque.getOxygen() >= Rangos.MAX_OXYGEN.rangeValue || tanque.getOxygen() <= Rangos.MIN_OXYGEN.rangeValue) {
                //Añadir alerta roja a la cola y estabilizar el nivel
                tanque.report.add("\nAlerta ROJA:\n" + tanque.getTankName() + ", " + tanque.getTankUbication() + "\nControl de oxígeno : parametro Oxígeno, nivel " + tanque.getOxygen() + "mg/l "
                        + actTime.getHour() + ":" + actTime.getMinute() + ":" + actTime.getSecond() + " " + LocalDate.now());
            } else {
                //Añador aleerta naranja a la cola de prioridad y estabilizar el nivel de oxigeno
                tanque.report.add("\nAlerta NARANJA:\n" + tanque.getTankName() + ", " + tanque.getTankUbication() + "\nControl de oxígeno : parametro Oxígeno, nivel " + tanque.getOxygen() + "mg/l " +
                        actTime.getHour() + ":" + actTime.getMinute() + ":" + actTime.getSecond() + " " + LocalDate.now());
            }
        }
        //Para estabilizar el nivel
        tanque.oxygen = rndMethod.nextDouble(Rangos.WARNLOW_OX.rangeValue, Rangos.WARNHIGH_OX.rangeValue);
    }
}