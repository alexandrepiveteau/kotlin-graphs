package io.github.alexandrepiveteau.graphs

/**
 * A vertex in a [Graph], which is uniquely identified. Vertices can be compared to each other, and
 * are totally ordered within a [Graph] instance.
 *
 * @param index the index of the vertex within a [Graph]. Unique and non-negative in valid [Graph]s.
 */
@JvmInline
public value class Vertex(public val index: Int) {
  override fun toString(): String = "Vertex #$index"
  public companion object {

    /** A [Vertex] with an identifier which is known not to be valid in a [Graph]. */
    public val Invalid: Vertex = Vertex(-1)
  }
}
