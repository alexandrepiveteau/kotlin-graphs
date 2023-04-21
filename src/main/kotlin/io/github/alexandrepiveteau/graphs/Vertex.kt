package io.github.alexandrepiveteau.graphs

/**
 * A vertex in a [UndirectedGraph], which is uniquely identified. Vertices can be compared to each
 * other, and are totally ordered within a [UndirectedGraph] instance.
 *
 * @param index the index of the vertex within its [UndirectedGraph].
 */
@JvmInline
public value class Vertex(public val index: Int) {
  override fun toString(): String = "Vertex #$index"
}
