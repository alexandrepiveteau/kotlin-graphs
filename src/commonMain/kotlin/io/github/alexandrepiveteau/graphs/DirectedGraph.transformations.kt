package io.github.alexandrepiveteau.graphs

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
