# <p align="center"> :computer: Social Network Analysis :computer: </p>
<br>

## About the project

The project consists six classes, one of which is the "MainTestClass" class that initiates the loading of the network, the preparation of the same for calculating the metrics, all with the help of other classes from the same package, which are mainly generic type other than the class that loads a particular graph from file.
Loading and preparing are done using the classes "LoadGraph" and "DetectComponentsDFS".

### First functionality
The classes "PearsonsCorrelation" and "SpearmansCorrelation" from the "Apache Commons Math" library were used to calculate the correlation coefficient itself with the help of metrics that were calculated using the "Metrics" class.

### Second functionality

The analysis of the decomposition of the giant component was performed by the class "DecompositionOfGraph".
As a result, the program produces a table in the .CSV file that can be displayed on a chart below

<a href="https://datawrapper.dwcdn.net/pV1Hr/1/" target="_blank">Link to Chart...</a>
* The chart shows with four lines: "SD, SB, SC, SE" where SD is the percentage of nodes in the largest component by "degree" in the order of removal, SB by "betweenness", SC by "closeness", SE by "eigenvektor" centrality.

### Third functionality

From the giant component, 90% of the links with the lowest "betweenness" centrality were removed. This is done with the "TenPerscent" class and with the help of metrics expressed in the first functionality.
<br>

## Console output:
```
Geting ready for functionalities...

The giant component contains 79.32% vertices of the entire graph
Total number of vertices: 5242
Total number of edges : 14496
The giant component contains 4158 vertices.

1. functionality:

Calcunating degree centrality...
Pearson's coefficient: 0.639
Speraman's coefficient: 0.549

Calcunating betweenness centrality...
Pearson's coefficient: 0.109
Spearman's coefficient: 0.173

Calcunating closeness centrality...
Pearson's coefficient: 0.783
Spearman's coefficient: 0.770

Calcunating eigenvector centrality...
Pearson's coefficient: 0.603
Spearman's coefficient: 0.448

Calculating metrics took 326 seconds (~5min)

2. functionality:

Printing...
Created file: decompositionData.csv

3. functionality:

In the graph 1342 edges survived
Number of components in 10% remaining graph: 3305
Number of isolated vertices in remaining graph: 3296
Size of the giant component is 842 vertices now
Number of edges in new giant component is 1330
The program took 390 seconds (~6min) to execute
```
