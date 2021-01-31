package com.example.morselight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText userInputText;
    Button translateButton, flashButton;
    TextView morseCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init(); //Initialize buttons and text
    }

    public void init(){
        userInputText = findViewById(R.id.UserInputText);
        translateButton = findViewById(R.id.TranslateButton);
        flashButton = findViewById(R.id.FlashButton);
        morseCode = findViewById(R.id.MorseCode);

        translateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                morseCode.setText(""); //Clear TextView for another translation
                translate();
                flashButton.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, "Translated!", Toast.LENGTH_SHORT).show();
            }
        }); //Translate to Morse code on click

        flashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    flashMorse();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }); //Use flashlight to flash Morse code on click
    }

    //Method to translate text to morse
    public void translate(){
        String userString = userInputText.getText().toString();
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
                        Thread.sleep(500);
                        flashOff();
                        Thread.sleep(500);
                        break;
                    case "-":
                        flashOn();
                        Thread.sleep(1500);
                        flashOff();
                        Thread.sleep(500);
                        break;
                    case "/":
                        Thread.sleep(1000);
                        break;
                    default:
                        Thread.sleep(500);
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

    //Method to cancel current message if need be.
    public void Cancel(){
        //stop everything
        //god i wish i knew
        //like i thing that is like when pressed cancel all functions currently going on
    }
}
