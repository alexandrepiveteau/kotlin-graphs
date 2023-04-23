package io.github.alexandrepiveteau.graphs

/**
 * An implementation of [Network] which uses an adjacency list to store the links and weights.
 *
 * @param neighbors the adjacency list of the graph.
 * @param weights the weights of the graph.
 */
@PublishedApi
internal class AdjacencyListNetwork(
    private val neighbors: Array<VertexArray>,
    private val weights: Array<IntArray>,
) : Network, Graph by AdjacencyListGraph(neighbors) {

  override fun weight(index: Int, neighborIndex: Int): Int {
    if (index < 0 || index >= size) throw IndexOutOfBoundsException()
    val weights = weights[index]
    if (neighborIndex < 0 || neighborIndex >= weights.size) throw IndexOutOfBoundsException()
    return weights[neighborIndex]
  }

  override fun weight(index: Int, neighbor: Vertex): Int {
    if (index < 0 || index >= size) throw IndexOutOfBoundsException()
    val neighbors = neighbors[index]
    val neighborIndex = neighbors.binarySearch(neighbor)
    if (neighborIndex < 0) throw NoSuchVertexException()
    return weights[index][neighborIndex]
  }
}

/** An implementation of [DirectedNetwork] which uses an adjacency list to store the links. */
@PublishedApi
internal class AdjacencyListDirectedNetwork(
    private val neighbors: Array<VertexArray>,
    weights: Array<IntArray>,
) : DirectedNetwork, Network by AdjacencyListNetwork(neighbors, weights) {

  override fun contains(arc: Arc): Boolean {
    val (u, v) = arc
    if (u !in this || v !in this) return false
    return neighbors[u.index].binarySearch(v) >= 0
  }
}

/** An implementation of [UndirectedNetwork] which uses an adjacency list to store the links. */
@PublishedApi
internal class AdjacencyListUndirectedNetwork(
    private val neighbors: Array<VertexArray>,
    weights: Array<IntArray>,
) : UndirectedNetwork, Network by AdjacencyListNetwork(neighbors, weights) {

  override fun contains(edge: Edge): Boolean {
    val (u, v) = edge
    if (u !in this || v !in this) return false
    return neighbors[u.index].binarySearch(v) >= 0 || neighbors[v.index].binarySearch(u) >= 0
  }
}
