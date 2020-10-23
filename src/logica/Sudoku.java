package logica;

import java.io.InputStream;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class Sudoku
 * Modela un Sudoku.
 * 
 * @author Camila Arena LU:121943.
 */

public class Sudoku {
	//Atributos
	/**Representa el tablero del juego*/
	private Celda [][] tablero;
	/**Representa la matriz que contiene la solucion del juego*/
	private int matrizSolucion[][];
	/**Representa la cantidad de filas (y columnas) del juego*/
	private int cantFilas;
	/**logger de la clase*/
	private static Logger logger;
	/**Indica si el juego cumple con las propiedades*/
	private boolean cumplePropiedad;
	
	//Constructor
	/**
	 * Crea un sudoku nuevo.
	 * @param f, representa la cantidad de filas y columnas del juego
	 */
	public Sudoku(int f) {
		cantFilas = f;
		tablero = new Celda[cantFilas][cantFilas];
		matrizSolucion=new int[cantFilas][cantFilas];

		if(logger == null) {
			
			logger= Logger.getLogger(Sudoku.class.getName());
			
			Handler hnd = new ConsoleHandler();
			hnd.setLevel(Level.SEVERE);
			
			logger.addHandler(hnd);
			logger.setLevel(Level.SEVERE);
			
			Logger rootLogger=logger.getParent();
			
			for(Handler h : rootLogger.getHandlers())
				h.setLevel(Level.OFF);
		}

			if(checkearSolucion()) {
				cumplePropiedad = true;
				this.inicializarConArchivo();
			}
			else {
				cumplePropiedad = false;
			}

	}
	
	//Metodos
	/**
	 * Inicializa el juego con los datos de la solucion que esta contenida en la matriz.
	 * */
	private void inicializarConArchivo() {
		Random rnd=new Random();
		Celda celda = new Celda();
		int c;
		
		for (int i =0; i<cantFilas; i++) {
			for (int j =0; j<cantFilas; j++) {
				c = matrizSolucion[i][j]-1;
				int aux=rnd.nextInt(3);
				celda = new Celda();
				tablero[i][j] = celda;
				celda.setFila(i);
				celda.setColumna(j);
				celda.setCumplePropiedad(true);
				
				if(aux == 0 || aux == 1) {
					celda.setValor(c);
				}
			}
		}
	}
	
	
	/**
	 * Verifica que la solucion del archivo sea valida.
	 * @return true si la solucion del archivo es valida.
	 * */
	public boolean checkearSolucion() {
		boolean toRet=true;
		Integer c;
		Scanner scn=null;
		int indiceF = 0;
		int indiceC = 0;
		
		InputStream in = Sudoku.class.getClassLoader().getResourceAsStream("Archivos/solucion.txt");	
		try {
			scn =new Scanner(in);
		}catch(NullPointerException e) {
			toRet = false;
			logger.severe("Error: archivo inexistente");
		}
		
		for(int i=0; i<cantFilas && toRet ; i++) {
			for(int j=0; j<cantFilas && toRet; j++) {
				c=scn.hasNext() ? scn.nextInt() : null;
				if(c==null) {
					toRet = false;
					logger.severe("Error: archivo invalido, faltan numeros");
				}
				else{
					if(c>9) {
							toRet = false;
							logger.severe("Error: El archivo no cumple con el formato, existen numeros mayores a 9");
						}
					else {
						matrizSolucion[i][j]=c;
					}
				}
			}
		}
		
		if(toRet) {
			if(scn.hasNext()) {
				toRet = false;
				logger.severe("Error: El archivo no cumple con el formato, contiene mas numeros de los que deberia");
			}
			
		for(int i=0; i<cantFilas && toRet; i++) {
			for(int j=0; j<cantFilas && toRet; j++) {
					switch (i) {
		         case 0: case 1: case 2:  
		        	 indiceF = 0;
		        	 break;
		         case 3: case 4: case 5: 
		        	 indiceF= 3;
		        	 break;
		         case 6: case 7: case 8:  
		        	 indiceF = 6;;
		             break;
				 }
				 
				 switch (j) {
		         case 0: case 1: case 2:  
		        	 indiceC = 0;
		        	 break;
		         case 3: case 4: case 5: 
		        	 indiceC= 3;
		        	 break;
		         case 6: case 7: case 8:  
		        	 indiceC = 6;;
		             break;
				 }
				 toRet = verificarPropiedadesMatriz(i,j,indiceF,indiceC);	
			}
		}
		}
		if(toRet) {
			scn.close();
		}
		return toRet;
	}
		
