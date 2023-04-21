package top.starp.util;

public class RandomUtil {
    // 方法二：Math.random()
    //     返回带有正号的{@code double}值，该值大于*等于或等于{@code 0.0}并且小于{@code 1.0}
  public static   int  randomInt(int from ,int to){
        //        java 随机 1 到 10
        // 方法二：Math.random()
        //     返回带有正号的{@code double}值，该值大于*等于或等于{@code 0.0}并且小于{@code 1.0}
//        int num2 = (int) (Math.random()*10+1);
        int num2 = (int) (Math.random()*to+from);
//        System.out.println(num2);
//————————————————
//        版权声明：本文为CSDN博主「骑着蜗牛ひ追导弹'」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//        原文链接：https://blog.csdn.net/qq_45797116/article/details/115007744
//
        return  num2;
    }

    public static void main(String[] args) {
//        int i = RandomUtil.randomInt(1, 10);
//        4,10,4,4,8,7,10,3,5,7,
        for (int j = 0; j <10 ; j++) {
            int i = RandomUtil.randomInt(1, 10);
            System.out.print(i+",");
        }
    }
}
