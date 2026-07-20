package visual;

import estructura.Estudiante;
import estructura.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;
import java.util.Optional;

import static visual.PaginaPrincipalController.*;

public class EstudianteController {

    @FXML private Button btnInsertar;
    @FXML private Button btnActualizar;
    @FXML private Button btnEliminar;

    @FXML private TextField fieldID;
    @FXML private TextField fieldPrimerNombre;
    @FXML private TextField fieldSegundoNombre;
    @FXML private TextField fieldPrimerApellido;
    @FXML private TextField fieldSegundoApellido;
    @FXML private TextField fieldCarrera;
    @FXML private TextField fieldCategoriaDePago;
    @FXML private TextField fieldNacionalidad;
    @FXML private TextField fieldDireccion;
    @FXML private DatePicker datePickerFechaDeNacimiento;

    @FXML private TableView<Estudiante> tablaEstudiantes;
    @FXML private TableColumn<Estudiante, String> colID;
    @FXML private TableColumn<Estudiante, String> colNombreCompleto;
    @FXML private TableColumn<Estudiante, String> colCarrera;
    @FXML private TableColumn<Estudiante, String> colCategoriaDePago;
    @FXML private TableColumn<Estudiante, String> colNacionalidad;
    @FXML private TableColumn<Estudiante, String> colDireccion;
    @FXML private TableColumn<Estudiante, Date> colFechaDeNacimiento;

    private Estudiante editando = null;

