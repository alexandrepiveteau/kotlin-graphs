package io.github.alexandrepiveteau.graphs

/** An [UndirectedNetwork] is a [Network] and an [UndirectedGraph]. */
public interface UndirectedNetwork : Network, UndirectedGraph {

  /**
   * Returns the weight of the [edge].
   *
   * @throws NoSuchVertexException if [edge] is not a valid edge for this [UndirectedNetwork].
   * @throws NoSuchEdgeException if there is no edge between the two vertices.
   */
  public fun weight(edge: Edge): Int = weight(edge.component1(), edge.component2())

  /**
   * An object which serves as the companion of the [UndirectedNetwork], and which provides a number
   * of methods to create [UndirectedNetwork]s.
   */
  public companion object
}
