package me.azimmuradov.svc.engine.geometry.shapes

import me.azimmuradov.svc.engine.geometry.*

object PlacedRectStrategy : PlacedShapeStrategy {

    override fun coordinates(corners: CanonicalCorners): Set<Coordinate> {
        val (bl, tr) = corners

        val xs = bl.x..tr.x
        val ys = bl.y..tr.y

        return (xs * ys).mapTo(
            destination = mutableSetOf(),
            transform = Pair<Int, Int>::toCoordinate
        )
    }
}


// Private utils

// Cartesian product

private operator fun <A, B> Iterable<A>.times(other: Iterable<B>): List<Pair<A, B>> =
    flatMap { a -> other.map { b -> a to b } }