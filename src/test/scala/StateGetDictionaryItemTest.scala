import org.scalatest.funsuite.AnyFunSuite


import com.casper.sdk._
import com.casper.sdk.core._
import com.casper.sdk.result._
import com.casper.sdk.core.implicits.idInstance


case class tmpParamItem(
  dictionary_item_key : String,
  seed_uref : String
)

case class tmpParam(
    URef : tmpParamItem
)

class StateGetDictionaryItemTest extends AnyFunSuite {

  val transport = new HttpTransport("http://65.21.202.120:7777/rpc",new JsonConverterByClass,100000,100000)
  val cspr = new SDK(transport)

  test("get dictionnary item not empty") {
    val property : String = "uref-0d689e987db7ee5be246282c3a7badf0411e34baeeab8e9d73c1223ae4ad02e5-007"
    val accountHash : String = "f870e3cadfde21d7d7686fdf3d1a8413838274d363ca7b27ae71fc9125eb6743"
    val stateRootHash : GetStateRootHashResult = cspr.get_chain_get_state_root_hash(Seq("f"))

    val paramItem = new tmpParamItem(accountHash, property)
    val param = new tmpParam(paramItem)

    val listParam = List[Any](stateRootHash.result.get.state_root_hash,param)
    val item : StateGetDictionaryItemResult = cspr.state_get_dictionary_item(listParam)
    assert (item.result  != Option.empty)
    assert (item.error  == Option.empty)
  }

  test("get dictionnary item empty") {
    val item : StateGetDictionaryItemResult = cspr.state_get_dictionary_item(Seq("f"))
    assert (item.result  == Option.empty)
    assert (item.error  != Option.empty)
  }
}
