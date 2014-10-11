package Sujet_Taxi_GUI;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Sujet_Taxi_GUI.Tarif;
import Sujet_Taxi_GUI.Saisies;

public class GUI extends JFrame {
	
	static Saisies saisies = new Saisies();
	static List<Tarif> tarifs = new ArrayList<Tarif>();
	
	private JPanel contentPane;
	private JLabel lblSujetTaxi;
	private JLabel labelDepartement;
	private JTextField saisieDepartement;
	private JButton searchDepartement;
	private JLabel labelTypeTrajet;
	private JRadioButton rbSimple;
	private JRadioButton rbRetour;
	private ButtonGroup TypeTrajet;
	private JLabel labelJour;
	private JRadioButton rbSemaine;
	private JRadioButton rbWeekend;
	private ButtonGroup Jour;
	private JLabel labelHeure;
	private JRadioButton rbJour;
	private JRadioButton rbNuit;
	private ButtonGroup Heure;
	private JLabel labeDuree;
	private JTextField saisieDuree;
	private JLabel labelKilometres;
	private JTextField saisieKilometre;
	private JLabel labelPrix;
	private JLabel prix;
	private JButton calculTarif;
	private JButton Reset;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					try {
						// Ouverture d'un flux d'entrée à tarifir du fichier 'grilleTarifs'
						BufferedReader monBuffer = new BufferedReader(new FileReader("/home/ronan/Documents/developpement/java/cours/src/Sujet_Taxi/grilleTarifs"));
						String line = null;

						// Tant qu'il reste une ligne au fichier
						while ((line = monBuffer.readLine()) != null) {
							// On découpe la ligne gràce au caractère ','
							String[] tarif = line.split(",");
							// Implémentation des grilles de tarifs à tarifir de la ligne du fichier découpée
							tarifs.add(new Tarif(Integer.parseInt(tarif[0]), Double.parseDouble(tarif[1]), Double.parseDouble(tarif[2]), Double.parseDouble(tarif[3]), Double.parseDouble(tarif[4]), Double.parseDouble(tarif[5]), Double.parseDouble(tarif[6]), Double.parseDouble(tarif[7])));
						}
						// Fermeture du buffer
						monBuffer.close();
					} catch (Exception e) {
						System.out.println("Fichier contenant les tarifs introuvable !!!");
					}

					GUI taxiGUI = new GUI();
					taxiGUI.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Fonction de recherche de la grille de tarifs correspondante au detarifement saisi
	 * @return indice (int)
	 */
	public static int Recherche()
	{
		int indice = -1;
		for (int i = 0; i < tarifs.size(); i++) { // Pour toutes les lignes du tableau,
			if (tarifs.get(i).getDepartement() == saisies.getDepartement()){ // Si un detarifement correspond à celui saisie
				indice = i; // Stockage de l'indice de la ligne dans le tableau
				break;
			}
		}
		return indice;
	}
	
