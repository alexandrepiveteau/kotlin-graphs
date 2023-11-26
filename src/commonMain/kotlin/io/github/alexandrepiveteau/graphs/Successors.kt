package io.github.alexandrepiveteau.graphs

/**
 * [Successors] are a [VertexSet] where each [Vertex] has a list of neighbors, which can be accessed
 * by their index.
 */
public interface Successors : VertexSet {

  /**
   * Returns the number of edges leaving the [Vertex] at the given index in this [Successors]. If
   * the vertex is not contained in this [Successors], an [IndexOutOfBoundsException] is thrown.
   */
  public fun successorsSize(index: Int): Int

  /**
   * Returns the number of edges leaving the given [vertex] in this [Successors]. If the vertex is
   * not contained in this [Successors], a [NoSuchVertexException] is thrown.
   */
  public fun successorsSize(vertex: Vertex): Int = successorsSize(index(vertex))

  /**
   * Returns the index of the vertex at the given [neighborIndex] index in the list of neighbors of
   * the [Vertex] in this [Successors]. If the vertex is not contained in this [Successors], an
   * [IndexOutOfBoundsException] is thrown.
   */
  public fun successorIndex(index: Int, neighborIndex: Int): Int

  /**
   * Returns the index of the vertex at the given [neighborIndex] index in the list of neighbors of
   * the [Vertex] in this [Successors]. If the vertex is not contained in this [Successors], a
   * [NoSuchVertexException], and if the neighbor index is not contained in the list of neighbors of
   * the vertex, an [IndexOutOfBoundsException] is thrown.
   */
  public fun successorIndex(
      vertex: Vertex,
      neighborIndex: Int,
  ): Int = successorIndex(index(vertex), neighborIndex)

  /**
   * Returns the [Vertex] at the given [neighborIndex] index in the list of neighbors of the
   * [Vertex] at the given [index] in this [Successors]. If the vertex is not contained in this
   * [Successors], an [IndexOutOfBoundsException] is thrown.
   */
  public fun successorVertex(index: Int, neighborIndex: Int): Vertex

  /**
   * Returns the [Vertex] at the given [neighborIndex] index in the list of neighbors of the given
   * [vertex] in this [Successors]. If the vertex is not contained in this [Successors], a
   * [NoSuchVertexException], and if the neighbor index is not contained in the list of neighbors of
   * the vertex, an [IndexOutOfBoundsException] is thrown.
   */
  public fun successorVertex(
      vertex: Vertex,
      neighborIndex: Int,
  ): Vertex = successorVertex(index(vertex), neighborIndex)
}
