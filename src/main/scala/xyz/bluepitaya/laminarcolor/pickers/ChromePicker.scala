package xyz.bluepitaya.laminarcolor.pickers

import com.raquo.laminar.api.L._
import xyz.bluepitaya.laminarcolor.Circles
import xyz.bluepitaya.laminarcolor.ColorField
import xyz.bluepitaya.laminarcolor.Saturation
import xyz.bluepitaya.laminarcolor.Sliders
import xyz.bluepitaya.laminarcolor.TextFields
import xyz.bluepitaya.laminarcolor.models.Hsv

object ChromePicker {
  def component(colorSignal: Signal[Hsv], onColorChanged: Observer[Hsv]) = {
    val colorField = ColorField
      .component(colorSignal)
      .amend(ColorField.lightBorderStyle, ColorField.circleStyle)

    def handler = Circles.filledCircle(12, 12)

    val sliders = div(
      flex("1 1 0%"),
      Sliders
        .hueComponent(10, handler, colorSignal, onColorChanged)
        .amend(marginBottom("8px")),
      Sliders.alphaComponent(10, handler, colorSignal, onColorChanged)
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
      .hexField(colorSignal, onColorChanged)
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

    val saturationHandler = Circles.blankCircle(12, 12)

    div(
      width("225px"),
      background("#fff"),
      borderRadius("2px"),
      boxShadow("rgb(0 0 0 / 30%) 0px 0px 2px, rgb(0 0 0 / 30%) 0px 4px 8px"),
      Saturation
        .component(saturationHandler, colorSignal, onColorChanged)
        .amend(height("125px")),
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
