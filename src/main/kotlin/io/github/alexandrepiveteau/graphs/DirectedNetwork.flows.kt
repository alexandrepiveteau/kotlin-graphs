package io.github.alexandrepiveteau.graphs

import kotlin.math.min

/** Returns the residual network of this [DirectedNetwork], given the current [flow]. */
private fun DirectedNetwork.residual(flow: DirectedNetwork): Network {
  return buildDirectedNetwork {
    forEachVertex { addVertex() }
    forEachArc { arc ->
      val capacity = weight(arc) - flow.weight(arc)
      if (capacity > 0) addArc(arc, capacity)
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
  path.forEachArc { arc -> capacity = min(capacity, this.weight(arc) - flow.weight(arc)) }
  return capacity
}

/** Augments the [flow] with the given [path] and [flow], and returns the new flow. */
private fun DirectedNetwork.augment(path: VertexArray, flow: Int): DirectedNetwork {
  return buildDirectedNetwork {
    forEachVertex { addVertex() }
    forEachArc { (u, v) -> addArc(u arcTo v, weight(u arcTo v)) }
    path.forEachArc { arc -> addArc(arc, flow) }
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
    val path = residual(maxFlow).shortestPathBreadthFirst(from, to) ?: break
    val flow = remainingCapacity(maxFlow, path)
    maxFlow = maxFlow.augment(path, flow)
  }
  return maxFlow
}