	/**
	 * Verifica que la solucion de la matriz sea valida.
	 * @param fila es la fila correspondiente de la celda que se desea verificar.
	 * @param columna es la columna correspondiente de la celda que se desea verificar.
	 * @param indiceF fila desde donde se va a verificar.
	 * @param indiceC columna desde donde se va a verificar.
	 * @return true si la solucion de la matriz es valida.
	 * */

	private boolean verificarPropiedadesMatriz(int fila, int columna, int indiceF, int indiceC) {
		boolean seVerifica = true;
		int cantVecesEncontrado = 0;
		int nroActual = matrizSolucion[fila][columna]; //este nro puede aparecer 1 vez en la fila, columna y matriz 3x3 perteneciente
			
		for(int i = 0; i< cantFilas && seVerifica; i++) {
			if(matrizSolucion[fila][i] == nroActual) {
				cantVecesEncontrado++;
				if(cantVecesEncontrado>1) {
					logger.severe("El archivo no verifica las propiedades");
					seVerifica = false;	
				}
			}
				
		}
		/** si llego hasta esta instancia cantVeces encontrado sera 1 ya que contemplo toda la fila*/
		if(seVerifica) {
			cantVecesEncontrado = 0;
			for(int j = 0; j<cantFilas && seVerifica; j++) {
				if(matrizSolucion[j][columna] == nroActual) 
					cantVecesEncontrado++;
					if(cantVecesEncontrado>1) {
						logger.severe("El archivo no verifica las propiedades");
						seVerifica = false;	
					}
			}
		}
			
		cantVecesEncontrado=0;
			
		for(int i = indiceF; i<indiceF + 3 && seVerifica; i++){
			for(int j = indiceC; j<indiceC + 3 && seVerifica; j++) {
				if(nroActual == matrizSolucion[i][j]) {
					if(matrizSolucion[i][j] == nroActual)
						cantVecesEncontrado++;
					if(cantVecesEncontrado>1) {
						logger.severe("El archivo no verifica las propiedades");
						seVerifica = false;	
					}
				}		
			}
		}	
			return seVerifica;
	}
	
	/**
	 * Al ser accionada la celda se actualiza su imagen.
	 * @param c, celda accionada.
	 * */		
	public void accionar(Celda c) {
		c.actualizarImagen();
	}
	
	/**
	 * Al ser accionada la celda se actualiza su imagen con una imagen de error.
	 * @param c, celda accionada.
	 * */
	public void accionarError(Celda c) {
		c.actualizarImagenError();
	}
	
	
	/**
	 * Obtiene la Celda ubicada segun los indices pasados por parametro
	 * @param i, fila donde se ubica la celda.
	 * @param j, columna donde se ubica la celda.
	 * @return Celda ubicada en i y j.
	 * */
	public Celda getCelda(int i, int j) {
		return this.tablero[i][j];
	}
	
	/**
	 * Obtiene la cantidad de filas del juego
	 * @return filas del juego.
	 **/
	public int getCantFilas() {
		return this.cantFilas;
	}
	
	
	/**
	 * Obtiene el tablero de celdas del sudoku.
	 * @return tablero del juego.
	 **/
	public Celda[][] getTablero(){
		return tablero;
	}
	
	/**
	 * Obtiene un booleano que indica si el juego cumple las propiedades. 
	 * @return true si el juego cumple las propiedades.
	 **/
	public boolean getCumplePropiedad() {
		return cumplePropiedad;
	}
	

	/**
	 * Verifica que la celda pasada por parametro cumpla con las propiedades del juego.
	 * @param c, celda a verificar.
	 * @return true si la celda verifica las propiedades.
	 * */
	public boolean verificarPropiedades(Celda c) {
		return verificarFila(c) && verificarColumna(c) && verificarPanel(c);
	}
	
