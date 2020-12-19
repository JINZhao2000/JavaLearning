package com.ayy.action;

/**
 * @ ClassName UserAction
 * @ Description
 * @ Author Zhao JIN
 * @ Date 10/12/2020 19
 * @ Version 1.0
 */
// 8.3
//@ParentPackage("struts-default")
//@Namespace("/")
//@Result(name="list",location="/list.jsp")
public class UserAction {

    public String save(){
        System.out.println("save");
        return "success";
    }

    public String delete(){
        System.out.println("delete");
        return "success";
    }

    public String update(){
        System.out.println("update");
        return "success";
    }
    // 8.3
//    @Action("query")
    public String query(){
        System.out.println("query");
        return "list";
    }
}
