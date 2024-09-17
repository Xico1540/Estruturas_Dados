package implementation_classes;

import exceptions.ElementNotFoundException;

public interface NetworkADT<T> extends GraphADT<T> {
    /**
     * Inserts an edge between two vertices of this graph.
     * @param firstVertex the first vertex.
     * @param secondVertex the second vertex.
     * @param weight the weight
     */
    public void addEdge(T firstVertex, T secondVertex, double weight);

    /**
     * Returns the weight of the shortest path in this network.
     * @param firstVertex the first vertex.
     * @param secondVertex the second vertex.
     * @return              the weight of the shortest path in this network.
     */
    public double shortestPathWeight(T firstVertex, T secondVertex) throws ElementNotFoundException;
}
