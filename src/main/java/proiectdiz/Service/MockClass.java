package proiectdiz.Service;

import java.util.List;

public class MockClass {
    private static String key;
    private static String p;
    private static List<String> uuid_list;

    public static void setKey(String key) {
        MockClass.key = key;
    }

    public static String getKey() {
        return key;
    }

    public static String getP() {
        return p;
    }

    public static List<String> getUuid_list() {
        return uuid_list;
    }

    public static void setP(String p) {
        MockClass.p = p;
    }

    public static void setUuid_list(List<String> uuid_list) {
        MockClass.uuid_list = uuid_list;
    }
}
