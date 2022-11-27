package cc.nolink.example.mapper;

import cc.nolink.example.model.MallOrderModel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 厂商信息表 Mapper 接口
 * </p>
 *
 * @auth lockgate
 * @since 2022-03-30
 */
@Mapper
public interface MallOrderMapper extends BaseMapper<MallOrderModel> {

}
