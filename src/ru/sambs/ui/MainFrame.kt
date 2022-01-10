package ru.sambs.ui

import ru.sambs.ui.painting.CartesianPainter
import ru.sambs.ui.painting.CartesianPlane
import ru.sambs.ui.painting.FunctionPainter
import ru.sambs.ui.painting.ParamFunctionPainter
import java.awt.Color
import java.awt.Dimension
import java.awt.event.*
import javax.swing.*
import kotlin.math.abs
class MainFrame : JFrame(){
    private val minDim = Dimension(600, 700)
    private val mainPanel: GraphicsPanel
    private val controlPanel: JPanel


    private val xMinM: SpinnerNumberModel
    private val yMinM: SpinnerNumberModel
    private val xMaxM: SpinnerNumberModel
    private val yMaxM: SpinnerNumberModel
    private val tMaxM: SpinnerNumberModel
    private val tMinM: SpinnerNumberModel
    private val xMin: JSpinner
    private val yMin:JSpinner
    private val xMax: JSpinner
    private val yMax:JSpinner
    private val tMin: JSpinner
    private val tMax:JSpinner

    private val colorpanelGraphic: JPanel
    private val colorpanelParam: JPanel
    private val textXMin: JLabel
    private  val textXMax: JLabel
    private val textYMin: JLabel
    private val textYMax: JLabel
    private val textTMax: JLabel
    private val textTMin: JLabel

    private val textGraphic: JLabel
    private val textParam: JLabel