	/**
	 * Fonction de calcul du prix à payer en fonction du détarifement saisi
	 * @param indiceDept (int)
	 * @return prix (double)
	 */
	public static double Calcul(int indiceDept)
	{
		double prix = 0.0;

		if (saisies.getTypeTrajet() == 's') { // Si le trajet est un aller simple
			if (saisies.getJour() == 's' && saisies.getHeure() == 'j') { // En semaine, de jour
				prix = tarifs.get(indiceDept).getPriseEnCharge() + (saisies.getKm() * tarifs.get(indiceDept).getKmAlleSimpleJourSem());
				if (saisies.getDuree() > 60) // Et de plus d'une heure
					prix += (int) (saisies.getDuree() / 60) * tarifs.get(indiceDept).getTarifHoraireJourSem();
			} else { // Sinon, s'il est de nuit, ou le dimanche
				prix = tarifs.get(indiceDept).getPriseEnCharge() + (saisies.getKm() * tarifs.get(indiceDept).getKmAlleSimpleNuitDim());
				if (saisies.getDuree() > 60) // ET de plus d'une heure
					prix += (int) (saisies.getDuree() / 60) * tarifs.get(indiceDept).getTarifHoraireNuitDim();
			}
		} else if (saisies.getTypeTrajet() == 'r') { // Si le trajet est un aller retour
			if (saisies.getJour() == 's' && saisies.getHeure() == 'j') { // En semaine, de jour
				prix = tarifs.get(indiceDept).getPriseEnCharge() + (saisies.getKm() * tarifs.get(indiceDept).getKmAlleRetourJourSem());
				if (saisies.getDuree() > 60) // Et de plus d'un heure
					prix += (int) (saisies.getDuree() / 60) * tarifs.get(indiceDept).getTarifHoraireJourSem();
			} else { // Sinon, s'il est de nuit ou le dimanche
				prix = tarifs.get(indiceDept).getPriseEnCharge() + (saisies.getKm() * tarifs.get(indiceDept).getKmAlleRetourNuitDim());
				if (saisies.getDuree() > 60) // Et de plus d'une heure
					prix += (int) (saisies.getDuree() / 60) * tarifs.get(indiceDept).getTarifHoraireNuitDim();
			}
		}
		
		return prix;
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
			}
		});
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 435, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblSujetTaxi = new JLabel("SujetTaxi");
		lblSujetTaxi.setForeground(Color.BLACK);
		lblSujetTaxi.setFont(new Font("Dialog", Font.BOLD, 20));
		lblSujetTaxi.setBounds(160, 12, 120, 25);
		contentPane.add(lblSujetTaxi);
		
		labelDepartement = new JLabel("Département :");
		labelDepartement.setBounds(30, 70, 110, 15);
		contentPane.add(labelDepartement);
		
		saisieDepartement = new JTextField();
		saisieDepartement.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				int lastCharIndex = saisieDepartement.getText().length();
				
				if (lastCharIndex != 0) {
					searchDepartement.setEnabled(true);
					String oldString = saisieDepartement.getText();
					char lastChar = saisieDepartement.getText().charAt(lastCharIndex - 1);
					if (Character.isDigit(lastChar) == true)
						saisieDepartement.setText(oldString);
					else
						saisieDepartement.setText(oldString.substring(0, lastCharIndex - 1));
					if (saisieDepartement.getText().length() > 2)
						saisieDepartement.setText(oldString.substring(0, lastCharIndex - 1));
				}
			}
		});
		saisieDepartement.setBounds(150, 65, 50, 25);
		saisieDepartement.setColumns(10);
		contentPane.add(saisieDepartement);
		
		searchDepartement = new JButton("Rechercher");
		searchDepartement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (saisieDepartement.isEditable() == true) {
					saisies.setDepartement(Integer.parseInt(saisieDepartement.getText()));

					if (Recherche() != -1) {
						searchDepartement.setText("Modifier");
						saisieDepartement.setEditable(false);
						rbSimple.setEnabled(true);
						rbRetour.setEnabled(true);
						rbSemaine.setEnabled(true);
						rbWeekend.setEnabled(true);
						rbJour.setEnabled(true);
						rbNuit.setEnabled(true);
						saisieDuree.setEnabled(true);
						saisieKilometre.setEnabled(true);
					} else
						JOptionPane.showMessageDialog(null, "Département inconnu !", "Erreur !", JOptionPane.ERROR_MESSAGE);
				} else {
					searchDepartement.setText("Rechercher");
					saisieDepartement.setEditable(true);
					rbSimple.setEnabled(false);
					rbRetour.setEnabled(false);
					rbSemaine.setEnabled(false);
					rbWeekend.setEnabled(false);
					rbJour.setEnabled(false);
					rbNuit.setEnabled(false);
					saisieDuree.setEnabled(false);
					saisieKilometre.setEnabled(false);
				}
			}
		});
		searchDepartement.setBounds(210, 65, 120, 25);
		searchDepartement.setEnabled(false);
		contentPane.add(searchDepartement);
		
		labelTypeTrajet = new JLabel("Type de trajet :");
		labelTypeTrajet.setBounds(30, 120, 110, 15);
		contentPane.add(labelTypeTrajet);
		
		rbSimple = new JRadioButton("Aller simple");
		rbSimple.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				saisies.setTypeTrajet('s');
				
				if(saisies.getJour() != 0 && saisies.getHeure() != 0 && saisies.getDuree() != 0 && saisies.getKm() != 0){
					calculTarif.setEnabled(true);
				}
			}
		});
		rbSimple.setEnabled(false);
		rbSimple.setBounds(150, 117, 130, 23);
		contentPane.add(rbSimple);
		
		rbRetour = new JRadioButton("Aller / Retour");
		rbRetour.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				saisies.setTypeTrajet('r');
				
				if(saisies.getJour() != 0 && saisies.getHeure() != 0 && saisies.getDuree() != 0 && saisies.getKm() != 0){
					calculTarif.setEnabled(true);
				}
			}
		});
		rbRetour.setEnabled(false);
		rbRetour.setBounds(280, 117, 130, 23);
		contentPane.add(rbRetour);
		
		TypeTrajet = new ButtonGroup();
		TypeTrajet.add(rbSimple);
		TypeTrajet.add(rbRetour);
		
		labelJour = new JLabel("Jour :");
		labelJour.setBounds(30, 150, 60, 15);
		contentPane.add(labelJour);
		
		rbSemaine = new JRadioButton("Semaine");
		rbSemaine.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				saisies.setJour('s');
				
				if(saisies.getTypeTrajet() != 0 && saisies.getHeure() != 0 && saisies.getDuree() != 0 && saisies.getKm() != 0){
					calculTarif.setEnabled(true);
				}
			}
		});
		rbSemaine.setEnabled(false);
		rbSemaine.setBounds(150, 147, 130, 23);
		contentPane.add(rbSemaine);
		
		rbWeekend = new JRadioButton("Week-end");
		rbWeekend.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				saisies.setJour('w');
				
				if(saisies.getTypeTrajet() != 0 && saisies.getHeure() != 0 && saisies.getDuree() != 0 && saisies.getKm() != 0){
					calculTarif.setEnabled(true);
				}
			}
		});
		rbWeekend.setEnabled(false);
		rbWeekend.setBounds(280, 147, 130, 23);
		contentPane.add(rbWeekend);
		
		Jour = new ButtonGroup();
		Jour.add(rbSemaine);
		Jour.add(rbWeekend);
		
		labelHeure = new JLabel("Heure :");
		labelHeure.setBounds(30, 180, 70, 15);
		contentPane.add(labelHeure);
		
		rbJour = new JRadioButton("Jour");
		rbJour.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				saisies.setHeure('j');
				
				if(saisies.getTypeTrajet() != 0 && saisies.getJour() != 0 && saisies.getDuree() != 0 && saisies.getKm() != 0){
					calculTarif.setEnabled(true);
				}
			}
		});
		rbJour.setEnabled(false);
		rbJour.setBounds(150, 177, 103, 23);
		contentPane.add(rbJour);
		
		rbNuit = new JRadioButton("Nuit");
		rbNuit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				saisies.setHeure('n');
				
				if(saisies.getTypeTrajet() != 0 && saisies.getJour() != 0 && saisies.getDuree() != 0 && saisies.getKm() != 0){
					calculTarif.setEnabled(true);
				}
			}
		});
		rbNuit.setEnabled(false);
		rbNuit.setBounds(280, 177, 130, 23);
		contentPane.add(rbNuit);
		
		Heure = new ButtonGroup();
		Heure.add(rbJour);
		Heure.add(rbNuit);

		labeDuree = new JLabel("Durée :");
		labeDuree.setBounds(30, 210, 70, 15);
		contentPane.add(labeDuree);
		
		saisieDuree = new JTextField();
		saisieDuree.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				int lastCharIndex = saisieDuree.getText().length();
				
				if (lastCharIndex != 0) {
					saisieDuree.setEnabled(true);
					String oldString = saisieDuree.getText();
					char lastChar = saisieDuree.getText().charAt(lastCharIndex - 1);
					if (Character.isDigit(lastChar) == true)
						saisieDuree.setText(oldString);
					else
						saisieDuree.setText(oldString.substring(0, lastCharIndex - 1));
					if (saisieDuree.getText().length() > 4)
						saisieDuree.setText(oldString.substring(0, lastCharIndex - 1));
					
					saisies.setDuree(Integer.parseInt(saisieDuree.getText()));
					
					if(saisies.getTypeTrajet() != 0 && saisies.getJour() != 0 && saisies.getHeure() != 0 && saisies.getKm() != 0){
						calculTarif.setEnabled(true);
					}
				}
			}
		});
		saisieDuree.setEnabled(false);
		saisieDuree.setColumns(10);
		saisieDuree.setBounds(150, 210, 153, 25);
		contentPane.add(saisieDuree);
		
		labelKilometres = new JLabel("Kilomètres :");
		labelKilometres.setBounds(30, 240, 110, 15);
		contentPane.add(labelKilometres);
		
		saisieKilometre = new JTextField();
		saisieKilometre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				int lastCharIndex = saisieKilometre.getText().length();
				
				if (lastCharIndex != 0) {
					saisieKilometre.setEnabled(true);
					String oldString = saisieKilometre.getText();
					char lastChar = saisieKilometre.getText().charAt(lastCharIndex - 1);
					if (Character.isDigit(lastChar) == true)
						saisieKilometre.setText(oldString);
					else
						saisieKilometre.setText(oldString.substring(0, lastCharIndex - 1));
					if (saisieKilometre.getText().length() > 4)
						saisieKilometre.setText(oldString.substring(0, lastCharIndex - 1));
					
					saisies.setKm(Integer.parseInt(saisieKilometre.getText()));
					
					if(saisies.getTypeTrajet() != 0 && saisies.getJour() != 0 && saisies.getHeure() != 0 && saisies.getDuree() != 0){
						calculTarif.setEnabled(true);
					}
				}
			}
		});
		saisieKilometre.setEnabled(false);
		saisieKilometre.setColumns(10);
		saisieKilometre.setBounds(150, 240, 153, 25);
		contentPane.add(saisieKilometre);
		
		labelPrix = new JLabel("Prix :");
		labelPrix.setFont(new Font("Dialog", Font.BOLD, 14));
		labelPrix.setBounds(30, 290, 70, 15);
		labelPrix.setVisible(false);
		contentPane.add(labelPrix);
		
		prix = new JLabel("labelPrix");
		prix.setFont(new Font("Dialog", Font.BOLD, 14));
		prix.setBounds(150, 290, 130, 19);
		prix.setVisible(false);
		contentPane.add(prix);
		
		calculTarif = new JButton("Valider");
		calculTarif.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				labelPrix.setVisible(true);
				prix.setVisible(true);
				prix.setText(Double.toString(Calcul(Recherche())) + " €");
			}
		});
		calculTarif.setEnabled(false);
		calculTarif.setBounds(290, 330, 120, 25);
		contentPane.add(calculTarif);
		
		Reset = new JButton("Réinitialiser");
		Reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saisieDepartement.setText(null);
				saisieDepartement.setEditable(true);
				searchDepartement.setText("Rechercher");
				
				rbSimple.setSelected(false);
				rbRetour.setSelected(false);
				rbSemaine.setSelected(false);
				rbWeekend.setSelected(false);
				rbJour.setSelected(false);
				rbNuit.setSelected(false);
				saisieDuree.setText(null);
				saisieKilometre.setText(null);
				
				rbSimple.setEnabled(false);
				rbRetour.setEnabled(false);
				rbSemaine.setEnabled(false);
				rbWeekend.setEnabled(false);
				rbJour.setEnabled(false);
				rbNuit.setEnabled(false);
				saisieDuree.setEnabled(false);
				saisieKilometre.setEnabled(false);
				calculTarif.setEnabled(false);
				
				labelPrix.setVisible(false);
				prix.setVisible(false);
				
				saisieDepartement.requestFocus();
			}
		});
		Reset.setBounds(30, 330, 120, 25);
		contentPane.add(Reset);
	}
}
