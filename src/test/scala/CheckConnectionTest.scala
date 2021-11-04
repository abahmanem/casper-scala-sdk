import org.scalatest.funsuite.AnyFunSuite


import com.casper.sdk._
import com.casper.sdk.core._
import com.casper.sdk.result._
import com.casper.sdk.core.implicits.idInstance

class CheckConnectionTest extends AnyFunSuite {


  test("Connection failed") {
    assertThrows[java.lang.Exception] { // Result type: Assertion
      val transport = new HttpTransport("http://1.2.3.4:7777/rpc",new JsonConverterByClass,1,1)
      val cspr = new SDK(transport)
      val peers : GetPeersResult = cspr.get_info_get_peers(Seq("f"))
    }
  }
}
