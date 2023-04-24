# Laminar Color

![Preview of pickers.](/preview.png)

Collection of color pickers written in [Laminar](https://laminar.dev/) library. Pickers are composed from reusable components that can be easily used to create your own color picker. Project inspired by [React color](https://github.com/casesandberg/react-color) (i stole some of their CSS).

See [demo](https://blue-pitaya.github.io/laminar-color/).

## Instalation

Currently library isn't published anywhere, but it will change soon.

## Usage

All pickers have the same component function signature.

```scala
def component(colorSignal: Signal[Hsv], onColorChanged: Observer[Hsv])
```

Default color model for pickers is Hsv. The hsv case class comes with mappings to both hsl and rgba models for easy use in CSS.

### Example

```scala
val color = Var(Hsv(200, 0.75, 0.75, 1))
val hslValueSignal = color.signal.map(_.toCssHsl)
val rgbaValueSignal = color.signal.map(_.toCssRgba)

val app = div(
  div(
    h2("Current color"),
    div(child.text <-- hslValueSignal),
    div(child.text <-- rgbaValueSignal)
  ),
  h2("Color picerks"),
  div(
    display.flex,
    flexDirection.row,
    styleProp("gap")("30px"),
    div(
      h3("Chrome picker"),
      ChromePicker.component(color.signal, color.writer)
    ),
    div(
      h3("Sketch picker"),
      SketchPicker.component(color.signal, color.writer)
    ),
    div(
      h3("Simple picker"),
      SimplePicker.component(color.signal, color.writer)
    )
  )
)
```

### Building your own picker

Best way is to look at source code of current out-of-box pickers to see how to build your onw.

## Development

To run example page you need to:

1. Run `sbt` -> `project example` -> `~fastLinkJS`
2. Execute `yarn` (only once to install JS deps) -> `yarn dev` in `example/ui` dir.
