package database;

import estructura.Grupo;
import estructura.GrupoHorario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GrupoHorarioDAO {

    private static final GrupoHorarioDAO INSTANCE = new GrupoHorarioDAO();

    private GrupoHorarioDAO() {

    }

    public static GrupoHorarioDAO getInstance() {
        return INSTANCE;
    }

    public void save(GrupoHorario grupoHorario) {
        final String sql = "INSERT INTO GrupoHorario (CodPeriodoAcademico, CodAsignatura, NumGrupo, Dia, HoraInicial, HoraFinal) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try(Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, grupoHorario.getCodPeriodoAcademico());
            ps.setString(2, grupoHorario.getCodAsignatura());
            ps.setString(3, grupoHorario.getNumGrupo());
            ps.setInt(4, grupoHorario.getDia());
            ps.setTime(5, grupoHorario.getHoraInicial());
            ps.setTime(6, grupoHorario.getHoraFinal());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("No se pudo guardar el grupoHorario:" + e.getMessage());
        }
    }

    public void delete(GrupoHorario grupoHorario) {
        final String sql = "DELETE FROM GrupoHorario WHERE CodPeriodoAcademico = ? AND CodAsignatura = ? AND NumGrupo = ? " +
                "AND Dia = ? AND  HoraInicial = ? ";
        try(Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, grupoHorario.getCodPeriodoAcademico());
            ps.setString(2, grupoHorario.getCodAsignatura());
            ps.setString(3, grupoHorario.getNumGrupo());
            ps.setInt(4, grupoHorario.getDia());
            ps.setTime(5, grupoHorario.getHoraInicial());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("No se pudo eliminar el grupoHorario:" + e.getMessage());
        }
    }


    public List<GrupoHorario> horariosPorGrupo(Grupo grupo) {
        final String sql = "SELECT * FROM GrupoHorario WHERE CodPeriodoAcademico = ? AND CodAsignatura = ? AND NumGrupo = ?";
        List<GrupoHorario> lista = new ArrayList<>();
        try(Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, grupo.getCodPeriodoAcademico());
            ps.setString(2, grupo.getCodAsignatura());
            ps.setString(3, grupo.getNumGrupo());
            try(ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    lista.add(new GrupoHorario(
                            rs.getString("CodPeriodoAcademico"),
                            rs.getString("CodAsignatura"),
                            rs.getString("NumGrupo"),
                            rs.getInt("Dia"),
                            rs.getTime("HoraInicial"),
                            rs.getTime("HoraFinal")));
                }
            }
        } catch (SQLException e) {
            System.out.println("No se pudo encontrar los horarios del grupo:" + e.getMessage());
        }
        return lista;
    }

}
