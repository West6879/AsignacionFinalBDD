package visual;

import estructura.Asignatura;
import estructura.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;
import java.util.Optional;

import static visual.PaginaPrincipalController.*;

public class AsignaturaController {

    @FXML private Button btnInsertar;
    @FXML private Button btnActualizar;
    @FXML private Button btnEliminar;

    @FXML private TextField fieldCodAsignatura;
    @FXML private TextField fieldNombre;
    @FXML private Spinner<Integer> spnCreditos;
    @FXML private Spinner<Integer> spnHorasTeoricas;
    @FXML private Spinner<Integer> spnHorasPracticas;
    @FXML private TextField fieldActividad;
    @FXML private TextField fieldUsuario;
    @FXML private DatePicker datePickerFechaHora;

    @FXML private TableView<Asignatura> tablaAsignaturas;
    @FXML private TableColumn<Asignatura, String> colCodigo;
    @FXML private TableColumn<Asignatura, String> colNombre;
    @FXML private TableColumn<Asignatura, Integer> colCreditos;
    @FXML private TableColumn<Asignatura, Integer> colHorasTeoricas;
    @FXML private TableColumn<Asignatura, Integer> colHorasPracticas;
    @FXML private TableColumn<Asignatura, String> colActividad;
    @FXML private TableColumn<Asignatura, String> colUsuario;
    @FXML private TableColumn<Asignatura, Date> colFechaHora;

    private Asignatura editando = null;

