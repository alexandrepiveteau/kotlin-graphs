package io.github.alexandrepiveteau.graphs.builder

import io.github.alexandrepiveteau.graphs.Vertex
import io.github.alexandrepiveteau.graphs.edgeTo
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
    var a: Vertex
    var b: Vertex
    val network = buildUndirectedNetwork {
      a = addVertex()
      b = addVertex()

      addEdge(a edgeTo b, 1)
      addEdge(a edgeTo b, 2)
    }

    assertEquals(3, network.weight(a, b))
  }

  @Test
  fun builderWithDuplicateEdgesWithNegativeSum() {
    var a: Vertex
    var b: Vertex
    val network = buildUndirectedNetwork {
      a = addVertex()
      b = addVertex()

      addEdge(a edgeTo b, 1)
      addEdge(a edgeTo b, -2)
    }
    assertEquals(-1, network.weight(a, b))
  }
}
