//package com.example.demo.util;
//
//import com.fasterxml.jackson.databind.util.LinkedNode;
//
//public class MergeTwoSortedLists {
////merge 外面包一个这个类
//    public LinkedNode merge(LinkedNode l1, LinkedNode l2) {
//        LinkedNode dummy = new LinkedNode(0); // 创建一个哑节点作为合并后链表的头部LinkedNode current = dummy; // 当前节点指针while (l1 != null && l2 != null) {
//            if (l1.val <= l2.val) {
//                current.next = l1; // 将较小的节点连接到合并后的链表
//                l1 = l1.next; // 链表1指针后移
//            } else {
//                current.next = l2; // 将较小的节点连接到合并后的链表
//                l2 = l2.next; // 链表2指针后移
//            }
//            current = current.next; // 当前节点指针后移
//        }
//
//        // 处理剩余的链表if (l1 != null) {
//            current.next = l1;
//        } else if (l2 != null) {
//            current.next = l2;
//        }
//
//        return dummy.next; // 返回合并后的链表的头部
//    }
//}