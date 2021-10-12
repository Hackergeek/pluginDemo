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
