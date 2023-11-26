package io.github.alexandrepiveteau.graphs

/** A set of vertices. Vertices can be iterated over, and their [size] can be queried. */
public interface VertexSet {

  /** The number of vertices in this [VertexSet]. */
  public val size: Int

  /** Returns true if the given [vertex] is contained in this [VertexSet], and false otherwise. */
  public operator fun contains(vertex: Vertex): Boolean = index(vertex) in 0 ..< size

  /**
   * Returns the index of the given [vertex] in this [VertexSet]. If the vertex is not contained in
   * this [VertexSet], a [NoSuchVertexException] is thrown.
   */
  public fun index(vertex: Vertex): Int

  /**
   * Returns the [Vertex] at the given [index] in this [VertexSet]. If the index is not contained in
   * this [VertexSet], an [IndexOutOfBoundsException] is thrown.
   */
  public fun vertex(index: Int): Vertex
}
