package io.github.pleuvoir.openapi;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@SpringBootApplication
public class PayOpenApiLauncher extends SpringBootServletInitializer {

    public static void main(String[] args) {


        List<Long> longs = Arrays.asList(
                200649567898438108L,
                200649897547214836L,
                200649871252034992L,
                652063945250586628L,
                652081369538674782L,
                652081458113953862L,
                652099301656248342L,
                200650234387206948L,
                651999024026534012L,
                651999266356609126L,
                200649897547214836L,
                200649897547214836L,
                200649897547214836L,
                200649871252034992L,
                652099467599724562L,
                200649175457602056L,
                651999024026534012L,
                651999024026534012L,
                200649897547214836L,
                200649897547214836L,
                200649871252034992L,
                200649871252034992L,
                652087942373720084L,
                651999382488531044L,
                200643885825823104L,
                200649897547214836L,
                200649897547214836L,
                652063945250586628L,
                652087942373720084L,
                200650234387206948L,
                200650234387206948L

        );

        HashSet<Long> longs1 = new HashSet<>(longs);
        for (Long aLong : longs1) {

            System.out.println(aLong);
        }
    }

}
