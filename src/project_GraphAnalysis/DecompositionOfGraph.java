package project_GraphAnalysis;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;

public class DecompositionOfGraph<V, E> {
	
	UndirectedSparseGraph<V, E> g;
	
	public DecompositionOfGraph(UndirectedSparseGraph<V, E> g) {
		this.g = g;
	}

	public void startDecomposition(Map<V, Metrics<V, E>.Package> map) {
		startDecompositionTMP(map);
	}
	private void startDecompositionTMP(Map<V, Metrics<V, E>.Package> map) {
		
		List<V> listD = map.entrySet().stream().sorted(Comparator.comparing(x -> -x.getValue().dc)).map(x -> x.getKey()).collect(Collectors.toList());
		List<V> listB = map.entrySet().stream().sorted(Comparator.comparing(x -> -x.getValue().bc)).map(x -> x.getKey()).collect(Collectors.toList());
		List<V> listC = map.entrySet().stream().sorted(Comparator.comparing(x -> -x.getValue().cc)).map(x -> x.getKey()).collect(Collectors.toList());
		List<V> listE = map.entrySet().stream().sorted(Comparator.comparing(x -> -x.getValue().ec)).map(x -> x.getKey()).collect(Collectors.toList());
						
		UndirectedSparseGraph<V, E> copy = LoadGraph.getCopyOfGraph(g);
		Map<Integer, Double> map1 = decomposition(copy, listD);
		copy = LoadGraph.getCopyOfGraph(g);
		Map<Integer, Double> map2 = decomposition(copy, listB);
		copy = LoadGraph.getCopyOfGraph(g);
		Map<Integer, Double> map3 = decomposition(copy, listC);
		copy = LoadGraph.getCopyOfGraph(g);
		Map<Integer, Double> map4 = decomposition(copy, listE);
		
		Map<Integer, List<Double>> bigMap = new TreeMap<>();
		for(int i = 1; i < g.getVertexCount(); i++) {
			bigMap.put(i, new ArrayList<>(Arrays.asList(map1.get(i), map2.get(i), map3.get(i), map4.get(i))));
		}	
		printDataInFile(bigMap);

	}
	
	private Map<Integer, Double> decomposition(UndirectedSparseGraph<V, E> g, List<V> l) {
		Map<Integer, Double> tmp = new TreeMap<Integer, Double>();
		
		int br = 1;
		for(V v : l) {
			g.removeVertex(v);

			if(g.getVertexCount() > 0) {
				double psg = getPersentage(g);
				tmp.put(br++, psg);
			}
		}
		
		return tmp;
	}

	private double getPersentage(UndirectedSparseGraph<V, E> g) {
		if(g == null) return 0;
		if(g.getVertexCount() == 0) return -1;
		DetectComponentsDFS.setGraph(g);
		double persentage = 100.0 * DetectComponentsDFS.getGiantComponent().size() / g.getVertexCount();
		return (double) Math.round(persentage * 100) / 100;
	}
	
	private void printDataInFile(Map<Integer, List<Double>> bigMap) {
		String fileType = ".csv";
	    File file = new File("decompositionData"+fileType);
	    PrintWriter printWriter = null;
	    try {
	        FileWriter writer = new FileWriter(file);
	        printWriter = new PrintWriter(writer);	
		    System.out.println("Printing...");
			printWriter.print("F, SD, SB, SC, SE\n");
			for(Entry<Integer, List<Double>> m : bigMap.entrySet()) {
				StringBuilder s = new StringBuilder();
				s.append(m.getKey());
				s.append(", ");
				s.append(m.getValue().get(0));
				s.append(", ");
				s.append(m.getValue().get(1));
				s.append(", ");
				s.append(m.getValue().get(2));
				s.append(", ");
				s.append(m.getValue().get(3));
				s.append("\n");
				printWriter.print(s);	
			}
			printWriter.flush();
	        printWriter.close();
	        System.out.println("Created file: "+file.getName());
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally{
	        if(printWriter!=null){
	            printWriter.flush();
	            printWriter.close();
	        }
	    }
	}
}
