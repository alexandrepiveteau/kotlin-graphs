package io.github.alexandrepiveteau.graphs.algorithms

import io.github.alexandrepiveteau.graphs.Vertex
import io.github.alexandrepiveteau.graphs.VertexArray
import io.github.alexandrepiveteau.graphs.arcTo
import io.github.alexandrepiveteau.graphs.builder.buildDirectedGraph
import io.github.alexandrepiveteau.graphs.toVertexArray
import io.github.alexandrepiveteau.graphs.util.Repeats
import io.github.alexandrepiveteau.graphs.util.assertContentEquals
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class TopologicalSortTests {

  @Test
  fun emptyGraphHasEmptyTopologicalSort() {
    val graph = buildDirectedGraph {}
    val order = graph.topologicalSort()
    assertContentEquals(VertexArray(0), order)
  }

  @Test
  fun singletonGraphHasSingletonTopologicalSort() {
    val graph = buildDirectedGraph { addVertex() }
    val order = graph.topologicalSort()
    assertContentEquals(VertexArray(1) { Vertex(0) }, order)
  }

  @Test
  fun loopHasNoTopologicalSort() {
    val graph = buildDirectedGraph {
      val a = addVertex()
      addArc(a arcTo a)
    }
    assertFailsWith<IllegalArgumentException> { graph.topologicalSort() }
  }

  @Test
  fun bidirectionalGraphHasNoTopologicalSort() {
    val graph = buildDirectedGraph {
      val (a, b) = addVertices()
      addArc(a arcTo b)
      addArc(b arcTo a)
    }
    assertFailsWith<IllegalArgumentException> { graph.topologicalSort() }
  }

  @Test
  fun disjointGraphHasTopologicalSort() {
    for (count in 0 until Repeats) {
      val graph = buildDirectedGraph { repeat(count) { addVertex() } }
      val order = graph.topologicalSort()
      assertEquals(graph.size, order.size)
    }
  }

  @Test
  fun lineGraphHasIdenticalTopologicalSort() {
    for (count in 2 until Repeats) {
      val graph = buildDirectedGraph {
        val vertices = VertexArray(count) { addVertex() }
        repeat(count - 1) { addArc(vertices[it] arcTo vertices[it + 1]) }
      }
      val order = graph.topologicalSort()
      assertContentEquals(VertexArray(graph.size) { graph[it] }, order)
    }
  }

  @Test
  fun geeksForGeeks() {
    val graph = buildDirectedGraph {
      val (v0, v1, v2, v3, v4, v5) = addVertices()
      addArc(v5 arcTo v2)
      addArc(v5 arcTo v0)
      addArc(v4 arcTo v0)
      addArc(v4 arcTo v1)
      addArc(v2 arcTo v3)
      addArc(v3 arcTo v1)
    }
    val order = graph.topologicalSort()
    val expected = arrayOf(graph[4], graph[5], graph[0], graph[2], graph[3], graph[1])
    assertContentEquals(expected.toVertexArray(), order)
  }
}
