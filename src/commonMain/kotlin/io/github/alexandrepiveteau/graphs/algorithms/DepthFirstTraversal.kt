@file:JvmName("Traversals")
@file:JvmMultifileClass

package io.github.alexandrepiveteau.graphs.algorithms

import io.github.alexandrepiveteau.graphs.Graph
import io.github.alexandrepiveteau.graphs.Vertex
import io.github.alexandrepiveteau.graphs.internal.collections.IntDequeue
import kotlin.contracts.contract
import kotlin.jvm.JvmMultifileClass
import kotlin.jvm.JvmName

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
 * @param visited the vertices that have already been visited, which will be updated by the search.
 * @param action the action to execute on each vertex.
 */
public inline fun Graph.forEachVertexDepthFirst(
    from: Vertex,
    visited: BooleanArray = BooleanArray(size),
    action: (Vertex) -> Unit,
) {
  contract { callsInPlace(action) }
  forEachVertexDepthFirstHelper(from, visited, action) {}
}

/**
 * Traverses the graph in depth-first order, starting from the given [from] vertex, and performs the
 * given [action] on each vertex after all its neighbors have been visited.
 *
 * ## Asymptotic complexity
 * - **Time complexity**: O(|N| + |E|), where |N| is the number of vertices in this graph and |E| is
 *   the number of edges in this graph.
 * - **Space complexity**: O(|N|), where |N| is the number of vertices in this graph.
 *
 * @param from the vertex from which to start the search.
 * @param visited the vertices that have already been visited, which will be updated by the search.
 * @param action the action to execute on each vertex.
 */
public inline fun Graph.forEachVertexDepthFirstPostOrder(
    from: Vertex,
    visited: BooleanArray = BooleanArray(size),
    action: (Vertex) -> Unit,
) {
  contract { callsInPlace(action) }
  forEachVertexDepthFirstHelper(from, visited, {}, action)
}

/**
 * Traverses the graph in depth-first order, starting from the given [from] vertex, and performs the
 * given [inOrderAction] on each vertex and [postOrderAction] after all its neighbors have been
 * visited.
 *
 * ## Asymptotic complexity
 * - **Time complexity**: O(|N| + |E|), where |N| is the number of vertices in this graph and |E| is
 *   the number of edges in this graph.
 * - **Space complexity**: O(|N|), where |N| is the number of vertices in this graph.
 *
 * @param from the vertex from which to start the search.
 * @param visited the vertices that have already been visited, which will be updated by the search.
 * @param inOrderAction the action to execute on each vertex in order.
 * @param postOrderAction the action to execute on each vertex post order.
 */
@PublishedApi
internal inline fun Graph.forEachVertexDepthFirstHelper(
    from: Vertex,
    visited: BooleanArray = BooleanArray(size),
    inOrderAction: (Vertex) -> Unit,
    postOrderAction: (Vertex) -> Unit,
) {
  contract {
    callsInPlace(inOrderAction)
    callsInPlace(postOrderAction)
  }
  val counts = IntArray(size)
  val path = IntDequeue().apply { addLast(get(from)) }
  while (path.size > 0) {
    val next = path.peekLast()
    if (!visited[next]) {
      inOrderAction(get(next))
      visited[next] = true
    }
    var found = false
    while (counts[next] < neighborsSize(next)) {
      val neighbor = get(next, counts[next]++)
      if (!visited[get(neighbor)]) {
        found = true
        path.addLast(get(neighbor))
        break
      }
    }
    if (!found) {
      val finished = path.removeLast()
      postOrderAction(get(finished))
    }
  }
}
