package io.github.alexandrepiveteau.graphs

/** A [MutableGraphScope] for [UndirectedGraph]s. */
public interface MutableUndirectedGraphScope : MutableGraphScope {

  /**
   * Adds a new edge to the graph.
   *
   * @param edge the edge to add.
   */
  public fun addEdge(edge: Edge)
}
