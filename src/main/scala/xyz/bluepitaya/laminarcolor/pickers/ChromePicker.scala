package xyz.bluepitaya.laminarcolor.pickers

import com.raquo.laminar.api.L._
import org.scalajs.dom
import xyz.bluepitaya.laminarcolor.ColorPicker
import xyz.bluepitaya.laminarcolor.Saturation
import xyz.bluepitaya.laminarcolor.SaturationStyles

object ChromePicker {
  val saturationStyle = Seq(cls("chromePickerSaturationContainer"))

  val circleStyle = SaturationStyles.circle(12, 12)

  def component(color: Var[ColorPicker.Hsv]) = div(
    width("225px"),
    Saturation.component(color, saturationStyle, circleStyle),
    div(padding("16px 16px 12px"), backgroundColor("red"))
  )
}
