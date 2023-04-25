package io.github.alexandrepiveteau.graphs.builder

import io.github.alexandrepiveteau.graphs.Arc
import io.github.alexandrepiveteau.graphs.DirectedGraph

/** A [GraphBuilderScope] for [DirectedGraph]s. */
public interface DirectedGraphBuilderScope : GraphBuilderScope {

  /**
   * Adds a new arc to the graph.
   *
   * @param arc the arc to add.
   */
  public fun addArc(arc: Arc)
}
