package com.shx.libs.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

/**
 * @Description: BaseMvvmFragment
 * @Author: sihaoxuan
 * @Date: 2026/1/26 下午5:56
 */
abstract class BaseMvvmFragment<VM : ViewModel, VB : ViewBinding> : Fragment() {

    /**
     * ViewModel实例
     */
    protected lateinit var mViewModel: VM

    /**
     * ViewBinding实例
     */
    protected lateinit var mBinding: VB

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initView()
        initData()
        initObserver()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = createBinding(inflater, container)
        return mBinding.root
    }


    private fun createBinding(inflater: LayoutInflater, container: ViewGroup?): VB {
        val vbClass = (javaClass.genericSuperclass as ParameterizedType)
            .actualTypeArguments[1] as Class<VB>

        val inflateMethod = vbClass.getDeclaredMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )

        return inflateMethod.invoke(null, inflater, container, false) as VB
    }

    /**
     * 初始化ViewModel
     */
    private fun initViewModel() {
        val type = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VM>
        mViewModel = ViewModelProvider(this)[type]
    }

}