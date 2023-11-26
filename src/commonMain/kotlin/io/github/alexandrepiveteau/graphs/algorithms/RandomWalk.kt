package io.github.alexandrepiveteau.graphs.algorithms

import io.github.alexandrepiveteau.graphs.Successors
import io.github.alexandrepiveteau.graphs.SuccessorsWeight
import io.github.alexandrepiveteau.graphs.Vertex
import io.github.alexandrepiveteau.graphs.internal.collections.IntVector
import kotlin.contracts.contract
import kotlin.random.Random

/**
 * Performs a random walk on the graph, starting from the given [from] vertex, and performs the
 * given [action] on each vertex. The walk may be infinite if the graph contains cycles, and the
 * walk will stop if the vertex has no successors.
 *
 * This method is inline, so it can be used to traverse the graph with suspending functions.
 *
 * ## Asymptotic complexity
 * - **Time complexity**: Depends on the graph.
 * - **Space complexity**: O(1).
 */
public inline fun <G> G.randomWalk(
    from: Vertex,
    random: Random = Random,
    action: (Vertex) -> Unit,
) where G : Successors {
  contract { callsInPlace(action) }

  var current = index(from)

  while (true) {
    action(vertex(current))
    val size = successorsSize(current)
    if (size == 0) return

    current = index(successorVertex(current, random.nextInt(size)))
  }
}

/**
 * Performs a random walk on the graph, starting from the given [from] vertex, and performs the
 * given [action] on each vertex. The walk may be infinite if the graph contains cycles, and the
 * walk will stop if the vertex has no successors. The walk will be weighted, and the probability of
 * choosing a successor is proportional to the weight of the link. Negative weights are not
 * supported, and will be ignored and treated as if they were 0.
 *
 * This method is inline, so it can be used to traverse the graph with suspending functions.
 *
 * ## Asymptotic complexity
 * - **Time complexity**: Depends on the graph.
 * - **Space complexity**: O(|D|), where |D| is the number of distinct successors for any given
 *   vertex.
 */
public inline fun <G> G.randomWalkWeighted(
    from: Vertex,
    random: Random = Random,
    action: (Vertex) -> Unit,
) where G : SuccessorsWeight {
  contract { callsInPlace(action) }

  var current = index(from)
  val vertices = IntVector()
  val weights = IntVector()

  while (true) {
    action(vertex(current))
    val size = successorsSize(current)
    if (size == 0) return

    vertices.clear()
    weights.clear()

    for (i in 0 ..< size) {
      val weight = successorWeight(current, i)
      if (weight <= 0) continue
      vertices += index(successorVertex(current, i))
      weights += weight
    }

    val total = weights.sum()
    if (total <= 0) return // No reachable successors.
    val value = random.nextInt(total)
    var sum = 0
    for (i in 0 ..< weights.size) {
      sum += weights[i]
      if (sum > value) {
        current = vertices[i]
        break
      }
    }
  }
}
