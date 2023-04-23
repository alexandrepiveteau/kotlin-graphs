package io.github.alexandrepiveteau.graphs

import io.github.alexandrepiveteau.graphs.internal.IntVector

/** A [DirectedGraphBuilder] is a [GraphBuilder] for [DirectedGraph]s. */
public interface DirectedGraphBuilder : GraphBuilder {

  /**
   * Adds a new arc to the graph.
   *
   * @param arc the arc to add.
   */
  public fun addArc(arc: Arc)
}

/** A [MutableListGraphBuilder] for [DirectedGraphBuilder]. */
@PublishedApi
internal open class MutableListDirectedGraphBuilder(
    private val neighbors: MutableList<IntVector>,
) : MutableListGraphBuilder(neighbors), DirectedGraphBuilder {
  override fun addArc(arc: Arc) {
    val (u, v) = arc
    checkLink(u, v)
    neighbors[u.index] += v.index
  }
}

/**
 * Builds a [DirectedGraph] using the given [scope].
 *
 * @param scope the scope in which the graph is built.
 * @return the newly built graph.
 */
public inline fun buildDirectedGraph(scope: DirectedGraphBuilder.() -> Unit): DirectedGraph {
  // TODO : Have a true / false parameter to choose if we want to use an adjacency matrix or not.
  // 1. Store the neighbors of each vertex in a vector.
  val neighbors = mutableListOf<IntVector>()
  MutableListDirectedGraphBuilder(neighbors).apply(scope)
  // 2. Store the neighbors of each vertex in an array.
  return AdjacencyListDirectedGraph(compactToVertexArray(neighbors))
}
