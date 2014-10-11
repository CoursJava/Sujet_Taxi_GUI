package Sujet_Taxi_GUI;

import java.util.Scanner; //Bibliothèque permettant de lire la saisie de l'utilisateur

public class Saisies {
	//Reader
	private Scanner reader = new Scanner(System.in);
	
	//Attributs
	private int dept, km, duree;
	private char typeTrajet, heure, jour;

	// Accesseurs
	/**
	 * Accesseur du département saisie
	 * 
	 * @return Numéro du département (int)
	 */
	public int getDepartement() {
		return dept;
	}
	public void setDepartement(int departement) {
		this.dept = departement;
	}
	
	/**
	 * Accesseur du type de trajet
	 * 
	 * @return Type de trajet (char)
	 */
	public char getTypeTrajet() {
		return typeTrajet;
	}
	public void setTypeTrajet(char typeTrajet) {
		this.typeTrajet = typeTrajet;
	}
	
	/**
	 * Accesseur du jour
	 * 
	 * @return Jour (char)
	 */
	public char getJour() {
		return jour;
	}
	public void setJour(char jour) {
		this.jour = jour;
	}
	
	/**
	 * Accesseur de l'heure
	 * 
	 * @return Heure (char)
	 */
	public char getHeure() {
		return heure;
	}
	public void setHeure(char heure) {
		this.heure = heure;
	}
	
	/**
	 * Accesseur des kilomètres
	 * 
	 * @return Km (int)
	 */
	public int getKm() {
		return km;
	}
	public void setKm(int km) {
		this.km = km;
	}
	
	/**
	 * Accesseur de la durée
	 * 
	 * @return Duree (int)
	 */
	public int getDuree() {
		return duree;
	}
	public void setDuree(int duree) {
		this.duree = duree;
	}
	
	//Méthodes
	/**
	 * Saisie du departement
	 */
	public void SaisieDepartement() {
		do {
			System.out.print("Veuillez saisir le departement : ");
			dept = reader.nextInt();
			if (dept <= 0 || dept > 95)
				System.out.print("Le departement saisi est incorrect\n");
		} while (dept <= 0 || dept > 95); // Tant que le departement est pas compris entre 0 et 95
	}

	/**
	 * Saisie du type de trajet (Aller simple ou aller/retour)
	 */
	public void SaisieTypeTrajet() {
		do {
			System.out.print("Veuillez saisir le type de trajet (S: Aller simple / R: Aller-Retour) : ");
			typeTrajet = reader.next().toLowerCase().charAt(0);
			if (typeTrajet != 's' && typeTrajet != 'r')
				System.out.print("Le type de trajet saisi est incorrect\n");
		} while (typeTrajet != 's' && typeTrajet != 'r'); // Tant que le type de trajet n'est pas S ou R
	}

	/**
	 * Saisie du jour de la semaine (Semaine ou week-end)
	 */
	public void SaisieJour() {
		do {
			System.out.print("Veuillez saisir le jour du trajet (S: Semaine / W: Week-end) : ");
			jour = reader.next().toLowerCase().charAt(0);
			if (jour != 's' && jour != 'w')
				System.out.print("Le jour saisi est incorrect\n");
		} while (jour != 's' && jour != 'w'); // Tant que le jour n'est pas S ou
												// W
	}

	/**
	 * Saisie du moment de la journée (Jour ou nuit)
	 */
	public void SaisieHeure() {
		do {
			System.out.print("Veuillez saisir l'heure du trajet (J: Avant 20h / N: Après 20h) : ");
			heure = reader.next().toLowerCase().charAt(0);
			if (heure != 'j' && heure != 'n')
				System.out.print("L'heure saisi est incorrect\n");
		} while (heure != 'j' && heure != 'n'); // Tant que l'heure n'est pas J
												// ou N
	}

	/**
	 * Saisie du nombre de kilomètres
	 */
	public void SaisieKm() {
		do {
			System.out.print("Veuillez saisir le nombre de kilomètres : ");
			km = reader.nextInt();
			if (km < 0)
				System.out.print("Le nombre de kilomètres saisi est incorrect\n");
		} while (km < 0); // Tant que le nombre de kilomètres est inferieur à 0
	}
	
	/**
	 * Saisie de la durée
	 */
	public void SaisieDuree() {
		do {
			System.out.print("Veuillez saisir la durée du trajet (en minutes) : ");
			duree = reader.nextInt();
			if (duree < 0)
				System.out.print("La durée saisie est incorrect\n");
		} while (duree < 0); // Tant que la durée est inferieur à 0
	}
	
	/**
	 * Saisie de toutes les informations
	 */
	public void Saisie()
	{
		SaisieDepartement();
		SaisieTypeTrajet();
		SaisieJour();
		SaisieHeure();
		SaisieDuree();
		SaisieKm();
	}
}
