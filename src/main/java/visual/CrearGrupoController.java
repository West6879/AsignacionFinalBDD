package visual;

import estructura.Asignatura;
import estructura.Grupo;
import estructura.Main;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import static visual.PaginaPrincipalController.alerta;

public class CrearGrupoController {

    @FXML private TextField fieldNumeroGrupo;
    @FXML private ComboBox<String> comboPeriodoAcademico;
    @FXML private Spinner<Integer> spinnerCupos;
    @FXML private TextField fieldDiasClase;

    // Horas de inicio y fin
    @FXML private TextField fieldHoraInicio;
    @FXML private ComboBox<String> comboAmPmInicio;
    @FXML private TextField fieldHoraFin;
    @FXML private ComboBox<String> comboAmPmFin;

    // Selector de Asignatura
    @FXML private ComboBox<Asignatura> comboAsignatura;

    @FXML private Button btnCrear;
    @FXML private Button btnCancelar;

    @FXML
    public void initialize() {
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 30);
        spinnerCupos.setValueFactory(valueFactory);

        comboPeriodoAcademico.setItems(FXCollections.observableArrayList(Main.getInstance().getPeriodos().keySet()));
        comboAmPmInicio.setItems(FXCollections.observableArrayList("AM", "PM"));
        comboAmPmFin.setItems(FXCollections.observableArrayList("AM", "PM"));

        comboAmPmInicio.getSelectionModel().select("AM");
        comboAmPmFin.getSelectionModel().select("AM");

        comboAsignatura.setItems(FXCollections.observableArrayList(Main.getInstance().getAsignaturas().values()));

        comboAsignatura.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Asignatura item, boolean empty) {
                super.updateItem(item, empty);
                setText((empty || item == null) ? null : item.getCodAsignatura() + " - " + item.getNombre());
            }
        });
        comboAsignatura.setButtonCell(comboAsignatura.getCellFactory().call(null));

        fieldNumeroGrupo.setTextFormatter(new TextFormatter<>(change -> {
            return change.getControlNewText().matches("\\d{0,4}") ? change : null;
        }));
    }

    @FXML
    public void btnCrearClicked(ActionEvent event) {
        String numGrupo = fieldNumeroGrupo.getText().trim();
        String periodo = comboPeriodoAcademico.getValue();
        Integer cupos = spinnerCupos.getValue();
        String dias = fieldDiasClase.getText().trim();
        String hInicio = fieldHoraInicio.getText().trim();
        String amPmInicio = comboAmPmInicio.getValue();
        String hFin = fieldHoraFin.getText().trim();
        String amPmFin = comboAmPmFin.getValue();
        Asignatura asignatura = comboAsignatura.getValue();

        if (numGrupo.isEmpty() || periodo == null || dias.isEmpty() ||
                hInicio.isEmpty() || hFin.isEmpty() || asignatura == null) {
            alerta("Campos incompletos", "Por favor completa todos los campos del formulario.", Alert.AlertType.ERROR);
            return;
        }

        String horarioFinal = String.format("%s %s %s - %s %s", dias, hInicio, amPmInicio, hFin, amPmFin);

        Grupo nuevoGrupo = new Grupo(periodo, asignatura.getCodAsignatura(), numGrupo, cupos, horarioFinal);

        if (Main.getInstance().existeGrupo(nuevoGrupo.getClaveGrupo())) {
            alerta("Grupo Duplicado", "Ya existe el grupo " + numGrupo + " para la asignatura " + asignatura.getCodAsignatura() + " en el periodo " + periodo, Alert.AlertType.ERROR);
            return;
        }

        if (!nuevoGrupo.esGrupoValido()) {
            alerta("Datos de Grupo Inválidos", "Revisa que los códigos no estén vacíos y que el cupo sea un número mayor a 0.", Alert.AlertType.ERROR);
            return;
        }

        Main.getInstance().guardarGrupo(nuevoGrupo);

        alerta("Éxito", "Grupo creado correctamente.", Alert.AlertType.INFORMATION);
        cerrarVentana();
    }

    @FXML
    public void btnCancelarClicked(ActionEvent event) {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }
}