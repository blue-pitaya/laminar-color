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

object SketchPicker {
  def component[A](s: A)(implicit state: State[A]) = {
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
      Sliders.hueComponent(s, 10, sliderHandler).amend(marginBottom("4px")),
      Sliders.alphaComponent(s, 10, sliderHandler)
    )

    val colorFieldComp = div(
      minWidth("24px"), // TODO: hack
      height("24px"),
      ColorField.component(s).amend(ColorField.darkBorderStyle)
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
