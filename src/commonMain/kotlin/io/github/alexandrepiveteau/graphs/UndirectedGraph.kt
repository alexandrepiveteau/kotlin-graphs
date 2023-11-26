package io.github.alexandrepiveteau.graphs

import io.github.alexandrepiveteau.graphs.algorithms.forEachEdge
import io.github.alexandrepiveteau.graphs.algorithms.forEachVertex
import io.github.alexandrepiveteau.graphs.builder.buildDirectedGraph

/** A [UndirectedGraph] is a [Graph] where [Vertex]s are linked using [Edge]s. */
public interface UndirectedGraph : Graph, Undirected {

  /**
   * An object which serves as the companion of [UndirectedGraph], and which provides a number of
   * factory methods to create [UndirectedGraph]s.
   */
  public companion object
}

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
