package visual;

import database.EstudianteDAO;
import estructura.Estudiante;
import estructura.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Date;
import java.util.List;

public class EstudianteController {

    @FXML private Button btnInsertar;
    @FXML private Button btnActualizar;
    @FXML private Button btnEliminar;

    @FXML private TextField fieldID;
    @FXML private TextField fieldPrimerNombre;
    @FXML private TextField fieldSegundoNombre;
    @FXML private TextField fieldPrimerApellido;
    @FXML private TextField fieldSegundoApellido;
    @FXML private TextField fieldCarrera;
    @FXML private TextField fieldCategoriaDePago;
    @FXML private TextField fieldNacionalidad;
    @FXML private TextField fieldDireccion;
    @FXML private DatePicker datePickerFechaDeNacimiento;

    @FXML private TableView tablaEstudiantes;
    @FXML private TableColumn colID;
    @FXML private TableColumn colNombreCompleto;
    @FXML private TableColumn colCarrera;
    @FXML private TableColumn colCategoriaDePago;
    @FXML private TableColumn colNacionalidad;
    @FXML private TableColumn colDireccion;
    @FXML private TableColumn colFechaDeNacimiento;

    private Estudiante editando = null;

    @FXML
    public void initialize() {
        tablaEstudiantes.getItems().addAll(Main.getInstance().getEstudiantes());
    }


    @FXML
    public void guardarEstudiante(ActionEvent event) {
        String id = fieldID.getText();
        String nombre1 = fieldPrimerNombre.getText();
        String nombre2 = fieldSegundoNombre.getText();
        String apellido1 = fieldPrimerApellido.getText();
        String apellido2 = fieldSegundoApellido.getText();
        String carrera = fieldCarrera.getText();
        String categoriaPago = fieldCategoriaDePago.getText();
        String nacionalidad = fieldNacionalidad.getText();
        String direccion = fieldDireccion.getText();
        Date fechaNacimiento = Date.valueOf(datePickerFechaDeNacimiento.getValue());


        if(this.editando == null) {
            Estudiante nuevoEstudiante = new Estudiante(id, nombre1, nombre2, apellido1, apellido2, carrera, categoriaPago,
                                                        nacionalidad, direccion, fechaNacimiento);
            Main.getInstance().guardarEstudiante(nuevoEstudiante);
            limpiarCampos();
        } else {


        }
    }

    public void setEstudiante(Estudiante estudiante) {
        this.editando = estudiante;
        if(this.editando != null) {
            fieldID.setText(estudiante.getId());
            fieldPrimerNombre.setText(estudiante.getNombre1());
            fieldSegundoNombre.setText(estudiante.getNombre2());
            fieldPrimerApellido.setText(estudiante.getApellido1());
            fieldSegundoApellido.setText(estudiante.getApellido2());
            fieldCarrera.setText(estudiante.getCarrera());
            fieldCategoriaDePago.setText(estudiante.getCategoriaPago());
            fieldNacionalidad.setText(estudiante.getNacionalidad());
            fieldDireccion.setText(estudiante.getDireccion());
            datePickerFechaDeNacimiento.setValue(estudiante.getFechaNacimiento().toLocalDate());
        }

    }

    private void limpiarCampos() {
        fieldID.clear();
        fieldPrimerNombre.clear();
        fieldSegundoNombre.clear();
        fieldPrimerApellido.clear();
        fieldSegundoApellido.clear();
        fieldCarrera.clear();
        fieldCategoriaDePago.clear();
        fieldNacionalidad.clear();
        fieldDireccion.clear();
        datePickerFechaDeNacimiento.getEditor().clear();
    }


}
