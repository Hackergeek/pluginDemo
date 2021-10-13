# 《Android插件化开发指南》随书源码

## BroadcastReceiver的插件化解决方案
### [动态广播的插件化解决方案](./Receiver1.0)
实现步骤：
1. 把宿主App和插件App的dex合并到一起
### [静态广播的插件化方案1](./Receiver1.1)
实现原理：将插件的AndroidManifest中将声明的静态广播当作动态广播来处理

实现步骤：
1. 把宿主App和插件App的dex合并到一起
2. PMS只能读取宿主App的AndroidManifest文件，读取其中的静态广播并注册。我们可以通过反射，手动控制PMS读取插件的AndroidManifest中声明的静态广播列表。
3. 遍历这个静态广播列表。使用插件的classLoader加载列表中的每个广播类，实例化成一个对象，然后作为动态广播注册到AMS中。
### [静态广播的插件化方案2](./Receiver1.2)
实现步骤：
1. 把宿主App和插件App的dex合并到一起
2. 在AndroidManifest文件中预先声明一个StubReceiver用于占坑，并根据需要为其配置多个action（比如上百个）
3. 通过配置文件配置StubReceiver中的action与插件中的静态广播的映射关系，配置文件可以是通过服务器下发的JSON字符串或者把它写到插件APK中（比如插件AndroidManifest中）
4. 在attachBaseContext将插件中所有的静态广播注册成动态广播，然后在StubReceiver收到广播之后，根据映射关系，发送给相应的动态广播。
### [ContentProvider的插件方案](./ContentProvider2)
实现步骤：
1. 把宿主App和插件App的dex合并到一起
2. 读取插件中的ContentProvider信息，借助PackageParser的parsePackage方法，然后提供generateProviderInfo方法，把得到的Package对象转换为我们需要的ProviderInfo类型对象。
3. 把这些插件ContentProvider的packageName设置为当前apk的packageName
4. 通过反射执行ActivityThread的installContentProviders方法，把ContentProvider作为插件的参数，相当于把这些插件ContentProvider“安装”到了宿主App中

优化：

声明一个占坑StubContentProvider，第三方应用通过访问StubContentProvider，然后由StubContentProvider转发给插件ContentProvider。

### [基于静态代理的插件化方案——任玉刚的dynamic-load-apk](https://github.com/singwhatiwanna/dynamic-load-apk)
#### [基于静态代理的Activity插件化方案](./That1.5)
实现步骤：
1. 在宿主的AndroidManifest文件中声明ProxyActivity
2. 启动插件Activity实际上启动的是ProxyActivity，读取Intent中携带的参数：插件APK路径和要启动的插件Activity类名
3. 在ProxyActivity的onCreate方法中创建插件APK对应的ClassLoader，并通过addAssetPath加载插件的资源
4. 通过插件ClassLoader加载并创建插件Activity实例，调用插件Activity实例的setProxy方法将ProxyActivity实例传递给插件Activity实例
5. 反射调用插件Activity的onCreate方法，当触发ProxyActivity其他生命周期回调时，同样需要通过反射调用插件Activity与之对应的方法。

* [That1.0——基于静态代理的Activity插件化方案](./That1.0)
* [That1.1——单个插件内Activity之间的跳转示例](./That1.1)
* [That1.2——将that关键字封装到BasePluginActivity内部示例](./That1.2)
* [That1.3——不同插件内Activity之间的跳转示例](./That1.3)
* [That1.4——面向接口编程，将Activity相关的方法抽象成一个接口，插件Activity实现该接口，宿主Activity可直接调用插件相关方法，避免反射调用的开销](./That1.4)
* [That1.5——通过模拟返回栈，让插件Activity支持LaunchMode](./That1.5)

#### [基于静态代理的Service和BroadcastReceiver插件化方案]