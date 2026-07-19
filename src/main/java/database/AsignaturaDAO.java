package database;

import estructura.Asignatura;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AsignaturaDAO {

    public static final AsignaturaDAO INSTANCE = new AsignaturaDAO();

    public AsignaturaDAO() {
    }

    public static AsignaturaDAO getInstance() { return INSTANCE; }


    public void save(Asignatura asignatura) {
        final String sql = "INSERT INTO Asignatura (CodAsginatura, Nombre, Creditos, HorasTeoricas, HorasPracticas, Actividad, Usuario, FechaHora) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try(Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement ps =connection.prepareStatement(sql);
            ps.setString(1, asignatura.getCodAsginatura());
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
        final String sql = "UPDATE Asginatura SET Nombre = ?, Creditos = ?, HorasTeoricas = ?, HorasPracticas = ?, Actividad = ?, Usuario = ?, " +
                "FechaHora = ? WHERE CodAsginatura = ?";

        try(Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement ps =connection.prepareStatement(sql);
            ps.setString(1, asignatura.getNombre());
            ps.setInt(2, asignatura.getCreditos());
            ps.setInt(3, asignatura.getHorasTeoricas());
            ps.setInt(4, asignatura.getHorasTeoricas());
            ps.setString(5, asignatura.getActividad());
            ps.setString(6, asignatura.getUsuario());
            ps.setDate(7, asignatura.getFechaHora());
            ps.setString(8, asignatura.getCodAsginatura());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("No se pudo actualizar la asignatura:" + e.getMessage());
        }
    }

    public void delete(String CodAsignatura) {
        final String sql = "DELETE FROM Asginatura WHERE CodAsginatura = ? ";
        try(Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement ps =connection.prepareStatement(sql);
            ps.setString(1, CodAsignatura);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("No se pudo eliminar la asignatura:" + e.getMessage());
        }
    }

    public List<Asignatura> findAll() {
        List<Asignatura> lista = new ArrayList<>();
        final String sql = "SELECT * FROM Asignatura";

        try (Connection connection = DatabaseConnection.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()) {
                Asignatura asignatura = new Asignatura();

                asignatura.setCodAsginatura(resultSet.getString("CodAsignatura"));
                asignatura.setNombre(resultSet.getString("Nombre"));
                asignatura.setCreditos(resultSet.getInt("Creditos"));
                asignatura.setHorasTeoricas(resultSet.getInt("HorasTeoricas"));
                asignatura.setHorasPracticas(resultSet.getInt("HorasPracticas"));
                asignatura.setActividad(resultSet.getString("Actividad"));
                asignatura.setUsuario(resultSet.getString("Usuario"));
                asignatura.setFechaHora(resultSet.getDate("FechaHora"));

                lista.add(asignatura);
            }

        } catch(SQLException e) {
            System.out.println("No se pudo obtener la lista de asignaturas: " + e.getMessage());
        }

        return lista;
    }

}
