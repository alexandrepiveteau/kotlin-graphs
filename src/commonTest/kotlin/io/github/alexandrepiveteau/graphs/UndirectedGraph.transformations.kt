package io.github.alexandrepiveteau.graphs

import io.github.alexandrepiveteau.graphs.util.Repeats
import kotlin.test.Test
import kotlin.test.assertEquals

class SCCTests {

  @Test
  fun emptyGraphHasNoScc() {
    val graph = UndirectedGraph.empty()
    val (scc, map) = graph.scc()
    assertEquals(0, scc.size)
    assertEquals(0, map.size)
  }

  @Test
  fun completeGraphHasOneScc() {
    for (count in 1..Repeats) {
      val graph = UndirectedGraph.complete(count)
      val (scc, map) = graph.scc()
      assertEquals(1, scc.size)
      repeat(count) { assertEquals(scc[0], map[graph[it]]) }
    }
  }

  @Test
  fun disjointGraphWithNVerticesHasNScc() {
    for (count in 1..Repeats) {
      val graph = buildUndirectedGraph { repeat(count) { addVertex() } }
      val (scc, map) = graph.scc()
      assertEquals(count, scc.size)
      repeat(count) { assertEquals(scc[it], map[graph[it]]) }
    }
  }
}
