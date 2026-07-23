package visual;

import database.GrupoDAO;
import estructura.Grupo;
import estructura.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class GrupoController {

    @FXML private Button btnSeleccionar;
    @FXML private TableView<Grupo> tablaGrupos;
    @FXML private TableColumn<Grupo, String> colCodPeriodoAcademico;
    @FXML private TableColumn<Grupo, String> colCodAsignatura;
    @FXML private TableColumn<Grupo, String> colNumGrupo;
    @FXML private TableColumn<Grupo, Integer> colCupoGrupo;
    @FXML private TableColumn<Grupo, String> colHorario;


    @FXML
    public void initialize() {
        btnSeleccionar.disableProperty().bind(
                tablaGrupos.getSelectionModel().selectedItemProperty().isNull()
        );

        tablaGrupos.setRowFactory(grupoTableView -> {
            TableRow<Grupo> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(row.isEmpty()) {
                    tablaGrupos.getSelectionModel().clearSelection();
                }
            });
            return row;
        });


        setupColumnas();
        tablaGrupos.getItems().addAll(Main.getInstance().getGrupos().values());

    }

    @FXML
    public void btnSeleccionarClicked(ActionEvent event) throws IOException{
        URL fxmlUrl = GrupoController.class.getResource("/fxml/GrupoHorario.fxml");
        if(fxmlUrl == null){
            throw new IOException("GrupoHorario.fxml no encontrado.");
        }
        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        Parent root = loader.load();
        GrupoHorarioController controller = loader.getController();
        Grupo grupoSeleccionado = tablaGrupos.getSelectionModel().getSelectedItem();
        controller.setGrupo(grupoSeleccionado);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.setTitle("Horarios del grupo: " + grupoSeleccionado.getClaveGrupo());
        stage.setScene(scene);

        stage.setOnHiding(e -> refrescarGrupo(grupoSeleccionado));
        stage.show();
    }

    public void refrescarGrupo(Grupo grupo){
        Grupo grupoActualizado = GrupoDAO.getInstance().actualizarHorarioDeGrupo(grupo);

        if(grupoActualizado != null){
            Main.getInstance().actualizarGrupo(grupoActualizado);

            int index = tablaGrupos.getItems().indexOf(grupo);
            if(index >= 0) {
                tablaGrupos.getItems().set(index, grupoActualizado);
            }
        }
    }

    public void setupColumnas() {
        colCodPeriodoAcademico.setCellValueFactory(new PropertyValueFactory<>("CodPeriodoAcademico"));
        colCodAsignatura.setCellValueFactory(new PropertyValueFactory<>("CodAsignatura"));
        colNumGrupo.setCellValueFactory(new PropertyValueFactory<>("NumGrupo"));
        colCupoGrupo.setCellValueFactory(new PropertyValueFactory<>("CupoGrupo"));
        colHorario.setCellValueFactory(new PropertyValueFactory<>("Horario"));

        colCodPeriodoAcademico.setCellFactory(column -> new TableCell<Grupo, String>() {
            @Override
            protected void updateItem(String codigo, boolean empty) {
                super.updateItem(codigo, empty);
                if (empty || codigo == null || codigo.length() != 9) {
                    setText(codigo); // muestra tal cual si no coincide con el formato esperado
                } else {
                    String anioInicio = codigo.substring(0, 4);
                    String anioFin = codigo.substring(4, 8);
                    String periodo = codigo.substring(8, 9);
                    setText(anioInicio + "-" + anioFin + "/" + periodo);
                }
            }
        });
    }



}
