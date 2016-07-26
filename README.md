#捋一捋Android的转场动画
 
##背景
 
随着 Material Design设计概念的提出，使得很多的开发过程中对动画和UI的优化越来越重要，其中一个重要的动画
就是`Material Deisgn : Material Motion`
 >>>“Motion provides meaning. Objects are presented to the user without breaking the continuity of experience even as they transform and reorganize. Motion in the world of material design is used to describe spatial relationships, functionality, and intention with beauty and fluidity.”

可以看出Motion是多么的强大，但是在Android机型上使用起来并不是一番风顺

##历史
* Android 4.0,引入了新的属性 android:animateLayoutChanges=[true/false] ，所有派生自 ViewGroup 的控件都具有此属性，只要在XML中添加上这个属性，就能实现添加/删除其中控件时，带有默认动画，如果要自定义动画，就需要使用 LayoutTransaction 了。实践证明，实际上这套机制使用起来并不是那么灵活
* Android 4.4 引入了 Scenes 和 Transitions（场景和变换），Scene 保存了布局的状态，包括所有的控件和控件的属性。布局可以是一个简单的视图控件或者复杂的视图树和子布局。保存了这个布局状态到 Scene 后，我们就可以从另一个场景变化到该场景。从一个场景到另一个场景的变换中会有动画效果，这些动画信息就保存在 Transition 对象中。要运行动画，我们要使用 TransitionManager 实例来应用 Transition

