package io.github.alexandrepiveteau.graphs.builder

import io.github.alexandrepiveteau.graphs.*
import io.github.alexandrepiveteau.graphs.internal.collections.IntVector

/**
 * A [GraphBuilder] is a [MutableGraphScope] with which a graph can be built, using the [toGraph]
 * method.
 */
public interface GraphBuilder : MutableGraphScope {

  /**
   * Returns a new [Graph] instance, which is built from the current state of the [GraphBuilder].
   */
  public fun toGraph(): Graph
}

// BUILDER HELPERS

/**
 * An implementation of [GraphBuilder] which uses a [MutableList] to store the neighbors of each
 * vertex.
 */
internal abstract class MutableListGraphBuilder : GraphBuilder {

  /** The list of neighbors of each vertex. */
  protected val neighbors: MutableList<IntVector> = mutableListOf()

  override fun addVertex(): Vertex = Vertex(neighbors.size).also { neighbors += IntVector() }

  /** Asserts that the given [u] and [v] are valid vertices. */
  fun checkLink(u: Vertex, v: Vertex) {
    if (u.index < 0 || u.index >= neighbors.size) throw IndexOutOfBoundsException()
    if (v.index < 0 || v.index >= neighbors.size) throw IndexOutOfBoundsException()
  }
}

/**
 * Compacts the given [neighbors] into an array of [VertexArray]s, suitable for being used in an
 * adjacency list representation.
 *
 * @param neighbors the list of neighbors of each vertex.
 * @return the array of [VertexArray]s.
 */
internal fun compactToVertexArray(neighbors: MutableList<IntVector>): Array<VertexArray> {
  return Array(neighbors.size) {
    var last = -1 // This is an invalid vertex.
    var count = 0
    val list = neighbors[it].toIntArray().apply { sort() }
    for (j in list.indices) {
      if (list[j] != last) {
        list[count++] = list[j] // Update the array in place.
        last = list[j]
      }
    }
    list.copyOf(count).asVertexArray() // Compact the array.
  }
}
