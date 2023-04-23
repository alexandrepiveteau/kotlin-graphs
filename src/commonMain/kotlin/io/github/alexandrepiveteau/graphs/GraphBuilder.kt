package io.github.alexandrepiveteau.graphs

import io.github.alexandrepiveteau.graphs.internal.IntVector

/**
 * A [GraphBuilder] is a mutable data structure which can be used to build a [Graph]. It is
 * implemented as a builder, and can be used to build a graph in a single expression.
 */
public fun interface GraphBuilder {

  /** Adds a new vertex to the graph, and returns it. */
  public fun addVertex(): Vertex

  /** Returns a new [Vertices] instance. */
  public fun addVertices(): Vertices = Vertices { addVertex() }
}

/** A [Vertices] is a helper class which allows to create multiple [Vertex]s at once. */
public fun interface Vertices {
  public operator fun invoke(): Vertex
  public operator fun component1(): Vertex = invoke()
  public operator fun component2(): Vertex = invoke()
  public operator fun component3(): Vertex = invoke()
  public operator fun component4(): Vertex = invoke()
  public operator fun component5(): Vertex = invoke()
  public operator fun component6(): Vertex = invoke()
  public operator fun component7(): Vertex = invoke()
  public operator fun component8(): Vertex = invoke()
  public operator fun component9(): Vertex = invoke()
}

// BUILDER HELPERS

/**
 * An implementation of [GraphBuilder] which uses a [MutableList] to store the neighbors of each
 * vertex.
 *
 * @param neighbors the list of neighbors of each vertex.
 */
@PublishedApi
internal open class MutableListGraphBuilder(
    private val neighbors: MutableList<IntVector>,
) : GraphBuilder {
  override fun addVertex(): Vertex = Vertex(neighbors.size).also { neighbors += IntVector() }
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
@PublishedApi
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
