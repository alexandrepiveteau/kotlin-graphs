package io.github.alexandrepiveteau.graphs

import io.github.alexandrepiveteau.graphs.internal.IntDequeue
import kotlin.contracts.contract

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
 * Traverses the graph in depth-first order, starting from the given [from] vertex, and performs the
 * given [action] on each vertex.
 *
 * ## Asymptotic complexity
 * - **Time complexity**: O(|N| + |E|), where |N| is the number of vertices in this graph and |E| is
 *   the number of edges in this graph.
 * - **Space complexity**: O(|N|), where |N| is the number of vertices in this graph.
 *
 * @param from the vertex from which to start the search.
 * @param action the action to execute on each vertex.
 */
public inline fun Graph.forEachVertexDepthFirst(from: Vertex, action: (Vertex) -> Unit) {
  contract { callsInPlace(action) }
  val visited = BooleanArray(size)
  val counts = IntArray(size)
  val path = IntArray(size).apply { this[0] = from.index }
  var pathLength = 1
  while (pathLength > 0) {
    val next = path[pathLength - 1]
    if (!visited[next]) {
      action(get(next))
      visited[next] = true
    }
    var found = false
    while (counts[next] < neighborsSize(next)) {
      val neighbor = get(next, counts[next]++)
      if (!visited[neighbor.index]) {
        found = true
        path[pathLength++] = neighbor.index
        break
      }
    }
    if (!found) pathLength--
  }
}

/**
 * Traverses the graph in breadth-first order, starting from the given [from] vertex, and performs
 * the given [action] on each vertex.
 *
 * ## Asymptotic complexity
 * - **Time complexity**: O(|N| + |E|), where |N| is the number of vertices in this graph and |E| is
 *   the number of edges in this graph.
 * - **Space complexity**: O(|N|), where n is the number of vertices in this graph.
 *
 * @param from the vertex from which to start the search.
 * @param action the action to execute on each vertex.
 */
public inline fun Graph.forEachVertexBreadthFirst(from: Vertex, action: (Vertex) -> Unit) {
  contract { callsInPlace(action) }
  val queue = IntDequeue().apply { addLast(from.index) }
  val visited = BooleanArray(size).apply { this[from.index] = true }
  while (queue.size > 0) {
    val next = queue.removeFirst()
    action(get(next))
    forEachNeighbor(get(next)) {
      if (!visited[it.index]) {
        queue.addLast(it.index)
        visited[it.index] = true
      }
    }
  }
}
