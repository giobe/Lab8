package it.polito.tdp.metrodeparis.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.metrodeparis.db.FermataDAO;
import it.polito.tdp.metrodeparis.db.LineaDAO;

public class GestioneMetro {
	
	protected DefaultDirectedWeightedGraph<Fermata, DefaultWeightedEdge> metroGraph = new DefaultDirectedWeightedGraph<Fermata, DefaultWeightedEdge>(DefaultWeightedEdge.class);
	
	ArrayList<Linea> linee = new ArrayList<Linea>();
	ArrayList<Fermata> fermate = new ArrayList<Fermata>();
	
	List<DefaultWeightedEdge> tappe;
	double tot;
	
	public void caricaLinee(){
		
		
		LineaDAO dao = new LineaDAO();
		linee.addAll(dao.creaLinee());
		for(Linea l : linee){
			System.out.println(l.getId());
			dao.riempiLinee(l);
			//dao.close();
		}
		//dao.linee.carcatutte; che return una list di linne vuote
		// dao.linee.cercafermate che ritorna lista ferate per ogni linea
		// 
		
		// creo il grafo
		
		//return la list di fermate
		
		System.out.println(linee.get(33).toString()+"\n");
		dao.close();
	}
	
	public void RiempiGrafo(){
		FermataDAO dao = new FermataDAO();
		ArrayList<Fermata> temp; 
		int count=0;
		
		
		for(Linea l : linee){
			for(Fermata f : l.fermate){
				f.setLineaId(l.getId());
				fermate.add(f);
				
				if(metroGraph.addVertex(f)==true){
				
				temp = dao.trovaArrivi(f,l.id);
				
				for(int i=0;i<temp.size();i++){
					temp.get(i).setLineaId(l.getId());
					metroGraph.addVertex(temp.get(i));
					//if(metroGraph.degreeOf(f)<2 || metroGraph.degreeOf(temp.get(i))<2){
						double a =LatLngTool.distance(new LatLng(temp.get(i).getCoordX(), temp.get(i).coordY), new LatLng(f.getCoordX(), f.getCoordY()), LengthUnit.KILOMETER);
						double vel = l.getVelocita();
						double tempo = (a/vel)*60*60;

						 DefaultWeightedEdge e = metroGraph.addEdge(f, temp.get(i)); 
						 if(e!=null){
			               metroGraph.setEdgeWeight(e, tempo); 
			               Graphs.addEdge(metroGraph, temp.get(i), f, tempo);
			               System.out.println("inserito arco "+temp.get(i).toString()+" "+f.toString());
			              count++;
						 }
			              //System.out.println(count);
					//}
					}
					temp.clear();
				
				}
			}
		}
		dao.close();
		
		//System.out.println(count);
		
	}
	
	public void aggiungiUltimiArchi(){
		
		for(Fermata f : fermate){
			this.cercaDoppi(f, fermate);
		}
	}
	
		public void cercaDoppi(Fermata f, ArrayList<Fermata> ferm){
		
		Linea l1=null;
		Linea l2=null;
			for(Fermata f1 : ferm){
				if(f1.getNome().compareTo(f.getNome())==0 && f1.getLineaId()!=f.getLineaId()){
					for(Linea l : linee){
						if(l.getId()==f.lineaId)
							l1=l;
						if(l.getId()==f1.getLineaId())
							l2=l;
					}
					DefaultWeightedEdge e =metroGraph.addEdge(f, f1);
					if(e!=null){
					metroGraph.setEdgeWeight(e, l1.getIntervallo());
					Graphs.addEdge(metroGraph, f1, f, l2.getIntervallo());
					}
				}
				 
			}
		
		
	}
	
	public ArrayList<String> ListaNomi(){
		ArrayList<String> nomi = new ArrayList<String>();
		for(Linea l : linee){
			for(Fermata f : l.fermate){
				if(!nomi.contains(f.getNome())){
					nomi.add(f.getNome());
					
					
				}
			}
		}
		System.out.println("dimensioni "+nomi.size());
		Collections.sort(nomi);
		return nomi;
	}
	
	public Fermata trovaFermata(String nome){
		for(Fermata f : fermate){
			if(f.getNome().compareTo(nome)==0)
				return f;
		}
		return null;
	}
	
	public List<DefaultWeightedEdge> percorso(Fermata f, Fermata f1){
		
		
		DijkstraShortestPath<Fermata, DefaultWeightedEdge> dijkstra = new DijkstraShortestPath<Fermata, DefaultWeightedEdge>(metroGraph, f, f1);
		
		tappe = dijkstra.getPathEdgeList();
		tot = dijkstra.getPathLength();
		
		return tappe;
	}
	
	public String percorsoIllustrato(){
		String result="";
		if(tappe==null)
			result="nessun percorso";
		else{
			int a = metroGraph.getEdgeTarget(tappe.get(0)).getLineaId();
			result ="percorso parte su linea "+a+"  ";
		for(DefaultWeightedEdge e : tappe){
			String staz = metroGraph.getEdgeTarget(e).getNome();
			if(result.contains(staz)==false)
			result+=metroGraph.getEdgeTarget(e).getNome()+"   ";
			else
				result+="\n cmabio linea passo alla "+metroGraph.getEdgeTarget(e).lineaId+" "+staz+"   ";
		}
		}
		return result;
	}
	
	public String tempoTotale(){
		String a="";
		int min=(int) (tot/60);
		if(min>0){
			int resto1= (int) (tot - min*60);
			int ore = (int) (min/60);
			if(ore>0){
				int resto= min-ore*60;
				return a=" "+ore+":"+resto+":"+resto1;
			}
			return a="00:"+min+":"+resto1;
		}
		
		return ""+tot+" secondi";
	}
	
	
	
	public void lineaX(){
	
		Linea l = linee.get(33);
		FermataDAO dao = new FermataDAO();
		ArrayList<Fermata> temp; 
		int count=0;
		
		for(Fermata f : l.fermate){
			f.setLineaId(l.getId());
			fermate.add(f);
			
			if(metroGraph.addVertex(f)==true){
			
			temp = dao.trovaArrivi(f,l.id);
			
			for(int i=0;i<temp.size();i++){
				temp.get(i).setLineaId(l.getId());
				metroGraph.addVertex(temp.get(i));
				//if(metroGraph.degreeOf(f)<2 || metroGraph.degreeOf(temp.get(i))<2){
					double a =LatLngTool.distance(new LatLng(temp.get(i).getCoordX(), temp.get(i).coordY), new LatLng(f.getCoordX(), f.getCoordY()), LengthUnit.KILOMETER);

					 DefaultWeightedEdge e = metroGraph.addEdge(f, temp.get(i)); 
					 if(e!=null){
		               metroGraph.setEdgeWeight(e, a); 
		               Graphs.addEdge(metroGraph, temp.get(i), f, a);
		               System.out.println("inserito arco "+temp.get(i).toString()+" "+f.toString());
		              count++;
		              //System.out.println(count);
				//}
					 }
				}
				temp.clear();
			
			}
			
		}
		dao.close();
	}
	
	

	public static void main (String [] args){
		GestioneMetro g = new GestioneMetro();
		g.caricaLinee();
		g.RiempiGrafo();
		g.aggiungiUltimiArchi();
		//g.inserisciArchi();
		//g.lineaX();
	}
}
