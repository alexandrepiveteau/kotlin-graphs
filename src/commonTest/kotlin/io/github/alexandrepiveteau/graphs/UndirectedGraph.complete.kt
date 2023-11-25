package io.github.alexandrepiveteau.graphs

import io.github.alexandrepiveteau.graphs.util.Repeats
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse

class UndirectedEmptyGraphTests {

  @Test
  fun emptyGraphHasNoVerticesNorEdges() {
    val graph = UndirectedGraph.empty()

    assertEquals(0, graph.size)
    assertFalse(graph.contains(Vertex(0)))
    assertFalse(graph.contains(Vertex(0) edgeTo Vertex(1)))
    assertFailsWith<IndexOutOfBoundsException> { graph.vertex(0) }
    assertFailsWith<NoSuchVertexException> { graph.index(Vertex(0)) }
    assertFailsWith<IndexOutOfBoundsException> { graph.successorsSize(0) }
    assertFailsWith<IndexOutOfBoundsException> { graph.successor(0, 0) }
  }
}

class UndirectedCompleteGraphTests {

  @Test
  fun completeGraphHasNonZeroVertexCount() {
    assertFailsWith<IllegalArgumentException> { UndirectedGraph.complete(-1) }
  }

  @Test
  fun completeGraphHasAllEdges() {
    for (count in 0..Repeats) {
      val graph = UndirectedGraph.complete(count)
      for (i in 0 until count) {
        for (j in 0 until count) {
          if (i != j) {
            val u = graph.vertex(i)
            val v = graph.vertex(j)
            assertEquals(true, graph.contains(u edgeTo v))
          }
        }
      }
    }
  }

  @Test
  fun completeGraphHasNoSelfEdges() {
    for (count in 0..Repeats) {
      val graph = UndirectedGraph.complete(count)
      for (i in 0 until count) {
        val v = graph.vertex(i)
        assertEquals(false, graph.contains(v edgeTo v))
      }
    }
  }
}
