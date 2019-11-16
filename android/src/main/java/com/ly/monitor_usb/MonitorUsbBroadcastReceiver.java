// Copyright 2019 The Chromium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

package com.ly.monitor_usb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbManager;

import io.flutter.plugin.common.EventChannel;

/**
 * The MonitorUsbBroadcastReceiver receives the connectivity updates and send them to the UIThread
 * through an {@link EventChannel.EventSink}
 *
 * <p>Use {@link
 * EventChannel#setStreamHandler(EventChannel.StreamHandler)}
 * to set up the receiver.
 */
class MonitorUsbBroadcastReceiver extends BroadcastReceiver implements EventChannel.StreamHandler {
    private Context context;
    private EventChannel.EventSink events;

    MonitorUsbBroadcastReceiver(Context context) {
        this.context = context;
    }

    @Override
    public void onListen(Object arguments, EventChannel.EventSink events) {
        this.events = events;
        context.registerReceiver(this, new IntentFilter(UsbManager.ACTION_USB_DEVICE_ATTACHED));
        context.registerReceiver(this, new IntentFilter(UsbManager.ACTION_USB_DEVICE_DETACHED));
    }

    @Override
    public void onCancel(Object arguments) {
        context.unregisterReceiver(this);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (events != null) {
            if (UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action)) {
                events.success("pullOut");
            } else if (UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action)) {
                events.success("plugIn");
            }
        }
    }
}
