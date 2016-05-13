package it.polito.tdp.metrodeparis.model;

import java.util.ArrayList;

public class Linea {

	int id;
	String nome;
	double velocita;
	double intervallo;
	String colore;
	ArrayList<Fermata> fermate = new ArrayList<Fermata>();
	
	
	public Linea(int id, String nome, double velocita, double intervallo, String colore) {
		this.id = id;
		this.nome = nome;
		this.velocita = velocita;
		this.intervallo = intervallo;
		this.colore = colore;
	}
	
	public void addFermata(Fermata f){
		fermate.add(f);
	}

	public int getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public double getVelocita() {
		return velocita;
	}

	public double getIntervallo() {
		return intervallo;
	}

	public String getColore() {
		return colore;
	}
	
	public boolean fermataPresente(Fermata f){
		return fermate.contains(f);
	}
	public String toString(){
		String s="";
		for(Fermata f : fermate){
			s+=" "+f.toString();
			
		}
		return s;
	}
	
}
