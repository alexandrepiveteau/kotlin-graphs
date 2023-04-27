package io.github.alexandrepiveteau.graphs.algorithms

import io.github.alexandrepiveteau.graphs.arcTo
import io.github.alexandrepiveteau.graphs.builder.buildDirectedNetwork
import io.github.alexandrepiveteau.graphs.builder.buildUndirectedNetwork
import io.github.alexandrepiveteau.graphs.edgeTo

object ShortestPathTestCases {

  // https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm#/media/File:Dijkstra_Animation.gif
  private val WikipediaDijkstra = buildUndirectedNetwork {
    val (v1, v2, v3, v4, v5, v6) = addVertices()
    addEdge(v1 edgeTo v6, 14)
    addEdge(v1 edgeTo v3, 9)
    addEdge(v1 edgeTo v2, 7)
    addEdge(v2 edgeTo v3, 10)
    addEdge(v2 edgeTo v4, 15)
    addEdge(v6 edgeTo v3, 2)
    addEdge(v6 edgeTo v5, 9)
    addEdge(v3 edgeTo v4, 11)
    addEdge(v5 edgeTo v4, 6)
  }
  private val WikipediaDijkstraSolution = buildDirectedNetwork {
    val (v1, v2, v3, v4, v5, v6) = addVertices()
    addArc(v1 arcTo v2, 7)
    addArc(v1 arcTo v3, 9)
    addArc(v3 arcTo v6, 2)
    addArc(v6 arcTo v5, 9)
    addArc(v3 arcTo v4, 11)
  }

  private val Singleton = buildDirectedNetwork { addVertex() }
  private val SingletonSolution = buildDirectedNetwork { addVertex() }

  private val SimpleGraph = buildUndirectedNetwork {
    val (a, b, c, d, e) = addVertices()
    addEdge(a edgeTo b, 1)
    addEdge(b edgeTo c, 1)
    addEdge(c edgeTo d, 1)
    addEdge(d edgeTo e, 1)
    addEdge(e edgeTo a, 5)
  }
  private val SimpleGraphSolution = buildDirectedNetwork {
    val (a, b, c, d, e) = addVertices()
    addArc(a arcTo b, 1)
    addArc(b arcTo c, 1)
    addArc(c arcTo d, 1)
    addArc(d arcTo e, 1)
  }

  /** Returns a [Sequence] of all the test cases with only positive weights. */
  fun positiveWeights() =
      sequenceOf(
          Triple(WikipediaDijkstra, WikipediaDijkstra[0], WikipediaDijkstraSolution),
          Triple(Singleton, Singleton[0], SingletonSolution),
          Triple(SimpleGraph, SimpleGraph[0], SimpleGraphSolution),
      )

  private val NegativeEdge = buildDirectedNetwork {
    val (a, b) = addVertices()
    addArc(a arcTo b, -1)
  }
  private val NegativeEdgeSolution = buildDirectedNetwork {
    val (a, b) = addVertices()
    addArc(a arcTo b, -1)
  }

  // https://en.wikipedia.org/wiki/Bellman–Ford_algorithm
  private val BellmanFordWikipedia = buildDirectedNetwork {
    val (s, t, x, y, z) = addVertices()
    addArc(s arcTo t, 6)
    addArc(s arcTo y, 7)
    addArc(t arcTo y, 8)
    addArc(t arcTo z, -4)
    addArc(t arcTo x, 5)
    addArc(y arcTo z, 9)
    addArc(y arcTo x, -3)
    addArc(z arcTo s, 2)
    addArc(z arcTo x, 7)
    addArc(x arcTo t, -2)
  }
  private val BellmanFordWikipediaSolution = buildDirectedNetwork {
    val (s, t, x, y, z) = addVertices()
    addArc(s arcTo y, 7)
    addArc(y arcTo x, -3)
    addArc(x arcTo t, -2)
    addArc(t arcTo z, -4)
  }

  /** Returns a [Sequence] of all the test cases with only negative weights. */
  fun negativeWeights() =
      sequenceOf(
          Triple(NegativeEdge, NegativeEdge[0], NegativeEdgeSolution),
          Triple(BellmanFordWikipedia, BellmanFordWikipedia[0], BellmanFordWikipediaSolution),
      )

  /** Returns a [Sequence] of all the test cases with both positive and negative weights. */
  fun allWeights() = positiveWeights() + negativeWeights()
}
