package dev.bluepitaya.laminarcolor.models

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

  def toCssHsl: String = {
    val l = (2 - s) * v / 2;
    val _s = l match {
      case _l if l == 0   => 0
      case _l if l == 1.0 => 0
      case _l if l < 0.5  => s * v / (l * 2)
      case _l             => s * v / (2 - l * 2)
    }

    s"hsl($h ${"%.2f".format(_s * 100)}% ${"%.2f".format(l * 100)}%)"
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
      val delta = (max - min) * 6;
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
    fromRgb(0xd0, 0x02, 0x1b),
    fromRgb(0xf5, 0xa6, 0x23),
    fromRgb(0xf8, 0xe7, 0x1c),
    fromRgb(0x8b, 0x57, 0x2a),
    fromRgb(0x7e, 0xd3, 0x21),
    fromRgb(0x41, 0x75, 0x05),
    fromRgb(0xbd, 0x10, 0xe0),
    fromRgb(0x90, 0x13, 0xfe),
    fromRgb(0x4a, 0x90, 0xe2),
    fromRgb(0x50, 0xe3, 0xc2),
    fromRgb(0xb8, 0xe9, 0x86),
    fromRgb(0x00, 0x00, 0x00),
    fromRgb(0x4a, 0x4a, 0x4a),
    fromRgb(0x9b, 0x9b, 0x9b),
    fromRgb(0xff, 0xff, 0xff)
  )

  def white = Hsv.fromRgb(255, 255, 255)
  def black = Hsv.fromRgb(0, 0, 0)
}
