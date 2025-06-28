class Rectangle(p1: Point, p2: Point) : Solid() {
    var p1: Point = p1
        private set

    var p2: Point = p2
        private set

    init {
        require(p1 != p2) { "A rectangle cannot be formed by identical points." }
        require(p1.x != p2.x && p1.y != p2.y) { "A rectangle cannot be formed without width or height." }
    }

    override fun move(deltaX: Double, deltaY: Double) {
        p1.move(deltaX, deltaY)
        p2.move(deltaX, deltaY)
    }

    override fun getArea(): Double {
        return kotlin.math.abs((p1.x - p2.x) * (p1.y - p2.y))
    }

}