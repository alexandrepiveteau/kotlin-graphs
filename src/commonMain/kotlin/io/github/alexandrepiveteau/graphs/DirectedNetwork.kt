package io.github.alexandrepiveteau.graphs

/** A [DirectedNetwork] is a [Network] and a [DirectedGraph]. */
public interface DirectedNetwork : Network, DirectedGraph {

  /**
   * An object which serves as the companion of the [DirectedNetwork], and which provides a number
   * of methods to create [DirectedNetwork]s.
   */
  public companion object
}

/**
 * Returns the weight of the [Arc].
 *
 * @throws NoSuchVertexException if [arc] is not a valid arc for this [UndirectedNetwork].
 * @throws NoSuchEdgeException if there is no arc between the two vertices.
 */
public fun <N> N.successorWeight(
    arc: Arc,
): Int where N : Directed, N : SuccessorsWeight = successorWeight(arc.from, arc.to)
