package xyz.bluepitaya.laminarcolor.pickers

import com.raquo.laminar.api.L._
import org.scalajs.dom
import xyz.bluepitaya.laminarcolor.ColorPicker
import xyz.bluepitaya.laminarcolor.Saturation
import xyz.bluepitaya.laminarcolor.ColorField
import xyz.bluepitaya.laminarcolor.Circles
import xyz.bluepitaya.laminarcolor.Sliders
import xyz.bluepitaya.laminarcolor.TextFields

object ChromePicker {
  val saturationStyle = Seq(cls("chromePickerSaturationContainer"))

  val saturationHandler = Circles.blankCircle(12, 12)

  def component(mColor: Var[ColorPicker.Hsv]) = {
    val colorField = ColorField
      .component(mColor, ColorField.lightBorderStyle ++ ColorField.circleStyle)

    val sliders = div(
      flex("1 1 0%"),
      Sliders.hueComponent(mColor, 10).amend(marginBottom("8px")),
      Sliders.alphaComponent(mColor, 10)
    )

    val colorFiledComp = div(
      width("32px"),
      div(
        width("16px"),
        height("16px"),
        marginRight("5px"),
        marginTop("6px"),
        colorField
      )
    )

    val hexInput = TextFields
      .hexField(mColor)
      .amend(
        spellCheck(false),
        boxSizing.borderBox,
        width("100%"),
        textAlign.center,
        fontSize("11px"),
        color("rgb(51, 51, 51)"),
        borderRadius("2px"),
        border("none"),
        boxShadow("rgb(218 218 218) 0px 0px 0px 1px inset"),
        height("21px")
      )

    div(
      width("225px"),
      background("#fff"),
      borderRadius("2px"),
      boxShadow("rgb(0 0 0 / 30%) 0px 0px 2px, rgb(0 0 0 / 30%) 0px 4px 8px"),
      Saturation.component(mColor, saturationStyle, saturationHandler),
      div(
        padding("16px 16px 12px"),
        div(display.flex, flexDirection.row, colorFiledComp, sliders),
        div(
          marginTop("16px"),
          width("100%"),
          hexInput,
          span(
            display("block"),
            fontSize("11px"),
            lineHeight("11px"),
            color("rgb(150, 150, 150)"),
            textAlign.center,
            marginTop("12px"),
            "HEX"
          )
        )
      )
    )
  }
}
