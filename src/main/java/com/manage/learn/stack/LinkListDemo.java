package com.manage.learn.stack;


/**
 * @author 曾佳容
 * @ClassName
 * @Description
 * @date 2020/3/27
 */
public class LinkListDemo<E>{

    private int size = 0;
    private Node<E> first;
    private Node<E> last;


    private static class Node<E>{

      public E item;

      public Node<E> next;

      public Node<E> prev;

      public Node(Node<E> prev, E item, Node<E> next) {
        this.item = item;
        this.next = next;
        this.prev = prev;

      }

    }

    //添加元素; 添加到最后一个结点;
    public void add(E e){
        //给尾结点赋值
        Node<E> last1 = last;
        //给尾结点设置新结点
        last = new Node<>(last1,e,null);
        //如果尾结点1是为空的
        if(last1 == null){
          //设置头结点为新结点
          first = last;
        } else {
          //设置尾结点1为新结点
          last1.next = last;
        }
        size++;
    }


    //删除
    public boolean remove(E index){

      for (Node<E> node = first; node != null; node = node.next) {
        if (node.item == null) {

          //获取被删除元素的后结点
          final Node<E> nodeNext = node.next;
          //获取被删除元素的前结点
          final Node<E> nodePrev = node.prev;

          //如果前结点为空
          if(nodePrev == null){
            //将后结点置为头结点
            first = nodeNext;
          } else {
            //前结点的尾结点指向被删除的尾结点
            nodePrev.next = nodeNext;
            //被删除结点前置为null
            node.prev = null;
          }

          //如果尾结点为空
          if(nodeNext == null){
            //将后结点置为尾结点
            last = nodePrev;
          } else {
            //前结点的尾结点指向被删除的尾结点
            nodeNext.next = nodePrev;
            //被删除结点尾置为null
            node.next = null;
          }

          size--;

          return true;
        }
      }
       return  false;
    }

    public E get(int index){
      return node(index).item; //获取下标index的node结点
    }


  /**
   * 获取下标为index的结点
   * @param index
   * @return
   */
   Node<E> node(int index) {

     // 如果index在前半部分, 则从前循环
      if (index < (size >> 1)) {
        Node<E> node = first;
        for (int i = 0; i < index; i++){
          node = node.next;
        }
        return node;
        }else { //如果index在后半部分, 则从后循环
          Node<E> node = last;
          for (int i = size - 1; i > index; i--) {
            node = node.prev;

          }
        return node;
      }
    }


    public int size(){
     return  size;
    }



  public static void main(String[] args) {

      LinkListDemo<String> demo = new LinkListDemo<>();
      demo.add("1");
      demo.add("2");
      demo.add("3");
      demo.add(null);
      demo.remove("1");
      demo.add("4");
      demo.add(null);
    System.out.println(demo.get(1));


  }


}
