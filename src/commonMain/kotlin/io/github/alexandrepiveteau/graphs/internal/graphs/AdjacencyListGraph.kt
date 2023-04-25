package io.github.alexandrepiveteau.graphs.internal.graphs

import io.github.alexandrepiveteau.graphs.*

/**
 * An implementation of [Graph] which uses an adjacency list to store the links.
 *
 * @param neighbors the adjacency list of the graph.
 */
internal class AdjacencyListGraph(private val neighbors: Array<VertexArray>) : Graph {

  override val size: Int
    get() = neighbors.size

  override fun get(vertex: Vertex): Int {
    if (vertex.index < 0 || vertex.index >= size) throw NoSuchVertexException()
    return vertex.index
  }

  override fun get(index: Int): Vertex {
    if (index < 0 || index >= size) throw IndexOutOfBoundsException()
    return Vertex(index)
  }

  override fun neighborsSize(index: Int): Int {
    if (index < 0 || index >= size) throw IndexOutOfBoundsException()
    return neighbors[index].size
  }

  override fun neighbor(index: Int, neighborIndex: Int): Vertex {
    if (index < 0 || index >= size) throw IndexOutOfBoundsException()
    val neighbors = neighbors[index]
    if (neighborIndex < 0 || neighborIndex >= neighbors.size) throw IndexOutOfBoundsException()
    return neighbors[neighborIndex]
  }
}

/** An implementation of [DirectedGraph] which uses an adjacency list to store the links. */
internal class AdjacencyListDirectedGraph(
    private val neighbors: Array<VertexArray>,
) : DirectedGraph, Graph by AdjacencyListGraph(neighbors) {

  override fun contains(arc: Arc): Boolean {
    val (u, v) = arc
    if (u !in this || v !in this) return false
    return neighbors[u.index].binarySearch(v) >= 0
  }
}

/** An implementation of [UndirectedGraph] which uses an adjacency list to store the links. */
internal class AdjacencyListUndirectedGraph(
    private val neighbors: Array<VertexArray>,
) : UndirectedGraph, Graph by AdjacencyListGraph(neighbors) {

  override fun contains(edge: Edge): Boolean {
    val (u, v) = edge
    if (u !in this || v !in this) return false
    return neighbors[u.index].binarySearch(v) >= 0 || neighbors[v.index].binarySearch(u) >= 0
  }
}
