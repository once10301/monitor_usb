import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

enum MonitorUsbResult { pullOut, plugIn }

class MonitorUsb {
  Stream<MonitorUsbResult> stream;

  @visibleForTesting
  static const MethodChannel methodChannel = MethodChannel('monitor_usb');

  @visibleForTesting
  static const EventChannel eventChannel = EventChannel('monitor_usb_status');

  Stream<MonitorUsbResult> get onMonitorUsbChanged {
    if (stream == null) {
      stream = eventChannel.receiveBroadcastStream().map((dynamic event) => parseMonitorUsbResult(event));
    }
    return stream;
  }

  MonitorUsbResult parseMonitorUsbResult(String state) {
    switch (state) {
      case 'plugIn':
        return MonitorUsbResult.plugIn;
      case 'pullOut':
      default:
        return MonitorUsbResult.pullOut;
    }
  }
}
