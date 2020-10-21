package gui;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
	
	
@SuppressWarnings("serial")
public class PanelConFondo extends JPanel {

	private Image background;
	
	public PanelConFondo() {
		
		background = new ImageIcon(getClass().getResource("/Imagenes/imgFondo/ImagenFondo.jpg")).getImage();
	}
	
	public Image getImagenFondo() {
		return background;
	}

	
	public void paintComponent(Graphics g) {

		int width = this.getSize().width;
		int height = this.getSize().height;
	 
		if (this.background != null) {
			g.drawImage(this.background, 0, 0, width, height, null);
		}
	 
		super.paintComponent(g);
	}
	 
	// Metodo donde le pasaremos la dirección de la imagen a cargar.
	public void setBackground(Image background2) {
		this.setOpaque(false);
		this.background = new ImageIcon(background2).getImage();
		repaint();	
	 
	}
	
}