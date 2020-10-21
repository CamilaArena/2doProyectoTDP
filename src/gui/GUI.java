package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Logica.Celda;
import Logica.Reloj;
import Logica.Sudoku;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.TimerTask;

import javax.sound.sampled.AudioSystem;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class GUI extends JFrame{

	private JPanel contentPane;
	private Sudoku juego;
	private JButton[][] celdas;
	private JPanel panelCeldas;
	private PanelConFondo panelBotones;
	private Reloj r;
	private JLabel labelSegundos;
	private JLabel labelSegundos2;
	private JLabel labelMinutos;
	private JLabel labelMinutos2;
	private JLabel labelHoras;
	private JLabel labelHoras2;
	private String[] imagenesReloj;
	private boolean jugar;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	
	public GUI() {
		initialize();
	}
	
	private void initialize() {
		juego = new Sudoku(9);
		if(!juego.getCumplePropiedad()) {
			//Sonido error
			/*javax.sound.sampled.Clip sonidoError = null;
		     try{
		       sonidoError=AudioSystem.getClip();
		       sonidoError.open(AudioSystem.getAudioInputStream(new File("\\Sonidos\\error.wav")));
		     }
		     catch(Exception e1){
		    	 System.out.println(e1);
		    }
		     
		    sonidoError.start();*/
			JOptionPane.showMessageDialog(null, "Error: no se pudo inicializar el juego");
			System.exit(0);		
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 755, 466);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//*********PANEL CELDAS**************
		panelCeldas = new JPanel();
		panelCeldas.setBounds(0, 0, 427, 427);
		contentPane.add(panelCeldas);
		panelCeldas.setLayout(new GridLayout(9,9));
		inicializarBotonesCelda();
		
		
		//********PANEL BOTONES Y RELOJ **************
		panelBotones= new PanelConFondo();
		panelBotones.setBounds(426, 0, 313, 427);
		contentPane.add(panelBotones);
		panelBotones.setLayout(null);
		Image background = panelBotones.getImagenFondo();
		panelBotones.setBackground(background);
		this.imagenesReloj = new String[] {"/Imagenes/Clock/0.png","/Imagenes/Clock/1.png","/Imagenes/Clock/2.png","/Imagenes/Clock/3.png","/Imagenes/Clock/4.png","/Imagenes/Clock/5.png","/Imagenes/Clock/6.png","/Imagenes/Clock/7.png","/Imagenes/Clock/8.png","/Imagenes/Clock/9.png"};
				
		//BOTON INICIAR
		JButton btnIniciar = new JButton("Iniciar");
		btnIniciar.setForeground(Color.BLACK);
		btnIniciar.setFont(new Font("Yu Gothic UI", Font.PLAIN, 20));
		btnIniciar.setBounds(39, 356, 127, 42);
		panelBotones.add(btnIniciar);
		btnIniciar.setBackground(java.awt.Color.yellow);
		
		//BOTON FINALIZAR
		JButton btnFinalizar = new JButton("Finalizar");
		btnFinalizar.setForeground(Color.BLACK);
		btnFinalizar.setFont(new Font("Yu Gothic UI", Font.PLAIN, 20));
		btnFinalizar.setBounds(176, 356, 127, 42);
		panelBotones.add(btnFinalizar);
		btnFinalizar.setBackground(java.awt.Color.yellow);
		btnFinalizar.setEnabled(false);
		
		//Labels hora
		labelHoras = new JLabel("New label");
		labelHoras.setBounds(10, 124, 42, 57);
		ImageIcon cero = new ImageIcon(this.getClass().getResource(imagenesReloj[0]));
		labelHoras.setIcon(cero);
		reDimensionarLabel(labelHoras,cero);
		panelBotones.add(labelHoras);
		
		labelHoras2 = new JLabel("New label");
		labelHoras2.setBounds(61, 124, 42, 57);
		labelHoras2.setIcon(cero);
		reDimensionarLabel(labelHoras2,cero);
		panelBotones.add(labelHoras2);
		
		//Labels minutos
		labelMinutos = new JLabel("New label");
		labelMinutos.setBounds(111, 124, 42, 57);
		labelMinutos.setIcon(cero);
		reDimensionarLabel(labelMinutos,cero);
		panelBotones.add(labelMinutos);
		
		labelMinutos2 = new JLabel("New label");
		labelMinutos2.setBounds(163, 124, 42, 57);
		labelMinutos2.setIcon(cero);
		reDimensionarLabel(labelMinutos2,cero);
		panelBotones.add(labelMinutos2);
		
		//Labels segundos
		
		labelSegundos = new JLabel("");
		labelSegundos.setBounds(215, 124, 42, 57);
		labelSegundos.setIcon(cero);
		reDimensionarLabel(labelSegundos,cero);
		panelBotones.add(labelSegundos);
		
		labelSegundos2 = new JLabel("New label");
		labelSegundos2.setBounds(267, 124, 42, 57);
		labelSegundos2.setIcon(cero);
		reDimensionarLabel(labelSegundos2,cero);
		panelBotones.add(labelSegundos2);
	

		//------------------ActionListeners-------------------------
		//BOTON INICIAR
				btnIniciar.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
					    
					/*	//Sonido coin
					       AudioInputStream soundStream = null;
						try {
							soundStream = AudioSystem.getAudioInputStream(new File("/Sonidos/Coin.wav"));
						} catch (UnsupportedAudioFileException | IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					       //sonidoCoin.open(AudioSystem.getAudioInputStream(new File("Sonidos\\Coin.wav")));
					       Clip sonidoCoin = null;
						try {
							sonidoCoin = AudioSystem.getClip();
						} catch (LineUnavailableException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					       try {
							sonidoCoin.open(soundStream);
						} catch (LineUnavailableException | IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					       sonidoCoin.start();*/
					   
					       for (int i = 0; i <juego.getCantFilas(); i++) {
							for(int j =0; j<juego.getCantFilas(); j++) {
								celdas[i][j].setEnabled(true);
							}
						}
						jugar = true;
						panelCeldas.setVisible(true);
						iniciarReloj();
						btnIniciar.setEnabled(false);
						btnFinalizar.setEnabled(true);
					}
				});	
			
			
		//BOTON FINALIZAR
				btnFinalizar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						jugar = false;
						
					if(juego.ganar() == true) {	
						
						//Sonido win
						/*javax.sound.sampled.Clip sonidoWin = null;
					     try{
					       sonidoWin=AudioSystem.getClip();
					       sonidoWin.open(AudioSystem.getAudioInputStream(new File("\\Win.wav")));
					     }
					     catch(Exception e1){
					    	 System.out.println(e1);
					    }
					     
					     	sonidoWin.start();*/
							JOptionPane.showMessageDialog(null, "¡Felicitaciones, ganaste el juego!");
						}
					
						else {
							//Sonido game over
							/*javax.sound.sampled.Clip sonido = null;
						     try
						     {
						       sonido=AudioSystem.getClip();
						       sonido.open(AudioSystem.getAudioInputStream(new File("\\Sonidos\\GameOver.wav")));
						     }catch(Exception e1)
						       {System.out.println(e1);}
					        sonido.start();*/
					        
					        JOptionPane.showMessageDialog(null, "¡Perdiste!");
						}
						panelCeldas.setVisible(false);
						btnFinalizar.setEnabled(false);
						btnIniciar.setEnabled(false);
					}
				});	
			}
				
		//RELOJ
			/**
	 		*Crea un nuevo reloj y lo inicializa.
	 		*/
			private void iniciarReloj() {
				r= new Reloj();
				TimerTask task = new TimerTask() {
					int p=0;
					public void run() {
					
						@SuppressWarnings("unused")
						String tiempo=r.getTime(p);
						p++;
					
						if(jugar) {
							String seg = r.getSeg();
							String min = r.getMin();
							String hora = r.getHora();
							
							int segundos = Integer.parseInt(seg);
					
							int s1 = (segundos/10)%10;//primer digito
							ImageIcon iconoS = new ImageIcon(this.getClass().getResource(imagenesReloj[s1]));
							labelSegundos.setIcon(iconoS);
							reDimensionarLabel(labelSegundos,iconoS);
					
							int s2 = segundos%10;//segundo digito
							ImageIcon iconoS2 = new ImageIcon(this.getClass().getResource(imagenesReloj[s2]));
							labelSegundos2.setIcon(iconoS2);
							reDimensionarLabel(labelSegundos,iconoS2);
					
							int minutos = Integer.parseInt(min);
							int m1 = (minutos/10)%10;//primer digito
							ImageIcon iconoM = new ImageIcon(this.getClass().getResource(imagenesReloj[m1]));
							labelMinutos.setIcon(iconoM);
							reDimensionarLabel(labelMinutos,iconoM);
					
							int m2 = minutos%10;//segundo digito
							ImageIcon iconoM2 = new ImageIcon(this.getClass().getResource(imagenesReloj[m2]));
							labelMinutos2.setIcon(iconoM2);
							reDimensionarLabel(labelMinutos2,iconoM2);
					
							int horas= Integer.parseInt(hora);
							int h1 = (horas/10)%10;//primer digito
							ImageIcon iconoH = new ImageIcon(this.getClass().getResource(imagenesReloj[h1]));
							labelMinutos.setIcon(iconoH);
							reDimensionarLabel(labelHoras,iconoH);
					
							int h2 = horas%10;//segundo digito
							ImageIcon iconoH2 = new ImageIcon(this.getClass().getResource(imagenesReloj[h2]));
							labelHoras2.setIcon(iconoH2);
							reDimensionarLabel(labelHoras,iconoH2);	
						}
				
					}	
				
				};
			
				r.getTimer().schedule(task,0,1000);
			}

		/**
		 * Inicializa gráficamente cada uno de los botones de la grilla de juego.
		 */
		private void inicializarBotonesCelda() {
			celdas = new JButton[juego.getCantFilas()][juego.getCantFilas()];
			
			for (int i = 0; i <juego.getCantFilas(); i++) {
				for(int j =0; j<juego.getCantFilas(); j++) {
					
					JButton b = new JButton();
					celdas[i][j] = b;
					Celda c = juego.getCelda(i,j);
					ImageIcon icono = c.getEntidadGrafica().getGrafico();

					setMinimumSize(new Dimension(25,25));
					b.setVisible(true);
					b.setEnabled(false);
					b.setIcon(icono);
					
					//Listeners de las celdas
					b.addComponentListener(new ComponentAdapter() {
						@Override
						public void componentResized(ComponentEvent e) {
							reDimensionar(b, icono);
							b.setIcon(icono);
						}
					});
					
					if(c.getValor() == null) {//Solo modifico a las celdas con ?
						b.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							c.actualizarValor();
						    juego.accionar(c);
						    
							Celda[][] tableroJuego = juego.getTablero();
							
							for(int i = 0; i< juego.getCantFilas(); i++) {
								for(int j = 0; j<juego.getCantFilas(); j++) {
									
									if(tableroJuego[i][j].getValor()!=null){
										if(!juego.verificarPropiedades(tableroJuego[i][j])) {///Si la celda no es un ? y no verifica las propiedades
											juego.accionarError(tableroJuego[i][j]);//le pongo la imagen roja
										}
										else {
											juego.accionar(tableroJuego[i][j]);//le pongo la imagen azul
										}	
									}
									
									ImageIcon icon = tableroJuego[i][j].getEntidadGrafica().getGrafico();
									reDimensionar(celdas[i][j],icon);
								}	
							}
						}
						});	
					}
					else {
						b.setBorder(BorderFactory.createLineBorder(Color.white));
					}
					panelCeldas.add(b);
				}
			}
		}
			
	/**
	 * Redimensiona el icono de un boton.
	 * @param boton, boton donde se encuentra la imagen a redimensionar.
	 * @param grafico, icono a redimensionar.
	 */
	private void reDimensionar(JButton boton, ImageIcon grafico) {
		Image image = grafico.getImage();
		if (image != null) {  
			Image newimg = image.getScaledInstance(boton.getWidth(), boton.getHeight(),  java.awt.Image.SCALE_SMOOTH);
			grafico.setImage(newimg);
			boton.repaint();
		}
	}
	
	/**
	 * Redimensiona el icono de una label.
	 * @param lbl, label donde se encuentra la imagen a redimensionar.
	 * @param grafico, icono a redimensionar.
	 */
	private void reDimensionarLabel(JLabel lbl, ImageIcon grafico) {
		Image image = grafico.getImage();
		if (image != null) {  
			Image newimg = image.getScaledInstance(lbl.getWidth(), lbl.getHeight(),  java.awt.Image.SCALE_SMOOTH);
			grafico.setImage(newimg);
			lbl.repaint();
		}
	}
}