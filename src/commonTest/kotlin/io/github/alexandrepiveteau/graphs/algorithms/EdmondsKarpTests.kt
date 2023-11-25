package io.github.alexandrepiveteau.graphs.algorithms

import io.github.alexandrepiveteau.graphs.VertexArray
import io.github.alexandrepiveteau.graphs.arcTo
import io.github.alexandrepiveteau.graphs.builder.buildDirectedNetwork
import io.github.alexandrepiveteau.graphs.util.Repeats
import io.github.alexandrepiveteau.graphs.util.assertEquals
import io.github.alexandrepiveteau.graphs.util.assertFlowValid
import kotlin.test.Test

class EdmondsKarpTests {

  @Test
  fun noEdgesHasEmptyFlow() {
    val capacities = buildDirectedNetwork {
      addVertex()
      addVertex()
    }
    val a = capacities.vertex(0)
    val b = capacities.vertex(1)

    val flow = capacities.maxFlowEdmondsKarp(a, b)

    assertFlowValid(flow, capacities, a, b, total = 0)
  }

  @Test
  fun singleArcHasNonEmptyFlow() {
    val capacities = buildDirectedNetwork {
      val (a, b) = addVertices()
      addArc(a arcTo b, 1)
    }
    val expected = buildDirectedNetwork {
      val (a, b) = addVertices()
      addArc(a arcTo b, 1)
    }

    val a = capacities.vertex(0)
    val b = capacities.vertex(1)
    val flow = capacities.maxFlowEdmondsKarp(a, b)

    assertFlowValid(flow, capacities, a, b, total = 1)
    assertEquals(expected, flow)
  }

  @Test
  fun twoArcsHasMinimumOfCapacities() {
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

    val a = capacities.vertex(0)
    val c = capacities.vertex(2)
    val flow = capacities.maxFlowEdmondsKarp(a, c)

    assertFlowValid(flow, capacities, a, c, total = 1)
    assertEquals(expected, flow)
  }

  @Test
  fun threeArcsHasSumOfCapacities() {
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

    val a = capacities.vertex(0)
    val d = capacities.vertex(3)
    val flow = capacities.maxFlowEdmondsKarp(a, d)

    assertFlowValid(flow, capacities, a, d, total = 2)
    assertEquals(expected, flow)
  }

  @Test
  fun wikipediaExampleHasCorrectTotalFlow() {
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

    val a = capacities.vertex(0)
    val g = capacities.vertex(6)
    val flow = capacities.maxFlowEdmondsKarp(a, g)

    assertFlowValid(flow, capacities, a, g, total = 5)
  }

  @Test
  fun completeGraphsHaveCorrectTotalFlow() {
    for (count in 2 until Repeats) {
      val capacities = buildDirectedNetwork {
        val vertices = VertexArray(count) { addVertex() }
        for (i in 0 until count) {
          for (j in 0 until count) {
            if (i != j) {
              addArc(vertices[i] arcTo vertices[j], 1)
            }
          }
        }
      }

      val a = capacities.vertex(0)
      val b = capacities.vertex(1)
      val flow = capacities.maxFlowEdmondsKarp(a, b)

      // The maximum flow is the number of vertices minus one, because we can reach the second
      // vertex directly, or by going through any other vertex and then to the second vertex.
      assertFlowValid(flow, capacities, a, b, total = count - 1)
    }
  }
}
