package org.example.luzverde;

/**
 * @author Milena Petrova
 * @Version 1.0
 * La clase factura me guarda la informacion para en un futuro imprimirla ,para que sea mas asesible para el usario
 */

import java.util.Date;

/**
 * Todos los atributos que creo que entran en una factura ,siempre se puede apliar la informacion
 */
public class Factura {
    private int idFactura;
    private int idPersona;
    private Date fechaEmision;
    private Date fechaVencimiento;
    private double montoTotal;

    public Factura() {
    }

    /**
     * El constructor con parametros
     * @param idFactura
     * @param idPersona
     * @param fechaEmision
     * @param fechaVencimiento
     * @param montoTotal
     */
    public Factura(int idFactura, int idPersona, Date fechaEmision, Date fechaVencimiento, double montoTotal) {
        this.idFactura = idFactura;
        this.idPersona = idPersona;
        this.fechaEmision = fechaEmision;
        this.fechaVencimiento = fechaVencimiento;
        this.montoTotal = montoTotal;
    }

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    /**
     * El metodo para sacar la informacion de factura
     * @return
     */
    @Override
    public String toString() {
        return "Factura{" +
                "idFactura=" + idFactura +
                ", idPersona=" + idPersona +
                ", fechaEmision=" + fechaEmision +
                ", fechaVencimiento=" + fechaVencimiento +
                ", montoTotal=" + montoTotal +
                '}';
    }
}