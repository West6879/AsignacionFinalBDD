package visual;

import database.HorarioDeEstudianteDAO;
import estructura.FilaDeHorario;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import java.util.List;

import static visual.PaginaPrincipalController.habilitarWrapText;

public class HorarioDeEstudianteController {

    @FXML private TableView<FilaDeHorario> tablaHorario;
    @FXML private TableColumn<FilaDeHorario, String> col1;
    @FXML private TableColumn<FilaDeHorario, String> col2;
    @FXML private TableColumn<FilaDeHorario, String> col3;
    @FXML private TableColumn<FilaDeHorario, String> col4;
    @FXML private TableColumn<FilaDeHorario, String> col5;
    @FXML private TableColumn<FilaDeHorario, String> col6;
    @FXML private TableColumn<FilaDeHorario, String> col7;


    @FXML
    public void initialize() {
        col1.setCellValueFactory(new PropertyValueFactory<>("col1"));
        col2.setCellValueFactory(new PropertyValueFactory<>("col2"));
        col3.setCellValueFactory(new PropertyValueFactory<>("col3"));
        col4.setCellValueFactory(new PropertyValueFactory<>("col4"));
        col5.setCellValueFactory(new PropertyValueFactory<>("col5"));
        col6.setCellValueFactory(new PropertyValueFactory<>("col6"));
        col7.setCellValueFactory(new PropertyValueFactory<>("col7"));

        tablaHorario.skinProperty().addListener((obs, oldSkin, newSkin) -> {
            Pane header = (Pane) tablaHorario.lookup("TableHeaderRow");
            if (header != null) {
                header.setMinHeight(0);
                header.setPrefHeight(0);
                header.setMaxHeight(0);
                header.setVisible(false);
            }
        });

        tablaHorario.setRowFactory(tv -> {
            TableRow<FilaDeHorario> row = new TableRow<>() {
                @Override
                protected void updateItem(FilaDeHorario item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null && (item.getCol1().equals("Estudiante:") || item.getCol1().equals("Período:"))) {
                        setPrefHeight(50);
                    } else {
                        setPrefHeight(25);
                    }
                }
            };
            return row;
        });

        habilitarWrapText(col2);
        habilitarWrapText(col3);
    }

    public void cargarHorario(String idEstudiante, String codPeriodoAcademico) {
        List<FilaDeHorario> lista = new HorarioDeEstudianteDAO().getHorarioDeEstudiante(idEstudiante, codPeriodoAcademico);
        tablaHorario.getItems().clear();
        tablaHorario.getItems().addAll(lista);
    }




}
