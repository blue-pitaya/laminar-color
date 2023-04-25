package dev.bluepitaya.laminarcolor

import com.raquo.laminar.api.L._
import dev.bluepitaya.laminarcolor.models.Hsv

object ColorValue {
  def rgbComponent(hsv: Var[Hsv]) =
    div(child.text <-- hsv.signal.map(_.toCssRgb))
}
