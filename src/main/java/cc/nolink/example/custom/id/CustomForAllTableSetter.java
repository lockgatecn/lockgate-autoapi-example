package cc.nolink.example.custom.id;

import cc.nolink.example.custom.dto.CustomBaseDTO;
import cn.lockgate.autoapi.runtime.aspect.handler.table.ITableOperationHandler;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;

/**
 * @auth lockgate
 * @description
 * @since 2022/10/24 21:53
 */
@Component
public class CustomForAllTableSetter implements ITableOperationHandler {

    /**
     * 插入之前设置值：针对所有配置表生效
     * 常见的需要设置的字段：id、创建人、创建时间戳、更新人、更新时间戳
     *
     * @param entity
     */
    @Override
    public void doBeforeInsert(Object entity) {
        // 方法 1：直接按数据库字段名进行赋值
        // setFieldValue(entity, "id", 1500l);
        // 方法 2：配置中定义了对象的父基础对象CustomBaseDTO，可以直接对该对象赋值
        /*
        if (Objects.nonNull(entity)) {
            ((CustomBaseDTO)entity).setId(1552l);
        }
        */

        // 除了赋值id，同时也可以赋值基础字段，如下
        // 方法 1：直接按数据库字段名进行赋值
        /*
        setFieldValue(entity, "create_time", Date.from(Instant.now()));
        setFieldValue(entity, "create_user", "102");
        setFieldValue(entity, "create_user_name", "阀门02");
         */
        // 方法 2：配置中定义了对象的父基础对象CustomBaseDTO，可以直接对该对象赋值
        if (Objects.nonNull(entity)) {
            ((CustomBaseDTO)entity).setOrgCode("C005");
            ((CustomBaseDTO)entity).setTenant("T005");
            ((CustomBaseDTO)entity).setAppId("swagger005");
            ((CustomBaseDTO)entity).setCreateTime(Date.from(Instant.now()));
            ((CustomBaseDTO)entity).setCreateUser("105");
            ((CustomBaseDTO)entity).setCreateUserName("阀门05");
        }
    }

    /**
     * 更新之前设置值：针对所有配置表生效
     * 常见的需要设置的字段：更新人、更新时间戳
     *
     * @param entity
     */
    @Override
    public void doBeforeUpdate(Object entity) {
        if (Objects.nonNull(entity)) {
            ((CustomBaseDTO)entity).setUpdateTime(Date.from(Instant.now()));
            ((CustomBaseDTO)entity).setUpdateUser("105");
            ((CustomBaseDTO)entity).setUpdateUserName("阀门05");
        }
    }

}
