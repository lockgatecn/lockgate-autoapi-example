package cc.nolink.example.free;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.lockgate.autoapi.runtime.reflect.InvokeHelper;
import cn.lockgate.framework.dto.UpdateByQueriesDTO;

/**
 * @auth lockgate
 * @description
 * @since 2022/9/26 10:23
 */
public class FreeMan {

    public void a() {
        UpdateByQueriesDTO<String, String> dto = new UpdateByQueriesDTO<>();
        dto.setQueries("q");
        dto.setUpdates("u");
        System.out.println(JSONUtil.toJsonStr(dto));

        int count = 10000000;
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            InvokeHelper.get().setFieldValue("eg_test", dto, "queries", String.valueOf(i));
        }
        System.out.println("times.loop:" + count + ", spend: " + (System.currentTimeMillis() - start));

        System.out.println(JSONUtil.toJsonStr(dto));
    }

//    public void b() {
//        ApiAutoController controller = new ApiAutoController();
//        System.out.println(controller.getClass().getAnnotation(AppointTableName.class).name());
//    }

    public void c() {
        String fieldName = "freeMan";
        System.out.println(StrUtil.toCamelCase(fieldName));
    }

    public static void main(String[] args) {
        FreeMan man = new FreeMan();
        man.c();
    }

}
