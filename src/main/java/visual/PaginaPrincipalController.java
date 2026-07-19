package visual;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.net.URL;

public class PaginaPrincipalController extends Application {

    @FXML private AnchorPane rootPane;

    public static void main(String[] args) {
        launch(args);
    }

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
        Window owner = rootPane.getScene().getWindow();
        stage.initOwner(owner);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.setTitle("Manejo de asginaturas");
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
}
