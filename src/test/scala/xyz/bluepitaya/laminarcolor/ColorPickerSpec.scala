package xyz.bluepitaya.laminarcolor

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ColorPickerSpec extends AnyFlatSpec with Matchers {
  "hsv and rgb convertions" should "be the same both ways" in {
    (0 to 255)
      .zip((0 to 255))
      .zip(0 to 255)
      .map { case ((r, g), b) =>
        val hsv = Hsv.fromRgb(r, g, b)
        val rgb = hsv.toRgba

        (r, g, b) shouldEqual (rgb.r, rgb.g, rgb.b)
      }
  }

  // "hsv and hsl convertions" should "be the same both ways" in {
  //  (0 to 360)
  //    .zip(BigDecimal(0.0) to BigDecimal(1.0) by BigDecimal(0.01))
  //    .zip(BigDecimal(0.0) to BigDecimal(1.0) by BigDecimal(0.01))
  //    .map { case ((h, s), v) =>
  //      val hsv = Hsv(h, s.toDouble, v.toDouble, 1.0)
  //      val rgb = hsv.toCssHsl

  //    // (r, g, b) shouldEqual (rgb.r, rgb.g, rgb.b)
  //    }
  // }
}
