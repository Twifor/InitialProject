package com.example.initialproject.model

import com.example.initialproject.view.ItemType

//日报信息类
data class Info(val name: String, val imageName: String, val id: Int, val date: String, val type: ItemType)
