package xyz.bluepitaya.laminarcolor.pickers

import com.raquo.laminar.api.L._
import org.scalajs.dom
import xyz.bluepitaya.laminarcolor.ColorPicker
import xyz.bluepitaya.laminarcolor.Saturation
import xyz.bluepitaya.laminarcolor.Circles
import xyz.bluepitaya.laminarcolor.Sliders
import xyz.bluepitaya.laminarcolor.ColorField

object SketchPicker {
  def component(color: Var[ColorPicker.Hsv]) = {
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
      Sliders.hueComponent(color, 10, sliderHandler).amend(marginBottom("4px")),
      Sliders.alphaComponent(color, 10, sliderHandler)
    )

    val colorFieldComp = div(
      minWidth("24px"), // TODO: hack
      height("24px"),
      ColorField.component(color, ColorField.darkBorderStyle)
    )

    div(
      containerStyle,
      Saturation.component(color, saturationHandler).amend(height("150px")),
      div(
        display.flex,
        flexDirection.row,
        width("100%"),
        padding("4px 0px"),
        sliders,
        colorFieldComp
      )
    )
  }
}
