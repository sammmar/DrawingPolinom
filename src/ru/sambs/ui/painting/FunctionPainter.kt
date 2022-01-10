package ru.sambs.ui.painting


import java.awt.*
import kotlin.math.pow
import kotlin.math.sin

class FunctionPainter(private val plane: CartesianPlane,val function:(Double)->Double) : Painter{

    var funColor= Color.BLUE


    //fun setColor(newColor: Color){funColor = newColor}//присваиваем функции новый цвет
    //private val polynom = mutableListOf<Polynom>()
    // lateinit var polynom: (Double)->Double
    //lateinit var polynom:(Double)->Double


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

                    //for (i in 0 until width) {
                    for (i in 0 until width) {
                        setRenderingHints(rh)

                            for (i in (xCrt2Scr(-5.0)..xCrt2Scr(5.0))) {

                                drawLine(
                                    i,
                                    yCrt2Scr((function(xScr2Crt(i))*2)),
                                    i+1,
                                    yCrt2Scr((function(xScr2Crt(i+1))*2)),
                                )
                            }
                        }
                    }
            }
            }
        }


