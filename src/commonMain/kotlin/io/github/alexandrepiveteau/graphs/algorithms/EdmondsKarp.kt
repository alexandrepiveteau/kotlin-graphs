package io.github.alexandrepiveteau.graphs.algorithms

import io.github.alexandrepiveteau.graphs.*
import kotlin.math.min

/** Returns the residual network of this [DirectedNetwork], given the current [flow]. */
private fun DirectedNetwork.residual(flow: DirectedNetwork): DirectedNetwork {
  return buildDirectedNetwork {
    forEachVertex { addVertex() }
    forEachArc { arc ->
      val forwardCapacity = weight(arc) - flow.weight(arc)
      if (forwardCapacity > 0) addArc(arc, forwardCapacity)
      val backwardCapacity = flow.weight(arc)
      if (backwardCapacity > 0) addArc(arc.reversed(), backwardCapacity)
    }
  }
}

/** Iterates over all the arcs in the path, and calls the [action] for each of them. */
private inline fun VertexArray.forEachArc(action: (Arc) -> Unit) {
  for (u in 0 until size - 1) {
    action(this[u] arcTo this[u + 1])
  }
}

/** Returns the remaining capacity of the [flow] in the [path]. */
private fun DirectedNetwork.remainingCapacity(flow: DirectedNetwork, path: VertexArray): Int {
  var capacity = Int.MAX_VALUE
  path.forEachArc { arc ->
    val local = if (arc in this) weight(arc) - flow.weight(arc) else flow.weight(arc.reversed())
    capacity = min(capacity, local)
  }
  return capacity
}

/** Augments the [flow] with the given [path] and [flow], and returns the new flow. */
private fun DirectedNetwork.augment(path: VertexArray, flow: Int): DirectedNetwork {
  return buildDirectedNetwork {
    forEachVertex { addVertex() }
    forEachArc { (u, v) -> addArc(u arcTo v, weight(u arcTo v)) }
    path.forEachArc { arc ->
      if (arc in this@augment) addArc(arc, flow) else addArc(arc.reversed(), -flow)
    }
  }
}

/**
 * Returns the maximum flow from the [from] [Vertex] to the [to] [Vertex] in this [Network], as
 * constrained by the capacities of the edges.
 *
 * @param from the [Vertex] from which the flow should start.
 * @param to the [Vertex] to which the flow should end.
 * @return the maximum flow from the [from] [Vertex] to the [to] [Vertex] in this [Network], as
 *   constrained by the capacities of the arcs.
 * @receiver the [Network] on which to compute the maximum flow.
 */
public fun DirectedNetwork.maxFlowEdmondsKarp(from: Vertex, to: Vertex): DirectedNetwork {
  // TODO : Use some MutableDirectedNetworks once they're implemented.
  var maxFlow = buildDirectedNetwork {
    forEachVertex { addVertex() }
    forEachArc { (u, v) -> addArc(u arcTo v, 0) }
  }
  while (true) {
    val residual = residual(maxFlow)
    val path = residual.shortestPathBreadthFirst(from, to) ?: break
    val flow = remainingCapacity(maxFlow, path)
    maxFlow = maxFlow.augment(path, flow)
  }
  return maxFlow
}
