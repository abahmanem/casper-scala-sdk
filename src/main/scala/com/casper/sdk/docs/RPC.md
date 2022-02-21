# CasperSdk API
Scala Class for interacting with Casper network using RPC calls

---
## Constructor
```scala
CasperSdk(url : string)
```
### Parameters
| Name | Type | Description | Mandatory |
|---|---|---|---|
| `url` | `String` | Full node url string | Yes |



---
## Get Deploy
```scala
getDeploy(deployHash : String): Try[Deploy]
```
Returns a Deploy entity 
### Parameters
| Name | Type | Description | Mandatory |
|---|---|---|---|
| `deployHash` | `String` | Hex-encoded hash of a deploy | Yes |


---
## Get Block by hash
```scala
getBlock(blockHash: String) : Try[Block]
```
Returns a Block object 
### Parameters
| Name | Type | Description | Mandatory |
|---|---|---|---|
| `blockHash` | `String` | Hex-encoded hash of a block | Yes |

---
## Get Block by height
```scala
getBlockByHeight(blockHeight: BigInt) : Try[Block]
```
Returns a Block entity object
### Parameters
| Name | Type | Description | Mandatory |
|---|---|---|---|
| `blockHeight` | `BigInt` | Block height | Yes |

---

---
## Get peers
```scala 
getPeers(): Try[Seq[Peer]]
```
Returns a Sequence of Peer object entity

---
## Get Status
```scala
def getStatus(): Try[NodeStatus]
```
Returns the current Status of the node

---
## Get Auction State
```scala
def getAuctionInfo(blockHash: String): Try[AuctionState]
```
Returns AuctionState  containing the bids and validators at a given block (block hash).
if called without a parameter, it returns the  AuctionState of the most recently added block

### Parameters
| Name | Type | Description | Mandatory |
|---|---|---|---|
| `blockHash` | `String` | Hex-encoded hash of the block | no |

---
## Get State_Root_Hash
```scala
getStateRootHash(blockHash: String): Try[String]
```
Returns state root hash `String` by the given block hash or the one for the latest added block 
### Parameters
| Name | Type | Description | Mandatory |
|---|---|---|---|
| `blockHash` | `String` | Hex-encoded hash of the block | Yes |

---

---
## Get Account balance
```scala
def getBalance(stateRootHash: String, accountUref: Option[URef]): Try[Long] 
```
Returns Account balance 
### Parameters
| Name | Type | Description | Mandatory |
|---|---|---|---|
| `stateRootHash` | `String` | Hex-encoded hash of the state root | Yes |
| `accountUref` | `URef` | Account URef object | Yes |

---
---
## Get  State Item
```scala
def getStateItem(stateRootHash: String, key: String, path: Seq[Any] = Seq.empty): Try[StoredValue]
```
Returns StoredValue object entity for the given state root hash, casper-type key and path
### Parameters
| Name | Type | Description | Mandatory |
|---|---|---|---|
| `stateRootHash` | `String` | Hex-encoded hash of the state root | Yes |
| `key` | `String` |  Casper-type key  | Yes |
| `path` | `Seq[Any]` | Path components  | No |

---
## Get Block transfers
```scala
def getBlockTransfers(blockHash: String): Try[Seq[Transfer]] 
```
Returns a sequence of Transfer objects for the block 
### Parameters
| Name | Type | Description | Mandatory |
|---|---|---|---|
| `blockHash` | `String` | Hex-encoded hash of the block | No |

---
## Get Era Summary by Switch Block Hash
```scala
def getEraInfoBySwitchBlock(blockHash: String): Try[EraSummary] 
```
Returns an EraSummary object for the given block hash
### Parameters
| Name | Type | Description | Mandatory |
|---|---|---|---|
| `blockHash` | `String` | Hex-encoded hash of the block | Yes |

---
## Get Dictionary Item
```scala
def getDictionaryItem(stateRootHash: String, itemKey: String, uref: String): Try[StoredValue]  
```
Returns an item from a Dictionary as StoredValue object.

### Parameters
| Name | Type | Description | Mandatory |
|---|---|---|---|
| `stateRootHash` | `String` | Hex-encoded hash of the state root | Yes |
| `itemKey` | `String` | The dictionary item key  | Yes |
| `uref` | `String` | The dictionary's seed URef | Yes |

---

## Put Deploy 
```scala

def putDeploy(deploy:Deploy): Try[Hash]
```
Sends a deploy to the casper network.
Returns a Hash object representing the deploy hash.


### Parameters
| Name | Type | Description | Mandatory |
|---|---|---|---|
| `deploy` | `Deploy` | Deploy object  | Yes |

