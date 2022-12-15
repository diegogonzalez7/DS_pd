package e2;

public enum Rangos {
    //Rangos pH
    MAX_PH(11.0),
    WARNHIGH_PH (8.5),
    WARNLOW_PH(7.5),
    MIN_PH(5),

    //Rangos oxigeno
    MAX_OXYGEN(9),
    WARNHIGH_OX (7.1),
    WARNLOW_OX (4.9),
    MIN_OXYGEN(3),

    //Rangos temperatura
    MAX_TEMP(29),
    WARNHIGH_TEMP (27.1),
    WARNLOW_TEMP(23.9),
    MIN_TEMP (22);

    final double rangeValue;
    Rangos(double rangeValue) {
        this.rangeValue=rangeValue;
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
