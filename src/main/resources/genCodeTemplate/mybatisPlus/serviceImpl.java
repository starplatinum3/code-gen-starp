package #包名#.serviceimpl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import #包名#.entity.#类名#;
import #包名#.domain.#类名#;
//import #包名#.mapper.#类名#Mapper;
import #包名#.repository.#类名#Mapper;
//import com.starp.exam.repository.ComponentMapper;
import #包名#.service.#类名#Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class #类名#ServiceImpl extends ServiceImpl<#类名#Mapper, #类名#> implements #类名#Service {

//    @Autowired
    @Resource
    private #类名#Mapper #实体名#Mapper;
@Override
public List<#类名#> batchAdd(MultipartFile file) {
        List<#类名#> #实体名#s = new ArrayList<>();
        try {
        InputStream inputStream = file.getInputStream();
        List<List<Object>> list = ExcelUtils.getStudentListByExcel(inputStream,
        file.getOriginalFilename());
        log.info("list {}",list);
        inputStream.close();

        for (int i = 0; i < list.size(); i++) {
        List<Object> #实体名#Parts = list.get(i);

        #类名# #实体名# = new #类名#();


        int partIdx=0;
        {batchAddPartsSetRows}

        #实体名#Mapper.save(#实体名#);

        #实体名#s.add(#实体名#);
        }
        } catch (Exception e){
        e.printStackTrace();
//return ReturnT.error(e.getMessage());
        }
        return #实体名#s;

        }
}
