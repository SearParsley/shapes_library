class Line(a: Point, b: Point) : Shape() {
    var a: Point = a
        private set

    var b: Point = b
        private set

    init {
        require(a != b) { "A line cannot be formed by identical points (a and b must be distinct)." }
    }

    fun getSlope(): Double {
        if (a.x == b.x) return Double.POSITIVE_INFINITY
        return (b.y - a.y) / (b.x - a.x)
    }

    fun getLength(): Double {
        val deltaX = b.x - a.x
        val deltaY = b.y - a.y
        return kotlin.math.sqrt(deltaX * deltaX + deltaY * deltaY)
    }

    override fun move(deltaX: Double, deltaY: Double) {
        this.a.move(deltaX, deltaY)
        this.b.move(deltaX, deltaY)
    }

}