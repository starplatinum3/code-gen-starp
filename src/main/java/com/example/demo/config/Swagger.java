package com.example.demo.config;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



@Configuration

@EnableSwagger2

public class Swagger {


    private static final String SPLITOR = ",";

    @Bean

    public Docket productApi() {

        // 全局参数
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        tokenPar.name("token").description("token userId").modelRef(new ModelRef("string"))
                .parameterType("header").required(false).build();
        pars.add(tokenPar.build());
//        这可能造成 forbind恩吗,问题不在这 是spring security的锅
//        2021年7月28日16:19:23  mqp

//————————————————
//        版权声明：本文为CSDN博主「蓝色D风车」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//        原文链接：https://blog.csdn.net/H_233/article/details/103129250

        return new Docket(DocumentationType.SWAGGER_2)

//                .apiInfo(apiInfo())
//
//                .select()
//
//                .apis(RequestHandlerSelectors.basePackage("zucc.kinect.controller" + SPLITOR
//                        + "zucc.kinect.security.controller"))//添加ApiOperiation注解的被扫描
////                .apis(RequestHandlerSelectors.basePackage("zucc.kinect.security.controller"))
////                添加 api ，2021年7月27日17:25:28  mqp
//
//                .paths(PathSelectors.any())
//
//                .build();

                .apiInfo(apiInfo()).select().apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any()).build()
                // 指定全局参数
//        https://blog.csdn.net/H_233/article/details/103129250
//                2021年7月28日14:58:28  mqp
                .globalOperationParameters(pars);
//                2021年7月27日21:17:02 mqp ，token的api
//                .securityContexts(securityContext())
//                .securitySchemes(securitySchemes());

//————————————————
//        版权声明：本文为CSDN博主「Nio不是nio」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//        原文链接：https://blog.csdn.net/qq_35473192/article/details/106756728

    }

//    private List<SecurityScheme> securitySchemes() {
//        return Collections.singletonList(new ApiKey("JWT", SecurityConstants.TOKEN_HEADER, "header"));
//    }

//    private List<SecurityContext> securityContext() {
//        SecurityContext securityContext = SecurityContext.builder()
//                .securityReferences(defaultAuth())
//                .build();
//        return Collections.singletonList(securityContext);
//    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Collections.singletonList(new SecurityReference("JWT", authorizationScopes));
    }

    private ApiInfo apiInfo() {

        return new ApiInfoBuilder().title("eye").description("服务平台")

                .version("1.0").build();

    }

}

