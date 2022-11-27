package cc.nolink.example.custom.dto;

import lombok.Data;

import java.util.Date;

/**
 * @auth lockgate
 * @description 自定义基础DTO对象
 * @since 2022/10/24 21:55
 */
@Data
public class CustomBaseDTO {

    /*
     * 自定义的基础对象，可以在有需求的情况下，声明规范的基础字段，常见的比如下列字段
     */
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 组织机构编码
     */
    private String orgCode;

    /**
     * 租户标识
     */
    private String tenant;

    /**
     * 应用标识
     */
    private String appId;

    /**
     * create_time 创建时间戳
     */
    private Date createTime;

    /**
     * 创建用户标识
     */
    private String createUser;

    /**
     * 创建用户名称，属于冗余存储
     */
    private String createUserName;

    /**
     * update_time 最后更新时间
     */
    private Date updateTime;

    /**
     * 最后更新用户标识
     */
    private String updateUser;

    /**
     * 最后更新用户名称，属于冗余存储
     */
    private String updateUserName;

}
