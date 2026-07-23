package database;

import estructura.Inscripcion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InscripcionDAO {

    public static final InscripcionDAO INSTANCE = new InscripcionDAO();

    public InscripcionDAO() {
    }

    public static InscripcionDAO getInstance() {
        return INSTANCE;
    }

    public void save(Inscripcion inscripcion) {
        final String sql = "INSERT INTO Inscripcion (CodPeriodoAcademico, Id, CodAsignatura, NumGrupo) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, inscripcion.getCodPeriodoAcademico());
            ps.setString(2, inscripcion.getId());
            ps.setString(3, inscripcion.getCodAsignatura());
            ps.setString(4, inscripcion.getNumGrupo());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("No se pudo registrar la inscripción: " + e.getMessage());
        }
    }

    public void delete(String codPeriodo, String Id, String codAsignatura, String numGrupo) {
        final String sql = "DELETE FROM Inscripcion WHERE CodPeriodoAcademico = ? AND Id = ? " +
                "AND CodAsignatura = ? AND NumGrupo = ?";

        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, codPeriodo);
            ps.setString(2, Id);
            ps.setString(3, codAsignatura);
            ps.setString(4, numGrupo);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("No se pudo eliminar la inscripción: " + e.getMessage());
        }
    }

    public List<Inscripcion> findByEstudiante(String idEstudiante) {
        List<Inscripcion> lista = new ArrayList<>();
        final String sql = "SELECT * FROM Inscripcion WHERE Id = ?";

        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, idEstudiante);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                Inscripcion inscripcion = new Inscripcion(
                        resultSet.getString("CodPeriodoAcademico"),
                        resultSet.getString("Id"),
                        resultSet.getString("CodAsignatura"),
                        resultSet.getString("NumGrupo")
                );
                lista.add(inscripcion);
            }

        } catch (SQLException e) {
            System.out.println("No se pudieron obtener las inscripciones del estudiante: " + e.getMessage());
        }

        return lista;
    }

    public List<Inscripcion> findAll() {
        List<Inscripcion> lista = new ArrayList<>();
        final String sql = "SELECT * FROM Inscripcion";

        try (Connection connection = DatabaseConnection.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Inscripcion inscripcion = new Inscripcion(
                        resultSet.getString("CodPeriodoAcademico"),
                        resultSet.getString("Id"),
                        resultSet.getString("CodAsignatura"),
                        resultSet.getString("NumGrupo")
                );
                lista.add(inscripcion);
            }

        } catch (SQLException e) {
            System.out.println("No se pudo obtener la lista global de inscripciones: " + e.getMessage());
        }

        return lista;
    }
}

