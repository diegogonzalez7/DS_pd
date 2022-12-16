package e2;

import java.util.Objects;
import java.util.PriorityQueue;

public class Tanque extends Subject {
    PriorityQueue<String> report = new PriorityQueue<>((o1, o2) -> {
        if (o1.contains("ROJA") && !o2.contains("ROJA")) return 1;
        else if (o1.contains("ROJA") && o2.contains("ROJA")) return 0;
        else return -1;
    });
    //Cada informe de las colas debe tener: tipo de alerta, nombre y ubicación del tanque
    //Nombre de la alerta, nombre del parámetro, nivel del parámetro y fecha y hora
    float ph;
    float oxygen;
    float temp;

    public Tanque(float ph, float oxygen, float temp) {
        if (ph >= 0 && ph <= 15 && oxygen >= 0) {
            this.ph = ph;
            this.oxygen = oxygen;
        } else throw new IllegalArgumentException("pH must be in range 0-15 and oxygen must be positive");
        this.temp = temp;
    }

    public float getPh() {
        return ph;
    }

    public float getOxygen() {
        return oxygen;
    }

    public float getTemp() {
        return temp;
    }

    public void incrementPh(int increment) {
        if (increment >= this.getPh() && increment <= 15) {
            ph += increment;
        }
        else throw new IllegalArgumentException("Invalid increment value");
        if (ph >= Rangos.MAX_PH.rangeValue) notifyObservers();
    }

    public void decrementPh(int decrement) {
        if (decrement <= this.getPh() && decrement >= 0) {
            ph -= decrement;
        }
        else throw new IllegalArgumentException("Invalid decrement value");
        if (ph <= Rangos.MIN_PH.rangeValue) notifyObservers();
    }

    public void incrementOxygen(int increment) {
        if (increment >= this.getOxygen()) {
            oxygen += increment;
        } else throw new IllegalArgumentException("Invalid increment value");
        if (oxygen >= Rangos.WARNHIGH_OX.rangeValue) notifyObservers();
    }

    public void decrementOxygen(int decrement) {
        if (decrement <= this.getOxygen() && decrement >= 0) {
            oxygen -= decrement;
        } else throw new IllegalArgumentException("Invalid decrement value");
        if (oxygen <= Rangos.WARNLOW_OX.rangeValue) notifyObservers();
    }

    public void incrementTemp(int increment) {
        if (increment >= this.getTemp()) {
            temp += increment;
        } else throw new IllegalArgumentException("Invalid increment value");
        if (temp >= Rangos.WARNHIGH_TEMP.rangeValue) notifyObservers();
    }

    public void decrementTemp(int decrement) {
        if (decrement <= this.getTemp()) {
            temp -= decrement;
        } else throw new IllegalArgumentException("Invalid decrement value");
        if (temp <= Rangos.WARNLOW_TEMP.rangeValue) notifyObservers();
    }

    public String showReport() {
        boolean orange = false;
        String returnedReport = "\nAlertas de mantenimiento del tanque\nAlertas ROJAS:";
        System.out.println("\nAlertas de mantenimiento del tanque\nAlertas ROJAS:");
        while (true) {
            assert report.peek() != null;
            if (!report.peek().contains("ROJA")) break;
            System.out.println(report.peek());
            returnedReport = returnedReport.concat(Objects.requireNonNull(report.poll()));
        }
        returnedReport = returnedReport.concat("\nAlertas NARANJAS:");
        System.out.println("\nAlertas NARANJAS:");
        while (report.peek() != null) {
            System.out.println(report.peek());
            returnedReport = returnedReport.concat(Objects.requireNonNull(report.poll()));
        }
        return returnedReport;
    }
}

/*
Nivel de pH:

Nivel normal entre 7,5 – 8,5
Nivel naranja 5 – 7,4 y 8,6 – 11
Nivel rojo 0 - 4,9 y 11,1 – 15

Nivel de oxígeno en agua:

Nivel normal entre 5 y 7 mg/l
Nivel naranja entre 3 - 4,9 mg/l y 7,1 - 9 mg/l
Nivel rojo entre 0 - 2,9 mg/l y más de 9 mg/l

Temperatura del agua:

Nivel normal: entre 24 y 27 ºC
Nivel naranja: entre 27,1 - 29 ºC y entre 22 – 23,9 grados
Nivel rojo: más de 29 grados o menos de 22 grados
 */