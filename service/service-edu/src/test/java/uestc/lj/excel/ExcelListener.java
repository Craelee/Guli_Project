package uestc.lj.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

public class ExcelListener extends AnalysisEventListener<DemoData> {
    /**
     * 逐行读取excel内容
     *
     * @param demoData
     * @param analysisContext
     */
    @Override
    public void invoke(DemoData demoData, AnalysisContext analysisContext) {
        System.out.println("*****" + demoData);
    }

    /**
     * 读取表头的方法
     * @param headMap
     * @param context
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头："+headMap);
    }

    /**
     * 读取完之后
     *
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
