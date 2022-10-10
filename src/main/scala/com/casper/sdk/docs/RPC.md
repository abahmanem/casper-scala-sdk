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

getDeploy(deployHash : String): Try[DeployResult]
```
Returns a Deploy entity 
### Parameters
| Name | Type | Description | Mandatory |
|---|---|---|---|
| `deployHash` | `String` | Hex-encoded hash of a deploy | Yes |


---
## Get Block by hash
```scala
getBlock(blockHash: HashBlockIdentifier) : Try[BlockResult]
```
Returns a Block object 
### Parameters
| Name | Type | Description | Mandatory |
|---|---|---|---|
| `blockHash` | `HashBlockIdentifier` | Hex-encoded hash of a block | Yes |

---
## Get Block by height
```scala
getBlockByHeight(blockHeight: HeightBlockIdentifier) : Try[BlockResult]
```
Returns a Block entity object
### Parameters
| Name | Type | Description | Mandatory |
|---|---|---|---|
| `blockHeight` | `HeightBlockIdentifier` | Block height | Yes |

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

def getAuctionInfo(blockHash: String): Try[AuctionStateResult]
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

getStateRootHash(blockHash: String): Try[StateRootHashResult]
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

def getBalance(stateRootHash: String, uref_purse: String): Try[BalanceResult] 
```
Returns Account balance 
### Parameters
| Name | Type     | Description                        | Mandatory |
|---|----------|------------------------------------|---|
| `stateRootHash` | `String` | Hex-encoded hash of the state root | Yes |
| `uref_purse` | `String` | Uref purse string                  | Yes |

---
---
## Get  Global state
```scala
def queryGlobalState(stateIdentifier: StateIdentifier, key: String, path: Seq[Json] = Seq.empty): Try[GlobalStateResult]
```
Returns StoredValue object entity for the given state root hash, casper-type key and path
### Parameters
| Name | Type              | Description                                         | Mandatory |
|---|-------------------|-----------------------------------------------------|---|
| `stateIdentifier` | `StateIdentifier` | State indentifier : (state root hash or block hash) | Yes |
| `key` | `String`          | Casper-type key                                     | Yes |
| `path` | `Seq[Json]`       | Path components                                     | No |

---
## Get  Account Info
```scala
def getAccountInfo(publicKey: String, blockIdentifier: BlockIdentifier): Try[AccountResult]
```
Returns account info for the given publicKey and block identifier
### Parameters
| Name | Type        | Description                       | Mandatory |
|---|-------------|-----------------------------------|---|
| `publicKey` | `String`    | Hex-encoded publick key           | Yes |
| `blockIdentifier` | `BlockIdentifier`    | Block identifier : Hash or Height | Yes |

---



## Get Block transfers
```scala
def getBlockTransfers(blockHash: String): Try[TransferResult] 
```
Returns a sequence of Transfer objects for the block 
### Parameters
| Name | Type | Description | Mandatory |
|---|---|---|---|
| `blockHash` | `String` | Hex-encoded hash of the block | No |

---
## Get Era Summary by Switch Block Hash
```scala
def getEraInfoBySwitchBlock(blockHash: String): Try[EraSummaryResult] 
```
Returns an EraSummary object for the given block hash
### Parameters
| Name | Type | Description | Mandatory |
|---|---|---|---|
| `blockHash` | `String` | Hex-encoded hash of the block | Yes |

---
## Get Dictionary Item
```scala
def getDictionaryItem(stateRootHash: String, itemKey: String, uref: String): Try[DictionaryItemResult]  
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

