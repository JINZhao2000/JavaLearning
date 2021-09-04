package cyou.zhaojin.reducejoin.reducer;

import cyou.zhaojin.reducejoin.bean.TableBean;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 04/09/2021
 * @ Version 1.0
 */

public class TableReducer extends Reducer<Text, TableBean, TableBean, NullWritable> {
    @Override
    protected void reduce(Text key, Iterable<TableBean> values, Reducer<Text, TableBean, TableBean, NullWritable>.Context context) throws IOException, InterruptedException {
        List<TableBean> orderBeans = new ArrayList<>();
        TableBean pdBean = new TableBean();
        for (TableBean value : values) {
            try {
                if ("order".equals(value.getFlag())) {
                    TableBean tmp = new TableBean();

                    BeanUtils.copyProperties(tmp, value);

                    orderBeans.add(tmp);
                } else {
                    BeanUtils.copyProperties(pdBean, value);
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        for (TableBean orderBean : orderBeans) {
            orderBean.setPName(pdBean.getPName());
            context.write(orderBean, NullWritable.get());
        }
    }
}
