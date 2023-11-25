@file:JvmName("Algorithms")
@file:JvmMultifileClass

package io.github.alexandrepiveteau.graphs.algorithms

import io.github.alexandrepiveteau.graphs.*
import io.github.alexandrepiveteau.graphs.internal.collections.IntDequeue
import kotlin.jvm.JvmMultifileClass
import kotlin.jvm.JvmName

/**
 * Returns the list of parents for each vertex in the shortest path tree from the [from] vertex.
 *
 * @param from the [Vertex] to start the search from.
 * @return the map of parents for each vertex in the shortest path tree from the [from] vertex.
 */
private fun Network.shortestPathFasterAlgorithmParents(from: Vertex): VertexMap {
  val enqueued = BooleanArray(size)
  val distances = IntArray(size) { Int.MAX_VALUE }
  val queue = IntDequeue()
  val parents = VertexMap(size) { Vertex.Invalid }

  distances[index(from)] = 0
  enqueued[index(from)] = true
  queue.addLast(index(from))

  // TODO : Handle negative cycles and throw an exception when one is found. This will be the case
  //        when the number of relaxations exceeds a certain threshold.
  while (queue.size > 0) {
    val v1 = vertex(queue.removeFirst())
    enqueued[index(v1)] = false
    forEachNeighbor(v1) { v2, weight ->
      val d1 = distances[index(v1)]
      val d2 = distances[index(v2)]
      if (d1 != Int.MAX_VALUE && (d2 == Int.MAX_VALUE || d1 + weight < d2)) {
        distances[index(v2)] = d1 + weight
        parents[v2] = v1
        if (!enqueued[index(v2)]) {
          enqueued[index(v2)] = true
          queue.addLast(index(v2))
        }
      }
    }
  }

  return parents
}

/**
 * Computes the shortest path from the given [from] vertex to all the other vertices in the network,
 * using the
 * [Shortest Path Faster Algorithm](https://en.wikipedia.org/wiki/Shortest_path_faster_algorithm),
 * which is a variant of the Bellman-Ford algorithm specialized for sparse graphs.
 *
 * ## Asymptotic complexity
 * - **Time complexity**: O(|N| * |E|), where |N| is the number of vertices, and |E| is the number
 *   of edges in this graph.
 * - **Space complexity**: O(|N|), where |N| is the number of vertices in this graph.
 *
 * @param from the source vertex.
 * @return the subnetwork of the shortest path from the given [from] vertex to all the other
 *   vertices in the network.
 * @throws NoSuchVertexException if the given [from] vertex is not in this graph.
 */
public fun Network.shortestPathFasterAlgorithm(from: Vertex): DirectedNetwork {
  if (from !in this) throw NoSuchVertexException()
  return computeNetwork(shortestPathFasterAlgorithmParents(from))
}

/**
 * Computes the shortest path from the given [from] vertex to the given [to] vertex in the network,
 * using the
 * [Shortest Path Faster Algorithm](https://en.wikipedia.org/wiki/Shortest_path_faster_algorithm),
 * which is a variant of the Bellman-Ford algorithm specialized for sparse graphs.
 *
 * ## Asymptotic complexity
 * - **Time complexity**: O(|N| * |E|), where |N| is the number of vertices, and |E| is the number
 *   of edges in this graph.
 * - **Space complexity**: O(|N|), where |N| is the number of vertices in this graph.
 *
 * @param from the source vertex.
 * @param to the target vertex.
 * @return the shortest path from the given [from] vertex to the given [to] vertex in the network,
 *   or `null` if no such path exists.
 * @throws NoSuchVertexException if the given [from] vertex or [to] vertex is not in this graph.
 */
public fun Network.shortestPathFasterAlgorithm(from: Vertex, to: Vertex): VertexArray? {
  if (from !in this) throw NoSuchVertexException()
  if (to !in this) throw NoSuchVertexException()
  return computePath(shortestPathFasterAlgorithmParents(from), from, to)
}
