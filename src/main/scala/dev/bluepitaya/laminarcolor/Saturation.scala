package dev.bluepitaya.laminarcolor

import com.raquo.laminar.api.L._
import org.scalajs.dom
import dev.bluepitaya.laminarcolor.models.Hsv
import dev.bluepitaya.laminardragging.Dragging
import dev.bluepitaya.laminardragging.DragEventKind

object Saturation {
  def component(
      handler: HtmlElement,
      colorSignal: Signal[Hsv],
      onColorChanged: Observer[Hsv]
  ) = {
    val draggingModule = Dragging.createModule[String]()

    val pointerTopValue = colorSignal.map(hsv => s"${100 - hsv.v * 100}%")
    val pointerLeftValue = colorSignal.map(hsv => s"${hsv.s * 100}%")
    val colorStyle = colorSignal.map(v => s"hsl(${v.h} 100% 50%)")

    div(
      display("grid"),
      div(
        display("grid"),
        background <-- colorStyle,
        div(
          whiteGradient,
          div(
            blackGradient,
            draggingModule.documentBindings,
            draggingModule.componentBindings("saturation"),
            inContext { el =>
              draggingModule
                .componentEvents("saturation")
                .withCurrentValueOf(colorSignal)
                .collect {
                  case (Dragging.Event(e, DragEventKind.Start), color) =>
                    pointerChange(e, el.ref, color)
                  case (Dragging.Event(e, DragEventKind.Move), color) =>
                    pointerChange(e, el.ref, color)
                } --> onColorChanged
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

  private def pointerChange(
      event: dom.PointerEvent,
      el: dom.Element,
      currentColor: Hsv
  ): Hsv = {
    val rect = el.getBoundingClientRect()
    val percentOffset = Util.getEventPositionPercent(event, rect)
    val saturation = percentOffset.x
    val brightness = percentOffset.y

    currentColor.copy(s = saturation, v = brightness)
  }

  // TODO: maybe relative/asbolute with inset
  private def whiteGradient = Seq(
    display("grid"),
    background("linear-gradient(to right, #fff, rgba(255, 255, 255, 0))")
  )

  private def blackGradient = Seq(
    position.relative,
    overflow.hidden,
    background("linear-gradient(to top, #000, rgba(0, 0, 0, 0))")
  )
}
