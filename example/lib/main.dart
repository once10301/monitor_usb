import 'dart:async';

import 'package:flutter/material.dart';
import 'package:monitor_usb/monitor_usb.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String text = 'Unknown';
  StreamSubscription<MonitorUsbResult> subscription;

  @override
  initState() {
    super.initState();
    subscription = MonitorUsb().onMonitorUsbChanged.listen((MonitorUsbResult result) {
      setState(() {
        if (result == MonitorUsbResult.plugIn) {
          text = 'USB插入';
        } else if (result == MonitorUsbResult.pullOut) {
          text = 'USB拔出';
        }
      });
    });
  }

  @override
  dispose() {
    super.dispose();
    subscription.cancel();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Text(text),
        ),
      ),
    );
  }
}