	/**
	 * Verifica que la celda pasada por parametro cumpla con las propiedades del juego en la fila donde se ubica.
	 * @param c, celda a verificar.
	 * @return true si la celda verifica las propiedades.
	 * */
	private boolean verificarFila(Celda c) {
		boolean seVerifica = true;
		int fila = c.getFila();
		int nroGraficoCeldaActual = c.getValor();
		
	    for(int i = 0; i < cantFilas; i++) {//Recorro toda la fila
			if(tablero[fila][i].getValor() != null && !(tablero[fila][i].equals(c))){//Me fijo de no pasar por arriba de la celda actual ni de un ?
				if(nroGraficoCeldaActual == tablero[fila][i].getValor()){
					c.setCumplePropiedad(false);
					tablero[fila][i].setCumplePropiedad(false);
					seVerifica = false;
				}
				else {
					c.setCumplePropiedad(true);
					tablero[fila][i].setCumplePropiedad(true);
				}
			}
		}
		return seVerifica;
	}
	
	
	/**
	 * Verifica que la celda pasada por parametro cumpla con las propiedades del juego en la columna donde se ubica.
	 * @param c, celda a verificar.
	 * @return true si la celda verifica las propiedades.
	 * */
	private boolean verificarColumna(Celda c) {
		boolean seVerifica = true;
		int columna = c.getColumna();
		int nroGraficoCeldaActual = c.getValor();
		
		for(int j = 0; j < cantFilas; j++) {//Recorro toda la columna.
			if(tablero[j][columna].getValor() != null  && !(tablero[j][columna].equals(c))){//Me fijo de no pasar por arriba de la celda actual ni de un ?
				if(nroGraficoCeldaActual == tablero[j][columna].getValor()) {
					c.setCumplePropiedad(false);
					tablero[j][columna].setCumplePropiedad(false);
					seVerifica = false;
				}
				else {
					c.setCumplePropiedad(true);
					tablero[j][columna].setCumplePropiedad(true);
				}
			}
		}
		return seVerifica;
	}
	
	/**
	 * Verifica que la celda pasada por parametro cumpla con las propiedades del juego en el panel donde se ubica.
	 * @param c, celda a verificar.
	 * @return true si la celda verifica las propiedades.
	 * */
	private boolean verificarPanel(Celda c) {
		boolean seVerifica = true;
		int fila = c.getFila();
		int columna = c.getColumna();
		int nroGraficoCeldaActual = c.getValor();
		int indiceF = 0;
		int indiceC = 0;
		
		 switch (fila) {
         case 0: case 1: case 2:  
        	 indiceF = 0;
        	 break;
         case 3: case 4: case 5: 
        	 indiceF= 3;
        	 break;
         case 6: case 7: case 8:  
        	 indiceF = 6;;
             break;
		 }
		 
		 switch (columna) {
         case 0: case 1: case 2:  
        	 indiceC = 0;
        	 break;
         case 3: case 4: case 5: 
        	 indiceC= 3;
        	 break;
         case 6: case 7: case 8:  
        	 indiceC = 6;;
             break;
		 }
		
		for(int i = indiceF; i<indiceF + 3; i++){
			for(int j = indiceC; j<indiceC + 3; j++) {
				
				if(tablero[i][j].getValor() != null  && !(tablero[i][j].equals(c))) {
					if(nroGraficoCeldaActual == tablero[i][j].getValor()) {
						c.setCumplePropiedad(false);
						tablero[i][j].setCumplePropiedad(false);
						seVerifica = false;
					}
					else {
						c.setCumplePropiedad(true);
						tablero[i][j].setCumplePropiedad(true);
					}
				}
			}
		}
		return seVerifica;	
	}
	
	/**
	 * Verifica que el juego haya sido ganado. 
	 * @return true si se gano el juego.
	 * */
	public boolean ganar() {
		boolean gane = true;
		Celda c;
	
		for(int i = 0; i<cantFilas && gane; i++) {
			for(int j = 0; j<cantFilas && gane; j++) {
				c = tablero[i][j]; 
				if(c.getValor() == null || !(verificarPropiedades(tablero[i][j]))) {//Si paso por un ? o si no se verifican las propiedades de esa celda.
					gane = false;
				}   
			}
		}
		return gane;		
	}
	
}