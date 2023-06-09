package io.github.alexandrepiveteau.graphs

/**
 * A [Graph] is a set of [Vertex], which may be linked together by some undirected [Edge]s or some
 * directed [Arc]s. Graphs can be iterated over, and their [size] can be queried.
 *
 * Vertices and links are indexed, and [Graph] provides a number of methods to access vertices and
 * links by their index. This is useful for algorithms which need to iterate over the vertices and
 * links of the graph without having to create an explicit iterator.
 */
public interface Graph {

  /** The number of vertices in this [Graph]. */
  public val size: Int

  /** Returns true if the given [vertex] is contained in this [Graph], and false otherwise. */
  public operator fun contains(vertex: Vertex): Boolean = get(vertex) in 0 until size

  /**
   * Returns the index of the given [vertex] in this [Graph]. If the vertex is not contained in this
   * [Graph], a [NoSuchVertexException] is thrown.
   */
  public operator fun get(vertex: Vertex): Int

  /**
   * Returns the [Vertex] at the given [index] in this [Graph]. If the index is not contained in
   * this [Graph], an [IndexOutOfBoundsException] is thrown.
   */
  public operator fun get(index: Int): Vertex

  /**
   * Returns the number of edges leaving the [Vertex] at the given index in this [Graph]. If the
   * vertex is not contained in this [Graph], an [IndexOutOfBoundsException] is thrown.
   */
  public fun neighborsSize(index: Int): Int

  /**
   * Returns the number of edges leaving the given [vertex] in this [Graph]. If the vertex is not
   * contained in this [Graph], a [NoSuchVertexException] is thrown.
   */
  public fun neighborsSize(vertex: Vertex): Int = neighborsSize(get(vertex))

  /**
   * Returns the [Vertex] at the given [neighborIndex] index in the list of neighbors of the
   * [Vertex] at the given [index] in this [Graph]. If the vertex is not contained in this [Graph],
   * an [IndexOutOfBoundsException] is thrown.
   */
  public fun neighbor(index: Int, neighborIndex: Int): Vertex

  /**
   * Returns the [Vertex] at the given [neighborIndex] index in the list of neighbors of the given
   * [vertex] in this [Graph]. If the vertex is not contained in this [Graph], a
   * [NoSuchVertexException], and if the neighbor index is not contained in the list of neighbors of
   * the vertex, an [IndexOutOfBoundsException] is thrown.
   */
  public fun neighbor(
      vertex: Vertex,
      neighborIndex: Int,
  ): Vertex = neighbor(get(vertex), neighborIndex)

  /** @see neighbor */
  public operator fun get(
      vertex: Int,
      neighborIndex: Int,
  ): Vertex = neighbor(vertex, neighborIndex)

  /** @see neighbor */
  public operator fun get(
      vertex: Vertex,
      neighborIndex: Int,
  ): Vertex = neighbor(vertex, neighborIndex)
}
