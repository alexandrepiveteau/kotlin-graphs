package io.github.alexandrepiveteau.graphs.builder

import io.github.alexandrepiveteau.graphs.Edge
import io.github.alexandrepiveteau.graphs.UndirectedNetwork

/** A [NetworkBuilderScope] for [UndirectedNetwork]s. */
public interface UndirectedNetworkBuilderScope : UndirectedGraphBuilderScope, NetworkBuilderScope {

  /**
   * Adds a new weighted edge to the graph.
   *
   * @param edge the edge to add.
   * @param weight the weight of the edge.
   */
  public fun addEdge(edge: Edge, weight: Int = 0)

  override fun addEdge(edge: Edge): Unit = addEdge(edge, 0)
}
