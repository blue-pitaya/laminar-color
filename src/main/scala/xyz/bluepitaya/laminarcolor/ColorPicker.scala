package xyz.bluepitaya.laminarcolor

import com.raquo.laminar.api.L._
import org.scalajs.dom

object ColorPicker {
  case class Hsv(h: Double, s: Double, v: Double) {
    // input: h in [0,360] and s,v in [0,1] - output: r,g,b in [0,1]
    //  let f= (n,k=(n+h/60)%6) => v - v*s*Math.max( Math.min(k,4-k,1), 0);
    //  return [f(5),f(3),f(1)];
    def toCssRgb: String = {
      def f(n: Double) = {
        val k = (n + h / 60) % 6
        v - v * s * Math.max(Math.min(Math.min(k, 4 - k), 1), 0)
      }

      val r = (f(5) * 255).toInt
      val g = (f(3) * 255).toInt
      val b = (f(1) * 255).toInt

      s"rgb($r, $g, $b)"
    }
  }

  def component = {
    val hsv = Var[Hsv](Hsv(0, 0, 0))

    div(Saturation.component(hsv), Hue.component(hsv), HexValue.component(hsv))
  }
}
