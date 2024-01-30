package org.example.manager.service.imp;

import com.alibaba.excel.EasyExcel;
import com.github.xiaoymin.knife4j.core.util.CollectionUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.example.exception.CustomLoginException;
import org.example.listener.ExcelListener;
import org.example.manager.mapper.CategoryMapper;
import org.example.manager.service.CategoryService;
import org.example.model.entity.product.Category;
import org.example.model.vo.common.ResultCodeEnum;
import org.example.model.vo.product.CategoryExcelVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImp implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;



    @Override
    public List<Category> findByParentId(Long id) {
        // 1.根据分类id查询到具有的所有子分类数据
        List<Category> categoryList =  categoryMapper.selectByParentId(id);

        // 2.当存在子类数据时,为其添加children属性
        if (CollectionUtils.isNotEmpty(categoryList)) {
            for (Category category : categoryList) {
                List<Category> list = categoryMapper.selectByParentId(category.getId());
                if (list!= null && list.size() > 0) {
                    category.setHasChildren(true);
                }else{
                    category.setHasChildren(false);
                }
            }
        }
        return categoryList;
    }

    @Override
    public void importData(MultipartFile file) {
        try {
            //创建监听器对象，传递mapper对象
            ExcelListener<CategoryExcelVo> excelListener = new ExcelListener<>(categoryMapper);
            //调用read方法读取excel数据
            EasyExcel.read(file.getInputStream(),
                    CategoryExcelVo.class,
                    excelListener).sheet().doRead();
        } catch (IOException e) {
            throw new CustomLoginException(ResultCodeEnum.DATA_ERROR);
        }
    }

    @Override
    public void exportData(HttpServletResponse response) {
        try {
            // 1.下载excel表格
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8"); // 设置编码集防止乱码
            // 设置文件名称
            String fileName = URLEncoder.encode("分类数据", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

            // 2.获取数据
            List<Category> categoryList = categoryMapper.selectAll();
            // 将Category数据转换为CategoryExcelVo数据
            List<CategoryExcelVo> categoryExcelVoList = new ArrayList<>();
            for (Category category : categoryList) {
                CategoryExcelVo categoryExcelVo = new CategoryExcelVo();
                BeanUtils.copyProperties(category,categoryExcelVo, CategoryExcelVo.class);
                categoryExcelVoList.add(categoryExcelVo);
            }

            // 3.导出数据
            EasyExcel.write(response.getOutputStream(), CategoryExcelVo.class)
                    .sheet("分类数据")
                    .doWrite(categoryExcelVoList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
