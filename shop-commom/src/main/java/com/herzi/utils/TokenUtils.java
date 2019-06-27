package com.herzi.utils;

import com.herzi.constants.Constants;

import java.util.UUID;

public class TokenUtils {
        public static String getMemberToken() {
            //可能会生成多个token，所以加上uuid
            return Constants.TOKEN_MEMBER + "-" + UUID.randomUUID();
        }
}
