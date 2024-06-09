package org.example.luzverde;



import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Milena Petrova
 * @Version 1.0
 * La clase que esta controlando todas las acciones dentro de mi aplicacion
 */
public class LuzController {
    private LuzApp vista;
    private LuzModel modelo;

    /**
     * El constructor inicializamos la vista y el modelo para que podemos usar todos sus metos
     * @param modelo
     * @param vista
     */
    public LuzController(LuzModel modelo, LuzApp vista) {
        this.modelo = modelo;
        this.vista = vista;
    }



    public Persona getPersona(int id) {
        return modelo.getPersonaById(id);
    }

    public Contrato getContrato(int idPersona) {
        return modelo.getContratoByIdPersona(idPersona);
    }

    /**
     * El metodo que me actualiza la informacion sobre un cliente,primero actualiza persona y luego actualzia contrato
     * @param id
     * @param nombre
     * @param apellido
     * @param nie
     * @param direccion
     * @param telefono
     * @param email
     * @param tipo
     * @param idTarifa
     * @param fechaInicio
     * @param fechaFin
     * @param tipoContrato
     */
    public void updateCliente(int id, String nombre, String apellido, String nie, String direccion, String telefono, String email, String tipo, int idTarifa, Date fechaInicio, Date fechaFin, String tipoContrato) {

        modelo.updatePersona(id, nombre, apellido, nie, direccion, telefono, email, tipo);
        modelo.updateContrato(id, idTarifa, fechaInicio, fechaFin, tipoContrato);
    }

    /**
     * El metodo que controla como calcular la factura
     * @param idPersona
     * @param fechaInicio
     * @param fechaFin
     * @return
     */
    public double calcularMontoTotal(int idPersona, Date fechaInicio, Date fechaFin) {
        return modelo.calcularMontoTotal(idPersona, fechaInicio, fechaFin);
    }

    /**
     * El metodo que genera las  facturas tomando como parametro las fechas indicadas
     * @param fechaInicio
     * @param fechaFin
     * @return
     */
    public List<Factura> generarFacturas(Date fechaInicio, Date fechaFin) {
        List<Cliente> clientes = modelo.getAllClientes();
        List<Factura> facturas = new ArrayList<>();

        for (Cliente cliente : clientes) {
            double montoTotal = calcularMontoTotal(cliente.getId(), fechaInicio, fechaFin);
            Date fechaEmision = new Date(System.currentTimeMillis());

            Factura factura = new Factura();
            factura.setIdPersona(cliente.getId());
            factura.setFechaEmision(fechaEmision);
            factura.setFechaVencimiento(fechaFin);
            factura.setMontoTotal(montoTotal);

            modelo.insertFactura(factura);
            facturas.add(factura);
        }
        return facturas;
    }

    /**
     *
     * Este es metodo que me maneja como se van ha mostrar mis facturas
     * @param fechaInicio
     * @param fechaFin
     */
    public void handleGenerarFacturas(Date fechaInicio, Date fechaFin) {
        List<Factura> facturas = generarFacturas(fechaInicio, fechaFin);
        vista.mostrarFacturas(facturas, fechaInicio, fechaFin);
    }

    /**
     * Aqui tengo el metodo que me controla como se va ha borrar un cliente
     * @param nie
     */
    public void deleteClienteByNie(String nie) {
        Persona persona = modelo.getPersonaByNie(nie);
        if (persona != null && persona instanceof Cliente) {
            modelo.deleteFacturaByPersonaId(persona.getId());
            modelo.deleteContratoByPersonaId(persona.getId());
            modelo.deleteConsumoByPersonaId(persona.getId());
            modelo.deletePersonaById(persona.getId());
        }
    }

    public Persona getPersonaByNie(String nie) {
        return modelo.getPersonaByNie(nie);
    }

    /**
     * El metodo que me permite guardar todas las facturas en un archivo de tipo txt,permitiendo me guardarlo donde el usario elege
     * @param facturas
     * @param file
     */
    public void saveFacturasToText(List<Factura> facturas, File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Factura factura : facturas) {
                Persona persona = modelo.getPersonaById(factura.getIdPersona());
                String nombreCompleto = persona.getNombre() + " " + persona.getApellido();

                writer.write("ID Factura: " + factura.getIdFactura());
                writer.newLine();
                writer.write("ID Persona: " + factura.getIdPersona());
                writer.newLine();
                writer.write("Nombre: " + nombreCompleto);
                writer.newLine();
                writer.write("Fecha Emisión: " + factura.getFechaEmision());
                writer.newLine();
                writer.write("Fecha Vencimiento: " + factura.getFechaVencimiento());
                writer.newLine();
                writer.write("Monto Total: " + factura.getMontoTotal() + " €");
                writer.newLine();
                writer.newLine();  // Espacio entre facturas,para que queda bonito
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * EL metodo que controla como se guarda un nuevo cliente,primero mete en la base de datos la nueva persona y luego en el contrato
     * @param nombre
     * @param apellido
     * @param nie
     * @param direccion
     * @param telefono
     * @param email
     * @param idTarifa
     * @param fechaInicio
     * @param fechaFin
     * @param tipoContrato
     */
    public void saveCliente(String nombre, String apellido, String nie, String direccion, String telefono, String email, int idTarifa, Date fechaInicio, Date fechaFin, String tipoContrato) {
        modelo.insertPersona(nombre, apellido, nie, direccion, telefono, email, "CLIENTE");
        int idPersona = modelo.getLastInsertedPersonaId();
        modelo.insertContrato(idPersona, idTarifa, fechaInicio, fechaFin, tipoContrato);
    }

    }






























