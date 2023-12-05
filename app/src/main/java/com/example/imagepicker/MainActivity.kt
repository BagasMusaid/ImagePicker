package com.example.imagepicker

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import com.karumi.dexter.Dexter
import com.karumi.dexter.DexterBuilder
import com.karumi.dexter.DexterBuilder.MultiPermissionListener
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener

class MainActivity : AppCompatActivity() {
    private  lateinit var imageView: ImageView
    private lateinit var gallery: Button
    private lateinit var camera: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageView = findViewById(R.id.IV)
        gallery = findViewById(R.id.btn_galeri)
        camera = findViewById(R.id.btn_camera)

        gallery.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 1)
        }
        camera.setOnClickListener{
            Dexter.withContext(this).withPermission(android.Manifest.permission.CAMERA)
                .withListener(object : PermissionListener{
                    override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                        val cameraImg = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        startActivityForResult(cameraImg, 2)
                    }
                    override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                        TODO("Not yet implemented")
                    }
                    override fun onPermissionRationaleShouldBeShown(
                        p0: PermissionRequest?,
                        p1: PermissionToken?,
                    ) {
                        p1?.continuePermissionRequest()
                    }
                }).check()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode==1 && resultCode == RESULT_OK){
            imageView.setImageURI(data?.data)
        }
        if(requestCode == 2 && resultCode == RESULT_OK){
            val cameraImg = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(cameraImg)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP

//            var pic : Bitmap? = data?.getParcelableExtra("data")
//            imageView.setImageBitmap(pic)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}



