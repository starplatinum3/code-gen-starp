package top.starp.util.leetCode.jinRong;

//public class Ques2 {
//}

import java.util.*;
public class Ques2 {
    public static void main(String [] args){
//       findPos()
//       String
        Scanner scanner=new Scanner(System.in);
        String  a=  scanner.nextLine();
//       a.split(" ")
        int [] arr=new int[a.length()];
        for(int i=0;i<a.length();i++){
            arr[i]=a.charAt(i)-'0';
        }
        int res= findPos(arr);
        if(res==-1){
            System.out.println("NO");
        }else{
            System.out.println("YES");
        }
    }
    static int findPos(int []arr){
        int n=arr.length;
        int []pref=new int[n];
//        int []suff=new int[n];
        int []suff=new int[n+1];
//         pref[0]=1;
        pref[0]=arr[0];
        for( int i=1;i<n;i++){
//            pref[i]=pref[i-1]*arr[i-1];
            pref[i]=pref[i-1]*arr[i];
        }
//        System.out.println("pref");
//        System.out.println(Arrays.toString(pref));
        suff[n-1]=arr[n-1];
//         suff[n]=1;
        for(int i=n-2;i>=0;i--){
//            suff[i]=suff[i+1]*arr[i+1];
            suff[i]=suff[i+1]*arr[i];
        }
//        System.out.println("suff");
//        System.out.println(Arrays.toString(suff));
        for(int i=0;i<n;i++){
//             if
//            if(n%2==0){
//                if(pref[i]==suff[i+1]){
//                    return i;
//                }
//            }else{
//                if(pref[i]==suff[i]){
//                    return i;
//                }
//            }

            if(pref[i]==suff[i+1]){
                return i;
            }

        }

        return -1;
    }
}
