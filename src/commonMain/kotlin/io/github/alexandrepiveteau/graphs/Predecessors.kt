package io.github.alexandrepiveteau.graphs

/**
 * [Predecessors] are a [VertexSet] where each [Vertex] has a list of neighbors, which can be
 * accessed by their index.
 */
public interface Predecessors : VertexSet {

  /**
   * Returns the number of edges entering the [Vertex] at the given index in this [Predecessors]. If
   * the vertex is not contained in this [Predecessors], an [IndexOutOfBoundsException] is thrown.
   */
  public fun predecessorsSize(index: Int): Int

  /**
   * Returns the number of edges entering the given [vertex] in this [Predecessors]. If the vertex
   * is not contained in this [Predecessors], a [NoSuchVertexException] is thrown.
   */
  public fun predecessorsSize(vertex: Vertex): Int = predecessorsSize(index(vertex))

  /**
   * Returns the [Vertex] at the given [neighborIndex] index in the list of neighbors of the
   * [Vertex] at the given [index] in this [Predecessors]. If the vertex is not contained in this
   * [Predecessors], an [IndexOutOfBoundsException] is thrown.
   */
  public fun predecessor(index: Int, neighborIndex: Int): Vertex

  /**
   * Returns the [Vertex] at the given [neighborIndex] index in the list of neighbors of the given
   * [vertex] in this [Predecessors]. If the vertex is not contained in this [Predecessors], a
   * [NoSuchVertexException], and if the neighbor index is not contained in the list of neighbors of
   * the vertex, an [IndexOutOfBoundsException] is thrown.
   */
  public fun predecessor(
      vertex: Vertex,
      neighborIndex: Int,
  ): Vertex = predecessor(index(vertex), neighborIndex)
}
