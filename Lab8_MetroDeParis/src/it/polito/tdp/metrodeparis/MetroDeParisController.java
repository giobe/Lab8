package it.polito.tdp.metrodeparis;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.metrodeparis.model.Fermata;
import it.polito.tdp.metrodeparis.model.GestioneMetro;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class MetroDeParisController {

	GestioneMetro model;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> cbPartenza;

    @FXML
    private Button btnCcerca;

    @FXML
    private ComboBox<String> cbArrivo;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCerca(ActionEvent event) {

    	String parto = cbPartenza.getValue();
    	String arrivo = cbArrivo.getValue();
    	Fermata part = model.trovaFermata(parto);
    	Fermata arr = model.trovaFermata(arrivo);
    	System.out.println(part.toString());
    	System.out.println(arr.toString());
    	List<DefaultWeightedEdge> tappe = model.percorso(part, arr);
    	String strada = model.percorsoIllustrato();
    	txtResult.setText(strada+"\n");
    	String a = "\n tempo totale previsto "+model.tempoTotale();
    	txtResult.appendText(a);
    	
    }
    
    void setModel(GestioneMetro model){
    	this.model=model;
    	model.caricaLinee();
    	model.RiempiGrafo();
    	model.aggiungiUltimiArchi();
    	cbPartenza.getItems().addAll(model.ListaNomi());
    	cbArrivo.getItems().addAll(model.ListaNomi());
    }

    @FXML
    void initialize() {
        assert cbPartenza != null : "fx:id=\"cbPartenza\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
        assert btnCcerca != null : "fx:id=\"btnCcerca\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
        assert cbArrivo != null : "fx:id=\"cbArrivo\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'MetroDeParis.fxml'.";

    }
}

