package io.github.alexandrepiveteau.graphs

/**
 * A [Network] is a [Graph] with some weights associated to each link. The weights are represented
 * as [Int] values.
 */
public interface Network : Graph {

  /**
   * Returns the weight of the edge between the [Vertex] at [index] and the [Vertex] at
   * [neighborIndex].
   *
   * @throws IndexOutOfBoundsException if [index] or [neighborIndex] are not valid indices.
   */
  public fun weight(index: Int, neighborIndex: Int): Int

  /**
   * Returns the weight of the edge between the [Vertex] at [index] and its [neighbor].
   *
   * @throws IndexOutOfBoundsException if [index] is not a valid index.
   * @throws NoSuchVertexException if [neighbor] is not a valid vertex.
   */
  public fun weight(index: Int, neighbor: Vertex): Int

  /**
   * Returns the weight of the edge between the [vertex] and its [neighbor].
   *
   * @throws NoSuchVertexException if [vertex] or [neighbor] are not valid vertices.
   * @throws NoSuchEdgeException if there is no edge between [vertex] and [neighbor].
   */
  public fun weight(vertex: Vertex, neighbor: Vertex): Int = weight(get(vertex), neighbor)

  /**
   * Returns the weight of the edge between the [vertex] and the [Vertex] at [neighborIndex].
   *
   * @throws NoSuchVertexException if [vertex] is not a valid vertex.
   * @throws IndexOutOfBoundsException if [neighborIndex] is not a valid index.
   */
  public fun weight(vertex: Vertex, neighborIndex: Int): Int = weight(get(vertex), neighborIndex)
}
