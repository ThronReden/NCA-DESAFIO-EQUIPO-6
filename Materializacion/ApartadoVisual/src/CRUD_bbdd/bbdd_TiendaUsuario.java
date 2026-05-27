package CRUD_bbdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class bbdd_TiendaUsuario {

    public static int obtenerPuntos(String nombreUsuario) {
        String sql = """
                     SELECT j.PUNTOS
                     FROM JUGADOR j
                     JOIN USUARIO u ON u.ID_USUARIO = j.ID_USUARIO
                     WHERE u.NOMBRE_USUARIO = ?
                     """;

        try (Connection con = ConexionBBDD.conexion()) {
            if (con == null) {
                return 0;
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, nombreUsuario);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("PUNTOS");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static boolean estaComprado(String nombreUsuario, int idArticulo) {
        if (idArticulo == 0) {
            return true;
        }

        String sql = """
                     SELECT COUNT(*)
                     FROM ARTICULOS_JUGADOR aj
                     JOIN JUGADOR j ON j.ID_JUGADOR = aj.ID_JUGADOR
                     JOIN USUARIO u ON u.ID_USUARIO = j.ID_USUARIO
                     WHERE u.NOMBRE_USUARIO = ? AND aj.ID_ARTICULOS = ?
                     """;

        try (Connection con = ConexionBBDD.conexion()) {
            if (con == null) {
                return false;
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, nombreUsuario);
                ps.setInt(2, idArticulo);

                try (ResultSet rs = ps.executeQuery()) {
                    return rs.next() && rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ResultadoCompra comprarArticulo(String nombreUsuario, int idArticulo, String nombreArticulo, int precio) {
        if (idArticulo == 0 || precio == 0) {
            return ResultadoCompra.COMPRADO;
        }

        String buscarJugador = """
                               SELECT j.ID_JUGADOR, j.PUNTOS
                               FROM JUGADOR j
                               JOIN USUARIO u ON u.ID_USUARIO = j.ID_USUARIO
                               WHERE u.NOMBRE_USUARIO = ?
                               """;
        String insertarArticulo = "INSERT IGNORE INTO TIENDA (ID_ARTICULOS, NOMBRE, PRECIO, ID_TIENDA) VALUES (?, ?, ?, ?)";
        String insertarCompra = "INSERT INTO ARTICULOS_JUGADOR (ID_JUGADOR, ID_ARTICULOS, FEHCACOMPRA) VALUES (?, ?, NOW())";
        String descontarPuntos = "UPDATE JUGADOR SET PUNTOS = PUNTOS - ? WHERE ID_JUGADOR = ?";

        try (Connection con = ConexionBBDD.conexion()) {
            if (con == null) {
                return ResultadoCompra.ERROR;
            }

            con.setAutoCommit(false);

            try {
                int idJugador;
                int puntos;

                try (PreparedStatement psJugador = con.prepareStatement(buscarJugador)) {
                    psJugador.setString(1, nombreUsuario);

                    try (ResultSet rs = psJugador.executeQuery()) {
                        if (!rs.next()) {
                            con.rollback();
                            return ResultadoCompra.ERROR;
                        }
                        idJugador = rs.getInt("ID_JUGADOR");
                        puntos = rs.getInt("PUNTOS");
                    }
                }

                if (estaComprado(nombreUsuario, idArticulo)) {
                    con.rollback();
                    return ResultadoCompra.COMPRADO;
                }

                if (puntos < precio) {
                    con.rollback();
                    return ResultadoCompra.SIN_PUNTOS;
                }

                try (PreparedStatement psArticulo = con.prepareStatement(insertarArticulo);
                     PreparedStatement psCompra = con.prepareStatement(insertarCompra);
                     PreparedStatement psPuntos = con.prepareStatement(descontarPuntos)) {

                    psArticulo.setInt(1, idArticulo);
                    psArticulo.setString(2, nombreArticulo);
                    psArticulo.setInt(3, precio);
                    psArticulo.setInt(4, idArticulo);
                    psArticulo.executeUpdate();

                    psCompra.setInt(1, idJugador);
                    psCompra.setInt(2, idArticulo);
                    psCompra.executeUpdate();

                    psPuntos.setInt(1, precio);
                    psPuntos.setInt(2, idJugador);
                    psPuntos.executeUpdate();
                }

                con.commit();
                return ResultadoCompra.COMPRADO;
            } catch (SQLException e) {
                con.rollback();
                e.printStackTrace();
                return ResultadoCompra.ERROR;
            } finally {
                con.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return ResultadoCompra.ERROR;
        }
    }

    public enum ResultadoCompra {
        COMPRADO,
        SIN_PUNTOS,
        ERROR
    }
}
