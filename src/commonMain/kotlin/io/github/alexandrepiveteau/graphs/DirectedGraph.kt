package io.github.alexandrepiveteau.graphs

import io.github.alexandrepiveteau.graphs.algorithms.forEachArc
import io.github.alexandrepiveteau.graphs.algorithms.forEachVertex
import io.github.alexandrepiveteau.graphs.builder.buildDirectedGraph
import io.github.alexandrepiveteau.graphs.builder.buildUndirectedGraph

/** A [DirectedGraph] is a [Graph] where [Vertex]s are linked using [Arc]s. */
public interface DirectedGraph : Graph {

  /** Returns true if the given [arc] is contained in this [DirectedGraph], and false otherwise. */
  public operator fun contains(arc: Arc): Boolean

  /**
   * An object which serves as the companion of [DirectedGraph], and which provides a number of
   * factory methods to create [DirectedGraph]s.
   */
  public companion object
}

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
