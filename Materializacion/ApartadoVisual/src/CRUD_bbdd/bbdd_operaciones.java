package CRUD_bbdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class bbdd_operaciones {

    public static boolean registrarResultadoPPT(String nombreUsuario, int piedra, int papel, int tijera, boolean partidaGanada, int puntosGanados) {
        String buscarJugador = """
                               SELECT j.ID_JUGADOR
                               FROM JUGADOR j
                               JOIN USUARIO u ON u.ID_USUARIO = j.ID_USUARIO
                               WHERE u.NOMBRE_USUARIO = ?
                               """;
        String actualizarJugador = "UPDATE JUGADOR SET PUNTOS = PUNTOS + ? WHERE ID_JUGADOR = ?";
        String actualizarEstadisticas = """
                                        UPDATE ESTADISTICAS_PPT
                                        SET PIEDRA = PIEDRA + ?,
                                            PAPEL = PAPEL + ?,
                                            TIJERA = TIJERA + ?,
                                            PARTIDAS_GANADAS = PARTIDAS_GANADAS + ?
                                        WHERE ID_JUGADOR = ?
                                        """;

        try (Connection con = ConexionBBDD.conexion()) {
            if (con == null) {
                return false;
            }

            con.setAutoCommit(false);

            try {
                int idJugador;

                try (PreparedStatement psBuscar = con.prepareStatement(buscarJugador)) {
                    psBuscar.setString(1, nombreUsuario);

                    try (ResultSet rs = psBuscar.executeQuery()) {
                        if (!rs.next()) {
                            con.rollback();
                            return false;
                        }
                        idJugador = rs.getInt("ID_JUGADOR");
                    }
                }

                asegurarEstadisticasPPT(con, idJugador);

                try (PreparedStatement psJugador = con.prepareStatement(actualizarJugador);
                     PreparedStatement psEstadisticas = con.prepareStatement(actualizarEstadisticas)) {

                    psJugador.setInt(1, puntosGanados);
                    psJugador.setInt(2, idJugador);
                    psJugador.executeUpdate();

                    psEstadisticas.setInt(1, piedra);
                    psEstadisticas.setInt(2, papel);
                    psEstadisticas.setInt(3, tijera);
                    psEstadisticas.setInt(4, partidaGanada ? 1 : 0);
                    psEstadisticas.setInt(5, idJugador);
                    psEstadisticas.executeUpdate();
                }

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

    public static boolean registrarResultado3EnRaya(String nombreUsuario, boolean partidaGanada, boolean empate) {
        String buscarJugador = """
                               SELECT j.ID_JUGADOR
                               FROM JUGADOR j
                               JOIN USUARIO u ON u.ID_USUARIO = j.ID_USUARIO
                               WHERE u.NOMBRE_USUARIO = ?
                               """;
        String actualizarJugador = "UPDATE JUGADOR SET PUNTOS = PUNTOS + ? WHERE ID_JUGADOR = ?";
        String actualizarEstadisticas = """
                                        UPDATE ESTADISTICAS_3_EN_RAYA
                                        SET PARTIDAS_JUGADAS = PARTIDAS_JUGADAS + 1,
                                            PARTIDAS_GANADAS = PARTIDAS_GANADAS + ?,
                                            PARTIDAS_PERDIDAS = PARTIDAS_PERDIDAS + ?,
                                            EMPATES = EMPATES + ?
                                        WHERE ID_JUGADOR = ?
                                        """;

        try (Connection con = ConexionBBDD.conexion()) {
            if (con == null) {
                return false;
            }

            con.setAutoCommit(false);

            try {
                int idJugador;

                try (PreparedStatement psBuscar = con.prepareStatement(buscarJugador)) {
                    psBuscar.setString(1, nombreUsuario);

                    try (ResultSet rs = psBuscar.executeQuery()) {
                        if (!rs.next()) {
                            con.rollback();
                            return false;
                        }
                        idJugador = rs.getInt("ID_JUGADOR");
                    }
                }

                asegurarEstadisticas3EnRaya(con, idJugador);

                int puntosGanados = partidaGanada ? 40 : (empate ? 10 : 0);

                try (PreparedStatement psJugador = con.prepareStatement(actualizarJugador);
                     PreparedStatement psEstadisticas = con.prepareStatement(actualizarEstadisticas)) {

                    psJugador.setInt(1, puntosGanados);
                    psJugador.setInt(2, idJugador);
                    psJugador.executeUpdate();

                    psEstadisticas.setInt(1, partidaGanada ? 1 : 0);
                    psEstadisticas.setInt(2, (!partidaGanada && !empate) ? 1 : 0);
                    psEstadisticas.setInt(3, empate ? 1 : 0);
                    psEstadisticas.setInt(4, idJugador);
                    psEstadisticas.executeUpdate();
                }

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

    private static void asegurarEstadisticasPPT(Connection con, int idJugador) throws SQLException {
        String sql = "INSERT IGNORE INTO ESTADISTICAS_PPT (ID_JUGADOR) VALUES (?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idJugador);
            ps.executeUpdate();
        }
    }

    private static void asegurarEstadisticas3EnRaya(Connection con, int idJugador) throws SQLException {
        String sql = "INSERT IGNORE INTO ESTADISTICAS_3_EN_RAYA (ID_JUGADOR) VALUES (?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idJugador);
            ps.executeUpdate();
        }
    }
}
