package xyz.bluepitaya.laminarcolor

import com.raquo.laminar.api.L._
import org.scalajs.dom

object TransparentImage {
  def data =
    "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAAXNSR0IArs4c6QAAAQ5JREFUOE9tU9sRwzAIEzt4/wm9Az2QeCRpPmL3ApKQqAF2AMDgccBh+c6H11uX+TQ1ZsCJpri4s5H3Lrp9M4O5P0ji2wlk89Ag5kUPw0WCERgBQIYURoChbd09gYEKWpG6gowYOCUxz361Lzd0LWfInP22FdC3/Yj50lRBpPyqouXnha8E1LBT+KeEMWqYwHOXWcRx4JrJ4PKhxAhQ4h5Tdg6hcGdTBmQui5dj7R1SUhVzyUk1oVKGM2A1l7MFzd0Ij4IqHPeMn+Vkq3NQluPayLNXbKKm4gRI5tGimDps7snqbPmzjONIgOkXNK9WvUBeXuUo3VFUUcRV7Zh3LmV/pfDdvwEwU4zz73yU/wA76oy6jX5IIgAAAABJRU5ErkJggg=="
}

//FIXME: alpha color is not handled
object ColorField {
  val darkBorderStyle = Seq(
    boxShadow(
      "rgb(0 0 0 / 15%) 0px 0px 0px 1px inset, rgb(0 0 0 / 25%) 0px 0px 4px inset"
    )
  )

  val lightBorderStyle =
    Seq(boxShadow("rgb(0 0 0 / 10%) 0px 0px 0px 1px inset"))

  val circleStyle = Seq(borderRadius("50%"))

  // TODO: remove modifiers
  /** Size of component is defined by parent div */
  def component(
      color: Var[ColorPicker.Hsv],
      modifiers: Seq[Setter[HtmlElement]] = Seq()
  ) = Seq(
    div(
      width("100%"),
      height("100%"),
      backgroundColor <-- color.signal.map(_.toCssRgb),
      modifiers,
      div(background(s"url('${TransparentImage.data}')"))
    )
  )
}
