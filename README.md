# [WebSocket API](https://github.com/58COIN/58coin-api/blob/master/58coin-websocket-docs-en.md)

# 58Coin REST API for Spot Trade
This document covers the details of 58Coin REST APIs which were used to access your accounts, manage your orders and retrieve spot market data.



----
## General
The 58Coin REST API is HTTP-based. You can use it with any programming language that has an HTTP library.

### URL
``` xml
https://api.58coin.com/v1/<endpoint>
```

### Responses
A successful response is returned with a single JSON object as follows.
```node
{
  "result": <a JSON object or array>
}
```
- **result**: A structured value that holds the content of the response. This member may contain String, JSON object or JSON array value.

### Errors
When the request encounters an error, the response contains a error member with a value that is an object as follows.
```python
{
  "error": {
    // error code
    "code": 10001, 
    // error message
    "message": "string"  
  }
}
```
To get more about error codes and messages, please refer to [Error Code](#Error Code)
<br/><br/><br/><br/><br/><br/>

----
## Private Endpoints
The private endpoints are available for account management and order management. All private endpoints require authentication.

### Authentication

----
#### Generating an API Key
In order to access the private endpoints, you must generate an API key and an Secret key using this page.
<br/><br/><br/><br/><br/><br/>

----
#### Creating a Request
All Private requests must contain the following headers:

- **X-58COIN-APIKEY** The API key as a string.
- **X-58COIN-SIGNATURE** The base64-encoded signature.
- **X-58COIN-TIMESTAMP** The timestamp for the request.

<br/><br/><br/><br/><br/><br/>

----
#### Signing a Message
The signature is the hex digest of an HMAC-SHA256 hash where the message is your query string and the secret key is your API secret.

> signature = Hex.encodeHex(HMAC-SHA256(queryString, secretKey),UPPER)

<br/><br/><br/><br/><br/><br/>

----
### product

----
#### All Currency pair

> GET product/list

Request frequency 10 times/2s

**Parameters**

| Name | Type | Required | Description |

| :- | :- | :- | :- |

| name | string | no | null |

**Response**

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

<br/><br/><br/><br/><br/><br/>

----
### Accounts

----
#### View accounts
Get a list of your spot trading accounts.
> GET spot/my/accounts

Request frequency 10 times/2s

**Parameters**
None

**Response**
```node
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
### Orders

----
#### Order Status
An valid order may have four following status values during its life cycle.
- **Received**: The order is sent to the matching engine.
- **Active**: The order is not fully filled immediately, and it will stay in the active state until cancelled or subsequently filled by new orders.
- **Finished**: The order is fully filled.
- **Cancelling**: The order is cancelled but the remaining holds have not been removed.  
- **Cancelled**: The order is cancelled.
<br/><br/><br/><br/><br/><br/>

----
#### Place a New Order
Place a new order to buy or sell the assets.
> POST spot/my/order/place

Request frequency 20 times/2s

**Common Parameters**

| Name | Type | Required | Description |
| :----- | :------- | :--------- | :---------------- |
| client_oid | string | no | Your custom order ID |
| symbol | string | yes | The symbol of the currency pair |
| type | string | yes | "limit" or "market" |
| side | string | yes | "buy" or "sell" |

**Limit Order Parameters**

| Name | Type | Required | Description |
|:- |:- |:- |:- |
| price | string | yes | Price to buy or sell at  |
| amount | string | yes | How much you want to buy or sell |

**Market Order Parameters**

| Name | Type | Required | Description |
|:- |:- |:- |:- |
| amount | string | yes | How much you want to buy or sell |

**Response**
```node
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


----
#### Cancel an Order
Cancel a previously placed order.
> POST spot/my/order/cancel

Request frequency 20 times/2s

**Parameters**

| Name | Type | Required | Description |
|:- |:- |:- |:- |
| symbol | string | yes | The symbol of the currency pair |
| order_id | string | yes | The order ID given by /order/place |

**Response**
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


----
#### Get an Order
Get the information of an order.
> GET spot/my/order

Request frequency 10 times/2s

**Parameters**

| Name | Type | Required | Description |
| :- | :- | :- | :- |
| symbol | string | yes | The symbol of the currency pair |
| order_id | string | yes | The order ID given by /order/place |

**Response**
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

#### Get Orders
Get a list of your orders.
> GET spot/my/orders

Request frequency 10 times/2s

**Parameters**

| Name | Type | Required | Description |
| :- | :- | :- | :- |
| symbol | string | yes | The symbol of the currency pair |
| status | string | no | "active", "finished", "cancelling" or "cancelled" (default is "active") |
| limit | int | no | Limit the number of records returned (default is 10, max is 100) |
| page | int | no | The current page when paging |
| isHistory | boolean | no | true: query orders before 7 days(Only effective when finish and cancel),false: Inquire for orders within 7 days |

**Response**
```node
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


----
### Historical Data

----
#### Trades
Get a list of your past trades.
> GET spot/my/trades

Request frequency 10 times/2s

**Parameters**

| Name | Type | Required | Description |
| :- | :- | :- | :- |
| symbol | string | yes | The symbol of the currency pair |
| order_id | string | yes | The order ID given by /order/place |
| limit | int | no | Limit the number of records returned (default is 50, max is 500) |

**Response**
```node
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


----
## Public Endpoints
----
### Ticker Price
Get 24 hour last price for currency pair.
> GET spot/ticker/price

**Parameters**

| Name | Type | Required | Description |
| :- | :- | :- | :- |
| symbol | string | no | The symbol of the currency pair |

**Response**
```node
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
----
### Ticker
Get 24 hour statistics for currency pair.
> GET spot/ticker

**Parameters**

| Name | Type | Required | Description |
| :- | :- | :- | :- |
| symbol | string | no | The symbol of the currency pair |

**Response**
```node
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


----
### Order Book
Get a list of active orders for a currency pair.
> GET spot/order_book

**Parameters**

| Name | Type | Required | Description |
| :- | :- | :- | :- |
| symbol | string | yes | The symbol of the currency pair |
| limit | int | no | Limit the number of records returned (default is 60, max is 200) |

**Response**
```node
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
### Historical Trades
Get a list of latest trades for a currency pair.
> GET spot/trades

**Parameters**

| Name | Type | Required | Description  |
| :- | :- | :- | :- |
| symbol | string | yes | The symbol of the currency pair |
| limit | int | no | Limit the number of records returned (default is 50, max is 500) |

**Response**
```node
{
  "result": [
    // [ time, price, amount, side ]
    [ 1521119063078, "0.020022", "0.018", "buy" ],
    [ 1521119063078, "0.020022", "-0.018", "sell" ],
    ...
  ]
}
```


----
### Candles
Get a list of candlestick chart data.
> GET spot/candles

**Parameters**

| Name | Type | Required | Description |
| :- | :- | :- | :- |
| symbol | string | yes | The symbol of the currency pair |
| period | string | yes | Desired interval like "1min", "3min", "5min", "15min", "30min", "1hour", "2hour", "4hour", "6hour", "12hour", "1day", "1week" |
| since | long | no |  |
| limit | int | no | Limit the number of records returned (default is 200, max is 1000) |

**Response**
```node
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


----
## Reference

### Error Code
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
