package cc.nolink.example.service;

import cc.nolink.example.mapper.MallOrderMapper;
import cc.nolink.example.model.MallOrderModel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @auth lockgate
 * @description
 * @since 2022/10/31 22:44
 */
@Service
public class MallOrderService extends ServiceImpl<MallOrderMapper, MallOrderModel> {

    /**
     * 分页查询
     * @param pageSize
     * @param pageNumber
     * @param goodsPriceStart
     * @param goodsPriceEnd
     * @return
     */
    public IPage<MallOrderModel> page(Long pageSize, Long pageNumber, BigDecimal goodsPriceStart, BigDecimal goodsPriceEnd) {
        LambdaQueryWrapper<MallOrderModel> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.ge(Objects.nonNull(goodsPriceStart), MallOrderModel::getGoodsPrice, goodsPriceStart)
                .le(Objects.nonNull(goodsPriceEnd), MallOrderModel::getGoodsPrice, goodsPriceEnd);

        Page<MallOrderModel> page = new Page<>(pageNumber, pageSize);
        IPage<MallOrderModel> result = page(page, queryWrapper);

        return result;
    }

}
