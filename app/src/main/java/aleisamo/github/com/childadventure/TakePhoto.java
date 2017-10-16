package aleisamo.github.com.childadventure;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TakePhoto extends AppCompatActivity {

    private static final int REQUEST_CAMERA_PERMISSION = 1 ;
    private Size previewSize;
    private Size jpegSizes[]=null;
    private CameraDevice cameraDevice;
    private CaptureRequest.Builder buildPreview;
    private CameraCaptureSession previewSession;

    private static final SparseIntArray ORIENTATIONS =
            new SparseIntArray();
    static {
        ORIENTATIONS.append(Surface.ROTATION_0,90);
        ORIENTATIONS.append(Surface.ROTATION_90,0);
        ORIENTATIONS.append(Surface.ROTATION_180,270);
        ORIENTATIONS.append(Surface.ROTATION_270,180);
    }

    @BindView (R.id.camera_preview)
    TextureView preview;
    @BindView(R.id.camera)
    Button openCamera;
    private File file;
    private HandlerThread backgroundThread;
    private Handler backgroundHandler;
    private String cameraId;
    private ImageReader imageReader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);
        ButterKnife.bind(this);
        openCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPhoto();
            }
        });

    }

    TextureView.SurfaceTextureListener textureListener =
            new TextureView.SurfaceTextureListener() {
                @Override
                public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                    openDevice();
                }

                @Override
                public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

                }

                @Override
                public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                    return false;
                }

                @Override
                public void onSurfaceTextureUpdated(SurfaceTexture surface) {

                }
            };

            private final CameraDevice.StateCallback stateCallback =
                    new CameraDevice.StateCallback() {
                        @Override
                        public void onOpened(CameraDevice camera) {
                            cameraDevice = camera;
                            createPreview();
                        }

                        @Override
                        public void onDisconnected(CameraDevice camera) {
                            cameraDevice.close();

                        }

                        @Override
                        public void onError(CameraDevice camera, int error){
                            cameraDevice.close();
                            cameraDevice = null;
                        }
                    };

                    final  CameraCaptureSession.CaptureCallback captureCallbackListener =
                            new CameraCaptureSession.CaptureCallback(){
                                @Override
                                public void onCaptureCompleted(CameraCaptureSession session,
                                                               CaptureRequest request, 
                                                               TotalCaptureResult result) 
                                {
                                    super.onCaptureCompleted(session, request, result);
                                    createPreview();
                                }
                            };
                            protected void thread() {
                                backgroundThread = new HandlerThread("camera Background");
                                backgroundThread.start();
                                backgroundHandler = new Handler(backgroundThread.getLooper());
                            }
                            protected void stopThread(){
                                backgroundThread.quitSafely();
                                try {
                                    backgroundThread.join();
                                    backgroundThread = null;
                                    backgroundHandler = null;

                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }










    public void getPhoto() {
        // check device
        if (cameraDevice == null){
            return;
        }
        // get all the cameras available the device including and id

        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try{
            CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(
                    cameraDevice.getId());
            if (cameraCharacteristics != null){

                jpegSizes = cameraCharacteristics.get(CameraCharacteristics
                        .SCALER_STREAM_CONFIGURATION_MAP).getOutputSizes(ImageFormat.JPEG);

            }
            int width = 640;
            int height = 480;
            if (jpegSizes != null && jpegSizes.length > 0){
                width = jpegSizes[0].getWidth();
                height = jpegSizes[0].getHeight();
            }

            imageReader = ImageReader.newInstance(width,height,ImageFormat.JPEG,1);
            List<Surface> outPutSurface = new ArrayList<>(2);
            outPutSurface.add(imageReader.getSurface());
            outPutSurface.add(new Surface(preview.getSurfaceTexture()));
            final CaptureRequest.Builder captureBuilder =
                    cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
            captureBuilder.addTarget(imageReader.getSurface());
            captureBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);

            int rotation = getWindowManager().getDefaultDisplay().getRotation();
            captureBuilder.set(CaptureRequest.JPEG_ORIENTATION,ORIENTATIONS.get(rotation));

            ImageReader.OnImageAvailableListener imageListener =
                    new ImageReader.OnImageAvailableListener() {
                        @Override
                        public void onImageAvailable(ImageReader reader) {
                            Image image = null;
                            try {
                                image = reader.acquireLatestImage();
                                ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                                byte[] bytes = new byte [buffer.capacity()];
                                buffer.get(bytes);
                                save(bytes);
                            } catch (IOException e) {
                                e.printStackTrace();
                            } finally {
                                if(image != null)
                                    image.close();
                            }
                        }
                        private void save (byte[]bytes) throws IOException{
                            OutputStream output = null;
                            try{
                                output = new FileOutputStream(file);
                                output.write(bytes);
                            }finally {
                                if (output != null)
                                    output.close();
                            }

                        }
                    };

                    imageReader.setOnImageAvailableListener(imageListener,backgroundHandler);
            final  CameraCaptureSession.CaptureCallback captureListener =
                    new CameraCaptureSession.CaptureCallback(){
                        @Override
                        public void onCaptureCompleted(CameraCaptureSession session,
                                                       CaptureRequest request,
                                                       TotalCaptureResult result)
                        {
                            super.onCaptureCompleted(session, request, result);
                            createPreview();
                        }
                    };

                    cameraDevice.createCaptureSession(outPutSurface, new CameraCaptureSession.StateCallback() {
                        @Override
                        public void onConfigured(CameraCaptureSession session) {
                            try{
                                session.capture(captureBuilder.build(),
                                        captureListener,backgroundHandler);
                            } catch (CameraAccessException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onConfigureFailed(@NonNull CameraCaptureSession session) {

                        }
                    },backgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

    }

    protected void createPreview() {
        try {
            SurfaceTexture surfaceTexture = preview.getSurfaceTexture();
            assert surfaceTexture != null;
            surfaceTexture.setDefaultBufferSize(previewSize.getWidth(), previewSize.getHeight());
            Surface surface = new Surface(surfaceTexture);
            buildPreview = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            buildPreview.addTarget(surface);
            cameraDevice.createCaptureSession(Arrays.asList(surface), new CameraCaptureSession.StateCallback(){
                @Override
                public void onConfigured(CameraCaptureSession cameraCaptureSession) {
                    //The camera is already closed
                    if (null == cameraDevice) {
                        return;
                    }
                    // When the session is ready, we start displaying the preview.
                    previewSession = cameraCaptureSession;
                    updatePreview();
                }
                @Override
                public void onConfigureFailed(CameraCaptureSession cameraCaptureSession) {
                    Toast.makeText(getApplication(),"Configuration change",Toast.LENGTH_SHORT).show();
                }
            }, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
    private void openDevice() {
        CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            cameraId = manager.getCameraIdList()[0];
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
            StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            assert map != null;
            previewSize = map.getOutputSizes(SurfaceTexture.class)[0];
            // Add permission for camera and let user grant the permission
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CAMERA_PERMISSION);
                return;
            }
            manager.openCamera(cameraId, stateCallback, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        Log.e(getClass().getSimpleName(), "openCamera X");
    }


    protected void updatePreview() {
        if(null == cameraDevice) {

        }
        buildPreview.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
        try {
            previewSession.setRepeatingRequest(buildPreview.build(), null, backgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
    private void closeCamera() {
        if (null != cameraDevice) {
            cameraDevice.close();
            cameraDevice = null;
        }
        if (null != imageReader ) {
            imageReader.close();
            imageReader = null;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                // close the app
                Toast.makeText(getApplication(), "Sorry!!!, you can't use this app without granting permission", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        thread();
        if (preview.isAvailable()) {
            openDevice();
        } else {
            preview.setSurfaceTextureListener(textureListener);
        }
    }
    @Override
    protected void onPause() {
        //closeCamera();
        stopThread();
        super.onPause();
    }

}



