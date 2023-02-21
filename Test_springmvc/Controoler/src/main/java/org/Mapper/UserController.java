package org.Mapper;

import org.Entity.Addr;
import org.Entity.Page;
import org.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author xcyb
 * @date 2022/10/18 15:58
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;


    /**
     *
     * @param map
     * @param locale 国际化参数，存入session，这样每次进入都能保存选择的语言,如果没有Locale 参数会从session里获取
     * @return
     */
    @RequestMapping("/GetQuery")
    public String getAll(Map<String, Object> map, Locale locale,Integer pageNo,Integer pageSize){
        System.out.println("-----------------------");

        Page page = new Page();

        //pageNo 没有值就赋值
        if(pageNo == null || pageNo == 0){
            page.setPageNo(1);
        }else{
            page.setPageNo(pageNo);
        }

        if(pageSize == null || pageSize == 0){
            page.setPageSize(5);
        }else{
            page.setPageSize(pageSize);
        }

        userService.getAll(page);
        System.out.println(page.toString());

        map.put("locale",locale.toString());
        map.put("page",page);

        return "Query";
    }


    @RequestMapping("/GetAdd")
    public String Add(Map<String, Object> map) {

        Map<String, String> map1 = new HashMap<>();
        map1.put("1", "男");
        map1.put("0", "女");


        List<Addr> addr = userService.getAddr();

        map.put("map1", map1);
        map.put("addr", addr);

        map.put("user", new User());

        return "Add";
    }

    /** 新增保存
     * redirect: 重定向
     * @return BindingResult 可以捕获错误，通常发生错误 都不会进入这个方法,有了这个参数就不会把错误抛到页面上
     * @Valid 接受参数认证, 也就是参数校验 才能使用
     * User user 所有获取的途径这个实体类的 都是通过 get set 方法
     */
    @RequestMapping(path = "/SaveAdd.do", method = RequestMethod.POST)
    public String SaveAdd(@Valid User user, BindingResult bindingResult, Map<String, Object> map,
                          @RequestParam("testfile") MultipartFile testfile) {

        System.out.println("post请求");
        System.out.println(user.toString());

        if (bindingResult.getErrorCount() > 0) {
//            System.out.println("捕获的error 大于 0 ");
//            for (FieldError error : bindingResult.getFieldErrors()) {
//                System.out.println(error.getField()+":===:"+error.getDefaultMessage());
//            }
            Map<String, String> map1 = new HashMap<>();
            map1.put("1", "男");
            map1.put("0", "女");
            List<Addr> addr = userService.getAddr();
            map.put("map1", map1);
            map.put("addr", addr);

            return "Add";//再返回新增页面
        }

        if(testfile != null && !testfile.equals("")){
            String path = getHead(testfile);
            user.setPath(path);
        }

        userService.UserAdd(user);
        return "redirect:GetQuery";
    }



    /** 删除
     * @return 重定向
     * ResponseStatus 加在方法上会等方法执行完成后抛出异常，方法是正常执行,不管方法有无异常都会抛出异常
     */
    /*@ResponseStatus(value = HttpStatus.NOT_FOUND,reason = "不是404")*/
    @RequestMapping(value = "/Delete.do/{id}", method = RequestMethod.DELETE)
    public String Delete(@PathVariable Integer id) {
        System.out.println("Delete 请求");

        User user = userService.UserEdit_Query(id);
        String path = user.getPath();

        //如果路径存在就删除该路径的文件 &&false 过后不会计算下一个表达式
        if(path != null && !path.equals("")){
            File file = new File(path);
            file.delete();
        }
        userService.UserDelete(user);
        return "redirect:/GetQuery";
    }




    /**
     * 修改返显
     * @param id
     * @param map
     * @return
     */
    @RequestMapping(value = "/Edit/{id}")
    public String Update(@PathVariable int id, Map<String, Object> map) {
        User user = userService.UserEdit_Query(id);
        Map<String, String> map1 = new HashMap<>();
        map1.put("1", "男");
        map1.put("0", "女");


        List<Addr> addr = userService.getAddr();


        map.put("map1", map1);
        map.put("addr", addr);


        map.put("user", user);

        return "Add";
    }


    /**修改 数据
     * put 请求可能请求不过去，需要在页面 上加上 isErrorPage="true"
     * redirect: 重定向
     * @param user Spirng MVC 默认不支持 PUT请求带参数，解决方案也很简单，就是在web.xml中把原来的过滤器改一下
     * @return
     */
    @RequestMapping(value = "/SaveAdd.do", method = RequestMethod.PUT)
    public String Update1(@Valid User user, BindingResult bindingResult,Map<String, Object> map,
                          @RequestParam("testfile") MultipartFile testfile) {
        System.out.println("Put 请求");

        if (bindingResult.getErrorCount() > 0) {

            for (FieldError error : bindingResult.getFieldErrors()) {
                System.out.println(error.getField()+":===:"+error.getDefaultMessage());
            }

            Map<String, String> map1 = new HashMap<>();
            map1.put("1", "男");
            map1.put("0", "女");
            List<Addr> addr = userService.getAddr();
            map.put("map1", map1);
            map.put("addr", addr);


            return "Add";//request 请求中 可能还有user
        }
        String filepath = user.getPath();

        //删除原文件
        if(filepath != null && !filepath.equals("")){
            File file = new File(filepath);
            file.delete();
        }

        //创建新文件
        String path = getHead(testfile);
        user.setPath(path);


        userService.UserEdit(user);
        return "redirect:/GetQuery";
    }


    /**
     * 执行其他方法前, 所有的方法都会先执行这里,利用这个机制，进入修改的时候不能修改name，会把name隐藏掉，但是值为null，所以要查出来
     * 放入request，再把修改的值 覆盖到request，被接受
     * required 表示该参数是否必须,默认true
     */
    @ModelAttribute
    public void getUser(@RequestParam(value = "id", required = false) Integer id, Map<String, Object> map) {
        //普通的add id=0,修改提交的时候 把name查出来
        if (id != null) {
            User user = userService.UserEdit_Query(id);
            map.put("user", user);
        }
    }









    /**
     * 头像加载
     * @param path
     * @return
     * @throws IOException
     */
    @RequestMapping("/ResponseEntityHead")
    public void TestResponse1(String path, HttpServletResponse response) throws IOException {

        if(path != null && !path.equals("")){//路径不能为空
            InputStream in = new FileInputStream(path);

            byte[] body = new byte[1024*1024*5];
            int length = 0;

            while((length = in.read(body)) != -1){
                response.getOutputStream().write(body,0,length);
            }
            in.close();
        }

    }



















    /**
     * 文件名
     * @param testfile
     * @return
     */
    public static String getHead(MultipartFile testfile){
        String path ="/root/tomcat/io"+getName(testfile.getOriginalFilename());//得到文件名
        try {
            InputStream inputStream = testfile.getInputStream();
            OutputStream outputStream = new FileOutputStream(path);
            byte[] body = new byte[1024*1024*5];
            int length = 0;

            while((length = inputStream.read(body)) != -1){
                outputStream.write(body,0,length);
                outputStream.flush();
            }

            inputStream.close();
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

    /**
     * 获取时间戳 + 获取文件后缀  组成 文件名
     * @param filename 上传的文件名
     * @return 返回自制文件名
     */
    public static String getName(String filename){
        String[] files = filename.split("\\.");//根据.分割, '.' 是敏感字符，要带解译符
        return getTime()+"."+ files[files.length-1];
    }

    /**
     * @return synchronized 返回当前一个13位的时间戳，精准单位到毫秒，加上一个线程锁，每次只能进去一个线程
     */
    public static synchronized String getTime(){
        return String.valueOf(System.currentTimeMillis());
    }

}
