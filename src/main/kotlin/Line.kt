import kotlin.math.sqrt

class Line(p1: Point, p2: Point) : Shape() {
    var p1: Point = p1
        private set

    var p2: Point = p2
        private set

    init {
        require(p1 != p2) { "A line cannot be formed by identical points (a and b must be distinct)." }
    }

    fun getSlope(): Double {
        if (p1.x == p2.x) return Double.POSITIVE_INFINITY
        return (p2.y - p1.y) / (p2.x - p1.x)
    }

    fun getLength(): Double {
        val deltaX = p2.x - p1.x
        val deltaY = p2.y - p1.y
        return sqrt(deltaX * deltaX + deltaY * deltaY)
    }

    override fun move(deltaX: Double, deltaY: Double) {
        p1.move(deltaX, deltaY)
        p2.move(deltaX, deltaY)
    }

}