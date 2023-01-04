package xyz.bluepitaya.laminarcolor

import com.raquo.laminar.api.L._
import org.scalajs.dom
import Util.toPxStr

object Hue {
  def pointerChange(
      event: dom.PointerEvent,
      el: dom.Element,
      hsv: Var[ColorPicker.Hsv]
  ) = {
    val rect = el.getBoundingClientRect()
    val percentOffset = Util.getEventPositionPercent(event, rect)
    val hue = percentOffset.x * 360

    hsv.update(v => v.copy(h = hue))
  }

  def hueToCssLeftAttr(h: Double) = s"${(h / 360.0) * 100.0}%"

  def component(hsv: Var[ColorPicker.Hsv], heightInPx: Int) = {
    val dragModule = DragLogic.enableDraggingInDocument()
    val handler = Circles.filledCircle(heightInPx + 2, heightInPx + 2)

    val circleLeftPositionSignal = hsv.signal.map(x => hueToCssLeftAttr(x.h))

    div(
      cls("horizontalHue"),
      position.relative,
      handler.amend(
        position.absolute,
        left <-- circleLeftPositionSignal,
        top("50%")
      ),
      inContext { el =>
        val docEvents = dragModule.docEvents
        val compEvents = dragModule.getComponentEvents(
          HtmlIdGenerator.ImpureRandomStringIdGenerator.randomId,
          Observer[DragLogic.DragEvent] {
            case DragLogic.DragEnd(e)      => ()
            case DragLogic.DragMove(e)     => pointerChange(e, el.ref, hsv)
            case DragLogic.DragStart(e, _) => pointerChange(e, el.ref, hsv)
          }
        )
        Seq(docEvents, compEvents)
      }
    )
  }
}
