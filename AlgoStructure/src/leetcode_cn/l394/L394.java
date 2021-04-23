package leetcode_cn.l394;

/**
 * 给定一个经过编码的字符串，返回它解码后的字符串。
 *
 * 编码规则为: k[encoded_string]，表示其中方括号内部的 encoded_string 正好重复 k 次。注意 k 保证为正整数。
 *
 * 你可以认为输入字符串总是有效的；输入字符串中没有额外的空格，且输入的方括号总是符合格式要求的。
 *
 * 此外，你可以认为原始数据不包含数字，所有的数字只表示重复的次数 k ，例如不会出现像 3a 或 2[4] 的输入
 */

public class L394 {
    public static void main(String[] args) {
        System.out.println(decodeString("3[a]2[bc]"));
        System.out.println(decodeString("3[a2[c]]"));
        System.out.println(decodeString("2[abc]3[cd]ef"));
        System.out.println(decodeString("abc3[cd]xyz"));
        System.out.println(decodeString("100[leetcode]"));
    }
    public static String number = "0123456789";

    public static String decodeString(String s) {
        int indexLeft = s.indexOf("[");
        if(indexLeft == -1){
            return s;
        }
        int reentrant = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = indexLeft; i < s.length(); i++) {
            if(s.charAt(i) == '['){
                reentrant++;
            }
            if(s.charAt(i) == ']' && --reentrant == 0){
                String repeat = s.substring(indexLeft+1, i);
                int numberPointer = indexLeft-1;
                while (number.contains(String.valueOf(s.charAt(numberPointer)))){
                    if(--numberPointer < 0){
                        break;
                    }
                }
                sb.append(s.substring(0,++numberPointer));
                int num = Integer.parseInt(s.substring(numberPointer, indexLeft));
                for (int j = 0; j < num; j++) {
                    sb.append(repeat);
                }
                sb.append(s.substring(i+1));
                return decodeString(sb.toString());
            }
        }
        return s;
    }
}
