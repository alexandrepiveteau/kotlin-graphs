package io.github.alexandrepiveteau.graphs

/** An implementation of [UndirectedGraph] which is empty. */
private data object EmptyUndirectedGraph : UndirectedGraph {

  override val size = 0

  override fun contains(vertex: Vertex) = false

  override fun contains(edge: Edge) = false

  override fun index(vertex: Vertex) = throw NoSuchVertexException()

  override fun vertex(index: Int) = throw IndexOutOfBoundsException()

  override fun successorsSize(index: Int) = throw IndexOutOfBoundsException()

  override fun successorIndex(index: Int, neighborIndex: Int) = throw IndexOutOfBoundsException()

  override fun successorVertex(index: Int, neighborIndex: Int) = throw IndexOutOfBoundsException()
}

/** Returns an empty [UndirectedGraph], which is a graph with no vertices and no edges. */
public fun UndirectedGraph.Companion.empty(): UndirectedGraph = EmptyUndirectedGraph

/** An implementation of [UndirectedNetwork] which is empty. */
private data object EmptyUndirectedNetwork : UndirectedNetwork, UndirectedGraph by EmptyUndirectedGraph {

  override fun successorWeight(index: Int, neighborIndex: Int) = throw IndexOutOfBoundsException()

  override fun successorWeight(index: Int, neighbor: Vertex) = throw IndexOutOfBoundsException()
}

/** Returns an empty [UndirectedNetwork], which is a network with no vertices and no edges. */
public fun UndirectedNetwork.Companion.empty(): UndirectedNetwork = EmptyUndirectedNetwork

/** An implementation of a [UndirectedGraph] which is complete. */
private open class CompleteUndirectedGraph(override val size: Int) : UndirectedGraph {

  override fun contains(edge: Edge): Boolean {
    val (u, v) = edge
    return u != v && u in this && v in this
  }

  override fun index(vertex: Vertex): Int {
    if (vertex.index < 0 || vertex.index >= size) throw NoSuchVertexException()
    return vertex.index
  }

  override fun vertex(index: Int): Vertex {
    if (index < 0 || index >= size) throw IndexOutOfBoundsException()
    return Vertex(index)
  }

  override fun successorsSize(index: Int): Int {
    if (index < 0 || index >= size) throw IndexOutOfBoundsException()
    return size - 1
  }

  override fun successorIndex(index: Int, neighborIndex: Int): Int {
    if (index < 0 || index >= size) throw IndexOutOfBoundsException()
    if (neighborIndex < 0 || neighborIndex >= size - 1) throw IndexOutOfBoundsException()
    var vertex = neighborIndex
    if (vertex >= index) vertex++
    return vertex
  }

  override fun successorVertex(index: Int, neighborIndex: Int): Vertex {
    return vertex(successorIndex(index, neighborIndex))
  }
}

/**
 * Returns a complete [UndirectedGraph], which is a graph with all possible edges and no self-loops.
 *
 * @param size the size of the graph.
 */
public fun UndirectedGraph.Companion.complete(size: Int): UndirectedGraph {
  if (size < 0) throw IllegalArgumentException("The size of a graph cannot be negative.")
  return if (size == 0) EmptyUndirectedGraph else CompleteUndirectedGraph(size)
}

/**
 * An implementation of a [UndirectedNetwork] which is complete.
 *
 * @param weight the weight of all edges in the network.
 */
private class CompleteUndirectedNetwork(size: Int, private val weight: Int) :
    CompleteUndirectedGraph(size), UndirectedNetwork {

  override fun successorWeight(index: Int, neighborIndex: Int): Int {
    if (index < 0 || index >= size) throw IndexOutOfBoundsException()
    if (neighborIndex < 0 || neighborIndex >= size - 1) throw IndexOutOfBoundsException()
    return weight
  }

  override fun successorWeight(index: Int, neighbor: Vertex): Int {
    if (index < 0 || index >= size) throw IndexOutOfBoundsException()
    if (neighbor.index < 0 || neighbor.index >= size) throw NoSuchVertexException()
    if (index == neighbor.index) throw NoSuchEdgeException()
    return weight
  }
}

/**
 * Returns a complete [UndirectedNetwork], which is a network with all possible edges and no
 * self-loops.
 *
 * @param size the size of the network.
 * @param weight the weight of all edges in the network.
 */
public fun UndirectedNetwork.Companion.complete(size: Int, weight: Int): UndirectedNetwork {
  if (size < 0) throw IllegalArgumentException("The size of a network cannot be negative.")
  return if (size == 0) EmptyUndirectedNetwork else CompleteUndirectedNetwork(size, weight)
}
