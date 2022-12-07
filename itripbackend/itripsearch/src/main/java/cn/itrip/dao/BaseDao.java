package cn.itrip.dao;

import cn.itrip.pojo.ItripHotelVO;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;

import java.util.List;

/**
 * @author YiShi
 * @date 2022/11/22 15:29
 */
public class BaseDao {
    private static String url="http://localhost:8080/solr/HoteCore";

    HttpSolrClient httpSolrClient;


    //分页获取酒店

    public Page<ItripHotelVO> getlist1(SolrQuery query,int index,int psize) throws Exception{
        query.setStart((index-1)*6);
        query.setRows(psize);
        QueryResponse queryResponse =null;
        queryResponse = httpSolrClient.query(query);
        List<ItripHotelVO> list = queryResponse.getBeans(ItripHotelVO.class);
        //public Page(int curpage,Integer pagesize,Integer total)
        SolrDocumentList solrDocuments = ((QueryResponse) queryResponse).getResults();
        //当前页 每页多少个 总条数
        Page page=new Page(index,psize,new Long(solrDocuments.getNumFound()).intValue());
        page.setRows(list);
        return page;
    }




    public BaseDao(){
        httpSolrClient = new HttpSolrClient(url);
        httpSolrClient.setParser(new XMLResponseParser());
        httpSolrClient.setConnectionTimeout(500);
    }

    //获取城市下的热门酒店

    public List<ItripHotelVO> getlist(SolrQuery query) throws Exception{

        QueryResponse queryResponse = null;
        queryResponse = httpSolrClient.query(query);
        List<ItripHotelVO> list = queryResponse.getBeans(ItripHotelVO.class);
        return list;

    }
}
