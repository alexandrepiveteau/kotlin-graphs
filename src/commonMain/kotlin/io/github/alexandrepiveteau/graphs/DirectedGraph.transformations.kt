package io.github.alexandrepiveteau.graphs

import io.github.alexandrepiveteau.graphs.internal.IntDequeue

/**
 * Transforms the [DirectedGraph] into an [UndirectedGraph], by adding an edge between each pair of
 * vertices that are connected by an arc.
 */
public fun DirectedGraph.toUndirectedGraph(): UndirectedGraph = buildUndirectedGraph {
  forEachVertex { addVertex() }
  forEachArc { (u, v) -> addEdge(u edgeTo v) }
}

/** Returns the transposed [DirectedGraph], where the direction of arcs has been reversed. */
public fun DirectedGraph.transposed(): DirectedGraph = buildDirectedGraph {
  forEachVertex { addVertex() }
  forEachArc { addArc(it.reversed()) }
}

/**
 * Returns the [DirectedGraph] where each vertex is replaced by its strongly connected component.
 *
 * @return a [Pair] of the [DirectedGraph] and the [VertexMap] that maps each vertex to its strongly
 *   connected component.
 * @receiver the [DirectedGraph] to transform.
 */
public fun DirectedGraph.stronglyConnectedComponentsKosaraju(): Pair<DirectedGraph, VertexMap> {
  // 1. Traverse all vertices in post-order fashion, and prepend them to the queue.
  val visited = BooleanArray(size)
  val queue = IntDequeue()
  forEachVertex { v ->
    if (!visited[get(v)]) {
      forEachVertexDepthFirstPostOrder(v, visited) { queue.addFirst(get(it)) }
    }
  }

  // 2. Take the transposed graph, and assign the vertices.
  val components = IntArray(size) { -1 }
  val transposed = transposed()
  val assigned = BooleanArray(size)
  val order = queue.toIntArray().asVertexArray()
  var nextVertex = 0
  for (i in 0 until order.size) {
    val v = order[i]
    if (!assigned[get(v)]) {
      transposed.forEachVertexDepthFirst(v, assigned) { components[get(it)] = nextVertex }
      nextVertex++
    }
  }

  // 3. Build the resulting graph, and the vertex map.
  val graph = buildDirectedGraph {
    val vertices = VertexArray(nextVertex) { addVertex() }
    forEachArc { (u, v) ->
      val cu = vertices[components[get(u)]]
      val cv = vertices[components[get(v)]]
      if (cu != cv) addArc(cu arcTo cv)
    }
  }
  return graph to VertexMap(components.size) { graph[components[get(it)]] }
}