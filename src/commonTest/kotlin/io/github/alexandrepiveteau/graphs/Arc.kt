package io.github.alexandrepiveteau.graphs

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class ArcTests {

  @Test
  fun oppositeArcsAreNotEqual() {
    val a = Vertex(0)
    val b = Vertex(1)
    val e = a arcTo b
    val f = b arcTo a
    assertNotEquals(e, f)
  }

  @Test
  fun arcsAreProperlyReversed() {
    val a = Vertex(0)
    val b = Vertex(1)
    val e = a arcTo b
    val r = e.reversed()
    assertEquals(a, r.to)
    assertEquals(b, r.from)
  }

  @Test
  fun arcContainsBothExtremities() {
    val a = Vertex(0)
    val b = Vertex(1)
    val e = a arcTo b
    assertTrue(a in e)
    assertTrue(b in e)
  }

  @Test
  fun arcDestructuringReturnsExtremitiesInOrder() {
    val a = Vertex(0)
    val b = Vertex(1)
    val e = a arcTo b
    val (from, to) = e
    assertEquals(a, from)
    assertEquals(b, to)
  }
}
