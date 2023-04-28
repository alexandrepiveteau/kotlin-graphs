package io.github.alexandrepiveteau.graphs.algorithms

import io.github.alexandrepiveteau.graphs.util.assertEquals
import kotlin.test.Test

class PrimTests {

  @Test
  fun testCases() {
    for ((graph, expected) in MinimumSpanningForestTestCases.allForests()) {
      val prim = graph.minimumSpanningForest()
      assertEquals(expected, prim)
    }
  }
}
