package xyz.bluepitaya.laminarcolor

import com.raquo.laminar.api.L._
import org.scalajs.dom

object Saturation {
  def pointerChange(
      event: dom.PointerEvent,
      el: dom.Element,
      hsv: Var[Hsv]
  ) = {
    val rect = el.getBoundingClientRect()
    val percentOffset = Util.getEventPositionPercent(event, rect)
    val saturation = percentOffset.x
    val brightness = percentOffset.y

    hsv.update(v => v.copy(s = saturation, v = brightness))
  }

  // TODO: maybe relative/asbolute with inset
  def whiteGradient = Seq(
    display("grid"),
    background("linear-gradient(to right, #fff, rgba(255, 255, 255, 0))")
  )

  def blackGradient = Seq(
    position.relative,
    overflow.hidden,
    background("linear-gradient(to top, #000, rgba(0, 0, 0, 0))")
  )

  def component(hsv: Var[Hsv], handler: HtmlElement) = {
    val dragModule = DragLogic.enableDraggingInDocument()

    val pointerTopValue = hsv.signal.map(hsv => s"${100 - hsv.v * 100}%")
    val pointerLeftValue = hsv.signal.map(hsv => s"${hsv.s * 100}%")
    val colorStyle = hsv.signal.map(v => s"hsl(${v.h} 100% 50%)")

    div(
      display("grid"),
      div(
        display("grid"),
        background <-- colorStyle,
        div(
          whiteGradient,
          div(
            blackGradient,
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
            handler.amend(
              position("absolute"),
              top <-- pointerTopValue,
              left <-- pointerLeftValue
            )
          )
        )
      )
    )
  }
}
