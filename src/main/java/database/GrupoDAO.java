package database;

import estructura.Grupo;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class GrupoDAO {

    public static final GrupoDAO INSTANCE = new GrupoDAO();

    public GrupoDAO() {

    }

    public static GrupoDAO getInstance() {
        return INSTANCE;
    }


    public Map<String, Grupo> findAll() {
        Map<String, Grupo> lista = new HashMap<>();
        final String sql = "SELECT * FROM Grupo";

        try (Connection connection = DatabaseConnection.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()) {
                Grupo grupo = new Grupo();

                grupo.setCodPeriodoAcademico(resultSet.getString("CodPeriodoAcademico"));
                grupo.setCodAsignatura(resultSet.getString("codAsignatura"));
                grupo.setNumGrupo(resultSet.getString("NumGrupo"));
                grupo.setCupoGrupo(resultSet.getInt("CupoGrupo"));
                grupo.setHorario(resultSet.getString("Horario"));


                lista.put(grupo.getClaveGrupo(), grupo);
            }

        } catch(SQLException e) {
            System.out.println("No se pudo obtener la lista de grupos: " + e.getMessage());
        }

        return lista;
    }

    public Grupo actualizarGrupo(Grupo grupo) {
        String sql = "SELECT * FROM Grupo WHERE CodPeriodoAcademico = ? AND CodAsignatura = ? AND NumGrupo = ?";
        try(Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, grupo.getCodPeriodoAcademico());
            ps.setString(2, grupo.getCodAsignatura());
            ps.setString(3, grupo.getNumGrupo());
            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    return new Grupo(rs.getString("CodPeriodoAcademico"), rs.getString("CodAsignatura"),
                            rs.getString("NumGrupo"), rs.getInt("CupoGrupo"), rs.getString("Horario"));
                }
            } catch(SQLException e) {
                System.out.println("No se pudo actualizar el horario del grupo: " + e.getMessage());
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

