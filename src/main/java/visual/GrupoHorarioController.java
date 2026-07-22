package visual;

import database.GrupoHorarioDAO;
import estructura.Asignatura;
import estructura.Grupo;
import estructura.GrupoHorario;
import estructura.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Map;
import java.util.Optional;

import static visual.PaginaPrincipalController.alerta;

public class GrupoHorarioController {

    @FXML private Button btnAsignar;
    @FXML private Button btnEliminar;
    @FXML private TableView<GrupoHorario> tablaHorarios;
    @FXML private TableColumn<GrupoHorario, String> colCodPeriodoAcademico;
    @FXML private TableColumn<GrupoHorario, String> colCodAsignatura;
    @FXML private TableColumn<GrupoHorario, String> colNumGrupo;
    @FXML private TableColumn<GrupoHorario, Integer> colDia;
    @FXML private TableColumn<GrupoHorario, Time> colHoraInicial;
    @FXML private TableColumn<GrupoHorario, Time> colHoraFinal;

    @FXML private ComboBox<String> cmbDia;
    @FXML private Spinner<LocalTime> spnHoraInicial;
    @FXML private Spinner<LocalTime> spnHoraFinal;

    private Grupo grupo = null;

    private static final Map<Integer, String> NUMEROS_A_DIA = Map.of(
            0, "Lunes",
            1, "Martes",
            2, "Miércoles",
            3, "Jueves",
            4, "Viernes",
            5, "Sábado",
            6, "Domingo"
            );



    @FXML public void initialize() {

        btnAsignar.disableProperty().bind(
                tablaHorarios.getSelectionModel().selectedItemProperty().isNotNull()
        );

        btnEliminar.disableProperty().bind(
                tablaHorarios.getSelectionModel().selectedItemProperty().isNull()
        );

        tablaHorarios.setRowFactory(grupoTableView -> {
            TableRow<GrupoHorario> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(row.isEmpty()) {
                    tablaHorarios.getSelectionModel().clearSelection();
                }
            });
            return row;
        });

        cmbDia.getItems().addAll("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo");
        spinnerHorario(spnHoraInicial);
        spinnerHorario(spnHoraFinal);

        setupColumnas();

    }


    @FXML
    public void btnAsignarClicked(ActionEvent event) {
        String codPeriodoAcademico = grupo.getCodPeriodoAcademico();
        String codAsignatura = grupo.getCodAsignatura();
        String numGrupo = grupo.getNumGrupo();
        int dia = cmbDia.getSelectionModel().getSelectedIndex();
        Time horaInicial = Time.valueOf(spnHoraInicial.getValue());
        Time horaFinal = Time.valueOf(spnHoraFinal.getValue());

        if(horaInicial.equals(horaFinal)) {
            alerta("Alerta!!", "El horario debe ser de al menos 1 hora.", Alert.AlertType.ERROR);
            return;
        }

        GrupoHorario nuevoHorario = new GrupoHorario(codPeriodoAcademico, codAsignatura, numGrupo, dia, horaInicial, horaFinal);
        GrupoHorarioDAO.getInstance().save(nuevoHorario);
        tablaHorarios.getItems().add(nuevoHorario);
        limpiarCampos();
    }

    @FXML
    public void btnEliminarClicked(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Eliminar");
        alert.setHeaderText(null);
        alert.setContentText("¿Estas seguro de que deseas eliminar este horario?");
        Optional<ButtonType> resultado = alert.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            GrupoHorario seleccionado = tablaHorarios.getSelectionModel().getSelectedItem();
            GrupoHorarioDAO.getInstance().delete(seleccionado);
            tablaHorarios.getItems().remove(seleccionado);
            limpiarCampos();
            IO.println("Asignatura ha sido eliminado correctamente.");
        } else {
            alert.close();
            IO.println("No se elimino la asignatura.");
        }
    }


    public void setGrupo(Grupo grupoSeleccionado) {
        this.grupo = grupoSeleccionado;
        tablaHorarios.getItems().addAll(GrupoHorarioDAO.getInstance().horariosPorGrupo(grupo));
    }

    private void limpiarCampos() {
        cmbDia.getSelectionModel().selectFirst();
        spnHoraInicial.getValueFactory().setValue(LocalTime.of(8, 0));
        spnHoraFinal.getValueFactory().setValue(LocalTime.of(8, 0));
    }

    public void setupColumnas() {
        colCodPeriodoAcademico.setCellValueFactory(new PropertyValueFactory<>("CodPeriodoAcademico"));
        colCodAsignatura.setCellValueFactory(new PropertyValueFactory<>("CodAsignatura"));
        colNumGrupo.setCellValueFactory(new PropertyValueFactory<>("NumGrupo"));
        colDia.setCellValueFactory(new PropertyValueFactory<>("Dia"));
        colHoraInicial.setCellValueFactory(new PropertyValueFactory<>("HoraInicial"));
        colHoraFinal.setCellValueFactory(new PropertyValueFactory<>("HoraFinal"));

        colDia.setCellFactory(column -> new TableCell<GrupoHorario, Integer>() {
            @Override
            protected void updateItem(Integer dia, boolean empty) {
                super.updateItem(dia, empty);
                if (empty || dia == null) {
                    setText(null);
                } else {
                    setText(NUMEROS_A_DIA.get(dia));
                }
            }
        });
    }

    private void spinnerHorario(Spinner<LocalTime> spinner) {
        spinner.setValueFactory(
                new SpinnerValueFactory<LocalTime>() {

                    {
                        setValue(LocalTime.of(8, 0));
                    }

                    @Override
                    public void decrement(int i) {
                        LocalTime nuevoTiempo = getValue().minusHours(i);

                        if(nuevoTiempo.getHour() < 8) {
                            nuevoTiempo = LocalTime.of(8, 0);
                        }
                        setValue(nuevoTiempo);
                    }

                    @Override
                    public void increment(int i) {
                        LocalTime nuevoTiempo = getValue().plusHours(i);

                        if(nuevoTiempo.getHour() > 22) {
                            nuevoTiempo = LocalTime.of(22, 0);
                        }

                        setValue(nuevoTiempo);
                    }
                }
        );
    }


}
