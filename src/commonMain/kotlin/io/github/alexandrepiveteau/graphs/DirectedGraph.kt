package io.github.alexandrepiveteau.graphs

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
