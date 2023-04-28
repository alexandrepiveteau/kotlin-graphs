@file:JvmName("Traversals")
@file:JvmMultifileClass

package io.github.alexandrepiveteau.graphs.algorithms

import io.github.alexandrepiveteau.graphs.*
import kotlin.contracts.contract
import kotlin.jvm.JvmMultifileClass
import kotlin.jvm.JvmName

/**
 * Performs the given [action] on each vertex of this graph.
 *
 * ## Asymptotic complexity
 * - **Time complexity**: O(|N|), where |N| is the number of vertices in this graph.
 * - **Space complexity**: O(1).
 */
public inline fun Graph.forEachVertex(action: (Vertex) -> Unit) {
  contract { callsInPlace(action) }
  for (i in 0 until size) action(get(i))
}

/**
 * Performs the given [action] on each neighbor of the given [vertex].
 *
 * ## Asymptotic complexity
 * - **Time complexity**: O(|N|), where |N| is the number of neighbors of the given [vertex].
 * - **Space complexity**: O(1).
 */
public inline fun Graph.forEachNeighbor(vertex: Vertex, action: (Vertex) -> Unit) {
  contract { callsInPlace(action) }
  val index = get(vertex)
  for (i in 0 until neighborsSize(index)) action(get(vertex, i))
}

/**
 * Performs the given [action] on each neighbor of the given [vertex], along with its weight.
 *
 * ## Asymptotic complexity
 * - **Time complexity**: O(|N|), where |N| is the number of neighbors of the given [vertex].
 * - **Space complexity**: O(1).
 */
public inline fun Network.forEachNeighbor(vertex: Vertex, action: (Vertex, Int) -> Unit) {
  contract { callsInPlace(action) }
  val index = get(vertex)
  for (i in 0 until neighborsSize(index)) action(get(vertex, i), weight(vertex, i))
}

/**
 * Performs the given [action] on each arc in this graph.
 *
 * ## Asymptotic complexity
 * - **Time complexity**: O(|A|), where |A| is the number of arcs in this graph.
 * - **Space complexity**: O(1).
 */
public inline fun DirectedGraph.forEachArc(action: (Arc) -> Unit) {
  contract { callsInPlace(action) }
  forEachVertex { u -> forEachNeighbor(u) { v -> action(u arcTo v) } }
}

/**
 * Performs the given [action] on each arc in this graph, along with its weight.
 *
 * ## Asymptotic complexity
 * - **Time complexity**: O(|A|), where |A| is the number of arcs in this graph.
 * - **Space complexity**: O(1).
 */
public inline fun DirectedNetwork.forEachArc(action: (Arc, Int) -> Unit) {
  contract { callsInPlace(action) }
  forEachVertex { u -> forEachNeighbor(u) { v, w -> action(u arcTo v, w) } }
}

/**
 * Performs the given [action] on each edge in this graph.
 *
 * ## Asymptotic complexity
 * - **Time complexity**: O(|E|), where |E| is the number of edges in this graph.
 * - **Space complexity**: O(1).
 */
public inline fun UndirectedGraph.forEachEdge(action: (Edge) -> Unit) {
  contract { callsInPlace(action) }
  forEachVertex { u -> forEachNeighbor(u) { v -> if (get(u) <= get(v)) action(u edgeTo v) } }
}

/**
 * Performs the given [action] on each edge in this graph, along with its weight.
 *
 * ## Asymptotic complexity
 * - **Time complexity**: O(|E|), where |E| is the number of edges in this graph.
 * - **Space complexity**: O(1).
 */
public inline fun UndirectedNetwork.forEachEdge(action: (Edge, Int) -> Unit) {
  contract { callsInPlace(action) }
  forEachVertex { u -> forEachNeighbor(u) { v, w -> if (get(u) <= get(v)) action(u edgeTo v, w) } }
}
