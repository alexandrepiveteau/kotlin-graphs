@file:JvmName("Algorithms")
@file:JvmMultifileClass

package io.github.alexandrepiveteau.graphs.algorithms

import io.github.alexandrepiveteau.graphs.UndirectedNetwork
import io.github.alexandrepiveteau.graphs.Vertex
import io.github.alexandrepiveteau.graphs.VertexMap
import io.github.alexandrepiveteau.graphs.builder.buildUndirectedNetwork
import io.github.alexandrepiveteau.graphs.edgeTo
import io.github.alexandrepiveteau.graphs.internal.collections.IntMinPriorityQueue
import kotlin.jvm.JvmMultifileClass
import kotlin.jvm.JvmName

/**
 * Returns an [UndirectedNetwork] corresponding to the minimum spanning forest of the current
 * [UndirectedNetwork].
 *
 * @return an [UndirectedNetwork] corresponding to the minimum spanning forest of the current
 * @receiver the [UndirectedNetwork] to compute the minimum spanning forest from.
 */
public fun UndirectedNetwork.minimumSpanningForest(): UndirectedNetwork = buildUndirectedNetwork {
  // Create all vertices in the resulting forest.
  repeat(size) { addVertex() }

  // Create the state for the Prim algorithm.
  val parents = VertexMap(size) { Vertex.Invalid }
  val weights = IntArray(size) { Int.MAX_VALUE }
  val queue = IntMinPriorityQueue(size)
  val visited = BooleanArray(size) { false }

  // Iterate over each tree in the forest.
  forEachVertex { start ->
    if (!visited[get(start)]) {
      queue[get(start)] = 0
      while (queue.size > 0) {
        val v1 = get(queue.remove())
        visited[get(v1)] = true
        if (parents[v1] != Vertex.Invalid) {
          addEdge(v1 edgeTo parents[v1], weights[get(v1)])
        }
        forEachNeighbor(v1) { v2, weight ->
          if (!visited[get(v2)]) {
            val d = if (queue.contains(get(v2))) queue[get(v2)] else Int.MAX_VALUE
            if (weight < d) {
              parents[v2] = v1
              weights[get(v2)] = weight
              queue[get(v2)] = weight
            }
          }
        }
      }
    }
  }
}
