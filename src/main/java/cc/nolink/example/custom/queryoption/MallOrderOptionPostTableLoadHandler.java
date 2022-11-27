package cc.nolink.example.custom.queryoption;

import cn.lockgate.autoapi.core.annotation.AssignedTableName;
import cn.lockgate.autoapi.core.constant.IAutoApiConstant;
import cn.lockgate.autoapi.runtime.jdbc.sql.consts.SqlOptEnum;
import cn.lockgate.autoapi.setup.loader.builder.metadata.handler.IPostTableLoadHandler;
import cn.lockgate.autoapi.setup.tableinfo.ColumnExtendUtil;
import cn.lockgate.autoapi.setup.tableinfo.entity.ColumnInfo;
import cn.lockgate.autoapi.setup.tableinfo.entity.TableInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @auth lockgate
 * @description
 * @since 2022/10/18 22:37
 */
@Component
@AssignedTableName("mall_order")
@Slf4j
public class MallOrderOptionPostTableLoadHandler implements IPostTableLoadHandler {

    /**
     * 元数据加载完成，可以在这里按需求变更元数据，比如：范围查询、模糊查询定义等
     *
     * @param tableInfo
     */
    @Override
    public void doPostHandle(TableInfo tableInfo) {
        if (Objects.nonNull(tableInfo) && !CollectionUtils.isEmpty(tableInfo.getColumns())) {
            List<ColumnInfo> columns = tableInfo.getColumns();

            // 如果数据库字段类型为datetime，则mysql驱动会将其映射成LocalDateTime类型，此时，配置文件中配置的日期格式化规则将会不生效
            // 解决办法是将其转映射成java的Timestamp类型
            // 同理，如果其他字段类型有问题，也可以在表信息加载完成后的监听中进行转换（即：此处）
            for (ColumnInfo column : columns) {
                if (column.getJavaType().equals(LocalDateTime.class)) {
                    column.setJavaType(Timestamp.class);
                    column.setTypeName(Timestamp.class.getName());
                }
            }

            // 转map
            Map<String, ColumnInfo> columnInfoMap = columns
                    .stream()
                    .parallel()
                    .filter(col -> !col.isVirtual()) // 排除过滤虚拟字段：不能针对虚拟字段进行二次设置
                    .collect(Collectors.toConcurrentMap(ColumnInfo::getColumnName, Function.identity()));

            // 自定义like查询
            orderNo2Like(columnInfoMap.get("order_no"));
            // 自定义范围查询
            List<ColumnInfo> goodsPriceVirtualList = goodsPrice2Scope(columnInfoMap.get("goods_price"));
            columns.addAll(goodsPriceVirtualList);
        }
    }

    /**
     * orderNo订单编码模糊查询范例
     * @param orderNoColumn
     */
    private void orderNo2Like(ColumnInfo orderNoColumn) {
        if (Objects.nonNull(orderNoColumn)) {
            // 支持的自定义操作详见：SqlOptEnum
            orderNoColumn.setSqlOpt(SqlOptEnum.PRE_LIKE.getCode());
        }
    }

    /**
     * 商品价格范围查询范例
     * @param goodsPriceColumn
     */
    private List<ColumnInfo> goodsPrice2Scope(ColumnInfo goodsPriceColumn) {
        List<ColumnInfo> virtualList = new ArrayList<>();

        if (Objects.nonNull(goodsPriceColumn)) {
            // 范围查询配置比较特殊，需要增加两个虚拟查询字段，起始值和结束值，ColumnExtendUtil.cloneAndRewrite方法为系统提供，不需要用户实现
            virtualList.add(ColumnExtendUtil.cloneAndRewrite(goodsPriceColumn, IAutoApiConstant.VIRTUAL_START));
            virtualList.add(ColumnExtendUtil.cloneAndRewrite(goodsPriceColumn, IAutoApiConstant.VIRTUAL_END));
        }

        return virtualList;
    }

}
