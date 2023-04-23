package io.github.alexandrepiveteau.graphs

import kotlin.contracts.contract

/**
 * Performs the given [action] on each edge in this graph.
 *
 * ## Asymptotic complexity
 * - **Time complexity**: O(|E|), where |E| is the number of edges in this graph.
 * - **Space complexity**: O(1).
 */
public inline fun UndirectedGraph.forEachEdge(action: (Edge) -> Unit) {
  contract { callsInPlace(action) }
  forEachVertex { u -> forEachNeighbor(u) { v -> if (u.index <= v.index) action(u edgeTo v) } }
}
