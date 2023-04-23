package io.github.alexandrepiveteau.graphs

/**
 * An implementation of [Graph] which uses an adjacency matrix to store the edges.
 *
 * @param present the adjacency matrix of the graph.
 */
@PublishedApi
internal abstract class AdjacencyMatrixGraph(private val present: Array<BooleanArray>) : Graph {

  // TODO : Precompute the neighbors using AdjacencyListGraph ?

  override val size: Int
    get() = present.size

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
    return present[index].count { it }
  }

  override fun neighbor(index: Int, neighborIndex: Int): Vertex {
    if (index < 0 || index >= size) throw IndexOutOfBoundsException()
    val neighbors = present[index]
    var remaining = neighborIndex
    for (i in neighbors.indices) {
      if (neighbors[i]) {
        if (remaining == 0) return Vertex(i) else remaining--
      }
    }
    throw IndexOutOfBoundsException()
  }
}

/** An implementation of [DirectedGraph] which uses an adjacency matrix to store the edges. */
@PublishedApi
internal open class AdjacencyMatrixDirectedGraph(
    private val present: Array<BooleanArray>,
) : AdjacencyMatrixGraph(present), DirectedGraph {

  override fun contains(arc: Arc): Boolean {
    val (u, v) = arc
    return u in this && v in this && present[u.index][v.index]
  }
}

/** An implementation of [UndirectedGraph] which uses an adjacency matrix to store the edges. */
@PublishedApi
internal open class AdjacencyMatrixUndirectedGraph(
    private val present: Array<BooleanArray>,
) : AdjacencyMatrixGraph(present), UndirectedGraph {

  override fun contains(edge: Edge): Boolean {
    val (u, v) = edge
    return u in this && v in this && present[u.index][v.index]
  }
}
