package io.github.alexandrepiveteau.graphs

/**
 * A [UndirectedNetworkBuilder] is a [UndirectedGraphBuilder] which can also add edges with a
 * weight.
 */
public interface UndirectedNetworkBuilder : UndirectedGraphBuilder {

  /**
   * Adds a new weighted edge to the graph.
   *
   * @param edge the edge to add.
   * @param weight the weight of the edge.
   */
  public fun addEdge(edge: Edge, weight: Int = 0)

  override fun addEdge(edge: Edge): Unit = addEdge(edge, 0)
}
