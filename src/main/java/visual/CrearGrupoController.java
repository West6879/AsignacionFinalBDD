package visual;

import database.GrupoHorarioDAO;
import estructura.Asignatura;
import estructura.Grupo;
import estructura.GrupoHorario;
import estructura.Main;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Time;

import static visual.PaginaPrincipalController.alerta;

public class CrearGrupoController {

    @FXML private TextField fieldNumeroGrupo;
    @FXML private ComboBox<String> comboPeriodoAcademico;
    @FXML private Spinner<Integer> spinnerCupos;

    @FXML private ComboBox<String> comboDiasClase;

    @FXML private Spinner<Integer> spinnerHoraInicio;
    @FXML private ComboBox<String> comboAmPmInicio;
    @FXML private Spinner<Integer> spinnerHoraFin;
    @FXML private ComboBox<String> comboAmPmFin;

    @FXML private ComboBox<Asignatura> comboAsignatura;

    @FXML private Button btnCrear;
    @FXML private Button btnCancelar;

    @FXML
    public void initialize() {
        SpinnerValueFactory<Integer> valueFactoryCupos =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 30);
        spinnerCupos.setValueFactory(valueFactoryCupos);

        SpinnerValueFactory<Integer> valueFactoryHoraInicio =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12, 8);
        spinnerHoraInicio.setValueFactory(valueFactoryHoraInicio);

        SpinnerValueFactory<Integer> valueFactoryHoraFin =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12, 10);
        spinnerHoraFin.setValueFactory(valueFactoryHoraFin);

        comboPeriodoAcademico.setItems(FXCollections.observableArrayList(Main.getInstance().getPeriodos().keySet()));

        comboDiasClase.setItems(FXCollections.observableArrayList(
                "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"
        ));

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
        String dias = comboDiasClase.getValue();
        Integer hInicio = spinnerHoraInicio.getValue();
        String amPmInicio = comboAmPmInicio.getValue();
        Integer hFin = spinnerHoraFin.getValue();
        String amPmFin = comboAmPmFin.getValue();
        Asignatura asignatura = comboAsignatura.getValue();

        if (numGrupo.isEmpty() || periodo == null || dias == null ||
                hInicio == null || hFin == null || amPmInicio == null ||
                amPmFin == null || asignatura == null) {
            alerta("Campos incompletos", "Por favor completa todos los campos del formulario.", Alert.AlertType.ERROR);
            return;
        }

        int numerodia = switch (dias) {
            case "Lunes"     -> 0;
            case "Martes"    -> 1;
            case "Miércoles" -> 2;
            case "Jueves"    -> 3;
            case "Viernes"   -> 4;
            case "Sábado"    -> 5;
            case "Domingo"   -> 6;
            default          -> -1;
        };
        if (numerodia == -1) {
            alerta("Error de Día", "Selecciona un día de clase válido.", Alert.AlertType.ERROR);
            return;
        }

        int horaInicio24 = hInicio;
        if ("PM".equalsIgnoreCase(amPmInicio) && hInicio < 12) {
            horaInicio24 += 12;
        } else if ("AM".equalsIgnoreCase(amPmInicio) && hInicio == 12) {
            horaInicio24 = 0;
        }

        int horaFin24 = hFin;
        if ("PM".equalsIgnoreCase(amPmFin) && hFin < 12) {
            horaFin24 += 12;
        } else if ("AM".equalsIgnoreCase(amPmFin) && hFin == 12) {
            horaFin24 = 0;
        }

        Time horaInicial = Time.valueOf(String.format("%02d:00:00", horaInicio24));
        Time horaFinal   = Time.valueOf(String.format("%02d:00:00", horaFin24));

        // 4. Validaciones de coherencia de horario
        if (horaInicial.equals(horaFinal)) {
            alerta("Alerta!!", "El horario debe ser de al menos 1 hora.", Alert.AlertType.ERROR);
            return;
        }

        if (horaInicial.after(horaFinal)) {
            alerta("Alerta!!", "La hora inicial no puede estar después de la hora final!", Alert.AlertType.ERROR);
            return;
        }

        String horarioFinal = String.format("%s %d %s - %d %s", dias, hInicio, amPmInicio, hFin, amPmFin);
        Grupo nuevoGrupo = new Grupo(periodo, asignatura.getCodAsignatura(), numGrupo, cupos, horarioFinal);

        if (Main.getInstance().existeGrupo(nuevoGrupo.getClaveGrupo())) {
            alerta("Grupo Duplicado", "Ya existe el grupo " + numGrupo + " para la asignatura " + asignatura.getCodAsignatura() + " en el periodo " + periodo, Alert.AlertType.ERROR);
            return;
        }

        if (!nuevoGrupo.esGrupoValido()) {
            alerta("Datos de Grupo Inválidos", "Revisa que los códigos no estén vacíos y que el cupo sea un número mayor a 0.", Alert.AlertType.ERROR);
            return;
        }

        GrupoHorario nuevoHoraGru = new GrupoHorario(periodo, asignatura.getCodAsignatura(), numGrupo, numerodia, horaInicial, horaFinal);

        Main.getInstance().guardarGrupo(nuevoGrupo);
        GrupoHorarioDAO.getInstance().save(nuevoHoraGru);

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