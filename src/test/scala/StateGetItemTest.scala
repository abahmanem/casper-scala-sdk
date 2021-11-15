import org.scalatest.funsuite.AnyFunSuite

import com.casper.sdk.util.implicits.idInstance

class StateGetItemTest extends AnyFunSuite {
/*
  val transport = new HttpTransport("http://65.21.202.120:7777/rpc",new JsonConverterByClass,100000,100000)
  val cspr = new SDK(transport)

  test("get item not empty") {
    val contract : String = "hash-fb8e0215c040691e9bbe945dd22a00989b532b9c2521582538edb95b61156698"

    val stateRootHash : GetStateRootHashResult = cspr.get_chain_get_state_root_hash(Seq("f"))
    val item : StateGetItemResult = cspr.state_get_item(List(stateRootHash.result.get.state_root_hash, contract))
    assert (item.result  != Option.empty)
    assert (item.error  == Option.empty)
  }

  test("get item empty") {
    val item : StateGetItemResult = cspr.state_get_item(Seq("f"))
    assert (item.result  == Option.empty)
    assert (item.error  != Option.empty)
  }
  */
}
