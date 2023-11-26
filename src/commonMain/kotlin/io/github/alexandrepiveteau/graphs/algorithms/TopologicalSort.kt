@file:JvmName("Algorithms")
@file:JvmMultifileClass

package io.github.alexandrepiveteau.graphs.algorithms

import io.github.alexandrepiveteau.graphs.*
import io.github.alexandrepiveteau.graphs.internal.collections.IntDequeue
import kotlin.jvm.JvmMultifileClass
import kotlin.jvm.JvmName

/**
 * Returns a [VertexArray] containing the vertices of the graph, sorted in topological order. If the
 * graph contains a cycle, an [IllegalArgumentException] is thrown.
 *
 * ## Asymptotic complexity
 * - **Time complexity**: O(|N| + |E|), where |N| is the number of vertices in this graph and |E| is
 *   the number of edges in this graph.
 * - **Space complexity**: O(|N|), where n is the number of vertices in this graph.
 *
 * @return a [VertexArray] containing the vertices of the graph, sorted in topological order.
 * @receiver the [DirectedGraph] to sort topologically.
 * @throws IllegalArgumentException if the graph contains a cycle.
 */
public fun <G> G.topologicalSort(): VertexArray where G : Directed, G : Successors {

  // 0. Set up a queue of vertices to add to the topological sort, and a boolean array to keep track
  // of the vertices that have already been sorted.
  val queue = IntDequeue()
  val sorted = BooleanArray(size)
  val result = IntDequeue()

  // 1. Compute the number of incoming edges for each vertex.
  val edges = IntArray(size)
  forEachArc { (_, to) -> edges[index(to)]++ }

  // 2. Add all the vertices with no incoming edges to the queue.
  for (i in edges.indices) {
    if (edges[i] == 0) {
      queue.addLast(i)
      sorted[i] = true
    }
  }

  // 3. While there are some vertices to add to the topological sort, add them and remove their
  // outgoing edges. If a vertex has no more outgoing edges and has not been sorted yet, add it to
  // the queue.
  while (queue.size > 0) {
    val vertex = queue.removeFirst()
    result.addLast(vertex)

    forEachSuccessor(vertex(vertex)) {
      val to = index(it)
      edges[to]--
      if (edges[to] == 0 && !sorted[to]) {
        queue.addLast(to)
        sorted[to] = true
      }
    }
  }

  // 4. If there are still some vertices that have not been sorted, then there is a cycle in the
  // graph. Throw an exception.
  if (result.size != size) throw IllegalArgumentException("The graph contains a cycle.")

  // 5. Return the result.
  return result.toIntArray().asVertexArray()
}
