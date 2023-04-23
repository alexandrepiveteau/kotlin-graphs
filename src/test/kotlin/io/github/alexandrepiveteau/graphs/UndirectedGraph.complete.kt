package io.github.alexandrepiveteau.graphs

import io.github.alexandrepiveteau.graphs.util.Repeats
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse

class UndirectedEmptyGraphTests {

  @Test
  fun `empty graph has no vertices nor edges`() {
    val graph = UndirectedGraph.empty()

    assertEquals(0, graph.size)
    assertFalse(graph.contains(Vertex(0)))
    assertFalse(graph.contains(Vertex(0) edgeTo Vertex(1)))
    assertFailsWith<IndexOutOfBoundsException> { graph[0] }
    assertFailsWith<NoSuchVertexException> { graph[Vertex(0)] }
    assertFailsWith<IndexOutOfBoundsException> { graph.neighborsSize(0) }
    assertFailsWith<IndexOutOfBoundsException> { graph.neighbor(0, 0) }
  }
}

class UndirectedCompleteGraphTests {

  @Test
  fun `complete graph has non-zero vertex count`() {
    assertFailsWith<IllegalArgumentException> { UndirectedGraph.complete(-1) }
  }

  @Test
  fun `complete graph has all edges`() {
    for (count in 0..Repeats) {
      val graph = UndirectedGraph.complete(count)
      for (i in 0 until count) {
        for (j in 0 until count) {
          if (i != j) {
            val u = graph[i]
            val v = graph[j]
            assertEquals(true, graph.contains(u edgeTo v))
          }
        }
      }
    }
  }

  @Test
  fun `complete graph has no self-edges`() {
    for (count in 0..Repeats) {
      val graph = UndirectedGraph.complete(count)
      for (i in 0 until count) {
        val v = graph[i]
        assertEquals(false, graph.contains(v edgeTo v))
      }
    }
  }
}
