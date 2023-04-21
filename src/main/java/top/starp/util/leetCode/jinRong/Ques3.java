package top.starp.util.leetCode.jinRong;

import java.util.*;

public class Ques3 {
    public static void main(String[] args) {
//        String s


     Scanner scanner=new Scanner(System.in);
        String s = scanner.nextLine();
        cntCh(s);
    }

    static String cntCh(String s){
        int [] count=new int[256];

        for(int i=0;i<s.length();i++){
            count[s.charAt(i)]++;
        }
        List<Character>list=new ArrayList<>();
        for(int i=0;i<256;i++){
            if (count[i]>0) {
                list.add((char)i);
            }
        }
        Collections.sort(list, new Comparator<Character>() {
            @Override
            public int compare(Character o1, Character o2) {
                int diff=count[o2]-count[o1];
                if(diff!=0){
                    return  diff;
                }
                return o1-o2;
            }
        });

        String res="";
        for (Character character : list) {
            System.out.print(character);
            res+=character;
        }
        return res;
    }
}
