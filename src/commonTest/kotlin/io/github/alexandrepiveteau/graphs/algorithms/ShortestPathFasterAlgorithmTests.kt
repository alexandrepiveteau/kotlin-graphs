package io.github.alexandrepiveteau.graphs.algorithms

import io.github.alexandrepiveteau.graphs.*
import io.github.alexandrepiveteau.graphs.builder.buildDirectedNetwork
import io.github.alexandrepiveteau.graphs.util.Repeats
import io.github.alexandrepiveteau.graphs.util.assertEquals
import kotlin.test.Test
import kotlin.test.assertFailsWith

class ShortestPathFasterAlgorithmTests {

  @Test
  fun spfaOnEmptyNetworkThrowsException() {
    assertFailsWith<NoSuchVertexException> {
      UndirectedNetwork.empty().shortestPathFasterAlgorithm(Vertex(0))
    }
  }

  @Test
  fun spfaOnCompleteGraphAssociatesDistanceOneExceptForSource() {
    for (count in 1 until Repeats) {
      val graph = UndirectedNetwork.complete(count, 1)
      val expected = buildDirectedNetwork {
        val from = addVertex()
        repeat(count - 1) { addArc(from arcTo addVertex(), 1) }
      }
      val spfa = graph.shortestPathFasterAlgorithm(graph.vertex(0))
      assertEquals(expected, spfa)
    }
  }

  @Test
  fun testCases() {
    for ((graph, from, expected) in ShortestPathTestCases.allWeights()) {
      val spfa = graph.shortestPathFasterAlgorithm(from)
      assertEquals(expected, spfa)
    }
  }
}
