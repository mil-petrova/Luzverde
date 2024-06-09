package org.example.luzverde;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.sql.Date;
import java.time.LocalDate;

import java.util.List;

/**
 * @author Milena Petrova
 * @version 1.0
 * Esta clase es la clase vista ,emplementando un interface simple para manejar los datos de personas que han elegido
 * la empresa LuzVerde para facturar sus kilovatios gastados en luz
 * He intentado seguir el modelo MVC
 */

public class LuzApp extends Application {
    LuzController cc;
    LuzModel modelo = new LuzModel();

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        /**
         * Aqui llamo todos los metodos que me crean las base de datos y me ingresen la informacion
         */
        modelo.createDatabase();
        modelo.createPersonaTable();
        modelo.createTarifaTable();
        modelo.createConsumoTable();
        modelo.createContratoTable();
        modelo.createFacturaTable();
        modelo.insertTarifas();
        modelo.insertarMultiplesPersonasConContratos();
        modelo.insertarDatosConsumo();
        modelo.insertarFacturasEjemplo();
        cc = new LuzController(modelo, this);
        stage.setTitle("LuzVerde");
        stage.setWidth(800);
        stage.setHeight(600);

        Image imagenFondo = new Image("file:src/main/resources/oip.jpg");
        ImageView imageView = new ImageView(imagenFondo);
        imageView.fitWidthProperty().bind(stage.widthProperty());
        imageView.fitHeightProperty().bind(stage.heightProperty());

        // El menu
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Opciones");
        MenuItem menuIt1 = new MenuItem("Informacion");
        MenuItem menuIt2 = new MenuItem("Tarifas");
        MenuItem menuIt3 = new MenuItem("Salir del programa");
        menu.getItems().addAll(menuIt1, menuIt2, menuIt3);
        Menu menu1 = new Menu("Calculos de tarifas");
        MenuItem menuIt4 = new MenuItem("¿Como calcular la tarifa de tu hogar?");
        MenuItem menuIt5 = new MenuItem("¿Que potencia elegir?");
        menu1.getItems().addAll(menuIt4, menuIt5);
        menuBar.getMenus().addAll(menu, menu1);
        menuIt1.setOnAction(e -> displayInfoWindow());
        menuIt2.setOnAction(e -> displayTarifasWindow());
        menuIt3.setOnAction(e -> {
            Stage primaryStage = (Stage) menuBar.getScene().getWindow();
            primaryStage.close();
        });
        menuIt4.setOnAction(e -> displayCalculoTarifaWindow());
        menuIt5.setOnAction(e -> displayPotenciaWindow());
        // Botones
        VBox vb = new VBox();
        Button btnAdmin = new Button("Administrador");
        Button btnCliente = new Button("Cliente");
        btnAdmin.setPrefWidth(200);
        btnCliente.setPrefWidth(200);
        btnAdmin.setStyle("-fx-font-size: 16px; -fx-text-fill: black;");
        btnCliente.setStyle("-fx-font-size: 16px; -fx-text-fill: black;");
        vb.getChildren().addAll(btnAdmin, btnCliente);
        btnAdmin.setOnAction(e -> showLoginScene(stage));
        btnCliente.setOnAction(e -> showClientNIEWindow( stage));
        vb.setAlignment(Pos.CENTER);
        vb.setSpacing(20);

        // Contenedor principal
        StackPane mil = new StackPane();
        mil.getChildren().addAll(imageView, vb);
        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(menuBar);
        mainLayout.setCenter(mil);