    @FXML
    public void initialize() {

        fieldFormatters();

        limiteDeLongitud(fieldPrimerNombre, 30);
        limiteDeLongitud(fieldSegundoNombre, 35);
        limiteDeLongitud(fieldPrimerApellido, 25);
        limiteDeLongitud(fieldSegundoApellido, 25);
        limiteDeLongitud(fieldDireccion, 256);
        datePickerFechaDeNacimiento.getEditor().setEditable(false);
        limitarFechasFuturas(datePickerFechaDeNacimiento);

        tablaEstudiantes.setRowFactory(estudianteTableView -> {
            TableRow<Estudiante> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(row.isEmpty()) {
                    tablaEstudiantes.getSelectionModel().clearSelection();
                    this.editando = null;
                    limpiarCampos();
                }
            });
            return row;
        });

        tablaEstudiantes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                setEstudiante(newValue);
            }
        });

        setupColumnas();
        activarBotones();
        tablaEstudiantes.getItems().addAll(Main.getInstance().getEstudiantes().values());
    }

    @FXML
    public void guardarEstudiante(ActionEvent event) {
        String id = fieldID.getText();
        String nombre1 = fieldPrimerNombre.getText();
        String nombre2 = fieldSegundoNombre.getText();
        String apellido1 = fieldPrimerApellido.getText();
        String apellido2 = fieldSegundoApellido.getText();
        String carrera = fieldCarrera.getText();
        String categoriaPago = fieldCategoriaDePago.getText();
        String nacionalidad = fieldNacionalidad.getText();
        String direccion = fieldDireccion.getText();
        Date fechaNacimiento = Date.valueOf(datePickerFechaDeNacimiento.getValue());


        if(Main.getInstance().existeEstudiante(id)) {
            alerta("Alerta!!", "Ya existe un estudiante con este id!!", Alert.AlertType.ERROR);
            return;
        }

        if(id.length() != 8) {
            alerta("Alerta!!", "El id debe contener exactamente 8 dígitos!!",  Alert.AlertType.ERROR);
            return;
        }

        if(!categoriaPago.matches("^[A-Z1-9]\\d{2}$")) {
            alerta("Alerta!!", "La categoria de pago debe seguir el formato: [A-Z1-9]12", Alert.AlertType.ERROR);
            return;
        }

        Estudiante nuevoEstudiante = new Estudiante(id, nombre1, nombre2, apellido1, apellido2, carrera, categoriaPago,
                                                    nacionalidad, direccion, fechaNacimiento);
        Main.getInstance().guardarEstudiante(nuevoEstudiante);
        tablaEstudiantes.getItems().add(nuevoEstudiante);
        limpiarCampos();
    }

    @FXML
    public void btnActualizarClicked(ActionEvent event) {
        String id = fieldID.getText();
        String nombre1 = fieldPrimerNombre.getText();
        String nombre2 = fieldSegundoNombre.getText();
        String apellido1 = fieldPrimerApellido.getText();
        String apellido2 = fieldSegundoApellido.getText();
        String carrera = fieldCarrera.getText();
        String categoriaPago = fieldCategoriaDePago.getText();
        String nacionalidad = fieldNacionalidad.getText();
        String direccion = fieldDireccion.getText();
        Date fechaNacimiento = Date.valueOf(datePickerFechaDeNacimiento.getValue());

        if(!categoriaPago.matches("^[A-Z1-9]\\d{2}$")) {
            alerta("Alerta!!", "La categoria de pago debe seguir el formato: [A-Z1-9]12", Alert.AlertType.ERROR);
            return;
        }

        setearDatos(id, nombre1, nombre2, apellido1, apellido2, carrera, categoriaPago, nacionalidad, direccion, fechaNacimiento);
        Main.getInstance().actualizarEstudiante(editando);
        tablaEstudiantes.refresh();
        tablaEstudiantes.getSelectionModel().clearSelection();
        editando = null;
        limpiarCampos();
    }

    @FXML
    public void btnEliminarClicked(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Eliminar");
        alert.setHeaderText(null);
        alert.setContentText("¿Estas seguro de que deseas eliminar a este estudiante?");
        Optional<ButtonType> resultado = alert.showAndWait();
        if(resultado.isPresent() && resultado.get() == ButtonType.OK) {
            Estudiante seleccionado = tablaEstudiantes.getSelectionModel().getSelectedItem();
            Main.getInstance().eliminarEstudiante(seleccionado.getId());
            tablaEstudiantes.getItems().remove(seleccionado);
            limpiarCampos();
            IO.println("Estudiante ha sido eliminado correctamente.");
        } else {
            alert.close();
            IO.println("No se elimino el estudiante");
        }
    }

    public void setEstudiante(Estudiante estudiante) {
        this.editando = estudiante;
        if(this.editando != null) {
            fieldID.setText(estudiante.getId());
            fieldID.setDisable(true);
            fieldPrimerNombre.setText(estudiante.getNombre1());
            fieldSegundoNombre.setText(estudiante.getNombre2());
            fieldPrimerApellido.setText(estudiante.getApellido1());
            fieldSegundoApellido.setText(estudiante.getApellido2());
            fieldCarrera.setText(estudiante.getCarrera());
            fieldCategoriaDePago.setText(estudiante.getCategoriaPago());
            fieldNacionalidad.setText(estudiante.getNacionalidad());
            fieldDireccion.setText(estudiante.getDireccion());
            datePickerFechaDeNacimiento.setValue(estudiante.getFechaNacimiento().toLocalDate());
        }
    }

    public void activarBotones() {
        btnActualizar.disableProperty().bind(
            tablaEstudiantes.getSelectionModel().selectedItemProperty().isNull()
        );
        btnEliminar.disableProperty().bind(
                tablaEstudiantes.getSelectionModel().selectedItemProperty().isNull()
        );
        btnInsertar.disableProperty().bind(
                tablaEstudiantes.getSelectionModel().selectedItemProperty().isNotNull()
        );
    }

    public void setupColumnas() {
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombreCompleto.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        colCarrera.setCellValueFactory(new PropertyValueFactory<>("carrera"));
        colCategoriaDePago.setCellValueFactory(new PropertyValueFactory<>("categoriaPago"));
        colNacionalidad.setCellValueFactory(new PropertyValueFactory<>("nacionalidad"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        colFechaDeNacimiento.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));
    }

    private void setearDatos(String id, String nombre1, String nombre2, String apellido1, String apellido2,
                             String carrera, String categoriaPago, String nacionalidad, String direccion, Date fechaNacimiento) {
        editando.setId(id);
        editando.setNombre1(nombre1);
        editando.setNombre2(nombre2);
        editando.setApellido1(apellido1);
        editando.setApellido2(apellido2);
        editando.setCarrera(carrera);
        editando.setCategoriaPago(categoriaPago);
        editando.setNacionalidad(nacionalidad);
        editando.setDireccion(direccion);
        editando.setFechaNacimiento(fechaNacimiento);
    }

    private void limpiarCampos() {
        fieldID.clear();
        fieldID.setDisable(false);
        fieldPrimerNombre.clear();
        fieldSegundoNombre.clear();
        fieldPrimerApellido.clear();
        fieldSegundoApellido.clear();
        fieldCarrera.clear();
        fieldCategoriaDePago.clear();
        fieldNacionalidad.clear();
        fieldDireccion.clear();
        datePickerFechaDeNacimiento.setValue(null);
    }

    private void fieldFormatters() {
        fieldID.setTextFormatter(new TextFormatter<>(change -> {
            String nuevoTexto = change.getControlNewText();
            return nuevoTexto.matches("\\d{0,8}") ? change : null;
        }));

        fieldCarrera.setTextFormatter(new TextFormatter<>(change -> {
            String nuevoTexto = change.getControlNewText().toUpperCase();
            if(nuevoTexto.matches("[A-Z]{0,4}")) {
                change.setText(nuevoTexto);
                change.setRange(0, change.getControlText().length());
                return change;
            }
            return null;
        }));

        fieldCategoriaDePago.setTextFormatter(new TextFormatter<>(change -> {
            String nuevoTexto = change.getControlNewText().toUpperCase();
            if(nuevoTexto.matches("([A-Z1-9]|[A-Z1-9]\\d|[A-Z1-9]\\d{2})?")) {
                change.setText(nuevoTexto);
                change.setRange(0, change.getControlText().length());
                return change;
            }
            return null;
        }));

        fieldNacionalidad.setTextFormatter(new TextFormatter<>(change -> {
            String nuevoTexto = change.getControlNewText().toUpperCase();
            if(nuevoTexto.matches("[A-Z]{0,3}")) {
                change.setText(nuevoTexto);
                change.setRange(0, change.getControlText().length());
                return change;
            }
            return null;
        }));
    }

}
