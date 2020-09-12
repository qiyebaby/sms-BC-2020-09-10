import com.SMS.dao.GlobalInfoMapper;
import com.SMS.dao.UserMapper;
import com.SMS.domain.GlobalInfo;
import com.SMS.domain.User;
import com.SMS.domain.UserExample;
import com.SMS.utils.AcademicBuild;
import com.SMS.utils.Const;
import com.SMS.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Description: TODO //
 *
 * @Date:2020/8/26 14:13
 * @Author:何磊
 */
public class Test01 {

    @Test
    public void test02(){
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserMapper userMapper = (UserMapper) ac.getBean("userMapper");
        User user = userMapper.selectByPrimaryKey(1l);
//        example.or().andUsernameEqualTo(user.getUsername());
//        example.or().andUserpwdEqualTo(user.getUserpwd());
        user.setUsername("admin");
        user.setUserpwd("123");
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        String sql = "ad";
        criteria.andUsernameLike("%"+sql+"%");
        List<User> users = userMapper.selectByExample(example);
        System.out.println(users);
        System.out.println(users.size());
    }

    @Test
    public void test01(){

        System.out.println(StringUtils.isNotBlank(null));
        UserExample example = new UserExample();
        User user = new User();
        user.setUsername("admin");
        user.setUserpwd("123");

        UserExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(user.getUsername())){
            criteria.andUsernameEqualTo(user.getUsername());
        }
        if (StringUtils.isNotBlank(user.getUserpwd())){
            criteria.andUserpwdEqualTo(user.getUserpwd());
        }
        if (StringUtils.isNotBlank(String.valueOf(user.getUsertype()))){
            criteria.andUsertypeEqualTo(user.getUsertype());
        }
        System.out.println(criteria.toString());
        System.out.println(criteria.getAllCriteria().toString());

        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserMapper userMapper = (UserMapper) ac.getBean("userMapper");
        List<User> users = userMapper.selectByExample(example);

        if (users == null){
            System.out.println("未查到");
        }else {
            System.out.println(users);
        }
/*
    select
        uid, username, userpwd, date, usertype
    from
        t_userlogin
    WHERE
        ( username = ? and userpwd = ? and usertype = ? )

    admin(String), 123(String), 0(Integer)

 */

    }

    @Test
    public void test04(){
        ResourceBundle bundle = ResourceBundle.getBundle("globalInfo");
        String falg = bundle.getString("global_infoStatistics");
        System.out.println(falg);
    }
    @Test
    public void  test05() throws IOException{
        FileOutputStream fos = new FileOutputStream("globalInfo.properties",true);
        fos.write("我是中国人".getBytes());
        fos.flush();
        fos.close();
    }

    @Test
    public void test06(){
        String path =   Thread.currentThread().
                        getContextClassLoader().
                        getResource("../../../globalInfo.properties").
                        getPath();
        System.out.println(path);
    }


    @Test
    public void test07(){
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        GlobalInfoMapper infoMapper = (GlobalInfoMapper) ac.getBean("globalInfoMapper");
        GlobalInfo globalInfo = infoMapper.selectByPrimaryKey("global_infoStatistics");
        System.out.println(globalInfo.getValue());
    }

    @Test
    public void test08(){

        int [][][] arr1 = new int[8][5][8];
        for (int i = 0; i < arr1.length; i++) {
            for (int j = 0; j < arr1[i].length; j++) {
                for (int z = 0; z < arr1[j].length; z++) {
                    System.out.print((i+1)+"#"+(j+1)+"0"+(z+1)+" ");
                }
                System.out.println();
            }
            System.out.println("============");
        }

    }
    @Test
    public void test10(){
        String str = "8#-101";
        char[] chars = str.toCharArray();
        System.out.println(chars[0]);
        System.out.println(chars[3]);
        System.out.println(chars[5]);


    }

    @Test
    public void test13(){


    }

    @Test
    public void test14(){

        String uid = "1";
        System.out.println(uid.contains(","));
        String[] uidList = uid.split(",");
        for (String s : uidList) {
            System.out.println(s);
        }
    }

    @Test
    public void test15(){
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        String uid = "1,2,3,4,5";
        String[] uidList = uid.split(",");
        for (String str : uidList) {
            example.or().andUidEqualTo(Long.valueOf(str));
        }


    }

    @Test
    public void test16(){
        String time = DateUtil.getTime(Const.TIME_FORMAT_TWO);
        System.out.println(time);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:dd:mm");

        System.out.println(sdf.format(new Date()));
    }

    @Test
    public void test17(){

    }
}
