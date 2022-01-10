package me.azimmuradov.svc.engine.geometry.shapes

import me.azimmuradov.svc.engine.geometry.*

object PlacedRectOutlineStrategy : PlacedShapeStrategy {

    override fun coordinates(corners: CanonicalCorners): Set<Coordinate> {
        val (bl, tr) = corners

        val xs = bl.x..tr.x
        val ys = bl.y..tr.y

        return (xs.map { xy(it, bl.y) } +
                xs.map { xy(it, tr.y) } +
                ys.map { xy(bl.x, it) } +
                ys.map { xy(tr.x, it) }).toSet()
    }
}