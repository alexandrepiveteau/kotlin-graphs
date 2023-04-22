package io.github.alexandrepiveteau.graphs

import kotlin.test.Test
import kotlin.test.assertEquals

class UndirectedNetworkBuilderTests {

  @Test
  fun `empty builder creates empty network`() {
    val network = buildUndirectedNetwork {}
    assertEquals(0, network.size)
  }

  @Test
  fun `builder with one vertex creates singleton network`() {
    val network = buildUndirectedNetwork { addVertex() }
    assertEquals(1, network.size)
  }

  @Test
  fun `builder with duplicate edge adds weights`() {
    val network = buildUndirectedNetwork {
      val (a, b) = addVertices()
      addEdge(a edgeTo b, 1)
      addEdge(a edgeTo b, 2)
    }
    val a = network[0]
    val b = network[1]
    assertEquals(3, network.weight(a, b))
  }

  @Test
  fun `builder with duplicate edges with negative sum`() {
    val network = buildUndirectedNetwork {
      val (a, b) = addVertices()
      addEdge(a edgeTo b, 1)
      addEdge(a edgeTo b, -2)
    }
    val a = network[0]
    val b = network[1]
    assertEquals(-1, network.weight(a, b))
  }
}
