package org.Mapper;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
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
import java.io.IOException;
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

    // 注入工具类
    @Autowired
    private FastFileStorageClient fastFileStorageClient;


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
            //String path = getHead(testfile);
            //user.setPath(path);


            // 调用上传方法，如果需要记录文件路径，group + path
            try {
                StorePath storePath = fastFileStorageClient.uploadFile(null,testfile.getInputStream(),
                        testfile.getSize(),getSuffix(testfile.getOriginalFilename()));

                user.setGroup(storePath.getGroup());
                user.setPath(storePath.getPath());

            } catch (IOException e) {
                e.printStackTrace();
            }
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
        String filepath = user.getPath();



        //如果路径存在就删除该路径的文件 &&false 过后不会计算下一个表达式
        if(filepath != null && !filepath.equals("")){
            try{
                fastFileStorageClient.deleteFile(user.getGroup(),filepath);
            }catch(Exception  e){
                e.printStackTrace();
            }

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

        //删除原头像文件
        if(filepath != null && !filepath.equals("")){
            try {
                fastFileStorageClient.deleteFile(user.getGroup(),filepath);

                StorePath storePath = fastFileStorageClient.uploadFile(null,testfile.getInputStream(),
                        testfile.getSize(),getSuffix(testfile.getOriginalFilename()));
                user.setGroup(storePath.getGroup());
                user.setPath(storePath.getPath());
            } catch (Exception e) {
                e.printStackTrace();
            }
            /*try {
                // 初始化配置文件
                ClientGlobal.init("D:\\IDEA_2023\\2023Projects\\linux\\FastDFS_client_springMvc\\Test_springmvc\\Webapps\\src\\main\\resources\\dfs.conf");
                // 创建跟踪器客户端对象
                TrackerClient tracker = new TrackerClient();
                // 获取跟踪器连接
                TrackerServer trackerServer = tracker.getTrackerServer();
                // 获取存储器客户端对象



                InputStream inputstream =  testfile.getInputStream();
                byte[] b= new byte[inputstream.available()];
                inputstream.read(b);
                StorageClient storageClient = new StorageClient(trackerServer, null);
                String strs[] = storageClient.upload_file(b, getSuffix(testfile.getOriginalFilename()), null);

                storageClient.delete_file(user.getGroup(),user.getPath());

                user.setGroup(strs[0]);
                user.setPath(strs[1]);

            }catch(Exception e)
            {
                e.printStackTrace();
            }*/
        }

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
    public void TestResponse1(String path,String group,HttpServletResponse response) throws IOException {

        if(path != null && !path.equals("")){//路径不能为空

            /**
             * 下载
             */
            byte[] body = fastFileStorageClient.downloadFile(group,path,new DownloadByteArray());
            response.getOutputStream().write(body,0, body.length);

        }

    }

    /**
     * 获取文件后缀
     * @param filename 上传的文件名
     * @return 返回自制文件名
     */
    public static String getSuffix(String filename){
        String[] files = filename.split("\\.");//根据.分割, '.' 是敏感字符，要带解译符
        return  files[files.length-1];
    }
}
