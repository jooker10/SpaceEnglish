package edu.SpaceLearning.SpaceEnglish.Broadcast;



import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkChangeReceiver extends BroadcastReceiver {

        public interface NetworkChangeListener {
            void onNetworkChange(boolean isConnected);
        }

        private NetworkChangeListener listener;

        public NetworkChangeReceiver(NetworkChangeListener listener) {
            this.listener = listener;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
            if (listener != null) {
                listener.onNetworkChange(isConnected);
            }
        }

    }