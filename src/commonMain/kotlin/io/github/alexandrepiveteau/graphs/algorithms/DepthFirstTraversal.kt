@file:JvmName("Traversals")
@file:JvmMultifileClass

package io.github.alexandrepiveteau.graphs.algorithms

import io.github.alexandrepiveteau.graphs.Successors
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
public inline fun <G> G.forEachVertexDepthFirst(
    from: Vertex,
    visited: BooleanArray = BooleanArray(size),
    action: (Vertex) -> Unit,
) where G : Successors {
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
public inline fun <G> G.forEachVertexDepthFirstPostOrder(
    from: Vertex,
    visited: BooleanArray = BooleanArray(size),
    action: (Vertex) -> Unit,
) where G : Successors {
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
internal inline fun <G> G.forEachVertexDepthFirstHelper(
    from: Vertex,
    visited: BooleanArray = BooleanArray(size),
    inOrderAction: (Vertex) -> Unit,
    postOrderAction: (Vertex) -> Unit,
) where G : Successors {
  contract {
    callsInPlace(inOrderAction)
    callsInPlace(postOrderAction)
  }
  val counts = IntArray(size)
  val path = IntDequeue().apply { addLast(index(from)) }
  while (path.size > 0) {
    val next = path.peekLast()
    if (!visited[next]) {
      inOrderAction(vertex(next))
      visited[next] = true
    }
    var found = false
    while (counts[next] < successorsSize(next)) {
      val neighbor = successorVertex(next, counts[next]++)
      if (!visited[index(neighbor)]) {
        found = true
        path.addLast(index(neighbor))
        break
      }
    }
    if (!found) {
      val finished = path.removeLast()
      postOrderAction(vertex(finished))
    }
  }
}
