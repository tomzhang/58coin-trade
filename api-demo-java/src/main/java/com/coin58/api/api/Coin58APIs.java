package com.coin58.api.api;

/**
 * @author coin58 - 2018/3/26.
 */
public class Coin58APIs {
    private static final String VERSION = "v1";
    private static final String PREFIX = "" + VERSION;
    /**
     * public
     */
    public static final String SPOT_TICKER = PREFIX + "/spot/ticker";
    public static final String ORDER_BOOK = PREFIX + "/spot/order_book";
    public static final String TRADES = PREFIX + "/spot/trades";
    public static final String CANDLES = PREFIX + "/spot/candles";

    /**
     * account
     */
    public static final String ACCOUNT = PREFIX + "/spot/my/accounts";

    /**
     * spot
     */
    public static final String PLACE_ORDER = PREFIX + "/spot/my/order/place";
    public static final String CANCEL_ORDER = PREFIX + "/spot/my/order/cancel";
    public static final String GET_ORDER = PREFIX + "/spot/my/order";
    public static final String ORDERS = PREFIX + "/spot/my/orders";
    public static final String ORDER_TRADES = PREFIX + "/spot/my/trades";
}
