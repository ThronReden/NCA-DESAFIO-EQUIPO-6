package pantallas;

import CRUD_bbdd.bbdd_PerfilUsuario;
import CRUD_bbdd.bbdd_PerfilUsuario.DatosPerfil;
import CRUD_bbdd.bbdd_PerfilUsuario.EstadisticasPerfil;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


/**
 *
 * @author LENOVO
 */
public class Perfil_Usuario extends javax.swing.JFrame {

    private final char EchoChar;
    private DatosPerfil datosOriginales;
    ImageIcon NoVer = new ImageIcon("src\\imagenes\\iconsNoVer.png");
    ImageIcon Ver = new ImageIcon("src\\imagenes\\iconsVer.png");

    /**
     * Creates new form Perfil_Usuario
     */
    public Perfil_Usuario() {
        initComponents();
        EchoChar = contraseña.getEchoChar();
        Menu_Ampliado.setVisible(false);
        jPanel2.setBackground(AppTheme.getFondoActivo());
        PanelContenido.setBackground(AppTheme.getFondoActivo());
        jLabel16.setIcon(AppTheme.getIconoPerfilActivo());
        jLabel23.setIcon(AppTheme.getPersonajePerfilActivo());
        ((Difuminar) jLabel23).setOpacity(0.75f);
         ((Difuminar) fondo_hack).setOpacity(0.18f);
        prepararEdicionPerfil();
        cargarDatosPerfil();
        cargarEstadisticasPerfil();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void prepararEdicionPerfil() {
        nombre_usuario.setEditable(false);
        correo_electronico.setEditable(false);
        contraseña.setEditable(false);

        editarNombre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                habilitarCampo(nombre_usuario);
            }
        });

        editarCorreo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                habilitarCampo(correo_electronico);
            }
        });

        botonContraseña.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                habilitarCampo(contraseña);
            }
        });

        botonGuardarCambios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                guardarCambiosPerfil();
            }
        });
    }

    private void habilitarCampo(javax.swing.text.JTextComponent campo) {
        campo.setEditable(true);
        campo.requestFocus();
        campo.selectAll();
    }

    private void cargarDatosPerfil() {
        datosOriginales = bbdd_PerfilUsuario.obtenerDatosPerfil(AppTheme.getNombreUsuarioActivo());

        if (datosOriginales == null) {
            nombre_usuario.setText(AppTheme.getNombreUsuarioActivo());
            correo_electronico.setText("");
            contraseña.setText("");
            return;
        }

        nombre_usuario.setText(datosOriginales.getNombreUsuario());
        correo_electronico.setText(datosOriginales.getCorreo());
        contraseña.setText(datosOriginales.getContrasena());
    }

    private void cargarEstadisticasPerfil() {
        EstadisticasPerfil estadisticas = bbdd_PerfilUsuario.obtenerEstadisticasPerfil(AppTheme.getNombreUsuarioActivo());

        if (estadisticas == null) {
            contador_puntos.setText("0");
            contador_ganadaPPT.setText("0");
            contador_piedra.setText("0");
            contador_papel.setText("0");
            contador_tijera.setText("0");
            contador_3R.setText("0");
            return;
        }

        contador_puntos.setText(String.valueOf(estadisticas.getPuntos()));
        contador_ganadaPPT.setText(String.valueOf(estadisticas.getPartidasGanadasPpt()));
        contador_piedra.setText(String.valueOf(estadisticas.getPiedra()));
        contador_papel.setText(String.valueOf(estadisticas.getPapel()));
        contador_tijera.setText(String.valueOf(estadisticas.getTijera()));
        contador_3R.setText(String.valueOf(estadisticas.getPartidasGanadas3R()));
    }

    private void guardarCambiosPerfil() {
        if (datosOriginales == null) {
            return;
        }

        String nuevoNombre = nombre_usuario.getText().trim();
        String nuevoCorreo = correo_electronico.getText().trim();
        String nuevaContrasena = new String(contraseña.getPassword()).trim();

        if (!hayCambios(nuevoNombre, nuevoCorreo, nuevaContrasena)) {
            bloquearCamposPerfil();
            return;
        }

        if (!datosPerfilValidos(nuevoNombre, nuevoCorreo, nuevaContrasena)) {
            return;
        }

        if (bbdd_PerfilUsuario.existeOtroUsuarioOCorreo(datosOriginales.getIdUsuario(), nuevoNombre, nuevoCorreo)) {
            JOptionPane.showMessageDialog(this, "Ese nombre o correo ya esta en uso.", "Perfil", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean actualizado = bbdd_PerfilUsuario.actualizarDatosPerfil(
                datosOriginales.getIdUsuario(),
                nuevoNombre,
                nuevoCorreo,
                nuevaContrasena
        );

        if (actualizado) {
            AppTheme.setNombreUsuarioActivo(nuevoNombre);
            datosOriginales = new DatosPerfil(datosOriginales.getIdUsuario(), nuevoNombre, nuevoCorreo, nuevaContrasena);
            bloquearCamposPerfil();
            JOptionPane.showMessageDialog(this, "Datos actualizados correctamente.", "Perfil", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No se pudieron actualizar los datos.", "Perfil", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean hayCambios(String nuevoNombre, String nuevoCorreo, String nuevaContrasena) {
        return !nuevoNombre.equals(datosOriginales.getNombreUsuario())
                || !nuevoCorreo.equals(datosOriginales.getCorreo())
                || !nuevaContrasena.equals(datosOriginales.getContrasena());
    }

    private boolean datosPerfilValidos(String nombre, String correo, String contrasena) {
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Introduce un nombre de usuario.", "Perfil", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (correo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Introduce un correo electronico.", "Perfil", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (!correo.contains("@") || !correo.contains(".")) {
            JOptionPane.showMessageDialog(this, "Introduce un correo electronico valido.", "Perfil", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Introduce una contraseña.", "Perfil", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (nombre.length() > 20) {
            JOptionPane.showMessageDialog(this, "El nombre no puede superar 20 caracteres.", "Perfil", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (correo.length() > 50) {
            JOptionPane.showMessageDialog(this, "El correo no puede superar 50 caracteres.", "Perfil", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (contrasena.length() > 45) {
            JOptionPane.showMessageDialog(this, "La contraseña no puede superar 45 caracteres.", "Perfil", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true;
    }

    private void bloquearCamposPerfil() {
        nombre_usuario.setEditable(false);
        correo_electronico.setEditable(false);
        contraseña.setEditable(false);
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
        jPanel2 = new javax.swing.JPanel();
        Boton_Menu_Desplegable = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        Menu_Ampliado = new javax.swing.JPanel();
        Boton_Tienda = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        Boton_Juego = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        PanelContenido = new javax.swing.JPanel();
        Bienvenido1 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        datosPersonales = new javax.swing.JLabel();
        panelDatos = new javax.swing.JPanel();
        editarNombre = new javax.swing.JPanel();
        editarCorreo = new javax.swing.JPanel();
        botonContraseña = new javax.swing.JPanel();
        Visibilidad = new javax.swing.JLabel();
        nombre_usuario = new javax.swing.JTextField();
        correo_electronico = new javax.swing.JTextField();
        contraseña = new javax.swing.JPasswordField();
        botonGuardarCambios = new javax.swing.JPanel();
        datoPersonales = new javax.swing.JLabel();
        panelEstadisticas = new javax.swing.JPanel();
        contador_puntos = new javax.swing.JLabel();
        contador_ganadaPPT = new javax.swing.JLabel();
        contador_Spock = new javax.swing.JLabel();
        contador_ganadaPPTLS = new javax.swing.JLabel();
        contador_3R = new javax.swing.JLabel();
        contador_Lagartos = new javax.swing.JLabel();
        contador_piedra = new javax.swing.JLabel();
        contador_papel = new javax.swing.JLabel();
        contador_tijera = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        Boton_Cierre_Perfil = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel23 = new Difuminar();
        fondo_hack = new Difuminar();

        jLabel5.setFont(new java.awt.Font("Dubai", 1, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Partidas ganadas al 3 en raya: {partidas_ganadas3Raya}");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(6);
        setUndecorated(true);
        setResizable(false);

        jPanel2.setBackground(new java.awt.Color(18, 18, 18));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Boton_Menu_Desplegable.setBackground(new java.awt.Color(22, 22, 22));
        Boton_Menu_Desplegable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Boton_Menu_Desplegable.setLayout(new java.awt.CardLayout());

        jLabel7.setBackground(new java.awt.Color(22, 22, 22));
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

        jPanel2.add(Boton_Menu_Desplegable, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 90, 80));

        Menu_Ampliado.setBackground(new java.awt.Color(22, 22, 22));
        Menu_Ampliado.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Boton_Tienda.setBackground(new java.awt.Color(22, 22, 22));
        Boton_Tienda.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Boton_Tienda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Boton_TiendaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Boton_TiendaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Boton_TiendaMouseExited(evt);
            }
        });
        Boton_Tienda.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("TIENDA");
        Boton_Tienda.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 0, 220, 50));

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/iconsTienda.png"))); // NOI18N
        Boton_Tienda.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 60, -1));

        jSeparator7.setBackground(new java.awt.Color(255, 255, 255));
        Boton_Tienda.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 350, 10));

        Menu_Ampliado.add(Boton_Tienda, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 363, -1, 59));

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

        Menu_Ampliado.add(Boton_Juego, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 469, -1, -1));

        jLabel15.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Menú de Opciones");
        Menu_Ampliado.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(114, 32, 210, -1));

        jPanel2.add(Menu_Ampliado, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 0, -1, 1090));

        PanelContenido.setBackground(new java.awt.Color(18, 18, 18));
        PanelContenido.setOpaque(false);
        PanelContenido.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Bienvenido1.setFont(new java.awt.Font("Dialog", 1, 70)); // NOI18N
        Bienvenido1.setForeground(new java.awt.Color(0, 204, 102));
        Bienvenido1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Bienvenido1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/TuPerfil.png"))); // NOI18N
        PanelContenido.add(Bienvenido1, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 20, 340, 100));

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/iconsUsuarioPerfil.png"))); // NOI18N
        PanelContenido.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 120, -1, -1));
        PanelContenido.add(datosPersonales, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 260, -1, -1));

        panelDatos.setOpaque(false);
        panelDatos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        editarNombre.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        editarNombre.setOpaque(false);

        javax.swing.GroupLayout editarNombreLayout = new javax.swing.GroupLayout(editarNombre);
        editarNombre.setLayout(editarNombreLayout);
        editarNombreLayout.setHorizontalGroup(
            editarNombreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );
        editarNombreLayout.setVerticalGroup(
            editarNombreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        panelDatos.add(editarNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 120, 150, 40));

        editarCorreo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        editarCorreo.setOpaque(false);

        javax.swing.GroupLayout editarCorreoLayout = new javax.swing.GroupLayout(editarCorreo);
        editarCorreo.setLayout(editarCorreoLayout);
        editarCorreoLayout.setHorizontalGroup(
            editarCorreoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );
        editarCorreoLayout.setVerticalGroup(
            editarCorreoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        panelDatos.add(editarCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 190, 150, 40));

        botonContraseña.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botonContraseña.setOpaque(false);

        javax.swing.GroupLayout botonContraseñaLayout = new javax.swing.GroupLayout(botonContraseña);
        botonContraseña.setLayout(botonContraseñaLayout);
        botonContraseñaLayout.setHorizontalGroup(
            botonContraseñaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );
        botonContraseñaLayout.setVerticalGroup(
            botonContraseñaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        panelDatos.add(botonContraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 260, 150, 40));

        Visibilidad.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Visibilidad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/iconsVer.png"))); // NOI18N
        Visibilidad.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Visibilidad.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                VisibilidadMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                VisibilidadMouseEntered(evt);
            }
        });
        panelDatos.add(Visibilidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 250, 50, 50));

        nombre_usuario.setEditable(false);
        nombre_usuario.setFont(new java.awt.Font("Leelawadee UI", 0, 24)); // NOI18N
        nombre_usuario.setForeground(new java.awt.Color(255, 255, 255));
        nombre_usuario.setText("{nombre_usuario}");
        nombre_usuario.setBorder(null);
        nombre_usuario.setOpaque(false);
        panelDatos.add(nombre_usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 120, 520, 40));

        correo_electronico.setEditable(false);
        correo_electronico.setFont(new java.awt.Font("Leelawadee UI", 0, 24)); // NOI18N
        correo_electronico.setForeground(new java.awt.Color(255, 255, 255));
        correo_electronico.setText("{correo_electronico}");
        correo_electronico.setBorder(null);
        correo_electronico.setOpaque(false);
        panelDatos.add(correo_electronico, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 190, 520, 40));

        contraseña.setEditable(false);
        contraseña.setFont(new java.awt.Font("Gadugi", 0, 24)); // NOI18N
        contraseña.setForeground(new java.awt.Color(255, 255, 255));
        contraseña.setText("{contraseña}");
        contraseña.setBorder(null);
        contraseña.setOpaque(false);
        panelDatos.add(contraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 260, 470, 40));

        botonGuardarCambios.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botonGuardarCambios.setOpaque(false);

        javax.swing.GroupLayout botonGuardarCambiosLayout = new javax.swing.GroupLayout(botonGuardarCambios);
        botonGuardarCambios.setLayout(botonGuardarCambiosLayout);
        botonGuardarCambiosLayout.setHorizontalGroup(
            botonGuardarCambiosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 450, Short.MAX_VALUE)
        );
        botonGuardarCambiosLayout.setVerticalGroup(
            botonGuardarCambiosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        panelDatos.add(botonGuardarCambios, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 340, 450, 50));

        datoPersonales.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        datoPersonales.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Datos.png"))); // NOI18N
        panelDatos.add(datoPersonales, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 1150, 390));

        PanelContenido.add(panelDatos, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 170, 1320, 420));

        panelEstadisticas.setOpaque(false);
        panelEstadisticas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        contador_puntos.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        contador_puntos.setForeground(new java.awt.Color(51, 255, 0));
        contador_puntos.setText("{contador_puntos}");
        panelEstadisticas.add(contador_puntos, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 120, 140, 30));

        contador_ganadaPPT.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        contador_ganadaPPT.setForeground(new java.awt.Color(51, 255, 0));
        contador_ganadaPPT.setText("{contador_puntos}");
        panelEstadisticas.add(contador_ganadaPPT, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 190, 140, 40));

        contador_Spock.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        contador_Spock.setForeground(new java.awt.Color(51, 255, 0));
        contador_Spock.setText("{contador_puntos}");
        panelEstadisticas.add(contador_Spock, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 260, 140, 70));

        contador_ganadaPPTLS.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        contador_ganadaPPTLS.setForeground(new java.awt.Color(51, 255, 0));
        contador_ganadaPPTLS.setText("{contador_puntos}");
        panelEstadisticas.add(contador_ganadaPPTLS, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 270, 140, 40));

        contador_3R.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        contador_3R.setForeground(new java.awt.Color(51, 255, 0));
        contador_3R.setText("{contador_puntos}");
        panelEstadisticas.add(contador_3R, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 350, 140, 40));

        contador_Lagartos.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        contador_Lagartos.setForeground(new java.awt.Color(51, 255, 0));
        contador_Lagartos.setText("{contador_puntos}");
        panelEstadisticas.add(contador_Lagartos, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 340, 140, 50));

        contador_piedra.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        contador_piedra.setForeground(new java.awt.Color(51, 255, 0));
        contador_piedra.setText("{contador_puntos}");
        panelEstadisticas.add(contador_piedra, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 70, 140, 50));

        contador_papel.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        contador_papel.setForeground(new java.awt.Color(51, 255, 0));
        contador_papel.setText("{contador_puntos}");
        panelEstadisticas.add(contador_papel, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 130, 140, 60));

        contador_tijera.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        contador_tijera.setForeground(new java.awt.Color(51, 255, 0));
        contador_tijera.setText("{contador_puntos}");
        panelEstadisticas.add(contador_tijera, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 200, 140, 60));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Estadisticas.png"))); // NOI18N
        panelEstadisticas.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 1100, 450));

        PanelContenido.add(panelEstadisticas, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 620, 1130, 470));

        jPanel2.add(PanelContenido, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 1560, 1090));

        Boton_Cierre_Perfil.setBackground(new java.awt.Color(18, 18, 18));
        Boton_Cierre_Perfil.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Boton_Cierre_Perfil.setLayout(new java.awt.CardLayout());

        jLabel18.setBackground(new java.awt.Color(255, 255, 255));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/iconsX_Blanco.png"))); // NOI18N
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

        jPanel2.add(Boton_Cierre_Perfil, new org.netbeans.lib.awtextra.AbsoluteConstraints(1830, 0, 90, 90));

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/MrNintendo.png"))); // NOI18N
        jPanel2.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(1220, 90, 1000, 1060));

        fondo_hack.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fondo_hack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/FondoPerfil.png"))); // NOI18N
        jPanel2.add(fondo_hack, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2001, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1926, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 75, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1470, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
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
            Boton_Menu_Desplegable.setBackground(new Color(22, 22, 22));

        }
    }//GEN-LAST:event_jLabel7MouseEntered

    private void jLabel7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseExited
        if (!Menu_Ampliado.isVisible()) {
            Boton_Menu_Desplegable.setBackground(new Color(17, 17, 17));
        }
    }//GEN-LAST:event_jLabel7MouseExited

    private void Boton_TiendaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Boton_TiendaMouseClicked
        new Tienda().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_Boton_TiendaMouseClicked

    private void Boton_TiendaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Boton_TiendaMouseEntered
        Boton_Tienda.setBackground(new Color(45, 45, 45));
    }//GEN-LAST:event_Boton_TiendaMouseEntered

    private void Boton_TiendaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Boton_TiendaMouseExited
        Boton_Tienda.setBackground(new Color(22, 22, 22));
    }//GEN-LAST:event_Boton_TiendaMouseExited

    private void Boton_JuegoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Boton_JuegoMouseClicked
        new Main_Juego().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_Boton_JuegoMouseClicked

    private void Boton_JuegoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Boton_JuegoMouseEntered
        Boton_Juego.setBackground(new Color(45, 45, 45));
    }//GEN-LAST:event_Boton_JuegoMouseEntered

    private void Boton_JuegoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Boton_JuegoMouseExited
        Boton_Juego.setBackground(new Color(22, 22, 22));
    }//GEN-LAST:event_Boton_JuegoMouseExited

    private void jLabel18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel18MouseClicked
        System.exit(0);
    }//GEN-LAST:event_jLabel18MouseClicked

    private void jLabel18MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel18MouseEntered
        jLabel18.setBackground(Color.red);
        Boton_Cierre_Perfil.setBackground(Color.red);
    }//GEN-LAST:event_jLabel18MouseEntered

    private void jLabel18MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel18MouseExited
        jLabel18.setBackground(new Color(17, 17, 17));
        Boton_Cierre_Perfil.setBackground(new Color(17, 17, 17));
    }//GEN-LAST:event_jLabel18MouseExited

    private void VisibilidadMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_VisibilidadMouseEntered

    }//GEN-LAST:event_VisibilidadMouseEntered

    private void VisibilidadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_VisibilidadMouseClicked
        if (contraseña.getEchoChar() == EchoChar) {
            Visibilidad.setIcon(NoVer);
            contraseña.setEchoChar((char) 0);
        } else {
            Visibilidad.setIcon(Ver);
            contraseña.setEchoChar(EchoChar);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_VisibilidadMouseClicked

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
            java.util.logging.Logger.getLogger(Perfil_Usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Perfil_Usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Perfil_Usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Perfil_Usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Perfil_Usuario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Bienvenido1;
    private javax.swing.JPanel Boton_Cierre_Perfil;
    private javax.swing.JPanel Boton_Juego;
    private javax.swing.JPanel Boton_Menu_Desplegable;
    private javax.swing.JPanel Boton_Tienda;
    private javax.swing.JPanel Menu_Ampliado;
    private javax.swing.JPanel PanelContenido;
    private javax.swing.JLabel Visibilidad;
    private javax.swing.JPanel botonContraseña;
    private javax.swing.JPanel botonGuardarCambios;
    private javax.swing.JLabel contador_3R;
    private javax.swing.JLabel contador_Lagartos;
    private javax.swing.JLabel contador_Spock;
    private javax.swing.JLabel contador_ganadaPPT;
    private javax.swing.JLabel contador_ganadaPPTLS;
    private javax.swing.JLabel contador_papel;
    private javax.swing.JLabel contador_piedra;
    private javax.swing.JLabel contador_puntos;
    private javax.swing.JLabel contador_tijera;
    private javax.swing.JPasswordField contraseña;
    private javax.swing.JTextField correo_electronico;
    private javax.swing.JLabel datoPersonales;
    private javax.swing.JLabel datosPersonales;
    private javax.swing.JPanel editarCorreo;
    private javax.swing.JPanel editarNombre;
    private javax.swing.JLabel fondo_hack;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JTextField nombre_usuario;
    private javax.swing.JPanel panelDatos;
    private javax.swing.JPanel panelEstadisticas;
    // End of variables declaration//GEN-END:variables
}
