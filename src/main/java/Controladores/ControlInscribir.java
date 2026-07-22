package Controladores;

import estructura.Administración;
import estructura.Estudiante;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class ControlInscribir {
    public static Administración laAdministracion = new Administración();

    @FXML
    private ComboBox<Estudiante> cbxEst;


    private void inicializar (){
        for (int i =0; i <= laAdministracion.getEstudiantes().size(); i++) {
            cbxEst.setValue(
                    laAdministracion.getEstudiantes().get(i));

        }
    }

}
