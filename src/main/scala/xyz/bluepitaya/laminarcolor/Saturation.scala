package xyz.bluepitaya.laminarcolor

import com.raquo.laminar.api.L._
import org.scalajs.dom

object Saturation {
  def pointerChange(
      event: dom.PointerEvent,
      el: dom.Element,
      hsv: Var[ColorPicker.Hsv]
  ) = {
    val rect = el.getBoundingClientRect()
    val percentOffset = Util.getEventPositionPercent(event, rect)
    val saturation = percentOffset.x
    val brightness = percentOffset.y

    hsv.update(v => v.copy(s = saturation, v = brightness))
  }

  def component(hsv: Var[ColorPicker.Hsv]) = {
    val dragModule = DragLogic.enableDraggingInDocument()

    val pointerStyle = hsv.signal
      .map { x =>
        s"position: absolute;top: ${100 - (x.v * 100)}%;left: ${x.s * 100}%"
      }

    val colorStyle = hsv.signal.map(v => s"background: hsl(${v.h} 100% 50%)")

    div(
      cls("saturationWindow"),
      div(
        cls("saturationColor"),
        styleAttr <-- colorStyle,
        div(
          cls("saturation-white"),
          div(
            cls("saturation-black"),
            inContext { el =>
              val docEvents = dragModule.docEvents
              val compEvents = dragModule.getComponentEvents(
                HtmlIdGenerator.ImpureRandomStringIdGenerator.randomId,
                Observer[DragLogic.DragEvent] {
                  case DragLogic.DragEnd(e)  => ()
                  case DragLogic.DragMove(e) => pointerChange(e, el.ref, hsv)
                  case DragLogic.DragStart(e, _) =>
                    pointerChange(e, el.ref, hsv)
                }
              )
              Seq(docEvents, compEvents)
            },
            div(cls("circle"), styleAttr <-- pointerStyle)
          )
        )
      )
    )
  }
}
