package io.github.alexandrepiveteau.graphs.algorithms

import io.github.alexandrepiveteau.graphs.UndirectedGraph
import io.github.alexandrepiveteau.graphs.buildUndirectedGraph
import io.github.alexandrepiveteau.graphs.complete
import io.github.alexandrepiveteau.graphs.edgeTo
import io.github.alexandrepiveteau.graphs.util.Repeats
import kotlin.test.Test
import kotlin.test.assertEquals

class BreadthFirstTraversalTests {

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
}
