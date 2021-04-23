package leetcode_cn.l1078;

import java.util.Arrays;

/**
 * 给出第一个词 first 和第二个词 second，
 * 考虑在某些文本 text 中可能以 "first second third" 形式出现的情况，
 * 其中 second 紧随 first 出现，third 紧随 second 出现。
 *
 * 对于每种这样的情况，将第三个词 "third" 添加到答案中，并返回答案。
 */

public class L1078 {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(findOcurrences("alice is a good girl she is a good student", "a", "good")));
        System.out.println(Arrays.toString(findOcurrences("we will we will rock you", "we", "will")));
        System.out.println(Arrays.toString(findOcurrences("alice is aa good girl she is a good student", "a", "good")));
        System.out.println(Arrays.toString(findOcurrences("obo jvezipre obo jnvavldde jvezipre jvezipre jnvavldde jvezipre jvezipre jvezipre y jnvavldde jnvavldde obo jnvavldde jnvavldde obo jnvavldde jnvavldde jvezipre","jnvavldde","y")));
    }

    public static String[] findOcurrences(String text, String first, String second) {
        String[] s = text.split(" ");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length-2; i++) {
            if(s[i].equals(first)&&s[i+1].equals(second)){
                sb.append(s[i+2]);
                sb.append(" ");
            }
        }
        String res = sb.toString();
        if(res.equals("")){
            return new String[]{};
        }
        return res.split(" ");
    }
}
