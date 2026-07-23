package visual;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;

public class PaginaPrincipalController extends Application {

    @FXML private StackPane rootPane;
    @Override
    public void start(Stage primaryStage) throws IOException {

        Scene scene = setupPrincipal();
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    @FXML
    public void insertarEstudiante(ActionEvent event) throws IOException {
        URL fxmlUrl = PaginaPrincipalController.class.getResource("/fxml/Estudiante.fxml");
        if(fxmlUrl == null){
            throw new IOException("Estudiante.fxml no encontrado.");
        }
        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        URL cssUrl = getClass().getResource("/css/tablas.css");
        if(cssUrl != null){
            scene.getStylesheets().add(cssUrl.toExternalForm());
        }
        Window owner = rootPane.getScene().getWindow();
        stage.initOwner(owner);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.setTitle("Manejo de estudiantes");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void insertarAsignatura(ActionEvent event) throws IOException {
        URL fxmlUrl = PaginaPrincipalController.class.getResource("/fxml/Asignatura.fxml");
        if(fxmlUrl == null){
            throw new IOException("Asignatura.fxml no encontrado.");
        }
        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        URL cssUrl = getClass().getResource("/css/tablas.css");
        if(cssUrl != null){
            scene.getStylesheets().add(cssUrl.toExternalForm());
        }
        Window owner = rootPane.getScene().getWindow();
        stage.initOwner(owner);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.setTitle("Manejo de asginaturas");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void btnGrupo(ActionEvent event) throws IOException {
        URL fxmlUrl = PaginaPrincipalController.class.getResource("/fxml/Grupo.fxml");
        if(fxmlUrl == null){
            throw new IOException("Grupo.fxml no encontrado.");
        }
        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        Window owner = rootPane.getScene().getWindow();
        stage.initOwner(owner);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.setTitle("Manejo de grupos");
        stage.setScene(scene);
        stage.show();
    }

    public static Scene setupPrincipal() throws IOException {
        URL fxmlUrl = PaginaPrincipalController.class.getResource("/fxml/PaginaPrincipal.fxml");
        if(fxmlUrl == null){
            throw new IOException("PaginaPrioncipal.fxml no encontrado.");
        }
        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        Parent root = loader.load();
        return new Scene(root);
    }

    public static void limiteDeLongitud(TextField field, int limite) {
        field.setTextFormatter(new TextFormatter<>(change -> {
            if(change.getControlNewText().length() <= limite) {
                return change;
            }
            return null;
        }));
    }

    public static void alerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public static void limitarFechasFuturas(DatePicker datePicker) {
        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate fecha, boolean empty) {
                super.updateItem(fecha, empty);
                LocalDate hoy = LocalDate.now();

                if(fecha != null && fecha.isAfter(hoy)) {
                    setDisable(true);

                }
            }
        });
    }


    @FXML
    public void btnInscribir(ActionEvent event) throws IOException {
        URL fxmlUrl = PaginaPrincipalController.class.getResource("/fxml/InscribirHorario.fxml");
        if (fxmlUrl == null) {
            throw new IOException("Inscribir.fxml no encontrado.");
        }
        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        Window owner = rootPane.getScene().getWindow();
        stage.initOwner(owner);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.setTitle("Inscripción de grupos");
        stage.setScene(scene);
        stage.show();
    }

    public static <S, T>void habilitarWrapText(TableColumn<S, T> columna) {
        columna.setCellFactory(col -> new TableCell<S, T>() {
            private final Label label = new Label();

            {
                label.setWrapText(true);
                label.setTextOverrun(OverrunStyle.CLIP);
                label.prefWidthProperty().bind(col.widthProperty().subtract(10));
            }

            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    label.setText(item.toString());
                    setGraphic(label);
                }
            }
        });
    }

    @FXML
    public void btnCrearGrupo(ActionEvent event) throws IOException {
        URL fxmlUrl = PaginaPrincipalController.class.getResource("/fxml/CrearGrupo.fxml");
        if (fxmlUrl == null) {
            throw new IOException("CrearGrupo.fxml no encontrado.");
        }
        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        Window owner = rootPane.getScene().getWindow();
        stage.initOwner(owner);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.setTitle("Creación de grupos");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void btnCrearPeriodo(ActionEvent event) throws IOException {
        URL fxmlUrl = PaginaPrincipalController.class.getResource("/fxml/CrearPeriodoAcademico.fxml");
        if (fxmlUrl == null) {
            throw new IOException("CrearPeriodoAcademico.fxml no encontrado.");
        }
        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        Window owner = rootPane.getScene().getWindow();
        stage.initOwner(owner);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.setTitle("Creación de Periodos Academicos");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void btnVerInforme(ActionEvent event) throws IOException {
        URL fxmlUrl = PaginaPrincipalController.class.getResource("/fxml/InformeInscripcion.fxml");
        if (fxmlUrl == null) {
            throw new IOException("InformeInscripcion.fxml no encontrado.");
        }
        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        Window owner = rootPane.getScene().getWindow();
        stage.initOwner(owner);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.setTitle("Informe de inscripción");
        stage.setScene(scene);
        stage.show();
    }
}
