package io.github.alexandrepiveteau.graphs

import io.github.alexandrepiveteau.graphs.util.assertEquals as assertEqualsGraph
import io.github.alexandrepiveteau.graphs.util.assertFlowValid
import kotlin.test.Test

class DirectedNetworkFlowsTests {

  @Test
  fun `no edges has empty flow`() {
    val capacities = buildDirectedNetwork {
      addVertex()
      addVertex()
    }
    val a = capacities[0]
    val b = capacities[1]

    val flow = capacities.maxFlowEdmondsKarp(a, b)

    assertFlowValid(flow, capacities, a, b, total = 0)
  }

  @Test
  fun `single arc has non-empty flow`() {
    val capacities = buildDirectedNetwork {
      val (a, b) = addVertices()
      addArc(a arcTo b, 1)
    }
    val expected = buildDirectedNetwork {
      val (a, b) = addVertices()
      addArc(a arcTo b, 1)
    }

    val a = capacities[0]
    val b = capacities[1]
    val flow = capacities.maxFlowEdmondsKarp(a, b)

    assertFlowValid(flow, capacities, a, b, total = 1)
    assertEqualsGraph(expected, flow)
  }

  @Test
  fun `two arcs has minimum of capacities`() {
    val capacities = buildDirectedNetwork {
      val (a, b, c) = addVertices()
      addArc(a arcTo b, 1)
      addArc(b arcTo c, 2)
    }
    val expected = buildDirectedNetwork {
      val (a, b, c) = addVertices()
      addArc(a arcTo b, 1)
      addArc(b arcTo c, 1)
    }

    val a = capacities[0]
    val c = capacities[2]
    val flow = capacities.maxFlowEdmondsKarp(a, c)

    assertFlowValid(flow, capacities, a, c, total = 1)
    assertEqualsGraph(expected, flow)
  }

  @Test
  fun `three arcs has sum of capacities`() {
    val capacities = buildDirectedNetwork {
      val (a, b, c, d) = addVertices()
      addArc(a arcTo b, 1)
      addArc(a arcTo c, 10)
      addArc(b arcTo d, 10)
      addArc(c arcTo d, 1)
    }
    val expected = buildDirectedNetwork {
      val (a, b, c, d) = addVertices()
      addArc(a arcTo b, 1)
      addArc(a arcTo c, 1)
      addArc(b arcTo d, 1)
      addArc(c arcTo d, 1)
    }

    val a = capacities[0]
    val d = capacities[3]
    val flow = capacities.maxFlowEdmondsKarp(a, d)

    assertFlowValid(flow, capacities, a, d, total = 2)
    assertEqualsGraph(expected, flow)
  }

  @Test
  fun `wikipedia example has right total flow`() {
    // Source: https://en.wikipedia.org/wiki/Edmonds–Karp_algorithm
    val capacities = buildDirectedNetwork {
      val (a, b, c, d, e, f, g) = addVertices()
      addArc(a arcTo d, 3)
      addArc(d arcTo f, 6)
      addArc(c arcTo a, 3)
      addArc(c arcTo d, 1)
      addArc(a arcTo b, 3)
      addArc(d arcTo e, 2)
      addArc(f arcTo g, 9)
      addArc(b arcTo c, 4)
      addArc(c arcTo e, 2)
      addArc(e arcTo b, 1)
      addArc(e arcTo g, 1)
    }

    val a = capacities[0]
    val g = capacities[6]
    val flow = capacities.maxFlowEdmondsKarp(a, g)

    assertFlowValid(flow, capacities, a, g, total = 5)
  }
}
