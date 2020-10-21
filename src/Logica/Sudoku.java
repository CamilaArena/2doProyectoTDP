package Logica;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
	/**Representa la cantidad de filas (y columnas) del juego*/
	private int cantFilas;
	/**Denota si el juego cumple con las propiedades*/
	private boolean cumplePropiedad;
	/**logger de la clase*/
	private static Logger logger;
	
	//Constructor
	/**
	 * Crea un sudoku nuevo.
	 * @param f, representa la cantidad de filas y columnas del juego
	 */
	 
	public Sudoku(int f) {
		
		cantFilas = f;
		tablero = new Celda[cantFilas][cantFilas]; //Creo el tablero de 9x9

		if(logger == null) {
			
			logger= Logger.getLogger(Sudoku.class.getName());
			
			Handler hnd = new ConsoleHandler();
			hnd.setLevel(Level.INFO);
			
			logger.addHandler(hnd);
			logger.setLevel(Level.WARNING);
			
			Logger rootLogger=logger.getParent();
			
			for(Handler h : rootLogger.getHandlers())
				h.setLevel(Level.OFF);
		}
		
		File archivo = new File("solucion.txt");
		
		if(!archivo.exists()) {
			logger.warning("Error: archivo inexistente");
		}
		else {
			if(checkearSolucion(archivo)) {
				cumplePropiedad = true;
		
				try {
					this.inicializarConArchivo(archivo);
				}
				catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			else {
				cumplePropiedad = false;
			}
		}
	}
	
	//Metodos
	
	/**
	 * Inicializa el juego con los datos del archivo.
	 * @param file, es el archivo que contiene la solucion del juego.
	 * */
	private void inicializarConArchivo(File file) throws IOException, FileNotFoundException {
		Scanner reader=null;
		Random rnd=new Random();
		Celda celda = new Celda();
		int c = 0;
		
		try {
			reader=new Scanner(file);
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		for (int i =0; i<cantFilas; i++) {
			for (int j =0; j<cantFilas; j++) {
				c = reader.nextInt()-1;
				int aux=rnd.nextInt(3);
				celda = new Celda();
				tablero[i][j] = celda;
				celda.setFila(i);
				celda.setColumna(j);
				celda.setCumplePropiedad(true);
				
				if(aux == 0) {
					celda.setValor(c);
				}
			}
		}
			reader.close();
	}
	
	
	/**
	 * Verifica que la solucion del archivo sea valida.
	 * @param file, es un archivo que contiene la solucion del juego.
	 * @return true si la solucion del archivo es valida.
	 * */
	public boolean checkearSolucion(File file) {
		boolean toRet=true;
		int[][] matriz=new int[cantFilas][cantFilas];
		Scanner reader=null;
		int c;
		int indiceF = 0;
		int indiceC = 0;
			
		try {
			reader=new Scanner(file);
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		for(int i=0; i<cantFilas && toRet; i++) {
			for(int j=0; j<cantFilas && toRet; j++) {
				if(reader.hasNext()) {
					c=reader.nextInt();
					if(c<10) {
						matriz[i][j]=c;
					}
					else {
						logger.warning("El archivo no cumple con el formato, existen numero mayores a 9");
						toRet = false;
					}
				}
				else {
					logger.warning("El archivo no cumple con el formato, faltan numeros");
					toRet = false;
				}
			}
		}
		
		if(toRet) {
			if(reader.hasNext()) {//Si leo mas elementos de los que deberia haber en el archivo.
				logger.warning("El archivo no cumple con el formato, hay numeros de mas.");
				toRet = false;	
			}
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
				 toRet=verificarPropiedadesMatriz(i,j,indiceF,indiceC,matriz);
			}		
		}
		return toRet;
	}
		
	/**
	 * Verifica que la solucion de la matriz sea valida.
	 * @param fila es la fila correspondiente de la celda que se desea verificar.
	 * @param columna es la columna correspondiente de la celda que se desea verificar.
	 * @param indiceF fila desde donde se va a verificar.
	 * @param indiceC columna desde donde se va a verificar.
	 * @param m es la matriz donde verifico las propiedades del archivo.
	 * @return true si la solucion de la matriz es valida.
	 * */

	private boolean verificarPropiedadesMatriz(int fila, int columna, int indiceF, int indiceC,int[][]m) {
		boolean seVerifica = true;
		int cantVecesEncontrado = 0;
		int nroActual = m[fila][columna]; //este nro puede aparecer 1 vez en la fila, columna y matriz 3x3 perteneciente
			
		for(int i = 0; i< 9 && seVerifica; i++) {//Recorro toda la fila
			if(m[fila][i] == nroActual) {
				cantVecesEncontrado++;
				if(cantVecesEncontrado>1) {
					logger.warning("El archivo no verifica las propiedades");
					seVerifica = false;	
				}
			}
				
		}
		/** si llego hasta esta instancia cantVeces encontrado sera 1 ya que contemplo toda la fila*/
		if(seVerifica) {
			cantVecesEncontrado = 0;
			for(int j = 0; j<9 && seVerifica; j++) {//Recorro toda la columna
				if(m[j][columna] == nroActual) //Si se repite el valor de la imagen no se verifica la propiedad
					cantVecesEncontrado++;
					if(cantVecesEncontrado>1) {
						logger.warning("El archivo no verifica las propiedades");
						seVerifica = false;	
					}
			}
		}
			
		cantVecesEncontrado=0;
			
		for(int i = indiceF; i<indiceF + 3 && seVerifica; i++){
			for(int j = indiceC; j<indiceC + 3 && seVerifica; j++) {
				if(nroActual==m[i][j]) {
					if(m[i][j] == nroActual)
						cantVecesEncontrado++;
					if(cantVecesEncontrado>1) {
						logger.warning("El archivo no verifica las propiedades");
						seVerifica = false;	
					}
				}
					
			}
		}	
			return seVerifica;
	}
	
	/**
	 * Al ser accionada la celda se actualiza su imagen.
	 * @param celda accionada.
	 * */		
	public void accionar(Celda c) {
		c.actualizarImagen();
	}
	
	/**
	 * Al ser accionada la celda se actualiza su imagen con una imagen de error.
	 * @param celda accionada.
	 * */
	public void accionarError(Celda c) {
		c.actualizarImagenError();
	}
	
	/**
	 * Modifica cumplePropiedad por el pasado por parametro.
	 * @param c, booleano que representa si se cumple la propiedad.
	 * */
	public void setCumplePropiedad(boolean c) {
		cumplePropiedad = c;
	}
	
	/**
	 * Obtiene la Celda ubicada segun los indices pasados por parametro
	 * @param i, fila donde se ubica la celda.
	 * @param j, columna donde se ubica la celda.
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
	 * Obtiene un booleano que indica si el juego cumple las propiedades. 
	 * @return true si el juego cumple las propiedades.
	 **/
	public boolean getCumplePropiedad() {
		return cumplePropiedad;
	}
	
	/**
	 * Obtiene el tablero de celdas del sudoku.
	 * @return tablero del juego.
	 **/
	public Celda[][] getTablero(){
		return tablero;
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
		cumplePropiedad = gane;
		return gane;		
	}
	
}