package xyz.bluepitaya.laminarcolor.pickers

import com.raquo.laminar.api.L._
import org.scalajs.dom
import xyz.bluepitaya.laminarcolor.ColorPicker
import xyz.bluepitaya.laminarcolor.Saturation
import xyz.bluepitaya.laminarcolor.ColorField
import xyz.bluepitaya.laminarcolor.Hue
import xyz.bluepitaya.laminarcolor.Circles

object ChromePicker {
  val saturationStyle = Seq(cls("chromePickerSaturationContainer"))

  val saturationHandler = Circles.blankCircle(12, 12)

  def component(color: Var[ColorPicker.Hsv]) = {
    val colorField = ColorField
      .component(color, ColorField.lightBorderStyle ++ ColorField.circleStyle)

    div(
      width("225px"),
      Saturation.component(color, saturationStyle, saturationHandler),
      div(
        display.flex,
        flexDirection.row,
        padding("16px 16px 12px"),
        div(width("16px"), height("16px"), marginRight("5px"), colorField),
        div(width("100%"), Hue.component(color, 16))
      )
    )
  }
}