但是归功于Android碎片化，由于不能适配低版本的机型随意很多开发者放弃了这个强大的功能
，但是现在好了有了下面就是开源库 [Transitions-Everywhere](https://github.com/andkulikov/Transitions-Everywhere)
，Transitions-Everywhere 向后移植到 Android 4.O ,并且兼容 Android 2.2 +
**所以是时候学习转场动画**

##作用
说了那么多虚无的概率，那转场动画到底是干啥的呢，咱们上一个效果大家就明白了：

![slide](https://github.com/wzgiceman/TransitionsDemo/blob/master/gif/slide.gif)

其实上面执行的逻辑就是一个Textview的visible设置及时显示和隐藏功能，而且使用起来也很方便代码：

```java
TransitionManager.beginDelayedTransition(llyout,  new Slide(Gravity.LEFT));
tv.setVisibility(tv.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
```
实现这样的效果，只需要一行代码就可以，是不是很强大！那就开始咱们今天的学习

##环境配置
* 依赖导入：
```java
compile "com.andkulikov:transitionseverywhere:1.6.5"
```
* 将所有类包名为 `android.transition.*` 的替代为 `com.transitionseverywhere.*`

##动画Transition 
**那有哪些现成的动画供我们使用呢**

* TransitionSet.用来驱动其他的 Transition .类似于 AnimationSet,能够让一组 Transition 有序，或者同时执行
* ChangeBounds. 改变 View 的位置和大小
* Fade 可以用来做最常用的淡入和淡出动画
* Slide 滑动动画，上面展示的例子就是这个动画
* Scale 缩放动画，可以缩小和扩大效果
* Recolor 颜色，渐变的颜色修改
* ChangeText 文本修改，主要是textview中使用
* Rotate 旋转动画
* ExplodeActivity 扩散动画

大家不要着急，下面一一为大家讲解这些动画的使用方法

##使用

###滑动动画
![silde](https://github.com/wzgiceman/TransitionsDemo/blob/master/gif/slide.gif)

[源码链接](https://github.com/wzgiceman/TransitionsDemo/blob/master/app/src/main/java/com/view/transitionsdemo/SlideActivity.java)

在textview显示很隐藏前调用动画方法，先初始化一个动画`Transition`，这里使用的是`Slide`动画初始化传入一个滑动方法值,`beginDelayedTransition`方法中第一个参数设定
需要动画变量的父view布局对象
```java
    //    Slide （滑行）
    public void onFadeClick(int diction) {
        Transition transition = new Slide(diction);
        TransitionManager.beginDelayedTransition(llyout, transition);
        tv.setVisibility(tv.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
    }

```

###Explode粒子扩散
![explode](https://github.com/wzgiceman/TransitionsDemo/blob/master/gif/export.gif)

[源码链接地址](https://github.com/wzgiceman/TransitionsDemo/blob/master/app/src/main/java/com/view/transitionsdemo/ExplodeActivity.java)

通过`EasyRecyclerView`实现一个gridview效果的界面，点击item后扩散每个item，当动画开始后设置移除adpter的数据，恢复的时候再讲数据添加回来
注意需要Explode的EpicenterCallback返回一个扩散的原点，这是使用的是点击对象的位置！

```java
/**
     * 开始的动画
     *
     * @param view
     */
    public void onStartClick(View view) {
        final Rect viewRect = new Rect();
        view.getGlobalVisibleRect(viewRect);
        TransitionSet set = new TransitionSet()
                .addTransition(new Explode().setEpicenterCallback(new Transition.EpicenterCallback() {
                    @Override
                    public Rect onGetEpicenter(Transition transition) {
                        return viewRect;
                    }
                }).excludeTarget(view, true));
        TransitionManager.beginDelayedTransition(recyle, set);
        open = !open;
        if (open) {
            btnClose.setVisibility(View.VISIBLE);
            for (String str : getData()) {
                adapter.remove(str);
            }
        } else {
            btnClose.setVisibility(View.GONE);
            adapter.addAll(getData());
        }
    }
```

###fade和scale伸缩动画
![scale](https://github.com/wzgiceman/TransitionsDemo/blob/master/gif/fade_scale.gif)
[源码链接地址](https://github.com/wzgiceman/TransitionsDemo/blob/master/app/src/main/java/com/view/transitionsdemo/ScaleAndFadeActivity.java)
* 单一的fade动画
```java
Transition transition=new Fade();
        transition.setDuration(2000);
        TransitionManager.beginDelayedTransition(llyout,transition );
        tv.setVisibility(tv.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
```

* 单一的scale动画
```java
Transition transition=new Scale(0.8f);
        transition.setDuration(2000);
        TransitionManager.beginDelayedTransition(llyout, transition);
        tv.setVisibility(tv.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
```

* 结合使用效果更好哦
```java
TransitionSet set = new TransitionSet()
                .addTransition(new Scale(0.9f))
                .addTransition(new Fade());
        set.setDuration(2000);
        TransitionManager.beginDelayedTransition(llyout, set);
        tv.setVisibility(tv.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
```

###Path动画
![path](https://github.com/wzgiceman/TransitionsDemo/blob/master/gif/path.gif)

[源码链接地址](https://github.com/wzgiceman/TransitionsDemo/blob/master/app/src/main/java/com/view/transitionsdemo/PathActivity.java)

* 使用 setPathMotion 方法，可以在任意两点之间的位置变换做路径动画,比如使用 ChangeBounds 改变 View的位置

```java
 TransitionManager.beginDelayedTransition(rlyout,
                new ChangeBounds().setDuration(1000));
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) btnPath.getLayoutParams();
        params.gravity = open ? Gravity.TOP : Gravity.BOTTOM;
        btnPath.setLayoutParams(params);
```
* 当我们需要移除父容器内所有的 view，然后再增加一些新的 view。这些元素可能非常相似，数据替换动画，我们怎么能够让 Transition 框架分清哪些元素是被移除的，哪些元素是需要移动到新的位置呢？ 
    只需要调用 TransitionManager.setTransitionName(View v, String transitionName) 方法就好了，
 第一参数传入想要标记的 view，在第二个参数传入一个唯一的标识符，这样就能可以保证每个 View 的 Transitions 的唯一性！

```java
TransitionManager.beginDelayedTransition(llyoutData, new ChangeBounds().setDuration(1000));
        llyoutData.removeAllViews();
        Collections.shuffle(list);
        for (int i = 0; i < list.size(); i++) {
            String item = list.get(i);
            TextView tv =(TextView) View.inflate(this, R.layout.llyout_item, null);
            tv.setText(item);
            TransitionManager.setTransitionName(tv, item);
            llyoutData.addView(tv);
        }
```
###文字动画集合
![tx](https://github.com/wzgiceman/TransitionsDemo/blob/master/gif/tx_more.gif)

[源码链接地址](https://github.com/wzgiceman/TransitionsDemo/blob/master/app/src/main/java/com/view/transitionsdemo/TxtActivity.java)

* Recolor动画

```java
TransitionManager.beginDelayedTransition(llyout, new Recolor().setDuration(1000));
                if (colorTag) {
                    tv.setTextColor(getResources().getColor(R.color.drak));
                    tv.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimaryDark)));
                } else {
                    tv.setTextColor(getResources().getColor(R.color.colorPrimary));
                    tv.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.drak)));
                }
```
* ChangeText动画

```java
TransitionManager.beginDelayedTransition(llyout, new ChangeText().setChangeBehavior(ChangeText
                        .CHANGE_BEHAVIOR_OUT_IN).setDuration(1000));
                txTag = !txTag;
                if (txTag) {
                    tv.setText("文字我改了");
                } else {
                    tv.setText("文字又复原了");
                }
```

* Rotate动画

```java
TransitionManager.beginDelayedTransition(llyout, new Rotate().setDuration(1000));
                roteTag = !roteTag;
                if (roteTag) {
                    img.setRotation(90);
                    tv.setRotation(180);
                } else {
                    img.setRotation(0);
                    tv.setRotation(0);
                }
```

##指定特定动画对象
配置 Transitions 也非常容易，你可以给一些特殊目标的 View 指定 Transitions，仅仅只有它们才能有动画
###增加动画目标
* addTarget(View target) . view
* addTarget(int targetViewId).  通过view 的id
* addTarget(String targetName)  .与 TransitionManager
* .setTransitionName 方法设定的标识符相对应。
* addTarget(Class targetType) . 类的类型 ，比如android.
* widget.TextView.class。

###移除动画目标
* removeTarget(View target)
* removeTarget(int targetId)
* removeTarget(String targetName)
* removeTarget(Class target)

###排除不想做动画的view
* excludeTarget(View target, boolean exclude)
* excludeTarget(int targetId, boolean exclude)
* excludeTarget(Class type, boolean exclude)
* excludeTarget(Class type, boolean exclude)

###排除某个 ViewGroup 的所有子 View
* excludeChildren(View target, boolean exclude)
* excludeChildren(int targetId, boolean exclude)
* excludeChildren(Class type, boolean exclude)





































