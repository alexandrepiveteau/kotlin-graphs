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

/**
 * Builds a [UndirectedGraph] using the given [scope].
 *
 * @param scope the scope in which the graph is built.
 * @return the newly built graph.
 */
public inline fun buildUndirectedGraph(scope: UndirectedGraphBuilder.() -> Unit): UndirectedGraph {
  val data =
      buildGraphData<UndirectedGraphBuilder>(scope) { adapter, builder ->
        object : UndirectedGraphBuilder, GraphBuilder by builder {
          override fun addEdge(edge: Edge) = adapter.addLink(edge.component1(), edge.component2())
        }
      }
  // TODO : Have a true / false parameter to choose if we want to use an adjacency matrix or not.
  // 1. Store the neighbors of each vertex in a vector.
  val neighbors = Array(data.size) { IntVector() }
  data.forEachLink { u, v ->
    neighbors[u] += v
    neighbors[v] += u
  }
  // 2. Store the neighbors of each vertex in an array.
  val arrays =
      Array(neighbors.size) {
        var last = -1 // This is an invalid vertex.
        var count = 0
        val list = neighbors[it].toIntArray().apply { sort() }
        for (j in list.indices) {
          if (list[j] != last) {
            list[count++] = list[j] // Update the array in place.
            last = list[j]
          }
        }
        list.copyOf(count).toVertexArray() // Compact the array.
      }
  return AdjacencyListUndirectedGraph(arrays)
}
