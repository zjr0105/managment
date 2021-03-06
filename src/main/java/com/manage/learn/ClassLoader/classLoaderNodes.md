## Java类加载机制

###classLoader能做什么？
    
  是用来加载Class的。可以将Class 的字节码形式转换成内存形式的class对象。

  字节码可以来自于 磁盘文件*.class, jar包里的*.class

  字节码本质就是一个字节数组[]byte,有着特定的复杂的内部格式
        
###什么是classLoader？
    
java文件通过编译器变成.class文件，接下来classLoader又将这些.class文件加载到JVM中。
 
classLoader的作用：类的加载
   
###classLoader的概念？
        
  将类的.class文件中的二进制数据读入到内存中，将其放在运行时数据区的方法区内，然后在堆内创建一个 java.lang.Class对象，
用来封装类在方法区内的数据结构
     
###classLoader的过程
    
顺序的开始： 加载 -> 验证 -> 准备 -> 解析(这个过程也可以在初始化之后在开始解析) -> 初始化 -> 使用 -> 卸载   
           
###延迟加载
        
JVM 运行并不是一次性加载所需要的全部类的，是按需加载  
        
###JVM
    
  JVM运行实例中会存在多个ClassLoader,不同的ClassLoader会从不同的地方加载字节码文件。

  JVM中内置重要的ClassLoader：

  BootstrapClassLoader(启动类加载器):由C语言写的
    负责加载JVM运行时核心类(位于JAVA_HOME/lib/rt.jar文件中)，常用内置库 java.xxx.*都在里面，
    比如： java.util.*, java.lang.*等等           

  ExtClassLoader(扩展类加载期): 负责加载JVM扩展类,比如 swing 系列, 内置的 js 引擎, xml解析器等等。
    库名通常以javax开头,jar包位于JAVA_HOME/lib/ext/*.jar中

  AppClassLoader(应用程序类加载器): 可以由ClassLoader类提供的静态方法getSystemClassLoader()得到,
因此它就是系统类加载器,平时编写的代码通常都是由它加载的,会加载Classpath环境变量里定义的路径中的jar包和目录


  三种类的加载顺序:
    自定义加载器 ->
            AppClassLoader > ExtClassLoader > BootstrapClassLoader
   
  jdk内置的URLClassLoader

  URLClassLoader: 用户只需要传递规范的网络路径给构造器,就可以使用URLClassLoader来加载远程类库
    可以加载远程类库,还可以加载本地路径的类库,取决于构造器中不同的地址形式
    子类: ExtensionClassLoader and appClassLoader 都是从本地文件系统中加载类库 
            
###ClassLoader传递性
       
   在程序运行中遇到一个位置的类, 则调用者ClassLoader来加载当前位置的未知的类, 这就是ClassLoad的传递性
    
   所有延迟加载的类都会由初始调用main方法的这个ClassLoader全权负责, 就是AppClassLoader
        
###双亲委派
   AppClassLoader 如果遇到没有加载的系统类库,则就必须把加载工作交个 BootstrapClassLoader和ExtClassLoader来做,这就是双亲委派
        
   AppClassLoader (classpath) ->(parent) ExtClassLoader(lib/ext/*.jar) ->(parent) BootstrapClassLoader(lib/rt.jar)

   AppClassLoader在加载一个未知的类时,会首先交给extClassLoader来加载,如果extClassLoader可以加载

   每个ClassLoader对象内部都会有一个parent属性指向它的父加载器
   如果parent是为null时就表示它的父加载器是BootstrapClassLoader
   如果某个class对象的classLoader属性值是null,那么就表示这个类也是BootstrapClassLoader加载的.
        
###Class.forName
   原理: mysql驱动的Driver类里有一个静态代码块,它会在Driver类被加载的时候执行
        
   forName方法也是使用调用者Class对象的ClassLoader来加载目标类,提供了多参数版本,可以指定使用哪个ClassLoader来加载
        
   forName通过这种形式可以突破内置加载器的限制,通过使用类加载器允许我们自由加载其它任意来源的类库。根据ClassLoader的传递性
    目标类库传递引用到其他类库也将会使用自定义加载器加载
      
###自定义加载器
   ClassLoader三个重要的方法 loadClass(),findClass(),defineClass();
       
   loadClass()方法是加载目标类的入口,它会先找 ClassLoader 双亲是否已经加载了,
     如果没有找到就会让双亲尝试加载,如果双亲都加载不了,就会调用findClass()让自定义自己加载
     
   findClass()方法是需要子类来覆盖的,不同的加载器将使用不同的逻辑来获取目标类的字节码.拿到字节码之后再调用
       defineClass()方法将字节码转换成Class对象
       
###Class.forName and ClassLoader.loadClass 的区别

   Class.forName: ① 可以将类的.class文件加载到jvm中之外,还会对类进行解释,执行类中的static块 (会加载静态方法)
                    内部调用的方法是: Class.forName(className,true,classloader)
                        true: 是否需要初始化,默认是需要初始化. 
   ClassLoader: ①只做一件事情, 就是讲.class文件加载到JVM中,不会执行static中的内容, 只有在newInstance才会执行static块 (不会加载静态方法)
                    内部调用的方法是: ClassLoader.loadClass(className,false)
                        false: 目标对象是否进行连接,false不进行连接
                   
ClassLoader就是遵循双亲委派模型最终调用启动类加载器的类加载,实现的功能是"通过一个类的quanxian定名来获取描述此类的二进制字节流", 获取到二进制流后放在JVM中.
Class.forName()方法实际上也是调用的ClassLoader来实现的
   
     
     
        
        