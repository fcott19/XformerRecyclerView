# XformerRecyclerView
一个可以自定义转场动画的RecyclerView

## 说明
该控件仅供参考交流 切勿盲目使用到项目中

## 使用
```
recyclerView = (XformerRecyclerView) this.findViewById(R.id.recyclerView);
recyclerView.setAdapter(new MyAdapter());
recyclerView.setPageTransformer(new SnapPageTransformer());
```
setPageTransformer(PageTransformer transformer)方法的参数是一个interface\
我们可以实现PageTransformer的transformPage方法来自定义动画

## 效果
项目中已经实现了三个常用的自定义动画类\
1.SnapPageTransformer\
![](https://github.com/fcott19/XformerRecyclerView/blob/master/app/src/main/gif/SnapPageTransformer.gif)  
2.RotateDownPageTransformer\
![](https://github.com/fcott19/XformerRecyclerView/blob/master/app/src/main/gif/RotateDownPageTransformer.gif)  
3.DepthPageTransformer\
![](https://github.com/fcott19/XformerRecyclerView/blob/master/app/src/main/gif/DepthPageTransformer.gif)  

## 我的博客
[我的博客](http://www.jianshu.com/u/832088a30e73 "快点我")  

