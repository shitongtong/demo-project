package cn.stt.nettysocket.demo5.server;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2017/9/4.
 */
public class ClientChannelMap {

    //phone : channelId
    private static Map<String, String> map = new ConcurrentHashMap<>();

    public static void add(String phone, String channelId) {
        map.put(phone, channelId);
    }

    public static String get(String phone) {
        return map.get(phone);
    }

    public static void remove(String phone) {
        map.remove(phone);
    }

    public static void removeById(String channelId) {
        for (Map.Entry entry : map.entrySet()) {
            if (entry.getValue() == channelId) {
                map.remove(entry.getKey());
            }
        }
    }
}
