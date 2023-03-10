package xyz.bluepitaya.laminarcolor.pickers

import com.raquo.laminar.api.L._
import xyz.bluepitaya.common.Hsv
import xyz.bluepitaya.laminarcolor.Circles
import xyz.bluepitaya.laminarcolor.ColorField
import xyz.bluepitaya.laminarcolor.Saturation
import xyz.bluepitaya.laminarcolor.Sliders

object SimplePicker {
  def component(colorSignal: Signal[Hsv], onColorChanged: Observer[Hsv]) = {
    val containerStyle = Seq(
      width("200px"),
      height("fit-content"),
      padding("10px 10px  0px"),
      backgroundColor("#fff"),
      borderRadius("4px"),
      boxShadow(
        "rgb(0 0 0 / 15%) 0px 0px 0px 1px, rgb(0 0 0 / 15%) 0px 8px 16px"
      )
    )

    val saturationHandler = Circles.blankCircleSpecialShadow(4, 4)

    def sliderHandler = Circles.filledCircle(12, 12)

    val sliders = div(
      width("100%"),
      marginLeft("8px"),
      alignSelf.center,
      Sliders.hueComponent(10, sliderHandler, colorSignal, onColorChanged)
    )

    val colorFieldComp = div(
      minWidth("20px"), // TODO: hack
      height("20px"),
      ColorField
        .component(colorSignal)
        .amend(ColorField.darkBorderStyle, ColorField.circleStyle)
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
        colorFieldComp,
        sliders
      )
    )
  }
}
