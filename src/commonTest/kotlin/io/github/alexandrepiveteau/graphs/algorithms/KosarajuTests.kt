package io.github.alexandrepiveteau.graphs.algorithms

import io.github.alexandrepiveteau.graphs.DirectedGraph
import io.github.alexandrepiveteau.graphs.VertexArray
import io.github.alexandrepiveteau.graphs.arcTo
import io.github.alexandrepiveteau.graphs.asIntArray
import io.github.alexandrepiveteau.graphs.builder.buildDirectedGraph
import io.github.alexandrepiveteau.graphs.builder.erdosRenyi
import io.github.alexandrepiveteau.graphs.util.Repeats
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class KosarajuTests {

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

  @Test
  fun geeksForGeeksExample() {
    // https://www.geeksforgeeks.org/strongly-connected-components/
    val graph = buildDirectedGraph {
      val (v0, v1, v2, v3, v4) = addVertices()
      addArc(v0 arcTo v2)
      addArc(v2 arcTo v1)
      addArc(v1 arcTo v0)
      addArc(v0 arcTo v3)
      addArc(v3 arcTo v4)
    }
    val (scc, map) = graph.stronglyConnectedComponentsKosaraju()

    assertEquals(3, scc.size)
    assertEquals(5, map.size)

    // First SCC (v0, v1, v2).
    assertEquals(scc[map[graph[0]]], scc[map[graph[1]]])
    assertEquals(scc[map[graph[1]]], scc[map[graph[2]]])
    assertEquals(scc[map[graph[0]]], scc[map[graph[2]]])

    // Second SCC (v3).
    assertNotEquals(scc[map[graph[0]]], scc[map[graph[3]]])
    assertNotEquals(scc[map[graph[1]]], scc[map[graph[3]]])
    assertNotEquals(scc[map[graph[2]]], scc[map[graph[3]]])
    assertNotEquals(scc[map[graph[4]]], scc[map[graph[3]]])

    // Third SCC (v4).
    assertNotEquals(scc[map[graph[0]]], scc[map[graph[4]]])
    assertNotEquals(scc[map[graph[1]]], scc[map[graph[4]]])
    assertNotEquals(scc[map[graph[2]]], scc[map[graph[4]]])
    assertNotEquals(scc[map[graph[3]]], scc[map[graph[4]]])
  }

  @Test
  fun randomGraphsHaveTopologicalSortAfterScc() {
    val random = Random(42)
    repeat(Repeats) {
      val graph = DirectedGraph.erdosRenyi(n = 100, p = 0.1, random = random)
      val (scc, map) = graph.stronglyConnectedComponentsKosaraju()
      val order = scc.topologicalSort()
      assertEquals(map.values().asIntArray().distinct().count(), order.size)
    }
  }
}
