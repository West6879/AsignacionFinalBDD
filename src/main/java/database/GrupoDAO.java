package database;

import estructura.Grupo;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class GrupoDAO {

    private static final GrupoDAO INSTANCE = new GrupoDAO();

    private GrupoDAO() {

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
                grupo.setCodAsignatura(resultSet.getString("CodAsignatura"));
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

    // CORREGIDO: Ahora es un UPDATE real, no un SELECT
    public Grupo actualizarGrupo(Grupo grupo) {
        final String sql = "UPDATE Grupo SET CupoGrupo = ?, Horario = ? " +
                "WHERE CodPeriodoAcademico = ? AND CodAsignatura = ? AND NumGrupo = ?";

        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, grupo.getCupoGrupo());
            ps.setString(2, grupo.getHorario());
            ps.setString(3, grupo.getCodPeriodoAcademico());
            ps.setString(4, grupo.getCodAsignatura());
            ps.setString(5, grupo.getNumGrupo());

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                return grupo;
            }

        } catch (SQLException e) {
            System.err.println("No se pudo actualizar el horario/cupo del grupo:");
            e.printStackTrace();
        }
        return null;
    }

    public void save(Grupo grupo) {
        final String sql = "INSERT INTO Grupo (CodPeriodoAcademico, CodAsignatura, NumGrupo, CupoGrupo, Horario) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, grupo.getCodPeriodoAcademico());
            ps.setString(2, grupo.getCodAsignatura());
            ps.setString(3, grupo.getNumGrupo());
            ps.setInt(4, grupo.getCupoGrupo());
            ps.setString(5, grupo.getHorario());

            int filasAfectadas = ps.executeUpdate();
            System.out.println("Grupo guardado en base de datos. Filas afectadas: " + filasAfectadas);

        } catch (SQLException e) {
            System.err.println("❌ ERROR FATAL AL INSERTAR GRUPO EN SQL:");
            e.printStackTrace();
        }
    }

    public void delete(String codPeriodo, String codAsignatura, String numGrupo) {
        final String sql = "DELETE FROM Grupo WHERE CodPeriodoAcademico = ? AND CodAsignatura = ? AND NumGrupo = ?";

        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, codPeriodo);
            ps.setString(2, codAsignatura);
            ps.setString(3, numGrupo);

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("No se pudo eliminar el grupo: " + e.getMessage());
        }
    }
}