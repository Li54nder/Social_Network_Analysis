package project_GraphAnalysis;


import java.util.HashMap;
import java.util.Map;

import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.commons.math3.stat.correlation.SpearmansCorrelation;

import edu.uci.ics.jung.algorithms.scoring.BetweennessCentrality;
import edu.uci.ics.jung.algorithms.scoring.ClosenessCentrality;
import edu.uci.ics.jung.algorithms.scoring.EigenvectorCentrality;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.Pair;

public class Metrics<V, E> {
	
	private UndirectedSparseGraph<V, E> g;
	
	private PearsonsCorrelation pearsons;
	private SpearmansCorrelation spearmans;
	
	private BetweennessCentrality<V, E> bc;
	private ClosenessCentrality<V, E> cc;
	private EigenvectorCentrality<V, E> ec;
	
	private Map<V, Package> metricsData = new HashMap<>();
	
	public Map<V, Package> getMetricsData() {
		return metricsData;
	}
	public BetweennessCentrality<V, E> getBetweennessCentrality() {
		return bc;
	}
	
	private double[] arr1;;
	private double[] arr2;
	private int counter;
	
	public class Package {
		double bc = 0, cc = 0, ec = 0, dc = 0;
		public void setBc(double bc) {
			this.bc = bc;
		}
		public void setCc(double cc) {
			this.cc = cc;
		}
		public void setEc(double ec) {
			this.ec = ec;
		}
		public void setDc(double dc) {
			this.dc = dc;
		}
	}

	
	public enum Type {
		DEGREE, BETWEENNESS, CLOSENESS, EIGENVECTOR;
	}
	
	
	public Metrics(UndirectedSparseGraph<V, E> g) {
		this.g = g;
	}
	
	
	public void calculateCentrality(Type type) {
		arr1 = new double[g.getEdgeCount() * 2];
		arr2 = new double[g.getEdgeCount() * 2];
		counter = 0;
		calculateMetric(type);
	}
	
	private void calculateMetric(Type type) {
		switch (type) {
		case DEGREE:
			System.out.println("Calcunating degree centrality...");
			fillPackages(Type.DEGREE);
			break;
		case BETWEENNESS:
			System.out.println("Calcunating betweenness centrality...");
			bc = new BetweennessCentrality<V, E>(g);
			fillPackages(Type.BETWEENNESS);
			break;
		case CLOSENESS:
			System.out.println("Calcunating closeness centrality...");
			cc = new ClosenessCentrality<V, E>(g);
			fillPackages(Type.CLOSENESS);
			break;
		case EIGENVECTOR:
			System.out.println("Calcunating eigenvector centrality...");
			ec = new EigenvectorCentrality<V, E>(g);
			ec.evaluate();
			fillPackages(Type.EIGENVECTOR);
			break;
		default:
			break;
		}
	}

	private void fillPackages(Type type) {
		for(E edge : g.getEdges()) {
			Pair<V> pair = g.getEndpoints(edge);
			V v1 = pair.getFirst();
			V v2 = pair.getSecond();
			Package p1;
			if(metricsData.containsKey(v1)) {
				p1 = metricsData.get(v1);
			} else {
				p1 = new Package();
				metricsData.put(v1, p1);
			}
			Package p2;
			if(metricsData.containsKey(v2)) {
				p2 = metricsData.get(v2);
			} else {
				p2 = new Package();
				metricsData.put(v2, p2);
			}
			double metric1 = 0, metric2 = 0;
			switch (type) {
			case BETWEENNESS:
				metric1 = bc.getVertexScore(v1);
				metric2 = bc.getVertexScore(v2);
				p1.bc = metric1;
				p2.bc = metric2;
				break;
			case CLOSENESS:
				metric1 = cc.getVertexScore(v1);
				metric2 = cc.getVertexScore(v2);
				p1.cc = metric1;
				p2.cc = metric2;
				break;
			case DEGREE:
				metric1 = g.degree(v1);
				metric2 = g.degree(v2);
				p1.dc = metric1;
				p2.dc = metric2;
				break;
			case EIGENVECTOR:
				metric1 = ec.getVertexScore(v1);
				metric2 = ec.getVertexScore(v2);
				p1.ec = metric1;
				p2.ec = metric2;
				break;
			}
			arr1[counter] = metric1;
			arr2[counter++] = metric2;
			arr1[counter] = metric2;
			arr2[counter++] = metric1;
		}
	}
	
	public double getPearsons() {
		return getPearsonsTMP();
	}
	private double getPearsonsTMP() {
		pearsons = new PearsonsCorrelation();
		return pearsons.correlation(arr1, arr2);
	}

	public double getSpearmans() {
		return getSpearmansTMP();
	}
	private double getSpearmansTMP() {
		spearmans = new SpearmansCorrelation();
		return spearmans.correlation(arr1, arr2);
	}
}
