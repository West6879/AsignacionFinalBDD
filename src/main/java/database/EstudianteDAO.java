package database;

import estructura.Estudiante;
import javafx.scene.paint.Color;

import java.sql.*;
import java.util.*;

public class EstudianteDAO {

    private static final EstudianteDAO INSTANCE = new EstudianteDAO();

    private EstudianteDAO() {
    }

    public static EstudianteDAO getInstance() { return INSTANCE;}


    public void save(Estudiante estudiante) {
        final String sql = "INSERT INTO Estudiante (Id, Nombre1, Nombre2, Apellido1, Apellido2, Carrera, CategoriaPago, Nacionalidad, Direccion, FechaNacimiento) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try(Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, estudiante.getId());
            ps.setString(2, estudiante.getNombre1());
            ps.setString(3, estudiante.getNombre2());
            ps.setString(4, estudiante.getApellido1());
            ps.setString(5, estudiante.getApellido2());
            ps.setString(6, estudiante.getCarrera());
            ps.setString(7, estudiante.getCategoriaPago());
            ps.setString(8, estudiante.getNacionalidad());
            ps.setString(9, estudiante.getDireccion());
            ps.setDate(10, estudiante.getFechaNacimiento());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("No se pudo guardar el estudiante:" + e.getMessage());
        }
    }

    public void update(Estudiante estudiante) {
        final String sql = "UPDATE Estudiante SET Nombre1 = ?, Nombre2 = ?, Apellido1 = ?, Apellido2 = ?, Carrera = ?, CategoriaPago = ?, Nacionalidad = ?, " +
                "Direccion = ?, FechaNacimiento = ? WHERE Id = ?";

        try(Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, estudiante.getNombre1());
            ps.setString(2, estudiante.getNombre2());
            ps.setString(3, estudiante.getApellido1());
            ps.setString(4, estudiante.getApellido2());
            ps.setString(5, estudiante.getCarrera());
            ps.setString(6, estudiante.getCategoriaPago());
            ps.setString(7, estudiante.getNacionalidad());
            ps.setString(8, estudiante.getDireccion());
            ps.setDate(9, estudiante.getFechaNacimiento());
            ps.setString(10, estudiante.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("No se pudo actualizar el estudiante:" + e.getMessage());
        }
    }

    public void delete(String id) {
        final String sql = "DELETE FROM Estudiante WHERE Id = ? ";
        try(Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("No se pudo eliminar el estudiante:" + e.getMessage());
        }
    }

    public Map<String, Estudiante> findAll() {
        Map<String, Estudiante> lista = new HashMap<>();
        final String sql = "SELECT * FROM Estudiante";

        try (Connection connection = DatabaseConnection.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()) {
                Estudiante estudiante = new Estudiante();

                estudiante.setId(resultSet.getString("Id"));
                estudiante.setNombre1(resultSet.getString("Nombre1"));
                estudiante.setNombre2(resultSet.getString("Nombre2"));
                estudiante.setApellido1(resultSet.getString("Apellido1"));
                estudiante.setApellido2(resultSet.getString("Apellido2"));
                estudiante.setCarrera(resultSet.getString("Carrera"));
                estudiante.setCategoriaPago(resultSet.getString("CategoriaPago"));
                estudiante.setNacionalidad(resultSet.getString("Nacionalidad"));
                estudiante.setDireccion(resultSet.getString("Direccion"));
                estudiante.setFechaNacimiento(resultSet.getDate("FechaNacimiento"));

                lista.put(estudiante.getId(), estudiante);
            }

        } catch(SQLException e) {
            System.out.println("No se pudo obtener la lista de estudiantes: " + e.getMessage());
        }

        return lista;
    }




}
