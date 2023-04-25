package io.github.alexandrepiveteau.graphs.builder

import io.github.alexandrepiveteau.graphs.*
import kotlin.random.Random

/**
 * Creates a random [UndirectedGraph] with [n] vertices and an edge between each pair of vertices
 * with probability [p].
 *
 * @param n the number of vertices in the graph.
 * @param p the probability of an edge between two vertices.
 * @param random the [Random] instance to use.
 */
fun UndirectedGraph.Companion.erdosRenyi(
    n: Int,
    p: Double,
    random: Random = Random,
) = buildUndirectedGraph {
  val vertices = VertexArray(n) { addVertex() }
  for (i in 0 until n) {
    for (j in i + 1 until n) {
      if (random.nextDouble() < p) {
        addEdge(vertices[i] edgeTo vertices[j])
      }
    }
  }
}

/**
 * Creates a random [DirectedGraph] with [n] vertices and an arc between each pair of vertices with
 * probability [p].
 *
 * @param n the number of vertices in the graph.
 * @param p the probability of an arc between two vertices.
 * @param random the [Random] instance to use.
 */
fun DirectedGraph.Companion.erdosRenyi(
    n: Int,
    p: Double,
    random: Random = Random,
) = buildDirectedGraph {
  val vertices = VertexArray(n) { addVertex() }
  for (i in 0 until n) {
    for (j in 0 until n) {
      if (random.nextDouble() < p) {
        addArc(vertices[i] arcTo vertices[j])
      }
    }
  }
}
