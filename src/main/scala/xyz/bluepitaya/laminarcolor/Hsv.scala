package xyz.bluepitaya.laminarcolor

import com.raquo.laminar.api.L._
import org.scalajs.dom

case class Hsv(h: Double, s: Double, v: Double, a: Double) {
  // https://stackoverflow.com/a/54024653
  def toRgba: Rgba = {
    def f(n: Double) = {
      val k = (n + h / 60) % 6
      v - v * s * Math.max(Math.min(Math.min(k, 4 - k), 1), 0)
    }

    def c(v: Double) = Math.round(v * 255).toInt

    val r = c(f(5))
    val g = c(f(3))
    val b = c(f(1))

    Rgba(r, g, b, a)
  }

  def toCssRgb: String = {
    val rgba = toRgba
    s"rgb(${rgba.r}, ${rgba.g}, ${rgba.b})"
  }

  def toCssRgba: String = {
    val rgba = toRgba
    s"rgb(${rgba.r}, ${rgba.g}, ${rgba.b}, ${rgba.a})"
  }

  def toCssRgbaTransparent: String = {
    val rgba = toRgba
    s"rgb(${rgba.r}, ${rgba.g}, ${rgba.b}, 0)"
  }

  def toHashedHexValue: String = {
    val rgba = toRgba
    f"#${rgba.r}%02X${rgba.g}%02X${rgba.b}%02X"
  }
}

object Hsv {
  // Taken from java.awt.Color.RGBtoHSB (rewrited to scala)
  def fromRgb(red: Int, green: Int, blue: Int): Hsv = {
    var h: Double = 0
    var s: Double = 0
    var v: Double = 0
    // Calculate brightness.
    var min: Double = 0;
    var max: Double = 0;
    if (red < green) {
      min = red;
      max = green;
    } else {
      min = green;
      max = red;
    }
    if (blue > max) max = blue;
    else if (blue < min) min = blue;
    v = max / 255.0;
    // Calculate saturation.
    if (max == 0) s = 0;
    else s = ((max - min) / max);
    // Calculate hue.
    if (s == 0) h = 0;
    else {
      var delta = (max - min) * 6;
      if (red == max) h = (green - blue) / delta;
      else if (green == max) h = 1.0 / 3 + (blue - red) / delta;
      else h = 2.0 / 3 + (red - green) / delta;
      if (h < 0) h = h + 1;
    }

    Hsv(h * 360.0, s, v, 1)
  }

  def fromHashedHexValue(v: String): Option[Hsv] = {
    try {
      val r = Integer.parseInt(v.slice(1, 3), 16)
      val g = Integer.parseInt(v.slice(3, 5), 16)
      val b = Integer.parseInt(v.slice(5, 7), 16)
      Some(fromRgb(r, g, b))
    } catch {
      case _: Throwable => None
    }

  }

  val sketchColors = Seq(
    "#D0021B",
    "#F5A623",
    "#F8E71C",
    "#8B572A",
    "#7ED321",
    "#417505",
    "#BD10E0",
    "#9013FE",
    "#4A90E2",
    "#50E3C2",
    "#B8E986",
    "#000000",
    "#4A4A4A",
    "#9B9B9B",
    "#FFFFFF"
  ).map(fromHashedHexValue).flatten
}
