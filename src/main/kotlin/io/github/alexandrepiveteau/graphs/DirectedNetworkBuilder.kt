package io.github.alexandrepiveteau.graphs

/** A [DirectedNetworkBuilder] is a [DirectedGraphBuilder] which can also add arcs with a weight. */
public interface DirectedNetworkBuilder : NetworkBuilder, DirectedGraphBuilder {

  /**
   * Adds a new weighted arc to the graph.
   *
   * @param arc the arc to add.
   * @param weight the weight of the arc.
   */
  public fun addArc(arc: Arc, weight: Int = 0)

  override fun addArc(arc: Arc): Unit = addArc(arc, 0)
}
