package me.azimmuradov.svc.engine.geometry.shapes

import me.azimmuradov.svc.engine.geometry.CanonicalCorners
import me.azimmuradov.svc.engine.geometry.Coordinate

interface PlacedShapeStrategy {

    fun coordinates(corners: CanonicalCorners): Set<Coordinate>
}

fun PlacedShapeStrategy.coordinates(
    a: Coordinate,
    b: Coordinate,
): Set<Coordinate> = coordinates(corners = CanonicalCorners.fromTwoCoordinates(a, b))