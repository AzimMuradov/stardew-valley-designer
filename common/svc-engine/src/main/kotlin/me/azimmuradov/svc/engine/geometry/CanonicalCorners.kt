package me.azimmuradov.svc.engine.geometry

data class CanonicalCorners(
    val bottomLeft: Coordinate,
    val topRight: Coordinate,
) {

    init {
        require(bottomLeft.x <= topRight.x && bottomLeft.y <= topRight.y)
    }

    companion object {

        fun fromTwoCoordinates(a: Coordinate, b: Coordinate): CanonicalCorners = CanonicalCorners(
            bottomLeft = xy(
                x = minOf(a.x, b.x),
                y = minOf(a.y, b.y)
            ),
            topRight = xy(
                x = maxOf(a.x, b.x),
                y = maxOf(a.y, b.y)
            )
        )
    }
}