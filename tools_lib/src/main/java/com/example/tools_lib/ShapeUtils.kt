package com.funchat.lib_base.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import com.funchat.lib_base.utils.picture.ImageUtils
import com.funchat.lib_base.utils.text.SharePlatform
import java.io.File

/**
 * 分享工具类
 */
class ShapeUtils {
    companion object {
        /**
         * 分享更多（文字链接）
         */
        fun shareTextToMore(context: Context, shareContent: String?) {
            val intent = Intent()
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            intent.action = Intent.ACTION_SEND
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_GRANT_READ_URI_PERMISSION
            intent.type = SharePlatform.TEXT_TYPE
            intent.putExtra(Intent.EXTRA_TEXT, shareContent)
            intent.putExtra("Kdescription", shareContent)
            context.startActivity(Intent.createChooser(intent, SharePlatform.SHARE_TITLE))
        }

        /**
         * 分享更多（图片）
         */
        /**
         * 分享更多
         */
        fun shareImageToMore(context: Context, bitmap: Bitmap?, imageDes: String?) {
            if (bitmap != null) {
                val intent = Intent()
                intent.addCategory(Intent.CATEGORY_DEFAULT)
                intent.action = Intent.ACTION_SEND
                intent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_GRANT_READ_URI_PERMISSION
                val file: File = ImageUtils.saveBitmapToFile(bitmap)
                val uri = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
                    FileProvider.getUriForFile(context, "com.maiz.tangdoo.fileprovider", file)
                } else {
                    Uri.fromFile(file)
                }
                intent.type = SharePlatform.IMAGE_TYPE
                intent.putExtra(Intent.EXTRA_STREAM, uri)
                intent.putExtra("Kdescription", imageDes)
                context.startActivity(Intent.createChooser(intent, SharePlatform.SHARE_TITLE))
            }
        }
    }
}