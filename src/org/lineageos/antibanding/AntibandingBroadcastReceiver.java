/*
 * Copyright (C) 2017 The LineageOS Project
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
 */

package org.lineageos.antibanding;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Country;
import android.location.CountryDetector;
import android.location.CountryListener;
import android.os.Looper;
import android.os.SystemProperties;

public class AntibandingBroadcastReceiver extends BroadcastReceiver {
    public static final String ANTIBANDING_50HZ = "3";
    public static final String ANTIBANDING_60HZ = "4";
    public static final String ANTIBANDING_KEY = "persist.camera.set.afd";

    public static void setAntibanding(String countryIso) {
        switch (countryIso) {
            case "as": // American Samoa
            case "ai": // Anguilla
            case "ag": // Antigua
            case "aw": // Aruba
            case "bs": // Bahamas
            case "bz": // Belize
            case "bm": // Bermuda
            case "br": // Brazil
            case "ca": // Canada
            case "ky": // Cayman Islands
            case "co": // Colombia
            case "cr": // Costa Rica
            case "cu": // Cuba
            case "do": // Dominican Republic
            case "ec": // Ecuador
            case "sv": // El Salvador
            case "gu": // Guam
            case "gt": // Guatemala
            case "gy": // Guyana
            case "ht": // Haiti
            case "hn": // Honduras
            case "lr": // Liberia
            case "mx": // Mexico
            case "fm": // Micronesia
            case "ms": // Montserrat
            case "ni": // Nicaragua
            case "pa": // Panama
            case "pe": // Peru
            case "ph": // Philippines
            case "pr": // Puerto Rico
            case "kn": // Saint Kitts and Nevis
            case "sa": // Saudi Arabia
            case "kr": // South Korea
            case "sr": // Suriname
            case "tw": // Taiwan
            case "tt": // Trinidad and Tobago
            case "us": // United States
            case "vi": // United States Virgin Islands
            case "ve": // Venezuela
                SystemProperties.set(ANTIBANDING_KEY, ANTIBANDING_60HZ);
                return;
            default:
                SystemProperties.set(ANTIBANDING_KEY, ANTIBANDING_50HZ);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            final CountryDetector countryDetector =
                    (CountryDetector) context.getSystemService(Context.COUNTRY_DETECTOR);
            countryDetector.addCountryListener(new CountryListener() {
                @Override
                public void onCountryDetected(Country country) {
                    setAntibanding(country.getCountryIso());
                }
            }, Looper.getMainLooper());

            Country country = countryDetector.detectCountry();
            if (country != null) {
                setAntibanding(country.getCountryIso());
            }
        }
    }
}
