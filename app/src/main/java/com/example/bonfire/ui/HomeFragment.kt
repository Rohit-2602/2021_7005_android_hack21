package com.example.bonfire.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bonfire.Adapters.allfrnds_adapter
import com.example.bonfire.Adapters.onlinefrnds_adapter
import com.example.bonfire.R
import com.example.bonfire.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class HomeFragment: Fragment(R.layout.fragment_home) {

    private var _binding : FragmentHomeBinding ?= null
    private val binding get() = _binding!!
    private var onlineAdapter: onlinefrnds_adapter? = null
    private var names: ArrayList<String>? = null
    private var images: ArrayList<String>? = null
    private var allfrnames: ArrayList<String>? = null
    private var allfrimages: ArrayList<String>? = null

    private var AllfrAdapter: com.example.bonfire.Adapters.allfrnds_adapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        initUI()
        initOnlineRv()
        initallfriendsRv()
    }

    private fun initallfriendsRv() {
        allfrimages = ArrayList()
        allfrnames = ArrayList()
        allfrimages!!.add("https://yt3.ggpht.com/ytc/AAUvwngR1996M-dLiW4w5fY-qaoHTI8YcMznHPuD49p-=s900-c-k-c0x00ffffff-no-rj")
        allfrimages!!.add("https://static1-www.millenium.gg/articles/9/57/69/@/62373-1145646-mitro-orig-1-article_image_t-1.jpg")
        allfrimages!!.add("https://a.espncdn.com/photo/2018/0917/r432464_1600x800cc.jpg")
        allfrimages!!.add("https://static.invenglobal.com/upload/image/2019/01/29/o1548790443179250.png")
        allfrimages!!.add("https://static.wikia.nocookie.net/fortnite_esports_gamepedia_en/images/7/70/Letshe.png/revision/latest?cb=20200108170108")
        allfrimages!!.add("https://esportspedia-streamers.s3.amazonaws.com/9/97/Chap.png")
        allfrimages!!.add("https://staticg.sportskeeda.com/editor/2021/04/0d886-16181206637148-800.jpg")
        allfrnames?.add("Mongraal")
        allfrnames?.add("Mitro")
        allfrnames?.add("Ninja")
        allfrnames!!.add("Ghost Aydan")
        allfrnames!!.add("Letshe")
        allfrnames!!.add("Chap")
        allfrnames!!.add("Tenz")
        AllfrAdapter = allfrnds_adapter(context, allfrimages, allfrnames)
        rvAllFriends.layoutManager=LinearLayoutManager(context,LinearLayoutManager.VERTICAL,true)
        rvAllFriends.adapter=AllfrAdapter

    }

    private fun initOnlineRv() {
        names = ArrayList()
        images = ArrayList()
        names!!.add("Lara")
        names!!.add("Mongraal")
        names!!.add("Mitro")
        names!!.add("Ninja")
        images!!.add("https://i.pinimg.com/564x/7c/32/31/7c32313848b48fb9a564cfa312999c84.jpg")
        images!!.add("https://yt3.ggpht.com/ytc/AAUvwngR1996M-dLiW4w5fY-qaoHTI8YcMznHPuD49p-=s900-c-k-c0x00ffffff-no-rj")
        images!!.add("https://static1-www.millenium.gg/articles/9/57/69/@/62373-1145646-mitro-orig-1-article_image_t-1.jpg")
        images!!.add("https://a.espncdn.com/photo/2018/0917/r432464_1600x800cc.jpg")
        onlineAdapter = onlinefrnds_adapter(activity, images, names)
        rvOnlineUsers.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,true)
        rvOnlineUsers.adapter=onlineAdapter

    }

    private fun initUI() {

       // _binding?.rvOnlineUsers?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}


