package visual;

import database.InscripcionDAO;
import estructura.Estudiante;
import estructura.Grupo;
import estructura.Inscripcion;
import estructura.Main;
import estructura.PeriodoAcademico;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

import static visual.PaginaPrincipalController.alerta;

public class ControlInscribir {

    @FXML private ComboBox<Estudiante> cbxEst;
    @FXML private ComboBox<PeriodoAcademico> cbxPeriodo;
    @FXML private TableView<Grupo> tvGrupDisp;
    @FXML private TableView<Grupo> tvGrupInsc;
    @FXML private TableColumn<Grupo, String> colDispId;
    @FXML private TableColumn<Grupo, String> colDispNombre;
    @FXML private TableColumn<Grupo, String> colDispHorario;
    @FXML private TableColumn<Grupo, String> colInscId;
    @FXML private TableColumn<Grupo, String> colInscNombre;
    @FXML private Button btnAgregar;
    @FXML private Button btnEliminar;
    @FXML private Button btnSalir;
    @FXML private Button btnVerHorario;

    @FXML
    public void initialize() {
        setupColumnas();
        activarBotones();
        formatearComboBoxEstudiante();
        formatearComboBoxPeriodo();

        tvGrupDisp.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                tvGrupInsc.getSelectionModel().clearSelection();
            }
        });

        tvGrupInsc.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                tvGrupDisp.getSelectionModel().clearSelection();
            }
        });

        cbxEst.getItems().addAll(Main.getInstance().getEstudiantes().values());
        cbxPeriodo.getItems().addAll(Main.getInstance().getPeriodos().values());

        cbxEst.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Estudiante>() {
            @Override
            public void changed(ObservableValue<? extends Estudiante> observable, Estudiante viejo, Estudiante nuevoEstudiante) {
                actualizarTablas();
            }
        });

        cbxPeriodo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PeriodoAcademico>() {
            @Override
            public void changed(ObservableValue<? extends PeriodoAcademico> observable, PeriodoAcademico viejo, PeriodoAcademico nuevoPeriodo) {
                actualizarTablas();
            }
        });
    }

    public void setupColumnas() {
        colDispId.setCellValueFactory(new PropertyValueFactory<>("numGrupo"));
        colDispNombre.setCellValueFactory(new PropertyValueFactory<>("codAsignatura"));
        if (colDispHorario != null) {
            colDispHorario.setCellValueFactory(new PropertyValueFactory<>("horario"));
        }

        colInscId.setCellValueFactory(new PropertyValueFactory<>("numGrupo"));
        colInscNombre.setCellValueFactory(new PropertyValueFactory<>("codAsignatura"));
    }

    private void formatearComboBoxEstudiante() {
        cbxEst.setCellFactory(new Callback<ListView<Estudiante>, ListCell<Estudiante>>() {
            @Override
            public ListCell<Estudiante> call(ListView<Estudiante> param) {
                return new ListCell<Estudiante>() {
                    @Override
                    protected void updateItem(Estudiante item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            setText(item.getId() + " - " + item.getNombreCompleto());
                        }
                    }
                };
            }
        });
        cbxEst.setButtonCell(cbxEst.getCellFactory().call(null));
    }

    private void formatearComboBoxPeriodo() {
        cbxPeriodo.setCellFactory(new Callback<ListView<PeriodoAcademico>, ListCell<PeriodoAcademico>>() {
            @Override
            public ListCell<PeriodoAcademico> call(ListView<PeriodoAcademico> param) {
                return new ListCell<PeriodoAcademico>() {
                    @Override
                    protected void updateItem(PeriodoAcademico item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            setText(item.getCodPeriodoAcademico());
                        }
                    }
                };
            }
        });
        cbxPeriodo.setButtonCell(cbxPeriodo.getCellFactory().call(null));
    }

    public void activarBotones() {
        btnAgregar.disableProperty().bind(
                tvGrupDisp.getSelectionModel().selectedItemProperty().isNull()
                        .or(cbxEst.getSelectionModel().selectedItemProperty().isNull())
                        .or(cbxPeriodo.getSelectionModel().selectedItemProperty().isNull())
        );

        btnEliminar.disableProperty().bind(
                tvGrupInsc.getSelectionModel().selectedItemProperty().isNull()
        );

        if (btnVerHorario != null) {
            btnVerHorario.disableProperty().bind(
                    cbxEst.getSelectionModel().selectedItemProperty().isNull()
            );
        }
    }

    private void actualizarTablas() {
        Estudiante est = cbxEst.getSelectionModel().getSelectedItem();
        PeriodoAcademico periodo = cbxPeriodo.getSelectionModel().getSelectedItem();

        tvGrupInsc.getItems().clear();
        tvGrupDisp.getItems().clear();

        if (est == null || periodo == null) return;

        String idPeriodoSel = periodo.getCodPeriodoAcademico();

        for (Inscripcion ins : Main.getInstance().getInscripciones()) {
            if (ins.getId().equals(est.getId()) && ins.getCodPeriodoAcademico().equals(idPeriodoSel)) {
                String claveGrupo = ins.getCodPeriodoAcademico() + "|" + ins.getCodAsignatura() + "|" + ins.getNumGrupo();
                Grupo grupoInscrito = Main.getInstance().getGrupos().get(claveGrupo);

                if (grupoInscrito != null) {
                    tvGrupInsc.getItems().add(grupoInscrito);
                }
            }
        }

        for (Grupo g : Main.getInstance().getGrupos().values()) {
            if (g.getCodPeriodoAcademico().equals(idPeriodoSel)) {

                if (!tvGrupInsc.getItems().contains(g)) {

                    int inscritos = 0;
                    for (Inscripcion ins : Main.getInstance().getInscripciones()) {
                        if (ins.getCodPeriodoAcademico().equals(g.getCodPeriodoAcademico()) &&
                                ins.getCodAsignatura().equals(g.getCodAsignatura()) &&
                                ins.getNumGrupo().equals(g.getNumGrupo())) {

                            inscritos++;
                        }
                    }

                    if (inscritos < g.getCupoGrupo()) {
                        tvGrupDisp.getItems().add(g);
                    }
                }
            }
        }
    }
    private boolean chocaHorario(Grupo nuevoGrupo) {
        if (nuevoGrupo.getHorario() == null || nuevoGrupo.getHorario().trim().isEmpty()) {
            return false;
        }

        for (Grupo inscrito : tvGrupInsc.getItems()) {
            if (inscrito.getHorario() != null && !inscrito.getHorario().trim().isEmpty()) {
                if (nuevoGrupo.getHorario().equalsIgnoreCase(inscrito.getHorario())) {
                    return true;
                }
            }
        }
        return false;
    }

    @FXML
    public void agregarGrupo(ActionEvent event) {
        Grupo grupoSeleccionado = tvGrupDisp.getSelectionModel().getSelectedItem();
        Estudiante est = cbxEst.getSelectionModel().getSelectedItem();

        if (grupoSeleccionado != null && est != null) {

            // Validar que no choque con los horarios de las asignaturas ya inscritas
            if (chocaHorario(grupoSeleccionado)) {
                Alert alertaError = new Alert(Alert.AlertType.ERROR);
                alertaError.setTitle("Conflicto de Horario");
                alertaError.setHeaderText("No se puede inscribir el grupo");
                alertaError.setContentText("El horario del grupo (" + grupoSeleccionado.getHorario() +
                        ") choca con una asignatura ya inscrita en este período.");
                alertaError.showAndWait();
                return;
            }

            StringBuilder mensaje = new StringBuilder();
            mensaje.append("¿Está seguro de que desea inscribir este grupo?\n\n");
            mensaje.append("• Estudiante: ").append(est.getId()).append(" - ").append(est.getNombreCompleto()).append("\n");
            mensaje.append("• Período: ").append(grupoSeleccionado.getCodPeriodoAcademico()).append("\n");
            mensaje.append("• Asignatura: ").append(grupoSeleccionado.getCodAsignatura()).append("\n");
            mensaje.append("• Número de Grupo: ").append(grupoSeleccionado.getNumGrupo()).append("\n");
            mensaje.append("• Horario: ").append(grupoSeleccionado.getHorario() != null ? grupoSeleccionado.getHorario() : "No asignado").append("\n");
            mensaje.append("• Cupo: ").append(grupoSeleccionado.getCupoGrupo());

            Alert alertaConfirm = new Alert(Alert.AlertType.CONFIRMATION);
            alertaConfirm.setTitle("Confirmar Inscripción");
            alertaConfirm.setHeaderText("Confirmación de Inscripción de Grupo");
            alertaConfirm.setContentText(mensaje.toString());

            Optional<ButtonType> resultado = alertaConfirm.showAndWait();

            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                Inscripcion nuevaInscripcion = new Inscripcion(
                        grupoSeleccionado.getCodPeriodoAcademico(),
                        est.getId(),
                        grupoSeleccionado.getCodAsignatura(),
                        grupoSeleccionado.getNumGrupo()
                );

                Main.getInstance().guardarInscripcion(nuevaInscripcion);
                actualizarTablas();
            }
        }
    }

    @FXML
    public void eliminarInscripcion(ActionEvent event) {
        Grupo grupoSeleccionado = tvGrupInsc.getSelectionModel().getSelectedItem();
        Estudiante est = cbxEst.getSelectionModel().getSelectedItem();

        if (grupoSeleccionado != null && est != null) {
            Inscripcion inscripcionAEliminar = null;

            for (Inscripcion ins : Main.getInstance().getInscripciones()) {
                if (ins.getId().equals(est.getId()) &&
                        ins.getCodPeriodoAcademico().equals(grupoSeleccionado.getCodPeriodoAcademico()) &&
                        ins.getCodAsignatura().equals(grupoSeleccionado.getCodAsignatura()) &&
                        ins.getNumGrupo().equals(grupoSeleccionado.getNumGrupo())) {

                    inscripcionAEliminar = ins;
                    break;
                }
            }

            if (inscripcionAEliminar != null) {
                Main.getInstance().eliminarInscripcion(inscripcionAEliminar);
                actualizarTablas();
            }
        }
    }

    @FXML
    public void salir(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void btnVerHorarioClicked(ActionEvent event) throws IOException {
        URL fxmlUrl = GrupoController.class.getResource("/fxml/HorarioDeEstudiante.fxml");
        if(fxmlUrl == null){
            throw new IOException("HorarioDeEstudiante.fxml no encontrado.");
        }
        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        Parent root = loader.load();

        Estudiante seleccionado = cbxEst.getSelectionModel().getSelectedItem();
        PeriodoAcademico periodo = cbxPeriodo.getSelectionModel().getSelectedItem();
        String codPeriodoAcademico = periodo.getCodPeriodoAcademico();

        HorarioDeEstudianteController controller = loader.getController();
        controller.cargarHorario(seleccionado.getId(), codPeriodoAcademico);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.setTitle("Horario del estudiante: ");
        stage.setScene(scene);

        stage.show();
    }
}