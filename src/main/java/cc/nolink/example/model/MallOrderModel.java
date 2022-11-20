package cc.nolink.example.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @auth reset
 * @description
 * @since 2022/10/31 22:37
 */
@Data
@TableName("mall_order")
public class MallOrderModel extends BaseModel {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @Schema(example = "[Integer][10][.0][notnull=Y][用户表的用户ID]")
    private Integer userId;
    
    @Schema(example = "[String][63][notnull=Y][订单编号]")
    private String orderNo;

    @Schema(example = "[Integer][5][.0][notnull=Y][订单状态]")
    private Integer orderStatus;

    @Schema(example = "[String][63][notnull=Y][收货人名称]")
    private String consignee;

    @Schema(example = "[String][63][notnull=Y][收货人手机号]")
    private String mobile;

    @Schema(example = "[String][127][notnull=Y][收货具体地址]")
    private String address;

    @Schema(example = "[String][512][notnull=Y][用户订单留言]")
    private String message;

    private BigDecimal goodsPrice;

    private BigDecimal freightPrice;

    private BigDecimal couponPrice;

    private BigDecimal integralPrice;

    private BigDecimal grouponPrice;

    private BigDecimal orderPrice;

    private BigDecimal actualPrice;

    @Schema(example = "[String][63][notnull=N][微信付款编号]")
    private String payId;

    @Schema(example = "[LocalDateTime][notnull=N][微信付款时间]")
    private Timestamp payTime;

    @Schema(example = "[Integer][3][.0][notnull=N][逻辑删除]")
    private Integer deleted;

}
