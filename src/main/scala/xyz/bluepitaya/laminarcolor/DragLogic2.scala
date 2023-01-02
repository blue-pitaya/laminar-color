package xyz.bluepitaya.laminarcolor

import com.raquo.laminar.api.L._
import org.scalajs.dom
import com.raquo.laminar.nodes.ReactiveElement

object DragLogic2 {
  sealed trait DragEvent
  final case class DragStart(e: dom.PointerEvent, id: String) extends DragEvent
  final case class DragMove(e: dom.PointerEvent) extends DragEvent
  final case class DragEnd(e: dom.PointerEvent) extends DragEvent

  case class DocumentDraggingModule(
      docEvents: Seq[Binder[ReactiveElement.Base]],
      // TODO: needs documentation beacuase hover shows v1, v2 and
      // i dont know what it means
      getComponentEvents: (
          String,
          Observer[DragEvent]
      ) => Seq[Binder[ReactiveElement.Base]]
  )

  def enableDraggingInDocument(): DocumentDraggingModule = {
    // TODO: impure
    def prepareEvent(e: dom.PointerEvent): dom.PointerEvent = {
      e.preventDefault()
      e.stopPropagation()
      e
    }

    val currentDragging = Var[Option[String]](None)

    val bus = new EventBus[DragEvent]
    val documentEventStream: EventStream[DragEvent] = bus.events

    def startDragging(id: String): Unit = {
      currentDragging.set(Some(id))
    }

    def resetDragging(): Unit = {
      currentDragging.set(None)
    }

    val documentObserver = Observer[DragEvent] {
      case DragStart(_, id) => startDragging(id)
      case DragEnd(_)       => resetDragging()
      case _                => ()
    }

    def componentEventStream(id: String): EventStream[DragEvent] = bus.events
      .filter { e =>
        currentDragging.now() match {
          case Some(x) if x == id => true
          case _                  => false
        }
      }

    val docEvents = Seq(
      documentEvents.onPointerMove.map(e => DragMove(prepareEvent(e))) --> bus,
      documentEvents.onPointerUp.map(e => DragEnd(prepareEvent(e))) --> bus,
      documentEventStream --> documentObserver
    )

    def componentEvents(id: String, observer: Observer[DragEvent]) = Seq(
      onPointerDown.map(e => DragStart(prepareEvent(e), id)) --> bus,
      componentEventStream(id) --> observer
    )

    DocumentDraggingModule(
      docEvents = docEvents,
      getComponentEvents = componentEvents
    )
  }
}
