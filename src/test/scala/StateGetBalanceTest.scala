import org.scalatest.funsuite.AnyFunSuite


import com.casper.sdk._
import com.casper.sdk.core._
import com.casper.sdk.result._
import com.casper.sdk.core.implicits.idInstance

class StateGetBalanceTest extends AnyFunSuite {

  val transport = new HttpTransport("http://65.21.202.120:7777/rpc",new JsonConverterByClass,100000,100000)
  val cspr = new SDK(transport)

  test("balance not empty") {
    val uref : String = "uref-3b4b07f9b59db6af01f74f13deef8b00f62aa6a3905c0355d6467693bf09466f-007"

    val stateRootHash : GetStateRootHashResult = cspr.get_chain_get_state_root_hash(Seq("f"))
    println(stateRootHash.result.get.state_root_hash)
    val balance : StateGetBalanceResult = cspr.state_get_balance(List(stateRootHash.result.get.state_root_hash, uref))
    assert (balance.result.get.balance_value  >= 0)
    assert (balance.error  == Option.empty)
  }

  test("wrong uref") {
    val uref : String = "uref-wronguref-007"
    val stateRootHash : GetStateRootHashResult = cspr.get_chain_get_state_root_hash(Seq("f"))
    val balance : StateGetBalanceResult = cspr.state_get_balance(List(stateRootHash.result.get.state_root_hash, uref))
    assert (balance.error  != Option.empty)
    assert (balance.result  == Option.empty)
  }

  test("no parameters") {
    val balance : StateGetBalanceResult = cspr.state_get_balance(List())
    assert (balance.error  != Option.empty)
    assert (balance.result  == Option.empty)
  }
}
