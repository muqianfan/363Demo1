package cn.itrip;

import cn.itrip.dao.itripAreaDic.ItripAreaDicMapper;
import cn.itrip.dao.itripLabelDic.ItripLabelDicMapper;
import cn.itrip.pojo.ItripAreaDic;
import cn.itrip.util.Dto;
import cn.itrip.util.DtoUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author YiShi
 * @date 2022/11/15 16:31
 */

@Controller
public class bizController {

    @Resource
    ItripAreaDicMapper dao;

    @Resource
    ItripLabelDicMapper dao1;

    @Resource
    ItripAreaDicMapper dao2;

    @RequestMapping("/api/hotel/querytradearea/{id}")
    @ResponseBody
    public Dto GetObj1(@PathVariable("id") int id) {

        return DtoUtil.returnDataSuccess(dao2.getarea(id));
    }

    @RequestMapping("/api/hotel/queryhotelfeature")
    @ResponseBody
    public Dto GetObj1() {

        //获取首页特色酒店

        return DtoUtil.returnDataSuccess(dao1.firsttop());
    }



    @RequestMapping("/api/hotel/queryhotcity/{id}")
    @ResponseBody
    public Dto GetObj(@PathVariable("id")int id) {

        //去数据库查询热门城市
        List<ItripAreaDic> list=dao.ishot(id);
        return DtoUtil.returnDataSuccess(list);
    }

}
