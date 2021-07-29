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

package com.github.hariprasanths.bounceview;

import ohos.agp.colors.RgbColor;
import ohos.agp.components.Component;
import ohos.agp.components.DirectionalLayout;
import ohos.agp.components.LayoutScatter;
import ohos.agp.components.Text;
import ohos.agp.components.element.Element;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.utils.Color;
import ohos.agp.window.dialog.PopupDialog;
import ohos.app.Context;

/**
 * Custom Popup Dialog.
 */
public class CustomPopupDialog extends PopupDialog {
    Component mCustomComponent = null;
    Component mBaseComponent = null;
    DirectionalLayout basePopupLayout;

    /**
     * Constructor to create Custom Popup dialog.
     *
     * @param context context
     * @param contentComponent contentComponent
     */
    public CustomPopupDialog(Context context, Component contentComponent) {
        super(context, contentComponent);
        basePopupLayout = (DirectionalLayout) LayoutScatter.getInstance(context)
                .parse(ResourceTable.Layout_base_popup_dialog, null, false);
    }

    public CustomPopupDialog(Context context, Component contentComponent, int width, int height) {
        super(context, contentComponent, width, height);
    }

    public Component getCustomComponent() {
        return mCustomComponent;
    }

    /**
     * Used to return component set by setText, setBackColor.
     * Required when customComponent is not set
     *
     * @return component set by setText, setBackColor
     */
    public Component getBaseComponent() {
        return mBaseComponent;
    }

    @Override
    public CustomPopupDialog setCustomComponent(Component component) {
        super.setCustomComponent(component);
        this.mCustomComponent = component;
        return this;
    }

    /**
     * text will be set only to the baseLayout(not to to customLayout )similar to platform's PopupDialog behaviour.
     *
     * @param text to set
     * @return base component where text field set to given parameter
     */
    @Override
    public PopupDialog setText(String text) {
        Text contentText = (Text) basePopupLayout.findComponentById(ResourceTable.Id_temp_text);
        contentText.setText(text);
        mBaseComponent = basePopupLayout;
        return this;
    }

    /**
     * background color will be set only to the baseLayout(not to to customLayout)
     * similar to platform's PopupDialog behaviour.
     *
     * @param color that need to be set as background of base Layout
     */
    @Override
    public void setBackColor(Color color) {
        DirectionalLayout directionalLayout =
                (DirectionalLayout) basePopupLayout.findComponentById(ResourceTable.Id_temp_Layout);
        directionalLayout.setBackground(buildDrawableByColor(color.getValue()));
        mBaseComponent = basePopupLayout;
    }

    @Override
    public void show() {
        if (mCustomComponent == null) {
            this.setCustomComponent(mBaseComponent);
        }
        super.show();
    }

    private static Element buildDrawableByColor(int color) {
        ShapeElement drawable = new ShapeElement();
        drawable.setShape(ShapeElement.RECTANGLE);
        drawable.setRgbColor(RgbColor.fromArgbInt(color));
        return drawable;
    }
}
