package visual;

import estructura.Main;
import estructura.PeriodoAcademico;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Date;

import static visual.PaginaPrincipalController.alerta;

public class PeriodoController {

    @FXML private TextField txtCodigo;
    @FXML private DatePicker dpFechaInicio;
    @FXML private DatePicker dpInicioClases;
    @FXML private DatePicker dpFinClases;
    @FXML private DatePicker dpMaxPrematricula;
    @FXML private DatePicker dpLimRetiro;
    @FXML private DatePicker dpLimPublicacion;
    @FXML private TextArea txtDescripcion;
    @FXML private Button btnCrear;
    @FXML private Button btnCancelar;

    @FXML
    public void crearPeriodo(ActionEvent event) {
        if (txtCodigo.getText().trim().isEmpty() || dpFechaInicio.getValue() == null ||
                dpInicioClases.getValue() == null || dpFinClases.getValue() == null ||
                dpMaxPrematricula.getValue() == null || dpLimRetiro.getValue() == null ||
                dpLimPublicacion.getValue() == null) {

            alerta("Error", "Debes completar todos los campos de fecha y el código.", Alert.AlertType.ERROR);
            return;
        }

        String codigo = txtCodigo.getText().trim();

        if (Main.getInstance().existePeriodo(codigo)) {
            alerta("Error", "Ya existe un periodo con el código: " + codigo, Alert.AlertType.ERROR);
            return;
        }

        Date fechaInicio = Date.valueOf(dpFechaInicio.getValue());
        Date inicioClases = Date.valueOf(dpInicioClases.getValue());
        Date finClases = Date.valueOf(dpFinClases.getValue());
        Date maxPrematricula = Date.valueOf(dpMaxPrematricula.getValue());
        Date limRetiro = Date.valueOf(dpLimRetiro.getValue());
        Date limPublicacion = Date.valueOf(dpLimPublicacion.getValue());

        Date fechaFin = finClases;

        String descripcion = txtDescripcion.getText();

        PeriodoAcademico nuevoPeriodo = new PeriodoAcademico(
                codigo, descripcion, fechaInicio, fechaFin, inicioClases,
                finClases, maxPrematricula, limRetiro, limPublicacion
        );

        if (!nuevoPeriodo.esCodigoValido()) {
            alerta("Error", "El código debe tener 9 dígitos (ej. 202520261) y el primer año debe ser igual al segundo menos 1.", Alert.AlertType.ERROR);
            return;
        }

        if (!nuevoPeriodo.esDuracionClasesValida()) {
            alerta("Error", "La diferencia entre la fecha de inicio de clases y la de fin de clases debe ser de al menos 70 días.", Alert.AlertType.ERROR);
            return;
        }

        if (!nuevoPeriodo.sonFechasEnRango()) {
            alerta("Error", "Las fechas límite y de clases deben estar dentro del rango de inicio y fin del período.", Alert.AlertType.ERROR);
            return;
        }

        Main.getInstance().guardarPeriodo(nuevoPeriodo);

        alerta("Éxito", "Periodo Académico creado correctamente.", Alert.AlertType.INFORMATION);
        cerrarVentana();
    }

    @FXML
    public void cancelar(ActionEvent event) {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }
}