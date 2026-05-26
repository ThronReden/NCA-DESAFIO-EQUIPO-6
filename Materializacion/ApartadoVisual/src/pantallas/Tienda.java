package pantallas;

import java.awt.Color;
import java.awt.Cursor;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author LENOVO
 */
public class Tienda extends javax.swing.JFrame {

    /**
     * Creates new form Perfil_Usuario
     */
    public Tienda() {
        initComponents();

        Menu_Ampliado.setVisible(false);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        prepararCompraFondos();
        prepararCompraIconosPerfil();
        prepararCompraPersonajes();
        aplicarFondoActivo();
        aplicarIconoPerfilActivo();
        aplicarPersonajeTiendaActivo();
    }

    private void prepararCompraFondos() {
        prepararFondo(Negro, "Negro", 0, new Color(15, 15, 15), null);
        prepararFondo(Negro2, "Rojo oscuro", 100, new Color(54, 18, 18), precioRojo);
        prepararFondo(Negro3, "Verde oscuro", 300, new Color(18, 36, 19), precioVerde);
        prepararFondo(Negro4, "Azul oscuro", 700, new Color(18, 16, 49), precioAzul);
        prepararFondo(Negro1, "Blanco", 1000, new Color(235, 235, 230), precioBlanco);
    }

    private void prepararFondo(JLabel fondo, String nombre, int precio, Color color, JLabel precioLabel) {
        String articulo = "fondo:" + nombre;
        fondo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        fondo.setToolTipText(nombre + " - " + precio + " monedas");
        fondo.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        if (AppTheme.estaComprado(articulo)) {
            ocultarPrecio(precioLabel);
        }
        fondo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                comprarFondo(nombre, precio, color, precioLabel);
            }

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                actualizarBordesFondos();
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                actualizarBordesFondos();
            }
        });
    }

    private void comprarFondo(String nombre, int precio, Color color, JLabel precioLabel) {
        String mensaje = "Vas a comprar este fondo: " + nombre + "\nPrecio: " + precio + " monedas\n\nQuieres aplicarlo?";
        int respuesta = JOptionPane.showConfirmDialog(this, mensaje, "Comprar fondo", JOptionPane.YES_NO_OPTION);

        if (respuesta == JOptionPane.YES_OPTION) {
            AppTheme.setFondoActivo(color);
            AppTheme.registrarCompra("fondo:" + nombre);
            ocultarPrecio(precioLabel);
            aplicarFondoActivo();
        }
    }

    private void aplicarFondoActivo() {
        Color fondo = AppTheme.getFondoActivo();
        shop.setBackground(fondo);
        PanelContenido.setBackground(fondo);
        Boton_Menu_Desplegable.setBackground(fondo);
        Boton_Cierre_Perfil.setBackground(fondo);
        jLabel18.setBackground(fondo);
        actualizarBordesFondos();
    }

    private void actualizarBordesFondos() {
        marcarFondoActivo(Negro, new Color(15, 15, 15));
        marcarFondoActivo(Negro2, new Color(54, 18, 18));
        marcarFondoActivo(Negro3, new Color(18, 36, 19));
        marcarFondoActivo(Negro4, new Color(18, 16, 49));
        marcarFondoActivo(Negro1, new Color(235, 235, 230));
    }

    private void marcarFondoActivo(JLabel fondo, Color color) {
        if (AppTheme.getFondoActivo().equals(color)) {
            fondo.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 130), 5));
        } else {
            fondo.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        }
    }

    private void prepararCompraIconosPerfil() {
        prepararIconoPerfil(Usuario, "Usuario", 0, "/imagenes/iconsUsuarioPerfil.png", null);
        prepararIconoPerfil(PajaroLoco, "Pajaro Loco", 250, "/imagenes/PajaroLoco.png", precioPajaro);
        prepararIconoPerfil(Thron, "Thron", 500, "/imagenes/Thron.png", precioThron);
        prepararIconoPerfil(MonaChina, "Mona China", 1000, "/imagenes/MonaChina.png", precioMonaChina);
        prepararIconoPerfil(TheRock, "The Rock", 2000, "/imagenes/Roca.gif", precioRoca);
    }

    private void prepararIconoPerfil(JLabel icono, String nombre, int precio, String ruta, JLabel precioLabel) {
        String articulo = "icono:" + ruta;
        icono.setCursor(new Cursor(Cursor.HAND_CURSOR));
        icono.setToolTipText(nombre + " - " + precio + " monedas");
        icono.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        if (AppTheme.estaComprado(articulo)) {
            ocultarPrecio(precioLabel);
        }
        icono.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                comprarIconoPerfil(nombre, precio, ruta, precioLabel);
            }

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                actualizarBordesIconosPerfil();
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                actualizarBordesIconosPerfil();
            }
        });
    }

    private void comprarIconoPerfil(String nombre, int precio, String ruta, JLabel precioLabel) {
        String mensaje = "Vas a comprar este icono de perfil: " + nombre + "\nPrecio: " + precio + " monedas\n\nQuieres aplicarlo?";
        int respuesta = JOptionPane.showConfirmDialog(this, mensaje, "Comprar icono", JOptionPane.YES_NO_OPTION);

        if (respuesta == JOptionPane.YES_OPTION) {
            AppTheme.setIconoPerfilActivo(ruta);
            AppTheme.registrarCompra("icono:" + ruta);
            ocultarPrecio(precioLabel);
            aplicarIconoPerfilActivo();
        }
    }

    private void aplicarIconoPerfilActivo() {
        actualizarBordesIconosPerfil();
    }

    private void actualizarBordesIconosPerfil() {
        marcarIconoPerfilActivo(Usuario, "/imagenes/iconsUsuarioPerfil.png");
        marcarIconoPerfilActivo(PajaroLoco, "/imagenes/PajaroLoco.png");
        marcarIconoPerfilActivo(Thron, "/imagenes/Thron.png");
        marcarIconoPerfilActivo(MonaChina, "/imagenes/MonaChina.png");
        marcarIconoPerfilActivo(TheRock, "/imagenes/Roca.gif");
    }

    private void marcarIconoPerfilActivo(JLabel icono, String ruta) {
        if (AppTheme.getIconoPerfilActivoRuta().equals(ruta)) {
            icono.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 130), 5));
        } else {
            icono.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        }
    }

    private void prepararCompraPersonajes() {
        prepararPersonaje(Sheldon, "Sheldon", 1000, "/imagenes/Sheldon.png", "main", null);
        prepararPersonaje(GLaDOS, "GLaDOS", 1000, "/imagenes/GLaDOS.png", "main", precioGLaDOS);
        prepararPersonaje(Griff, "Griff", 1000, "/imagenes/Griff.png", "tienda", null);
        prepararPersonaje(ToomNook, "Tom Nook", 1000, "/imagenes/TomNook.png", "tienda", precioToom);
        prepararPersonaje(MrNintendo, "Mr Nintendo", 1000, "/imagenes/MrNintendo.png", "perfil", null);
        prepararPersonaje(Bmo, "BMO", 1000, "/imagenes/BMO.png", "perfil", precioBmo);
        prepararPersonaje(Gon, "Gon", 1000, "/imagenes/GonPPT.png", "ppt", null);
        prepararPersonaje(Josep, "Joseph Joestar", 1000, "/imagenes/JosephJoestar.png", "ppt", precioJosep);
        prepararPersonaje(Hand, "Master Hand", 1000, "/imagenes/Master_Hand.png", "pptls", null);
        prepararPersonaje(Spock, "Spock", 1000, "/imagenes/SpockPPTLS.png", "pptls", precioSpock);
        prepararPersonaje(RickYMorty, "Rick y Morty", 1000, "/imagenes/RickyMorty .png", "raya", null);
        prepararPersonaje(Bill, "Bill Cipher", 1000, "/imagenes/BillCipher.png", "raya", precioBill);
    }

    private void prepararPersonaje(JLabel personaje, String nombre, int precio, String ruta, String pantalla, JLabel precioLabel) {
        String articulo = "personaje:" + pantalla + ":" + ruta;
        personaje.setCursor(new Cursor(Cursor.HAND_CURSOR));
        personaje.setToolTipText(nombre + " - " + precio + " monedas");
        personaje.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        if (AppTheme.estaComprado(articulo)) {
            ocultarPrecio(precioLabel);
        }
        personaje.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                comprarPersonaje(nombre, precio, ruta, pantalla, precioLabel);
            }

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                actualizarBordesPersonajes();
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                actualizarBordesPersonajes();
            }
        });
    }

    private void comprarPersonaje(String nombre, int precio, String ruta, String pantalla, JLabel precioLabel) {
        String mensaje = "Vas a comprar este personaje: " + nombre + "\nPrecio: " + precio + " monedas\n\nQuieres aplicarlo?";
        int respuesta = JOptionPane.showConfirmDialog(this, mensaje, "Comprar personaje", JOptionPane.YES_NO_OPTION);

        if (respuesta == JOptionPane.YES_OPTION) {
            switch (pantalla) {
                case "main" -> AppTheme.setPersonajeMainActivo(ruta);
                case "tienda" -> AppTheme.setPersonajeTiendaActivo(ruta);
                case "perfil" -> AppTheme.setPersonajePerfilActivo(ruta);
                case "ppt" -> AppTheme.setPersonajePptActivo(ruta);
                case "pptls" -> AppTheme.setPersonajePptlsActivo(ruta);
                case "raya" -> AppTheme.setPersonajeRayaActivo(ruta);
                default -> {
                }
            }
            AppTheme.registrarCompra("personaje:" + pantalla + ":" + ruta);
            ocultarPrecio(precioLabel);
            aplicarPersonajeTiendaActivo();
            actualizarBordesPersonajes();
        }
    }

    private void ocultarPrecio(JLabel precioLabel) {
        if (precioLabel != null) {
            precioLabel.setText("");
            precioLabel.setVisible(false);
        }
    }

    private void aplicarPersonajeTiendaActivo() {
        Griff_Dineros.setIcon(AppTheme.getPersonajeTiendaActivo());
        bocadilloText.setVisible(!AppTheme.getPersonajeTiendaActivoRuta().equals("/imagenes/TomNook.png"));
        actualizarBordesPersonajes();
    }

    private void actualizarBordesPersonajes() {
        marcarPersonajeActivo(Sheldon, AppTheme.getPersonajeMainActivoRuta(), "/imagenes/Sheldon.png");
        marcarPersonajeActivo(GLaDOS, AppTheme.getPersonajeMainActivoRuta(), "/imagenes/GLaDOS.png");
        marcarPersonajeActivo(Griff, AppTheme.getPersonajeTiendaActivoRuta(), "/imagenes/Griff.png");
        marcarPersonajeActivo(ToomNook, AppTheme.getPersonajeTiendaActivoRuta(), "/imagenes/TomNook.png");
        marcarPersonajeActivo(MrNintendo, AppTheme.getPersonajePerfilActivoRuta(), "/imagenes/MrNintendo.png");
        marcarPersonajeActivo(Bmo, AppTheme.getPersonajePerfilActivoRuta(), "/imagenes/BMO.png");
        marcarPersonajeActivo(Gon, AppTheme.getPersonajePptActivoRuta(), "/imagenes/GonPPT.png");
        marcarPersonajeActivo(Josep, AppTheme.getPersonajePptActivoRuta(), "/imagenes/JosephJoestar.png");
        marcarPersonajeActivo(Hand, AppTheme.getPersonajePptlsActivoRuta(), "/imagenes/Master_Hand.png");
        marcarPersonajeActivo(Spock, AppTheme.getPersonajePptlsActivoRuta(), "/imagenes/SpockPPTLS.png");
        marcarPersonajeActivo(RickYMorty, AppTheme.getPersonajeRayaActivoRuta(), "/imagenes/RickyMorty .png");
        marcarPersonajeActivo(Bill, AppTheme.getPersonajeRayaActivoRuta(), "/imagenes/BillCipher.png");
    }

    private void marcarPersonajeActivo(JLabel personaje, String rutaActiva, String rutaPersonaje) {
        if (rutaActiva.equals(rutaPersonaje)) {
            personaje.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 130), 5));
        } else {
            personaje.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel5 = new javax.swing.JLabel();
        shop = new javax.swing.JPanel();
        Boton_Menu_Desplegable = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        Menu_Ampliado = new javax.swing.JPanel();
        Boton_Perfil = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        Boton_Juego = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        PanelContenido = new javax.swing.JPanel();
        tituloTienda = new javax.swing.JLabel();
        iconMoneda = new javax.swing.JLabel();
        panelPuntos = new javax.swing.JLabel();
        Tienda = new javax.swing.JPanel();
        precioBlanco = new javax.swing.JLabel();
        precioAzul = new javax.swing.JLabel();
        precioVerde = new javax.swing.JLabel();
        precioRojo = new javax.swing.JLabel();
        precioPajaro = new javax.swing.JLabel();
        precioThron = new javax.swing.JLabel();
        precioMonaChina = new javax.swing.JLabel();
        precioRoca = new javax.swing.JLabel();
        precioToom = new javax.swing.JLabel();
        precioBmo = new javax.swing.JLabel();
        precioSpock = new javax.swing.JLabel();
        precioJosep = new javax.swing.JLabel();
        precioBill = new javax.swing.JLabel();
        ToomNook = new javax.swing.JLabel();
        Usuario = new javax.swing.JLabel();
        PajaroLoco = new javax.swing.JLabel();
        Thron = new javax.swing.JLabel();
        MonaChina = new javax.swing.JLabel();
        TheRock = new javax.swing.JLabel();
        Griff = new javax.swing.JLabel();
        MrNintendo = new javax.swing.JLabel();
        Hand = new javax.swing.JLabel();
        Gon = new javax.swing.JLabel();
        RickYMorty = new javax.swing.JLabel();
        Sheldon = new javax.swing.JLabel();
        GLaDOS = new javax.swing.JLabel();
        Bill = new javax.swing.JLabel();
        Josep = new javax.swing.JLabel();
        Spock = new javax.swing.JLabel();
        Bmo = new javax.swing.JLabel();
        Negro = new javax.swing.JLabel();
        Negro1 = new javax.swing.JLabel();
        Negro2 = new javax.swing.JLabel();
        Negro3 = new javax.swing.JLabel();
        Negro4 = new javax.swing.JLabel();
        precioGLaDOS = new javax.swing.JLabel();
        tienda = new javax.swing.JLabel();
        bocadilloText = new javax.swing.JLabel();
        puntuacion_jugador1 = new javax.swing.JLabel();
        Boton_Cierre_Perfil = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        Griff_Dineros = new javax.swing.JLabel();

        jLabel5.setFont(new java.awt.Font("Dubai", 1, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Partidas ganadas al 3 en raya: {partidas_ganadas3Raya}");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(6);
        setUndecorated(true);
        setResizable(false);

        shop.setBackground(new java.awt.Color(15, 15, 15));
        shop.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Boton_Menu_Desplegable.setBackground(new java.awt.Color(15, 15, 15));
        Boton_Menu_Desplegable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Boton_Menu_Desplegable.setLayout(new java.awt.CardLayout());

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/iconsMenu.png"))); // NOI18N
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel7MouseExited(evt);
            }
        });
        Boton_Menu_Desplegable.add(jLabel7, "card2");

        shop.add(Boton_Menu_Desplegable, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 90, 80));

        Menu_Ampliado.setBackground(new java.awt.Color(22, 22, 22));

        Boton_Perfil.setBackground(new java.awt.Color(22, 22, 22));
        Boton_Perfil.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Boton_Perfil.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Boton_PerfilMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Boton_PerfilMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Boton_PerfilMouseExited(evt);
            }
        });
        Boton_Perfil.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("PERFIL");
        Boton_Perfil.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 0, 220, 50));

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/iconsAjustes.png"))); // NOI18N
        Boton_Perfil.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 60, -1));

        jSeparator7.setBackground(new java.awt.Color(255, 255, 255));
        Boton_Perfil.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 350, 10));

        Boton_Juego.setBackground(new java.awt.Color(22, 22, 22));
        Boton_Juego.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Boton_Juego.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Boton_JuegoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Boton_JuegoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Boton_JuegoMouseExited(evt);
            }
        });
        Boton_Juego.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/iconsJuego.png"))); // NOI18N
        Boton_Juego.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 50, -1));

        jSeparator8.setBackground(new java.awt.Color(255, 255, 255));
        Boton_Juego.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 52, 350, 10));

        jLabel14.setBackground(new java.awt.Color(255, 255, 255));
        jLabel14.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("JUGAR");
        Boton_Juego.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 0, -1, 50));

        jLabel15.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Menú de Opciones");

        javax.swing.GroupLayout Menu_AmpliadoLayout = new javax.swing.GroupLayout(Menu_Ampliado);
        Menu_Ampliado.setLayout(Menu_AmpliadoLayout);
        Menu_AmpliadoLayout.setHorizontalGroup(
            Menu_AmpliadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Boton_Perfil, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Boton_Juego, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Menu_AmpliadoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );
        Menu_AmpliadoLayout.setVerticalGroup(
            Menu_AmpliadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Menu_AmpliadoLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel15)
                .addGap(307, 307, 307)
                .addComponent(Boton_Perfil, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(Boton_Juego, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        shop.add(Menu_Ampliado, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 0, -1, 1130));

        PanelContenido.setBackground(new java.awt.Color(15, 15, 15));
        PanelContenido.setOpaque(false);
        PanelContenido.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tituloTienda.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tituloTienda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/TituloTienda.png"))); // NOI18N
        PanelContenido.add(tituloTienda, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 30, -1, -1));

        iconMoneda.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconMoneda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Moneda.png"))); // NOI18N
        PanelContenido.add(iconMoneda, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 20, 150, 120));

        panelPuntos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        panelPuntos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/panelPuntos.png"))); // NOI18N
        PanelContenido.add(panelPuntos, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 0, 550, 160));

        Tienda.setOpaque(false);
        Tienda.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        precioBlanco.setFont(new java.awt.Font("Impact", 1, 36)); // NOI18N
        precioBlanco.setForeground(new java.awt.Color(204, 204, 0));
        precioBlanco.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        precioBlanco.setText("1000");
        Tienda.add(precioBlanco, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 210, 90, 50));

        precioAzul.setFont(new java.awt.Font("Impact", 1, 36)); // NOI18N
        precioAzul.setForeground(new java.awt.Color(204, 204, 0));
        precioAzul.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        precioAzul.setText("700");
        Tienda.add(precioAzul, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 210, 90, 50));

        precioVerde.setFont(new java.awt.Font("Impact", 1, 36)); // NOI18N
        precioVerde.setForeground(new java.awt.Color(204, 204, 0));
        precioVerde.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        precioVerde.setText("300");
        Tienda.add(precioVerde, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 210, 90, 50));

        precioRojo.setFont(new java.awt.Font("Impact", 1, 36)); // NOI18N
        precioRojo.setForeground(new java.awt.Color(204, 204, 0));
        precioRojo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        precioRojo.setText("100");
        Tienda.add(precioRojo, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 210, 110, 50));

        precioPajaro.setFont(new java.awt.Font("Impact", 1, 36)); // NOI18N
        precioPajaro.setForeground(new java.awt.Color(204, 204, 0));
        precioPajaro.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        precioPajaro.setText("250");
        Tienda.add(precioPajaro, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 470, 100, 50));

        precioThron.setFont(new java.awt.Font("Impact", 1, 36)); // NOI18N
        precioThron.setForeground(new java.awt.Color(204, 204, 0));
        precioThron.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        precioThron.setText("500");
        Tienda.add(precioThron, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 470, 110, 50));

        precioMonaChina.setFont(new java.awt.Font("Impact", 1, 36)); // NOI18N
        precioMonaChina.setForeground(new java.awt.Color(204, 204, 0));
        precioMonaChina.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        precioMonaChina.setText("1000");
        Tienda.add(precioMonaChina, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 470, 90, 50));

        precioRoca.setFont(new java.awt.Font("Impact", 1, 36)); // NOI18N
        precioRoca.setForeground(new java.awt.Color(204, 204, 0));
        precioRoca.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        precioRoca.setText("2000");
        Tienda.add(precioRoca, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 470, 90, 50));

        precioToom.setFont(new java.awt.Font("Impact", 1, 36)); // NOI18N
        precioToom.setForeground(new java.awt.Color(204, 204, 0));
        precioToom.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        precioToom.setText("1000");
        Tienda.add(precioToom, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 860, 90, 50));

        precioBmo.setFont(new java.awt.Font("Impact", 1, 36)); // NOI18N
        precioBmo.setForeground(new java.awt.Color(204, 204, 0));
        precioBmo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        precioBmo.setText("1000");
        Tienda.add(precioBmo, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 860, 90, 50));

        precioSpock.setFont(new java.awt.Font("Impact", 1, 36)); // NOI18N
        precioSpock.setForeground(new java.awt.Color(204, 204, 0));
        precioSpock.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        precioSpock.setText("1000");
        Tienda.add(precioSpock, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 860, 90, 50));

        precioJosep.setFont(new java.awt.Font("Impact", 1, 36)); // NOI18N
        precioJosep.setForeground(new java.awt.Color(204, 204, 0));
        precioJosep.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        precioJosep.setText("1000");
        Tienda.add(precioJosep, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 860, 90, 50));

        precioBill.setFont(new java.awt.Font("Impact", 1, 36)); // NOI18N
        precioBill.setForeground(new java.awt.Color(204, 204, 0));
        precioBill.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        precioBill.setText("1000");
        Tienda.add(precioBill, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 860, 90, 50));

        ToomNook.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/TomNookTienda.png"))); // NOI18N
        Tienda.add(ToomNook, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 757, 133, -1));

        Usuario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Usuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/iconsUsuarioPerfil_Tienda.png"))); // NOI18N
        Tienda.add(Usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 350, 133, 133));

        PajaroLoco.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        PajaroLoco.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/PajaroLoco_Tienda.png"))); // NOI18N
        Tienda.add(PajaroLoco, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 350, 140, 133));

        Thron.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Thron.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Thron_Tienda.png"))); // NOI18N
        Tienda.add(Thron, new org.netbeans.lib.awtextra.AbsoluteConstraints(463, 350, 140, 133));

        MonaChina.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        MonaChina.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/MonaChina_Tienda.png"))); // NOI18N
        Tienda.add(MonaChina, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 350, 133, 133));

        TheRock.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TheRock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Roca_Tienda.gif"))); // NOI18N
        Tienda.add(TheRock, new org.netbeans.lib.awtextra.AbsoluteConstraints(853, 350, 160, 133));

        Griff.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Griff.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/GriffTienda.png"))); // NOI18N
        Tienda.add(Griff, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 610, 140, 130));

        MrNintendo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        MrNintendo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/MrNintendoTienda.png"))); // NOI18N
        Tienda.add(MrNintendo, new org.netbeans.lib.awtextra.AbsoluteConstraints(703, 610, 140, 130));

        Hand.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Hand.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Master_HandTienda.png"))); // NOI18N
        Tienda.add(Hand, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 610, 140, 130));

        Gon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Gon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/GonPPTTienda.png"))); // NOI18N
        Tienda.add(Gon, new org.netbeans.lib.awtextra.AbsoluteConstraints(373, 610, 140, 130));

        RickYMorty.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RickYMorty.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/RickyMortyTienda.png"))); // NOI18N
        Tienda.add(RickYMorty, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 610, 133, 130));

        Sheldon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Sheldon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/SheldonTienda.png"))); // NOI18N
        Tienda.add(Sheldon, new org.netbeans.lib.awtextra.AbsoluteConstraints(43, 610, 140, 130));

        GLaDOS.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        GLaDOS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/GLaDOSTienda.png"))); // NOI18N
        Tienda.add(GLaDOS, new org.netbeans.lib.awtextra.AbsoluteConstraints(43, 760, 140, 130));

        Bill.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Bill.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/BillCipherTienda.png"))); // NOI18N
        Tienda.add(Bill, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 760, 133, 130));

        Josep.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Josep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/JosephJoestarTienda.png"))); // NOI18N
        Tienda.add(Josep, new org.netbeans.lib.awtextra.AbsoluteConstraints(373, 757, 140, -1));

        Spock.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Spock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/SpockPPTLSTienda.png"))); // NOI18N
        Tienda.add(Spock, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 760, 133, 120));

        Bmo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Bmo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/BMOTienda.png"))); // NOI18N
        Tienda.add(Bmo, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 757, 140, -1));

        Negro.setBackground(new java.awt.Color(18, 18, 18));
        Negro.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Negro.setOpaque(true);
        Tienda.add(Negro, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 80, 140, 130));

        Negro1.setBackground(new java.awt.Color(18, 18, 18));
        Negro1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Negro1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/blanco.png"))); // NOI18N
        Tienda.add(Negro1, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 70, 160, 140));

        Negro2.setBackground(new java.awt.Color(54, 18, 18));
        Negro2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Negro2.setOpaque(true);
        Tienda.add(Negro2, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 80, 150, 130));

        Negro3.setBackground(new java.awt.Color(18, 36, 19));
        Negro3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Negro3.setOpaque(true);
        Tienda.add(Negro3, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 80, 150, 130));

        Negro4.setBackground(new java.awt.Color(18, 16, 49));
        Negro4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Negro4.setOpaque(true);
        Tienda.add(Negro4, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 80, 150, 130));

        precioGLaDOS.setFont(new java.awt.Font("Impact", 1, 36)); // NOI18N
        precioGLaDOS.setForeground(new java.awt.Color(204, 204, 0));
        precioGLaDOS.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        precioGLaDOS.setText("1000");
        Tienda.add(precioGLaDOS, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 860, 90, 50));

        tienda.setBackground(new java.awt.Color(60, 25, 33));
        tienda.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tienda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Tienda.png"))); // NOI18N
        Tienda.add(tienda, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1070, 930));

        PanelContenido.add(Tienda, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 160, 1060, 910));

        bocadilloText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bocadilloText.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/GriffTexto.png"))); // NOI18N
        PanelContenido.add(bocadilloText, new org.netbeans.lib.awtextra.AbsoluteConstraints(1400, 210, 380, 230));

        puntuacion_jugador1.setFont(new java.awt.Font("Impact", 1, 36)); // NOI18N
        puntuacion_jugador1.setForeground(new java.awt.Color(204, 204, 0));
        puntuacion_jugador1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        puntuacion_jugador1.setText("{puntuacion_jugador}");
        PanelContenido.add(puntuacion_jugador1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 30, -1, 110));

        shop.add(PanelContenido, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 1760, 1090));

        Boton_Cierre_Perfil.setBackground(new java.awt.Color(15, 15, 15));
        Boton_Cierre_Perfil.setLayout(new java.awt.CardLayout());

        jLabel18.setBackground(new java.awt.Color(15, 15, 15));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/iconsX_Blanco.png"))); // NOI18N
        jLabel18.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel18MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel18MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel18MouseExited(evt);
            }
        });
        Boton_Cierre_Perfil.add(jLabel18, "card2");

        shop.add(Boton_Cierre_Perfil, new org.netbeans.lib.awtextra.AbsoluteConstraints(1830, 0, 90, 90));

        Griff_Dineros.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Griff_Dineros.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Griff.png"))); // NOI18N
        shop.add(Griff_Dineros, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 230, 970, 980));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1932, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(shop, javax.swing.GroupLayout.PREFERRED_SIZE, 1926, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 6, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1210, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(shop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked

        boolean abrir = !Menu_Ampliado.isVisible();
        Menu_Ampliado.setVisible(true);

        int objetivo = abrir ? 180 : 0;

        javax.swing.Timer timer = new javax.swing.Timer(5, null);
        timer.addActionListener(e -> {
            int x = PanelContenido.getX();

            if (abrir) {
                if (x < objetivo) {
                    PanelContenido.setLocation(x + 10, 0);
                } else {
                    PanelContenido.setLocation(objetivo, 0);
                    timer.stop();
                }
            } else {
                if (x > objetivo) {
                    PanelContenido.setLocation(x - 10, 0);
                } else {
                    PanelContenido.setLocation(objetivo, 0);
                    Menu_Ampliado.setVisible(false);
                    timer.stop();
                }
            }
        });

        timer.start();
    }//GEN-LAST:event_jLabel7MouseClicked

    private void jLabel7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseEntered
        if (!Menu_Ampliado.isVisible()) {
            Boton_Menu_Desplegable.setBackground(new Color(22,22,22));

        }
    }//GEN-LAST:event_jLabel7MouseEntered

    private void jLabel7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseExited
        if (!Menu_Ampliado.isVisible()) {
            Boton_Menu_Desplegable.setBackground(AppTheme.getFondoActivo());
        }
    }//GEN-LAST:event_jLabel7MouseExited

    private void Boton_PerfilMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Boton_PerfilMouseClicked
        new Perfil_Usuario().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_Boton_PerfilMouseClicked

    private void Boton_PerfilMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Boton_PerfilMouseEntered
        Boton_Perfil.setBackground(new Color(45,45,45));
    }//GEN-LAST:event_Boton_PerfilMouseEntered

    private void Boton_PerfilMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Boton_PerfilMouseExited
        Boton_Perfil.setBackground(new Color(22,22,22));
    }//GEN-LAST:event_Boton_PerfilMouseExited

    private void Boton_JuegoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Boton_JuegoMouseClicked
        new Main_Juego().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_Boton_JuegoMouseClicked

    private void Boton_JuegoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Boton_JuegoMouseEntered
        Boton_Juego.setBackground(new Color(45,45,45));
    }//GEN-LAST:event_Boton_JuegoMouseEntered

    private void Boton_JuegoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Boton_JuegoMouseExited
        Boton_Juego.setBackground(new Color(22,22,22));
    }//GEN-LAST:event_Boton_JuegoMouseExited

    private void jLabel18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel18MouseClicked
        System.exit(0);
    }//GEN-LAST:event_jLabel18MouseClicked

    private void jLabel18MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel18MouseEntered
        jLabel18.setBackground(Color.red);
        Boton_Cierre_Perfil.setBackground(Color.red);
    }//GEN-LAST:event_jLabel18MouseEntered

    private void jLabel18MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel18MouseExited
        jLabel18.setBackground(AppTheme.getFondoActivo());
        Boton_Cierre_Perfil.setBackground(AppTheme.getFondoActivo());
    }//GEN-LAST:event_jLabel18MouseExited

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Tienda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Tienda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Tienda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Tienda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Tienda().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Bill;
    private javax.swing.JLabel Bmo;
    private javax.swing.JPanel Boton_Cierre_Perfil;
    private javax.swing.JPanel Boton_Juego;
    private javax.swing.JPanel Boton_Menu_Desplegable;
    private javax.swing.JPanel Boton_Perfil;
    private javax.swing.JLabel GLaDOS;
    private javax.swing.JLabel Gon;
    private javax.swing.JLabel Griff;
    private javax.swing.JLabel Griff_Dineros;
    private javax.swing.JLabel Hand;
    private javax.swing.JLabel Josep;
    private javax.swing.JPanel Menu_Ampliado;
    private javax.swing.JLabel MonaChina;
    private javax.swing.JLabel MrNintendo;
    private javax.swing.JLabel Negro;
    private javax.swing.JLabel Negro1;
    private javax.swing.JLabel Negro2;
    private javax.swing.JLabel Negro3;
    private javax.swing.JLabel Negro4;
    private javax.swing.JLabel PajaroLoco;
    private javax.swing.JPanel PanelContenido;
    private javax.swing.JLabel RickYMorty;
    private javax.swing.JLabel Sheldon;
    private javax.swing.JLabel Spock;
    private javax.swing.JLabel TheRock;
    private javax.swing.JLabel Thron;
    private javax.swing.JPanel Tienda;
    private javax.swing.JLabel ToomNook;
    private javax.swing.JLabel Usuario;
    private javax.swing.JLabel bocadilloText;
    private javax.swing.JLabel iconMoneda;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JLabel panelPuntos;
    private javax.swing.JLabel precioAzul;
    private javax.swing.JLabel precioBill;
    private javax.swing.JLabel precioBlanco;
    private javax.swing.JLabel precioBmo;
    private javax.swing.JLabel precioGLaDOS;
    private javax.swing.JLabel precioJosep;
    private javax.swing.JLabel precioMonaChina;
    private javax.swing.JLabel precioPajaro;
    private javax.swing.JLabel precioRoca;
    private javax.swing.JLabel precioRojo;
    private javax.swing.JLabel precioSpock;
    private javax.swing.JLabel precioThron;
    private javax.swing.JLabel precioToom;
    private javax.swing.JLabel precioVerde;
    private javax.swing.JLabel puntuacion_jugador1;
    private javax.swing.JPanel shop;
    private javax.swing.JLabel tienda;
    private javax.swing.JLabel tituloTienda;
    // End of variables declaration//GEN-END:variables
}
