package io.github.alexandrepiveteau.graphs.util

import io.github.alexandrepiveteau.graphs.DirectedNetwork
import io.github.alexandrepiveteau.graphs.Graph
import io.github.alexandrepiveteau.graphs.Network
import io.github.alexandrepiveteau.graphs.Vertex
import io.github.alexandrepiveteau.graphs.algorithms.forEachArc
import kotlin.test.assertEquals
import io.github.alexandrepiveteau.graphs.util.assertEquals as assertEqualsGraph

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
    assertEquals(expected.neighborsSize(i), actual.neighborsSize(i), "neighbors size different: $i")
    for (j in 0 until expected.neighborsSize(i)) {
      assertEquals(expected.neighbor(i, j), actual.neighbor(i, j), "neighbor different: $i, $j")
    }
  }
}

/**
 * Asserts that two networks are strictly equal, i.e. that they have the same size, and that they
 * contain the same vertices and edges with the same indices, and that they have the same weights.
 *
 * @param expected the expected network.
 * @param actual the actual network.
 */
fun assertEquals(expected: Network, actual: Network) {
  assertEqualsGraph(expected as Graph, actual as Graph)
  for (i in 0 until expected.size) {
    for (j in 0 until expected.neighborsSize(i)) {
      assertEquals(expected.weight(i, j), actual.weight(i, j))
    }
  }
}

/**
 * Asserts that the given [flow] is valid, i.e. that it is a flow on the given [capacities] network,
 * and that it has the same flow at the [source] and [sink] vertices.
 *
 * @param flow the flow to check.
 * @param capacities the capacities of the network.
 * @param source the source vertex.
 * @param sink the sink vertex.
 * @param total the total flow, if known.
 */
fun assertFlowValid(
    flow: DirectedNetwork,
    capacities: DirectedNetwork,
    source: Vertex,
    sink: Vertex,
    total: Int,
) {
  val outgoing = IntArray(flow.size) { 0 }
  val incoming = IntArray(flow.size) { 0 }
  flow.forEachArc { (u, v) ->
    val capacity = capacities.weight(u, v)
    val weight = flow.weight(u, v)
    assertEquals(true, weight in 0..capacity)
    outgoing[u.index] += weight
    incoming[v.index] += weight
  }
  assertEquals(outgoing[source.index], incoming[sink.index], "Source and sink flows are different.")
  for (i in 0 until flow.size) {
    if (i == source.index || i == sink.index) continue
    assertEquals(outgoing[i], incoming[i], "Vertex $i has unbalanced flow.")
  }
  assertEquals(total, outgoing[source.index], "Total flow is different.")
}
