/*
 * Copyright (C) 2020-21 Application Library Engineering Group
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

package com.github.hariprasanths.bounceviewsample.slice;

import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.DirectionalLayout;
import ohos.agp.components.LayoutScatter;
import ohos.agp.components.ListContainer;
import ohos.agp.components.PageFlipper;
import ohos.agp.components.TabList;
import ohos.agp.components.element.PixelMapElement;
import ohos.agp.utils.Color;
import ohos.agp.utils.TextAlignment;
import ohos.agp.window.service.DisplayManager;
import ohos.global.resource.NotExistException;
import ohos.media.image.PixelMap;
import com.github.hariprasanths.bounceview.BounceView;
import com.github.hariprasanths.bounceview.CustomDialog;
import com.github.hariprasanths.bounceview.CustomPopupDialog;
import com.github.hariprasanths.bounceviewsample.ResourceTable;
import com.github.hariprasanths.bounceviewsample.adapter.ListItemProvider;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * MainAbilitySlice.
 */
public class MainAbilitySlice extends AbilitySlice  {
    private TabList tabList;
    private PageFlipper pageFlipper;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_tab_list);
        tabList = (TabList) findComponentById(ResourceTable.Id_TabList);
        setTabListMethods();
        BounceView.addAnimTo(tabList);
        pageFlipper = (PageFlipper) findComponentById(ResourceTable.Id_PageFlipper);
        addListContainerPage();
        addDialogAnimPage();
        addCoolAnimPage();
    }

    private void addDialogAnimPage() {
        DirectionalLayout dialog  = (DirectionalLayout) LayoutScatter.getInstance(this)
                .parse(ResourceTable.Layout_ability_main, null, false);
        Button alert = (Button) dialog.findComponentById(ResourceTable.Id_button1);
        Button custom = (Button) dialog.findComponentById(ResourceTable.Id_button2);
        Button popUp = (Button) dialog.findComponentById(ResourceTable.Id_button3);
        BounceView.addAnimTo(alert);
        BounceView.addAnimTo(custom);
        BounceView.addAnimTo(popUp);
        addAlertDialog(dialog);
        addCustomDialog(dialog);
        addPopupDialog(dialog);
        pageFlipper.addComponent(dialog);
    }

    private void addPopupDialog(DirectionalLayout dialog) {
        Button popUp = (Button) dialog.findComponentById(ResourceTable.Id_button3);
        popUp.setClickedListener(component -> {
            DirectionalLayout dialogLayout = (DirectionalLayout) LayoutScatter.getInstance(getContext())
                    .parse(ResourceTable.Layout_custom_popup, null, false);
            CustomPopupDialog popupDialog = new CustomPopupDialog(getContext(), null);
            popupDialog.setCustomComponent(dialogLayout);
            popupDialog.setAutoClosable(true);
            popupDialog.setAlignment(72);
            popupDialog.show();
            BounceView.addAnimTo(popupDialog);
        });
    }

    private void addCustomDialog(DirectionalLayout dialog) {
        Button custom = (Button) dialog.findComponentById(ResourceTable.Id_button2);
        custom.setClickedListener(component -> {
            CustomDialog commonDialog = new CustomDialog(getContext());
            DirectionalLayout customDialog = (DirectionalLayout) LayoutScatter.getInstance(getContext())
                    .parse(ResourceTable.Layout_custom_dialog, null, false);
            Button buttonNo = (Button) customDialog.findComponentById(ResourceTable.Id_btn_no);
            Button buttonYes = (Button) customDialog.findComponentById(ResourceTable.Id_btn_yes);
            BounceView.addAnimTo(buttonNo);
            BounceView.addAnimTo(buttonYes);
            buttonNo.setClickedListener(component1 -> commonDialog.destroy());
            buttonYes.setClickedListener(component12 -> terminateAbility());
            commonDialog.setContentCustomComponent(customDialog);
            commonDialog.setAlignment(TextAlignment.CENTER);
            commonDialog.setSize(800, 400);
            commonDialog.setAutoClosable(true);
            commonDialog.show();
            BounceView.addAnimTo(commonDialog);
        });
    }

    private void addAlertDialog(DirectionalLayout dialog) {
        Button alert = (Button) dialog.findComponentById(ResourceTable.Id_button1);
        alert.setClickedListener(component -> {
            CustomDialog commonDialog = new CustomDialog(getContext());
            commonDialog.setTitleText("Alert Dialog");
            commonDialog.setContentText("Do you want to exit?");
            commonDialog.setCommonButton(0, "No", 100, 0, component12 -> commonDialog.destroy());
            commonDialog.setCommonButton(1, "Yes", 100, 0, component1 -> terminateAbility());
            commonDialog.setSize(400, 300);
            commonDialog.setAutoClosable(true);
            commonDialog.show();
            BounceView.addAnimTo(commonDialog);
        });
    }

    private void addCoolAnimPage() {
        DirectionalLayout directionalLayout = (DirectionalLayout) LayoutScatter.getInstance(this)
                .parse(ResourceTable.Layout_cool_animation, null, false);
        Button button1 = (Button) directionalLayout.findComponentById(ResourceTable.Id_bounce);
        Button button2 = (Button) directionalLayout.findComponentById(ResourceTable.Id_horizontalFlip);
        Button button3 = (Button) directionalLayout.findComponentById(ResourceTable.Id_verticalFlip);
        Button button4 = (Button) directionalLayout.findComponentById(ResourceTable.Id_flicker);
        BounceView.addAnimTo(button1)
                .setScaleForPushInAnim(BounceView.PUSH_IN_SCALE_X, BounceView.PUSH_IN_SCALE_Y)
                .setScaleForPopOutAnim(BounceView.POP_OUT_SCALE_X, BounceView.POP_OUT_SCALE_Y)
                .setPushInAnimDuration(BounceView.PUSH_IN_ANIM_DURATION)
                .setPopOutAnimDuration(BounceView.POP_OUT_ANIM_DURATION)
                .setInterpolatorPushIn(BounceView.DEFAULT_INTERPOLATOR)
                .setInterpolatorPopOut(BounceView.DEFAULT_INTERPOLATOR);
        //Horizontal flip animation
        BounceView.addAnimTo(button2)
                .setScaleForPopOutAnim(1f, 0f);
        //Vertical flip animation
        BounceView.addAnimTo(button3)
                .setScaleForPopOutAnim(0f, 1f);
        //Flicker animation
        BounceView.addAnimTo(button4)
                .setScaleForPopOutAnim(0f, 0f);
        pageFlipper.addComponent(directionalLayout);
    }

    private void addListContainerPage() {
        List<String> list = new ArrayList<>();
        list.add("Item 1");
        list.add("Item 2");
        list.add("Item 3");
        list.add("Item 4");
        ListItemProvider listItemProvider = new ListItemProvider(this);
        listItemProvider.setItemCount(4);
        listItemProvider.setList(list);
        ListContainer listContainer = (ListContainer) LayoutScatter.getInstance(this)
                .parse(ResourceTable.Layout_display_list_view, null, false);
        listContainer.setItemProvider(listItemProvider);
        pageFlipper.addComponent(listContainer);
    }

    private void setTabListMethods() {
        tabList.setHeight(100);
        setTabs();
        tabList.setTabLength(400);
        int dis = DisplayManager.getInstance().getDefaultDisplay(this).get().getRealAttributes().width;
        int diff = (dis - 3 * tabList.getTabLength()) / 2;
        tabList.setPadding(diff, 0, diff, 0);
        tabList.setSelectedTabIndicatorColor(Color.GRAY.getValue());
        tabList.setIndicatorType(1);
        tabList.setTabTextColors(Color.BLACK.getValue(), Color.WHITE.getValue());
        tabList.setTabTextSize(60);
        tabList.setSelectedTabIndicatorHeight(100);
        tabList.addTabSelectedListener(new TabList.TabSelectedListener() {
            @Override
            public void onSelected(TabList.Tab tab) {
                tab.setBackground(Utils.buildDrawableByColor(Color.GRAY.getValue()));
                pageFlipper.setCurrentIndex(tab.getPosition());
            }

            @Override
            public void onUnselected(TabList.Tab tab) {
                tab.setBackground(Utils.buildDrawableByColor(Color.DKGRAY.getValue()));
            }

            @Override
            public void onReselected(TabList.Tab tab) {
                // Not required
            }
        });
    }

    private void setTabs() {
        TabList.Tab tab1 = tabList.new Tab(this);
        try {
            PixelMap pixelMap = Utils.createByResourceId(this,
                    ResourceTable.Media_phone_white_24x24, "image1/png");
            PixelMapElement pixelMapElement = new PixelMapElement(pixelMap);
            pixelMapElement.setBounds(0, 0, 100, 100);
            tab1.setIconElement(pixelMapElement);
            tab1.setPadding(20, 0, 5, 5);
        } catch (NotExistException | IOException e) {
            //LogUtil.error(TAG, "createTabIcon " + e.getLocalizedMessage());
        }
        tab1.setText("Tab1");
        tabList.addTab(tab1);

        TabList.Tab tab2 = tabList.new Tab(this);
        try {
            PixelMap pixelMap = Utils.createByResourceId(this,
                    ResourceTable.Media_phone_forwarded_white_24x24, "image2/png");
            PixelMapElement pixelMapElement = new PixelMapElement(pixelMap);
            pixelMapElement.setBounds(0, 0, 100, 100);
            tab2.setIconElement(pixelMapElement);
            tab2.setPadding(20, 0, 20, 5);
        } catch (NotExistException | IOException e) {
            //LogUtil.error(TAG, "createTabIcon " + e.getLocalizedMessage());
        }
        tab2.setText("Tab2");
        tabList.addTab(tab2);

        TabList.Tab tab3 = tabList.new Tab(this);
        try {
            PixelMap pixelMap = Utils.createByResourceId(this,
                    ResourceTable.Media_phone_missed_white_24x24, "image3/png");
            PixelMapElement pixelMapElement = new PixelMapElement(pixelMap);
            pixelMapElement.setBounds(0, 0, 100, 100);
            tab3.setIconElement(pixelMapElement);
            tab3.setPadding(20, 0, 20, 5);
        } catch (NotExistException | IOException e) {
            //LogUtil.error(TAG, "createTabIcon " + e.getLocalizedMessage());
        }
        tab3.setText("Tab3");
        tabList.addTab(tab3);
        tabList.selectTab(tab1);
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
