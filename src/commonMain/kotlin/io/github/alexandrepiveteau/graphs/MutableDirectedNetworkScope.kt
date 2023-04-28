package io.github.alexandrepiveteau.graphs

/** A [MutableNetworkScope] for [DirectedNetwork]s. */
public interface MutableDirectedNetworkScope : MutableDirectedGraphScope, MutableNetworkScope {

  /**
   * Adds a new weighted arc to the graph.
   *
   * @param arc the arc to add.
   * @param weight the weight of the arc.
   */
  public fun addArc(arc: Arc, weight: Int = 0)

  override fun addArc(arc: Arc): Unit = addArc(arc, 0)
}
