package com.shx.libs.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

/**
 * @Description: BaseMvvmActivity
 * @Author: sihaoxuan
 * @Date: 2026/1/26 下午5:56
 */
abstract class BaseMvvmActivity<VM : ViewModel, VB : ViewBinding> : AppCompatActivity() {

    /**
     * ViewBinding实例
     */
    protected lateinit var mBinding: VB

    /**
     * ViewModel实例
     */
    protected lateinit var mViewModel: VM

    /**
     * 初始化视图
     */
    abstract fun initView()

    /**
     * 初始化数据
     */
    abstract fun initData()

    /**
     * 初始化观察者
     */
    abstract fun initObserver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = autoCreateViewBinding()
        setContentView(mBinding.root)

        initViewModel()
        initView()
        initData()
        initObserver()
    }


    /**
     * 自动创建ViewBinding（通过反射）
     */
    private fun autoCreateViewBinding(): VB {
        try {
            // 获取泛型类型
            val superClass = javaClass.genericSuperclass
            if (superClass is ParameterizedType) {
                val type = superClass.actualTypeArguments[1]
                if (type is Class<*>) {
                    // 查找inflate方法
                    val method = type.getMethod("inflate", LayoutInflater::class.java)
                    return method.invoke(null, layoutInflater) as VB
                }
            }
            throw IllegalArgumentException("无法获取ViewBinding类型")
        } catch (e: Exception) {
            throw RuntimeException("创建ViewBinding失败，请检查是否使用了正确的泛型参数", e)
        }
    }

    /**
     * 初始化ViewModel
     */
    private fun initViewModel() {
        mViewModel = ViewModelProvider(this)[vmClass]
    }

    /**
     * ViewModel的Class类型
     */
    private val vmClass: Class<VM> get() {
        val type = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]
        return type as Class<VM>
    }

}