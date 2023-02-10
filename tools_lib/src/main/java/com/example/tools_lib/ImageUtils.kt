package com.funchat.lib_base.utils.picture

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.webp.decoder.WebpDrawable
import com.bumptech.glide.integration.webp.decoder.WebpDrawableTransformation
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.funchat.lib_base.BaseApplication
import com.funchat.lib_base.R
import com.luck.picture.lib.utils.ActivityCompatHelper.isDestroy
import jp.wasabeef.glide.transformations.BlurTransformation
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class ImageUtils {

    companion object {
        fun getBitmapFromView(view: View): Bitmap {
            val bitmap = Bitmap.createBitmap(
                view.width, view.height, Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            view.draw(canvas)
            return bitmap
        }

        /**
         * 保存bitMap成path
         */
        fun saveBitmapToPath(bitmap: Bitmap): String {
            var file: File =
                File("${BaseApplication.context.externalCacheDir}${System.currentTimeMillis()}.png")
            if (file.exists()) {
                file.delete()
            }
            var out: FileOutputStream? = null
            try {
                out = FileOutputStream(file)
                if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)) {
                    out.flush()
                    out.close()
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace();
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return file.absolutePath
        }

        /**
         * 保存bitMap成file
         */
        fun saveBitmapToFile(bitmap: Bitmap): File {
            var file: File =
                File("${BaseApplication.context.externalCacheDir}${System.currentTimeMillis()}.png")
            if (file.exists()) {
                file.delete()
            }
            var out: FileOutputStream? = null
            try {
                out = FileOutputStream(file)
                if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)) {
                    out.flush()
                    out.close()
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace();
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return file
        }

        fun checkContext(context: Context?): Boolean {
            if (context == null) {
                return false
            }
            return if (context is Activity) {
                !isDestroy(context as Activity?)
            } else true
        }

        fun showBlurImage(view: View, res: String, context: Context, placeholderId: Int) {
            if (view.context == null) return
            if (!checkContext(view.context)) {
                return
            }
            Glide.with(context)
                .asBitmap()
                .load(res)
                .apply(
                    RequestOptions()
                        .error(placeholderId)
                        .placeholder(placeholderId)
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .transform(CenterCrop(), BlurTransformation(80))
                )
                .into(object : SimpleTarget<Bitmap?>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap?>?
                    ) {
                        val drawable: Drawable = BitmapDrawable(resource)
                        view.background = drawable
                    }
                })
        }

        fun showImage(view: ImageView, res: Any, context: Context) {
            if (view.context == null) return
            if (!checkContext(view.context)) {
                return
            }
            Glide.with(context)
                .load(res)
                .error(Glide.with(context).load(com.luck.picture.lib.R.drawable.ps_ic_placeholder))
                .into(view)
        }

        fun showWebpImage(view: ImageView, res: Any, context: Context, callback: () -> Unit) {
            if (view.context == null) return
            if (!checkContext(view.context)) {
                return
            }
            val transformation: Transformation<Bitmap> = CenterInside()
            Glide.with(context).load(res).optionalTransform(transformation)
                .optionalTransform(
                    WebpDrawable::class.java,
                    WebpDrawableTransformation(transformation)
                )
                .error(com.luck.picture.lib.R.drawable.ps_ic_placeholder)
                .addListener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        var webpDrawable: WebpDrawable = resource as WebpDrawable
                        webpDrawable.loopCount = 1
                        webpDrawable.registerAnimationCallback(object :
                            Animatable2Compat.AnimationCallback() {
                            override fun onAnimationStart(drawable: Drawable?) {
                                super.onAnimationStart(drawable)
                            }

                            override fun onAnimationEnd(drawable: Drawable?) {
                                super.onAnimationEnd(drawable)
                                callback.invoke()
                            }
                        });
                        return false;
                    }
                }).into(view)
        }

    }
}