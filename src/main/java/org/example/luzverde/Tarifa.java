package org.example.luzverde;

/**
 * @author Milena Petrova
 * @version 1.0
 * La clase Tarifa ,que contiene las 6 tarifa indicadas previamente y si se pueden cambiar en un futuro
 */
public class Tarifa {
    private int idTarifa;
    private String descripcion;
    private double costoKwh;

    /***
     * El constructor vasio
     */
    public Tarifa() {
    }

    /**
     * Los geters y seters ,algunos usados  y otros no de momento
     * @return
     */
    public int getIdTarifa() {
        return idTarifa;
    }

    public void setIdTarifa(int idTarifa) {
        this.idTarifa = idTarifa;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getCostoKwh() {
        return costoKwh;
    }

    public void setCostoKwh(double costoKwh) {
        this.costoKwh = costoKwh;
    }

    /**
     * El metodo sacar informacion completa sobre tarifa
     * @return
     */
    @Override
    public String toString() {
        return "Tarifa{" +
                "idTarifa=" + idTarifa +
                ", descripcion='" + descripcion + '\'' +
                ", costoKwh=" + costoKwh +
                '}';
    }
}



