package io.github.alexandrepiveteau.graphs.algorithms

import io.github.alexandrepiveteau.graphs.builder.buildUndirectedNetwork
import io.github.alexandrepiveteau.graphs.edgeTo
import io.github.alexandrepiveteau.graphs.util.Repeats

object MinimumSpanningForestTestCases {

  // https://www.javatpoint.com/prims-algorithm-java
  private val JavaTPoint = buildUndirectedNetwork {
    val (v1, v2, v3, v4, v5, v6, v7) = addVertices()
    addEdge(v1 edgeTo v2, 28)
    addEdge(v1 edgeTo v6, 10)
    addEdge(v2 edgeTo v3, 16)
    addEdge(v2 edgeTo v7, 14)
    addEdge(v3 edgeTo v4, 12)
    addEdge(v4 edgeTo v5, 22)
    addEdge(v4 edgeTo v7, 18)
    addEdge(v5 edgeTo v6, 25)
    addEdge(v5 edgeTo v7, 24)
  }
  private val JavaTPointSolution = buildUndirectedNetwork {
    val (v1, v2, v3, v4, v5, v6, v7) = addVertices()
    addEdge(v1 edgeTo v6, 10)
    addEdge(v5 edgeTo v6, 25)
    addEdge(v4 edgeTo v5, 22)
    addEdge(v3 edgeTo v4, 12)
    addEdge(v2 edgeTo v3, 16)
    addEdge(v2 edgeTo v7, 14)
  }

  private fun disjoint() = sequence {
    for (count in 0 until Repeats) {
      val graph = buildUndirectedNetwork { repeat(count) { addVertex() } }
      val expected = buildUndirectedNetwork { repeat(count) { addVertex() } }
      yield(graph to expected)
    }
  }

  /** Returns a [Sequence] of all the test cases. */
  fun allForests() =
      sequenceOf(
          JavaTPoint to JavaTPointSolution,
      ) + disjoint()
}
