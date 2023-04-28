# Change Log

## [Unreleased]

### Improvements

+ Perform chunk copies in `IntDequeue.toIntArray()`.

## [0.5.0] - 2023-04-28

### Algorithms

+ Add Prim's minimum spanning tree algorithm.
+ Add overloads to `forEachNeighbor`, `forEachArc` and `forEachEdge` with weights.

### API Changes

+ Introduce a bunch of interfaces to model mutable graphs.
    - Added `MutableGraph`, `MutableNetwork` and directed/undirected variants.
    - Renamed `GraphBuilderScope` and variants to `MutableGraphScope`, which is implemented by the new `MutableGraph` interface.

### Bug fixes

+ Remove some references to `Vertex.index` from multiple algorithms.

## [0.4.0] - 2023-04-27

### Algorithms

+ Add Dijkstra's shortest path algorithm.

## [0.3.1] - 2023-04-26

### Fixes

+ Make int packing functions non-inline.

## [0.3.0] - 2023-04-26

### Algorithms

+ Add a topological sort algorithm.

### API Changes

+ Add `Array<Vertex>.toVertexArray()` and `VertexArray.toTypedArray()` extension methods.

## [0.2.0] - 2023-04-25

+ Moved algorithms to the `io.github.alexandrepiveteau.graphs.algorithms` package.
+ Added scope and dedicated builder interfaces to graph builders.

## [0.1.1] - 2023-04-25

### Documentation

+ Added some download instructions.

## [0.1.0] - 2023-04-25

Initial release


[Unreleased]: https://github.com/alexandrepiveteau/kotlin-graphs/compare/0.5.0...HEAD

[0.5.0]: https://github.com/alexandrepiveteau/kotlin-graphs/releases/tag/0.5.0

[0.4.0]: https://github.com/alexandrepiveteau/kotlin-graphs/releases/tag/0.4.0

[0.3.1]: https://github.com/alexandrepiveteau/kotlin-graphs/releases/tag/0.3.1

[0.3.0]: https://github.com/alexandrepiveteau/kotlin-graphs/releases/tag/0.3.0

[0.2.0]: https://github.com/alexandrepiveteau/kotlin-graphs/releases/tag/0.2.0

[0.1.1]: https://github.com/alexandrepiveteau/kotlin-graphs/releases/tag/0.1.1

[0.1.0]: https://github.com/alexandrepiveteau/kotlin-graphs/releases/tag/0.1.0
