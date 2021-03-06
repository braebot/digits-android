/*
 * Copyright (C) 2015 Twitter, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.app;

import android.app.Application;
import android.os.StrictMode;
import android.util.Log;

import io.fabric.sdk.android.DefaultLogger;
import io.fabric.sdk.android.Fabric;

import com.digits.sdk.android.Digits;
import com.squareup.leakcanary.LeakCanary;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

public class SampleApplication extends Application {
    private static final String TAG = SampleApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);

        Log.d(TAG, "Setting up StrictMode policy checking.");
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build());

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build());

        final TwitterAuthConfig authConfig = new TwitterAuthConfig(BuildConfig.CONSUMER_KEY,
                BuildConfig.CONSUMER_SECRET);

        final Fabric fabric = new Fabric.Builder(this)
                .kits(new Digits(), new TwitterCore(authConfig))
                .logger(new DefaultLogger(Log.DEBUG))
                .debuggable(true)
                .build();

        Fabric.with(fabric);
    }
}
