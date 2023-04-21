package io.github.alexandrepiveteau.graphs

import kotlin.math.max
import kotlin.math.min

/** An undirected edge in a [UndirectedGraph] between two [Vertex]. */
@JvmInline
public value class Edge private constructor(private val encoded: Long) {

  /** Constructs a new [Edge] from the given [u] and [v] vertices. */
  // We use a long to encode the two vertices, and use the lower 32 bits for the first vertex, and
  // the upper 32 bits for the second vertex. The reason for storing the minimum vertex first is
  // that it allows us to use the standard [equals] and [hashCode] methods, which are based on the
  // underlying long value, and we're using this trick until typed equality for inline classes is
  // supported in Kotlin.
  public constructor(
      u: Vertex,
      v: Vertex
  ) : this(min(u.index, v.index).toLong() shl 32 or max(u.index, v.index).toLong())

  /* Returns the vertex with the lowest id of this edge. Different from [v]. */
  private inline val u: Vertex
    get() = Vertex(encoded.toInt())

  /* Returns the vertex with the highest id of this edge. Different from [u]. */
  private inline val v: Vertex
    get() = Vertex((encoded ushr 32).toInt())

  /** Returns an arbitrary vertex from this edge. */
  public fun any(): Vertex = u

  /**
   * Returns the vertex that is at the other end of this edge, compared to the given [vertex]. The
   * given [vertex] must be part of this edge; otherwise, an [IllegalArgumentException] is thrown.
   */
  public fun other(vertex: Vertex): Vertex =
      when (vertex) {
        u -> v
        v -> u
        else -> throw IllegalArgumentException("Vertex $vertex is not part of this edge.")
      }

  /** Returns a vertex of this edge, which is not the same as the one returned by [component2]. */
  public operator fun component1(): Vertex = u

  /** Returns a vertex of this edge, which is not the same as the one returned by [component1]. */
  public operator fun component2(): Vertex = v

  /** Returns `true` if the given [vertex] is part of this edge, and `false` otherwise. */
  public operator fun contains(vertex: Vertex): Boolean = vertex == u || vertex == v
}

/** Returns an [Edge] that contains both this and the [other] [Vertex]. */
public infix fun Vertex.edgeTo(other: Vertex): Edge = Edge(this, other)
