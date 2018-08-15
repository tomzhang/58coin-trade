# [WebSocket API](https://github.com/58COIN/58coin-api/blob/master/58coin-websocket-docs-ZH-cn.md)

# 用于现货交易的58Coin REST API。 

本文件涵盖了58Coin REST api的详细信息，这些api用于访问您的帐户、管理您的订单和检索现货市场数据。 


<br/><br/><br/><br/><br/><br/>

----
## 说明
58Coin REST API是基于http的。您可以使用任何具有HTTP库的编程语言连接API。 

### URL
``` xml
https://api.58coin.com/v1/<endpoint>
```

### 返回正确
一个成功的返回是一个JSON对象，如下所示。 

``` json
{
  "result": <a JSON object or array>
}
```
- 结果:包含返回内容的结构化值。该元素可能是字符串、JSON对象或JSON数组值。 

### 返回错误
当请求遇到错误时，返回结果包含一个带有code的错误信息，如下所示。 

``` json
{
  "error": {
    "code": 10001,    // error code
    "message": "string"  // error message
  }
}
```
要获取更多关于错误代码和消息的信息，请参考本文档结尾处的参考。 
<br/><br/><br/><br/><br/><br/>

----
### 私人接口

私有接口可用于帐户管理和订单管理。所有私有接口都需要身份验证。 

### 身份验证 

----
### 生成一个API key 

为了访问私有接口，您必须在官网生成一个API key和一个API secret。 
<br/><br/><br/><br/><br/><br/>

----
### 创建一个请求 

所有私有请求必须包含以下HTTP headers: 

- **X-58COIN-APIKEY** API key。
- **X-58COIN-SIGNATURE** base64加密的签名字符串。
- **X-58COIN-TIMESTAMP** 用于请求的时间戳。

<br/><br/><br/><br/><br/><br/>

----
### 签名字符串 

签名字符串是一个经过HMAC-SHA256算法加密后的16进制字符串,其中加密信息是您的查询字符串，而秘钥是您的API secret 

> signature = HMAC-SHA256(queryString, secretKey)


签名字符串算法：首先对api_key和参数一块进行自然排序，然后把API secret和时间戳添加到请求参数的最后，然后加密，例如：spot/my/order/place这个下单接口

参数包括client_oid，symbol，type，side，api_key这几个。

（1）对上述参数自然排序

 结果：

 api_key,client_oid，side，symbol，type

所以目前形式是这样：spot/my/order/place？api_key=xx&client_oid=xx&side=xx&symbol=xx&type=xx

（2）把api secret和时间戳拼到后面

 最终形式是：spot/my/order/place？api_key=xx&client_oid=xx&side=xx&symbol=xx&type=xx&api_secret=xx&timestamp=xx

（3）加密

 可能对于普通的接口上面的工作就足够了，不过为了您的安全，这里需要再进行一步加密操作，保证您的操作不会被泄露，只需要对上述url问号后面的部分和您的secretKey进行HmacSHA256加密，生成一个签名：

例如您的secretKey=abcdefg

上面url问号后面的部分是：api_key=xx&client_oid=xx&side=xx&symbol=xx&type=xx&api_secret=xx&timestamp=xx

所以queryString=api_key=xx&client_oid=xx&side=xx&symbol=xx&type=xx&api_secret=xx&timestamp=xx

签名公式：

	signature = Hex.encodeHex(HMAC-SHA256(queryString, secretKey),UPPER)；

带入公式即可。

再对签名进行base64加密，这就是X-58COIN-SIGNATURE需要的内容。

<br/><br/><br/><br/><br/><br/>

----
### 交易对

----
#### 获取所有交易对信息

> GET product/list


**传参**

| Name | Type | Required | Description |

| :- | :- | :- | :- |

| name | string | no | null |

**返回数据样本**

```json

{
  "result": {
    "name": "BTC/USDT",
     "baseCurrencyName": "BTC",
     "quoteCurrencyName": "USDT",
     "baseMinSize": "0.001",
     "baseIncrement": "0.001",
     "quoteIncrement": "0.0.01"
  }
}
```

<br/><br/><br/><br/><br/>

----
### 账户 

----
#### 查看账户 

获取你的现货交易账户的资产列表。

GET spot/my/accounts

请求频率10次/2s

##### 参数 

None

##### 返回值

```json
{
  "result": [
    {
      "currency": "BTC",
      "balance": "0.00000000",
      "available": "0.00000000",
    },
    {
      "currency": "LTC",
      "balance": "0.00000000",
      "available": "0.00000000",
    },
    {
      "currency": "ETH",
      "balance": "0.00000000",
      "available": "0.00000000",
    },
    ...
  ]
}
```
<br/><br/><br/><br/><br/><br/>

----
### 订单 

----
#### 订单状态 

一个订单有效的顺序可能在其生命周期中有四个状态值。 

- Received:订单被发送到匹配的引擎。 

- Active:订单没有立即完全成交，并且它将保持在活动状态直到取消或随后订单完全成交。 

