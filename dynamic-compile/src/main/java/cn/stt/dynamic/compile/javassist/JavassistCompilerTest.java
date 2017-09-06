package cn.stt.dynamic.compile.javassist;

import cn.stt.dynamic.compile.util.ClassHelper;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Method;

/**
 * 使用动态编译，但是无法获取System.out.print的数据
 *
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2017/8/25.
 */
public class JavassistCompilerTest {
    public static void main(String[] args) throws Exception {
        File file = new File("D:\\project_git\\stt\\demo-project\\dynamic-compile\\src\\main\\java\\cn\\stt\\dynamic\\compile\\javassist\\JavassistCompilerTest.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = bufferedReader.readLine())!=null){
            sb.append(line);
        }
        String code = sb.toString();
        JavassistCompiler javassistCompiler = new JavassistCompiler();
        Class<?> aClass = javassistCompiler.compile(code, ClassHelper.getCallerClassLoader(JavassistCompilerTest.class));
        Method method = aClass.getDeclaredMethod("main",String[].class);
        method.invoke(null, (Object) new String[]{});

    }

    @Test
    public void test1() throws Exception {
        HelloWorld helloWorld = new HelloWorld();
        Class clazz = helloWorld.getClass();
        Method method = clazz.getDeclaredMethod("main",String[].class);
        Object invoke = method.invoke(null, (Object) new String[]{});
        System.out.println(invoke.toString());
    }

    @Test
    public void test() throws Exception {
        Foo foo = new Foo("这个一个Foo对象！");
        Class clazz = foo.getClass();
        Method m1 = clazz.getDeclaredMethod("outInfo");
        Method m2 = clazz.getDeclaredMethod("setMsg", String.class);
        Method m3 = clazz.getDeclaredMethod("getMsg");
        m1.invoke(foo);
        m2.invoke(foo, "重新设置msg信息！");
        String msg = (String) m3.invoke(foo);
        System.out.println(msg);
    }

    class Foo {
        private String msg;

        public Foo(String msg) {
            this.msg = msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getMsg() {
            return msg;
        }

        public void outInfo() {
            System.out.println("这是测试Java反射的测试类");
        }
    }
}