    init {
        minimumSize = minDim
        defaultCloseOperation = EXIT_ON_CLOSE
        xMinM = SpinnerNumberModel(-5.0, -100.0, 4.9, 0.1)
        xMin = JSpinner(xMinM)
        xMaxM = SpinnerNumberModel(5.0, -4.9, 100.0, 0.1)
        xMax = JSpinner(xMaxM)
        yMinM = SpinnerNumberModel(-5.0, -100.0, 4.9, 0.1)
        yMin = JSpinner(yMinM)
        yMaxM = SpinnerNumberModel(5.0, -4.9, 100.0, 0.1)
        yMax = JSpinner(yMaxM)
        tMinM=SpinnerNumberModel(-5.0, -100.0, 4.9, 0.1)
        tMin=JSpinner(tMinM)
        tMaxM = SpinnerNumberModel(5.0, -4.9, 100.0, 0.1)
        tMax = JSpinner(tMaxM)

        val mainPlane = CartesianPlane(
            xMinM.value as Double,
            xMaxM.value as Double,
            yMinM.value as Double,
            yMaxM.value as Double
        )

        val cartesianPainter = CartesianPainter(mainPlane)//отрисовка системы координат

        val funk = { x:Double ->  3*x-x*x*x }
        val functionPainter=FunctionPainter(mainPlane,funk)
        val x = {t:Double -> (t*t-1)/(t*(t+2)) }
        val y = {t:Double -> (t*t)/((t+2)*(t+1)) }
        val paramPainter = ParamFunctionPainter(x,y,mainPlane)
        val painters = mutableListOf(cartesianPainter,functionPainter,paramPainter)
        mainPanel = GraphicsPanel(painters).apply {
            background = Color.WHITE
        }

        textXMin= JLabel().apply {
            text="Xmin"
        }
        textXMax=JLabel().apply {
            text="Xmax"
        }
        textYMin= JLabel().apply {
            text="Ymin"
        }
        textYMax=JLabel().apply {
            text="Ymax"
        }
        textTMin= JLabel().apply {
            text="tmin"
        }
        textTMax=JLabel().apply {
            text="tmax"
        }


        textGraphic= JLabel().apply {
            text="график функции в явном виде"
        }

        textParam= JLabel().apply {
            text="график функции в параметрическом виде"
        }


        colorpanelGraphic=JPanel().apply{
            background = functionPainter.funColor
        }
        colorpanelParam=JPanel().apply{
            background = functionPainter.funColor
        }

        colorpanelGraphic.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent?) {
                val background = JColorChooser.showDialog(null, "Select a color", Color.RED)
                colorpanelGraphic.background=background
                functionPainter.funColor=background
                mainPanel.repaint()
            }
        })
        colorpanelParam.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent?) {
                val background = JColorChooser.showDialog(null, "Select a color", Color.BLUE)
                colorpanelParam.background=background
                functionPainter.funColor=background
                mainPanel.repaint()
            }
        })
        mainPlane.pixelSize = mainPanel.size
        mainPanel.addComponentListener(object: ComponentAdapter() {
            override fun componentResized(e: ComponentEvent?) {
                mainPlane.pixelSize = mainPanel.size
                mainPanel.repaint()
            }
        })


        controlPanel= JPanel()
        layout = GroupLayout(contentPane).apply{
            setHorizontalGroup(
                createSequentialGroup()
                    .addGap(4)
                    .addGroup(
                        createParallelGroup()
                            .addComponent(mainPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                            .addComponent(controlPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                    )
                    .addGap(4)
            )

            setVerticalGroup(
                createSequentialGroup()
                    .addGap(4)
                    .addComponent(mainPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                    .addGap(4)
                    .addComponent(controlPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(4)
            )
        }
        controlPanel.layout=GroupLayout(controlPanel).apply{ //делаем контрольную панель
            setHorizontalGroup(createSequentialGroup()
                .addGap(8)

                .addGroup(
                    createParallelGroup()
                        .addComponent(textXMin, GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE)
                        .addComponent(textYMin, GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE)
                        .addComponent(textTMin, GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE)
                )
                .addGap(8)
                .addGroup(
                    createParallelGroup()
                        .addComponent(xMin, 100,  GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE)
                        .addComponent(yMin, 100,  GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE)
                        .addComponent(tMin, 100,  GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE)
                )

                .addGap(30)
                .addGroup(
                    createParallelGroup()
                        .addComponent(textXMax, GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE)
                        .addComponent(textYMax, GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE)
                        .addComponent(textTMax, GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE)
                )
                .addGap(8)
                .addGroup(
                    createParallelGroup()
                        .addComponent(xMax, 100,  GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE)
                        .addComponent(yMax, 100,  GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE)
                        .addComponent(tMax, 100,  GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE)
                )
                .addGap(30)
                .addGroup(
                    createParallelGroup()

                )
                .addGap(30)
                .addGroup(
                    createParallelGroup()

                        .addComponent(textGraphic, GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE)
                        .addComponent(textParam, GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE)

                )

                .addGap(10)
                .addGroup(
                    createParallelGroup()

                        .addComponent(colorpanelGraphic, GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE)
                        .addComponent(colorpanelParam, GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE)

                )

            )
            setVerticalGroup(createSequentialGroup()
                .addGap(8)
                .addGroup(
                    createParallelGroup()
                        //.addComponent(chButton1, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(textXMin, GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE)
                        .addComponent(xMin,GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE)
                        .addComponent(textXMax, GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE)
                        .addComponent(xMax, GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE)

                )
                .addGap(8)
                .addGroup(
                    createParallelGroup()
                        //.addComponent(chButton2, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(textYMin, GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE)
                        .addComponent(yMin, GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE)
                        .addComponent(textYMax, GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE)
                        .addComponent(yMax, GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE)
                        .addComponent(textGraphic, GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE)

                        .addComponent(colorpanelGraphic, GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE)
                        //.addComponent(colorpanelParam, GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE)
                )
                .addGap(8)
                .addGroup(
                    createParallelGroup()
                        .addComponent(textTMin, GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE)
                        .addComponent(tMin,GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE)
                        .addComponent(textTMax, GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE)
                        .addComponent(tMax, GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE)
                        .addComponent(textParam, GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE)
                        .addComponent(colorpanelParam, GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE,  GroupLayout.PREFERRED_SIZE)

                )
                .addGap(8)
            )

        }



        xMin.addChangeListener{
            xMaxM.minimum = xMin.value as Double + 0.1
            mainPlane.xSegment = Pair(xMin.value as Double, xMax.value as Double)
            mainPanel.repaint()
        }
        xMax.addChangeListener{
            xMinM.maximum = xMax.value as Double - 0.1
            mainPlane.xSegment = Pair(xMin.value as Double, xMax.value as Double)
            mainPanel.repaint()
        }
        yMin.addChangeListener{
            yMaxM.minimum = yMin.value as Double + 0.1
            mainPlane.ySegment = Pair(yMin.value as Double, yMax.value as Double)
            mainPanel.repaint()
        }
        yMax.addChangeListener{
            yMinM.maximum = yMax.value as Double - 0.1
            mainPlane.ySegment = Pair(yMin.value as Double, yMax.value as Double)
            mainPanel.repaint()
        }
        tMin.addChangeListener{
            tMaxM.minimum = tMin.value as Double + 0.1
            mainPlane.tMax = tMax.value as Double
            mainPlane.tMin = tMin.value as Double
            mainPanel.repaint()
        }
        tMax.addChangeListener{
            tMinM.maximum = tMax.value as Double - 0.1
            mainPlane.tMax = tMax.value as Double
            mainPlane.tMin = tMin.value as Double
            mainPanel.repaint()
        }

        pack()
        mainPlane.width = mainPanel.width
        mainPlane.height = mainPanel.height
    }
}