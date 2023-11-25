package io.github.alexandrepiveteau.graphs.algorithms

import io.github.alexandrepiveteau.graphs.*
import io.github.alexandrepiveteau.graphs.builder.buildDirectedNetwork
import io.github.alexandrepiveteau.graphs.builder.buildUndirectedNetwork
import io.github.alexandrepiveteau.graphs.util.Repeats
import io.github.alexandrepiveteau.graphs.util.assertEquals
import kotlin.test.Test
import kotlin.test.assertFailsWith

class DijkstraTests {

  @Test
  fun emptyNetworkFailsOnShortestPath() {
    assertFailsWith<NoSuchVertexException> {
      UndirectedNetwork.empty().shortestPathDijkstra(Vertex.Invalid)
    }
  }

  @Test
  fun singletonNetworkReturnsItself() {
    val network = buildUndirectedNetwork { addVertex() }
    val expected = buildUndirectedNetwork { addVertex() }
    val dijkstra = network.shortestPathDijkstra(network.vertex(0))
    assertEquals(expected, dijkstra)
  }

  @Test
  fun respectsArcsDirections() {
    var a: Vertex
    val network = buildDirectedNetwork {
      a = addVertex()
      val b = addVertex()
      addArc(b arcTo a, 1)
    }
    val expected = buildDirectedNetwork {
      addVertex()
      addVertex()
    }
    val dijkstra = network.shortestPathDijkstra(a)
    assertEquals(expected, dijkstra)
  }

  @Test
  fun directedEdge() {
    var aN: Vertex
    var bN: Vertex
    val network = buildDirectedNetwork {
      aN = addVertex()
      bN = addVertex()
      addArc(aN arcTo bN, 1)
    }

    var aE: Vertex
    var bE: Vertex
    val expected = buildDirectedNetwork {
      aE = addVertex()
      bE = addVertex()
      addArc(aE arcTo bE, 1)
    }
    val dijkstra = network.shortestPathDijkstra(aN)
    assertEquals(expected, dijkstra)
  }

  @Test
  fun testCasesPositive() {
    for ((graph, from, expected) in ShortestPathTestCases.positiveWeights()) {
      val dijkstra = graph.shortestPathDijkstra(from)
      assertEquals(expected, dijkstra)
    }
  }

  @Test
  fun testCasesNegative() {
    for ((graph, from, _) in ShortestPathTestCases.negativeWeights()) {
      assertFailsWith<IllegalArgumentException> { graph.shortestPathDijkstra(from) }
    }
  }

  @Test
  fun complete() {
    for (count in 1 until Repeats) {
      val network = buildUndirectedNetwork {
        val vertices = VertexArray(count) { addVertex() }
        for (i in 0 until count) {
          for (j in i + 1 until count) {
            addEdge(vertices[i] edgeTo vertices[j], 1)
          }
        }
      }
      for (start in 0 until count) {
        val expected = buildDirectedNetwork {
          val vertices = VertexArray(count) { addVertex() }
          for (i in 0 until count) {
            if (i != start) addArc(vertices[start] arcTo vertices[i], 1)
          }
        }
        val dijkstra = network.shortestPathDijkstra(network.vertex(start))
        assertEquals(expected, dijkstra)
      }
    }
  }
}