    @FXML
    public void initialize() {

        fieldCodAsignatura.setTextFormatter(new TextFormatter<>(change -> {
            String nuevoTexto = change.getControlNewText().toUpperCase();
            if(nuevoTexto.matches("[A-Z]{0,3}\\d{0,3}[TP]?")) {
                change.setText(nuevoTexto);
                change.setRange(0, change.getControlText().length());
                return change;
            }
            return null;
        }));

        limiteDeLongitud(fieldNombre, 60);
        limiteDeLongitud(fieldActividad, 30);
        limiteDeLongitud(fieldUsuario, 50);
        datePickerFechaHora.getEditor().setEditable(false);
        limitarFechasFuturas(datePickerFechaHora);

        tablaAsignaturas.setRowFactory(asignaturaTableView ->{
            TableRow<Asignatura> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(row.isEmpty()) {
                    tablaAsignaturas.getSelectionModel().clearSelection();
                    this.editando = null;
                    limpiarCampos();
                }
            });
            return row;
        });

        tablaAsignaturas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                setAsignatura(newValue);
            }
        });

        setupSpinners();
        setupColumnas();
        activarBotones();
        habilitarWrapText(colNombre);
        tablaAsignaturas.getItems().addAll(Main.getInstance().getAsignaturas().values());
    }

    @FXML
    public void guardarAsignatura(ActionEvent event) {
        String codAsignatura = fieldCodAsignatura.getText();
        String nombre = fieldNombre.getText();
        Integer creditos = spnCreditos.getValue();
        Integer horasTeoricas = spnHorasTeoricas.getValue();
        Integer horasPracticas = spnHorasPracticas.getValue();
        String actividad = fieldActividad.getText();
        String usuario = fieldUsuario.getText();
        Date fechaHora = Date.valueOf(datePickerFechaHora.getValue());

        System.out.println("HorasTeoricas: " + horasTeoricas);
        System.out.println("HorasPracticas: " + horasPracticas);
        System.out.println("Creditos: " + creditos);
        System.out.println("Resultado formula: " + (horasTeoricas + 0.75 * horasPracticas) + " <= " + creditos + " ?");

        if(Main.getInstance().existeAsignatura(codAsignatura)) {
            alerta("Alerta!!", "Ya existe una asignatura con este código!!", Alert.AlertType.ERROR);
            return;
        }

        if(!codAsignatura.matches("^[A-Z]{3}\\d{3}[TP]$")) {
            alerta("Alerta!!", "El código de asignatura debe seguir un formato especifico: ABC123[TP]", Alert.AlertType.ERROR);
            return;
        }

        if(horasTeoricas + (horasPracticas * 0.75) > creditos) {
            alerta("Alerta!!", "Las horas teóricas mas el 75% de las horas practicas no puede ser mayor a créditos!!", Alert.AlertType.ERROR);
            return;
        }

        Asignatura nuevaAsignatura = new Asignatura(codAsignatura, nombre, creditos, horasTeoricas, horasPracticas, actividad,
                usuario, fechaHora);
        Main.getInstance().guardarAsignatura(nuevaAsignatura);
        tablaAsignaturas.getItems().add(nuevaAsignatura);
        limpiarCampos();
    }

    @FXML
    public void btnActualizarClicked(ActionEvent event) {
        String codAsignatura = fieldCodAsignatura.getText();
        String nombre = fieldNombre.getText();
        Integer creditos = spnCreditos.getValue();
        Integer horasTeoricas = spnHorasTeoricas.getValue();
        Integer horasPracticas = spnHorasPracticas.getValue();
        String actividad = fieldActividad.getText();
        String usuario = fieldUsuario.getText();
        Date fechaHora = Date.valueOf(datePickerFechaHora.getValue());

        System.out.println("HorasTeoricas: " + horasTeoricas);
        System.out.println("HorasPracticas: " + horasPracticas);
        System.out.println("Creditos: " + creditos);
        System.out.println("Resultado formula: " + (horasTeoricas + 0.75 * horasPracticas) + " <= " + creditos + " ?");

        if(horasTeoricas + (horasPracticas * 0.75) > creditos) {
            alerta("Alerta!!", "Las horas teóricas mas el 75% de las horas practicas no puede ser mayor a créditos!!", Alert.AlertType.ERROR);
            return;
        }

        setearDatos(codAsignatura, nombre, creditos, horasTeoricas, horasPracticas, actividad, usuario, fechaHora);
        Main.getInstance().actualizarAsignatura(editando);
        tablaAsignaturas.refresh();
        tablaAsignaturas.getSelectionModel().clearSelection();

        editando = null;
        limpiarCampos();
    }

    @FXML
    public void btnEliminarClicked(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Eliminar");
        alert.setHeaderText(null);
        alert.setContentText("¿Estas seguro de que deseas eliminar esta asignatura?");
        Optional<ButtonType> resultado = alert.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            Asignatura seleccionado = tablaAsignaturas.getSelectionModel().getSelectedItem();
            Main.getInstance().eliminarAsignatura(seleccionado.getCodAsignatura());
            tablaAsignaturas.getItems().remove(seleccionado);
            limpiarCampos();
            IO.println("Asignatura ha sido eliminado correctamente.");
        } else {
            alert.close();
            IO.println("No se elimino la asignatura.");
        }
    }


    public void setAsignatura(Asignatura asignatura) {
        this.editando = asignatura;
        if(this.editando != null) {
            fieldCodAsignatura.setText(asignatura.getCodAsignatura());
            fieldCodAsignatura.setDisable(true);
            fieldNombre.setText(asignatura.getNombre());
            spnCreditos.getValueFactory().setValue(asignatura.getCreditos());
            spnHorasTeoricas.getValueFactory().setValue(asignatura.getHorasTeoricas());
            spnHorasPracticas.getValueFactory().setValue(asignatura.getHorasPracticas());
            fieldActividad.setText(asignatura.getActividad());
            fieldUsuario.setText(asignatura.getUsuario());
            datePickerFechaHora.setValue(asignatura.getFechaHora().toLocalDate());
        }
    }

    public void activarBotones() {
        btnActualizar.disableProperty().bind(
                tablaAsignaturas.getSelectionModel().selectedItemProperty().isNull()
        );
        btnEliminar.disableProperty().bind(
                tablaAsignaturas.getSelectionModel().selectedItemProperty().isNull()
        );
        btnInsertar.disableProperty().bind(
                tablaAsignaturas.getSelectionModel().selectedItemProperty().isNotNull()
        );
    }

    public void setupColumnas() {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codAsignatura"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCreditos.setCellValueFactory(new PropertyValueFactory<>("creditos"));
        colHorasTeoricas.setCellValueFactory(new PropertyValueFactory<>("horasTeoricas"));
        colHorasPracticas.setCellValueFactory(new PropertyValueFactory<>("horasPracticas"));
        colActividad.setCellValueFactory(new PropertyValueFactory<>("actividad"));
        colUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        colFechaHora.setCellValueFactory(new PropertyValueFactory<>("fechaHora"));
    }

    private void setearDatos(String codAsignatura, String nombre, int creditos, int horasTeoricas, int horasPracticas,
                             String actividad, String usuario, Date fechaHora) {
        editando.setCodAsignatura(codAsignatura);
        editando.setNombre(nombre);
        editando.setCreditos(creditos);
        editando.setHorasTeoricas(horasTeoricas);
        editando.setHorasPracticas(horasPracticas);
        editando.setActividad(actividad);
        editando.setUsuario(usuario);
        editando.setFechaHora(fechaHora);
    }

    private void limpiarCampos() {
        fieldCodAsignatura.clear();
        fieldCodAsignatura.setDisable(false);
        fieldNombre.clear();
        spnCreditos.getValueFactory().setValue(0);
        spnHorasTeoricas.getValueFactory().setValue(0);
        spnHorasPracticas.getValueFactory().setValue(0);
        fieldActividad.clear();
        fieldUsuario.clear();
        datePickerFechaHora.setValue(null);
    }

    private void setupSpinners() {
        spnCreditos.setEditable(true);
        spnHorasTeoricas.setEditable(true);
        spnHorasPracticas.setEditable(true);

        spnCreditos.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 6, 0, 1)
        );
        spnHorasTeoricas.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 6, 0, 1)
        );
        spnHorasPracticas.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 6, 0, 1)
        );
    }


}
