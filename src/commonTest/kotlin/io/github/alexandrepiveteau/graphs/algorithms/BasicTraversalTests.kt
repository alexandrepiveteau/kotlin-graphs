package io.github.alexandrepiveteau.graphs.algorithms

import io.github.alexandrepiveteau.graphs.UndirectedGraph
import io.github.alexandrepiveteau.graphs.complete
import io.github.alexandrepiveteau.graphs.util.Repeats
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class BasicTraversalTests {

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
}
