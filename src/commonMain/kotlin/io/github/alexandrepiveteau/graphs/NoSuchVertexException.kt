package io.github.alexandrepiveteau.graphs

/** An exception thrown when a [Vertex] is not found in a [UndirectedGraph]. */
public class NoSuchVertexException : NoSuchElementException("No such vertex.")
