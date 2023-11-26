package io.github.alexandrepiveteau.graphs

/** A marker interface for [Graph]s that are directed. */
public interface Directed {

  /** Returns true if the given [arc] is contained in this [Directed] graph, and false otherwise. */
  public operator fun contains(arc: Arc): Boolean
}
