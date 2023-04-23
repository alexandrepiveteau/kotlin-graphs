package io.github.alexandrepiveteau.graphs

/**
 * Transforms the [UndirectedGraph] into a [DirectedGraph], by adding an arc between each pair of
 * vertices that are connected by an edge in both directions.
 */
public fun UndirectedGraph.toDirectedGraph(): DirectedGraph = buildDirectedGraph {
  forEachVertex { addVertex() }
  forEachEdge { (u, v) ->
    addArc(u arcTo v)
    addArc(v arcTo u)
  }
}

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
public fun UndirectedGraph.connectedComponents(): Pair<UndirectedGraph, VertexMap> {
  val map = VertexMap(size)
  val visited = BooleanArray(size)
  // TODO : We could use a specific implementation of Graph which has no edges between vertices.
  return buildUndirectedGraph {
    for (i in visited.indices) {
      if (!visited[i]) {
        val next = addVertex()
        forEachVertexDepthFirst(get(i)) {
          visited[it.index] = true
          map[it] = next
        }
      }
    }
  } to map
}
