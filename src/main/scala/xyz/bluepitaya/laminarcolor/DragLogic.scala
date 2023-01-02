package xyz.bluepitaya.laminarcolor

import com.raquo.laminar.api.L._
import org.scalajs.dom
import com.raquo.laminar.nodes.ReactiveElement

object DragLogic {
  sealed trait DragEvent
  final case class DragStart(e: dom.PointerEvent, id: String) extends DragEvent
  final case class DragMove(e: dom.PointerEvent) extends DragEvent
  final case class DragEnd(e: dom.PointerEvent) extends DragEvent

  case class DocumentDraggingModule(
      docEvents: Seq[Binder[ReactiveElement.Base]],
      // TODO: documentation
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

    val documentEventBus = new EventBus[DragEvent]
    val componentEventBus = new EventBus[DragEvent]

    val dragEventBroadcast = Observer[DragEvent] { e =>
      // order of emits is important here
      documentEventBus.emit(e)
      componentEventBus.emit(e)
    }

    val documentEventStream: EventStream[DragEvent] = documentEventBus.events

    val documentObserver = Observer[DragEvent] {
      case DragStart(_, id) => currentDragging.set(Some(id))
      case DragEnd(_)       => currentDragging.set(None)
      case _                => ()
    }

    def componentEventStream(id: String): EventStream[DragEvent] =
      componentEventBus
        .events
        .filter { e =>
          currentDragging.now() match {
            case Some(x) if x == id => true
            case _                  => false
          }
        }

    val docEvents = Seq(
      documentEvents.onPointerMove.map(e => DragMove(prepareEvent(e))) -->
        dragEventBroadcast,
      documentEvents.onPointerUp.map(e => DragEnd(prepareEvent(e))) -->
        dragEventBroadcast,
      documentEventStream --> documentObserver
    )

    def componentEvents(id: String, observer: Observer[DragEvent]) = Seq(
      onPointerDown.map(e => DragStart(prepareEvent(e), id)) -->
        dragEventBroadcast,
      componentEventStream(id) --> observer
    )

    DocumentDraggingModule(
      docEvents = docEvents,
      getComponentEvents = componentEvents
    )
  }
}
