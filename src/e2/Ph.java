package e2;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;

public class Ph implements Observer {
    @Override
    public void update(Subject s) {
        Random rndMethod = new Random();
        LocalTime actTime = LocalTime.now();
        Tanque tanque = (Tanque) s;
        //Si se pasa por encima del rango normal
        if (tanque.getPh() >= Rangos.WARNHIGH_PH.rangeValue || tanque.getPh() <= Rangos.WARNLOW_PH.rangeValue) {
            if (tanque.getPh() >= Rangos.MAX_PH.rangeValue || tanque.getPh() <= Rangos.MIN_PH.rangeValue) {
                //Añadir alerta roja a la cola y estabilizar el nivel
                tanque.report.add("\nAlerta ROJA:\n" + tanque.getTankName() + ", " + tanque.getTankUbication() + "\nControl de pH : parametro pH, nivel " + tanque.getPh()
                        + actTime.getHour() + ":" + actTime.getMinute() + ":" + actTime.getSecond() + LocalDate.now());
            } else {
                //Añador aleerta naranja a la cola de prioridad y estabilizar el nivel de oxigeno
                tanque.report.add("\nAlerta NARANJA:\n" + tanque.getTankName() + ", " + tanque.getTankUbication() + "\nControl de pH : parametro pH, nivel " + tanque.getPh()
                        + actTime.getHour() + ":" + actTime.getMinute() + ":" + actTime.getSecond() + LocalDate.now());
            }
        }
        //Para estabilizar el nivel generamos un numero random en el rango normal y lo establecemos como el nuevo nivel de oxigeno
        tanque.ph = rndMethod.nextDouble(Rangos.WARNLOW_PH.rangeValue, Rangos.WARNHIGH_PH.rangeValue);
    }
}