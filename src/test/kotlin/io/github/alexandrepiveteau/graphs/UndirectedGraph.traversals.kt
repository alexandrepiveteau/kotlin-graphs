package io.github.alexandrepiveteau.graphs

import kotlin.test.Test
import kotlin.test.assertEquals

class UndirectedGraphDFSTests {

  @Test
  fun `dfs on singleton graph visits one vertex`() {
    val graph = UndirectedGraph.complete(1)
    var visited = 0
    graph.forEachVertexDepthFirst(graph[0]) { visited++ }
    assertEquals(1, visited)
  }

  @Test
  fun `dfs on line graph visits all vertices`() {
    for (count in 1..Repeats) {
      val graph = buildUndirectedGraph {
        var last = addVertex()
        for (i in 1 until count) {
          val next = addVertex()
          addEdge(Edge(last, next))
          last = next
        }
      }
      val visited = BooleanArray(count)
      graph.forEachVertexDepthFirst(graph[0]) { visited[it.index] = true }
      assertEquals(true, visited.all { it })
    }
  }
}
