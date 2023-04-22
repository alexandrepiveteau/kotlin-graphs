package io.github.alexandrepiveteau.graphs

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class UndirectedNetworkTraversalTests {

  @Test
  fun `spfa on empty network throws exception`() {
    assertFailsWith<NoSuchVertexException> { UndirectedNetwork.empty().spfa(Vertex(0)) }
  }

  @Test
  fun `spfa on singleton associates distance zero`() {
    val graph = buildUndirectedNetwork { addVertex() }
    val spfa = graph.spfa(graph[0])
    assertEquals(0, spfa[0])
  }

  @Test
  fun `spfa on complete graph associates distance one except for source`() {
    for (count in 1 until Repeats) {
      val graph = UndirectedNetwork.complete(count, 1)
      val spfa = graph.spfa(graph[0])
      assertEquals(0, spfa[0])
      for (i in 1 until count) {
        assertEquals(1, spfa[i])
      }
    }
  }
}
