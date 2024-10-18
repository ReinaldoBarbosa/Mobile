package com.example.uniaovoluntaria;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Util {

    public static long isCacheAvailable(Context context){
        File dir  = context.getCacheDir();
        if (dir.exists())
            return dir.getUsableSpace();
        else
            return 0L;
    }

    public static String showCacheInfo(Context context){
        StringBuilder stringBuilder = new StringBuilder();
        File dir  = context.getCacheDir();
        if (dir.exists()) {
            String[] lista = dir.list();
            stringBuilder.append("\n Quantidade de diretórios em cache: " +
                        String.valueOf(lista.length));
            if(lista.length > 0){
                stringBuilder.append("\n Nome do diretório em cache: " +String.valueOf(lista[0]));
                stringBuilder.append("\n Espaço total em cache: " + String.valueOf(dir.getTotalSpace()));
                stringBuilder.append("\n Espaço disponível em cache: " + String.valueOf(dir.getFreeSpace()));
                stringBuilder.append("\n Espaço utilizado pelo cache: " + String.valueOf(dir.getUsableSpace()));
            } else {
                stringBuilder.append("\n Cache apagado.");
            }

        }

        return stringBuilder.toString();
    }

    public static boolean deleteCacheFile(Context context){
        File dir  = context.getCacheDir();
        if (dir.exists()) {
            String[] lista = dir.list();

            if (lista != null){
                int i = 0;
                for(String itemLista : lista){
                    File cacheFile = new File(context.getCacheDir(), String.valueOf(lista[i]));
                    try{
                        long espacoUsado = cacheFile.getUsableSpace();
                        deleteRecursive(cacheFile);
                        long espacoAtual = cacheFile.getUsableSpace();
                        Log.d("CACHE: ", String.valueOf(espacoUsado - espacoAtual));
                        i = i + 1;
                    } catch(Exception e){
                        Log.e("CACHE: ", e.getMessage());
                    }
                }
                return true;
            }
            return false;
        }
        else
            return false;
    }

    public static boolean deleteRecursive(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
                deleteRecursive(child);

        Log.d("CACHE", "Deleting: " + fileOrDirectory);
        return fileOrDirectory.delete();
    }

    /**
     * método de conversão de um objeto byte[] (array de bytes) em Bitmap
     * @param foto
     * @return
     */
    public static Bitmap converterByteToBipmap(byte[] foto) {
        Bitmap bmp = null;
        Bitmap bitmapReduzido = null;
        byte[] x = foto;

        try {
            bmp = BitmapFactory.decodeByteArray(x, 0, x.length);

            bitmapReduzido = Bitmap.createScaledBitmap(bmp, 1080, 1000, true);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmapReduzido;
    }

    // https://www.it-swarm.dev/pt/android/compartilhar-imagem-e-texto-atraves-do-whatsapp-ou-facebook/1046274258/

    public static @Nullable
    Uri getUriFromFile(Context context, @Nullable File file) {
        if (file == null)
            return null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                return FileProvider.getUriForFile(context, "br.com.visualmix.av.vm_av_mobile_app.fileprovider", file);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return Uri.fromFile(file);
        }
    }

    // Returns the URI path to the Bitmap displayed in specified ImageView
    public static Uri getLocalBitmapUri(Context context, ImageView imageView) {
        Drawable drawable = imageView.getDrawable();
        Bitmap bmp = null;
        if (drawable instanceof BitmapDrawable) {
            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } else {
            return null;
        }
        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            // Use methods on Context to access package-specific directories on external storage.
            // This way, you don't need to request external read/write permission.
            File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                    "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();

            bmpUri = getUriFromFile(context, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

    public static Uri getBytesUri(Context context, byte[] foto) {
        Bitmap bmp = null;
        Bitmap bitmapReduzido = null;
        byte[] x = foto;

        try {
            bmp = BitmapFactory.decodeByteArray(x, 0, x.length);

            bitmapReduzido = Bitmap.createScaledBitmap(bmp, 1080, 1000, true);


        } catch (Exception e) {
            e.printStackTrace();
        }

        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            // Use methods on Context to access package-specific directories on external storage.
            // This way, you don't need to request external read/write permission.
            File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                    "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bitmapReduzido.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();

            bmpUri = getUriFromFile(context, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

    public static void temInternetWiFi(Context context){
        ConnectivityManager cm =
                (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if(isConnected==true){
            boolean isWiFi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
            if(isWiFi == false){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Wi-Fi Indisponível!");
                alertDialogBuilder
                        .setMessage("Este aplicativo necessita de conexão com a rede local!")
                        .setCancelable(true);
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        }else{
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle("Conexão Indisponível!");
            alertDialogBuilder
                    .setMessage("Este aplicativo necessita de conexão com a rede local!")
                    .setCancelable(true);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    // Converter Drawable para Bitmap
    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static byte[] bitmapToBytes(Bitmap bitmap){
        byte[] imagemEnviada = null;

        if(bitmap != null){
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            imagemEnviada = stream.toByteArray();
        }
        return imagemEnviada;
    }

}
