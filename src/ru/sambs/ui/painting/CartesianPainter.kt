package ru.sambs.ui.painting

import java.awt.*

class CartesianPainter(private val plane: CartesianPlane) : Painter
{


   var fontSize: Int = 16
    var intTickColor: Color = Color.RED
    var axesColor: Color = Color.BLACK

    override fun paint(g: Graphics){
        paintAxes(g)
        paintLabels(g)
        paintTicks(g)
    }

    private fun paintLabels(g: Graphics) {
        g.font = Font("Cambria", Font.BOLD, 14)
        val m = g.fontMetrics

        var x0 = plane.xCrt2Scr(0.0)
        var y0 = plane.yCrt2Scr(0.0)
        if (x0 <= 0) x0 = 0

        if (x0 > plane.width) x0 = plane.width
        if (y0 <= 0) y0 = 0
        if (y0 > plane.height) y0 = plane.height

        g.color = Color.BLACK

        val kX = when {
            plane.xMax - plane.xMin < 10 -> { 10.0 }
            plane.xMax - plane.xMin < 24 -> { 5.0 }
            plane.xMax - plane.xMin < 36 -> { 2.5 }
            else -> { 1.25 }
        }

        for (i in (plane.xMin * kX).toInt() until (plane.xMax * kX).toInt()) {
            if (i % 5 != 0 || i == 0) continue
            val x = plane.xCrt2Scr(((i / kX)))
            val dy = m.getStringBounds((((i / kX))).toString(), g).height.toInt()
            val dx = m.getStringBounds(((i / kX)).toString(), g).width.toInt()
            val dt: Int = if (y0 > plane.height - dy) dy + 5 else 0
            g.drawString((i / kX).toString(), x - dx / 2, y0 + dy - dt)
        }

        val kY = when {
            plane.yMax - plane.yMin < 10 -> { 10.0 }
            plane.yMax - plane.yMin < 24 -> { 5.0 }
            plane.yMax - plane.yMin < 36 -> { 2.5 }
            else -> { 1.25 }
        }
        for (i in (plane.yMin * kY).toInt() until (plane.yMax * kY).toInt()) {
            if (i % 5 != 0 || i == 0) continue
            val y = plane.yCrt2Scr((i / kY))
            val dy = m.getStringBounds((i / kY).toString(), g).height.toInt()
            val dx = m.getStringBounds((i / kY).toString(), g).width.toInt()
            val dt: Int = if (x0 > plane.width - dx) 10 + dx else 0
            g.drawString((i / kY).toString(), x0 + 4 - dt, y + dy / 3)
        }
    }

    private fun paintAxes(g: Graphics){
        with(plane) {
            (g as Graphics2D).apply {
                stroke = BasicStroke(3F)
                color = axesColor
                if (yMin <= 0 && yMax >= 0) {
                    drawLine(0, yCrt2Scr(0.0), width, yCrt2Scr(0.0))
                } else {
                    drawLine(0, 0, width, 0)
                    drawLine(0, height, width, height)
                }

                if (xMin <= 0 && xMax >= 0) {
                    drawLine(xCrt2Scr(0.0), 0, xCrt2Scr(0.0), height)
                } else {
                    drawLine(0, 0, 0, height)
                    drawLine(width, 0, width, height)
                }
            }
        }
    }
    private fun paintTicks(g: Graphics) {
        val dt = 3
        g.color = Color.BLACK
        var x0 =  plane.xCrt2Scr(0.0)
        var y0 = plane.yCrt2Scr(0.0)

        if (x0 <= 0) x0 = 0
        if (x0 >plane.width) x0 = plane.width
        if (y0 <= 0) y0 = 0
        if (y0 > plane.height) y0 = plane.height

        val kX = when {
            plane.xMax - plane.xMin < 10 -> { 10.0 }
            plane.xMax - plane.xMin < 24 -> { 5.0 }
            plane.xMax - plane.xMin < 36 -> { 2.5 }
            else -> { 1.25 }
        }
        for (i in (plane.xMin * kX).toInt() until (plane.xMax * kX).toInt()) {

            val gap = if (i % 10 == 0) 2 else if (i % 5 == 0) 1 else 0
            val x = plane.xCrt2Scr((i / kX))
            g.drawLine(x, y0 - dt - gap, x, y0 + dt + gap)
        }
        val kY = when {
            plane.yMax - plane.yMin < 10 -> { 10.0 }
            plane.yMax - plane.yMin < 24 -> { 5.0 }
            plane.yMax - plane.yMin < 36 -> { 2.5 }
            else -> { 1.25 }
        }
        for (i in (plane.yMin * kY).toInt() until (plane.yMax * kY).toInt()) {
            val gap = if (i % 10 == 0) 2 else if (i % 5 == 0) 1 else 0
            val y = plane.yCrt2Scr((i / kY))
            g.drawLine(x0 - dt - gap, y, x0 + dt + gap, y)
        }
    }


}