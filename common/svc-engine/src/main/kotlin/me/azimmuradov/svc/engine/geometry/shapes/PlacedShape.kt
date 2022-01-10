package me.azimmuradov.svc.engine.geometry.shapes

import me.azimmuradov.svc.engine.geometry.CanonicalCorners
import me.azimmuradov.svc.engine.geometry.Coordinate

data class PlacedShape(
    val corners: CanonicalCorners,
    val strategy: PlacedShapeStrategy,
) {

    val coordinates: Set<Coordinate> by lazy { strategy.coordinates(corners) }
}