package database;

import estructura.Asignatura;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class AsignaturaDAO {

    public static final AsignaturaDAO INSTANCE = new AsignaturaDAO();

    public AsignaturaDAO() {
    }

    public static AsignaturaDAO getInstance() { return INSTANCE; }


    public void save(Asignatura asignatura) {
        final String sql = "INSERT INTO Asignatura (CodAsignatura, Nombre, Creditos, HorasTeoricas, HorasPracticas, Actividad, Usuario, FechaHora) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try(Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, asignatura.getCodAsignatura());
            ps.setString(2, asignatura.getNombre());
            ps.setInt(3, asignatura.getCreditos());
            ps.setInt(4, asignatura.getHorasTeoricas());
            ps.setInt(5, asignatura.getHorasPracticas());
            ps.setString(6, asignatura.getActividad());
            ps.setString(7, asignatura.getUsuario());
            ps.setDate(8, asignatura.getFechaHora());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("No se pudo guardar la asignatura:" + e.getMessage());
        }
    }

    public void update(Asignatura asignatura) {
        final String sql = "UPDATE Asignatura SET Nombre = ?, Creditos = ?, HorasTeoricas = ?, HorasPracticas = ?, Actividad = ?, Usuario = ?, " +
                "FechaHora = ? WHERE CodAsignatura = ?";

        try(Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, asignatura.getNombre());
            ps.setInt(2, asignatura.getCreditos());
            ps.setInt(3, asignatura.getHorasTeoricas());
            ps.setInt(4, asignatura.getHorasPracticas());
            ps.setString(5, asignatura.getActividad());
            ps.setString(6, asignatura.getUsuario());
            ps.setDate(7, asignatura.getFechaHora());
            ps.setString(8, asignatura.getCodAsignatura());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("No se pudo actualizar la asignatura:" + e.getMessage());
        }
    }

    public void delete(String CodAsignatura) {
        final String sql = "DELETE FROM Asignatura WHERE CodAsignatura = ? ";
        try(Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, CodAsignatura);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("No se pudo eliminar la asignatura:" + e.getMessage());
        }
    }

    public Map<String, Asignatura> findAll() {
        Map<String, Asignatura> lista = new HashMap<>();
        final String sql = "SELECT * FROM Asignatura";

        try (Connection connection = DatabaseConnection.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()) {
                Asignatura asignatura = new Asignatura();

                asignatura.setCodAsignatura(resultSet.getString("CodAsignatura"));
                asignatura.setNombre(resultSet.getString("Nombre"));
                asignatura.setCreditos(resultSet.getInt("Creditos"));
                asignatura.setHorasTeoricas(resultSet.getInt("HorasTeoricas"));
                asignatura.setHorasPracticas(resultSet.getInt("HorasPracticas"));
                asignatura.setActividad(resultSet.getString("Actividad"));
                asignatura.setUsuario(resultSet.getString("Usuario"));
                asignatura.setFechaHora(resultSet.getDate("FechaHora"));

                lista.put(asignatura.getCodAsignatura(), asignatura);
            }

        } catch(SQLException e) {
            System.out.println("No se pudo obtener la lista de asignaturas: " + e.getMessage());
        }

        return lista;
    }

}
