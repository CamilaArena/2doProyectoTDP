package Logica;

import javax.swing.ImageIcon;

/**
 * Class EntidadGrafica
 * Modela la parte grafica de las celdas del sudoku.
 * 
 * @author Camila Arena LU:121943.
 */

public class EntidadGrafica {
	
	//Atributos
	/**Representa la imagen de la celda*/
	private ImageIcon grafico;
	/**Representa a las imagenes del juego*/
	private String[] imagenes;
	/**Representa a las imagenes del juego que no cumplen con las propiedades*/
	private String[] imagenesError;
	/**Representa al grafico inicial que tienen las celdas al iniciar el juego*/
	private String[] graficoInicial;
	
	//Constuctor
		/**
		 * Crea una entidad grafica.
		*/
	public EntidadGrafica() {
		this.grafico=new ImageIcon();
		this.graficoInicial=new String[] {"/Imagenes/img/QuestionMark.png"};
		ImageIcon imageIcon = new ImageIcon(this.getClass().getResource(this.graficoInicial[0]));
		this.grafico.setImage(imageIcon.getImage());
		this.imagenes=new String[] {"/Imagenes/img/1.png","/Imagenes/img/2.png","/Imagenes/img/3.png","/Imagenes/img/4.png","/Imagenes/img/5.png","/Imagenes/img/6.png","/Imagenes/img/7.png","/Imagenes/img/8.png","/Imagenes/img/9.png"};
		this.imagenesError=new String[] {"/Imagenes/imgError/1Error.png","/Imagenes/imgError/2Error.png","/Imagenes/imgError/3Error.png","/Imagenes/imgError/4Error.png","/Imagenes/imgError/5Error.png","/Imagenes/imgError/6Error.png","/Imagenes/imgError/7Error.png","/Imagenes/imgError/8Error.png","/Imagenes/imgError/9Error.png" };
	}
	
	
	//Metodos
	/**
 	* Modifica la imagen de la entidad grafica.
 	* @param indice indica la posicion donde se ubica la imagen en el arreglo de imagenes.
 	**/
	public void actualizarImagen(int indice) {
		if (indice < this.imagenes.length) {
			ImageIcon imageIcon = new ImageIcon(this.getClass().getResource(this.imagenes[indice]));
			this.grafico.setImage(imageIcon.getImage());
		}
	}
	
	/**
 	* Modifica la imagen de la entidad grafica.
 	* @param indice indica la posicion donde se ubica la imagen en el arreglo de imagenes de error.
 	**/
	public void actualizarImagenError(int indice) {
		if (indice < this.imagenes.length) {
			ImageIcon imageIcon = new ImageIcon(this.getClass().getResource(this.imagenesError[indice]));
			this.grafico.setImage(imageIcon.getImage());
		}
	}	
	
	/**
 	* Obtiene el grafico de la entidad grafica.
 	* @return imagen de la celda.
 	**/
	public ImageIcon getGrafico() {
		return this.grafico;
	}

	/**
 	* Modifica la imagen de la entidad grafica por la pasada por parametro.
 	* @param grafico a ubicar en la entidad grafica.
 	**/
	public void setGrafico(ImageIcon grafico) {
		this.grafico = grafico;
	}
	
	/**
 	* Obtiene las imagenes de la entidad grafica.
 	* @return imagenes de la entidad grafica.
 	**/
	public String[] getImagenes() {
		return this.imagenes;
	}
	
	/**
 	* Obtiene las imagenes de error de la entidad grafica.
 	* @return imagenes de error de la entidad grafica.
 	**/
	public String[] getImagenesError() {
		return this.imagenes;
	}
	
	/**
 	* Modifica las imagenes de la entidad grafica por las pasadas por parametro.
 	* @return imagenes de la entidad grafica.
 	**/
	public void setImagenes(String[] imagenes) {
		this.imagenes = imagenes;
	}
	
	/**
 	* Modifica las imagenes  de error de la entidad grafica por las pasadas por parametro.
 	* @return imagenes de error de la entidad grafica.
 	**/
	public void setImagenesError(String[] imagenes) {
		this.imagenes = imagenes;
	}
}