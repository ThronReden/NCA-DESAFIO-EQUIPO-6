package CRUD_bbdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class bbdd_registroUsuario {

    public static boolean existeUsuarioOCorreo(String nombreUsuario, String correo) {
        String sql = "SELECT COUNT(*) FROM USUARIO WHERE NOMBRE_USUARIO = ? OR CORREO = ?";

        try (Connection con = ConexionBBDD.conexion()) {
            if (con == null) {
                return false;
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, nombreUsuario);
                ps.setString(2, correo);

                try (ResultSet rs = ps.executeQuery()) {
                    return rs.next() && rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean registrarUsuario(String nombreUsuario, String correo, String contrasena) {
        String insertarUsuario = "INSERT INTO USUARIO (ID_USUARIO, NOMBRE_USUARIO, CORREO, `CONTRASEÑA`) VALUES (?, ?, ?, ?)";
        String insertarJugador = "INSERT INTO JUGADOR (ID_JUGADOR, PUNTOS, ULTIMA_CONEXION, ID_USUARIO) VALUES (?, 0, NOW(), ?)";
        String insertarEstadisticasPpt = "INSERT INTO ESTADISTICAS_PPT (ID_JUGADOR) VALUES (?)";
        String insertarEstadisticasPptls = "INSERT INTO ESTADISTICAS_PPTLS (ID_JUGADOR) VALUES (?)";
        String insertarEstadisticasRaya = "INSERT INTO ESTADISTICAS_3_EN_RAYA (ID_JUGADOR) VALUES (?)";

        try (Connection con = ConexionBBDD.conexion()) {
            if (con == null) {
                return false;
            }

            con.setAutoCommit(false);

            int idUsuario = siguienteId(con, "USUARIO", "ID_USUARIO");
            int idJugador = siguienteId(con, "JUGADOR", "ID_JUGADOR");

            try (PreparedStatement psUsuario = con.prepareStatement(insertarUsuario);
                 PreparedStatement psJugador = con.prepareStatement(insertarJugador);
                 PreparedStatement psPpt = con.prepareStatement(insertarEstadisticasPpt);
                 PreparedStatement psPptls = con.prepareStatement(insertarEstadisticasPptls);
                 PreparedStatement psRaya = con.prepareStatement(insertarEstadisticasRaya)) {

                psUsuario.setInt(1, idUsuario);
                psUsuario.setString(2, nombreUsuario);
                psUsuario.setString(3, correo);
                psUsuario.setString(4, contrasena);
                psUsuario.executeUpdate();

                psJugador.setInt(1, idJugador);
                psJugador.setInt(2, idUsuario);
                psJugador.executeUpdate();

                psPpt.setInt(1, idJugador);
                psPpt.executeUpdate();

                psPptls.setInt(1, idJugador);
                psPptls.executeUpdate();

                psRaya.setInt(1, idJugador);
                psRaya.executeUpdate();

                con.commit();
                return true;
            } catch (SQLException e) {
                con.rollback();
                e.printStackTrace();
                return false;
            } finally {
                con.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static int siguienteId(Connection con, String tabla, String columna) throws SQLException {
        String sql = "SELECT COALESCE(MAX(" + columna + "), 0) + 1 FROM " + tabla;

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }

        return 1;
    }
}
