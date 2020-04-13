package com.weiliai.chapter9;

import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * @Author: Doug Li
 * @Date 2020/4/12
 * @Describe: TimeUnit方法测试
 * 计算机原码,反码,补码,计算加减均按照补码
 *  使用int 4个字节举例
 *  例如:4 ,正数的原反补码都是原码
 *  原码:00000000 00000000 00000000 00000100
 *  反码:00000000 00000000 00000000 00000100
 *  补码:00000000 00000000 00000000 00000100
 *
 *  例如:-3,负数的反码是除符号位取反,补码是反码+1
 *  原码:10000000 00000000 00000000 00000011
 *  反码:11111111 11111111 11111111 11111100
 *  补码:11111111 11111111 11111111 11111101
 *
 *  计算加减 4+(-3)
 *  补码:00000000 00000000 00000000 00000100
 *  补码:11111111 11111111 11111111 11111101
 *  计算后
 *  原码:00000000 00000000 00000000 00000001
 *  反码:01111111 11111111 11111111 11111110
 *  补码:01111111 11111111 11111111 11111111
 *
 *  得到原码=1
 *
 */
public class TimeUnitTest {

    public static void main(String[] args) {
        System.err.println(Long.compareUnsigned(Long.MAX_VALUE,Long.MIN_VALUE));
        System.err.println(Long.MIN_VALUE);
    }

    @Test
    public void testConvert(){
        //将60分钟转为秒为单位
        System.err.println(TimeUnit.SECONDS.convert(60, TimeUnit.MINUTES));
        //将1000纳秒转为秒为单位
        System.err.println(TimeUnit.SECONDS.convert(1000, TimeUnit.NANOSECONDS));
    }

    @Test
    public void toUnit(){
        //24小时转换为天为单位
        System.err.println(TimeUnit.HOURS.toDays(24));
        //1440分钟转为天为单位
        System.err.println(TimeUnit.MINUTES.toDays(60*24));
    }

    @Test
    public void pattern(){
        System.err.println(Pattern.matches("[A-Za-z0-9]*",""));
        System.err.println(Pattern.matches("[A-Za-z]*","12"));

    }

}