- Finished:订单已完全成交。 

- Cancelling:撤单中。 

- Cancelled:订单已取消。 

  
  <br/><br/><br/><br/><br/><br/>

----
#### 下一个新订单 

买卖资产的新订单。 

> POST spot/my/order/place

请求频率20次/2s  

##### 常见的参数 

| Name | Type | Required | Description |
|:- |:- |:- |:- |
| client_oid | string | no | 您的自定义订单ID |
| symbol | string | yes | 货币对的符号 |
| type | string | yes | “limit”或“market” |
| side | string | yes | “buy”或“sell” |

##### 限价单参数 

| Name | Type | Required | Description |
|:- |:- |:- |:- |
| price | string | yes | 买进或卖出价格 |
| amount | string | yes | 买卖的数量 |

##### 市价单的参数 

| Name | Type | Required | Description |
|:- |:- |:- |:- |
| amount | string | yes | 买卖的数量 |

##### 返回值

```json
{
  "result": {
    "order_id": "4303100732",
    "client_oid": "...",
    "symbol": "LTC_BTC",
    "type": "limit",
    "side": "buy",
    "price": "0.019708",
    "amount": "0.322",
    "base_filled": "0.0",
    "quote_filled": "0.0",
    "status": "received",
    "created_time": 1521142156000
  }
}
```
<br/><br/><br/><br/><br/><br/>

----
### 取消一个订单 

取消订单。 

> POST spot/my/order/cancel

请求频率20次/2s  

##### 参数 

| Name | Type | Required | Description |
|:- |:- |:- |:- |
| symbol | string | yes | 货币对的名称 |
| order_id | string | yes | 订单的ID |

##### 返回值

```json
{
  "result": {
    "order_id": "4303100732",
    "client_oid": "...",
    "symbol": "LTC_BTC",
    "type": "limit",
    "side": "buy",
    "price": "0.019708",
    "amount": "0.322",
    "base_filled": "0.261",
    "quote_filled": "23.123",
    "status": "active",
    "created_time": 1521142156000
  }
}
```
<br/><br/><br/><br/><br/><br/>

----
### 获取一个订单 

获取订单信息。 

> GET spot/my/order

请求频率10次/2s 

##### 参数

| Name | Type | Required | Description |
| :- | :- | :- | :- |
| symbol | string | yes | 货币对的名称 |
| order_id | string | yes | 订单的ID |

##### 返回值

```json
{
  "result": {
    "order_id": "4303100732",
    "client_oid": "...",
    "symbol": "LTC_BTC",
    "type": "limit",
    "side": "buy",
    "price": "0.019708",
    "amount": "0.322",
    "base_filled": "0.261",
    "quote_filled": "23.123",
    "status": "active",
    "created_time": 1521142156000
  }
}
```

<br/><br/><br/><br/><br/><br/>

### 获取个人所有订单 

列出你的订单列表。 

> GET spot/my/orders

请求频率10次/2s 

##### 参数

| Name | Type | Required | Description |
| :- | :- | :- | :- |
| symbol | string | yes | 币对的名称 |
| status | string | no | “active”、“finished”、“cancelling”或者“cancelled”(默认为“active”) |
| limit | int | no | 每页返回最大记录数(默认值为10，最大值为100) |
| page | int | no | 查询订单的当前页 |
| isHistory | boolean | no | true:查询7天之前的订单(finished和cancelled的时候才有效),false:查询7天之内的订单 |


##### 返回值

```json
{
  "result": [
    {
      "order_id": "4303100732",
      "client_oid": "...",
      "symbol": "LTC_BTC",
      "type": "limit",
      "side": "buy",
      "price": "0.019708",
      "amount": "0.322",
      "base_filled": "0.261",
      "quote_filled": "23.123",
      "status": "active",
      "created_time": 1521142156000
    },
	...
  ]
}
```
<br/><br/><br/><br/><br/><br/>

----
### 历史成交 

----
##### 交易 

获取你历史成交的清单 

> GET spot/my/trades

请求频率10次/2s 

##### 参数

| Name | Type | Required | Description |
| :- | :- | :- | :- |
| symbol | string | yes | 货币对的名称 |
| order_id | string | yes | 订单的ID |
| limit | int | no | 每页返回最大记录数(默认值为50，最大值为500) |

##### 返回值

```json
{
  "result": [
    {
      "trade_id": 231234,
      "order_id": "4303100732",
      "symbol": "LTC_BTC",
      "price": "4.00000000",
      "amount": "1.00000000",
      "fee": "0.10000000",
      "fee_currency": "BTC",
      "side": "buy",
      "liquidity": "maker",
      "time": 1499865549590
    }
    ...
  ]
}
```
<br/><br/><br/><br/><br/><br/>

----
### 公共接口
----
#### Ticker Price

获得24小时货币对的最新价格。 

> GET spot/ticker/price

##### 参数

| Name | Type | Required | Description |
| :- | :- | :- | :- |
| symbol | string | no | 货币对的名称。 |

##### 返回值

