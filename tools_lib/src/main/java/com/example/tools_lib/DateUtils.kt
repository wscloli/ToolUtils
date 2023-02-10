package com.example.tools_lib

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

class DateUtils {
    companion object {
        private val msFormat = SimpleDateFormat("mm:ss")

        /**
         * MS turn every minute
         *
         * @param duration Millisecond
         * @return Every minute
         */
        fun timeParse(duration: Long): String? {
            var time: String? = ""
            if (duration > 1000) {
                time = timeParseMinute(duration)
            } else {
                val minute = duration / 60000
                val seconds = duration % 60000
                val second = Math.round(seconds.toFloat() / 1000).toLong()
                if (minute < 10) {
                    time += "0"
                }
                time += "$minute:"
                if (second < 10) {
                    time += "0"
                }
                time += second
            }
            return time
        }

        /**
         * MS turn every minute
         *
         * @param duration Millisecond
         * @return Every minute
         */
        fun timeParseMinute(duration: Long): String? {
            return try {
                msFormat.format(duration)
            } catch (e: Exception) {
                e.printStackTrace()
                "0:00"
            }
        }

        /**
         * 判断两个时间戳相差多少秒
         *
         * @param d
         * @return
         */
        fun dateDiffer(d: Long): Int {
            return try {
                val l1 = System.currentTimeMillis()/1000
                Log.e("DateUtils", "onViewCreated: --------到期时间-${d}")
                Log.e("DateUtils", "onViewCreated: --------当前时间-${l1}")

                val interval = l1 - d
                Math.abs(interval).toInt()
            } catch (e: Exception) {
                e.printStackTrace()
                -1
            }
        }

        @Throws(java.lang.Exception::class)
        fun stampToTime(s: Long): String? {
            val res: String
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val lt: Long = s
            val date = Date(lt)
            res = simpleDateFormat.format(date)
            return res
        }

        /**
         * 判断两个时间戳相差多少秒
         *
         * @param d
         * @return
         */
        fun dateDifferFromLocal(d: Long): Int {
            return try {
                val l1 = System.currentTimeMillis()
                val interval = l1 - d
                Math.abs(interval).toInt()
            } catch (e: Exception) {
                e.printStackTrace()
                -1
            }
        }
/*
        fun dataPar(){
            var fmt : DateFormat = SimpleDateFormat("yyyy-MM-dd")
            String dateBegin=fmt.format(carrierCommand.getDateBegin());

            String dateEnd=fmt.format(carrierCommand.getDateEnd());
        }*/

        /**
         * 根据时间戳判断是否是同一天
         * @param lastTime    上次时间
         * @param currentTime 当前时间
         */
        fun isSameDate(lastTime: Long, currentTime: Long): Boolean {
            val timeInterval: Long = 24 * 60 * 60 * 1000L;
            return lastTime / timeInterval == currentTime / timeInterval
        }

        /**
         * 判断当前时间是否在当天某一时间之前
         */
        fun isAfterForTime(hour: Int): Boolean {
            var currentTime = System.currentTimeMillis()
            var mNowCalendar = Calendar.getInstance()
            mNowCalendar.timeInMillis = currentTime
            var nowHour = mNowCalendar.get(Calendar.HOUR_OF_DAY)
            return nowHour >= hour
        }

    }
}