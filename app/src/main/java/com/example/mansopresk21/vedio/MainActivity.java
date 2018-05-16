package com.example.mansopresk21.vedio;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {
    ImageView videos;

    VideoView videoView;
    Button captureVideoButton,playVideoButton,captureWithoutDataVideoButton;
    Uri videoFileUri;




    public static int VIDEO_CAPTURED = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


         captureVideoButton = (Button) this.findViewById(R.id.CaptureVideoButton);
        playVideoButton = (Button) this.findViewById(R.id.PlayVideoButton);
        captureWithoutDataVideoButton = (Button) this.findViewById(R.id.CaptureVideoWithoutDataButton);
        videoView = (VideoView) this.findViewById(R.id.VideoView);
        videos=(ImageView)findViewById(R.id.videos);

        //Creating MediaController
        final MediaController mediaController= new MediaController(this);
        mediaController.setAnchorView(videoView);



        playVideoButton.setEnabled(false);
        videos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this,VideoList.class);
                startActivity(it);

            }
        });
        captureVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent captureVideoIntent =new Intent(android.provider.MediaStore.ACTION_VIDEO_CAPTURE);
                startActivityForResult(captureVideoIntent,VIDEO_CAPTURED);

            }
        });
        captureWithoutDataVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playVideoButton.setEnabled(false);
                Intent captureVideoIntent =new Intent(android.provider.MediaStore.ACTION_VIDEO_CAPTURE);
                startActivity(captureVideoIntent);

            }
        });
        playVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                videoView.setMediaController(mediaController);
                videoView.setVideoURI(videoFileUri);
                videoView.start();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == VIDEO_CAPTURED) {
            videoFileUri = data.getData();
            playVideoButton.setEnabled(true);
        }
    }
}
