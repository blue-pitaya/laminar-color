package xyz.bluepitaya.laminarcolor

import com.raquo.laminar.api.L._
import org.scalajs.dom

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

  def component(hsv: Var[ColorPicker.Hsv]) = {
    val dragModule = DragLogic2.enableDraggingInDocument()

    div(
      cls("horizontalHue"),
      inContext { el =>
        val docEvents = dragModule.docEvents
        val compEvents = dragModule.getComponentEvents(
          HtmlIdGenerator.ImpureRandomStringIdGenerator.randomId,
          Observer[DragLogic2.DragEvent] {
            case DragLogic2.DragEnd(e)      => ()
            case DragLogic2.DragMove(e)     => pointerChange(e, el.ref, hsv)
            case DragLogic2.DragStart(e, _) => pointerChange(e, el.ref, hsv)
          }
        )
        Seq(docEvents, compEvents)
      }
    )
  }
}
