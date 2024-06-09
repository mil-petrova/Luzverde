package org.example.luzverde;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Milena Petrova ,la clase de Administrador ,que va extender todos los atributos de la clase persona pero tambiene
 * contiene y las listas de contratos y consumos
 * @Version 1.0
 */
public class Admin extends Persona{
    private int id_admin;
    private List<Cliente> clientes;
    private List<Factura> facturas;
    private List<Consumo> consumos;
    private int facturaIdCounter;

    /**
     * El constructor sin parametros
     */
    public Admin() {
        super();
        this.clientes = new ArrayList<>();
        this.facturas = new ArrayList<>();
        this.consumos = new ArrayList<>();
        this.facturaIdCounter = 0;
    }

    /**
     * Y el constructor parametizado
     * @param id
     * @param nombre
     * @param apellido
     * @param nie
     * @param direccion
     * @param telefono
     * @param email
     * @param tipo
     * @param id_admin
     */
    public Admin(int id, String nombre, String apellido, String nie, String direccion, String telefono, String email, String tipo, int id_admin) {
        super.setId(id);
        super.setNombre(nombre);
        super.setApellido(apellido);
        super.setNie(nie);
        super.setDireccion(direccion);
        super.setTelefono(telefono);
        super.setEmail(email);
        super.setTipo(tipo);
        this.id_admin = id_admin;
        this.clientes = new ArrayList<>();
        this.facturas = new ArrayList<>();
        this.consumos = new ArrayList<>();
        this.facturaIdCounter = 0;
    }

    /**
     * El metodo que nos proporciona la informacion completa para un Administrador
     * @return
     */
    @Override
    public String toString() {
        return "Admin{" +
                "id_admin=" + id_admin +
                ", clientes=" + clientes +
                ", facturas=" + facturas +
                ", consumos=" + consumos +
                ", facturaIdCounter=" + facturaIdCounter +
                '}';
    }
}






