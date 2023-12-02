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
public inline fun <G> G.forEachVertex(
    action: (Vertex) -> Unit,
) where G : VertexSet {
  contract { callsInPlace(action) }
  repeat(size) { action(vertex(it)) }
}

/**
 * Performs the given [action] on each neighbor of the given [vertex].
 *
 * ## Asymptotic complexity
 * - **Time complexity**: O(|N|), where |N| is the number of neighbors of the given [vertex].
 * - **Space complexity**: O(1).
 */
public inline fun <G> G.forEachSuccessor(
    vertex: Vertex,
    action: (Vertex) -> Unit,
) where G : Successors {
  contract { callsInPlace(action) }
  repeat(successorsSize(index(vertex))) { action(successorVertex(vertex, it)) }
}

/**
 * Performs the given [action] on each neighbor of the given [vertex], along with its weight.
 *
 * ## Asymptotic complexity
 * - **Time complexity**: O(|N|), where |N| is the number of neighbors of the given [vertex].
 * - **Space complexity**: O(1).
 */
public inline fun <N> N.forEachSuccessor(
    vertex: Vertex,
    action: (Vertex, Int) -> Unit,
) where N : SuccessorsWeight {
  contract { callsInPlace(action) }
  repeat(successorsSize(index(vertex))) {
    action(successorVertex(vertex, it), successorWeight(vertex, it))
  }
}

/**
 * Performs the given [action] on each arc in this graph.
 *
 * ## Asymptotic complexity
 * - **Time complexity**: O(|A|), where |A| is the number of arcs in this graph.
 * - **Space complexity**: O(1).
 */
public inline fun <G> G.forEachArc(
    action: (Arc) -> Unit,
) where G : Directed, G : Successors {
  contract { callsInPlace(action) }
  forEachVertex { u -> forEachSuccessor(u) { v -> action(u arcTo v) } }
}

/**
 * Performs the given [action] on each arc in this graph, along with its weight.
 *
 * ## Asymptotic complexity
 * - **Time complexity**: O(|A|), where |A| is the number of arcs in this graph.
 * - **Space complexity**: O(1).
 */
public inline fun <N> N.forEachArc(
    action: (Arc, Int) -> Unit,
) where N : Directed, N : SuccessorsWeight {
  contract { callsInPlace(action) }
  forEachVertex { u -> forEachSuccessor(u) { v, w -> action(u arcTo v, w) } }
}

/**
 * Performs the given [action] on each edge in this graph.
 *
 * ## Asymptotic complexity
 * - **Time complexity**: O(|E|), where |E| is the number of edges in this graph.
 * - **Space complexity**: O(1).
 */
public inline fun <G> G.forEachEdge(
    action: (Edge) -> Unit,
) where G : Undirected, G : Successors {
  contract { callsInPlace(action) }
  forEachVertex { u -> forEachSuccessor(u) { v -> if (index(u) <= index(v)) action(u edgeTo v) } }
}

/**
 * Performs the given [action] on each edge in this graph, along with its weight.
 *
 * ## Asymptotic complexity
 * - **Time complexity**: O(|E|), where |E| is the number of edges in this graph.
 * - **Space complexity**: O(1).
 */
public inline fun <N> N.forEachEdge(
    action: (Edge, Int) -> Unit,
) where N : Undirected, N : SuccessorsWeight {
  contract { callsInPlace(action) }
  forEachVertex { u ->
    forEachSuccessor(u) { v, w -> if (index(u) <= index(v)) action(u edgeTo v, w) }
  }
}
