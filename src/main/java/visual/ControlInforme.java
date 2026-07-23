package visual;

import estructura.Asignatura;
import estructura.Estudiante;
import estructura.Grupo;
import estructura.Inscripcion;
import estructura.Main;
import estructura.PeriodoAcademico;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ControlInforme {

    @FXML private ComboBox<Estudiante> cbxEstudiante;
    @FXML private ComboBox<PeriodoAcademico> cbxPeriodo;
    @FXML private TextArea txtReporte;
    @FXML private Button btnVerInforme;
    @FXML private Button btnCerrar;

    @FXML
    public void initialize() {
        formatearComboBoxEstudiante();
        formatearComboBoxPeriodo();

        cbxEstudiante.getItems().addAll(Main.getInstance().getEstudiantes().values());
        cbxPeriodo.getItems().addAll(Main.getInstance().getPeriodos().values());

        btnVerInforme.disableProperty().bind(
                cbxEstudiante.getSelectionModel().selectedItemProperty().isNull()
                        .or(cbxPeriodo.getSelectionModel().selectedItemProperty().isNull())
        );
    }

    private void formatearComboBoxEstudiante() {
        cbxEstudiante.setCellFactory(new Callback<ListView<Estudiante>, ListCell<Estudiante>>() {
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
        cbxEstudiante.setButtonCell(cbxEstudiante.getCellFactory().call(null));
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

    @FXML
    public void generarInforme(ActionEvent event) {
        Estudiante est = cbxEstudiante.getSelectionModel().getSelectedItem();
        PeriodoAcademico periodo = cbxPeriodo.getSelectionModel().getSelectedItem();

        if (est == null || periodo == null) return;

        StringBuilder reporte = new StringBuilder();

        reporte.append("INFORME DE INSCRIPCIÓN\n\n");
        reporte.append("Periodo: ").append(periodo.getCodPeriodoAcademico()).append("\n");
        reporte.append("Estudiante: ").append(est.getId()).append(" - ").append(est.getNombreFormateado()).append("\n");
        reporte.append("Carrera: ").append(est.getCarrera() != null ? est.getCarrera() : "N/A").append("\n\n");

        reporte.append("Grupos inscritos:\n");

        int totalGrupos = 0;
        int totalCreditos = 0;

        for (Inscripcion ins : Main.getInstance().getInscripciones()) {
            if (ins.getId().equals(est.getId()) && ins.getCodPeriodoAcademico().equals(periodo.getCodPeriodoAcademico())) {

                String claveGrupo = ins.getCodPeriodoAcademico() + "|" + ins.getCodAsignatura() + "|" + ins.getNumGrupo();
                Grupo g = Main.getInstance().getGrupos().get(claveGrupo);

                if (g != null) {
                    totalGrupos++;

                    Asignatura asig = Main.getInstance().getAsignaturas().get(g.getCodAsignatura());
                    String nombreAsig = (asig != null && asig.getNombre() != null) ? asig.getNombre() : "Sin Nombre";
                    int creditos = (asig != null) ? asig.getCreditos() : 0;
                    totalCreditos += creditos;

                    String horario = (g.getHorario() != null) ? g.getHorario() : "Sin horario";

                    reporte.append(g.getCodAsignatura()).append(" (Grupo ").append(g.getNumGrupo()).append(") - ")
                            .append(nombreAsig).append(" | Créditos: ").append(creditos)
                            .append(" | Horario: ").append(horario).append("\n");
                }
            }
        }

        if (totalGrupos == 0) {
            reporte.append("Sin grupos inscritos en este período.\n");
        }

        reporte.append("\nTotal de grupos: ").append(totalGrupos).append("\n");
        reporte.append("Total de créditos: ").append(totalCreditos);

        txtReporte.setText(reporte.toString());
    }

    @FXML
    public void cerrar(ActionEvent event) {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }
}