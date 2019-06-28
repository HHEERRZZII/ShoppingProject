package com.herzi.feign;

import com.herzi.api.service.MemberService;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;

@Component
@FeignClient(value = "member")
public interface MemberServiceFeign extends MemberService {
    //feign调用
}
