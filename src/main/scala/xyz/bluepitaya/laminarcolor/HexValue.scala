package xyz.bluepitaya.laminarcolor

import com.raquo.laminar.api.L._
import org.scalajs.dom

object HexValue {
  def component(hsv: Var[ColorPicker.Hsv]) =
    div(child.text <-- hsv.signal.map(_.toCssRgb))
}
