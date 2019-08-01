# Rx-MVVM
Kotlin + RxBinding + Rxjava + MVVM + Koin

#### 基于Google Android Architecture Component组件设计的MVVM架构

#### 主要分为三层，如下图所示：

![image](final-architecture.png)

##### 由上图可以清晰的看到，整个架构三层分层（DAL层(数据访问层)， DLL层(业务逻辑层)， UI层(表现层)） 十分的清晰明了，各层各司其职，

##### 1. UI层，UI层主要负责视图的显示，不应包含任何业务逻辑的操作，并且UI层不能跨层去访问数据层。

##### 2. ViewModel层（DLL层），主要负责业务逻辑的处理和数据层数据的分发，不应持有View层的任何引用。

##### 3. Repository层（DAL层）， 主要负责数据的处理，包括数据的请求、缓存等。

#### 一、本框架核心组件core的各个目录解析

api         接口相关
    
        APIException                            针对于后台接口的错误统一处理为APIException
base        基类
        
        view
                BaseActivity                
                BaseViewModel
                BaseRefreshActivity             带上下拉刷新的Activity基类
                BaseRefreshViewModel            
                CommonAdapter                   通用的RecyclerView适配器Adapter，支持单类型和多类型，非常方便变换
        response
                BaseResponse                    基本数据类
              
di          注入
        
        module                                  
                NetworkModule                   网络相关单例
                
extensions   扩展

        context
                ContextExtension
        drawable
                DrawableExtension
        rxjava
                RxJavaExtension
        view
                ViewExtension
                
models   数据实体类

utils    工具类
        AddressableActivity                     定义Activity跳转的class路径(用于模块化界面跳转)

vo  核心组件
        
        NetworkBoundResource                                 

widget  自定义控件

#### 二、使用说明

   1.新建工程并引入core module, 并在app模块中添加AppModule、App、ActivityLauncherHelper三个文件        

   1.1 AppModule 初始化依赖项 
   
        val apiModule = module {
            single {
               get<Retrofit>().create(APIService::class.java)
            }
        }
        val splashModule = module {
              factory { SplashRepository(get(), get()) }
              viewModel { SplashViewModel() }   
        }
        val appModule = networkModule + apiModule + splashModule
 
   1.2 App 自定义Application 初始化DI库Koin， 在onCreate中初始化
   
        //init DI
        startKoin {
            logger(AndroidLogger())
            androidContext(this@App.applicationContext)
            modules(appModule)
        }
            
   
   1.3 ActivityLauncherHelper 跳转Router 所有界面的绝对路径定义
         
        object Activities {
            object Login: AddressableActivity {
                override val className: String = "$packageName.LoginActivity"
            }
            object Main: AddressableActivity {
                override val className: String = "$packageName.MainActivity"
            }
            object WebView: AddressableActivity {
                override val className: String = "$packageName.WebViewActivity"
            }
        }

   1.4 新建APIService、APIResponse两个文件，APIResponse定义后台数据的统一返回格式，因为名字可能
   不一样，所以需要使用SerializedName命名为服务器对应的名字， 这里需要注意的是APIService定义请求接口时
   使用的是APIResponse 而不是BaseResponse
        
        data class APIResponse<T>(
            @SerializedName("error") override var code: Any,
            @SerializedName("results") override var data: T
        ): BaseResponse<T>(code, data)
            
   
        interface APIService {
            @GET("today")
            fun getTodayList(): Flowable<APIResponse<TodayResp>>
        }
 
   好的，配置到此已经完成，可以开发具体的业务逻辑了。是不是so easy.
  
   2.CommonAdapter的使用
        
        单个或者多个Type的使用，这里唯一要注意的是，layoutId要和modelType对应，如下面的
        R.layout.listitem_title类型对应的数据类型是String,  R.layout.listitem_feed对应的数据类型是FeedData
        
        private val multiItemAdapter = CommonAdapter(arrayOf(R.layout.listitem_title, R.layout.listitem_feed),
        arrayOf(String::class.java, FeedData::class.java), { itemView, model, _ ->
            when (model) {
                is FeedData -> {
                    itemView.tv_desc.text = model.desc
                    itemView.tv_type.text = model.type
                    itemView.tv_date.text = model.publishedAt
                }
                is String -> itemView.tv_title.text = model
            }
        }, { model, _ ->
            when (model) {
                is FeedData -> gotoDetail(model.url)
            }
        })     
 
   3.startTo的使用
        
        3.1单纯跳转,不带参数，不结束当前界面
        startTo(Activities.WebView) { }
        
        3.2单纯跳转，带参数，并结束当前界面
        startTo(Activities.WebView, finish = true) {
            putExtra("url", url)
        }
        
        3.3跳转，带参数，带requestCode，并结束当前界面
        startTo(Activities.WebView, 100, true) {
            putExtra("url", url)
        }
   
 [Koin](https://github.com/InsertKoinIO/koin)
 
 [RxBinding](https://github.com/JakeWharton/RxBinding)
 
 [RxJava](https://github.com/ReactiveX/RxJava)
 
 [RxAndroid](https://github.com/ReactiveX/RxAndroid)
 
 [RxKotlin](https://github.com/ReactiveX/RxKotlin)
      
 [Retrofit](https://github.com/square/retrofit)
                 
