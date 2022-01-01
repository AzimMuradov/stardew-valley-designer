package me.azimmuradov.svc.engine.geometry


fun Coordinate.toVector(): Vector = vec(x, y)

fun Vector.toCoordinate(): Coordinate = xy(x, y)


fun Rect.toCoordinate(): Coordinate = xy(x = w, y = h)

fun Rect.toVector(): Vector = vec(x = w, y = h)


operator fun Coordinate.minus(other: Coordinate): Vector = vec(x = this.x - other.x, y = this.y - other.y)

operator fun Coordinate.plus(other: Vector): Coordinate = xy(x = this.x + other.x, y = this.y + other.y)

operator fun Coordinate.minus(other: Vector): Coordinate = xy(x = this.x - other.x, y = this.y - other.y)