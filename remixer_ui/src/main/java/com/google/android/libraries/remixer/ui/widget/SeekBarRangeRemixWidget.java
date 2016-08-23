/*
 * Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.android.libraries.remixer.ui.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.libraries.remixer.RangeRemix;
import com.google.android.libraries.remixer.ui.R;

/**
 * Displays a {@link RangeRemix} as a SeekBar.
 */
public class SeekBarRangeRemixWidget extends RelativeLayout implements RemixWidget<RangeRemix> {

  private SeekBar seekBar;
  private TextView nameText;
  private TextView currentValueText;

  public SeekBarRangeRemixWidget(Context context) {
    super(context);
  }

  public SeekBarRangeRemixWidget(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public SeekBarRangeRemixWidget(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
    seekBar = (SeekBar) findViewById(R.id.rangeRemixSeekBar);
    nameText = (TextView) findViewById(R.id.rangeRemixName);
    currentValueText = (TextView) findViewById(R.id.rangeRemixCurrentValue);
  }

  @Override
  public void bindRemix(@NonNull final RangeRemix remix) {
    nameText.setText(remix.getTitle());
    seekBar.setMax(remix.getMaxValue() - remix.getMinValue());
    int progress = remix.getSelectedValue() - remix.getMinValue();
    seekBar.setProgress(remix.getSelectedValue() - remix.getMinValue());
    updateCurrentValue(remix, progress);
    seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

      @Override
      public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        updateCurrentValue(remix, progress);
        remix.setValue(remix.getMinValue() + progress);
      }

      @Override
      public void onStartTrackingTouch(SeekBar seekBar) {
      }

      @Override
      public void onStopTrackingTouch(SeekBar seekBar) {
      }
    });
  }

  private void updateCurrentValue(@NonNull final RangeRemix remix, int progress) {
    int value = remix.getMinValue() + progress;
    currentValueText.setText(String.valueOf(value));
  }
}