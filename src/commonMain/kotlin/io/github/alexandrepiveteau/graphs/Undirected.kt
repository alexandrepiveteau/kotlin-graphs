package io.github.alexandrepiveteau.graphs

/** A marker interface for [Graph]s that are undirected. */
public interface Undirected {

  /** Returns true if the given [edge] is contained in this [Undirected] graph, and false otherwise. */
  public operator fun contains(edge: Edge): Boolean
}
