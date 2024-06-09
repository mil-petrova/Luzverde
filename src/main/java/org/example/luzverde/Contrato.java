package org.example.luzverde;
/**
 * @author Milena Petrova
 * @Version 1.4
 * Esta clase se cambio varias veces hasta conseguir todas las relaciones entre persona,tarifa y contrato
 */

import java.util.Date;

public class Contrato {
    private int idContrato;
    private int idPersona;
    private Date fechaInicio;
    private Date fechaFin;
    private String tipoContrato;
    private int id_tarifa;

    /**
     * El constructor vasio por las buenas practicas
     */
    public Contrato() {
    }

    /**
     * El constructor con parametros que le he utilizado en el momento de invocar datos en mi base de datos
     * @param idContrato
     * @param idPersona
     * @param idTarifa
     * @param fechaInicio
     * @param fechaFin
     * @param tipoContrato
     */
    public Contrato(int idContrato, int idPersona, int idTarifa, Date fechaInicio, Date fechaFin, String tipoContrato) {
        this.idContrato = idContrato;
        this.idPersona = idPersona;
        this.id_tarifa = idTarifa;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.tipoContrato = tipoContrato;
    }

    /**
     * Los geters y seters lo he sacado en todos los atributos porque creo que es importante si tengo tiempo se puede
     * ampliar las funcionalidades del programa
     * @return
     */
    public int getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(int idContrato) {
        this.idContrato = idContrato;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public java.sql.Date getFechaInicio() {
        return (java.sql.Date) fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public java.sql.Date getFechaFin() {
        return (java.sql.Date) fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(String tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    public int getId_tarifa() {
        return id_tarifa;
    }

    public void setId_tarifa(int id_tarifa) {
        this.id_tarifa = id_tarifa;
    }

    /**
     * El metodo que nos da toda la informacion sobre contrato
     * @return
     */
    @Override
    public String toString() {
        return "Contrato{" +
                "idContrato=" + idContrato +
                ", idPersona=" + idPersona +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", tipoContrato='" + tipoContrato + '\'' +
                ", id_tarifa=" + id_tarifa +
                '}';
    }
}