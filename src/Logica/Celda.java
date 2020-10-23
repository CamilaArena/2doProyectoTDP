package logica;
/**
 * Class Celda
 * Modela una celda del sudoku.
 * 
 * @author Camila Arena LU:121943.
 */

public class Celda {
	
	//Atributos
	/**Representa los valores de la celda*/
	private Integer valor;//van de 0 a 8
	/**Representa las imagenes de la celda*/
	private EntidadGrafica entidadGrafica;
	/**Representa la fila donde se ubica la celda*/
	private int fila;
	/**Representa la columna donde se ubica la celda*/
	private int columna;
	/**Indica si la celda cumple o no con las propiedades del juego*/
	boolean cumplePropiedad;
	
	//Constructor
	/**
	 * Crea una celda nueva.
	*/
	public Celda() {
		this.valor = null;
		this.entidadGrafica = new EntidadGrafica();
		cumplePropiedad = true;
	}
	
	//Metodos
	/**
	 * Actualiza la imagen de la celda por una imagen de error.
	 * */
	public void actualizarImagen() {
		entidadGrafica.actualizarImagen(this.valor);
	}
	
	/**
	 * Actualiza la imagen de la celda por una imagen.
	 * */
	public void actualizarImagenError() {
		entidadGrafica.actualizarImagenError(this.valor);
	}
	
	/**
	 * Actualiza el valor de la celda.
	 * */
	public void actualizarValor() {
		if (valor!=null && valor < getCantElementos()-1){//Si no es nulo o si se pasa de 8
			valor++;
		}
		else {
			valor = 0;
		}		
	}
	
	//Getters
	/**
	 * Obtiene la cantidad de imagenes que hay por cada celda
	 * @return entero que indica la cantidad de imagenes. 
	 * */
	public int getCantElementos() {
		return this.entidadGrafica.getImagenes().length;
	}
		
	/**
	 * Obtiene el valor de la celda.
	 * @return valor de la celda.
	 **/
	public Integer getValor() {
		return this.valor;
	}
	
	/**
 	* Obtiene la fila donde se ubica la celda.
 	* @return fila de la celda.
 	**/
	public int getFila() {
		return fila;
	}
	
	/**
 	* Obtiene la columna donde se ubica la celda.
 	* @return columna de la celda.
 	**/
	public int getColumna() {
		return columna;
	}
	
	/**
 	* Obtiene la entidad grafica de la celda.
 	* @return entidad grafica de la celda.
 	**/
	public EntidadGrafica getEntidadGrafica() {
		return this.entidadGrafica;
	}
	
	/**
 	* Obtiene si la celda cumple la propiedad.
 	* @return true si cumple la propiedad.
 	**/
	public boolean getCumplePropiedad() {
		return cumplePropiedad;
	}
	
	
	//Setters
	/**
 	* Modifica cumple por el valor pasado por parametro.
 	* @param cumple representa si la celda cumple la propiedad.
 	**/
	public void setCumplePropiedad(boolean cumple) {
		cumplePropiedad = cumple;
	}
	
	/**
 	* Modifica el valor de la celda por el valor pasado por parametro.
 	* @param cumple representa el valor de la imagen.
 	**/
	public void setValor(Integer valor) {//El max va a ser 8
		if (valor!=null && valor < this.getCantElementos()) {
			this.valor = valor;
			this.entidadGrafica.actualizarImagen(this.valor);
		}
		else {
			this.valor = null;
		}
	}
	
	/**
 	* Modifica la entidad grafica de la celda por la pasada por parametro.
 	* @param g representa a la entidad grafica.
 	**/
	public void setGrafica(EntidadGrafica g) {
		this.entidadGrafica = g;
	}		
	
	/**
 	* Modifica la fila de la celda por la pasada por parametro.
 	* @param i representa la fila.
 	**/
	public void setFila(int i) {	
		fila = i;
	}
	
	/**
 	* Modifica la columna de la celda por la pasada por parametro.
 	* @param j representa la columna.
 	**/
	public void setColumna(int j) {
		columna = j;
	}
}