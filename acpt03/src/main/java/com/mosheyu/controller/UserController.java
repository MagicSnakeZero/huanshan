package com.mosheyu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mosheyu.en.User;
import com.mosheyu.en.VO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/attest")
    public String out(){
        System.out.println("控制器执行了。");
        return "/test";
//        return "testSpringMVC";             //解析   /jsp/  +testSpringMVC + .jsp
    }

    @RequestMapping("/attest2")
    public ModelAndView out2(){
        /*
            Model 模型，用来封装数据。
            View  视图，用来展示数据。
         */
        ModelAndView modelAndView = new ModelAndView();
        //设置视图名
        modelAndView.setViewName("test");
        //设置模型数据
        modelAndView.addObject("name","out2");
        return modelAndView;
    }

    @RequestMapping("/attest3")
    public ModelAndView out3(ModelAndView modelAndView){
        modelAndView.setViewName("test");
        modelAndView.addObject("name","out3");
        return modelAndView;
    }
    @RequestMapping("/attest4")
    public String out4(Model model){
        model.addAttribute("name","out4");
        return "test";
    }
    @RequestMapping("/attest5")
    public String out5(HttpServletRequest request){
        request.setAttribute("name","out5");
        return "test";
    }
    @RequestMapping("/attest6")
    public void out6(HttpServletResponse response) throws IOException {
        response.getWriter().println("test能否使用");
    }
    @RequestMapping("/attest7")
    @ResponseBody
    public String out7(HttpServletResponse response) throws IOException {
        return  "test能否使用";
    }

    @RequestMapping("/attest8")
    @ResponseBody
    public String out8() throws IOException {
        return  "{\"name\":\"out8,\"age\":\"2\"}";
    }
    @RequestMapping("/attest9")
    @ResponseBody
    public String out9() throws IOException {
        User user = new User();
        user.setName("mosheyu");
        user.setAge(99);
        //使用json转换工具将对象转为json字符串返回。
        ObjectMapper objectMapper = new ObjectMapper();
        String json  = objectMapper.writeValueAsString(user);
        return  json;
    }

    //spring自动将User转换成json格式的字符串。
    @RequestMapping("/attest10")
    @ResponseBody
    public String out10() throws IOException {
        User user = new User();
        user.setName("mosheyu");
        user.setAge(10);
        //使用json转换工具将对象转为json字符串返回。
        ObjectMapper objectMapper = new ObjectMapper();
        String json  = objectMapper.writeValueAsString(user);
        return  json;
    }

    @RequestMapping("/attest11")
    @ResponseBody
    public User out11() throws IOException {
        User user = new User();
        user.setName("mosheyu");
        user.setAge(11);
        return  user;
    }
    @RequestMapping("/attest12")
    @ResponseBody
    public void out12(String name,int age) throws IOException {
        System.out.println(name);
        System.out.println(age);
    }
    @RequestMapping("/attest13")
    @ResponseBody
    public void out13(User user) throws IOException {
        System.out.println(user);
    }
    @RequestMapping("/attest14")
    @ResponseBody
    public void out14(String[] strs) throws IOException {
        System.out.println(Arrays.asList(strs));
    }
    @RequestMapping("/attest15")
    @ResponseBody
    public void out15(VO vo) throws IOException {
        System.out.println(vo.toString());
    }
    @RequestMapping("/attest16")
    @ResponseBody
    public void out16(@RequestBody List<User> userList) throws IOException {
        System.out.println(userList.toString());
    }
    @RequestMapping("/attest17")
    @ResponseBody
    public void out17(@RequestParam(value = "name") String username) throws IOException {
        System.out.println(username.toString());
    }
    @RequestMapping("/attest18/{name}")
    @ResponseBody
    public void out18(@PathVariable(value = "name",required = true) String name) throws IOException {
        System.out.println(name.toString());
    }

    @RequestMapping("/attest19")
    @ResponseBody
    public void out18(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        System.out.println(request);
        System.out.println(response);
        System.out.println(session);
    }
    @RequestMapping("/attest20")
    @ResponseBody
    public void out20(@RequestHeader(value = "User-Agent",required = false) String headerValue) throws IOException {
        System.out.println(headerValue);
    }
    @RequestMapping("/attest21")
    @ResponseBody
    public void out21(@CookieValue(value = "JSESSIONID",required = false) String jsessionid) throws IOException {
        System.out.println(jsessionid);
    }
    @RequestMapping("/attest22")
    @ResponseBody
    public void out22(String name, MultipartFile uploadFile) throws IOException {
        System.out.println(name);
        //获取文件的名称
        String fileName = uploadFile.getOriginalFilename();
        //存储文件
        uploadFile.transferTo(new File("D:\\www\\"+fileName));
    }
    @RequestMapping("/attest23")
    @ResponseBody
    public void out23(String name, MultipartFile uploadFile,MultipartFile uploadFile2,MultipartFile uploadFile3) throws IOException {
        System.out.println(name);
        //获取文件的名称
        String fileName = uploadFile.getOriginalFilename();
        //存储文件
        uploadFile.transferTo(new File("D:\\www\\"+fileName));

        String fileName2 = uploadFile2.getOriginalFilename();
        uploadFile.transferTo(new File("D:\\www\\"+fileName2));
        String fileName3 = uploadFile3.getOriginalFilename();
        uploadFile.transferTo(new File("D:\\www\\"+fileName3));
    }
}
