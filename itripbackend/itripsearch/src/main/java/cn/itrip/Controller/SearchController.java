package cn.itrip.Controller;

import cn.itrip.dao.BaseDao;
import cn.itrip.dao.Page;
import cn.itrip.pojo.ItripHotelVO;
import cn.itrip.pojo.SearchHotCityVO;
import cn.itrip.pojo.SearchHotelVO;
import cn.itrip.util.Dto;
import cn.itrip.util.DtoUtil;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author
 * @date 2022/11/22 15:21
 */
@Controller
public class SearchController {
    @RequestMapping("/api/hotellist/searchItripHotelPage")
    @ResponseBody
    public Dto get2(@RequestBody SearchHotelVO vo) throws Exception {
        SolrQuery solrQuery=new SolrQuery("*:*");

        if(vo.getFeatureIds()!=null&&vo.getFeatureIds()!=""){
            String[] str=vo.getFeatureIds().split(",");
            String pjstr="";
            for (int i = 0; i <str.length ; i++) {
                if(i==0){
                    pjstr+="featureIds:*,"+str[i]+",*";
                }else {
                    pjstr+=" or featureIds:*,"+str[i]+",*";
                }
            }
            solrQuery.addFilterQuery(pjstr);
        }

        if(vo.getDestination()!=null&&vo.getKeywords()!=""){
            solrQuery.addFilterQuery("destination:"+vo.getDestination());
        }
        if(vo.getKeywords()!=null&&vo.getKeywords()!=""){
            solrQuery.addFilterQuery("keyword:"+vo.getKeywords());
        }
        if(vo.getHotelLevel()!=null){
            solrQuery.addFilterQuery("hotelLevel:"+vo.getHotelLevel());
        }
        if(vo.getPageNo()==null){
            vo.setPageNo(1);
        }
        if(vo.getPageSize()==null){
            vo.setPageSize(6);
        }
        BaseDao dao=new BaseDao();
        Page<ItripHotelVO> obj=dao.getlist1(solrQuery,vo.getPageNo(),vo.getPageSize());
        return DtoUtil.returnDataSuccess(obj);
    }




    @RequestMapping("/api/hotellist/searchItripHotelListByHotCity")
    @ResponseBody
    public Dto get1(@RequestBody SearchHotCityVO vo) throws Exception{
        BaseDao dao=new BaseDao();
        SolrQuery query = new SolrQuery("*:*");
        query.addFilterQuery("cityId:"+vo.getCityId());

        List<ItripHotelVO> obj=dao.getlist(query);
        return DtoUtil.returnDataSuccess(obj);
    }
}