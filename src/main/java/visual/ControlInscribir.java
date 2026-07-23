package visual;

import estructura.Estudiante;
import estructura.Grupo;
import estructura.Inscripcion;
import estructura.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ControlInscribir {

    @FXML
    private ComboBox<Estudiante> cbxEst;

    // Tablas
    @FXML
    private TableView<Grupo> tvGrupDisp;
    @FXML
    private TableView<Grupo> tvGrupInsc;

    // Columnas Disponibles
    @FXML
    private TableColumn<Grupo, String> colDispId;
    @FXML
    private TableColumn<Grupo, String> colDispNombre;

    // Columnas Inscritos
    @FXML
    private TableColumn<Grupo, String> colInscId;
    @FXML
    private TableColumn<Grupo, String> colInscNombre;

    // Botones
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnEliminar;

    @FXML
    public void initialize() {
        setupColumnas();
        activarBotones();

        tvGrupDisp.setOnMouseClicked(e -> tvGrupInsc.getSelectionModel().clearSelection());
        tvGrupInsc.setOnMouseClicked(e -> tvGrupDisp.getSelectionModel().clearSelection());

        cbxEst.getItems().addAll(Main.getInstance().getEstudiantes().values());

        cbxEst.getSelectionModel().selectedItemProperty().addListener((obs, viejo, nuevoEstudiante) -> {
            if (nuevoEstudiante != null) {
                cargarTablasParaEstudiante(nuevoEstudiante.getId());
            }
        });
    }

    public void setupColumnas() {
        colDispId.setCellValueFactory(new PropertyValueFactory<>("numGrupo"));
        colDispNombre.setCellValueFactory(new PropertyValueFactory<>("codAsignatura"));

        colInscId.setCellValueFactory(new PropertyValueFactory<>("numGrupo"));
        colInscNombre.setCellValueFactory(new PropertyValueFactory<>("codAsignatura"));
    }

    public void activarBotones() {
        btnAgregar.disableProperty().bind(
                tvGrupDisp.getSelectionModel().selectedItemProperty().isNull()
        );
        btnEliminar.disableProperty().bind(
                tvGrupInsc.getSelectionModel().selectedItemProperty().isNull()
        );
    }

    private void cargarTablasParaEstudiante(String idEstudiante) {
        tvGrupInsc.getItems().clear();
        tvGrupDisp.getItems().clear();

        for (Inscripcion ins : Main.getInstance().getInscripciones()) {
            if (ins.getId().equals(idEstudiante)) {
                String claveGrupo = ins.getCodAsignatura() + "-" + ins.getNumGrupo(); // Ajusta según la clave de tu Map<String, Grupo>
                Grupo grupoInscrito = Main.getInstance().getGrupos().get(claveGrupo);

                if (grupoInscrito != null) {
                    tvGrupInsc.getItems().add(grupoInscrito);
                }
            }
        }

        for (Grupo g : Main.getInstance().getGrupos().values()) {
            if (!tvGrupInsc.getItems().contains(g)) {
                tvGrupDisp.getItems().add(g);
            }
        }
    }

        @FXML
        public void agregarGrupo (ActionEvent event){
            Grupo grupoSeleccionado = tvGrupDisp.getSelectionModel().getSelectedItem();
            Estudiante est = cbxEst.getSelectionModel().getSelectedItem();

            if (grupoSeleccionado != null && est != null) {
                Inscripcion nuevaInscripcion = new Inscripcion(
                        "2026-1",
                        est.getId(),
                        grupoSeleccionado.getCodAsignatura(),
                        grupoSeleccionado.getNumGrupo()
                );
                Main.getInstance().guardarInscripcion(nuevaInscripcion);
                cargarTablasParaEstudiante(est.getId());
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
                            ins.getCodAsignatura().equals(grupoSeleccionado.getCodAsignatura()) &&
                            ins.getNumGrupo().equals(grupoSeleccionado.getNumGrupo())) {

                        inscripcionAEliminar = ins;
                        break;
                    }
                }

                if (inscripcionAEliminar != null) {
                    Main.getInstance().eliminarInscripcion(inscripcionAEliminar);
                    cargarTablasParaEstudiante(est.getId());
                }
            }
        }
}