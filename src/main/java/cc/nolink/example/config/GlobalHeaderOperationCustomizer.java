package cc.nolink.example.config;

import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

/**
 * @auth nolink
 * @create 2021/9/28 23:34
 * @description 公共请求头参数定义
 */
@Component
public class GlobalHeaderOperationCustomizer implements OperationCustomizer {

    @Override
    public Operation customize(Operation operation, HandlerMethod handlerMethod) {
        // 版本
//        operation.addParametersItem(new Parameter()
//                .in(ParameterIn.HEADER.toString())
//                .name(IHttpContext.KEY_VERSION)
//                .description("接口版本号")
//                .schema(new StringSchema()).example("v1.0.0")
//                .required(false));
//        // userId
//        operation.addParametersItem(new Parameter()
//                .in(ParameterIn.HEADER.toString())
//                .name(IHttpContext.KEY_USER_ID)
//                .description("用户ID")
//                .schema(new StringSchema()).example("-1")
//                .required(true));
//        // orgCode
//        operation.addParametersItem(new Parameter()
//                .in(ParameterIn.HEADER.toString())
//                .name(IHttpContext.KEY_ORG_CODE)
//                .description("组织机构编码")
//                .schema(new StringSchema()).example("")
//                .required(false));
//        // appId
//        operation.addParametersItem(new Parameter()
//                .in(ParameterIn.HEADER.toString())
//                .name(IHttpContext.KEY_APP_ID)
//                .description("接口消费者（调用者）应用编码")
//                .schema(new StringSchema()).example("")
//                .required(false));
//
//        operation.addParametersItem(new Parameter()
//                .in(ParameterIn.HEADER.toString())
//                .name(IHttpContext.KEY_TENANT)
//                .description("租户编码，在多租户场景中使用")
//                .schema(new StringSchema()).example("")
//                .required(false));

        operation.addParametersItem(new Parameter()
                .in(ParameterIn.HEADER.toString())
                .name("token")
                .description("令牌，登录认证场景中使用")
                .schema(new StringSchema()).example("")
                .required(false));

//        operation.addParametersItem(new Parameter()
//                .in(ParameterIn.HEADER.toString())
//                .name(IHttpContext.KEY_TRACE_ID)
//                .description("日志链路标识，便于日志追踪")
//                .schema(new StringSchema()).example("")
//                .required(false));

        return operation;
    }

}
