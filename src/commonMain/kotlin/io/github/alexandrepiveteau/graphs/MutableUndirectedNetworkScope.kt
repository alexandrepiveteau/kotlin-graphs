package io.github.alexandrepiveteau.graphs

/** A [MutableNetworkScope] for [UndirectedNetwork]s. */
public interface MutableUndirectedNetworkScope : MutableUndirectedGraphScope, MutableNetworkScope {

  /**
   * Adds a new weighted edge to the graph.
   *
   * @param edge the edge to add.
   * @param weight the weight of the edge.
   */
  public fun addEdge(edge: Edge, weight: Int = 0)

  override fun addEdge(edge: Edge): Unit = addEdge(edge, 0)
}
