package io.github.alexandrepiveteau.graphs.algorithms

import io.github.alexandrepiveteau.graphs.*
import io.github.alexandrepiveteau.graphs.internal.IntDequeue

/**
 * Returns the [VertexArray] containing all the vertices traversed to go from the [from] vertex to
 * the [to] vertex, using the given [parents] map.
 *
 * @param parents the map containing the parents of each vertex.
 * @param from the starting vertex.
 * @param to the ending vertex.
 * @return the [VertexArray] containing all the vertices traversed to go from the [from] vertex to
 *   the [to] vertex.
 * @receiver the [Graph] on which the traversal was performed.
 */
internal fun Graph.computePath(parents: VertexMap, from: Vertex, to: Vertex): VertexArray? {
  if (from == Vertex.Invalid) throw IllegalArgumentException()
  if (to == Vertex.Invalid) throw IllegalArgumentException()
  val path = IntDequeue()
  var current = to
  while (current != from) {
    if (current == Vertex.Invalid) return null
    path.addFirst(get(current))
    current = parents[current]
  }
  path.addFirst(get(from))
  return VertexArray(path.toIntArray())
}

/**
 * Returns the [DirectedNetwork] for the subgraph of this [Network] defined by the [parents] map.
 *
 * @param parents the [VertexMap] that maps each vertex to its parent in the shortest path tree.
 * @return the [DirectedNetwork] for the subgraph of this [Network] defined by the [parents] map.
 * @receiver the [Network] to transform.
 */
internal fun Network.computeNetwork(parents: VertexMap): DirectedNetwork {
  return buildDirectedNetwork {
    forEachVertex { addVertex() }
    parents.forEach { vertex, parent ->
      if (parent != Vertex.Invalid) {
        addArc(parent arcTo vertex, weight(parent, vertex))
      }
    }
  }
}
