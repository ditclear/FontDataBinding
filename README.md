# FontDataBinding
Custom fonts in Android the easier way...

简书：[使用DataBinding来进行字体的自定义](http://www.jianshu.com/p/749af9b6c504)

***写在前面***

> 在Android应用开发中，由于客户或者个人的需要（~~谁叫Android默认的字体那么丑~~），所以需要配置不同的字体,而 Android 只能在 xml 中配置系统默认提供的四种字体,需要自定义的字体都需要在 Java 代码中配置。

总结一下以前自定义字体的方法

1 .**通过findViewById找到view,然后一个个的去设置字体**

```java
Typeface customFont = Typeface.createFromAsset(this.getAssets(), "fonts/customFont.ttf");
TextView view = (TextView) findViewById(R.id.text);
view.setTypeface(customFont);
···
```

​      一个看着还好，可是应用中可是有很多的文本或者按钮的，不可能逐个去设置。于是大多数都选择了第二种方法。

2 .**创建一个子类，继承自`TextView`或者`Button`等等**

```java
public class CustomTextView extends TextView {

        public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }

        public CustomTextView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public CustomTextView(Context context) {
            super(context);
        }

        public void setTypeface(Typeface tf, int style) {
            if (style == Typeface.BOLD) {
                super.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/CustomFont_Bold.ttf"));
            } else {
                super.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/CustomFont.ttf"));
            }
        }
    }
```

然后在xml文件中每次都使用自定义的View，相比上一个方案，这一个要稍微好点。但相信都达不到各位的要求，只不过是换一个字体，需要这么麻烦吗？

3 .  **Calligraphy**

这是一个开源库，地址是https://github.com/chrisjenx/Calligraphy ，在github上5000+star,感觉还是不错的，也从侧面说出自定义字体的需求量有多大。

### *那么接下来就讲重点了*

如何使用DataBinding来进行字体的自定义呢？

**TOO EASY,不用每次都去手写，也不需要自定义View**

首先，打开DataBinding

```gr
android {
    compileSdkVersion 25
    buildToolsVersion "25.0.0"
    defaultConfig {
        ···
    }
    buildTypes {
        ···
    }
    dataBinding {
        enabled = true
    }
}
```

再看结构

![结构.png](http://upload-images.jianshu.io/upload_images/3722695-a66e799a55a3ab38.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/400)

在项目中的assets/fonts文件夹下加入了5种字体，然后在`strings.xml`中定义了字体的路径

```xml
<resources>
	···
    <string name="notoSans_regular">fonts/NotoSans-Regular.ttf</string>
    <string name="notoSansui_boldItalic">fonts/NotoSansUI-BoldItalic.ttf</string>
    <string name="icomoon">fonts/icomoon.ttf</string>
    <string name="nutso2">fonts/Nutso2.otf</string>
    <string name="ruthie">fonts/Ruthie.ttf</string>
  	···
</resources>
```

而在layout的xml中只需要这么使用，那么便已经完成了8成了

```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android">

    <data>

    </data>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        android:gravity="center">

        <TextView
            android:layout_width="match_parent"
            android:layout_height="50dp"
            android:gravity="center"
            android:text="Hello world"
            android:textSize="18sp"
            android:typeface="@{@string/nutso2}"/>

        <TextView
            android:layout_width="match_parent"
            android:layout_height="50dp"
            android:gravity="center"
            android:textSize="18sp"
            android:text="Hello world"
            android:typeface="@{@string/notoSans_regular}"/>

        <TextView
            android:layout_width="match_parent"
            android:layout_height="50dp"
            android:gravity="center"
            android:textSize="18sp"
            android:text="Hello world"
            android:typeface="@{@string/notoSansui_boldItalic}"/>

        <TextView
            android:layout_width="match_parent"
            android:layout_height="50dp"
            android:gravity="center"
            android:text="Hello world"
            android:textSize="18sp"
            android:typeface="@{@string/icomoon}"/>

        <TextView
            android:layout_width="match_parent"
            android:layout_height="50dp"
            android:gravity="center"
            android:text="Hello world"
            android:textSize="18sp"
            android:typeface="@{@string/ruthie}"/>
    </LinearLayout>
</layout>
```

而剩下的两成，一成在`FontBinding`文件

```java
public class FontBinding {

    @BindingConversion
    public static Typeface convertStringToFace(String s){
        try {
            return Typeface.createFromAsset(FontApp.getInstance().getAssets(),s);
        }catch (Exception e){
            throw e;
        }
    }
}
```

这里定义了一个转换器，将xml文件中获取到的字符资源，转换成了一个`Typeface`

最后一成，只需要使用就好了

```java
public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }
}
```

看一下截图，一起哈啤

![截图.jpg](http://upload-images.jianshu.io/upload_images/3722695-1ea5bc839008136b.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/400)

没了解过DataBinding的人可能不知道`ActivityMainBinding`是怎么来的，这是编译时生成的，在如下图所示的位置可以找到
![344369DA-628F-42E1-8D30-5D32528F567A.png](http://upload-images.jianshu.io/upload_images/3722695-db29098323133312.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/400)

打开`ActivityMainBinding`可以看到这样的代码

```java
            this.mboundView1.setTypeface(com.ditclear.sample.FontBinding.convertStringToFace(mboundView1.getResources().getString(R.string.nutso2)));
            this.mboundView2.setTypeface(com.ditclear.sample.FontBinding.convertStringToFace(mboundView2.getResources().getString(R.string.notoSans_regular)));
            this.mboundView3.setTypeface(com.ditclear.sample.FontBinding.convertStringToFace(mboundView3.getResources().getString(R.string.notoSansui_boldItalic)));
            this.mboundView4.setTypeface(com.ditclear.sample.FontBinding.convertStringToFace(mboundView4.getResources().getString(R.string.icomoon)));
            this.mboundView5.setTypeface(com.ditclear.sample.FontBinding.convertStringToFace(mboundView5.getResources().getString(R.string.ruthie)));
```

这些`mboundViewX`就是xml中的TextView,由于没写id，所以都是自动生成的名称，可以看到给每一个使用了绑定的TextView都设置了字体，相当于系统帮我们做了第一个方法中重复性的工作。

当然我们还可以优化一下，由于每次都要操作assests文件夹，也会带来一定的开销，所以也有必要提供一个字体缓存`FontCache`,代码来自 [britzl on stackoverflow](http://stackoverflow.com/a/16902532/814353)

```java
public class FontCache {

    private static ArrayMap<String, Typeface> fontCache = new ArrayMap<String, Typeface>();

    public static Typeface getTypeface(String fontName, Context context) {
        Typeface tf = fontCache.get(fontName);
        if(tf == null) {
            try {
                tf = Typeface.createFromAsset(context.getAssets(), fontName);
            }
            catch (Exception e) {
                return null;
            }
            fontCache.put(fontName, tf);
        }
        return tf;
    }
}
```
那么`FontBinding`就变成了这样
```java
public class FontBinding {

    @BindingConversion
    public static Typeface convertStringToFace(String fontName){
        try {
            return FontCache.getTypeface(fontName,FontApp.getInstance());
        }catch (Exception e){
            throw e;
        }
    }

}

```

好了，大功告成！这也算是一种新思路吧。

#### 总结
这只是DataBinding的一个小例子，它能做的远不止如此，而且DataBinding是android自带的，不需要引入第三方库，只需要在app.build文件中开启就好了，而且学习成本极低。
再说明一个可能被大家误解的地方，DataBinding并不是要一定和MVVM结合使用，应该说是MVVM离不开DataBinding,但DataBinding是可以在其它任何框架下使用的，包括MVC、MVP等等，至少它可以取代`ButterKnife`,不用生成那么一长串@BindView的代码。

想要了解DataBinding的可以看看慕课网上的视频或者看看[完全掌握Android Data Binding](http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0603/2992.html)
