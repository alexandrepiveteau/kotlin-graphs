@file:JvmName("Algorithms")
@file:JvmMultifileClass

package io.github.alexandrepiveteau.graphs.algorithms

import io.github.alexandrepiveteau.graphs.*
import io.github.alexandrepiveteau.graphs.internal.collections.IntMinPriorityQueue
import kotlin.jvm.JvmMultifileClass
import kotlin.jvm.JvmName

/**
 * Returns the list of parents for each vertex in the shortest path tree from the [from] vertex.
 *
 * @param from the [Vertex] to start the search from.
 * @return the map of parents for each vertex in the shortest path tree from the [from] vertex.
 */
private fun Network.shortestPathDijkstraParents(from: Vertex): VertexMap {
  val parents = VertexMap(size) { Vertex.Invalid }
  val queue = IntMinPriorityQueue(size)
  val distances = IntArray(size) { Int.MAX_VALUE }
  val visited = BooleanArray(size) { false }

  distances[get(from)] = 0
  queue[get(from)] = 0

  while (queue.size > 0) {
    val v1 = get(queue.remove())
    visited[get(v1)] = true
    forEachNeighbor(v1) { v2, weight ->
      if (!visited[get(v2)]) {
        // Note : we only throw on negative weights if they are visited, so a graph with negative
        //        weights in a component disconnected from the source will not throw.
        if (weight < 0) throw IllegalArgumentException("Negative weights are not supported.")
        val d1 = distances[get(v1)]
        val d2 = distances[get(v2)]
        val alt = d1 + weight
        if (alt < d2) {
          distances[get(v2)] = alt
          parents[v2] = v1
          queue[get(v2)] = alt
        }
      }
    }
  }

  return parents
}

/**
 * Computes the shortest path from the given [from] vertex to all the other vertices in the network,
 * using Dijkstra's algorithm.
 *
 * ## Asymptotic complexity
 * - **Time complexity**: O(|N| * log(|N|) + |E|), where |N| is the number of vertices, and |E| is
 *   the number of edges in this graph.
 * - **Space complexity**: O(|N|), where |N| is the number of vertices in this graph.
 *
 * @param from the [Vertex] to start the search from.
 * @return the [DirectedNetwork] containing the shortest paths from the [from] vertex to all the
 *   other vertices in the network.
 * @throws NoSuchVertexException if the [from] vertex is not in this network.
 * @throws IllegalArgumentException if the network contains negative weights.
 */
public fun Network.shortestPathDijkstra(from: Vertex): DirectedNetwork {
  if (from !in this) throw NoSuchVertexException()
  return computeNetwork(shortestPathDijkstraParents(from))
}

/**
 * Computes the shortest path from the given [from] vertex to the [to] vertex in the network, using
 * Dijkstra's algorithm.
 *
 * ## Asymptotic complexity
 * - **Time complexity**: O(|N| * log(|N|) + |E|), where |N| is the number of vertices, and |E| is
 *   the number of edges in this graph.
 * - **Space complexity**: O(|N|), where |N| is the number of vertices in this graph.
 *
 * @param from the source vertex.
 * @param to the target vertex.
 * @return the shortest path from the given [from] vertex to the given [to] vertex in the network,
 *   or `null` if no such path exists.
 * @throws NoSuchVertexException if the [from] or [to] vertices are not in this graph.
 * @throws IllegalArgumentException if the network contains negative weights.
 */
public fun Network.shortestPathDijkstra(from: Vertex, to: Vertex): VertexArray? {
  if (from !in this) throw NoSuchVertexException()
  if (to !in this) throw NoSuchVertexException()
  return computePath(shortestPathDijkstraParents(from), from, to)
}
