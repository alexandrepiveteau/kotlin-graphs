package io.github.alexandrepiveteau.graphs

/**
 * A [Graph] is a set of [Vertex], which may be linked together by some undirected [Edge]s or some
 * directed [Arc]s. Graphs can be iterated over, and their [size] can be queried.
 *
 * Vertices and links are indexed, and [Graph] provides a number of methods to access vertices and
 * links by their index. This is useful for algorithms which need to iterate over the vertices and
 * links of the graph without having to create an explicit iterator.
 */
public interface Graph : VertexSet, Successors
