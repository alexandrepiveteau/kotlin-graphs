package io.github.alexandrepiveteau.graphs

import io.github.alexandrepiveteau.graphs.util.packInts
import io.github.alexandrepiveteau.graphs.util.unpackInt1
import io.github.alexandrepiveteau.graphs.util.unpackInt2

/** A directed arc in a [UndirectedGraph] between two [Vertex]. */
@JvmInline
public value class Arc private constructor(private val encoded: Long) {

  /** Constructs a new [Arc] [from] and [to] vertices. */
  public constructor(
      from: Vertex,
      to: Vertex,
  ) : this(packInts(from.index, to.index))

  /** The origin vertex of this arc. */
  public val from: Vertex
    get() = Vertex(unpackInt1(encoded))

  /** The destination vertex of this arc. */
  public val to: Vertex
    get() = Vertex(unpackInt2(encoded))

  /** Returns the [from] [Vertex]. */
  public operator fun component1(): Vertex = from

  /** Returns the [to] [Vertex]. */
  public operator fun component2(): Vertex = to

  /** Returns `true` if the given [vertex] is part of this arc, and `false` otherwise. */
  public operator fun contains(vertex: Vertex): Boolean = vertex == from || vertex == to

  /** Returns an [Arc] that goes from [to] to [from]. */
  public fun reversed(): Arc = Arc(to, from)
}

/** Returns an [Arc] that goes from this [Vertex] to the [other] [Vertex]. */
public infix fun Vertex.arcTo(other: Vertex): Arc = Arc(this, other)
