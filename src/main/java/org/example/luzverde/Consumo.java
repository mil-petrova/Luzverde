package org.example.luzverde;
/**
 * @Author Milena Petrova
 * @Version 1.0
 * La clase la nesesitamos para guardar los valores de Kilovatios consumidos por persona
 * importo la clase Date porque en cada consumo tenemos dos fechas para indicar el principio y el fin
 * Esta vinculado con la clase Tarifa porque la nesesitamos para calcular el total que hay que pagar cada persona
 */

import java.util.Date;

class Consumo {
    private int id_consumo;
    private int id_persona;
    private double kWConsumidos;
    private Date fechaInicio;
    private Date fechaFin;
    private Tarifa tarifa;

    /**
     * El constructor sin parametros
     */
    public Consumo() {
    }

    /***
     * El constructor con parametros lo voy a nesesitar cuando en el futuro implemento una nueva funcion editar consumo el cliente
     * @param id_consumo
     * @param id_persona
     * @param kWConsumidos
     * @param fechaInicio
     * @param fechaFin
     * @param tarifa
     */
    public Consumo(int id_consumo, int id_persona, double kWConsumidos, Date fechaInicio, Date fechaFin, Tarifa tarifa) {
        this.id_consumo = id_consumo;
        this.id_persona = id_persona;
        this.kWConsumidos = kWConsumidos;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.tarifa = tarifa;
    }

    /**
     * Los geters y los seteres en este caso si que lo nesesito para llegar a los valores
     * @return
     */
    public int getId_consumo() {
        return id_consumo;
    }

    public void setId_consumo(int id_consumo) {
        this.id_consumo = id_consumo;
    }

    public int getId_persona() {
        return id_persona;
    }

    public void setId_persona(int id_persona) {
        this.id_persona = id_persona;
    }

    public double getkWConsumidos() {
        return kWConsumidos;
    }

    public void setkWConsumidos(double kWConsumidos) {
        this.kWConsumidos = kWConsumidos;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Tarifa getTarifa() {
        return tarifa;
    }

    public void setTarifa(Tarifa tarifa) {
        this.tarifa = tarifa;
    }

    /**
     * El metodo que nos da informacion copleta del consumo
     * @return
     */
    @Override
    public String toString() {
        return "Consumo{" +
                "id_consumo=" + id_consumo +
                ", id_persona=" + id_persona +
                ", kWConsumidos=" + kWConsumidos +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", tarifa=" + tarifa +
                '}';
    }
}

