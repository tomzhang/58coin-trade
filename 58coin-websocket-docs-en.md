# WebSocket API

## Request and subscription description

## 1. wss url
wss://ws.58coin.com/websocket

## 2. Parameter description

| Name | Type | Required | Description |
| :- | :- | :- | :- |
| event | string | yes | event name，like "SUB"，"CANCEL"，"CALL" |
| product | string | yes | The symbol of the currency pair |
| period | string | no | Desired interval like "1min", "3min", "5min", "15min", "30min", "1hour", "2hour", "4hour", "6hour", "12hour", "1day",  "1week" |
## 3. response

response message is compressed with deflate,
but response message of cancelling and subscribing will not be compressed.

## 4. content

### (1)ticker

Get 24 hour statistics for currency pair.
```
{
    
    "event" : "SUB",
    "type" : "TICKER",
    "product" : "btc_usdt"
   
}

```
response:
```
{"event":"SUB","product":"btc_usdt","type":"TICKER","msg":"success","code":"0"}
```
Subscribe to success，Return data:
```
{
    "product": "btc_usdt",
    "type": "TICKER",
    "data": {
        "id": "1",
        "open": "0",
        "high": "30004.8",
        "low": "0",
        "last": "0",
        "sell": "0",
        "buy": "0",
        "volume": "0.8",
        "amount": "24002.32",
        "productId": "131073",
        "change": "0",
        "time": "1508126546000"
    }
}

```
### (2)orderBook 
Get a list of active orders for a currency pair.
```
{
    
    "event" : "SUB",
    "type" : "ORDER_BOOK",
    "product" : "btc_usdt"
   
}
```
response:
```
{"event":"SUB","product":"btc_usdt","type":"ORDER_BOOK","msg":"success","code":"0"}
```
Subscribe to success，Return data:
```
{
    "product": "btc_usdt",
    "type": "ORDER_BOOK",
    "data": {
        "bids": [
            [
                10, //price
                1 //amount
            ]
        ],
        "asks": [
            [
                30000,
                0.2
            ]
        ]
    }
}

```
### (3)tradeHistory
Get a list of latest trades for a currency pair.
```
{
    
    "event" : "SUB",
    "type" : "TRADE_HISTORY",
    "product" : "btc_usdt"
   
}
```
response:
```
{"event":"SUB","product":"btc_usdt","type":"TRADE_HISTORY","msg":"success","code":"0"}
```
Subscribe to success，Return data:
```
{
    "product": "btc_usdt",
    "type": "TRADE_HISTORY",
    "data": [
        [
            1508331900000, //date
            [
                10, // price
                [
                    1 // amount
                ]
            ]
        ],
        [
            1508331900000,
            [
                10,
                [
                    1
                ]
            ]
        ]
    ]
}

```
### (4)barData
Get a list of candlestick chart data.
```
{
    
    "event" : "SUB",
    "type" : "BAR_DATA",
    "product" : "btc_usdt",
    "params" : {
           "period" : "1min" 
       }

}
```
response:
```
{"event":"SUB","product":"btc_usdt","type":"BAR_DATA","msg":"success","code":"0","params":{"period":"1min"}}
```
Subscribe to success，Return data:
```
{
    "product": "btc_usdt",
    "type": "BAR_DATA",
    "data": {
        "id": "5975",
        "open": "0",
        "high": "0",
        "low": "0",
        "close": "0",
        "volume": "0",
        "amount": "0",
        "productId": "131073",
        "type": "2", // 0~11 ['1min', '3min', '5min', '15min', '30min', '1hour', '2hour', '4hour', '6hour', '12hour', '1day', '1week']
        "time": "1508331900000"
    }
}

```

### 5. error code

  格式：
  ```
  {
      "code": "10010",
      "desc": "系统繁忙，请稍后再试"
  }

  ```
  代码：
  ```
  10000:无效请求
  10001:JSON格式错误
  10002:无效事件类型
  10003:事件参数不能为空
  10004:订阅种类错误
  10005:订阅种类不能为空
  10006:未登录
  10007:参数缺失
  10008:周期缺失
  10009:周期错误
  10010:系统繁忙，请稍后再试

  ```

