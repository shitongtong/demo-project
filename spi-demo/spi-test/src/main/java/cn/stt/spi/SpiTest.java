package cn.stt.spi;

import cn.stt.spi.api.Log;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2017/8/18.
 */
public class SpiTest {
    public static void main(String[] args) {
        Log log = null;
        ServiceLoader<Log> serviceLoader = ServiceLoader.load(Log.class);
        Iterator<Log> logs = serviceLoader.iterator();
        while (logs.hasNext()) {
            log = logs.next();
            log.write("wwww");
        }
        /*for (Log log1 :serviceLoader){
            log1.write("hhhh");
        }*/
    }
}
