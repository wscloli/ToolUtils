package com.example.tools_lib

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*

class Utils {

    companion object {
        /**
         * MD5加密
         *
         * @param string
         * @return
         */
        fun md5(text: String): String {
            try {
                //获取md5加密对象
                val instance: MessageDigest = MessageDigest.getInstance("MD5")
                //对字符串加密，返回字节数组
                val digest: ByteArray = instance.digest(text.toByteArray())
                var sb: StringBuffer = StringBuffer()
                for (b in digest) {
                    //获取低八位有效值
                    var i: Int = b.toInt() and 0xff
                    //将整数转化为16进制
                    var hexString = Integer.toHexString(i)
                    if (hexString.length < 2) {
                        //如果是一位的话，补0
                        hexString = "0" + hexString
                    }
                    sb.append(hexString)
                }
                return sb.toString()

            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            }

            return ""
        }

        /**
         * 设置输入法展现和关闭
         *
         * @param bShow
         * @param context
         * @param view
         */
        fun setInputShow(bShow: Boolean, context: Context, view: View) {
            if (bShow) {
                view.requestFocus()
                val imm =
                    context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(view, 0)
            } else {
                val imm =
                    context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }

        /**
         * Timestamp to String
         * @param Timestamp
         * @return String
         */
        fun timeToStr(time: String): String {
            try {
                if (time != "0") {
                    return SimpleDateFormat("yyyy-MM-dd").format(time.toLong() * 1000L)
                }
            } catch (e: Exception) {
            }
            return ""
        }

        /**
         * String to Timestamp
         * @param String
         * @return Timestamp
         */
        fun strToTimeStamp(date: String): Long {
            return SimpleDateFormat("yyyy-MM-dd").parse(date, ParsePosition(0)).time
        }

        /**
         * 重启App
         */
/*        fun rebootApp() {
            val intent: Intent? =
                BaseApplication.context.packageManager.getLaunchIntentForPackage(BaseApplication.context.packageName)
            intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent?.putExtra("REBOOT", "reboot")
            BaseApplication.context.startActivity(intent)
            android.os.Process.killProcess(android.os.Process.myPid())
            System.exit(0)
        }*/

        fun getChinese(originStr: String): String {
            return try {
                originStr.replace("[^\u4E00-\u9FA5]".toRegex(), "")
            } catch (e: Exception) {
                ""
            }
        }

        /**
         * 处理调用接口回参中的特殊字符
         * @param str
         * @return
         */
        fun replaceAll(str: String): String? {
            var replaceAll3: String? = null
            if (str.isNotEmpty()) {
                val replaceAll = str.replace("\\\\".toRegex(), "")
                println(replaceAll)
                val replaceAll2 = replaceAll.replace("\"[{]".toRegex(), "{")
                replaceAll3 = replaceAll2.replace("[}]\"".toRegex(), "}")
            }
            return replaceAll3
        }

        /**]
         * 跳转谷歌商店更新
         */
        fun jumpToGooglePlay(c: Context) {
            try {
                val i = Intent(Intent.ACTION_VIEW)
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                i.data =
                    Uri.parse("https://play.google.com/store/apps/details?id=" + c.getPackageName())
                c.startActivity(i)
            } catch (e: Exception) {
                Log.e("TAG", "jumpToGooglePlay: ---失败")
            }
        }
    }


}