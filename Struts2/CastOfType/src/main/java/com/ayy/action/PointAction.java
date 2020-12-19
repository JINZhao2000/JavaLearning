package com.ayy.action;

import com.ayy.vo.Point;

/**
 * @ ClassName PointAction
 * @ Description
 * @ Author Zhao JIN
 * @ Date 11/12/2020 20
 * @ Version 1.0
 */
public class PointAction {
    private Point point;

    public String execute(){
        System.out.println("x="+point.getX()+"\ty="+point.getY());
        return "success";
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }
}
