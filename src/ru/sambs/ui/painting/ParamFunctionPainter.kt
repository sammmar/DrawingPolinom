package ru.sambs.ui.painting

import java.awt.*

class ParamFunctionPainter(var x: (Double)->Double,var y: (Double)->Double,val plane: CartesianPlane) : Painter
{
    var funColor: Color = Color.RED

    override fun paint(g: Graphics){
        with(g as Graphics2D) {
            color = funColor
            stroke = BasicStroke(4F, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)
            val rh = mapOf(
                RenderingHints.KEY_ANTIALIASING to RenderingHints.VALUE_ANTIALIAS_ON,
                RenderingHints.KEY_INTERPOLATION to RenderingHints.VALUE_INTERPOLATION_BICUBIC,
                RenderingHints.KEY_RENDERING to RenderingHints.VALUE_RENDER_QUALITY,
                RenderingHints.KEY_DITHERING to RenderingHints.VALUE_DITHER_ENABLE
            )
            setRenderingHints(rh)
            with(plane) {
                var i = tMin
                val dt = (tMax-tMin)/1000
                while (i<= tMax) {
                    drawLine(
                        xCrt2Scr(x(i)),
                        yCrt2Scr(y(i)),
                        xCrt2Scr(x(i+dt)),
                        yCrt2Scr(y(i+dt)),
                    )
                    i +=dt
                }
            }

        }
    }
}












/**(
    private val plane: CartesianPlane,
    val x: (Double) -> Double,
    val y: (Double) -> Double,
    val tMin: Double,
    val tMax: Double
) : Painter {
    var funColor= Color.RED
    override fun paint(g: Graphics) {
        with(g as Graphics2D) {
            color = funColor
            stroke = BasicStroke(4F, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)
            val rh = mapOf(
                RenderingHints.KEY_ANTIALIASING to RenderingHints.VALUE_ANTIALIAS_ON,
                RenderingHints.KEY_INTERPOLATION to RenderingHints.VALUE_INTERPOLATION_BICUBIC,
                RenderingHints.KEY_RENDERING to RenderingHints.VALUE_RENDER_QUALITY,
                RenderingHints.KEY_DITHERING to RenderingHints.VALUE_DITHER_ENABLE
            )
            setRenderingHints(rh)
            var t = tMin
            val d = (tMax - tMin) / 1000
            while (t <= tMax) {
                with(plane) {
                    while (t <= tMax) {
                        drawLine(
                            xCrt2Scr(x(t)),
                            yCrt2Scr(y(t)),
                            xCrt2Scr(x(t + d)),
                            yCrt2Scr(y(t + d)),
                        )
                        t += d
                    }

                }
            }
        }
    }
}*/
