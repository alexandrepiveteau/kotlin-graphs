package io.github.alexandrepiveteau.graphs

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class UndirectedEmptyGraphTests {

  @Test
  fun `empty graph has no vertices`() {
    val graph = UndirectedGraph.empty()
    assertEquals(0, graph.size)
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
            assertEquals(true, graph.contains(Edge(u, v)))
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
        assertEquals(false, graph.contains(Edge(v, v)))
      }
    }
  }
}
