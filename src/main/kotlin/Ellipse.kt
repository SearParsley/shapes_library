import kotlin.math.PI

private const val EPSILON = 1e-9

class Ellipse(center: Point, radiusX: Double, radiusY: Double) : Solid() {
    var center: Point = center
        private set

    var radiusX: Double = radiusX
        private set

    var radiusY: Double = radiusY
        private set

    init {
        require(radiusX >= EPSILON && radiusY >= EPSILON) { "An ellipse cannot be formed with a non-positive radius." }
    }

    override fun move(deltaX: Double, deltaY: Double) {
        center.move(deltaX, deltaY)
    }

    override fun getArea(): Double {
        return PI * radiusX * radiusY
    }

}