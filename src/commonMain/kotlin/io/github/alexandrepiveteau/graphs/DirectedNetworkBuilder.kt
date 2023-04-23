package io.github.alexandrepiveteau.graphs

import io.github.alexandrepiveteau.graphs.internal.IntVector

/** A [DirectedNetworkBuilder] is a [DirectedGraphBuilder] which can also add arcs with a weight. */
public interface DirectedNetworkBuilder : NetworkBuilder, DirectedGraphBuilder {

  /**
   * Adds a new weighted arc to the graph.
   *
   * @param arc the arc to add.
   * @param weight the weight of the arc.
   */
  public fun addArc(arc: Arc, weight: Int = 0)

  override fun addArc(arc: Arc): Unit = addArc(arc, 0)
}

/** A [MutableListNetworkBuilder] for [DirectedNetworkBuilder]. */
@PublishedApi
internal open class MutableListDirectedNetworkBuilder(
    private val neighbors: MutableList<IntVector>,
    private val weights: MutableList<IntVector>,
) : MutableListNetworkBuilder(neighbors, weights), DirectedNetworkBuilder {
  override fun addArc(arc: Arc, weight: Int) {
    val (u, v) = arc
    checkLink(u, v)
    neighbors[u.index] += v.index
    weights[u.index] += weight
  }
}

/**
 * Builds a [DirectedNetwork] using the given [scope].
 *
 * @param scope the scope in which the graph is built.
 * @return the newly built graph.
 */
public inline fun buildDirectedNetwork(scope: DirectedNetworkBuilder.() -> Unit): DirectedNetwork {
  // TODO : Have a true / false parameter to choose if we want to use an adjacency matrix or not.
  // 1. Store the neighbors of each vertex in a vector.
  val neighbors = mutableListOf<IntVector>()
  val weights = mutableListOf<IntVector>()
  MutableListDirectedNetworkBuilder(neighbors, weights).apply(scope)
  // 2. Store the neighbors of each vertex in an array.
  val (n, w) = compactToVertexAndWeightsArray(neighbors, weights)
  return AdjacencyListDirectedNetwork(n, w)
}
