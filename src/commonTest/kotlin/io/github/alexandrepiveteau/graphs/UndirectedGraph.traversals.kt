package io.github.alexandrepiveteau.graphs

import io.github.alexandrepiveteau.graphs.util.Repeats
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class UndirectedGraphDFSTests {

  @Test
  fun forEachVertexIteratesExactlyOnceOverEachVertex() {
    for (count in 0..Repeats) {
      val graph = UndirectedGraph.complete(count)
      val visited = BooleanArray(count)
      graph.forEachVertex {
        assertFalse(visited[it.index])
        visited[it.index] = true
      }
      assertEquals(true, visited.all { it })
    }
  }

  @Test
  fun bfsOnSingletonGraphVisitsOneVertex() {
    val graph = UndirectedGraph.complete(1)
    var visited = 0
    graph.forEachVertexBreadthFirst(graph[0]) { visited++ }
    assertEquals(1, visited)
  }

  @Test
  fun bfsOnLineGraphVisitsAllVertices() {
    for (count in 1..Repeats) {
      val graph = buildUndirectedGraph {
        var last = addVertex()
        for (i in 1 until count) {
          val next = addVertex()
          addEdge(last edgeTo next)
          last = next
        }
      }
      val visited = BooleanArray(count)
      graph.forEachVertexBreadthFirst(graph[0]) { visited[it.index] = true }
      assertEquals(true, visited.all { it })
    }
  }

  @Test
  fun dfsOnSingletonGraphVisitsOneVertex() {
    val graph = UndirectedGraph.complete(1)
    var visited = 0
    graph.forEachVertexDepthFirst(graph[0]) { visited++ }
    assertEquals(1, visited)
  }

  @Test
  fun dfsOnLineGraphVisitsAllVertices() {
    for (count in 1..Repeats) {
      val graph = buildUndirectedGraph {
        var last = addVertex()
        for (i in 1 until count) {
          val next = addVertex()
          addEdge(last edgeTo next)
          last = next
        }
      }
      val visited = BooleanArray(count)
      graph.forEachVertexDepthFirst(graph[0]) { visited[it.index] = true }
      assertEquals(true, visited.all { it })
    }
  }
}
