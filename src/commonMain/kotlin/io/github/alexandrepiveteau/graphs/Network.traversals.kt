package io.github.alexandrepiveteau.graphs

import io.github.alexandrepiveteau.graphs.internal.IntDequeue

/**
 * Computes the shortest path from the given [source] vertex to all the other vertices in the
 * network, using the
 * [Shortest Path Faster Algorithm](https://en.wikipedia.org/wiki/Shortest_path_faster_algorithm),
 * which is a variant of the Bellman-Ford algorithm specialized for sparse graphs.
 *
 * ## Asymptotic complexity
 * - **Time complexity**: `O(|N| * |E|)`, where |N| is the number of vertices, and |E| is the number
 *   of edges in this graph.
 * - **Space complexity**: `O(|N|)`, where |N| is the number of vertices in this graph.
 *
 * @param source the source vertex.
 * @return an array of distances from the index of the source vertex to all the other vertices in
 *   the network.
 */
public fun Network.shortestPathFasterAlgorithm(source: Vertex): IntArray {
  if (source !in this) throw NoSuchVertexException()

  val enqueued = BooleanArray(size)
  val distances = IntArray(size) { Int.MAX_VALUE }
  val queue = IntDequeue()

  distances[get(source)] = 0
  enqueued[get(source)] = true
  queue.addLast(get(source))

  // TODO : Handle negative cycles and throw an exception when one is found. This will be the case
  //        when the number of relaxations exceeds a certain threshold.
  while (queue.size > 0) {
    val v1 = get(queue.removeFirst())
    enqueued[get(v1)] = false
    forEachNeighbor(v1) { v2 ->
      val weight = weight(v1, v2)
      val d1 = distances[get(v1)]
      val d2 = distances[get(v2)]
      if (d1 != Int.MAX_VALUE && (d2 == Int.MAX_VALUE || d1 + weight < d2)) {
        distances[get(v2)] = d1 + weight
        if (!enqueued[get(v2)]) {
          enqueued[get(v2)] = true
          queue.addLast(get(v2))
        }
      }
    }
  }

  return distances
}
