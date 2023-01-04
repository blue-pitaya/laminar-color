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
      case DragStart(e, id) => currentDragging.set(Some(id))
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
      documentEvents.onPointerMove.map(e => DragMove(e)) --> dragEventBroadcast,
      documentEvents.onPointerUp.map(e => DragEnd(e)) --> dragEventBroadcast,
      documentEventStream --> documentObserver
    )

    def componentEvents(id: String, observer: Observer[DragEvent]) = Seq(
      onPointerDown.map(e => DragStart(e, id)) --> dragEventBroadcast,
      componentEventStream(id).map { e =>
        e match {
          case DragStart(e, id) =>
            e.preventDefault()
            e.stopPropagation()
          case DragMove(e) =>
            e.preventDefault()
            e.stopPropagation()
          case DragEnd(e) =>
            e.preventDefault()
            e.stopPropagation()
        }
        e
      } --> observer
    )

    DocumentDraggingModule(
      docEvents = docEvents,
      getComponentEvents = componentEvents
    )
  }
}
