package e2;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class TanqueTest {


    Tanque tanque = new Tanque(7.6, 5.1, 24.1, "Piscina de las focas", "Exterior");
    Temp temp = new Temp();
    Oxigeno oxigeno = new Oxigeno();
    Ph ph = new Ph();


    @Test
    void incrementPh() {
        /*
    Nivel de pH:

    Nivel normal entre 7,5 – 8,5
    Nivel naranja 5 – 7,4 y 8,6 – 11
    Nivel rojo 0 - 4,9 y 11,1 – 15
    */
        tanque.attach(ph);
        tanque.incrementPh(8);
        assertEquals(tanque.getPh(), 8);
        assertThrows(IllegalArgumentException.class, () -> tanque.incrementPh(17));
        assertThrows(IllegalArgumentException.class, () -> tanque.incrementPh(6));
        tanque.incrementPh(9);
        assertEquals(tanque.report.size(), 1);
        assertTrue(tanque.report.peek().contains("NARANJA"));
        assertTrue(tanque.ph >= Rangos.WARNLOW_PH.rangeValue && tanque.ph <= Rangos.WARNHIGH_PH.rangeValue);
        tanque.showReport();
        tanque.incrementPh(14);
        assertEquals(tanque.report.size(), 1);
        assertTrue(tanque.report.peek().contains("ROJA"));
        assertTrue(tanque.ph >= Rangos.WARNLOW_PH.rangeValue && tanque.ph <= Rangos.WARNHIGH_PH.rangeValue);
        tanque.showReport();
    }

    @Test
    void decrementPh() {
           /*
    Nivel de pH:

    Nivel normal entre 7,5 – 8,5
    Nivel naranja 5 – 7,4 y 8,6 – 11
    Nivel rojo 0 - 4,9 y 11,1 – 15
    */
        tanque.attach(ph);
        tanque.decrementPh(7.55);
        assertEquals(tanque.getPh(), 7.55);
        assertThrows(IllegalArgumentException.class, () -> tanque.decrementPh(-1));
        assertThrows(IllegalArgumentException.class, () -> tanque.decrementPh(8));
        tanque.decrementPh(6);
        assertEquals(tanque.report.size(), 1);
        assertTrue(tanque.report.peek().contains("NARANJA"));
        assertTrue(tanque.ph >= Rangos.WARNLOW_PH.rangeValue && tanque.ph <= Rangos.WARNHIGH_PH.rangeValue);
        tanque.showReport();
        tanque.decrementPh(0);
        assertEquals(tanque.report.size(), 1);
        assertTrue(tanque.report.peek().contains("ROJA"));
        assertTrue(tanque.ph >= Rangos.WARNLOW_PH.rangeValue && tanque.ph <= Rangos.WARNHIGH_PH.rangeValue);
        tanque.showReport();
    }

    @Test
    void incrementOxygen() {
        /*
        Nivel de oxígeno en agua:

        Nivel normal entre 5 y 7 mg/l
        Nivel naranja entre 3 - 4,9 mg/l y 7,1 - 9 mg/l
        Nivel rojo entre 0 - 2,9 mg/l y más de 9 mg/l
*/
        tanque.attach(oxigeno);
        tanque.incrementOxygen(6);
        assertEquals(tanque.getOxygen(), 6);
        assertThrows(IllegalArgumentException.class, () -> tanque.incrementOxygen(4));
        tanque.incrementOxygen(8);
        assertEquals(tanque.report.size(), 1);
        assertTrue(tanque.report.peek().contains("NARANJA"));
        assertTrue(tanque.oxygen >= Rangos.WARNLOW_OX.rangeValue && tanque.oxygen <= Rangos.WARNHIGH_OX.rangeValue);
        tanque.showReport();
        tanque.incrementOxygen(14);
        assertEquals(tanque.report.size(), 1);
        assertTrue(tanque.report.peek().contains("ROJA"));
        assertTrue(tanque.oxygen >= Rangos.WARNLOW_OX.rangeValue && tanque.oxygen <= Rangos.WARNHIGH_OX.rangeValue);
        tanque.showReport();
    }

    @Test
    void decrementOxygen() {
         /*
        Nivel de oxígeno en agua:

        Nivel normal entre 5 y 7 mg/l
        Nivel naranja entre 3 - 4,9 mg/l y 7,1 - 9 mg/l
        Nivel rojo entre 0 - 2,9 mg/l y más de 9 mg/l
*/
        tanque.attach(oxigeno);
        tanque.decrementOxygen(5.05);
        assertEquals(tanque.getOxygen(), 5.05);
        assertThrows(IllegalArgumentException.class, () -> tanque.decrementOxygen(-1));
        assertThrows(IllegalArgumentException.class, () -> tanque.decrementOxygen(7));
        tanque.decrementOxygen(4);
        assertEquals(tanque.report.size(), 1);
        assertTrue(tanque.report.peek().contains("NARANJA"));
        assertTrue(tanque.oxygen >= Rangos.WARNLOW_OX.rangeValue && tanque.oxygen <= Rangos.WARNHIGH_OX.rangeValue);
        tanque.showReport();
        tanque.decrementOxygen(2);
        assertEquals(tanque.report.size(), 1);
        assertTrue(tanque.report.peek().contains("ROJA"));
        assertTrue(tanque.oxygen >= Rangos.WARNLOW_OX.rangeValue && tanque.oxygen <= Rangos.WARNHIGH_OX.rangeValue);
        tanque.showReport();
    }

    @Test
    void incrementTemp() {
        /*
        Temperatura del agua:

        Nivel normal: entre 24 y 27 ºC
        Nivel naranja: entre 27,1 - 29 ºC y entre 22 – 23,9 grados
        Nivel rojo: más de 29 grados o menos de 22 grados
         */
        tanque.attach(temp);
        tanque.incrementTemp(25);
        assertEquals(tanque.getTemp(), 25);
        assertThrows(IllegalArgumentException.class, () -> tanque.incrementTemp(17));
        tanque.incrementTemp(28);
        assertEquals(tanque.report.size(), 1);
        assertTrue(tanque.report.peek().contains("NARANJA"));
        assertTrue(tanque.temp >= Rangos.WARNLOW_TEMP.rangeValue && tanque.temp <= Rangos.WARNHIGH_TEMP.rangeValue);
        tanque.showReport();
        tanque.incrementTemp(30);
        assertEquals(tanque.report.size(), 1);
        assertTrue(tanque.report.peek().contains("ROJA"));
        assertTrue(tanque.temp >= Rangos.WARNLOW_TEMP.rangeValue && tanque.temp <= Rangos.WARNHIGH_TEMP.rangeValue);
        tanque.showReport();
    }

    @Test
    void decrementTemp() {
        /*
        Temperatura del agua:

        Nivel normal: entre 24 y 27 ºC
        Nivel naranja: entre 27,1 - 29 ºC y entre 22 – 23,9 grados
        Nivel rojo: más de 29 grados o menos de 22 grados
         */
        tanque.attach(temp);
        tanque.decrementTemp(24.05);
        assertEquals(tanque.getTemp(), 24.05);
        assertThrows(IllegalArgumentException.class, () -> tanque.decrementTemp(27));
        tanque.decrementTemp(23);
        assertEquals(tanque.report.size(), 1);
        assertTrue(tanque.report.peek().contains("NARANJA"));
        assertTrue(tanque.temp >= Rangos.WARNLOW_TEMP.rangeValue && tanque.temp <= Rangos.WARNHIGH_TEMP.rangeValue);
        tanque.showReport();
        tanque.decrementTemp(21);
        assertEquals(tanque.report.size(), 1);
        assertTrue(tanque.report.peek().contains("ROJA"));
        assertTrue(tanque.temp >= Rangos.WARNLOW_TEMP.rangeValue && tanque.temp <= Rangos.WARNHIGH_TEMP.rangeValue);
        tanque.showReport();
    }

    @Test
    void showReport() {
        tanque.attach(ph);
        tanque.attach(oxigeno);
        tanque.attach(temp);
        tanque.incrementPh(9);
        assertEquals(tanque.report.size(), 1);
        tanque.decrementPh(0);
        assertEquals(tanque.report.size(), 2);
        tanque.incrementOxygen(8);
        assertEquals(tanque.report.size(), 3);
        tanque.decrementOxygen(2);
        assertEquals(tanque.report.size(), 4);
        tanque.incrementTemp(28);
        assertEquals(tanque.report.size(), 5);
        tanque.decrementTemp(21);
        assertEquals(tanque.report.size(), 6);
        LocalTime actTime = LocalTime.now();
        assertEquals(tanque.showReport(), "\nAlertas de mantenimiento del tanque\n" +

                "Alertas ROJAS:\n" +
                "Alerta ROJA:\n" +
                "Piscina de las focas, Exterior\n" +
                "Control de oxígeno : parametro Oxígeno, nivel 2.0mg/l "
                + actTime.getHour() + ":" + actTime.getMinute() + ":" + actTime.getSecond() + " " + LocalDate.now() + "\n" +
                "Alerta ROJA:\n" +
                "Piscina de las focas, Exterior\n" +
                "Control de pH : parametro pH, nivel 0.0 " +
                +actTime.getHour() + ":" + actTime.getMinute() + ":" + actTime.getSecond() + " " + LocalDate.now() + "\n" +
                "Alerta ROJA:\n" +
                "Piscina de las focas, Exterior\n" +
                "Control de temperatura : parametro temperatura, nivel 21.0ºC " +
                +actTime.getHour() + ":" + actTime.getMinute() + ":" + actTime.getSecond() + " " + LocalDate.now() + "\n" +
                "Alertas NARANJAS:\n" +
                "Alerta NARANJA:\n" +
                "Piscina de las focas, Exterior\n" +
                "Control de temperatura : parametro temperatura, nivel 28.0ºC " +
                +actTime.getHour() + ":" + actTime.getMinute() + ":" + actTime.getSecond() + " " + LocalDate.now() + "\n" +
                "Alerta NARANJA:\n" +
                "Piscina de las focas, Exterior\n" +
                "Control de oxígeno : parametro Oxígeno, nivel 8.0mg/l " +
                +actTime.getHour() + ":" + actTime.getMinute() + ":" + actTime.getSecond() + " " + LocalDate.now() + "\n" +
                "Alerta NARANJA:\n" +
                "Piscina de las focas, Exterior\n" +
                "Control de pH : parametro pH, nivel 9.0 " +
                +actTime.getHour() + ":" + actTime.getMinute() + ":" + actTime.getSecond() + " " + LocalDate.now());
        assertEquals(tanque.report.size(), 0);
        tanque.detach(ph);
        tanque.detach(oxigeno);
        tanque.detach(temp);
    }
}