package org.example.luzverde;
/**
 * @author Milena Petrova
 * @Version 1.0
 * La clase de cliente que tambien extende la clase Persona la nesesitamos porque en nuestra base de datos hay que definir el tipo de persona
 */

import java.util.List;

public class Cliente extends Persona {
    private List<Contrato> contratos;
    private List<Consumo> consumos;
    private int id_cliente;


    /**
     * El constructor sin parametros en este caso porque hay que usarlo en las buenas practicas
     */
    public Cliente(){

    }

    /**
     * El constructor con parametros que realmente hereda de las clase Persona
     * @param id
     * @param nombre
     * @param apellido
     * @param nie
     * @param direccion
     * @param telefono
     * @param email
     * @param tipo
     * @param contratos
     * @param consumos
     * @param id_cliente
     */
    public Cliente(int id, String nombre, String apellido, String nie, String direccion, String telefono, String email, String tipo, List<Contrato> contratos, List<Consumo> consumos, int id_cliente) {
        super.setId(id);
        super.setNombre(nombre);
        super.setApellido(apellido);
        super.setNie(nie);
        super.setDireccion(direccion);
        super.setTelefono(telefono);
        super.setEmail(email);
        super.setTipo(tipo);
        this.contratos = contratos;
        this.consumos = consumos;
        this.id_cliente = id_cliente;
    }

    /**
     * El metodo que puede sacar toda la informacia sobre un cliente
     * @return
     */
    @Override
    public String toString() {
        return "Cliente{" +
                "contratos=" + contratos +
                ", consumos=" + consumos +
                ", id_cliente=" + id_cliente +
                '}';
    }
}




