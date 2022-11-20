package cc.nolink.example.custom.aspect;

import cn.lockgate.autoapi.core.annotation.ActionType;
import cn.lockgate.autoapi.core.annotation.AssignedTableName;
import cn.lockgate.autoapi.core.enums.ActionEnum;
import cn.lockgate.autoapi.runtime.aspect.handler.action.IInsideTransactionOperationAspectHandler;
import org.springframework.stereotype.Component;

/**
 * @auth reset
 * @description
 * @since 2022/11/3 22:37
 */
@AssignedTableName("mall_order")
@ActionType(ActionEnum.SAVE)    // 注意：save和add是两个独立方法，两者互不相干
@Component
public class MallOrderSaveInsideTransactionServiceAspectHandler implements IInsideTransactionOperationAspectHandler {

    /**
     * try块：逻辑执行前
     * 具体参数类型：cn.lockgate.autoapi.runtime.IApiAutoOperation对应方法的参数和返回类型
     * @param tableName
     * @param args
     */
    public void doBefore(String tableName, Object[] args) {

    }

    /**
     * try块：逻辑执行后
     * 具体参数类型：cn.lockgate.autoapi.runtime.IApiAutoOperation对应方法的参数和返回类型
     * @param tableName
     * @param args
     * @param result
     */
    public void doAfter(String tableName, Object[] args, Object result) {

    }

}
