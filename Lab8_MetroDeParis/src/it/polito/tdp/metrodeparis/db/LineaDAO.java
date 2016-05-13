package it.polito.tdp.metrodeparis.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import it.polito.tdp.metrodeparis.model.Fermata;
import it.polito.tdp.metrodeparis.model.Linea;

public class LineaDAO {
	
	Connection con = DBConnect.getConnection();
	ArrayList<Linea> linee = new ArrayList<Linea>();
	
	public ArrayList<Linea> creaLinee(){
		
		
		try {
			Statement st = con.createStatement();
			String sql = "select * from linea ";
	         ResultSet res= st.executeQuery(sql);
	         while(res.next()){
	        	 Linea l = new Linea(res.getInt("id_linea"),res.getString("nome"),
	        			 res.getInt("velocita"),res.getDouble("intervallo"),res.getString("colore"));
	        	 linee.add(l);
	         }
	         
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return linee;
	}

	public void riempiLinee(Linea l)
	{
		try {
			String sql = "select id_fermata, nome, coordX, coordY from fermata, connessione where (fermata.id_fermata=connessione.id_stazP"
					+ " or fermata.id_fermata=connessione.id_stazA) and connessione.id_linea='"+l.getId()+"'";
			Statement st = con.prepareStatement(sql);
	         ResultSet res= st.executeQuery(sql);
	         while(res.next()){
	        	 Fermata f = new Fermata(res.getInt("id_fermata"),res.getString("nome"),res.getDouble("coordX"),res.getDouble("coordY"));
	        	 if(!l.fermataPresente(f))
	        	 l.addFermata(f);
	         }
	         
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void close(){
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
