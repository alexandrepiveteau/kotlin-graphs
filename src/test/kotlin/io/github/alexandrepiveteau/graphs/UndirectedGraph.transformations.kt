package io.github.alexandrepiveteau.graphs

import kotlin.test.Test
import kotlin.test.assertEquals

class SCCTests {

  @Test
  fun `empty graph has no scc`() {
    val graph = UndirectedGraph.empty()
    val (scc, map) = graph.scc()
    assertEquals(0, scc.size)
    assertEquals(0, map.size)
  }

  @Test
  fun `complete graph has one scc`() {
    for (count in 1..Repeats) {
      val graph = UndirectedGraph.complete(count)
      val (scc, map) = graph.scc()
      assertEquals(1, scc.size)
      repeat(count) { assertEquals(scc[0], map[graph[it]]) }
    }
  }

  @Test
  fun `disjoint graph with n vertices has n scc`() {
    for (count in 1..Repeats) {
      val graph = buildUndirectedGraph { repeat(count) { addVertex() } }
      val (scc, map) = graph.scc()
      assertEquals(count, scc.size)
      repeat(count) { assertEquals(scc[it], map[graph[it]]) }
    }
  }
}
