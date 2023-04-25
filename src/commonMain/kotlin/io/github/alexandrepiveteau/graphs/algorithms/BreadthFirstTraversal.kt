package io.github.alexandrepiveteau.graphs.algorithms

import io.github.alexandrepiveteau.graphs.*
import io.github.alexandrepiveteau.graphs.internal.IntDequeue
import kotlin.contracts.contract

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

/**
 * Returns a [VertexArray] with the shortest path going from the [from] vertex to the [to] vertex,
 * using breadth-first search.
 *
 * ## Asymptotic complexity
 * - **Time complexity**: O(|N| + |E|), where |N| is the number of vertices in this graph and |E| is
 *   the number of edges in this graph.
 * - **Space complexity**: O(|N|), where |N| is the number of vertices in this graph.
 *
 * @param from the starting vertex.
 * @param to the ending vertex.
 * @return the [VertexArray] with the shortest path going from the [from] vertex to the [to] vertex,
 *   or `null` if there is no path between the two vertices.
 */
public fun Graph.shortestPathBreadthFirst(from: Vertex, to: Vertex): VertexArray? {
  if (from !in this) throw NoSuchVertexException()
  if (to !in this) throw NoSuchVertexException()

  val parents = VertexMap(size) { Vertex.Invalid }

  forEachVertexBreadthFirst(from) { u ->
    forEachNeighbor(u) { v ->
      if (parents[v] == Vertex.Invalid) parents[v] = u
      if (v == to) return computePath(parents, from, to)
    }
  }

  return null
}
