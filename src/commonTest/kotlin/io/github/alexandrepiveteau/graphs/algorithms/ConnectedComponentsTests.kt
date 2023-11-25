package io.github.alexandrepiveteau.graphs.algorithms

import io.github.alexandrepiveteau.graphs.UndirectedGraph
import io.github.alexandrepiveteau.graphs.builder.buildUndirectedGraph
import io.github.alexandrepiveteau.graphs.complete
import io.github.alexandrepiveteau.graphs.empty
import io.github.alexandrepiveteau.graphs.util.Repeats
import kotlin.test.Test
import kotlin.test.assertEquals

class ConnectedComponentsTests {

  @Test
  fun emptyGraphHasNoCc() {
    val graph = UndirectedGraph.empty()
    val (scc, map) = graph.connectedComponents()
    assertEquals(0, scc.size)
    assertEquals(0, map.size)
  }

  @Test
  fun completeGraphHasOneCc() {
    for (count in 1..Repeats) {
      val graph = UndirectedGraph.complete(count)
      val (scc, map) = graph.connectedComponents()
      assertEquals(1, scc.size)
      repeat(count) { assertEquals(scc.vertex(0), map[graph.vertex(it)]) }
    }
  }

  @Test
  fun disjointGraphWithNVerticesHasNCc() {
    for (count in 1..Repeats) {
      val graph = buildUndirectedGraph { repeat(count) { addVertex() } }
      val (scc, map) = graph.connectedComponents()
      assertEquals(count, scc.size)
      repeat(count) { assertEquals(scc.vertex(it), map[graph.vertex(it)]) }
    }
  }
}
