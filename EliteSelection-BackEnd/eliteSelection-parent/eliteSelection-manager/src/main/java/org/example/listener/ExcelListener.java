package org.example.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.ListUtils;
import org.example.manager.mapper.CategoryMapper;
import org.example.model.vo.product.CategoryExcelVo;
import org.springframework.stereotype.Component;

import java.util.List;
// 监听器无法交付给Spring管理(spring管理的类是单例的 ),因为并发情况下无法实现读取;
public class ExcelListener<T> extends AnalysisEventListener<T> {
    /**
     每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;
    /**
     * 缓存的数据
     */
    private List<T> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    //获取mapper对象
    private CategoryMapper categoryMapper;

    public ExcelListener(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    // 每解析一行数据就会调用一次该方法
    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        cachedDataList.add(t);
        if (cachedDataList.size() == BATCH_COUNT) {
            saveData();
            cachedDataList.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // excel解析完毕以后需要执行的代码
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
    }

    private void saveData() {
        categoryMapper.batchInsert(cachedDataList);
    }
}
