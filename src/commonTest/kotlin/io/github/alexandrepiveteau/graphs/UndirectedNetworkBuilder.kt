package io.github.alexandrepiveteau.graphs

import kotlin.test.Test
import kotlin.test.assertEquals

class UndirectedNetworkBuilderTests {

  @Test
  fun emptyBuilderCreatesEmptyNetwork() {
    val network = buildUndirectedNetwork {}
    assertEquals(0, network.size)
  }

  @Test
  fun builderWithOneVertexCreatesSingletonNetwork() {
    val network = buildUndirectedNetwork { addVertex() }
    assertEquals(1, network.size)
  }

  @Test
  fun builderWithDuplicateEdgeAddsWeights() {
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
  fun builderWithDuplicateEdgesWithNegativeSum() {
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
