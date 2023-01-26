package xyz.bluepitaya.laminarcolor

import com.raquo.laminar.api.L._
import xyz.bluepitaya.common.Hsv

object ColorValue {
  def rgbComponent(hsv: Var[Hsv]) =
    div(child.text <-- hsv.signal.map(_.toCssRgb))
}
