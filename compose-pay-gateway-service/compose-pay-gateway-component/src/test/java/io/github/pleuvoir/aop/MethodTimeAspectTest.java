package io.github.pleuvoir.aop;

import java.util.regex.Pattern;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class MethodTimeAspectTest {

  public static void main(String[] args) {

    String content = "ZF20171109121329141048";
    String pattern = "[Z|D]F[0-9]{20}";

    boolean isMatch = Pattern.matches(pattern, content);
    System.out.println("是平台订单？" + isMatch);

    //判断是不是商户号
    String mid = "812345678910111";
    boolean isMid = Pattern.matches("[0-9]{15}", mid);
    System.out.println("是商户号？" + isMid);
  }


  private void tyest() {

  }
}
