package pantallas;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;
import javax.swing.ImageIcon;

public final class AppTheme {

    private static Color fondoActivo = new Color(15, 15, 15);
    private static String iconoPerfilActivo = "/imagenes/iconsUsuarioPerfil.png";
    private static String personajeMainActivo = "/imagenes/Sheldon.png";
    private static String personajeTiendaActivo = "/imagenes/Griff.png";
    private static String personajePerfilActivo = "/imagenes/MrNintendo.png";
    private static String personajePptActivo = "/imagenes/GonPPT.png";
    private static String personajePptlsActivo = "/imagenes/Master_Hand.png";
    private static String personajeRayaActivo = "/imagenes/RickyMorty .png";
    private static final Set<String> articulosComprados = new HashSet<>();

    private AppTheme() {
    }

    public static Color getFondoActivo() {
        return fondoActivo;
    }

    public static void setFondoActivo(Color color) {
        fondoActivo = color;
    }

    public static ImageIcon getIconoPerfilActivo() {
        return new ImageIcon(AppTheme.class.getResource(iconoPerfilActivo));
    }

    public static String getIconoPerfilActivoRuta() {
        return iconoPerfilActivo;
    }

    public static void setIconoPerfilActivo(String ruta) {
        iconoPerfilActivo = ruta;
    }

    public static ImageIcon getPersonajeMainActivo() {
        return new ImageIcon(AppTheme.class.getResource(personajeMainActivo));
    }

    public static String getPersonajeMainActivoRuta() {
        return personajeMainActivo;
    }

    public static void setPersonajeMainActivo(String ruta) {
        personajeMainActivo = ruta;
    }

    public static ImageIcon getPersonajeTiendaActivo() {
        return new ImageIcon(AppTheme.class.getResource(personajeTiendaActivo));
    }

    public static String getPersonajeTiendaActivoRuta() {
        return personajeTiendaActivo;
    }

    public static void setPersonajeTiendaActivo(String ruta) {
        personajeTiendaActivo = ruta;
    }

    public static ImageIcon getPersonajePerfilActivo() {
        return new ImageIcon(AppTheme.class.getResource(personajePerfilActivo));
    }

    public static String getPersonajePerfilActivoRuta() {
        return personajePerfilActivo;
    }

    public static void setPersonajePerfilActivo(String ruta) {
        personajePerfilActivo = ruta;
    }

    public static ImageIcon getPersonajePptActivo() {
        return new ImageIcon(AppTheme.class.getResource(personajePptActivo));
    }

    public static String getPersonajePptActivoRuta() {
        return personajePptActivo;
    }

    public static void setPersonajePptActivo(String ruta) {
        personajePptActivo = ruta;
    }

    public static ImageIcon getPersonajePptlsActivo() {
        return new ImageIcon(AppTheme.class.getResource(personajePptlsActivo));
    }

    public static String getPersonajePptlsActivoRuta() {
        return personajePptlsActivo;
    }

    public static void setPersonajePptlsActivo(String ruta) {
        personajePptlsActivo = ruta;
    }

    public static ImageIcon getPersonajeRayaActivo() {
        return new ImageIcon(AppTheme.class.getResource(personajeRayaActivo));
    }

    public static String getPersonajeRayaActivoRuta() {
        return personajeRayaActivo;
    }

    public static void setPersonajeRayaActivo(String ruta) {
        personajeRayaActivo = ruta;
    }

    public static void registrarCompra(String articulo) {
        articulosComprados.add(articulo);
    }

    public static boolean estaComprado(String articulo) {
        return articulosComprados.contains(articulo);
    }
}
