package xyz.bluepitaya.laminarcolor.pickers

import com.raquo.laminar.api.L._
import xyz.bluepitaya.common.Hsv
import xyz.bluepitaya.laminarcolor.Circles
import xyz.bluepitaya.laminarcolor.ColorField
import xyz.bluepitaya.laminarcolor.Palette
import xyz.bluepitaya.laminarcolor.Saturation
import xyz.bluepitaya.laminarcolor.Sliders

object SketchPicker {
  def component(colorSignal: Signal[Hsv], onColorChanged: Observer[Hsv]) = {
    val containerStyle = Seq(
      width("200px"),
      padding("10px 10px  0px"),
      backgroundColor("#fff"),
      borderRadius("4px"),
      boxShadow(
        "rgb(0 0 0 / 15%) 0px 0px 0px 1px, rgb(0 0 0 / 15%) 0px 8px 16px"
      )
    )

    val saturationHandler = Circles.blankCircleSpecialShadow(4, 4)

    // FIXME: handler should not go outside slider
    def sliderHandler = Circles.rectangle(4, 8)

    val sliders = div(
      width("100%"),
      marginRight("4px"),
      Sliders
        .hueComponent(10, sliderHandler, colorSignal, onColorChanged)
        .amend(marginBottom("4px")),
      Sliders.alphaComponent(10, sliderHandler, colorSignal, onColorChanged)
    )

    val colorFieldComp = div(
      minWidth("24px"), // TODO: hack
      height("24px"),
      ColorField.component(colorSignal).amend(ColorField.darkBorderStyle)
    )

    def colorButton(c: Hsv) = div(
      width("16px"),
      height("16px"),
      padding("0px 10px 10px 0px"),
      Palette.defaultColorButton(c, onColorChanged)
    )

    div(
      containerStyle,
      Saturation
        .component(saturationHandler, colorSignal, onColorChanged)
        .amend(height("150px")),
      div(
        display.flex,
        flexDirection.row,
        width("100%"),
        padding("4px 0px"),
        sliders,
        colorFieldComp
      ),
      Palette
        .component(8, 2, Hsv.sketchColors, colorButton)
        // TODO: omg, copied from react color but this hack is too much for me
        .amend(
          position.relative,
          margin("0px -10px"),
          padding("10px 0px 0px 10px")
        )
    )
  }
}
