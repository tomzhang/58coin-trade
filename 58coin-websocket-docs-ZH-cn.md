# WebSocket API

## 请求与订阅说明

## 1. 访问地址
wss://ws.58coin.com/websocket

## 2. 参数

| Name | Type | Required | Description |
| :- | :- | :- | :- |
| event | string | yes | 事件名称，如"SUB"订阅，"CANCEL"取消订阅，"CALL"查询最近一次快照 |
| product | string | yes | 货币对的名称。 |
| period | string | no | 如 "1min", "3min", "5min", "15min", "30min", "1hour", "2hour", "4hour", "6hour", "12hour", "1day",  "1week" |
## 3. response消息格式

返回消息会采用deflate进行压缩,需要 client 在收到数据之后解压;
其中的订阅、取消时的响应，不会压缩

## 4. 接口内容

### (1)ticker

获得24小时货币对的统计数据。
```
{
    
    "event" : "SUB",
    "type" : "TICKER",
    "product" : "btc_usdt"
   
}

```
返回值
```
{"event":"SUB","product":"btc_usdt","type":"TICKER","msg":"success","code":"0"}
```
订阅成功后，当市场有最新成交就会返回数据：
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
### (2)orderBook 深度的订阅
```
{
    
    "event" : "SUB",
    "type" : "ORDER_BOOK",
    "product" : "btc_usdt"
   
}
```
返回值
```
{"event":"SUB","product":"btc_usdt","type":"ORDER_BOOK","msg":"success","code":"0"}
```
订阅成功后，当深度有变化后返回数据：
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
### (3)tradeHistory 最新成交
```
{
    
    "event" : "SUB",
    "type" : "TRADE_HISTORY",
    "product" : "btc_usdt"
   
}
```
返回值
```
{"event":"SUB","product":"btc_usdt","type":"TRADE_HISTORY","msg":"success","code":"0"}
```
订阅成功后，返回数据：
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
### (4)barData k线订阅
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
返回值
```
{"event":"SUB","product":"btc_usdt","type":"BAR_DATA","msg":"success","code":"0","params":{"period":"1min"}}
```
订阅成功后，返回数据：
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

### 5. 错误消息

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

