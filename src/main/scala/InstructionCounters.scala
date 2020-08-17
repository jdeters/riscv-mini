package mini

import chisel3._
import freechips.rocketchip.config.Parameters

class InstructionCountersIO(implicit p: Parameters) extends CoreBundle()(p) {
  val inst = Input(UInt(xlen.W))
}

class InstructionCounters (implicit val p: Parameters) extends Module{
  val io = IO(new InstructionCountersIO)

  printf(p"Executing instruction: $io.inst")
}
