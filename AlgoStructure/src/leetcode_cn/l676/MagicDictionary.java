package leetcode_cn.l676;

import java.util.ArrayList;
import java.util.List;

/**
 * 设计一个使用单词列表进行初始化的数据结构，单词列表中的单词互不相同。
 * 如果给出一个单词，请判定能否只将这个单词中一个字母换成另一个字母，使得所形成的新单词存在于你构建的字典中。
 *
 * 实现 MagicDictionary 类：
 *
 * MagicDictionary() 初始化对象
 * void buildDict(String[] dictionary) 使用字符串数组 dictionary 设定该数据结构，dictionary 中的字符串互不相同
 * bool search(String searchWord) 给定一个字符串 searchWord ，
 * 判定能否只将字符串中 一个 字母换成另一个字母，使得所形成的新字符串能够与字典中的任一字符串匹配。
 * 如果可以，返回 true ；否则，返回 false 。
 */

public class MagicDictionary {
    private String[] dict;

    public MagicDictionary() {
        dict = null;
    }

    public void buildDict(String[] dictionary) {
        dict = dictionary;
    }

    public boolean search(String searchWord) {
        List<String> strs = new ArrayList<>();
        for (String s : dict) {
            if (searchWord.length() == s.length()) {
                strs.add(s);
            }
        }
        boolean b;
        for (String s : strs){
            b = compare(searchWord, s);
            if(b){
                return true;
            }
        }
        return false;
    }

    private boolean compare(String s1, String s2){
        char[] ch1 = s1.toCharArray();
        char[] ch2 = s2.toCharArray();
        int diff = 0;
        for (int i = 0; i < ch1.length; i++) {
            if(diff>1){
                return false;
            }
            if(ch1[i]!=ch2[i]){
                diff++;
            }
        }
        return diff == 1;
    }
}