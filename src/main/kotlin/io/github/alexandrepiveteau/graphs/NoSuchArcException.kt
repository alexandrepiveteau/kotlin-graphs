package io.github.alexandrepiveteau.graphs

/** An exception thrown when an [Arc] is not found in a [DirectedGraph]. */
public class NoSuchArcException : NoSuchElementException("No such arc.")
