package com.arobs.internship.library.dao.impl.hibernate.util;

import java.util.List;

public class QueryUtil {

    public static <T> T safeGetUniqueResult(List resultList) {
        if (resultList == null || resultList.size() != 1) {
            return null;
        }
        return (T) resultList.get(0);
    }
}