``` json
{
  "result": [
    {
      "symbol": "LTC_BTC",
      // The timestamp at which this information was valid
      "time": 1512744759000,
      // The price at which the last trade executed
      "last": "0.019984"
    },
    ...
  ]
}
```
<br/><br/><br/><br/><br/><br/>
----
#### Ticker

获得24小时货币对的统计数据。 

> GET spot/ticker

##### 参数

| Name | Type | Required | Description |
| :- | :- | :- | :- |
| symbol | string | no | 货币对的名称。 |

##### 返回值

``` json
{
  "result": [
    {
      "symbol": "LTC_BTC",
      // The timestamp at which this information was valid
      "time": 1512744759000,
      // Current best bid price
      "bid": "0.019983",
      // Current best ask price
      "ask": "0.019984",
      // The price at which the last trade executed
      "last": "0.019984",
      "change": "4.02",
      // Open price of the last 24 hours
      "open": "0.019223",
      // Highest price of the last 24 hours
      "high": "0.020268",
      // Lowest price of the last 24 hours
      "low": "0.019107",
      // of the last 24 hours
      "volume": "1425.391",
      // of the last 24 hours
      "quote_volume": "28.046991116"
    },
    ...
  ]
}
```
<br/><br/><br/><br/><br/><br/>

----
### 最新成交价 

获取一个货币对的最新价。 

> GET spot/order_book

##### 参数

| Name | Type | Required | Description |
| :- | :- | :- | :- |
| symbol | string | yes | 货币对的名称 |
| limit | int | no | 每页返回最大记录数(默认值为60，最大值为200) |



##### 返回值

```json
{
  "result": {
    "bids": [
      // price, amount
      [ "0.018", "0.5" ],
      [ "0.0187", "0.176" ],
      ...
    ],
    "asks": [
      // price, amount
      [ "0.019726", "0.017" ],
      [ "0.019727", "0.349" ],
	  ...
    ]
  }
}
```
<br/><br/><br/><br/><br/><br/>

----
### 历史成交 

 获取一个货币对的最新交易列表。 

> GET spot/trades

##### 参数

| Name | Type | Required | Description  |
| :- | :- | :- | :- |
| symbol | string | yes | 货币对的名称 |
| limit | int | no | 每页返回最大记录数(默认值为50，最大值为500) |

##### 返回值

```json
{
  "result": [
    // [ time, price, amount, side ]
    [ 1521119063078, "0.020022", "0.018", "buy" ],
    [ 1521119063078, "0.020022", "-0.018", "sell" ],
    ...
  ]
}
```
<br/><br/><br/><br/><br/><br/>

----
### k线
获取一份k线数据列表。 

> GET spot/candles

##### 参数

| Name | Type | Required | Description |
| :- | :- | :- | :- |
| symbol | string | yes | 货币对的名称。 |
| period | string | yes | 如 "1min", "3min", "5min", "15min", "30min", "1hour", "2hour", "4hour", "6hour", "12hour", "1day",  "1week" |
| since | long | no |  |
| limit | int | no | 每页返回最大记录数(默认为200，最大值为1000) |

##### 返回值

```json
{
  "result": [
    [
      1521119063000,  // Open time
      "0.00000000",  // Open
      "0.00000000",  // High
      "0.00000000",  // Low
      "0.00000000",  // Close
      "0.00000000",  // Base Volume
      "0.00000000",  // Quote volume
    ],
    ...
  ]
}
```
<br/><br/><br/><br/><br/><br/>

----
### 参考 

### 错误的请求码
| Code | Message |
| :- | :- |
| 10000 | 必选参数不能为空                             |
| 10001 | 用户请求频率过快                             |
| 10002 | 用户请求过期                               |
| 10003 | 请求参数错误                               |
| 10004 | 此IP不在访问白名单内                          |
| 10006 | api_key不存在                           |
| 10007 | 签名不匹配                                |
| 10017 | API鉴权失败                              |
| 1048  | 用户被冻结                                |
| 20000 | 产品不存在                                |
| 20001 | 下单类型错误                               |
| 20002 | 下单方向错误                               |
| 20003 | STP Value 错误 0 1                     |
| 20004 | 长度限制                                 |
| 20005 | 价格小于或者等于0                            |
| 20006 | 下单量小于最小交易单位                          |
| 20007 | 账户余额不足                               |
| 20008 | 基准货币不是标准增量                           |
| 20009 | 计价货币不是标准增量                           |
| 20010 | 订单Id不能为空                             |
| 20011 | 订单状态错误                               |
| 20012 | 时间类型错误                               |
| 20013 | location Value 'finish' or 'history' |
| 20014 | pageSize 超过100                       |
| 20015 | 订单不存在                                |
| 20016 | TiME_FORCE  错误                       |
| 20017 | POST NOLY 错误                         |
| 20018 | ORDER_FROM 错误                        |
| 20019 | 价格最高不能超过1000                         |
| 20020 | 请求过快，请稍后再试                         |
| 20021 | 未成交订单数量不得多于100                   |
| 99999 | System error |