        Scene escena = new Scene(mainLayout, 800, 800);
        stage.setScene(escena);
        stage.show();
    }

    private void showLoginScene(Stage stage) {
        // Creo campos de texto para usuario y contraseña
        Label userLabel = new Label("Usuario:");
        TextField userField = new TextField();
        userField.setPrefWidth(200);
        Label passwordLabel = new Label("Contraseña:");
        PasswordField passwordField = new PasswordField();
        passwordField.setPrefWidth(200);
        Button loginButton = new Button("Iniciar sesión");

        // Acción del botón de inicio de sesión
        loginButton.setOnAction(e -> {
            String username = userField.getText();
            String password = passwordField.getText();
            if ("admin".equals(username) && "admin".equals(password)) {

                showAlert(Alert.AlertType.INFORMATION, "Login Exitoso", "Bienvenido, Toca trabajar!");
                showActionScene(stage);
            } else {

                showAlert(Alert.AlertType.ERROR, "Error de Login", "No tienes permiso para este tipo de acciones");
            }
        });


        VBox loginLayout = new VBox(10, userLabel, userField, passwordLabel, passwordField, loginButton);
        loginLayout.setAlignment(Pos.CENTER);
        loginLayout.setPadding(new Insets(20));
        loginLayout.setStyle("-fx-background-color: #226c6e;");
        Scene loginScene = new Scene(loginLayout, 800, 600);
        stage.setScene(loginScene);
    }

    /**
     * El metodo que me consigue sacar la alertas,cuando algo no es corecto
     * @param alertType
     * @param title
     * @param message
     */
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * El metodo que se dispara cuendo tenemos usario que elije admin
     * @param stage
     */
    private void showActionScene(Stage stage) {
        // Botones de acción
        Button btnCreateClient = new Button("Crear Nuevo Cliente");
        Button btnEditClient = new Button("Editar Cliente");
        Button btnCreateInvoice = new Button("Hacer Factura");
        Button btnDeleteClient = new Button("Borrar Cliente");
        Button btnSaveToPrint = new Button("Guarda en documento ");
        Button btnBackToMain = new Button("Volver al Inicio");
        btnCreateClient.setPrefWidth(200);
        btnCreateClient.setOnAction(e -> showCreateClientScene(stage));
        btnEditClient.setPrefWidth(200);
        btnEditClient.setOnAction(e -> showEditClientScene(stage));
        btnCreateInvoice.setPrefWidth(200);
        btnCreateInvoice.setOnAction(e -> showGenerarFacturasWindow(stage));
        btnDeleteClient.setPrefWidth(200);
        btnDeleteClient.setOnAction(e -> showDeleteClientWindow(stage));
        btnBackToMain.setPrefWidth(200);
        btnSaveToPrint.setPrefWidth(200);
        btnSaveToPrint.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar Facturas");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            File file = fileChooser.showSaveDialog(stage);
            if (file != null) {
                List<Factura> facturas = cc.generarFacturas(Date.valueOf(LocalDate.now().minusDays(30)), Date.valueOf(LocalDate.now()));
                cc.saveFacturasToText(facturas, file);
            }
        });

        btnBackToMain.setOnAction(e -> {
            try {
                start(stage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        VBox actionLayout = new VBox(20, btnCreateClient, btnEditClient, btnCreateInvoice, btnDeleteClient, btnSaveToPrint, btnBackToMain);
        actionLayout.setAlignment(Pos.CENTER);
        actionLayout.setPadding(new Insets(20));
        actionLayout.setStyle("-fx-background-color: #904d95;");
        Scene actionScene = new Scene(actionLayout, 800, 600);
        stage.setScene(actionScene);
    }


    private void displayInfoWindow() {
        Stage window = new Stage();
        window.setTitle("Información de Luz Verde");

        Label titleLabel = new Label("Empresa: Luz Verde");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #9f59a3;");

        Text dateText = new Text("Fecha de formación: Septiembre 2023\n");
        dateText.setStyle("-fx-font-size: 16px;");

        Text descriptionText = new Text("Descripción: Luz Verde es una empresa dedicada a la distribución de energía eléctrica, formada en septiembre de 2023. Nuestro objetivo es proporcionar energía sostenible y accesible para todos. Trabajamos con las tecnologías más avanzadas para asegurar un servicio eficiente y respetuoso con el medio ambiente.\n\n");
        descriptionText.setStyle("-fx-font-size: 16px;");

        Text missionText = new Text("Misión: Ofrecer soluciones energéticas innovadoras y sostenibles para hogares y empresas, contribuyendo al desarrollo de una sociedad más verde y responsable.\n\n");
        missionText.setStyle("-fx-font-size: 16px;");

        Text valuesText = new Text("Valores: \n- Sostenibilidad: Comprometidos con el medio ambiente.\n- Innovación: Implementamos las últimas tecnologías.\n- Accesibilidad: Energía para todos.\n- Transparencia: Comunicación abierta y honesta.\n");
        valuesText.setStyle("-fx-font-size: 16px;");

        TextFlow textFlow = new TextFlow(dateText, descriptionText, missionText, valuesText);
        textFlow.setTextAlignment(TextAlignment.LEFT);
        textFlow.setPadding(new Insets(10));

        VBox layout = new VBox(10);
        layout.getChildren().addAll(titleLabel, textFlow);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 700, 700);
        window.setScene(scene);
        window.show();
    }

    private void displayTarifasWindow() {
        Stage window = new Stage();
        window.setTitle("Tipos de Tarifas - Luz Verde");

        Label titleLabel = new Label("Tipos de Tarifas");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #61dcdf;");


        VBox tarifasLayout = new VBox(10);
        tarifasLayout.setAlignment(Pos.CENTER);
        tarifasLayout.setPadding(new Insets(20));


        String[] tarifas = {
                " Básica: Energía a bajo costo para hogares ***0.2914***.",
                " Nocturna: Tarifas reducidas durante la noche *** 0.10914***.",
                " Solar: Beneficios por usar energía solar. ***0.3914*** ",
                " Empresarial: Tarifas especiales para negocios.***0.1814***",
                " Verde: Energía 100% renovable ***0.09***.",
                " Estudiante: Descuentos para estudiantes.***0.22***"
        };


        String[] iconPaths = {
                "file:src/main/resources/bolt.png",
                "file:src/main/resources/efficiency.png",
                "file:src/main/resources/energia.png",
                "file:src/main/resources/flash.png",
                "file:src/main/resources/power.png",
                "file:src/main/resources/renewable-energy.png"
        };

        for (int i = 0; i < tarifas.length; i++) {
            HBox tarifaBox = new HBox(10);
            tarifaBox.setAlignment(Pos.CENTER_LEFT);

            ImageView icon = new ImageView(new Image(iconPaths[i]));
            icon.setFitWidth(50);
            icon.setFitHeight(50);

            Label tarifaLabel = new Label(tarifas[i]);
            tarifaLabel.setStyle("-fx-font-size: 16px;");

            tarifaBox.getChildren().addAll(icon, tarifaLabel);
            tarifasLayout.getChildren().add(tarifaBox);
        }

        VBox layout = new VBox(20);
        layout.getChildren().addAll(titleLabel, tarifasLayout);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        // Imagen de fondo
        ImageView backgroundImage = new ImageView(new Image("file:src/main/resources/fondo.jpg"));
        backgroundImage.fitWidthProperty().bind(window.widthProperty());
        backgroundImage.fitHeightProperty().bind(window.heightProperty());

        StackPane root = new StackPane();
        root.getChildren().addAll(backgroundImage, layout);

        Scene scene = new Scene(root, 600, 500);
        window.setScene(scene);
        window.show();
    }

    private void displayCalculoTarifaWindow() {
        Stage window = new Stage();
        window.setTitle("Cálculo de Tarifa - Luz Verde");

        Label titleLabel = new Label("¿Cómo calcular la tarifa de tu hogar?");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #0c0c0c;");

        Text descriptionText = new Text("Para calcular la tarifa de tu hogar, debes conocer la potencia contratada y el consumo de energía en kilovatios hora (kWh). Aquí te mostramos ejemplos de cómo se calculan las diferentes tarifas:\n");
        descriptionText.setStyle("-fx-font-size: 16px;");

        String[] tarifasEjemplos = {
                "Tarifa Básica:   Potencia Contratada * 0.2914 * Consumo * IVA\n",
                "Tarifa Nocturna: Potencia Contratada * 0.10914 * Consumo * IVA\n",
                "Tarifa Solar:    Potencia Contratada * 0.3914 * Consumo * IVA\n",
                "Tarifa Empresarial: Potencia Contratada * 0.1814 * Consumo * IVA\n",
                "Tarifa Verde: PC: Potencia Contratada * 0.2614 * Consumo * IVA\n",
                "Tarifa Estudiante: Potencia Contratada PC * 0.22 * Consumo * IVA\n"
        };

        VBox ejemplosLayout = new VBox(10);
        for (String ejemplo : tarifasEjemplos) {
            Label ejemploLabel = new Label(ejemplo);
            ejemploLabel.setStyle("-fx-font-size: 16px;");
            ejemplosLayout.getChildren().add(ejemploLabel);
        }

        VBox layout = new VBox(20);
        layout.getChildren().addAll(titleLabel, new TextFlow(descriptionText), ejemplosLayout);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));


        ImageView backgroundImage = new ImageView(new Image("file:src/main/resources/calculo.jpg"));
        backgroundImage.fitWidthProperty().bind(window.widthProperty());
        backgroundImage.fitHeightProperty().bind(window.heightProperty());

        StackPane root = new StackPane();
        root.getChildren().addAll(backgroundImage, layout);

        Scene scene = new Scene(root, 800, 700);
        window.setScene(scene);
        window.show();
    }

    private void displayPotenciaWindow() {
        Stage window = new Stage();
        window.setTitle("¿Qué potencia elegir? - Luz Verde");

        Label titleLabel = new Label("¿Qué potencia elegir?");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #4095a5;");

        Text descriptionText = new Text("Elegir la potencia adecuada para tu hogar o negocio es crucial para optimizar el consumo energético y reducir costos. Aquí te mostramos diferentes tipos de potencia y para qué sirve cada una:\n\n");
        descriptionText.setStyle("-fx-font-size: 16px;");

        String[] potenciasEjemplos = {
                "Potencia 3 kW: Ideal para pequeños apartamentos o estudios con bajo consumo eléctrico.",
                "Potencia 4.6 kW: Recomendada para pisos de tamaño medio con electrodomésticos básicos y algunas comodidades adicionales.",
                "Potencia 5.75 kW: Adecuada para hogares grandes con varios electrodomésticos y sistemas de climatización.",
                "Potencia 6.9 kW: Perfecta para viviendas con alto consumo energético, incluyendo sistemas de calefacción o aire acondicionado centralizados.",
                "Potencia 10 kW:  Necesaria para negocios pequeños o grandes viviendas con múltiples aparatos eléctricos funcionando simultáneamente.",
                "Potencia superior a 10 kW:  Usada generalmente en grandes empresas, industrias o viviendas con instalaciones eléctricas especiales."
        };

        VBox ejemplosLayout = new VBox(10);
        for (String ejemplo : potenciasEjemplos) {
            Label ejemploLabel = new Label(ejemplo);
            ejemploLabel.setStyle("-fx-font-size: 16px;");
            ejemplosLayout.getChildren().add(ejemploLabel);
        }

        VBox layout = new VBox(20);
        layout.getChildren().addAll(titleLabel, new TextFlow(descriptionText), ejemplosLayout);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));


        ImageView backgroundImage = new ImageView(new Image("file:src/main/resources/potencia_background.jpg"));
        backgroundImage.fitWidthProperty().bind(window.widthProperty());
        backgroundImage.fitHeightProperty().bind(window.heightProperty());

        StackPane root = new StackPane();
        root.getChildren().addAll(backgroundImage, layout);

        Scene scene = new Scene(root, 600, 500);
        window.setScene(scene);
        window.show();
    }

    private void showCreateClientScene(Stage stage) {
        // Crear campos de texto para los datos del cliente
        TextField nombreField = new TextField();
        nombreField.setPromptText("Nombre");
        TextField apellidoField = new TextField();
        apellidoField.setPromptText("Apellido");
        TextField nieField = new TextField();
        nieField.setPromptText("NIE");
        TextField direccionField = new TextField();
        direccionField.setPromptText("Direccion");
        TextField telefonoField = new TextField();
        telefonoField.setPromptText("Telefono");
        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        // Campos adicionales para contrato
        TextField idTarifaField = new TextField();
        idTarifaField.setPromptText("ID Tarifa");
        DatePicker fechaInicioField = new DatePicker();
        fechaInicioField.setPromptText("Fecha de Inicio");
        DatePicker fechaFinField = new DatePicker();
        fechaFinField.setPromptText("Fecha de Fin");
        TextField tipoContratoField = new TextField();
        tipoContratoField.setPromptText("Tipo de Contrato");


        Button saveButton = new Button("Crear Cliente");
        saveButton.setOnAction(e -> {
            try {
                String nombre = nombreField.getText();
                String apellido = apellidoField.getText();
                String nie = nieField.getText();
                String direccion = direccionField.getText();
                String telefono = telefonoField.getText();
                String email = emailField.getText();
                int idTarifa = Integer.parseInt(idTarifaField.getText());
                Date fechaInicio = Date.valueOf(fechaInicioField.getValue());
                Date fechaFin = Date.valueOf(fechaFinField.getValue());
                String tipoContrato = tipoContratoField.getText();

                // Validar los campos de NIE y Teléfono
                if (!validarCampos(nie, telefono)) {
                    return;
                }

                // Llamar al método del controlador para guardar el cliente
                cc.saveCliente(nombre, apellido, nie, direccion, telefono, email, idTarifa, fechaInicio, fechaFin, tipoContrato);

                showAlert(Alert.AlertType.INFORMATION, "Cliente Guardado", "El nuevo cliente ha sido guardado exitosamente.");
                showActionScene(stage);
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Error de Formato", "Por favor, ingrese valores válidos en todos los campos.");
            }
        });

        VBox formLayout = new VBox(10, nombreField, apellidoField, nieField, direccionField, telefonoField, emailField, idTarifaField, fechaInicioField, fechaFinField, tipoContratoField, saveButton);
        formLayout.setAlignment(Pos.CENTER);
        formLayout.setPadding(new Insets(20));
        formLayout.setStyle("-fx-background-color: #d4e157;");

        Scene createClientScene = new Scene(formLayout, 800, 600);
        stage.setScene(createClientScene);
    }

    private boolean validarCampos(String nie, String telefono) {

        String niePattern = "^[A-Z]\\d{7}[A-Z]$";

        String telefonoPattern = "^\\d{9}$";

        if (!nie.matches(niePattern)) {
            showAlert(Alert.AlertType.ERROR, "El NIE no tiene el formato corecto", "El NIE no es válido. Debe comenzar con una letra mayúscula, seguido de 7 dígitos y terminar con una letra mayúscula.");
            return false;
        }

        if (!telefono.matches(telefonoPattern)) {
            showAlert(Alert.AlertType.ERROR, "El teleono no tiene el formato corecto", "El número de teléfono no es válido. Debe contener 9 dígitos.");
            return false;
        }

        return true;
    }

    private void showEditClientScene(Stage stage) {
        // Crear campos de texto para los datos del cliente
        TextField idField = new TextField();
        idField.setPromptText("ID Cliente");

        Button searchButton = new Button("Buscar Cliente");
        Button saveButton = new Button("Guardar Cambios");
        saveButton.setDisable(true);

        // Crear campos de texto para mostrar y editar los datos del cliente
        TextField nombreField = new TextField();
        nombreField.setPromptText("Nombre");
        TextField apellidoField = new TextField();
        apellidoField.setPromptText("Apellido");
        TextField nieField = new TextField();
        nieField.setPromptText("NIE");
        TextField direccionField = new TextField();
        direccionField.setPromptText("Direccion");
        TextField telefonoField = new TextField();
        telefonoField.setPromptText("Telefono");
        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        TextField tipoField = new TextField();
        tipoField.setPromptText("Tipo");

        // Campos adicionales para contrato
        TextField idTarifaField = new TextField();
        idTarifaField.setPromptText("ID Tarifa");
        DatePicker fechaInicioField = new DatePicker();
        fechaInicioField.setPromptText("Fecha de Inicio");
        DatePicker fechaFinField = new DatePicker();
        fechaFinField.setPromptText("Fecha de Fin");
        TextField tipoContratoField = new TextField();
        tipoContratoField.setPromptText("Tipo de Contrato");

        // Botón para volver atrás
        Button volverButton = new Button("Volver Atrás");
        volverButton.setOnAction(e -> showClientActionsWindow(stage, Integer.parseInt(idField.getText())));

        searchButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                Persona persona = cc.getPersona(id);
                Contrato contrato = cc.getContrato(id);

                if (persona != null) {
                    nombreField.setText(persona.getNombre());
                    apellidoField.setText(persona.getApellido());
                    nieField.setText(persona.getNie());
                    direccionField.setText(persona.getDireccion());
                    telefonoField.setText(persona.getTelefono());
                    emailField.setText(persona.getEmail());
                    tipoField.setText(persona.getTipo());

                    if (contrato != null) {
                        idTarifaField.setText(String.valueOf(contrato.getId_tarifa()));
                        if (contrato.getFechaInicio() != null) {
                            fechaInicioField.setValue(convertToLocalDate(contrato.getFechaInicio()));
                        }
                        if (contrato.getFechaFin() != null) {
                            fechaFinField.setValue(convertToLocalDate(contrato.getFechaFin()));
                        }
                        tipoContratoField.setText(contrato.getTipoContrato());
                    }

                    saveButton.setDisable(false);
                } else {
                    showAlert(Alert.AlertType.ERROR, "Cliente no encontrado", "No se encontró ningún cliente con el ID proporcionado.");
                }
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Error de Formato", "Por favor, ingrese un ID válido.");
            }
        });

        // Acción del botón guardar cambios
        saveButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String nombre = nombreField.getText();
                String apellido = apellidoField.getText();
                String nie = nieField.getText();
                String direccion = direccionField.getText();
                String telefono = telefonoField.getText();
                String email = emailField.getText();
                String tipo = tipoField.getText();

                int idTarifa = Integer.parseInt(idTarifaField.getText());
                java.sql.Date fechaInicio = java.sql.Date.valueOf(fechaInicioField.getValue());
                java.sql.Date fechaFin = java.sql.Date.valueOf(fechaFinField.getValue());
                String tipoContrato = tipoContratoField.getText();

                // Validar los campos de NIE y Teléfono
                if (!validarCampos(nie, telefono)) {
                    return;
                }

                // Llamar al método del controlador para actualizar el cliente y el contrato
                cc.updateCliente(id, nombre, apellido, nie, direccion, telefono, email, tipo, idTarifa, fechaInicio, fechaFin, tipoContrato);

                showAlert(Alert.AlertType.INFORMATION, "Cliente Actualizado", "Los datos del cliente han sido actualizados exitosamente.");
                showClientActionsWindow(stage, id);
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Error de Formato", "Por favor, ingrese valores válidos en todos los campos.");
            }
        });

        VBox formLayout = new VBox(10, idField, searchButton, nombreField, apellidoField, nieField, direccionField, telefonoField, emailField, tipoField, idTarifaField, fechaInicioField, fechaFinField, tipoContratoField, saveButton, volverButton);
        formLayout.setAlignment(Pos.CENTER);
        formLayout.setPadding(new Insets(20));
        formLayout.setStyle("-fx-background-color: #f0ad4e;");

        Scene editClientScene = new Scene(formLayout, 800, 600);
        stage.setScene(editClientScene);
    }

    private LocalDate convertToLocalDate(java.sql.Date dateToConvert) {
        return dateToConvert.toLocalDate();
    }
    private void showGenerarFacturasWindow(Stage stage) {
        Stage window = new Stage();
        window.setTitle("Generar Facturas");

        DatePicker fechaInicioField = new DatePicker();
        DatePicker fechaFinField = new DatePicker();
        Button generarButton = new Button("Generar Facturas");

        generarButton.setOnAction(e -> {
            LocalDate fechaInicio = fechaInicioField.getValue();
            LocalDate fechaFin = fechaFinField.getValue();
            if (fechaInicio != null && fechaFin != null) {
                cc.handleGenerarFacturas(java.sql.Date.valueOf(fechaInicio), java.sql.Date.valueOf(fechaFin));
                mostrarTodosLosConsumos(); // Llama al método para mostrar todos los consumos
                window.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Por favor, selecciona ambas fechas.");
                alert.showAndWait();
            }
        });

        VBox layout = new VBox(10, new Label("Fecha Inicio:"), fechaInicioField, new Label("Fecha Fin:"), fechaFinField, generarButton);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 400, 300);
        window.setScene(scene);
        window.show();
    }

    void mostrarFacturas(List<Factura> facturas, Date fechaInicio, Date fechaFin) {
        Stage window = new Stage();
        window.setTitle("Facturas Generadas");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER_LEFT);

        for (Factura factura : facturas) {
            VBox facturaBox = new VBox(5);
            facturaBox.setStyle("-fx-border-color: black; -fx-padding: 10;");

            Label idFacturaLabel = new Label("ID Factura: " + factura.getIdFactura());
            Label idPersonaLabel = new Label("ID Persona: " + factura.getIdPersona());
            Label fechaEmisionLabel = new Label("Fecha Emisión: " + factura.getFechaEmision());
            Label fechaVencimientoLabel = new Label("Fecha Vencimiento: " + factura.getFechaVencimiento());
            Label montoTotalLabel = new Label("Monto Total: " + factura.getMontoTotal() + " €");

            List<Consumo> consumos = modelo.getConsumosByIdPersonaAndDateRange(factura.getIdPersona(), fechaInicio, fechaFin);
            VBox consumosBox = new VBox(5);
            for (Consumo consumo : consumos) {
                Label consumoLabel = new Label("Consumo: " + consumo.getkWConsumidos() + " kWh, Tarifa: " + consumo.getTarifa().getCostoKwh() + " €/kWh, Fecha Inicio: " + consumo.getFechaInicio() + ", Fecha Fin: " + consumo.getFechaFin());
                consumosBox.getChildren().add(consumoLabel);
            }

            facturaBox.getChildren().addAll(idFacturaLabel, idPersonaLabel, fechaEmisionLabel, fechaVencimientoLabel, montoTotalLabel, consumosBox);
            layout.getChildren().add(facturaBox);
        }

        Button volverButton = new Button("Volver atrás");
        volverButton.setOnAction(e -> window.close());

        VBox mainLayout = new VBox(10, layout, volverButton);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.CENTER);

        ScrollPane scrollPane = new ScrollPane(mainLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        Scene scene = new Scene(scrollPane, 400, 400);
        window.setScene(scene);
        window.show();
    }
    private void showDeleteClientWindow(Stage stage) {
        Stage window = new Stage();
        window.setTitle("Dar de Baja Cliente");

        Label nieLabel = new Label("NIE del Cliente:");
        TextField nieField = new TextField();
        Button deleteButton = new Button("Borrar Cliente");

        deleteButton.setOnAction(e -> {
            String nie = nieField.getText();
            if (nie != null && !nie.isEmpty()) {
                if (validarCampos(nie, "123456789")) { // Validar NIE con tu método
                    Persona persona = cc.getPersonaByNie(nie);
                    if (persona != null && persona instanceof Cliente) {
                        cc.deleteClienteByNie(nie);
                        showAlert(Alert.AlertType.INFORMATION, "Cliente Borrado", "El cliente con NIE " + nie + " ha sido borrado correctamente.");
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Error", "No se encontró un cliente con el NIE proporcionado.");
                    }
                    window.close();
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Por favor, ingrese un NIE válido.");
            }
        });

        VBox layout = new VBox(10, nieLabel, nieField, deleteButton);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 400, 200);
        window.setScene(scene);
        window.show();
    }
    private void showClientNIEWindow(Stage stage) {
        Stage window = new Stage();
        window.setTitle("Ingresar NIE y Teléfono");

        Label nieLabel = new Label("Ingrese NIE:");
        TextField nieInput = new TextField();
        Label telefonoLabel = new Label("Ingrese Teléfono:");
        TextField telefonoInput = new TextField();
        Button submitButton = new Button("Enviar");

        submitButton.setOnAction(e -> {
            String nie = nieInput.getText();
            String telefono = telefonoInput.getText();
            if (validarCampos(nie, telefono)) {
                int idPersona = obtenerIdPersona(nie, telefono);
                if (idPersona != -1) {
                    showClientActionsWindow(stage, idPersona);
                    window.close();
                } else {
                    showAlert(Alert.AlertType.ERROR, "NIE no encontrado", "No se encontró un cliente con el NIE y teléfono proporcionados.");
                }
            }
        });

        VBox layout = new VBox(10, nieLabel, nieInput, telefonoLabel, telefonoInput, submitButton);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 300, 250);
        window.setScene(scene);
        window.show();
    }
    private void showClientActionsWindow(Stage stage, int idPersona) {
        Stage window = new Stage();
        window.setTitle("Acciones del Cliente");

        Button verFacturasButton = new Button("Ver Facturas");
        verFacturasButton.setOnAction(e -> {
            // Define las fechas de inicio y fin aquí
            java.sql.Date fechaInicio = new java.sql.Date(System.currentTimeMillis() - 30L * 24 * 60 * 60 * 1000); // 30 días atrás
            java.sql.Date fechaFin = new java.sql.Date(System.currentTimeMillis());
            showFacturasWindow(idPersona, fechaInicio, fechaFin);
            window.close();
        });

        Button volverInicioButton = new Button("Volver al Inicio");
        volverInicioButton.setOnAction(e -> {
            try {
                start(stage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            window.close();
        });

        VBox layout = new VBox(10, verFacturasButton, volverInicioButton);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 300, 200);
        window.setScene(scene);
        window.show();
    }
    private void showFacturasWindow(int idPersona, java.sql.Date fechaInicio, java.sql.Date fechaFin) {
        List<Factura> facturas = modelo.getFacturasByIdPersona(idPersona);

        // Calcular el monto total después de leer el consumo y tarifas
        double montoTotal = cc.calcularMontoTotal(idPersona, fechaInicio, fechaFin);

        Stage facturasWindow = new Stage();
        facturasWindow.setTitle("Facturas del Cliente");

        ListView<String> facturasListView = new ListView<>();
        for (Factura factura : facturas) {
            facturasListView.getItems().add(factura.toString());
        }


        Label montoTotalLabel = new Label("Monto Total: " + montoTotal);

        VBox layout = new VBox(10, montoTotalLabel, facturasListView);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 400, 300);
        facturasWindow.setScene(scene);
        facturasWindow.show();
    }
    private int obtenerIdPersona(String nie, String telefono) {
        Persona persona = modelo.getPersonaByNie(nie);

        if (persona != null && persona.getTelefono().equals(telefono)) {
            return persona.getId();
        }
        return -1;
    }

    private void mostrarTodosLosConsumos() {
        List<Consumo> consumos = modelo.getAllConsumos();
        for (Consumo consumo : consumos) {
            System.out.println("Consumo ID: " + consumo.getId_consumo() +
                    ", Persona ID: " + consumo.getId_persona() +
                    ", kW Consumidos: " + consumo.getkWConsumidos() +
                    ", Fecha Inicio: " + consumo.getFechaInicio() +
                    ", Fecha Fin: " + consumo.getFechaFin() +
                    ", Tarifa: " + consumo.getTarifa().getDescripcion() +
                    ", Costo kWh: " + consumo.getTarifa().getCostoKwh());
        }
    }
}

























