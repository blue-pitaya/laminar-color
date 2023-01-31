package xyz.bluepitaya.laminarcolor

import com.raquo.laminar.api.L._
import xyz.bluepitaya.common.Hsv

trait State[A] {
  def signal(a: A): Signal[Hsv]
  def updateHue(a: A, hue: Double): Unit
  def updateAlpha(a: A, alpha: Double): Unit
  def updateSaturationAndBrigthness(a: A, s: Double, v: Double): Unit
  def now(a: A): Hsv
  def set(a: A, v: Hsv): Unit
}

object State {
  implicit val hsvVarState = new State[Var[Hsv]] {
    override def signal(a: Var[Hsv]): Signal[Hsv] = a.signal

    override def updateHue(a: Var[Hsv], hue: Double): Unit = a
      .update(s => s.copy(h = hue))

    override def updateAlpha(a: Var[Hsv], alpha: Double): Unit = a
      .update(s => s.copy(a = alpha))

    override def updateSaturationAndBrigthness(
        a: Var[Hsv],
        s: Double,
        v: Double
    ): Unit = a.update(x => x.copy(s = s, v = v))

    override def now(a: Var[Hsv]): Hsv = a.now()

    override def set(a: Var[Hsv], v: Hsv): Unit = a.set(v)

  }
}
