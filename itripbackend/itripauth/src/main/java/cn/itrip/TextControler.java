package cn.itrip;


import cn.itrip.dao.itripHotel.ItripHotelMapper;
import cn.itrip.dao.itripUser.ItripUserMapper;
import cn.itrip.pojo.ItripHotel;
import cn.itrip.pojo.ItripUser;
import cn.itrip.pojo.ItripUserVO;
import cn.itrip.util.*;
import com.alibaba.fastjson.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Random;

/**
 * @author
 * @date 2022/11/7 15:10
 */
@Controller
public class TextControler {
    @Resource
    ItripHotelMapper dao;
    @Resource
    ItripUserMapper dao1;
    @Resource
    RedisHelp redisHelp;
    @Resource
    TokenBiz biz;

    @RequestMapping("/api/validatephone")
    @ResponseBody
    public Dto validatephone(String user,String code){
        //验证redis 中的数据是否正确
        String rCode = redisHelp.getkey(user);
        if (rCode.equals(code)){
            //验证成功,修改数据库的状态为激活
            dao1.jh(user);
            return DtoUtil.returnSuccess("激活成功");
        }

        return DtoUtil.returnFail("激活失败","1000");
    }

    //手机注册

    @RequestMapping("/api/registerbyphone")
    @ResponseBody
    public Dto Getlist(@RequestBody ItripUserVO vo, HttpServletRequest request)  throws Exception{

        //1.插入到数据库中
        ItripUser user=new ItripUser();
        user.setUserCode(vo.getUserCode());
        user.setUserName(vo.getUserName());
        user.setUserPassword(vo.getUserPassword());
        dao1.insertItripUser(user);
        //2.把手机号及验证码,存入redis中
        Random random = new Random();
        String code = "" + random.nextInt(9999);
        redisHelp.setKet(vo.getUserCode(),code,7200);
        //3.发送短信
        Sms_Sent.SentSms(user.getUserCode(),code);

        return DtoUtil.returnSuccess("注册成功");
    }






    @RequestMapping("/api/dologin")
    @ResponseBody
    public Dto Getlist(String name, String password, HttpServletRequest request) throws Exception {

        String Agent=request.getHeader("User-Agent");
        ItripUser pojo=dao1.login(name,password);
//生成一个加密后的token
        String token=biz.generateToken(Agent,pojo);
        //redis 存储数据
        redisHelp.setKet(token,JSONArray.toJSONString(pojo),50);
        ItripTokenVO vo=new ItripTokenVO(
                token,
                Calendar.getInstance().getTimeInMillis()+7200,
                Calendar.getInstance().getTimeInMillis()
        );
        return DtoUtil.returnDataSuccess(vo);
    }


    @RequestMapping("/list")
    @ResponseBody
    public Object Getlist() throws Exception {

        ItripHotel pojo=dao.getItripHotelById(new Long(1));
        return  pojo;
    }

}