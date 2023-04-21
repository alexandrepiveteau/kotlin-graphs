package io.github.alexandrepiveteau.graphs

import kotlin.contracts.contract

/**
 * Performs the given [action] on each arc in this graph.
 *
 * ## Asymptotic complexity
 * - **Time complexity**: O(|A|), where |A| is the number of arcs in this graph.
 * - **Space complexity**: O(1).
 */
public inline fun DirectedGraph.forEachArc(action: (Arc) -> Unit) {
  contract { callsInPlace(action) }
  forEachVertex { u -> forEachNeighbor(u) { v -> action(Arc(u, v)) } }
}
