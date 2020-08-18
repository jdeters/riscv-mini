package mini

import chisel3._
import freechips.rocketchip.config.Parameters
import chisel3.util.MuxLookup

class InstructionCountersIO(implicit p: Parameters) extends CoreBundle()(p) {
  val inst = Input(UInt(xlen.W))
}

class InstructionCounters (implicit val p: Parameters) extends Module with CoreParams{
  val io = IO(new InstructionCountersIO)

  val instructionCount = 49

  //create a new set of resetable registers that always go back to 0
  val counters = RegInit(VecInit(Seq.fill(instructionCount+1)(0.U(xlen.W))))

  //construct a new list that maps the instructions to a counter
  val instMap = for (i <- 0 until instructionCount) yield {
    //doing this to avoid using util.Lookup which is likely to be deprecated
    Control.map(i)._1.value.asUInt(xlen.W) -> i.U;
  }

  //look up the register and increase the count
  val insCounter = counters(MuxLookup(io.inst, counters(instructionCount), instMap))
  insCounter := insCounter + 1.U

  if(p(Trace)) printf("Executing instruction: %x has been seen %d times\n", io.inst, insCounter);
}
