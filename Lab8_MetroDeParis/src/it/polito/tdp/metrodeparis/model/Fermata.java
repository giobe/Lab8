package it.polito.tdp.metrodeparis.model;

import java.util.ArrayList;

public class Fermata {

	int idF;
	String nome;
	double coordX;
	int lineaId;
	double coordY;
	ArrayList<Linea> linee = new ArrayList<Linea>();
	public Fermata(int idF, String nome, double coordX, double coordY) {
		this.idF = idF;
		this.nome = nome;
		this.coordX = coordX;
		this.coordY = coordY;
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idF;
		result = prime * result + lineaId;
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Fermata other = (Fermata) obj;
		if (idF != other.idF)
			return false;
		if (lineaId != other.lineaId)
			return false;
		return true;
	}



	public int getIdF() {
		return idF;
	}
	public String getNome() {
		return nome;
	}
	public double getCoordX() {
		return coordX;
	}
	public double getCoordY() {
		return coordY;
	}
	public int getLineaId() {
		return lineaId;
	}
	public void setLineaId(int lineaId) {
		this.lineaId = lineaId;
	}
	public String toString(){
		return nome+"--"+lineaId+",  ";
	}
	
}
