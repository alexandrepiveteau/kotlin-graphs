@file:JvmName("Algorithms")
@file:JvmMultifileClass

package io.github.alexandrepiveteau.graphs.algorithms

import io.github.alexandrepiveteau.graphs.*
import io.github.alexandrepiveteau.graphs.builder.buildDirectedGraph
import io.github.alexandrepiveteau.graphs.internal.collections.IntDequeue
import kotlin.jvm.JvmMultifileClass
import kotlin.jvm.JvmName

/**
 * Returns the [DirectedGraph] where each vertex is replaced by its strongly connected component.
 *
 * @return a [Pair] of the [DirectedGraph] and the [VertexMap] that maps each vertex to its strongly
 *   connected component.
 * @receiver the [DirectedGraph] to transform.
 */
public fun DirectedGraph.stronglyConnectedComponentsKosaraju(): Pair<DirectedGraph, VertexMap> {
  // 1. Traverse all vertices in post-order fashion, and prepend them to the queue.
  val visited = BooleanArray(size)
  val queue = IntDequeue()
  forEachVertex { v ->
    if (!visited[index(v)]) {
      forEachVertexDepthFirstPostOrder(v, visited) { queue.addFirst(index(it)) }
    }
  }

  // 2. Take the transposed graph, and assign the vertices.
  val components = IntArray(size) { -1 }
  val transposed = transposed()
  val assigned = BooleanArray(size)
  val order = queue.toIntArray().asVertexArray()
  var nextVertex = 0
  for (i in 0 until order.size) {
    val v = order[i]
    if (!assigned[index(v)]) {
      transposed.forEachVertexDepthFirst(v, assigned) { components[index(it)] = nextVertex }
      nextVertex++
    }
  }

  // 3. Build the resulting graph, and the vertex map.
  val graph = buildDirectedGraph {
    val vertices = VertexArray(nextVertex) { addVertex() }
    forEachArc { (u, v) ->
      val cu = vertices[components[index(u)]]
      val cv = vertices[components[index(v)]]
      if (cu != cv) addArc(cu arcTo cv)
    }
  }
  return graph to VertexMap(components.size) { graph.vertex(components[index(it)]) }
}
