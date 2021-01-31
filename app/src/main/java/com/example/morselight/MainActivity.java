package com.example.morselight;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;

import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    EditText userInputText;
    Button translateButton, flashButton, uploadImage, btnToggleDark;
    TextView morseCode;
    Bitmap bitmap = null;
    public static final int GET_FROM_GALLERY = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init(); //Initialize buttons and text
    }


    public void init(){
        userInputText = findViewById(R.id.UserInputText);
        translateButton = findViewById(R.id.TranslateButton);
        uploadImage = findViewById(R.id.UploadButton);
        flashButton = findViewById(R.id.FlashButton);
        morseCode = findViewById(R.id.MorseCode);
        btnToggleDark = findViewById(R.id.btnToggleDark);


        //Dark Mode
        btnToggleDark.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    }
                });


        //Translate to Morse code on click
        translateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                morseCode.setText(""); //Clear TextView for another translation
                translate();
                flashButton.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, "Translated!", Toast.LENGTH_SHORT).show();
            }
        });

        //Use flashlight to flash Morse code on click
        flashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    flashMorse();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        //Button to upload image and get text from the image
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
            }
        });
    }

    //Method to translate text to morse
    public void translate(){
        String userString = userInputText.getText().toString();
        //Loop through message and translate
        for (int n = 0; n < userString.length(); n++){
            switch (userString.charAt(n)) {
                case ' ' :
                    morseCode.append("/ ");
                    break;
                case 'a' : case 'A':
                    morseCode.append(".- ");
                    break;
                case 'b' : case 'B':
                    morseCode.append("-... ");
                    break;
                case 'c' : case 'C':
                    morseCode.append("-.-. ");
                    break;
                case 'd' : case 'D':
                    morseCode.append("-.. ");
                    break;
                case 'e' : case 'E':
                    morseCode.append(". ");
                    break;
                case 'f' : case 'F':
                    morseCode.append("..-. ");
                    break;
                case 'g' : case 'G':
                    morseCode.append("--. ");
                    break;
                case 'h' : case 'H':
                    morseCode.append(".... ");
                    break;
                case 'i' : case 'I':
                    morseCode.append(".. ");
                    break;
                case 'j' : case 'J':
                    morseCode.append(".--- ");
                    break;
                case 'k' : case 'K':
                    morseCode.append("-.- ");
                    break;
                case 'l' : case 'L':
                    morseCode.append(".-.. ");
                    break;
                case 'm' : case 'M':
                    morseCode.append("-- ");
                    break;
                case 'n' : case 'N':
                    morseCode.append("-. ");
                    break;
                case 'o' : case 'O':
                    morseCode.append("--- ");
                    break;
                case 'p' : case 'P':
                    morseCode.append(".--. ");
                    break;
                case 'q' : case 'Q':
                    morseCode.append("--.- ");
                    break;
                case 'r' : case 'R':
                    morseCode.append(".-. ");
                    break;
                case 's' : case 'S':
                    morseCode.append("... ");
                    break;
                case 't' : case 'T':
                    morseCode.append("- ");
                    break;
                case 'u' : case 'U':
                    morseCode.append("..- ");
                    break;
                case 'v' : case 'V':
                    morseCode.append("...- ");
                    break;
                case 'w' : case 'W':
                    morseCode.append(".-- ");
                    break;
                case 'x' : case 'X':
                    morseCode.append("-..- ");
                    break;
                case 'y' : case 'Y':
                    morseCode.append("-.-- ");
                    break;
                case 'z' : case 'Z':
                    morseCode.append("--.. ");
                    break;
                case '1' :
                    morseCode.append(".---- ");
                    break;
                case '2' :
                    morseCode.append("..--- ");
                    break;
                case '3' :
                    morseCode.append("...-- ");
                    break;
                case '4' :
                    morseCode.append("....- ");
                    break;
                case '5' :
                    morseCode.append("..... ");
                    break;
                case '6' :
                    morseCode.append("-.... ");
                    break;
                case '7' :
                    morseCode.append("--... ");
                    break;
                case '8' :
                    morseCode.append("---.. ");
                    break;
                case '9' :
                    morseCode.append("----. ");
                    break;
                case '0' :
                    morseCode.append("----- ");
                    break;
                case '.' :
                    morseCode.append(".-.-.- ");
                    break;
                case ',' :
                    morseCode.append("--..-- ");
                    break;
                case '?' :
                    morseCode.append("..--.. ");
                    break;
                case '!' :
                    morseCode.append("-.-.-- ");
                    break;
                case '"' :
                    morseCode.append(".-..-. ");
                    break;
                case '\'' :
                    morseCode.append(".----. ");
                    break;
                case '(' :
                    morseCode.append("-.--. ");
                    break;
                case ')' :
                    morseCode.append("-.--.- ");
                    break;
                case '&' :
                    morseCode.append(".-... ");
                    break;
                case ':' :
                    morseCode.append("---... ");
                    break;
                case ';' :
                    morseCode.append("-.-.-. ");
                    break;
                case '/' :
                    morseCode.append("-..-. ");
                    break;
                case '_' :
                    morseCode.append("..--.- ");
                    break;
                case '=' :
                    morseCode.append("-...- ");
                    break;
                case '+' :
                    morseCode.append(".-.-. ");
                    break;
                case '-' :
                    morseCode.append("-....- ");
                    break;
                case '$' :
                    morseCode.append("...-..- ");
                    break;
                case '@' :
                    morseCode.append(".--.-. ");
                    break;
            }//end switch
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Detect the requested codes
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            try{
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        imageToText(); //Run method to detect text from image
    }

    public void imageToText(){
        InputImage image = InputImage.fromBitmap(bitmap, 0);
        TextRecognizer recognizer = TextRecognition.getClient();
        Task<Text> result = recognizer.process(image).addOnSuccessListener(new OnSuccessListener<Text>() {
            @Override
            public void onSuccess(Text visionText) {
                //Task is completed successfully, run processTextBlock method
                processTextBlock(visionText);
            }
        }) .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Task has failed with an exception
                e.printStackTrace();
            }
        });
    }

    //Method to process the text to a string variable
    private void processTextBlock(Text result){
        String resultText = result.getText();
        for (Text.TextBlock block : result.getTextBlocks()) {
            String blockText = block.getText();
            Point[] blockCornerPoints = block.getCornerPoints();
            Rect blockFrame = block.getBoundingBox();
            for (Text.Line line : block.getLines()) {
                String lineText = line.getText();
                Point[] lineCornerPoints = line.getCornerPoints();
                Rect lineFrame = line.getBoundingBox();
                for (Text.Element element : line.getElements()) {
                    String elementText = element.getText();
                    Point[] elementCornerPoints = element.getCornerPoints();
                    Rect elementFrame = element.getBoundingBox();
                }
            }
        }
        //Set the userInputText field to text that is detected from image
        userInputText.setText(resultText);
    }

    //Method to flash Morse code
    public void flashMorse() throws InterruptedException {
        if (morseCode.getText().toString().isEmpty()) {
            Toast.makeText(MainActivity.this, "No Morse Code to Translate", Toast.LENGTH_SHORT).show();
        } else {
            String morse = morseCode.getText().toString();
            for (int n = 0; n < morse.length(); n++) {
                String m = String.valueOf(morse.charAt(n));
                switch (m) {
                    case ".":
                        flashOn();
                        Thread.sleep(250);
                        flashOff();
                        Thread.sleep(250);
                        break;
                    case "-":
                        flashOn();
                        Thread.sleep(750);
                        flashOff();
                        Thread.sleep(250);
                        break;
                    case "/":
                        Thread.sleep(500);
                        break;
                    default:
                        Thread.sleep(250);
                        break;
                }
            }
        }
    }

    //Method to turn get access and turn on flash
    public void flashOn(){
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)){
            try{
                CameraManager camera = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                if (camera != null){
                    camera.setTorchMode("0", true);
                }
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
    }

    //Method to turn get access and turn off flash
    public void flashOff(){
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)){
            try{
                CameraManager camera = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                if (camera != null){
                    camera.setTorchMode("0", false);
                }
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
