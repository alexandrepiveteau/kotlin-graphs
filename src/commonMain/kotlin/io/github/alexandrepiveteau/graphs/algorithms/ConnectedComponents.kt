@file:JvmName("Algorithms")
@file:JvmMultifileClass

package io.github.alexandrepiveteau.graphs.algorithms

import io.github.alexandrepiveteau.graphs.Successors
import io.github.alexandrepiveteau.graphs.Undirected
import io.github.alexandrepiveteau.graphs.UndirectedGraph
import io.github.alexandrepiveteau.graphs.VertexMap
import io.github.alexandrepiveteau.graphs.builder.buildUndirectedGraph
import kotlin.jvm.JvmMultifileClass
import kotlin.jvm.JvmName

/**
 * Computes the strongly connected components of this [UndirectedGraph], and returns a new
 * [UndirectedGraph] where each vertex is a strongly connected component, and a [VertexMap] which
 * maps each vertex to its corresponding strongly connected component.
 *
 * ## Asymptotic complexity
 * - **Time complexity**: `O(|N| + |E|)`, where |N| is the number of vertices, and |E| is the number
 *   of edges in this graph.
 * - **Space complexity**: `O(|N|)`, where |N| is the number of vertices in this graph.
 */
public fun <G> G.connectedComponents(): Pair<UndirectedGraph, VertexMap> where
G : Undirected,
G : Successors {
  val map = VertexMap(size)
  val visited = BooleanArray(size)
  // TODO : We could use a specific implementation of Graph which has no edges between vertices.
  return buildUndirectedGraph {
    for (i in visited.indices) {
      if (!visited[i]) {
        val next = addVertex()
        forEachVertexDepthFirst(vertex(i)) {
          visited[index(it)] = true
          map[it] = next
        }
      }
    }
  } to map
}
