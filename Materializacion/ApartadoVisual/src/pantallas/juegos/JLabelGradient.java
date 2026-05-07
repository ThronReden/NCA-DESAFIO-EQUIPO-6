package pantallas.juegos;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JLabel;

public class JLabelGradient extends JLabel {

    private Color color1 = Color.GREEN;
    private Color color2 = new Color(56, 219,255);


    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);

        g2d.setPaint(gp);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }

    public void setGradienteColores(Color c1, Color c2) {
        this.color1 = c1;
        this.color2 = c2;
        repaint();

    }
}
