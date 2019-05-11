package project_GraphAnalysis;

import java.time.Duration;
import java.time.Instant;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import project_GraphAnalysis.Metrics.Type;

public class MainTestClass {
	
	private static UndirectedSparseGraph<Integer, String> graph;
	private static Metrics<Integer, String> m;
	
	public static void main(String[] args) {

		Instant bigStart = Instant.now();
		
		getReadyForFunctionality();
		
		firstFunctionality();

		secondFunctionality();
		
		thirdFunctionality();
		
		Instant bigEnd = Instant.now();
		
		executionsDate(bigStart, bigEnd);
	}

	private static void getReadyForFunctionality() {
		System.out.println("Geting ready for functionalities...\n");
		graph = LoadGraph.getGraph();
		DetectComponentsDFS.setGraph(graph);
		DetectComponentsDFS.getSizeOfGiantComp();
		System.out.println("Total number of vertices: " + graph.getVertexCount());
		System.out.println("Total number of edges : " + graph.getEdgeCount());
		LoadGraph.getModifiedGraph(graph, DetectComponentsDFS.getGiantComponent());
		//graph is giant component now...
		System.out.printf("The giant component contains %d vertices.\n", graph.getVertexCount());
	}

	private static void firstFunctionality() {
		System.out.println("\n1. functionality:\n");
		Instant timeStart = Instant.now();
		
		m = new Metrics<>(graph);
		m.calculateCentrality(Type.DEGREE);
		System.out.printf("Pearson's coefficient: %.3f\n", m.getPearsons());
		System.out.printf("Spearman's coefficient: %.3f\n\n", m.getSpearmans());
		m.calculateCentrality(Type.BETWEENNESS);
		System.out.printf("Pearson's coefficient: %.3f\n", m.getPearsons());
		System.out.printf("Spearman's coefficient: %.3f\n\n", m.getSpearmans());
		m.calculateCentrality(Type.CLOSENESS);
		System.out.printf("Pearson's coefficient: %.3f\n", m.getPearsons());
		System.out.printf("Spearman's coefficient: %.3f\n\n", m.getSpearmans());
		m.calculateCentrality(Type.EIGENVECTOR);
		System.out.printf("Pearson's coefficient: %.3f\n", m.getPearsons());
		System.out.printf("Spearman's coefficient: %.3f\n\n", m.getSpearmans());
		Instant timeEnd = Instant.now();
		
		Duration duration = Duration.between(timeStart, timeEnd);
		System.out.printf("Calculating metrics took %d seconds (~%dmin)\n", duration.toMillis()/1000, duration.toMinutes());
	}

	private static void secondFunctionality() {
		System.out.println("\n2. functionality:\n");
		DecompositionOfGraph<Integer, String> decomposition = new DecompositionOfGraph<Integer, String>(graph);
		decomposition.startDecomposition(m.getMetricsData());
	}

	private static void thirdFunctionality() {
		System.out.println("\n3. functionality:\n");
		TenPercent<Integer, String> percentage = new TenPercent<>(graph, m);
		percentage.calculatePercentage();
	}

	private static void executionsDate(Instant bigStart, Instant bigEnd) {
		Duration duration = Duration.between(bigStart, bigEnd);
		System.out.printf("The program took %d seconds (~%dmin) to execute\n", duration.toMillis()/1000, duration.toMinutes());
	}
}
