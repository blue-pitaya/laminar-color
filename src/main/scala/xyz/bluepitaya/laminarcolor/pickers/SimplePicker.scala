package xyz.bluepitaya.laminarcolor.pickers

import com.raquo.laminar.api.L._
import org.scalajs.dom
import xyz.bluepitaya.laminarcolor.Circles
import xyz.bluepitaya.laminarcolor.ColorField
import xyz.bluepitaya.laminarcolor.Palette
import xyz.bluepitaya.laminarcolor.Saturation
import xyz.bluepitaya.laminarcolor.Sliders
import xyz.bluepitaya.laminarcolor.Hsv
import xyz.bluepitaya.laminarcolor.State

object SimplePicker {
  def component[A](s: A)(implicit state: State[A]) = {
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
      Sliders.hueComponent(s, 10, sliderHandler)
    )

    val colorFieldComp = div(
      minWidth("20px"), // TODO: hack
      height("20px"),
      ColorField
        .component(s)
        .amend(ColorField.darkBorderStyle, ColorField.circleStyle)
    )

    def colorButton(c: Hsv) = div(
      width("16px"),
      height("16px"),
      padding("0px 10px 10px 0px"),
      Palette.defaultColorButton(s, c)
    )

    div(
      containerStyle,
      Saturation.component(s, saturationHandler).amend(height("150px")),
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
