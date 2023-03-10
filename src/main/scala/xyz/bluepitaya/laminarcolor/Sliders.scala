package xyz.bluepitaya.laminarcolor

import com.raquo.laminar.api.L._
import org.scalajs.dom
import Util._
import xyz.bluepitaya.laminardragging.Dragging
import xyz.bluepitaya.common.Hsv
import xyz.bluepitaya.laminardragging.Dragging.DragStart
import xyz.bluepitaya.laminardragging.Dragging.DragMove

object Sliders {
  def hueComponent(
      heightInPx: Int,
      handler: HtmlElement,
      colorSignal: Signal[Hsv],
      onColorChanged: Observer[Hsv]
  ) = {
    val backgroundDiv = div(
      position.absolute,
      width("100%"),
      height("100%"),
      background(
        "linear-gradient(to right, #f00 0%, #ff0 17%, #0f0 33%, #0ff 50%, #00f 67%, #f0f 83%, #f00 100%)"
      )
    )

    val circleLeftPositionSignal = colorSignal.map(c => hueToCssLeftAttr(c.h))

    val baseBindings = getBaseBindings(
      cssLeftPositionSignal = circleLeftPositionSignal,
      heightInPx = heightInPx,
      background = backgroundDiv,
      handler = handler
    )
    val (draggingBindings, dragEvents) = getDraggingLogic()

    div(
      baseBindings,
      draggingBindings,
      inContext { ctx =>
        dragEvents
          .collect {
            case DragStart(e) => getSliderXPercent(e, ctx.ref)
            case DragMove(e)  => getSliderXPercent(e, ctx.ref)
          }
          .withCurrentValueOf(colorSignal)
          .map { case (xPercent, color) =>
            val hue = xPercent * 360
            color.copy(h = hue)
          } --> onColorChanged
      }
    )
  }

  def alphaComponent[A](
      heightInPx: Int,
      handler: HtmlElement,
      colorSignal: Signal[Hsv],
      onColorChanged: Observer[Hsv]
  ) = {
    val backgroundGradientSignal = colorSignal.map(c =>
      s"linear-gradient(to right, ${c.toCssRgbaTransparent} 0%, ${c.toCssRgb} 100%)"
    )
    val circleLeftPositionSignal = colorSignal.map(c => alphaToCssLeftAttr(c.a))

    val backgroundDiv = div(
      position.absolute,
      inset("0"),
      overflow.hidden,
      div(
        position.absolute,
        inset("0"),
        background(s"url('${TransparentImage.data}')")
      ),
      div(
        position.absolute,
        inset("0"),
        background <-- backgroundGradientSignal
      )
    )

    val baseBindings = getBaseBindings(
      cssLeftPositionSignal = circleLeftPositionSignal,
      heightInPx = heightInPx,
      background = backgroundDiv,
      handler = handler
    )
    val (draggingBindings, dragEvents) = getDraggingLogic()

    div(
      baseBindings,
      draggingBindings,
      inContext { ctx =>
        dragEvents
          .collect {
            case DragStart(e) => getSliderXPercent(e, ctx.ref)
            case DragMove(e)  => getSliderXPercent(e, ctx.ref)
          }
          .withCurrentValueOf(colorSignal)
          .map { case (xPercent, color) =>
            color.copy(a = xPercent)
          } --> onColorChanged
      }
    )
  }

  private def alphaToCssLeftAttr(a: Double) = s"${a * 100.0}%"

  private def hueToCssLeftAttr(h: Double) = s"${(h / 360.0) * 100.0}%"

  private def getSliderXPercent(event: dom.PointerEvent, el: dom.Element) = {
    val rect = el.getBoundingClientRect()
    Util.getEventPositionPercent(event, rect).x
  }

  private def getBaseBindings(
      cssLeftPositionSignal: Signal[String],
      heightInPx: Int,
      background: HtmlElement,
      handler: HtmlElement
  ) = Seq(
    position.relative,
    height(Util.toPxStr(heightInPx)),
    background,
    handler.amend(position.absolute, left <-- cssLeftPositionSignal, top("50%"))
  )

  private def getDraggingLogic() = {
    val draggingId = "slider"
    val draggingModule = Dragging.createModule()
    val bindings = Seq(
      draggingModule.documentBindings,
      draggingModule.componentBindings(draggingId)
    )

    (bindings, draggingModule.componentEvents(draggingId))
  }
}
