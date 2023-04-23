package io.github.alexandrepiveteau.graphs

import io.github.alexandrepiveteau.graphs.util.Repeats
import kotlin.test.Test
import kotlin.test.assertEquals

class DirectedGraphSCCTests {

  @Test
  fun emptyGraphHasNoScc() {
    val graph = buildDirectedGraph {}
    val (scc, map) = graph.stronglyConnectedComponentsKosaraju()

    assertEquals(0, scc.size)
    assertEquals(0, map.size)
  }

  @Test
  fun singletonGraphHasOneScc() {
    val graph = buildDirectedGraph { addVertex() }
    val (scc, map) = graph.stronglyConnectedComponentsKosaraju()

    assertEquals(1, scc.size)
    assertEquals(1, map.size)
    assertEquals(map[scc[0]], graph[0])
  }

  @Test
  fun lineGraphHasOneSccPerVertex() {
    for (count in 1 until Repeats) {
      val graph = buildDirectedGraph {
        val vertices = VertexArray(count) { addVertex() }
        repeat(count - 1) { addArc(vertices[it] arcTo vertices[it + 1]) }
      }
      val (scc, map) = graph.stronglyConnectedComponentsKosaraju()

      assertEquals(count, scc.size)
      assertEquals(count, map.size)
      assertEquals(count, map.values().asIntArray().asSequence().distinct().count())
    }
  }

  @Test
  fun disjointGraphHasOneSccPerVertex() {
    for (count in 1 until Repeats) {
      val graph = buildDirectedGraph { repeat(count) { addVertex() } }
      val (scc, map) = graph.stronglyConnectedComponentsKosaraju()

      assertEquals(count, scc.size)
      assertEquals(count, map.size)
      assertEquals(count, map.values().asIntArray().asSequence().distinct().count())
    }
  }

  @Test
  fun cycleHasOneSccForWholeGraph() {
    for (count in 1 until Repeats) {
      val graph = buildDirectedGraph {
        val vertices = VertexArray(count) { addVertex() }
        repeat(count) { addArc(vertices[it] arcTo vertices[(it + 1) % count]) }
      }
      val (scc, map) = graph.stronglyConnectedComponentsKosaraju()

      assertEquals(1, scc.size)
      assertEquals(count, map.size)
      assertEquals(setOf(scc[0].index), map.values().asIntArray().asSequence().distinct().toSet())
    }
  }

  @Test
  fun twoCyclesHaveTwoSccForWholeGraph() {
    for (count in 1 until Repeats) {
      val graph = buildDirectedGraph {
        val loop1 = VertexArray(count) { addVertex() }
        val loop2 = VertexArray(count) { addVertex() }
        repeat(count) { addArc(loop1[it] arcTo loop1[(it + 1) % count]) }
        repeat(count) { addArc(loop2[it] arcTo loop2[(it + 1) % count]) }
      }
      val (scc, map) = graph.stronglyConnectedComponentsKosaraju()

      assertEquals(2, scc.size)
      assertEquals(2 * count, map.size)
      assertEquals(
          mapOf(scc[0].index to count, scc[1].index to count),
          map.values().asIntArray().asSequence().groupingBy { it }.eachCount(),
      )
    }
  }
}
