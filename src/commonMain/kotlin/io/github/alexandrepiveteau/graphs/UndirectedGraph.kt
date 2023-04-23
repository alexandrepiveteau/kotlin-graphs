package io.github.alexandrepiveteau.graphs

/** A [UndirectedGraph] is a [Graph] where [Vertex]s are linked using [Edge]s. */
public interface UndirectedGraph : Graph {

  /**
   * Returns true if the given [edge] is contained in this [UndirectedGraph], and false otherwise.
   */
  public operator fun contains(edge: Edge): Boolean

  /**
   * An object which serves as the companion of [UndirectedGraph], and which provides a number of
   * factory methods to create [UndirectedGraph]s.
   */
  public companion object
}
