package io.github.alexandrepiveteau.graphs

/** A [MutableGraphScope] for [DirectedGraph]s. */
public interface MutableDirectedGraphScope : MutableGraphScope {

  /**
   * Adds a new arc to the graph.
   *
   * @param arc the arc to add.
   */
  public fun addArc(arc: Arc)
}
