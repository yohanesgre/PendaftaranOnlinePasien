/*
 * Copyright (c) 2018. Evren Coşkun
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.example.pendaftaranonlinepasien.TableView.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder;
import com.example.pendaftaranonlinepasien.R;

/**
 * Created by evrencoskun on 4.02.2018.
 */

public class MoodCellViewHolder extends AbstractViewHolder {

    public final LinearLayout cell_container;
    public final ImageView cell_image;

    public MoodCellViewHolder(View itemView) {
        super(itemView);
        cell_container = (LinearLayout) itemView.findViewById(R.id.cell_container);
        cell_image = (ImageView) itemView.findViewById(R.id.cell_image);
    }


    public void setData(Object data) {
        int mood = (int) data;
   //     Drawable moodDrawable = ContextCompat.getDrawable(itemView.getContext(), mood ==
     //           TableViewModel.HAPPY ? R.drawable.ic_happy : R.drawable.ic_sad);

       // cell_image.setImageDrawable(moodDrawable);
    }
}