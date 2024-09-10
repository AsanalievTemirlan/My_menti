package com.example.mymenti.ui.fragment.Menti

import com.example.mymenti.data.local.model.MentiModel

interface OnClick {
    fun onClick(mentiId: MentiModel)
    fun onLongClick(mentiId: MentiModel)
}