package pantallas;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.Icon;
import javax.swing.JLabel;

public class Difuminar extends JLabel {

    private float opacity = 0.2f;

    public void setOpacity(float opacity) {
        this.opacity = opacity;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setComposite(
            AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER,
                opacity
            )
        );

        super.paintComponent(g2);

        g2.dispose();
    }
}