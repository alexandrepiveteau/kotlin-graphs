package io.github.alexandrepiveteau.graphs.builder

import io.github.alexandrepiveteau.graphs.Arc
import io.github.alexandrepiveteau.graphs.DirectedNetwork

/** A [NetworkBuilderScope] for [DirectedNetwork]s. */
public interface DirectedNetworkBuilderScope : DirectedGraphBuilderScope, NetworkBuilderScope {

  /**
   * Adds a new weighted arc to the graph.
   *
   * @param arc the arc to add.
   * @param weight the weight of the arc.
   */
  public fun addArc(arc: Arc, weight: Int = 0)

  override fun addArc(arc: Arc): Unit = addArc(arc, 0)
}
