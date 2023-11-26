package io.github.alexandrepiveteau.graphs

/**
 * [SuccessorsWeight] are a [Successors] where each link has a weight, which can be accessed by
 * their index.
 */
public interface SuccessorsWeight : Successors {

  /**
   * Returns the weight of the edge between the [Vertex] at [index] and the [Vertex] at
   * [neighborIndex].
   *
   * @throws IndexOutOfBoundsException if [index] or [neighborIndex] are not valid indices.
   */
  public fun successorWeight(index: Int, neighborIndex: Int): Int

  /**
   * Returns the weight of the edge between the [Vertex] at [index] and its [neighbor].
   *
   * @throws IndexOutOfBoundsException if [index] is not a valid index.
   * @throws NoSuchVertexException if [neighbor] is not a valid vertex.
   */
  public fun successorWeight(index: Int, neighbor: Vertex): Int

  /**
   * Returns the weight of the edge between the [vertex] and its [neighbor].
   *
   * @throws NoSuchVertexException if [vertex] or [neighbor] are not valid vertices.
   * @throws NoSuchEdgeException if there is no edge between [vertex] and [neighbor].
   */
  public fun successorWeight(vertex: Vertex, neighbor: Vertex): Int =
      successorWeight(index(vertex), neighbor)

  /**
   * Returns the weight of the edge between the [vertex] and the [Vertex] at [neighborIndex].
   *
   * @throws NoSuchVertexException if [vertex] is not a valid vertex.
   * @throws IndexOutOfBoundsException if [neighborIndex] is not a valid index.
   */
  public fun successorWeight(vertex: Vertex, neighborIndex: Int): Int =
      successorWeight(index(vertex), neighborIndex)
}
