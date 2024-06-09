package org.example.luzverde;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Milena Petrova
 * @version 1.0
 * La clase Modelo que sirve  en enseñar ,los procesos de crear la base de datos,las inseraciones de las datos,y su manejo
 */

public class LuzModel {
    private Connection connection = null;
    private String dbName = "luzverde";
    private String url = "jdbc:mysql://localhost:3306/";
    private String user = "root";
    private String password = "123456";
    private static final String LETRAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /***
     * El metodo que me crea la base de datos en el workbench
     */
    public void createDatabase() {
        String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS " + dbName;

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(createDatabaseSQL);
            System.out.println("Database " + dbName + " created successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * El metodo que me crea la pimera tabla que tiene que ser Persona ,guardando el orden de insertacion
     * @throws SQLException
     */
    public void createPersonaTable() throws SQLException {
        String createPersonaTable = "CREATE TABLE IF NOT EXISTS Persona (" +
                "id INT NOT NULL AUTO_INCREMENT, " +
                "nombre VARCHAR(255) NOT NULL, " +
                "apellido VARCHAR(255) NOT NULL, " +
                "nie VARCHAR(255) NOT NULL, " +
                "direccion VARCHAR(255) NOT NULL, " +
                "telefono VARCHAR(255) NOT NULL, " +
                "email VARCHAR(255) NOT NULL, " +
                "tipo ENUM('ADMIN', 'CLIENTE') NOT NULL, " +
                "PRIMARY KEY(id)" +
                ")";

        try (Connection connection = DriverManager.getConnection(url + dbName, user, password);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(createPersonaTable);
            System.out.println("Table 'Persona' created successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * El metodo que crea la segunda tabla Tarifa
     * He searado una por una para controlar mejor los erores que me pueden ocurir
     * @throws SQLException
     */
    public void createTarifaTable() throws SQLException {
        String createTarifaTable = "CREATE TABLE IF NOT EXISTS Tarifa (" +
                "id_tarifa INT NOT NULL AUTO_INCREMENT, " +
                "descripcion VARCHAR(255) NOT NULL, " +
                "costo_kwh DOUBLE NOT NULL, " +
                "PRIMARY KEY(id_tarifa)" +
                ")";

        try (Connection connection = DriverManager.getConnection(url + dbName, user, password);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(createTarifaTable);
            System.out.println("Table 'Tarifa' created successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * El metodo crear la siguente tabla Consumo,que esta conectada con la tabla persona y la tabla tarifa y tabla consumo
     * @throws SQLException
     */
    public void createConsumoTable() throws SQLException {
        String createConsumoTable = "CREATE TABLE IF NOT EXISTS Consumo (" +
                "id_consumo INT NOT NULL AUTO_INCREMENT, " +
                "id_persona INT NOT NULL, " +
                "kWConsumidos DOUBLE NOT NULL, " +
                "fechaInicio DATE NOT NULL, " +
                "fechaFin DATE NOT NULL, " +
                "id_tarifa INT NOT NULL, " +
                "PRIMARY KEY(id_consumo), " +
                "CONSTRAINT FK_Consumo_Persona FOREIGN KEY (id_persona) REFERENCES Persona(id) " +
                "ON UPDATE CASCADE ON DELETE NO ACTION, " +
                "CONSTRAINT FK_Consumo_Tarifa FOREIGN KEY (id_tarifa) REFERENCES Tarifa(id_tarifa) " +
                "ON UPDATE CASCADE ON DELETE NO ACTION" +
                ")";

        try (Connection connection = DriverManager.getConnection(url + dbName, user, password);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(createConsumoTable);
            System.out.println("Table 'Consumo' created successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * El metodo que me crea la tabla Contrato que esta conectada con persona y tarifa
     * @throws SQLException
     */
    public void createContratoTable() throws SQLException {
        String createContratoTable = "CREATE TABLE IF NOT EXISTS Contrato (" +
                "id_contrato INT NOT NULL AUTO_INCREMENT, " +
                "id_persona INT NOT NULL, " +
                "id_tarifa INT NOT NULL, " +
                "fecha_inicio DATE NOT NULL, " +
                "fecha_fin DATE, " +
                "tipo_contrato VARCHAR(255) NOT NULL, " +
                "PRIMARY KEY(id_contrato), " +
                "CONSTRAINT FK_Contrato_Persona FOREIGN KEY (id_persona) REFERENCES Persona(id) " +
                "ON UPDATE CASCADE ON DELETE NO ACTION" +
                ")";

        try (Connection connection = DriverManager.getConnection(url + dbName, user, password);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(createContratoTable);
            System.out.println("Table 'Contrato' created successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * El metodo que me crea la tabla Factura ,esta conectada con persona
     * @throws SQLException
     */
    public void createFacturaTable() throws SQLException {
        String createFacturaTable = "CREATE TABLE IF NOT EXISTS Factura (" +
                "id_factura INT NOT NULL AUTO_INCREMENT, " +
                "id_persona INT NOT NULL, " +
                "fecha_emision DATE NOT NULL, " +
                "fecha_vencimiento DATE NOT NULL, " +
                "monto_total DOUBLE NOT NULL, " +
                "PRIMARY KEY(id_factura), " +
                "CONSTRAINT FK_Factura_Persona FOREIGN KEY (id_persona) REFERENCES Persona(id) " +
                "ON UPDATE CASCADE ON DELETE NO ACTION" +
                ")";

        try (Connection connection = DriverManager.getConnection(url + dbName, user, password);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(createFacturaTable);
            System.out.println("Table 'Factura' created successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * El metodo que me sirve para insertar datos dentro de mis tablas en la base de datos luzverde
     */

    public void insertTarifas() {
        String[] tarifasEjemplos = {
                "Tarifa Básica:   sin hora restrigida  * 0.2914 * Consumo",
                "Tarifa Nocturna:   Desde 10:00h hasta 11:00h   * 0.10914 * Consumo",
                "Tarifa Solar:   sin hora  restrigida  * 0.3914 * Consumo",
                "Tarifa Empresarial: 24h  * 0.1814 * Consumo",
                "Tarifa Verde: sin hora restrigida  * 0.2614 * Consumo",
                "Tarifa Estudiante: Desde 08:00h hasta  11:00H * 0.22 * Consumo"
        };

        double[] costosKwh = {0.2914, 0.10914, 0.3914, 0.1814, 0.2614, 0.22};

        String insertTarifa = "INSERT INTO Tarifa (descripcion, costo_kwh) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(url + dbName, user, password);
             PreparedStatement statement = connection.prepareStatement(insertTarifa)) {

            for (int i = 0; i < tarifasEjemplos.length; i++) {
                statement.setString(1, tarifasEjemplos[i]);
                statement.setDouble(2, costosKwh[i]);
                statement.executeUpdate();
                System.out.println("Tarifa " + (i + 1) + " inserted successfully!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * El metodo que sirve para insertar datos en la tabla persona en el momento de editar cliente
     * @param nombre
     * @param apellido
     * @param nie
     * @param direccion
     * @param telefono
     * @param email
     * @param tipo
     * @return
     */
    public int insertPersona(String nombre, String apellido, String nie, String direccion, String telefono, String email, String tipo) {
        String sql = "INSERT INTO Persona(nombre, apellido, nie, direccion, telefono, email, tipo) VALUES(?, ?, ?, ?, ?, ?, ?)";
        int personaId = 0;
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = DriverManager.getConnection(url + dbName, user, password);
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, nombre);
            pstmt.setString(2, apellido);
            pstmt.setString(3, nie);
            pstmt.setString(4, direccion);
            pstmt.setString(5, telefono);
            pstmt.setString(6, email);
            pstmt.setString(7, tipo);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        personaId = rs.getInt(1);
                    }
                }
            }

            connection.commit();  // Commit transaction
            System.out.println("Persona insertada correctamente con ID: " + personaId);
        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();  // Rollback transaction on error
                    System.out.println("Transaction rolled back due to error: " + e.getMessage());
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);  // Restore default auto-commit behavior
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return personaId;
    }

    /**
     * El metodo que me sirve para insertar datos dentro del Contrato
     * @param idPersona
     * @param idTarifa
     * @param fechaInicio
     * @param fechaFin
     * @param tipoContrato
     */
    public void insertContrato(int idPersona, int idTarifa, Date fechaInicio, Date fechaFin, String tipoContrato) {
        String sql = "INSERT INTO Contrato(id_persona, id_tarifa, fecha_inicio, fecha_fin, tipo_contrato) VALUES(?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url + dbName, user, password);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, idPersona);
            pstmt.setInt(2, idTarifa);
            pstmt.setDate(3, fechaInicio);
            pstmt.setDate(4, fechaFin);
            pstmt.setString(5, tipoContrato);
            pstmt.executeUpdate();
            System.out.println("Contrato insertado correctamente con idPersona: " + idPersona);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * El metodo que me sirve insertar  20 personas en mi base de datos con sus datos conseguidos aliatoriamente grasias a dos metodos externos
     */
    public void insertarMultiplesPersonasConContratos() {
        for (int i = 1; i <= 20; i++) {
            String nie = generarNie();
            String telefono = generarTelefono();
            int idPersona = insertPersona("Nombre" + i, "Apellido" + i, nie, "ADDRESS" + i, telefono, "email" + i + "@gmail.com", "CLIENTE");

            if (idPersona > 0) { // Comprobamos si lapersona se insertó correctamente
                int idTarifa = 1; // La id de la tarifa he puesto 1 porque esto sirve solo para probar mi programa
                Date fechaInicio = Date.valueOf("2023-01-01");
                Date fechaFin = Date.valueOf("2024-01-01");
                String tipoContrato = "TipoContrato" + i;

                insertContrato(idPersona, idTarifa, fechaInicio, fechaFin, tipoContrato);
            } else {
                System.out.println("Error al insertar persona " + i);
            }
        }

        // Insertar un administrador
        String nieAdmin = generarNie();
        String telefonoAdmin = generarTelefono();
        int idPersonaAdmin = insertPersona("Admin", "Admin", nieAdmin, "DireccionAdmin", telefonoAdmin, "admin@example.com", "ADMIN");

        if (idPersonaAdmin > 0) { // Compruebo si  admin se insertó correctamente
            int idTarifaAdmin = 1;
            Date fechaInicioAdmin = Date.valueOf("2023-01-01");
            Date fechaFinAdmin = Date.valueOf("2024-01-01");
            String tipoContratoAdmin = "Controla";

            insertContrato(idPersonaAdmin, idTarifaAdmin, fechaInicioAdmin, fechaFinAdmin, tipoContratoAdmin);
        } else {
            System.out.println("Error al insertar administrador");
        }
    }

    /**
     * El metodo que me sirve para insertar datos del consumo de las 20 personas ,que he metido en el metodo interior
     */
    public void insertarDatosConsumo() {
        int numberOfRecords = 20;
        Random rand = new Random();

        String sql = "INSERT INTO Consumo (id_persona, kWConsumidos, fechaInicio, fechaFin, id_tarifa) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url + dbName, user, password);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            for (int i = 1; i <= numberOfRecords; i++) {
                if (getPersonaById(i) != null) {
                    double kwhConsumidos = 100 + (200 - 100) * rand.nextDouble(); // El consumo entre 100 y 200 kWh ,realmete debe ser mayor
                    Date fechaInicio = new Date(System.currentTimeMillis() - (rand.nextInt(365) + 1) * 24L * 60L * 60L * 1000L); // Fecha de inicio aleatoria dentro del último año
                    Date fechaFin = new Date(fechaInicio.getTime() + 30L * 24L * 60L * 60L * 1000L); // Fecha de fin 30 días después de la fecha de inicio
                    int idTarifa = rand.nextInt(3) + 1; // ID de tarifa entre 1 y 3 tambien aleatoria

                    pstmt.setInt(1, i);
                    pstmt.setDouble(2, kwhConsumidos);
                    pstmt.setDate(3, fechaInicio);
                    pstmt.setDate(4, fechaFin);
                    pstmt.setInt(5, idTarifa);
                    pstmt.executeUpdate();

                    // El mensaje que me sirve para comprobar si me funciona el metodo
                    System.out.println("Consumo insertado para persona ID " + i + ": " + kwhConsumidos + " kWh desde " + fechaInicio + " hasta " + fechaFin);
                } else {
                    System.out.println("La persona con ID " + i + " no existe.");
                }
            }

            System.out.println("Datos de Consumo insertados correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * El metodo que sirve para insertar facturas en mi base de datos
     */
    public void insertarFacturasEjemplo() {
        int numberOfRecords = 20;
        Random rand = new Random();

        for (int i = 1; i <= numberOfRecords; i++) {
            Date fechaEmision = new Date(System.currentTimeMillis() - (rand.nextInt(30) + 1) * 24 * 60 * 60 * 1000L); // Fecha aleatoria dentro de los últimos 30 días
            Date fechaVencimiento = new Date(fechaEmision.getTime() + 30L * 24 * 60 * 60 * 1000); // 30 días después de la fecha de emisión
            double montoTotal = calcularMontoTotal(i, fechaEmision, fechaVencimiento);

            Factura factura = new Factura();
            factura.setIdPersona(i);
            factura.setFechaEmision(fechaEmision);
            factura.setFechaVencimiento(fechaVencimiento);
            factura.setMontoTotal(montoTotal);

            insertFactura(factura);
        }
        System.out.println("Facturas de ejemplo insertadas correctamente.");
    }

    /**
     * El metodo que me sirve para generar un nie valido para meterlo en  mi metodo insertar personas
     * @return
     */
    private String generarNie() {
        Random rand = new Random();
        char letraInicial = LETRAS.charAt(rand.nextInt(LETRAS.length()));
        char letraFinal = LETRAS.charAt(rand.nextInt(LETRAS.length()));
        String numeros = String.format("%07d", rand.nextInt(10000000));
        return String.valueOf(letraInicial) + numeros + letraFinal;
    }

    /**
     * El metodo que me sirve para generar un telefono siguendo el patron de 9 cifras que le he indicado
     * @return
     */
    private String generarTelefono() {
        Random rand = new Random();
        StringBuilder telefono = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            telefono.append(rand.nextInt(10));
        }
        return telefono.toString();
    }

    /**
     * Este metodo me sirve para asegurarme que siempre voy a poner id con numero actualizado buscando el ultimo en mi base de datos
     * @return
     */
    public int getLastInsertedPersonaId() {
        String sql = "SELECT LAST_INSERT_ID() AS id";
        int id = 0;

        try (Connection connection = DriverManager.getConnection(url + dbName, user, password);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

    /**
     * El metodo que sirve para actualizar los datos en mi base de datos ,maneja sobre la tabla persona
     * @param id
     * @param nombre
     * @param apellido
     * @param nie
     * @param direccion
     * @param telefono
     * @param email
     * @param tipo
     */
    public void updatePersona(int id, String nombre, String apellido, String nie, String direccion, String telefono, String email, String tipo) {
        String sql = "UPDATE Persona SET nombre = ?, apellido = ?, nie = ?, direccion = ?, telefono = ?, email = ?, tipo = ? WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(url + dbName, user, password);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, apellido);
            pstmt.setString(3, nie);
            pstmt.setString(4, direccion);
            pstmt.setString(5, telefono);
            pstmt.setString(6, email);
            pstmt.setString(7, tipo);
            pstmt.setInt(8, id);
            pstmt.executeUpdate();
            System.out.println("Persona actualizada correctamente con ID: " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Cuando se actualiza tabla persona se actualia tambien y el contrato
     * @param idPersona
     * @param idTarifa
     * @param fechaInicio
     * @param fechaFin
     * @param tipoContrato
     */
    public void updateContrato(int idPersona, int idTarifa, Date fechaInicio, Date fechaFin, String tipoContrato) {
        String sql = "UPDATE Contrato SET id_tarifa = ?, fecha_inicio = ?, fecha_fin = ?, tipo_contrato = ? WHERE id_persona = ?";

        try (Connection connection = DriverManager.getConnection(url + dbName, user, password);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, idTarifa);
            pstmt.setDate(2, fechaInicio);
            pstmt.setDate(3, fechaFin);
            pstmt.setString(4, tipoContrato);
            pstmt.setInt(5, idPersona);
            pstmt.executeUpdate();
            System.out.println("Contrato actualizado correctamente para idPersona: " + idPersona);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Este metodo me sirve cuando debo buscar persona para editarla
     * @param id
     * @return
     */
    public Persona getPersonaById(int id) {
        String sql = "SELECT * FROM Persona WHERE id = ?";
        Persona persona = null;

        try (Connection connection = DriverManager.getConnection(url + dbName, user, password);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String tipo = rs.getString("tipo");
                if ("CLIENTE".equals(tipo)) {
                    persona = new Cliente(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getString("nie"),
                            rs.getString("direccion"),
                            rs.getString("telefono"),
                            rs.getString("email"),
                            tipo,
                            new ArrayList<>(), // Contratos serán cargados posteriormente cuando lo tenga
                            new ArrayList<>(), // Consumos serán cargados posteriormente cuando lo tenga
                            rs.getInt("id") // El ID del cliente es el mismo ID de persona
                    );
                } else if ("ADMIN".equals(tipo)) {
                    persona = new Admin(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getString("nie"),
                            rs.getString("direccion"),
                            rs.getString("telefono"),
                            rs.getString("email"),
                            tipo,
                            rs.getInt("id") // El ID del admin es el mismo ID de persona
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return persona;
    }

    /**
     * El metodo que me consigue el contrato buscandolo en mi base da datos por id  persona
     * @param idPersona
     * @return
     */
    public Contrato getContratoByIdPersona(int idPersona) {
        String sql = "SELECT * FROM Contrato WHERE id_persona = ?";
        Contrato contrato = null;

        try (Connection connection = DriverManager.getConnection(url + dbName, user, password);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, idPersona);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                contrato = new Contrato(
                        rs.getInt("id_contrato"),
                        rs.getInt("id_persona"),
                        rs.getInt("id_tarifa"),
                        rs.getDate("fecha_inicio"),
                        rs.getDate("fecha_fin"),
                        rs.getString("tipo_contrato")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contrato;
    }

    /**
     * El metodo que me sirve insertar una nueva factura en mi base de datos
     * @param factura
     * @return
     */
    public int insertFactura(Factura factura) {
        String sql = "INSERT INTO Factura (id_persona, fecha_emision, fecha_vencimiento, monto_total) VALUES (?, ?, ?, ?)";
        int facturaId = 0;

        try (Connection connection = DriverManager.getConnection(url + dbName, user, password);
             PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, factura.getIdPersona());
            pstmt.setDate(2, new java.sql.Date(factura.getFechaEmision().getTime()));
            pstmt.setDate(3, new java.sql.Date(factura.getFechaVencimiento().getTime()));
            pstmt.setDouble(4, factura.getMontoTotal());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                facturaId = rs.getInt(1);
            }
            System.out.println("Factura insertada correctamente para idPersona: " + factura.getIdPersona() + ", monto total: " + factura.getMontoTotal());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return facturaId;
    }

    /**
     * El metodo que calcula el total dentro de una factura que calcula una vez corecto y 10 veces incorecto
     * @param idPersona
     * @param fechaInicio
     * @param fechaFin
     * @return
     */
    public double calcularMontoTotal(int idPersona, Date fechaInicio, Date fechaFin) {
        double montoTotal = 0.0;
        final double IVA = 0.05;

        String sql = "SELECT c.kWConsumidos, t.costo_kwh FROM Consumo c " +
                "JOIN Tarifa t ON c.id_tarifa = t.id_tarifa " +
                "WHERE c.id_persona = ? AND c.fechaInicio >= ? AND c.fechaFin <= ?";

        try (Connection connection = DriverManager.getConnection(url + dbName, user, password);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, idPersona);
            pstmt.setDate(2, fechaInicio);
            pstmt.setDate(3, fechaFin);

            System.out.println("Ejecutando consulta con parámetros - ID Persona: " + idPersona + ", Fecha Inicio: " + fechaInicio + ", Fecha Fin: " + fechaFin);

            ResultSet rs = pstmt.executeQuery();

            if (!rs.isBeforeFirst()) {
                System.out.println("No se encontraron registros de consumo para los parámetros dados.");
            }

            while (rs.next()) {
                double kWConsumidos = rs.getDouble("kWConsumidos");
                double costoKwh = rs.getDouble("costo_kwh");
                double subtotal = kWConsumidos * costoKwh;
                double subtotalConIva = subtotal * (1 + IVA);
                montoTotal += subtotalConIva;


                System.out.println("ID Persona: " + idPersona + ", kWConsumidos: " + kWConsumidos + ", costoKwh: " + costoKwh + ", Subtotal: " + subtotal + ", Subtotal con IVA: " + subtotalConIva);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Monto Total calculado para ID Persona " + idPersona + ": " + montoTotal);
        return montoTotal;
    }
    /**
     * Metodo que me coge todos los clientes de mis base de datos
     * @return
     */
    public List<Cliente> getAllClientes() {
        String sql = "SELECT * FROM Persona WHERE tipo = 'CLIENTE'";
        List<Cliente> clientes = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url + dbName, user, password);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("nie"),
                        rs.getString("direccion"),
                        rs.getString("telefono"),
                        rs.getString("email"),
                        rs.getString("tipo"),
                        new ArrayList<>(),
                        new ArrayList<>(),
                        rs.getInt("id")
                );
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }

    /**
     * El metodo getAllConsumos me sirve para manejo de la vista mostrar todos los consumos antes de facturar
     * @return
     */
    public List<Consumo> getAllConsumos() {
        List<Consumo> consumos = new ArrayList<>();
        String sql = "SELECT c.*, t.descripcion, t.costo_kwh FROM Consumo c " +
                "JOIN Tarifa t ON c.id_tarifa = t.id_tarifa";

        try (Connection connection = DriverManager.getConnection(url + dbName, user, password);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Consumo consumo = new Consumo();
                consumo.setId_consumo(rs.getInt("id_consumo"));
                consumo.setId_persona(rs.getInt("id_persona"));
                consumo.setkWConsumidos(rs.getDouble("kWConsumidos"));
                consumo.setFechaInicio(rs.getDate("fechaInicio")); // Obtener fechaInicio
                consumo.setFechaFin(rs.getDate("fechaFin")); // Obtener fechaFin

                Tarifa tarifa = new Tarifa();
                tarifa.setIdTarifa(rs.getInt("id_tarifa"));
                tarifa.setDescripcion(rs.getString("descripcion"));
                tarifa.setCostoKwh(rs.getDouble("costo_kwh"));

                consumo.setTarifa(tarifa);
                consumos.add(consumo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return consumos;
    }

    /**
     * Este metodo sirve cuando una persona de tipo cliente busca su consumo en la base de datos
     * @param idPersona
     * @param fechaInicio
     * @param fechaFin
     * @return
     */
    public List<Consumo> getConsumosByIdPersonaAndDateRange(int idPersona, Date fechaInicio, Date fechaFin) {
        List<Consumo> consumos = new ArrayList<>();
        String sql = "SELECT c.*, t.descripcion, t.costo_kwh FROM Consumo c " +
                "JOIN Tarifa t ON c.id_tarifa = t.id_tarifa " +
                "WHERE c.id_persona = ? AND c.fechaInicio >= ? AND c.fechaFin <= ?";

        try (Connection connection = DriverManager.getConnection(url + dbName, user, password);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, idPersona);
            pstmt.setDate(2, fechaInicio);
            pstmt.setDate(3, fechaFin);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Consumo consumo = new Consumo();
                consumo.setId_consumo(rs.getInt("id_consumo"));
                consumo.setId_persona(rs.getInt("id_persona"));
                consumo.setkWConsumidos(rs.getDouble("kWConsumidos"));
                consumo.setFechaInicio(rs.getDate("fechaInicio")); // Usar fechaInicio
                consumo.setFechaFin(rs.getDate("fechaFin")); // Usar fechaFin

                Tarifa tarifa = new Tarifa();
                tarifa.setIdTarifa(rs.getInt("id_tarifa"));
                tarifa.setDescripcion(rs.getString("descripcion"));
                tarifa.setCostoKwh(rs.getDouble("costo_kwh"));

                consumo.setTarifa(tarifa);
                consumos.add(consumo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return consumos;
    }

    /**
     * Este metodo me sirve tambien encontarr en mi base de datos persona que contiene este nie indicado del usario
     * @param nie
     * @return
     */
    public Persona getPersonaByNie(String nie) {
        String sql = "SELECT * FROM Persona WHERE nie = ?";
        Persona persona = null;

        try (Connection connection = DriverManager.getConnection(url + dbName, user, password);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, nie);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String tipo = rs.getString("tipo");
                if ("CLIENTE".equals(tipo)) {
                    persona = new Cliente(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getString("nie"),
                            rs.getString("direccion"),
                            rs.getString("telefono"),
                            rs.getString("email"),
                            tipo,
                            new ArrayList<>(),
                            new ArrayList<>(),
                            rs.getInt("id")
                    );
                } else if ("ADMIN".equals(tipo)) {
                    persona = new Admin(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getString("nie"),
                            rs.getString("direccion"),
                            rs.getString("telefono"),
                            rs.getString("email"),
                            tipo,
                            rs.getInt("id")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return persona;
    }

    /**
     * El metodo que me borra persona ,y se asegura de borrar todos los datos
     * @param idPersona
     */
    public void deleteContratoByPersonaId(int idPersona) {
        String sql = "DELETE FROM Contrato WHERE id_persona = ?";

        try (Connection connection = DriverManager.getConnection(url + dbName, user, password);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, idPersona);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * El metodo que me borra el consumo de la persona
     * @param idPersona
     */
    public void deleteConsumoByPersonaId(int idPersona) {
        String sql = "DELETE FROM Consumo WHERE id_persona = ?";

        try (Connection connection = DriverManager.getConnection(url + dbName, user, password);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, idPersona);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * El metodo que sirve borrar persona por su id
     * @param id
     */
    public void deletePersonaById(int id) {
        String sql = "DELETE FROM Persona WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(url + dbName, user, password);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * El metodo que me borrar la facturas de la persona con este id
     * @param idPersona
     */
    public void deleteFacturaByPersonaId(int idPersona) {
        String sql = "DELETE FROM Factura WHERE id_persona = ?";

        try (Connection connection = DriverManager.getConnection(url + dbName, user, password);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, idPersona);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * El metodo que genera facturas por persona ,es decir su propia factura
     * @param idPersona
     * @return
     */
    public List<Factura> getFacturasByIdPersona(int idPersona) {
        String sql = "SELECT * FROM Factura WHERE id_persona = ?";
        List<Factura> facturas = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url + dbName, user, password);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, idPersona);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Factura factura = new Factura(
                        rs.getInt("id_factura"),
                        rs.getInt("id_persona"),
                        rs.getDate("fecha_emision"),
                        rs.getDate("fecha_vencimiento"),
                        rs.getDouble("monto_total")
                );
                facturas.add(factura);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return facturas;
    }
}












































































