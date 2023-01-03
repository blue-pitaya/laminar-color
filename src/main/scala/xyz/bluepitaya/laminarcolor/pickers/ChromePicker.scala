package xyz.bluepitaya.laminarcolor.pickers

import com.raquo.laminar.api.L._
import org.scalajs.dom
import xyz.bluepitaya.laminarcolor.ColorPicker
import xyz.bluepitaya.laminarcolor.Saturation
import xyz.bluepitaya.laminarcolor.SaturationStyles
import xyz.bluepitaya.laminarcolor.ColorField

object ChromePicker {
  val saturationStyle = Seq(cls("chromePickerSaturationContainer"))

  val circleStyle = SaturationStyles.circle(12, 12)

  def component(color: Var[ColorPicker.Hsv]) = {
    val colorField = ColorField
      .component(color, ColorField.lightBorderStyle ++ ColorField.circleStyle)

    div(
      width("225px"),
      Saturation.component(color, saturationStyle, circleStyle),
      div(
        padding("16px 16px 12px"),
        div(width("16px"), height("16px"), colorField)
      )
    )
  }
}
