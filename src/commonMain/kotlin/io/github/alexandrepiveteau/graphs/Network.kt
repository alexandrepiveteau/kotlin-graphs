package io.github.alexandrepiveteau.graphs

/**
 * A [Network] is a [Graph] with some weights associated to each link. The weights are represented
 * as [Int] values.
 */
public interface Network : Graph, SuccessorsWeight
