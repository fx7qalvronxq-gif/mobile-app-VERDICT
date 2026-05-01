package kz.example.verdict.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import androidx.core.content.FileProvider;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ImageHelper {

    public static Uri createImageUri(Context context) {
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        try {
            File imageFile = File.createTempFile("IMG_" + timeStamp, ".jpg", storageDir);
            return FileProvider.getUriForFile(
                context,
                context.getPackageName() + ".fileprovider",
                imageFile
            );
        } catch (IOException e) {
            return null;
        }
    }
}
