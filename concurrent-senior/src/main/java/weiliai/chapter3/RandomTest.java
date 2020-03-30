package weiliai.chapter3;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @Author: Doug Li
 * @Date 2020/3/28
 * @Describe: 随机数测试
 */
public class RandomTest {

    public static void main(String[] args) {

    }


    public static void testRandom(){
        //创建一个默认种子的随机数生成器
        final Random random = new Random();
        //输出10个0-5(包含0不包含5)之间的随机数
        for (int i = 0; i < 10; i++) {
            System.out.println(random.nextInt(5));
        }
    }

    public static void testThreadLocalRandom(){
        //获取当前线程随机数生成器
        final ThreadLocalRandom random = ThreadLocalRandom.current();
        //输出10个0-5(包含0不包含5)之间的随机数
        for (int i = 0; i < 10; i++) {
            System.out.println(random.nextInt(5));
        }
    }
}
