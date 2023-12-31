package hello.core.autowired;


import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Grade;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import hello.core.member.Member;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class AllBeanTest {

    @Test
    void findAllBean() {

        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class,DiscountService.class);
        DiscountService discountService = ac.getBean(DiscountService.class);
        Member member = new Member(1L,"userA", Grade.VIP);
        int fixDiscountPrice = discountService.discount(member,10000,"fixDiscountPolicy");

        assertThat(discountService).isInstanceOf(DiscountService.class);
        assertThat(fixDiscountPrice).isEqualTo(1000);

        int rateDiscountPrice = discountService.discount(member,20000,"rateDiscountPolicy");
        assertThat(rateDiscountPrice).isEqualTo(2000);


    }

   static class DiscountService{
        private final Map<String, DiscountPolicy> policyMap;
        private final List<DiscountPolicy> discountPolicyList;

       DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> discountPolicyList) {
           this.policyMap = policyMap;
           this.discountPolicyList = discountPolicyList;

           System.out.println("policyMap = " + policyMap);
           System.out.println("discountPolicyList = " + discountPolicyList);
       }




       public int discount(Member member, int price, String discountCode) {
           DiscountPolicy discountPolicy = policyMap.get(discountCode);

           return discountPolicy.discount(member, price);
       }
   }

}
