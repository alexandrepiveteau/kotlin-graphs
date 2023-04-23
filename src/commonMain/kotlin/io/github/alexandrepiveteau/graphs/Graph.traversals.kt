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
 * Returns the [VertexArray] containing all the vertices traversed to go from the [from] vertex to
 * the [to] vertex, using the given [parents] map.
 *
 * @param parents the map containing the parents of each vertex.
 * @param from the starting vertex.
 * @param to the ending vertex.
 * @return the [VertexArray] containing all the vertices traversed to go from the [from] vertex to
 *   the [to] vertex.
 * @receiver the [Graph] on which the traversal was performed.
 */
private fun Graph.computePath(parents: VertexMap, from: Vertex, to: Vertex): VertexArray {
  val path = IntDequeue()
  var current = to
  while (current != from) {
    path.addFirst(get(current))
    current = parents[current]
  }
  path.addFirst(get(from))
  return VertexArray(path.toIntArray())
}

// TODO : Use this to compute the graph of the shortest path between two vertices.
private fun Graph.computeGraph(parents: VertexMap): DirectedGraph {
  return buildDirectedGraph {
    forEachVertex { addVertex() }
    parents.forEach { vertex, parent ->
      if (parent != Vertex.Invalid) {
        addArc(parent arcTo vertex)
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
