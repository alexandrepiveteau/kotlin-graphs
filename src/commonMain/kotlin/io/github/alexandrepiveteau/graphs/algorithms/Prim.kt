@file:JvmName("Algorithms")
@file:JvmMultifileClass

package io.github.alexandrepiveteau.graphs.algorithms

import io.github.alexandrepiveteau.graphs.*
import io.github.alexandrepiveteau.graphs.internal.collections.IntMinPriorityQueue
import kotlin.jvm.JvmMultifileClass
import kotlin.jvm.JvmName

private fun UndirectedNetwork.primParents(from: Vertex): VertexMap {
  if (size == 0) return VertexMap(0)

  val parents = VertexMap(size) { Vertex.Invalid }
  val queue = IntMinPriorityQueue(size)
  val visited = BooleanArray(size) { false }

  queue[get(from)] = 0
  while (queue.size > 0) {
    val v1 = get(queue.remove())
    visited[get(v1)] = true
    forEachNeighbor(v1) { v2, weight ->
      if (!visited[get(v2)]) {
        val d = queue[get(v2)]
        if (weight < d) {
          parents[v2] = v1
          queue[get(v2)] = weight
        }
      }
    }
  }

  return parents
}

public fun UndirectedNetwork.minimumSpanningTree(from: Vertex): DirectedNetwork {
  if (from !in this) throw NoSuchVertexException()
  return computeNetwork(primParents(from))
}
