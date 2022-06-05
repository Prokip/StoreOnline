package com.online.store.util;

import static com.online.store.util.Constant.LONG_NULL;

public class ValidationUtil {

    public static boolean isNullOrEmpty(String str) {
        return (str == null || str.trim().isEmpty());
    }

    public static boolean isNull(Boolean field) {
        return field == null;
    }

    public static boolean isNull(Integer field) {
        return field == null;
    }

    public static boolean isNull(Long id) {
        return (id == 0) || (LONG_NULL.equals(id));
    }


}
