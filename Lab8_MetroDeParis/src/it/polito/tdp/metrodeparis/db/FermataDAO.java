package it.polito.tdp.metrodeparis.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import it.polito.tdp.metrodeparis.model.Fermata;

public class FermataDAO {

	Connection con = DBConnect.getConnection();
	
	public ArrayList<Fermata> trovaArrivi(Fermata f, int id){
		ArrayList<Fermata> vicini = new ArrayList<Fermata>();
		try {
			String sql = "select id_fermata,nome,coordX,coordY from connessione,fermata where connessione.id_stazA=fermata.id_fermata and id_stazP='"+f.getIdF()+"' and connessione.id_linea='"+id+"'";
			Statement st = con.prepareStatement(sql);
	         ResultSet res= st.executeQuery(sql);
	         while(res.next()){
	        	 Fermata f1 = new Fermata(res.getInt("id_fermata"),res.getString("nome"),res.getDouble("coordX"),res.getDouble("coordY"));
	        	// if(!l.fermataPresente(f))
	        	 vicini.add(f1);
	         }
	         
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vicini;
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
