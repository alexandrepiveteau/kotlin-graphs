package io.github.alexandrepiveteau.graphs.algorithms

import io.github.alexandrepiveteau.graphs.*
import io.github.alexandrepiveteau.graphs.builder.buildDirectedNetwork
import io.github.alexandrepiveteau.graphs.builder.buildUndirectedNetwork
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
  fun spfaOnSingletonAssociatesDistanceZero() {
    val graph = buildUndirectedNetwork { addVertex() }
    val expected = buildDirectedNetwork { addVertex() }
    val spfa = graph.shortestPathFasterAlgorithm(graph[0])
    assertEquals(expected, spfa)
  }

  @Test
  fun spfaOnCompleteGraphAssociatesDistanceOneExceptForSource() {
    for (count in 1 until Repeats) {
      val graph = UndirectedNetwork.complete(count, 1)
      val expected = buildDirectedNetwork {
        val from = addVertex()
        repeat(count - 1) { addArc(from arcTo addVertex(), 1) }
      }
      val spfa = graph.shortestPathFasterAlgorithm(graph[0])
      assertEquals(expected, spfa)
    }
  }

  @Test
  fun spfaComputesMinimumDistanceOnSimpleGraph() {
    val graph = buildUndirectedNetwork {
      val (a, b, c, d, e) = addVertices()
      addEdge(a edgeTo b, 1)
      addEdge(b edgeTo c, 1)
      addEdge(c edgeTo d, 1)
      addEdge(d edgeTo e, 1)
      addEdge(e edgeTo a, 5)
    }
    val expected = buildDirectedNetwork {
      val (a, b, c, d, e) = addVertices()
      addArc(a arcTo b, 1)
      addArc(b arcTo c, 1)
      addArc(c arcTo d, 1)
      addArc(d arcTo e, 1)
    }

    val spfa = graph.shortestPathFasterAlgorithm(graph[0])
    assertEquals(expected, spfa)
  }
}
