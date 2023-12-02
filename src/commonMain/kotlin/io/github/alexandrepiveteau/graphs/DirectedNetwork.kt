package io.github.alexandrepiveteau.graphs

import io.github.alexandrepiveteau.graphs.algorithms.forEachArc
import io.github.alexandrepiveteau.graphs.algorithms.forEachVertex
import io.github.alexandrepiveteau.graphs.builder.buildDirectedNetwork

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

/** Returns the transposed [Network], where the direction of arcs has been reversed. */
public fun <N> N.transposed(): DirectedNetwork where N : Directed, N : SuccessorsWeight {
  return buildDirectedNetwork {
    forEachVertex { addVertex() }
    forEachArc { arc, weight -> addArc(arc.reversed(), weight) }
  }
}
