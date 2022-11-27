package cc.nolink.example.custom.aspect;

import cn.lockgate.autoapi.core.annotation.ActionType;
import cn.lockgate.autoapi.core.annotation.AssignedTableName;
import cn.lockgate.autoapi.core.enums.ActionEnum;
import cn.lockgate.autoapi.runtime.aspect.handler.action.IOutsideTransactionOperationAspectHandler;
import org.springframework.stereotype.Component;

/**
 * @auth lockgate
 * @description
 * @since 2022/11/3 22:39
 */
@AssignedTableName("mall_order")    // 指定表，必选
@ActionType(ActionEnum.SAVE)    // 指定方法，必选
@Component
public class MallOrderSaveOutsideTransactionServiceAspectHandler implements IOutsideTransactionOperationAspectHandler {

    /**
     * try块：逻辑执行前
     * 具体参数类型：cn.lockgate.autoapi.runtime.IApiAutoOperation对应方法的参数和返回类型
     * @param tableName
     * @param args
     */
    @Override
    public void doBefore(String tableName, Object[] args) {
        // business code here ...
    }

    /**
     * try块：逻辑执行后
     * 具体参数类型：cn.lockgate.autoapi.runtime.IApiAutoOperation对应方法的参数和返回类型
     * @param tableName
     * @param args
     * @param result
     */
    @Override
    public void doAfter(String tableName, Object[] args, Object result) {
        // business code here ...
    }

    /**
     * catch块中调用
     * result:
     *  true -> 继续抛出异常
     *  false -> 只打印异常信息，不抛出异常
     * 具体参数类型：cn.lockgate.autoapi.runtime.IApiAutoOperation对应方法的参数和返回类型
     * @param tableName
     * @param args
     */
    @Override
    public boolean doException(String tableName, Object[] args, Throwable ex) {
        // business code here ...
        return true;
    }

    /**
     * finally块中调用
     * 具体参数类型：cn.lockgate.autoapi.runtime.IApiAutoOperation对应方法的参数和返回类型
     * @param tableName
     * @param args
     * @param result
     */
    @Override
    public void doFinally(String tableName, Object[] args, Object result) {
        // business code here ...
    }

}
