package com.example.qrcode;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

import static java.lang.System.out;

public class MainActivity extends AppCompatActivity {

    String TAG="GenerateQrCode";
    EditText edittext;
    ImageView qrimg;
    Button start,Save;
    String inputvalue;
    Bitmap bitmap,bitmap2;
    QRGEncoder qrgEncoder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        qrimg = findViewById(R.id.qrcode);
        edittext = findViewById(R.id.edittext);
        start = findViewById(R.id.createButton);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputvalue = edittext.getText().toString().trim();
                if(inputvalue.length()>0){
                    WindowManager manager = (WindowManager)getSystemService(WINDOW_SERVICE);
                    Display display = manager.getDefaultDisplay();
                    Point point = new Point();
                    display.getSize(point);
                    int width = point.x;
                    int height = point.y;
                    int smallerdimension = width<height ? width:height;
                    smallerdimension = smallerdimension*3/4;
                    qrgEncoder = new QRGEncoder(inputvalue, null, QRGContents.Type.TEXT,smallerdimension);
                    try{
                        bitmap = qrgEncoder.encodeAsBitmap();

                        qrimg.setImageBitmap(bitmap);



                    }catch (WriterException e){
                        Log.v(TAG,e.toString());
                    }

                }else{
                    edittext.setError("Required");
                }
            }
        });

        Save = findViewById(R.id.save);
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //Bitmap drawable = ((BitmapDrawable)qrimg.getDrawable()).getBitmap();
                bitmap = BitmapFactory.decodeResource(getResources(),
                        R.id.qrcode);

                qrimg.setImageBitmap(bitmap);


                File filepath = Environment.getExternalStorageDirectory();
                File dir = new File(filepath.getAbsolutePath()+"/Demo/");
                dir.mkdirs();
                File file = new File(dir,"myimage.png");
                Toast.makeText(MainActivity.this, "create file", Toast.LENGTH_LONG).show();


                try {
                    FileOutputStream outputStream = new FileOutputStream(file);

                    bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);


                    outputStream.flush();
                    outputStream.close();
                    Toast.makeText(MainActivity.this, "ok", Toast.LENGTH_SHORT).show();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

           //bitmap = ((BitmapDrawable) imgPreview.getDrawable()).getBitmap();
        });
    }
}
//Toast.makeText(MainActivity.this, "ok", Toast.LENGTH_SHORT).show();
// bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
//
//
//                    outputStream.flush();
//                    outputStream.close();