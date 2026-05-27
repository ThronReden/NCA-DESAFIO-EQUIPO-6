package CRUD_bbdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class bbdd_inicioSesion {

    public static boolean credencialesValidas(String nombreOCorreo, String contrasena) {
        return obtenerNombreUsuario(nombreOCorreo, contrasena) != null;
    }

    public static String obtenerNombreUsuario(String nombreOCorreo, String contrasena) {
        String sql = """
                     SELECT u.ID_USUARIO, u.NOMBRE_USUARIO
                     FROM USUARIO u
                     WHERE (u.NOMBRE_USUARIO = ? OR u.CORREO = ?)
                     AND u.`CONTRASEÑA` = ?
                     """;

        try (Connection con = ConexionBBDD.conexion()) {
            if (con == null) {
                return null;
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, nombreOCorreo);
                ps.setString(2, nombreOCorreo);
                ps.setString(3, contrasena);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        actualizarUltimaConexion(con, rs.getInt("ID_USUARIO"));
                        return rs.getString("NOMBRE_USUARIO");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static void actualizarUltimaConexion(Connection con, int idUsuario) throws SQLException {
        String sql = "UPDATE JUGADOR SET ULTIMA_CONEXION = NOW() WHERE ID_USUARIO = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ps.executeUpdate();
        }
    }
}
