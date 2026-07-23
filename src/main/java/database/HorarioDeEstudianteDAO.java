package database;

import estructura.FilaDeHorario;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HorarioDeEstudianteDAO {

    public List<FilaDeHorario> getHorarioDeEstudiante(String idEstudiante, String codPeriodoAcad) {
        List<FilaDeHorario> lista = new ArrayList<>();
        String sql = "{call HorarioDeEstudiante(?, ?)}";

        try(Connection connection = DatabaseConnection.getConnection()) {
            CallableStatement statement = connection.prepareCall(sql);

            statement.setString(1, idEstudiante);
            statement.setString(2, codPeriodoAcad);

            try(ResultSet rs = statement.executeQuery()) {
                while(rs.next()) {
                    lista.add(new FilaDeHorario(
                            rs.getString(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getString(6),
                            rs.getString(7)
                    ));
                }
            }


        } catch(SQLException e) {
            IO.println("No se puedo obtener el horario del estudiante:" + e.getMessage());
        }
        return lista;
    }
}
