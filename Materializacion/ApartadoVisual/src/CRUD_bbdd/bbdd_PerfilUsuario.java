package CRUD_bbdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class bbdd_PerfilUsuario {

    public static DatosPerfil obtenerDatosPerfil(String nombreUsuario) {
        String sql = "SELECT ID_USUARIO, NOMBRE_USUARIO, CORREO, `CONTRASEÑA` FROM USUARIO WHERE NOMBRE_USUARIO = ?";

        try (Connection con = ConexionBBDD.conexion()) {
            if (con == null) {
                return null;
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, nombreUsuario);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return new DatosPerfil(
                                rs.getInt("ID_USUARIO"),
                                rs.getString("NOMBRE_USUARIO"),
                                rs.getString("CORREO"),
                                rs.getString("CONTRASEÑA")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static EstadisticasPerfil obtenerEstadisticasPerfil(String nombreUsuario) {
        String sql = """
                     SELECT j.PUNTOS,
                            COALESCE(e.PIEDRA, 0) AS PIEDRA,
                            COALESCE(e.PAPEL, 0) AS PAPEL,
                            COALESCE(e.TIJERA, 0) AS TIJERA,
                            COALESCE(e.PARTIDAS_GANADAS, 0) AS PARTIDAS_GANADAS_PPT,
                            COALESCE(r.PARTIDAS_GANADAS, 0) AS PARTIDAS_GANADAS_3R
                     FROM JUGADOR j
                     JOIN USUARIO u ON u.ID_USUARIO = j.ID_USUARIO
                     LEFT JOIN ESTADISTICAS_PPT e ON e.ID_JUGADOR = j.ID_JUGADOR
                     LEFT JOIN ESTADISTICAS_3_EN_RAYA r ON r.ID_JUGADOR = j.ID_JUGADOR
                     WHERE u.NOMBRE_USUARIO = ?
                     """;

        try (Connection con = ConexionBBDD.conexion()) {
            if (con == null) {
                return null;
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, nombreUsuario);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return new EstadisticasPerfil(
                                rs.getInt("PUNTOS"),
                                rs.getInt("PIEDRA"),
                                rs.getInt("PAPEL"),
                                rs.getInt("TIJERA"),
                                rs.getInt("PARTIDAS_GANADAS_PPT"),
                                rs.getInt("PARTIDAS_GANADAS_3R")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean existeOtroUsuarioOCorreo(int idUsuario, String nombreUsuario, String correo) {
        String sql = "SELECT COUNT(*) FROM USUARIO WHERE ID_USUARIO <> ? AND (NOMBRE_USUARIO = ? OR CORREO = ?)";

        try (Connection con = ConexionBBDD.conexion()) {
            if (con == null) {
                return true;
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, idUsuario);
                ps.setString(2, nombreUsuario);
                ps.setString(3, correo);

                try (ResultSet rs = ps.executeQuery()) {
                    return rs.next() && rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

    public static boolean actualizarDatosPerfil(int idUsuario, String nombreUsuario, String correo, String contrasena) {
        String sql = "UPDATE USUARIO SET NOMBRE_USUARIO = ?, CORREO = ?, `CONTRASEÑA` = ? WHERE ID_USUARIO = ?";

        try (Connection con = ConexionBBDD.conexion()) {
            if (con == null) {
                return false;
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, nombreUsuario);
                ps.setString(2, correo);
                ps.setString(3, contrasena);
                ps.setInt(4, idUsuario);
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static class DatosPerfil {

        private final int idUsuario;
        private final String nombreUsuario;
        private final String correo;
        private final String contrasena;

        public DatosPerfil(int idUsuario, String nombreUsuario, String correo, String contrasena) {
            this.idUsuario = idUsuario;
            this.nombreUsuario = nombreUsuario;
            this.correo = correo;
            this.contrasena = contrasena;
        }

        public int getIdUsuario() {
            return idUsuario;
        }

        public String getNombreUsuario() {
            return nombreUsuario;
        }

        public String getCorreo() {
            return correo;
        }

        public String getContrasena() {
            return contrasena;
        }
    }

    public static class EstadisticasPerfil {

        private final int puntos;
        private final int piedra;
        private final int papel;
        private final int tijera;
        private final int partidasGanadasPpt;
        private final int partidasGanadas3R;

        public EstadisticasPerfil(int puntos, int piedra, int papel, int tijera, int partidasGanadasPpt, int partidasGanadas3R) {
            this.puntos = puntos;
            this.piedra = piedra;
            this.papel = papel;
            this.tijera = tijera;
            this.partidasGanadasPpt = partidasGanadasPpt;
            this.partidasGanadas3R = partidasGanadas3R;
        }

        public int getPuntos() {
            return puntos;
        }

        public int getPiedra() {
            return piedra;
        }

        public int getPapel() {
            return papel;
        }

        public int getTijera() {
            return tijera;
        }

        public int getPartidasGanadasPpt() {
            return partidasGanadasPpt;
        }

        public int getPartidasGanadas3R() {
            return partidasGanadas3R;
        }
    }
}
