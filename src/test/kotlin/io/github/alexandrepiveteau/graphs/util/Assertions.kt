package io.github.alexandrepiveteau.graphs.util

import io.github.alexandrepiveteau.graphs.Graph
import kotlin.test.assertEquals

/**
 * Asserts that two graphs are strictly equal, i.e. that they have the same size, and that they
 * contain the same vertices and edges with the same indices.
 *
 * @param expected the expected graph.
 * @param actual the actual graph.
 */
fun assertEquals(expected: Graph, actual: Graph) {
  assertEquals(expected.size, actual.size)
  for (i in 0 until expected.size) {
    assertEquals(expected.neighborsSize(i), actual.neighborsSize(i))
    for (j in 0 until expected.neighborsSize(i)) {
      assertEquals(expected.neighbor(i, j), actual.neighbor(i, j))
    }
  }
}
