package io.github.alexandrepiveteau.graphs

import io.github.alexandrepiveteau.graphs.internal.IntVector

/**
 * A [UndirectedNetworkBuilder] is a [UndirectedGraphBuilder] which can also add edges with a
 * weight.
 */
public interface UndirectedNetworkBuilder : NetworkBuilder, UndirectedGraphBuilder {

  /**
   * Adds a new weighted edge to the graph.
   *
   * @param edge the edge to add.
   * @param weight the weight of the edge.
   */
  public fun addEdge(edge: Edge, weight: Int = 0)

  override fun addEdge(edge: Edge): Unit = addEdge(edge, 0)
}

/** A [MutableListNetworkBuilder] for [UndirectedNetworkBuilder]. */
@PublishedApi
internal open class MutableListUndirectedNetworkBuilder(
    private val neighbors: MutableList<IntVector>,
    private val weights: MutableList<IntVector>,
) : MutableListNetworkBuilder(neighbors, weights), UndirectedNetworkBuilder {
  override fun addEdge(edge: Edge, weight: Int) {
    val (u, v) = edge
    checkLink(u, v)
    neighbors[u.index] += v.index
    neighbors[v.index] += u.index
    weights[u.index] += weight
    weights[v.index] += weight
  }
}

/**
 * Builds a [UndirectedNetwork] using the given [scope].
 *
 * @param scope the scope in which the graph is built.
 * @return the newly built graph.
 */
public inline fun buildUndirectedNetwork(
    scope: UndirectedNetworkBuilder.() -> Unit
): UndirectedNetwork {
  // TODO : Have a true / false parameter to choose if we want to use an adjacency matrix or not.
  // 1. Store the neighbors of each vertex in a vector.
  val neighbors = mutableListOf<IntVector>()
  val weights = mutableListOf<IntVector>()
  MutableListUndirectedNetworkBuilder(neighbors, weights).apply(scope)
  // 2. Store the neighbors of each vertex in an array.
  val (n, w) = compactToVertexAndWeightsArray(neighbors, weights)
  return AdjacencyListUndirectedNetwork(n, w)
}
