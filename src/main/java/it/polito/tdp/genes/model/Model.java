package it.polito.tdp.genes.model;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.genes.db.GenesDao;

public class Model {

	private GenesDao dao;
	private Graph<Integer, DefaultWeightedEdge> grafo;
	
	
	public Model() {
		dao = new GenesDao();
		
	}
	
	public String creaGrafo() {
		grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(this.grafo, this.dao.getVertici());
		
		for(Coppia c : this.dao.getArchi()) {
			Graphs.addEdge(this.grafo, c.getC1(), c.getC2(), c.getPeso());
		}
		
		return "Il grafo ottenuto ha "+this.grafo.vertexSet().size()+" vertici e "
				+this.grafo.edgeSet().size()+" archi";
	}
	
	public double getPesoMIn() {
		double min =99999;
		
			for(Coppia e : dao.getArchi()) {
				if(e.getPeso()<min)
					min = e.getPeso();
			
		}
		return min;
	}
	public double getPesoMax() {
		double max =-9999;
		
			for(Coppia c : dao.getArchi()) {
				if(c.getPeso()>max)
					max = c.getPeso();
			
		}
		return max;
	}
	
	
	public String getContaArchi(Double s) {
		
		String f = "Archi prima e archi dopo";
		int prima = 0;
		int dopo = 0;
		
			for(Coppia c : this.dao.getArchi()) {
				if( c.getPeso()<=s) {
					prima ++;
				}
				if(c.getPeso()>s )
					dopo ++;
			}
		return f +":\n prima = "+prima +" dopo = "+dopo;
	}
}