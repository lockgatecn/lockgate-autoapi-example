package cc.nolink.example.custom.service;

import cc.nolink.example.model.MallOrderModel;
import cc.nolink.example.service.MallOrderService;
import cn.lockgate.autoapi.core.annotation.AssignedTableName;
import cn.lockgate.autoapi.core.utils.JacksonUtil;
import cn.lockgate.autoapi.runtime.AbstractApiAppointedOperation;
import cn.lockgate.autoapi.runtime.ApiAutoOperationFactory;
import cn.lockgate.framework.dto.PageRequestDTO;
import cn.lockgate.framework.dto.PageResponseDTO;
import cn.lockgate.framework.utils.copier.PojoCopier;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @auth reset
 * @description
 * @since 2022/10/31 22:58
 */
 @Service
 @AssignedTableName("mall_order") // 必须：需要指定哪张表对应的service
@Slf4j
public class CustomMallOrderService extends AbstractApiAppointedOperation {

    private MallOrderService mallOrderService;

    @Autowired
    public CustomMallOrderService(MallOrderService mallOrderService) {
        this.mallOrderService = mallOrderService;
    }

    /**
     * 场景1：通过自定义的方式实现接口后面的业务逻辑，比如这里选择mybatis-plus接管后续的处理
     *
     * @param tableName
     * @param arg
     * @return
     */
    @SneakyThrows
    @Override
    public Object add(String tableName, Object arg) {
        if (Objects.nonNull(arg)) {
            MallOrderModel order = PojoCopier.get().copy(arg, MallOrderModel.class);
            boolean r = mallOrderService.save(order);

            if (r) {
                return order;
            }
        }

        return null;
    }

    /**
     * 场景2：仅仅是想插入业务处理逻辑，数据保存继续使用框架提供的能力
     *  注意：这里ApiAutoService的获取必须通过如下方式，以ApiAutoOperationFactory的方式获取，不能通过spring bean注入的方式，因为ApiAutoService的初始化一定在自定义类之后
     *
     * @param tableName
     * @param arg
     * @return
     */
    @Override
    public int updateById(String tableName, Object arg) {
        // 业务逻辑处理 1 ...
        int r = ApiAutoOperationFactory.get().getApiAutoService().updateById(tableName, arg);
        // 业务逻辑处理 2 ...
        return r;
    }

    /**
     * 自定义实现对应的方法
     * @param tableName
     * @param id
     * @return
     */
    @Override
    public Object findById(String tableName, long id) {
        log.info("CustomMallOrderService.findById params: tableName={}, id={}", tableName, id);
        MallOrderModel model = mallOrderService.getById(id);
        log.info("CustomMallOrderService.findById result: {}", JacksonUtil.format(JacksonUtil.toJsonString(model)));
        return model;
    }

    /**
     * 依据id列表查询对象列表
     *
     * @param tableName
     * @param ids
     * @return
     */
    @Override
    public List<?> queryByIds(String tableName, List<Long> ids) {
        log.info("CustomMallOrderService.queryByIds params: tableName={}, ids={}", tableName, ids);
        List<MallOrderModel> list = mallOrderService.listByIds(ids);
        log.info("CustomMallOrderService.findById result: {}", JacksonUtil.format(JacksonUtil.toJsonString(list)));

        return list;
    }

    /**
     * 分页条件查询
     *
     * @param tableName
     * @param page
     * @return
     */
    @Override
    public PageResponseDTO query(String tableName, PageRequestDTO page) {
        Map<String, Object> queries = JacksonUtil.toMap(page.getQueries());

        // 测试的范例简写，实际开发请依据需要执行，比如声明对应的查询参数对象，然后通过对象映射转换的方式，而不是这种获取map属性的方式
        BigDecimal goodsPriceStart = BigDecimal.valueOf(Long.valueOf(queries.get("goodsPriceStart").toString()));
        BigDecimal goodsPriceEnd = BigDecimal.valueOf(Long.valueOf(queries.get("goodsPriceEnd").toString()));

        IPage<MallOrderModel> result = mallOrderService.page(page.getSize(), page.getCurrent(), goodsPriceStart, goodsPriceEnd);

        PageResponseDTO<MallOrderModel> r = new PageResponseDTO();
        r.setCurrent(page.getCurrent() + 1);
        r.setSize(page.getSize());
        r.setOptimizeCountSql(false);
        r.setTotal(page.getCurrent() * page.getSize() + result.getSize());
        r.setRecords(result.getRecords());

        return r;
    }

}
