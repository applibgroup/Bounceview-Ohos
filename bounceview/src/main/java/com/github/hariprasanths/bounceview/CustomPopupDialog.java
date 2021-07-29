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
    Component mComponent = null;
    Component mTempComponent = null;
    DirectionalLayout customLayout;

    /**
     * Constructor to create Custom Popup dialog.
     *
     * @param context context
     * @param contentComponent contentComponent
     */
    public CustomPopupDialog(Context context, Component contentComponent) {
        super(context, contentComponent);
        mComponent = contentComponent;
        customLayout = (DirectionalLayout) LayoutScatter.getInstance(context)
                .parse(ResourceTable.Layout_temp_popup, null, false);
    }

    public CustomPopupDialog(Context context, Component contentComponent, int width, int height) {
        super(context, contentComponent, width, height);
    }

    public Component getCustomComponent() {
        return mComponent;
    }

    /**
     * Used to return component set by setText, setBackColor.
     * Required when customComponent is not set
     *
     * @return component set by setText, setBackColor
     */
    public Component getBaseComponent() {
        return mTempComponent;
    }

    @Override
    public CustomPopupDialog setCustomComponent(Component component) {
        super.setCustomComponent(component);
        this.mComponent = component;
        return this;
    }

    @Override
    public PopupDialog setText(String text) {
        Text contentText = (Text) customLayout.findComponentById(ResourceTable.Id_temp_text);
        contentText.setText(text);
        mTempComponent = customLayout;
        return this;
    }

    @Override
    public void setBackColor(Color color) {
        DirectionalLayout directionalLayout =
                (DirectionalLayout) customLayout.findComponentById(ResourceTable.Id_temp_Layout);
        directionalLayout.setBackground(buildDrawableByColor(color.getValue()));
        mTempComponent = customLayout;
    }

    @Override
    public void show() {
        if (mComponent == null) {
            this.setCustomComponent(mTempComponent);
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
