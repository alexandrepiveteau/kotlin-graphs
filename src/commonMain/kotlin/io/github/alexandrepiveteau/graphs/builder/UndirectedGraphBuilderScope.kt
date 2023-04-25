package io.github.alexandrepiveteau.graphs.builder

import io.github.alexandrepiveteau.graphs.Edge
import io.github.alexandrepiveteau.graphs.UndirectedGraph

/** A [GraphBuilderScope] for [UndirectedGraph]s. */
public interface UndirectedGraphBuilderScope : GraphBuilderScope {

  /**
   * Adds a new edge to the graph.
   *
   * @param edge the edge to add.
   */
  public fun addEdge(edge: Edge)
}
