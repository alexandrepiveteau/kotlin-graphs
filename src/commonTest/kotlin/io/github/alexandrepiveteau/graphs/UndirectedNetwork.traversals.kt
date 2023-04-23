package io.github.alexandrepiveteau.graphs

import io.github.alexandrepiveteau.graphs.util.Repeats
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class UndirectedNetworkTraversalTests {

  @Test
  fun spfaOnEmptyNetworkThrowsException() {
    assertFailsWith<NoSuchVertexException> {
      UndirectedNetwork.empty().shortestPathFasterAlgorithm(Vertex(0))
    }
  }

  @Test
  fun spfaOnSingletonAssociatesDistanceZero() {
    val graph = buildUndirectedNetwork { addVertex() }
    val spfa = graph.shortestPathFasterAlgorithm(graph[0])
    assertEquals(0, spfa[0])
  }

  @Test
  fun spfaOnCompleteGraphAssociatesDistanceOneExceptForSource() {
    for (count in 1 until Repeats) {
      val graph = UndirectedNetwork.complete(count, 1)
      val spfa = graph.shortestPathFasterAlgorithm(graph[0])
      assertEquals(0, spfa[0])
      for (i in 1 until count) {
        assertEquals(1, spfa[i])
      }
    }
  }

  @Test
  fun spfaComputesMinimumDistanceOnSimpleGraph() {
    val graph = buildUndirectedNetwork {
      val (a, b, c, d, e) = addVertices()
      addEdge(a edgeTo b, 1)
      addEdge(b edgeTo c, 1)
      addEdge(a edgeTo d, 4)
      addEdge(c edgeTo e, 1)
      addEdge(d edgeTo e, 1)
    }
    val spfa = graph.shortestPathFasterAlgorithm(graph[0])
    assertEquals(0, spfa[0])
    assertEquals(1, spfa[1])
    assertEquals(2, spfa[2])
    assertEquals(4, spfa[3])
    assertEquals(3, spfa[4])
  }
}
