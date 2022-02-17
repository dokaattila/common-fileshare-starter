package com.codecool.fileshare;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

public class Util {
    public static UUID uuidFromBase64(String str) {
        byte[] bytes = Base64.getDecoder().decode(str.getBytes(StandardCharsets.UTF_8));
        return UUID.nameUUIDFromBytes(bytes);
    }
}
