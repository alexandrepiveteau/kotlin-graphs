package io.github.alexandrepiveteau.graphs

/** Returns the transposed [DirectedGraph], where the direction of arcs has been reversed. */
public fun DirectedGraph.transposed(): DirectedGraph = buildDirectedGraph {
  forEachVertex { addVertex() }
  forEachArc { addArc(it.reversed()) }
}
