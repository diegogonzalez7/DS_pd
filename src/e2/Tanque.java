package e2;

import java.util.Objects;
import java.util.PriorityQueue;

public class Tanque extends Subject {
    PriorityQueue<String> report = new PriorityQueue<>((o1, o2) -> {
        if (o1.contains("ROJA") && !o2.contains("ROJA")) return 1;
        else if (o1.contains("ROJA") && o2.contains("ROJA")) return 0;
        else return -1;
    });

    double ph;
    double oxygen;
    double temp;
    String tankName;

    String tankUbication;

    public Tanque(double ph, double oxygen, double temp, String tankName, String tankUbication) {
        if (ph >= 0 && ph <= 15 && oxygen >= 0) {
            this.ph = ph;
            this.oxygen = oxygen;
        } else throw new IllegalArgumentException("pH must be in range 0-15 and oxygen must be positive");
        this.temp = temp;
        this.tankName = tankName;
        this.tankUbication = tankUbication;
    }


    public String getTankName() {
        return tankName;
    }

    public String getTankUbication() {
        return tankUbication;
    }

    public double getPh() {
        return ph;
    }

    public double getOxygen() {
        return oxygen;
    }

    public double getTemp() {
        return temp;
    }

    public void incrementPh(double increment) {
        if (increment >= this.getPh() && increment <= 15) {
            ph = increment;
        } else throw new IllegalArgumentException("Invalid increment value");
        if (ph >= Rangos.WARNHIGH_PH.rangeValue) notifyObservers();
    }

    public void decrementPh(double decrement) {
        if (decrement <= this.getPh() && decrement >= 0) {
            ph = decrement;
        } else throw new IllegalArgumentException("Invalid decrement value");
        if (ph <= Rangos.WARNLOW_PH.rangeValue) notifyObservers();
    }

    public void incrementOxygen(double increment) {
        if (increment >= this.getOxygen()) {
            oxygen = increment;
        } else throw new IllegalArgumentException("Invalid increment value");
        if (oxygen >= Rangos.WARNHIGH_OX.rangeValue) notifyObservers();
    }

    public void decrementOxygen(double decrement) {
        if (decrement <= this.getOxygen() && decrement >= 0) {
            oxygen = decrement;
        } else throw new IllegalArgumentException("Invalid decrement value");
        if (oxygen <= Rangos.WARNLOW_OX.rangeValue) notifyObservers();
    }

    public void incrementTemp(double increment) {
        if (increment >= this.getTemp()) {
            temp = increment;
        } else throw new IllegalArgumentException("Invalid increment value");
        if (temp >= Rangos.WARNHIGH_TEMP.rangeValue) notifyObservers();
    }

    public void decrementTemp(double decrement) {
        if (decrement <= this.getTemp()) {
            temp = decrement;
        } else throw new IllegalArgumentException("Invalid decrement value");
        if (temp <= Rangos.WARNLOW_TEMP.rangeValue) notifyObservers();
    }

    public String showReport() {

        String red = "\nAlertas ROJAS:";
        String orange = "\nAlertas NARANJAS:";
        String returnedReport = "\nAlertas de mantenimiento del tanque";
        System.out.println(returnedReport);
        while (report.peek() != null) {
            if (report.peek().contains("ROJA")) red = red.concat(Objects.requireNonNull(report.poll()));
            else orange = orange.concat(Objects.requireNonNull(report.poll()));
        }
        System.out.println(red + orange);
        returnedReport = returnedReport.concat(red + orange);
        return returnedReport;
    }
}

