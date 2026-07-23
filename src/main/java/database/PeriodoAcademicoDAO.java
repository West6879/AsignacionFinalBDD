package database;

import estructura.PeriodoAcademico;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class PeriodoAcademicoDAO {

    public static final PeriodoAcademicoDAO INSTANCE = new PeriodoAcademicoDAO();

    public PeriodoAcademicoDAO() {}

    public static PeriodoAcademicoDAO getInstance() {
        return INSTANCE;
    }

    public void save(PeriodoAcademico periodo) {
        if (!periodo.esCodigoValido()) {
            throw new IllegalArgumentException("El código de período es inválido (debe cumplir formato 8 dígitos + término 1-3 y años consecutivos).");
        }
        if (!periodo.esDuracionClasesValida()) {
            throw new IllegalArgumentException("La duración de clases debe ser de al menos 70 días.");
        }
        if (!periodo.sonFechasEnRango()) {
            throw new IllegalArgumentException("Las fechas límite están fuera del rango de inicio y fin del período.");
        }

        // --- 2. SENTENCIA SQL (Se incluyó FechaFin que faltaba) ---
        final String sql = "INSERT INTO [Periodo Academico] " +
                "(CodPeridoAcademico, Descripcion, FechaInicio, FechaFin, FechaInicioClases, FechaFinClases, FechaLimitePrematricula, FechaLimiteRetiro, FechaLimitePublicacionCalif) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, periodo.getCodPeriodoAcademico());
            ps.setString(2, periodo.getDescripcion());
            ps.setDate(3, periodo.getFechaInicio());
            ps.setDate(4, periodo.getFechaFin()); // Agregado
            ps.setDate(5, periodo.getFechaInicioClases());
            ps.setDate(6, periodo.getFechaFinClases());
            ps.setDate(7, periodo.getFechaLimitePrematricula());
            ps.setDate(8, periodo.getFechaLimiteRetiro());
            ps.setDate(9, periodo.getFechaLimitePublicacionCalif());

            ps.executeUpdate();
            System.out.println("Periodo Académico guardado en la BD.");

        } catch (SQLException e) {
            System.err.println("ERROR AL GUARDAR PERIODO EN SQL:");
            e.printStackTrace();
        }
    }

    public Map<String, PeriodoAcademico> findAll() {
        Map<String, PeriodoAcademico> lista = new HashMap<>();
        final String sql = "SELECT * FROM [Periodo Academico]";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            while(rs.next()) {
                PeriodoAcademico p = new PeriodoAcademico();
                p.setCodPeriodoAcademico(rs.getString("CodPeridoAcademico"));
                p.setDescripcion(rs.getString("Descripcion"));
                p.setFechaInicio(rs.getDate("FechaInicio"));
                p.setFechaFin(rs.getDate("FechaFin")); // Agregado
                p.setFechaInicioClases(rs.getDate("FechaInicioClases"));
                p.setFechaFinClases(rs.getDate("FechaFinClases"));
                p.setFechaLimitePrematricula(rs.getDate("FechaLimitePrematricula"));
                p.setFechaLimiteRetiro(rs.getDate("FechaLimiteRetiro"));
                p.setFechaLimitePublicacionCalif(rs.getDate("FechaLimitePublicacionCalif"));

                lista.put(p.getCodPeriodoAcademico(), p);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}