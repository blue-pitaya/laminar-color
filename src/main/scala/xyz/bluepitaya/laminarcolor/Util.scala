package xyz.bluepitaya.laminarcolor

import com.raquo.laminar.api.L._
import org.scalajs.dom
import xyz.bluepitaya.laminarcolor.Vec2f

object Util {
  def getEventPositionPercent(
      event: dom.PointerEvent,
      rect: dom.DOMRect
  ): Vec2f = {
    val x = event.pageX - (rect.x + dom.window.pageXOffset)
    val y = event.pageY - (rect.y + dom.window.pageYOffset)
    val w = rect.width
    val h = rect.height

    def norm(v: Double, max: Double) =
      if (v < 0) 0
      else if (v > max) max
      else v

    val normX = norm(x, w)
    val normY = norm(y, h)

    // y axis increses to bottom
    Vec2f(normX / w, 1 - (normY / h))
  }

  def getSelectorPositionFormHsv(
      hsv: ColorPicker.Hsv,
      componentSize: Vec2f
  ): Vec2f = {
    val percentX = hsv.s
    val percentY = 1.0 - hsv.v
    Vec2f(percentX * componentSize.x, percentY * componentSize.y)
  }

  def toPxStr(v: Int) = s"${v}px"
}
