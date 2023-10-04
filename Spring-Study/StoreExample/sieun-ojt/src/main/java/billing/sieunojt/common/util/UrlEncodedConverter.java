package billing.sieunojt.common.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class UrlEncodedConverter {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String convertToUrlEncoded(Object obj) {
        Map<String, String> map = objectMapper.convertValue(obj, Map.class);

        return map.keySet().stream()
                .map(key -> {
                    try {
                        Class<?> typeClass = PropertyUtils.getPropertyType(obj, key);
                        String type = typeClass.getSimpleName();
                        String value = null;

                        if (type.equals("String")) {
                            value = map.get(key);
                        } else if (typeClass.isPrimitive()) {
                            value = String.valueOf(map.get(key));
                        }

                        return value != null && value.length() > 0
                                ? key + "=" + URLEncoder.encode(value, StandardCharsets.UTF_8)
                                : null;
                    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        e.printStackTrace();
                    }

                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.joining("&"));
    }

    public static MultiValueMap<String, String> convertToMap(Object dto) {
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            Map<String, String> map = objectMapper.convertValue(dto, new TypeReference<Map<String, String>>() {}); // (3)
            params.setAll(map); // (4)

            return params;
        } catch (Exception e) {
            log.error("Url Parameter 변환중 오류가 발생했습니다. requestDto={}", dto, e);
            throw new IllegalStateException("Url Parameter 변환중 오류가 발생했습니다.");
        }

    }
}
