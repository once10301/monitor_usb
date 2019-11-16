package com.ly.monitor_usb;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** MonitorUsbPlugin */
public class MonitorUsbPlugin implements FlutterPlugin {

  private MethodChannel methodChannel;
  private EventChannel eventChannel;

  /** Plugin registration. */
  public static void registerWith(Registrar registrar) {
    MonitorUsbPlugin plugin = new MonitorUsbPlugin();
    plugin.setupChannels(registrar.messenger(), registrar.context());
  }

  @Override
  public void onAttachedToEngine(FlutterPluginBinding binding) {
    setupChannels(binding.getFlutterEngine().getDartExecutor(), binding.getApplicationContext());
  }

  @Override
  public void onDetachedFromEngine(FlutterPluginBinding binding) {
    teardownChannels();
  }

  private void setupChannels(BinaryMessenger messenger, Context context) {
    methodChannel = new MethodChannel(messenger, "monitor_usb");
    eventChannel = new EventChannel(messenger, "monitor_usb_status");
    MonitorUsbBroadcastReceiver receiver = new MonitorUsbBroadcastReceiver(context);
    eventChannel.setStreamHandler(receiver);
  }

  private void teardownChannels() {
    methodChannel.setMethodCallHandler(null);
    eventChannel.setStreamHandler(null);
    methodChannel = null;
    eventChannel = null;
  }
}
