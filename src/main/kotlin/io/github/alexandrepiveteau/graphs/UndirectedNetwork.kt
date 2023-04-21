package io.github.alexandrepiveteau.graphs

/**
 * A [UndirectedNetwork] is a [UndirectedGraph] with some weights associated to each edge. The
 * weights are represented as [Int] values.
 */
public interface UndirectedNetwork : UndirectedGraph {

  /**
   * Returns the weight of the edge between the [Vertex] at [index] and the [Vertex] at
   * [neighborIndex].
   *
   * @throws IndexOutOfBoundsException if [index] or [neighborIndex] are not valid indices.
   */
  public fun weight(index: Int, neighborIndex: Int): Int

  /**
   * Returns the weight of the edge between the [vertex] and its [neighbor].
   *
   * @throws NoSuchVertexException if [vertex] or [neighbor] are not valid vertices.
   * @throws NoSuchEdgeException if there is no edge between [vertex] and [neighbor].
   */
  public fun weight(vertex: Vertex, neighbor: Vertex): Int

  /**
   * Returns the weight of the edge between the [vertex] and the [Vertex] at [neighborIndex].
   *
   * @throws NoSuchVertexException if [vertex] is not a valid vertex.
   * @throws IndexOutOfBoundsException if [neighborIndex] is not a valid index.
   */
  public fun weight(vertex: Vertex, neighborIndex: Int): Int = weight(get(vertex), neighborIndex)

  /**
   * Returns the weight of the [edge].
   *
   * @throws NoSuchVertexException if [edge] is not a valid edge for this [UndirectedNetwork].
   * @throws NoSuchEdgeException if there is no edge between [edge] and [neighbor].
   */
  public fun weight(edge: Edge): Int = weight(edge.component1(), edge.component2())

  /**
   * An object which serves as the companion of the [UndirectedNetwork], and which provides a number
   * of methods to create [UndirectedNetwork]s.
   */
  public companion object
}
