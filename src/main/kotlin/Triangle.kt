private const val EPSILON = 1e-9

class Triangle(p1: Point, p2: Point, p3: Point) : Solid() {
    var p1: Point = p1
        private set

    var p2: Point = p2
        private set

    var p3: Point = p3
        private set

    private val determinant = (p2.x - p1.x) * (p3.y - p1.y) - (p2.y - p1.y) * (p3.x - p1.x)

    init {
        require(p1 != p2 && p1 != p3) { "A triangle cannot be formed by identical points." }
        require(kotlin.math.abs(determinant) < EPSILON) { "A triangle cannot be formed by collinear points" }
    }

    override fun move(deltaX: Double, deltaY: Double) {
        p1.move(deltaX, deltaY)
        p2.move(deltaX, deltaY)
        p3.move(deltaX, deltaY)
    }

    override fun getArea(): Double {
        return 0.5 * kotlin.math.abs(determinant)
    }

}