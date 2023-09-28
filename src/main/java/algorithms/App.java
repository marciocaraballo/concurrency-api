package algorithms;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import java.awt.GridLayout;
import java.awt.CardLayout;
import javax.swing.SpringLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class App {

	private JFrame frame;
	private JTextField iterationsCountInput;
	private JTextField inputSizeInput;
	private JTextField threadsCountInput;
	private JTextField avgParallelTimeInput;
	private JComboBox<String[]> comboBox;
	private JTextField avgSequentialTimeInput;
	private JTextField accelerationInput;
	private JLabel inputSizeLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App window = new App();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public App() {
		initialize();
	}
	
	private void runPi(int inputSize, int iterationsCount, int threadsCount) {
		
		PiParallel PiParallelCalculator = new PiParallel(threadsCount);
		
		PiSequential PiCalculator = new PiSequential();
		
		Double piAvgTime = PiCalculator.averageExecTime(inputSize, iterationsCount);
		
		Double piParallelAvgTime = PiParallelCalculator.averageExecTime(inputSize, iterationsCount);
		
		avgSequentialTimeInput.setText(piAvgTime.toString());
		avgParallelTimeInput.setText(piParallelAvgTime.toString());
		
		Double acceleration = piAvgTime / piParallelAvgTime;
		DecimalFormat df = new DecimalFormat("#.##");
		
		accelerationInput.setText(df.format(acceleration));
	}
	
	private void runMatrix(int inputSize, int iterationsCount, int threadsCount) {
		
		MatrixParallel MatrixParallelCalculator = new MatrixParallel(threadsCount);
		MatrixSequential MatrixCalculator = new MatrixSequential();
		
		Double matrixAvgTime = MatrixCalculator.averageExecTime(inputSize, iterationsCount);
		
		Double matrixParallelAvgTime = MatrixParallelCalculator.averageExecTime(inputSize, iterationsCount);
		
		avgSequentialTimeInput.setText(matrixAvgTime.toString());
		avgParallelTimeInput.setText(matrixParallelAvgTime.toString());
		
		Double acceleration = matrixAvgTime / matrixParallelAvgTime;
		DecimalFormat df = new DecimalFormat("#.##");
		
		accelerationInput.setText(df.format(acceleration));
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 16));
		frame.setBounds(100, 100, 400, 534);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Java concurrency API");
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Algoritmo");
		lblNewLabel.setToolTipText("Se debe elegir que algoritmo se desea ejecutar");
		lblNewLabel.setBounds(10, 67, 79, 20);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		frame.getContentPane().add(lblNewLabel);
		
		comboBox = new JComboBox<String[]>();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				/** 
				 * Se setean algunos valores por defecto para evitar correr uno de los algoritmos 
				 * con un inputSize recomendado para el otro
				 */
				if (comboBox.getSelectedIndex() == 0) {
					inputSizeInput.setText("150000000");
					inputSizeLabel.setText("Cantidad de steps");
				} else {
					inputSizeInput.setText("700");
					inputSizeLabel.setText("Dimensiones");
				}
			}
		});
		comboBox.setBounds(151, 64, 203, 32);
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 16));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Calculo de Pi", "Multiplicacion de Matrices"}));
		frame.getContentPane().add(comboBox);
		
		JLabel lblNewLabel_1 = new JLabel("Iteraciones");
		lblNewLabel_1.setToolTipText("Se debe elegir cuantas veces se correra cada algoritmo, para calcular un promedio de los tiempso de ejecucion");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(10, 208, 79, 13);
		frame.getContentPane().add(lblNewLabel_1);
		
		iterationsCountInput = new JTextField();
		iterationsCountInput.setText("10");
		iterationsCountInput.setFont(new Font("Tahoma", Font.PLAIN, 16));
		iterationsCountInput.setBounds(151, 201, 61, 26);
		frame.getContentPane().add(iterationsCountInput);
		iterationsCountInput.setColumns(10);
		
		inputSizeLabel = new JLabel("Numero de steps");
		inputSizeLabel.setToolTipText("Para el calculo de pi, se elije el valor de steps. Para multiplicar matrices, se elije las dimensiones de las mismas");
		inputSizeLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		inputSizeLabel.setBounds(10, 115, 136, 18);
		frame.getContentPane().add(inputSizeLabel);
		
		inputSizeInput = new JTextField();
		inputSizeInput.setText("150000000");
		inputSizeInput.setFont(new Font("Tahoma", Font.PLAIN, 16));
		inputSizeInput.setBounds(151, 111, 203, 26);
		frame.getContentPane().add(inputSizeInput);
		inputSizeInput.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Configuracion");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_3.setBounds(10, 20, 122, 26);
		frame.getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Resultados");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_4.setBounds(10, 307, 109, 20);
		frame.getContentPane().add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Numero de threads");
		lblNewLabel_5.setToolTipText("Se debe elegir cuantos threads se crearan para ejecutar la version concurrente");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_5.setBounds(10, 159, 143, 16);
		frame.getContentPane().add(lblNewLabel_5);
		
		threadsCountInput = new JTextField();
		threadsCountInput.setText("4");
		threadsCountInput.setFont(new Font("Tahoma", Font.PLAIN, 16));
		threadsCountInput.setBounds(151, 154, 61, 26);
		frame.getContentPane().add(threadsCountInput);
		threadsCountInput.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("Tiempo promedio");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_6.setBounds(10, 344, 186, 20);
		frame.getContentPane().add(lblNewLabel_6);
		
		avgParallelTimeInput = new JTextField();
		avgParallelTimeInput.setFont(new Font("Tahoma", Font.PLAIN, 16));
		avgParallelTimeInput.setEditable(false);
		avgParallelTimeInput.setBounds(126, 423, 92, 26);
		frame.getContentPane().add(avgParallelTimeInput);
		avgParallelTimeInput.setColumns(10);
		
		JButton btnNewButton = new JButton("Calcular");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Integer iterationsCount = Integer.parseInt(iterationsCountInput.getText(), 10);
					Integer threadsCount = Integer.parseInt(threadsCountInput.getText(), 10);
					Integer inputSize = Integer.parseInt(inputSizeInput.getText(), 10);
					
					if (comboBox.getSelectedIndex() == 0) {
						runPi(inputSize, iterationsCount, threadsCount);
					} else {
						runMatrix(inputSize, iterationsCount, threadsCount);
					}
				} catch (NumberFormatException ex) {
					ex.printStackTrace();
				}
			}
		});
		
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton.setBounds(10, 250, 109, 26);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel_7 = new JLabel("Paralelo");
		lblNewLabel_7.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_7.setBounds(10, 425, 69, 22);
		frame.getContentPane().add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("Secuencial");
		lblNewLabel_8.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_8.setBounds(10, 387, 79, 25);
		frame.getContentPane().add(lblNewLabel_8);
		
		avgSequentialTimeInput = new JTextField();
		avgSequentialTimeInput.setEditable(false);
		avgSequentialTimeInput.setFont(new Font("Tahoma", Font.PLAIN, 16));
		avgSequentialTimeInput.setBounds(126, 385, 92, 28);
		frame.getContentPane().add(avgSequentialTimeInput);
		avgSequentialTimeInput.setColumns(10);
		
		JLabel lblNewLabel_9 = new JLabel("ms");
		lblNewLabel_9.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_9.setBounds(228, 391, 31, 17);
		frame.getContentPane().add(lblNewLabel_9);
		
		JLabel lblNewLabel_10 = new JLabel("ms");
		lblNewLabel_10.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_10.setBounds(228, 430, 31, 13);
		frame.getContentPane().add(lblNewLabel_10);
		
		JLabel lblNewLabel_11 = new JLabel("Aceleracion");
		lblNewLabel_11.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_11.setBounds(10, 463, 92, 20);
		frame.getContentPane().add(lblNewLabel_11);
		
		accelerationInput = new JTextField();
		accelerationInput.setEditable(false);
		accelerationInput.setFont(new Font("Tahoma", Font.PLAIN, 16));
		accelerationInput.setBounds(126, 459, 92, 28);
		frame.getContentPane().add(accelerationInput);
		accelerationInput.setColumns(10);
	}
}
