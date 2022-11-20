# AutoApi（自动化接口）

**产品宗旨：通过配置的方式自动化处理项目开发中简单重复的内容，解放生产力，使其更加专注产品业务逻辑；快速标准化输出，缩短项目研发周期**
-- ------
### 1. 产品简介
- 通过简单的接口规则配置，自动生成13个单表crud接口 + 3个主子表关联接口，基本覆盖单表crud的所有场景  
- 支持各种场景自定义，在crud基础之上增加对复杂业务场景的自定义支持

### 2. 架构说明
#### 2.1 系统架构
![系统架构图](https://user-images.githubusercontent.com/117927019/202881127-ab0ff79c-227f-4097-bbed-6ead99c8c9f9.png)
#### 2.2 技术栈
|seq|选型|版本|
|:---:|---|---|
|1|spring boot|2.7.3|
|2|spring-web|5.3.22|
|3|javax.servlet-api|4.0.1|
|4|spring-boot-starter-log4j2|2.7.3|
|5|springdoc-openapi-ui|1.6.11|
|6|spring-boot-starter-jdbc|2.7.3|
|7|groovy-all|3.0.9|
|8|velocity|1.7|
|9|jackson-datatype-jdk8|2.13.3|

### 3. 功能说明
#### 3.1 接口全景展示
![接口全景图](https://user-images.githubusercontent.com/117927019/202881168-98725b52-8195-42c7-b050-dd0cbaae895e.png)
#### 3.2 部分接口示例
##### 3.2.1 分页查询接口
![分页查询接口示例图](https://user-images.githubusercontent.com/117927019/202881178-2f6861ba-732f-451c-a3f1-9776a1a95dbc.png)
##### 3.2.2 单对象保存接口
![api_save](https://user-images.githubusercontent.com/117927019/202881192-4036738b-b6e5-4705-809e-d6a40576eea8.png)
#### 3.3 自定义场景支持
|seq|场景|状态|
|:---:|---|---|
|1|自定义排序字段配置|支持|
|2|查询操作自定义，比如like等|支持|
|3|自定义id生成（默认数据库自增）|支持|
|4|公共字段赋值的支持，如create_time/create_user等|支持|
|5|自定义service实现的支持（复杂业务场景支持）|支持|
|6|事务前事务后，非事务前非事务后自定义操作的支持|支持|
|7|sql打印和级别定义（方便定位问题）|支持|
|8|自定义基础实体类支持|支持|
|9|主子表关联，支持定义对应的主表字段和字表字段|支持|
|10|mysql数据库支持|支持|
|11|postgres数据库支持|研发中|
|12|接口定义配置支持数据库持久化（默认为配置文件）|研发中|

### 4. 开发指引
#### 4.1 注意事项
本产品仅支持数值类型的id，比如int/long等，可以是多个字段的组合id，但要求至少有一个是数值类型
#### 4.2 基础功能配置
基础的api功能，只需要执行以下几个简单的步骤，即可完成
##### 4.2.1 配置步骤
###### (1) 创建数据库表，范例如下
_注意事项：表字段说明会直接提现在接口上，所以表注解越详细易懂，接口说明则越清晰，沟通成本越低_
```sql
-- 订单主表
CREATE TABLE `mall_order` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID编号',
  `user_id` bigint NOT NULL COMMENT '用户表的用户ID',
  `order_no` varchar(63) NOT NULL COMMENT '订单编码',
  `order_status` smallint DEFAULT NULL COMMENT '订单状态',
  `consignee` varchar(63) DEFAULT NULL COMMENT '收货人名称',
  `mobile` varchar(63) DEFAULT NULL COMMENT '收货人手机号',
  `address` varchar(127) DEFAULT NULL COMMENT '收货具体地址',
  `message` varchar(512) DEFAULT '' COMMENT '用户订单留言',
  `goods_price` decimal(12,4) DEFAULT NULL COMMENT '商品总金额',
  `freight_price` decimal(12,4) DEFAULT NULL COMMENT '配送金额',
  `coupon_price` decimal(12,4) DEFAULT NULL COMMENT '优惠券减免金额',
  `integral_price` decimal(12,4) DEFAULT NULL COMMENT '用户积分减免金额',
  `groupon_price` decimal(12,4) DEFAULT NULL COMMENT '团购优惠价减免金额',
  `order_price` decimal(12,4) DEFAULT NULL COMMENT '订单金额',
  `actual_price` decimal(12,4) DEFAULT NULL COMMENT '实付金额',
  `pay_id` varchar(63) DEFAULT NULL COMMENT '微信付款编号',
  `pay_time` datetime DEFAULT NULL COMMENT '微信付款时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '软删除',
  `org_code` varchar(63) DEFAULT NULL COMMENT '组织机构编码',
  `tenant` varchar(32) DEFAULT NULL COMMENT '租户标识',
  `app_id` varchar(32) DEFAULT NULL COMMENT '应用标识 - 一般是创建者应用标识',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建人标识',
  `create_user_name` varchar(255) DEFAULT NULL COMMENT '创建人名称',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '最后更新时间',
  `update_user` varchar(32) DEFAULT NULL COMMENT '最后更新人标识',
  `update_user_name` varchar(255) DEFAULT NULL COMMENT '最后更新人名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1574 DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- 订单明细表
CREATE TABLE `mall_order_item` (
   `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID编号',
   `order_id` bigint NOT NULL DEFAULT '0' COMMENT '订单表的订单ID',
   `brand_id` bigint DEFAULT NULL COMMENT '店铺编码',
   `goods_id` bigint NOT NULL DEFAULT '0' COMMENT '商品表的商品ID',
   `goods_name` varchar(127) DEFAULT '' COMMENT '商品名称',
   `goods_sn` varchar(63) DEFAULT '' COMMENT '商品编号',
   `product_id` int DEFAULT '0' COMMENT '货品ID',
   `number` smallint DEFAULT '0' COMMENT '购买数量',
   `price` decimal(12,4) DEFAULT '0.0000' COMMENT '售价',
   `specifications` text COMMENT '规格',
   `pic_url` varchar(255) DEFAULT '' COMMENT '图片地址',
   `comment` bigint DEFAULT '0' COMMENT '评价id',
   `deleted` tinyint(1) DEFAULT '0' COMMENT '软删除',
   `refund_id` bigint DEFAULT NULL COMMENT '退款跟踪ID',
   `org_code` varchar(63) DEFAULT NULL COMMENT '组织机构编码',
   `tenant` varchar(32) DEFAULT NULL COMMENT '租户标识',
   `app_id` varchar(32) DEFAULT NULL COMMENT '应用标识 - 一般是创建者应用标识',
   `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
   `create_user` varchar(32) DEFAULT NULL COMMENT '创建人标识',
   `create_user_name` varchar(255) DEFAULT NULL COMMENT '创建人名称',
   `update_time` timestamp NULL DEFAULT NULL COMMENT '最后更新时间',
   `update_user` varchar(32) DEFAULT NULL COMMENT '最后更新人标识',
   `update_user_name` varchar(255) DEFAULT NULL COMMENT '最后更新人名称',
   PRIMARY KEY (`id`) USING BTREE,
   KEY `idx_01_order_item` (`order_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='订单明细表';
```
###### (2) maven仓库地址配置：settings.xml
仓库认证
```xml
  <servers>
    <server>
        <id>lockgate-releases</id>
        <username>63704d6128b61c88e75b4600</username>
        <password>7669WMzm=HrM</password>
    </server>
  </servers>
```
仓库profile
```xml
  <profiles>
    <profile>
      <id>lockgate</id>
      <repositories>
        <repository>
          <id>lockgate-releases</id>
          <name>lockgate dependences</name>
          <url>https://packages.aliyun.com/maven/repository/2139326-release-jtDdoi/</url>
        </repository>
      </repositories>
    </profile>
  </profiles>
```
激活profile
```xml
  <activeProfiles>
    <activeProfile>lockgate</activeProfile>
  </activeProfiles>
```

完整范例
```xml
<?xml version="1.0" encoding="UTF-8"?>

<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd">
  
  <localRepository>D:\repository\lockgate</localRepository>
  
  <servers>
    <server>
      <id>lockgate-releases</id>
      <username>63704d6128b61c88e75b4600</username>
      <password>7669WMzm=HrM</password>
    </server>
  </servers>

  <mirrors>
    <mirror>
      <id>AliRepo-aliyun</id>
      <mirrorOf>*,!lockgate-releases</mirrorOf>
      <name>Mirror Name for the Alirepo.</name>
      <url>https://maven.aliyun.com/repository/public</url>
    </mirror>
  </mirrors>

  <profiles>
    <profile>
      <id>lockgate</id>
      <repositories>
        <repository>
          <id>lockgate-releases</id>
          <name>lockgate dependences</name>
          <url>https://packages.aliyun.com/maven/repository/2139326-release-jtDdoi/</url>
        </repository>
      </repositories>
    </profile>
  </profiles>

  <activeProfiles>
    <!-- 可以激活多个 -->
    <activeProfile>lockgate</activeProfile>
  </activeProfiles>
</settings>
```

###### (3) 创建springboot工程，添加如下依赖
_当前仅支持mysql数据库，所以依赖mysql支持包即可_
```xml
<dependency>
    <groupId>cn.lockgate</groupId>
    <artifactId>lockgate-autoapi-support-mysql</artifactId>
    <version>2.0.0</version>
</dependency>
```
###### (4) 配置application.yml或者bootstrap.yml
```yaml
# 基础配置，略
# 数据源配置，略
# autoapi接口配置，如下
autoapi:
  config:
    # resource -> 配置文件, db -> 数据库
    configReaderType: "resource"
    # api的包路径
    # autoPackage: "com.nolink.demo.codeless.auto.api"
    # 是否强制为post
    forcePosting: false
    # 是否开启自动接口，default true
    autoApiEnable: true
    # 开启sql日志打印级别，默认为info
    sqlLogLevel: info
    # 自定义基础类
    # baseDTOClass: cc.nolink.framework.dto.BaseResponseDTO
    # 自定义查询基础类
    # baseQueryDTOClass: cc.nolink.framework.dto.BaseRequestDTO
    # 批处理批次大小
    jdbc:
      batchSize: 500
      maxPageSize: 1000
  api:
    apis:
      - domain: "retails" # 领域，构成接口path的组成部分，规则：/domain/service/entity/action
        services:
          - service: "mall" # 服务，构成接口path的组成部分
            entities:
              - entity: "order" # 实体，构成接口path的组成部分
                table: "mall_order" # 实体对应的数据库表名
                tags: 
                  tagName: "订单头表API" # 接口归类名称，默认为：entity + "API"
                  tagDesc: "订单头表：mall_order增删改查接口" # 接口归类描述，默认为：table + "API"
                actions: # 支持的action，all为全部，为空则表示一个也不支持，可以自定义选择需要的接口。支持的接口全集如下
                  - all
                  #- add # 新增一条记录
                  #- adds  # 批量新增
                  #- updateById  # 依据ID更新
                  #- updateByIds # 依据ID列表批量更新
                  #- updateByQueries # 依据自定义条件批量更新
                  #- save  # 单个：id == null?新增:修改
                  #- saves # 批量：id == null?新增:修改
                  #- deleteById  # 依据ID删除
                  #- deleteByIds # 依据ID列表批量删除
                  #- deleteByQueries # 依据自定义条件批量删除
                  #- findById  # 依据ID查询
                  #- queryByIds  # 依据ID列表查询列表，不带分页
                  #- query # 依据自定义条件查询列表，带分页
                  #- addWithItems  # 主子表关联新增
                  #- deleteWithItemsById # 依据主表ID级联删除主子表
                  #- findWithItemsById # 依据主表ID级联查询主子表
                orders: # map格式的排序规则配置，多个字段按定义的顺序进行排序
                  - order_no: desc # 数据库字段名：排序规则（asc/desc）
                  - create_time: asc
                items: # 主子表关联配置，可以配置多个，如果没有，则不配置该items项
                  - tableName: "mall_order_item" # 子表名称
                    foreignKey: "order_id" # 子表关联字段名称
                    parentKey: "id" # 对应主表字段名称，默认是id
              - entity: "order/item" # 实体，为了path显示更合理，entity支持定义成order/item这样的格式
                table: "mall_order_item"
                actions: # 自定义选择的action，最终接口仅仅展示选择的内容
                  - add
                  - adds
                  - updateById
                  - save
                  - saves
                  - deleteById
          - service: "customer"
            entities:
              - entity: "customer"
                table: "eg_customer"
                actions:
                  - all
      - domain: "marketing"
```
###### (5) 启动工程，打开swagger-ui接口列表，即可提交前端进行调试
例如范例工程打开：http://127.0.0.1:8080/swagger-ui.html
![swagger效果示例](https://user-images.githubusercontent.com/117927019/202881197-f400598a-4f92-49af-ba3e-c082c2ffa404.png)
-- ------
**更多开发说明请参考产品说明文档
https://lockgate.yuque.com/staff-zw2e7d/gzcxcl/glyg7l?# 《AutoApi（自动化接口）》**
-- ------
### 版权说明
- 这是一个范例工程，产品本身并不提供源码，但是提供完整功能 
- 产品规划目标是全行业的低代码平台，包含前后端的全功能配置，当前仅仅是后端自动接口部分的功能，请持续关注这里的变化
- 如果需要源码进行定制二开，可以联系我们
### 联系我们
- 如果使用过程中遇到问题或者建议，请通过issue提交，我们会即时处理和回复
- 需要源码或者其他商务合作，请通过邮件联系我们，邮箱地址：lockgate@163.com
