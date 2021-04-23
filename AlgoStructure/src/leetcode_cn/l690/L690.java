package leetcode_cn.l690;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 给定一个保存员工信息的数据结构，它包含了员工 唯一的 id ，重要度 和 直系下属的 id 。
 *
 * 比如，员工 1 是员工 2 的领导，员工 2 是员工 3 的领导。他们相应的重要度为 15 , 10 , 5 。
 * 那么员工 1 的数据结构是 [1, 15, [2]] ，员工 2的 数据结构是 [2, 10, [3]] ，员工 3 的数据结构是 [3, 5, []] 。
 * 注意虽然员工 3 也是员工 1 的一个下属，但是由于 并不是直系 下属，因此没有体现在员工 1 的数据结构中。
 *
 * 现在输入一个公司的所有员工信息，以及单个员工 id ，返回这个员工和他所有下属的重要度之和。
 *
 */

public class L690 {
    public static void main(String[] args) {
        List<Integer> a = new ArrayList<>();
        List<Integer> b = new ArrayList<>();
        List<Integer> c = new ArrayList<>();
        a.add(2);
        a.add(3);
        Employee e1 = new Employee();
        e1.id = 1;
        e1.importance = 5;
        e1.subordinates = a;
        Employee e2 = new Employee();
        e2.id = 2;
        e2.importance = 3;
        e2.subordinates = b;
        Employee e3 = new Employee();
        e3.id = 3;
        e3.importance = 3;
        e3.subordinates = c;
        List<Employee> list = new ArrayList<>();
        list.add(e1);
        list.add(e2);
        list.add(e3);
        System.out.println(getImportance(list, 1));
    }

    public static int getImportance(List<Employee> employees, int id) {
        Map<Integer, Employee> employeeMap = new HashMap<>();
        employees.forEach((e)->{
            employeeMap.put(e.id,e);
        });
        Employee employee = employeeMap.get(id);
        return getOneImportant(employeeMap, id, 0);
    }

    public static int getOneImportant(Map<Integer, Employee> map, int id, int lastScore){
        Employee employee = map.get(id);
        lastScore += employee.importance;
        List<Integer> subIdList = employee.subordinates;
        if(subIdList.size()==0){
            return lastScore;
        }
        for (Integer i : subIdList){
            lastScore = getOneImportant(map, i, lastScore);
        }
        return lastScore;
    }
}
