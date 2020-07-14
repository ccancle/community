package com.nowcoder.community.controller;

import com.nowcoder.community.service.AlphaService;
import com.nowcoder.community.util.CommunityUtil;
import org.hibernate.validator.constraints.EAN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * @author ：ccancle菜菜
 * @date ：Created in 2019/11/5 21:43
 * @description：TODO
 * @version: TODO
 */
@Controller
@RequestMapping("/alpha")
public class AlphaController {

    @Autowired
    private AlphaService alphaService;


    @RequestMapping("/hello")
    @ResponseBody
    public String sayHello(){
        return "Hello Spring Boot";
    }

    @RequestMapping("/data")
    @ResponseBody
    public String getData(){
        return alphaService.find();
    }

    @RequestMapping("/http")
    public void http(HttpServletRequest request, HttpServletResponse response){
//        获取请求数据
        System.out.println(request.getMethod());
        System.out.println(request.getServletPath());
        Enumeration<String> enumeration =request.getHeaderNames();
        while (enumeration.hasMoreElements()){
            String name = enumeration.nextElement();
            String value = request.getHeader(name);
            System.out.println(name+":"+value);
        }
        System.out.println(request.getParameter("code"));
        //返回响应数据Response
        response.setContentType("text/html;charset = utf-8");
        try(
                PrintWriter writer = response.getWriter();
        ){
            writer.write("<h1>牛客网</h1>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //GET请求 默认请求为Get

    //查询所有学生  /students?current=1&limit=20
    @RequestMapping(path = "/students",method = RequestMethod.GET)
    @ResponseBody
    public String getStudents(@RequestParam(name = "current",required = false,defaultValue = "1") int current,
                              @RequestParam(name = "limit",required = false,defaultValue = "10")int limit)
    {
        System.out.println(current);
        System.out.println(limit);
        return "some students";
    }

    //  /students/123 参数成为路径的一部分
    @RequestMapping(path = "/student/{id}",method = RequestMethod.GET)
    @ResponseBody
    public String getStudent(@PathVariable("id") int id){
        System.out.println(id);
        return "a student";
    }

    //POST 请求
    @RequestMapping(path = "/student" ,method = RequestMethod.POST)
    @ResponseBody
    public String saveStudent(String name, int age ){
        System.out.println(name);
        System.out.println(age);
        return "success";
    }

    //响应HTML数据
    @RequestMapping(path = "/teacher",method = RequestMethod.GET)
    public ModelAndView getTeacher(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("name","孙悟空");
        modelAndView.addObject("age",50);
        modelAndView.setViewName("/demo/view");
        //默认view.html
        return modelAndView;
    }

    @RequestMapping(path = "/school",method = RequestMethod.GET)
    public String getSchool(Model model){
        model.addAttribute("name","天津理工大学");
        model.addAttribute("age","40");
        return "/demo/view";
    }

    //响应JSON数据  (异步请求) 当前网页不刷新 直接访问数据库判断
    // Java 对象 解析对象使用JS --> JS对象
    //Java 对象 --> JSON 字符串 --> JS对象 跨语言

    @RequestMapping(path = "/emp",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getEmp(){
        Map<String,Object> emp = new HashMap<>();
        emp.put("name","蔡鹏渤");
        emp.put("age",23);
        emp.put("salary",8000.00);
        return emp;

    }

    @RequestMapping(path = "/emps",method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String,Object>> getEmps(){
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> emp = new HashMap<>();
        emp.put("name","蔡鹏渤");
        emp.put("age",20);
        emp.put("salary",8000.00);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name","毛毛");
        emp.put("age",20);
        emp.put("salary",9000.00);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name","抹茶");
        emp.put("age",2);
        emp.put("salary",1000.00);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name","飞");
        emp.put("age",28);
        emp.put("salary",10000.00);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name","酸奶");
        emp.put("age",3);
        emp.put("salary",5000.00);
        list.add(emp);
        return list;

    }

    @RequestMapping(path = "/cookie/set" ,method = RequestMethod.GET)
    @ResponseBody
    public String setCookie(HttpServletResponse response){
        //
        Cookie cookie = new Cookie("code", CommunityUtil.generateUUID());

        //设置cookie生效范围
        cookie.setPath("/community/alpha");
        //生存时间 默认存在内存中 关掉就消失  设置存储在硬盘中
        cookie.setMaxAge(60 * 10);
        //发送cookie
        response.addCookie(cookie);

        return "set cookie";
    }

    @RequestMapping(path = "/cookie/get" ,method = RequestMethod.GET)
    @ResponseBody
    public String getCookie(@CookieValue("code")String code){
        System.out.println(code);
        return "get cookie:"+code;
    }

    /**Session实例*/
    @RequestMapping(path = "/session/set" ,method = RequestMethod.GET)
    @ResponseBody
    public String setSession(HttpSession session){
        session.setAttribute("id",1);
        session.setAttribute("name","Maccha");
        return "set Session";
    }

    @RequestMapping(path = "/session/get" ,method = RequestMethod.GET)
    @ResponseBody
    public String getSession(HttpSession session){
        System.out.println(session.getAttribute("id"));
        System.out.println(session.getAttribute("name"));
        return "get Session:ID = "+session.getAttribute("id")+"name = "+session.getAttribute("name");
    }

    /**ajax实例*/
    @RequestMapping(path = "/ajax" ,method = RequestMethod.POST)
    @ResponseBody
    public String textAjax(String name,int age){
        System.out.println(name);
        System.out.println(age);
        return CommunityUtil.getJSONString(0,"操作成功");
    }


}