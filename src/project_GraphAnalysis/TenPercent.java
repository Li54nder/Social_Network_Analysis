package project_GraphAnalysis;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import edu.uci.ics.jung.algorithms.scoring.BetweennessCentrality;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

public class TenPercent<V, E>{

	private UndirectedSparseGraph<V, E> graph;
	private Metrics<V, E> metrics;
	private BetweennessCentrality<V, E> bc;
	
	public TenPercent(UndirectedSparseGraph<V, E> g, Metrics<V, E> m) {
		graph = g;
		metrics = m;
	}
	
	public void calculatePercentage() {
		calculatePercentageTMP(graph);
	}
	private void calculatePercentageTMP(UndirectedSparseGraph<V, E> graph) {
		
		double limit = graph.getEdgeCount() * 0.9;
		
		bc = metrics.getBetweennessCentrality();
		
		Comparator<E> comp = new Comparator<E>() {
            public int compare(E e1, E e2) {
            	return bc.getEdgeScore(e1) - bc.getEdgeScore(e2) > 0? 1 : -1;
            }
        };
        Set<E> edges = new TreeSet<E>(comp);
        graph.getEdges().stream()
        		.forEach(x -> edges.add(x));
		int counter = 0;
		boolean ok = true;
		Iterator<E> it = edges.iterator();
		while(it.hasNext() && ok) {
			if(++counter > limit) {
				ok = false;						
			}
			graph.removeEdge(it.next());
			it.remove();
		}
		
		printAboutData();
		
	}

	private void printAboutData() {
		System.out.printf("In the graph %d edges survived\n", graph.getEdgeCount());
		
		DetectComponentsDFS.setGraph(graph);
		System.out.println("Number of components in 10% remaining graph: " + DetectComponentsDFS.getComponents().size());
		
		int counter = 0;
		for(Set<Object> v : DetectComponentsDFS.getComponents()) {
			if(v.size() == 1) counter++;
		}
		System.out.println("Number of isolated vertices in remaining graph: " + counter);
		
		System.out.printf("Size of the giant component is %d vertices now\n", DetectComponentsDFS.getGiantComponent().size());
		
		LoadGraph.getModifiedGraph(graph, DetectComponentsDFS.getGiantComponent());
		System.out.println("Number of edges in new giant component is " + graph.getEdgeCount());
	}
}
