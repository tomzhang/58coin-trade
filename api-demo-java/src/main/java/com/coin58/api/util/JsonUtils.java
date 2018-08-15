package com.coin58.api.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.fasterxml.jackson.databind.DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT;

/**
 * @author coin58
 * @date 2017/10/11
 */
@Slf4j
public class JsonUtils {

    /**
     * 不输出value为空的结点
     */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
        .setSerializationInclusion(Include.NON_NULL)
        .enable(ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
        .configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .configure(Feature.ALLOW_SINGLE_QUOTES, true)
        // BigDecimal要去尾零和写成string
        .registerModule(new SimpleModule().addSerializer(BigDecimal.class, new JsonSerializer<BigDecimal>() {
            @Override
            public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers)
                throws IOException {
                gen.writeString(value.stripTrailingZeros()
                    .toPlainString());
            }
        }));

    public static String toJSONString(Object o) {
        try {
            return OBJECT_MAPPER.writeValueAsString(o);
        } catch (Throwable e) {
            log.error("convert json error ", e.getMessage());
        }
        return StringUtils.EMPTY;
    }

    public static <T> T parseJson(String json, TypeReference typeReference) throws IOException {
        return OBJECT_MAPPER.readValue(json, typeReference);
    }

}
