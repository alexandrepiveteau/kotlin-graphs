package io.github.alexandrepiveteau.graphs

import io.github.alexandrepiveteau.graphs.internal.IntVector

/** An [UndirectedGraphBuilder] is a [GraphBuilder] for [UndirectedGraph]s. */
public interface UndirectedGraphBuilder : GraphBuilder {

  /**
   * Adds a new edge to the graph.
   *
   * @param edge the edge to add.
   */
  public fun addEdge(edge: Edge)
}

/** A [MutableListGraphBuilder] for [UndirectedGraphBuilder]. */
@PublishedApi
internal open class MutableListUndirectedGraphBuilder(
    private val neighbors: MutableList<IntVector>
) : MutableListGraphBuilder(neighbors), UndirectedGraphBuilder {
  override fun addEdge(edge: Edge) {
    val (u, v) = edge
    checkLink(u, v)
    neighbors[u.index] += v.index
    neighbors[v.index] += u.index
  }
}

/**
 * Builds a [UndirectedGraph] using the given [scope].
 *
 * @param scope the scope in which the graph is built.
 * @return the newly built graph.
 */
public inline fun buildUndirectedGraph(scope: UndirectedGraphBuilder.() -> Unit): UndirectedGraph {
  // TODO : Have a true / false parameter to choose if we want to use an adjacency matrix or not.
  // 1. Store the neighbors of each vertex in a vector.
  val neighbors = mutableListOf<IntVector>()
  MutableListUndirectedGraphBuilder(neighbors).apply(scope)
  // 2. Store the neighbors of each vertex in an array.
  return AdjacencyListUndirectedGraph(compactToVertexArray(neighbors))
}
