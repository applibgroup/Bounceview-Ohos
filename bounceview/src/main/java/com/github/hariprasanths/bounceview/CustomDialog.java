package com.github.hariprasanths.bounceview;

import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.DirectionalLayout;
import ohos.agp.components.Image;
import ohos.agp.components.LayoutScatter;
import ohos.agp.components.Text;
import ohos.agp.window.dialog.CommonDialog;
import ohos.app.Context;

/**
 * Custom Common Dialog.
 */
public class CustomDialog extends CommonDialog {
    Component baseCustomLayout;
    Component baseTitleLayout;
    Component baseContentLayout;
    Component baseButtonLayout;
    Component baseImageButtonLayout;
    Component mTitleComponent = null;
    Component mContentComponent = null;
    Component mButtonComponent = null;
    Component mImageButtonComponent = null;
    Context context;

    /**
     * Constructor to create Custom Common Dialog.
     *
     * @param context context
     */
    public CustomDialog(Context context) {
        super(context);
        this.context = context;
        baseCustomLayout = LayoutScatter.getInstance(context)
                .parse(ResourceTable.Layout_custom_common_dialog, null, false);
        DirectionalLayout.LayoutConfig layoutConfig = new DirectionalLayout.LayoutConfig(
                ComponentContainer.LayoutConfig.MATCH_CONTENT, ComponentContainer.LayoutConfig.MATCH_CONTENT);
        baseCustomLayout.setLayoutConfig(layoutConfig);
        baseTitleLayout = LayoutScatter.getInstance(context)
                .parse(ResourceTable.Layout_title, null, false);
        baseTitleLayout.setLayoutConfig(layoutConfig);
        baseContentLayout = LayoutScatter.getInstance(context)
                .parse(ResourceTable.Layout_content, null, false);
        baseContentLayout.setLayoutConfig(layoutConfig);
        baseButtonLayout = LayoutScatter.getInstance(context)
                .parse(ResourceTable.Layout_buttons, null, false);
        baseImageButtonLayout = LayoutScatter.getInstance(context)
                .parse(ResourceTable.Layout_image_buttons, null, false);
        baseButtonLayout.setLayoutConfig(layoutConfig);
    }

    public CommonDialog setComponentContainer(Component component) {
        return super.setContentCustomComponent(component);
    }

    public Component getComponentContainer() {
        return super.getContentCustomComponent();
    }

    @Override
    public CommonDialog setTitleCustomComponent(DirectionalLayout component) {
        mTitleComponent = component;
        return this;
    }

    public Component getTitleCustomComponent() {
        return mTitleComponent;
    }

    @Override
    public CommonDialog setContentCustomComponent(Component component) {
        mContentComponent = component;
        return this;
    }

    @Override
    public Component getContentCustomComponent() {
        return mContentComponent;
    }

    private CommonDialog setButtonCustomComponent(Component component) {
        mButtonComponent = component;
        return this;
    }

    @Override
    public CommonDialog setTitleText(String text) {
        Text titleText = (Text) baseTitleLayout.findComponentById(ResourceTable.Id_titleText);
        titleText.setText(text);
        titleText.setVisibility(0);
        this.setTitleCustomComponent((DirectionalLayout) baseTitleLayout);
        return this;
    }

    @Override
    public CommonDialog setTitleSubText(String text) {
        Text titleText = (Text) baseTitleLayout.findComponentById(ResourceTable.Id_subTitleText);
        titleText.setText(text);
        titleText.setVisibility(Component.VISIBLE);
        this.setTitleCustomComponent((DirectionalLayout) baseTitleLayout);
        return this;
    }

    @Override
    public CommonDialog setContentText(String text) {
        Text contentText = (Text) baseContentLayout.findComponentById(ResourceTable.Id_contentText);
        contentText.setText(text);
        contentText.setVisibility(0);
        this.setContentCustomComponent(baseContentLayout);
        return this;
    }

    @Override
    public CommonDialog setContentImage(int resId) {
        Image image = (Image) baseContentLayout.findComponentById(ResourceTable.Id_imgContent);
        image.setPixelMap(resId);
        image.setVisibility(0);
        this.setContentCustomComponent(baseContentLayout);
        return this;
    }

    /**
     * Does some thing in old style.
     * use {@link
     * #setCommonButton(int buttonNum,
     * String text, int leftMargin, int rightMargin,
     * Component.ClickedListener listener)} ()}
     * instead.
     */
    @Override
    public CommonDialog setButton(int buttonNum, String text, ClickedListener listener)
            throws UnsupportedOperationException {
        String message = "setButton is unsupported, use setCommonButton(int buttonNum, String text, int leftMargin,"
                + " int rightMargin, Component.ClickedListener listener) instead.";
        throw new UnsupportedOperationException(message);
    }

    /**
     * set buttons for the custom common dialog.
     *
     * @param buttonNum by default at most 3 buttons can be added, takes 0,1,2.
     * @param text text to display on button
     * @param leftMargin left margin for button
     * @param rightMargin right margin for button
     * @param listener component listener
     * @return Custom common dialog
     */
    public CommonDialog setCommonButton(int buttonNum, String text, int leftMargin, int rightMargin,
                                        Component.ClickedListener listener) {
        Button button;
        switch (buttonNum) {
            case 0 :
                button = (Button) baseButtonLayout.findComponentById(ResourceTable.Id_bb0);
                break;
            case 1 :
                button = (Button) baseButtonLayout.findComponentById(ResourceTable.Id_bb1);
                break;
            case 2:
                button = (Button) baseButtonLayout.findComponentById(ResourceTable.Id_bb2);
                break;
            default :
                return this;
        }
        button.setVisibility(0);
        button.setText(text);
        button.setMarginsLeftAndRight(leftMargin, rightMargin);
        button.setClickedListener(listener);
        this.setButtonCustomComponent(baseButtonLayout);
        return this;
    }

    /**
     * Does some thing in old style.
     * use {@link #setCommonImageButton(int, int, int, int, Component.ClickedListener)} instead.
     */
    @Override
    public CommonDialog setImageButton(int buttonNum, int resId, ClickedListener listener)
            throws UnsupportedOperationException {
        String message = "setButton is unsupported, use setCommonImageButton(int buttonNum, int resId, int leftMargin,"
                + " int rightMargin, Component.ClickedListener listener) instead.";
        throw new UnsupportedOperationException(message);
    }

    /**
     * set Image buttons for the custom common dialog.
     *
     * @param buttonNum by default at most 3 buttons can be added, takes 0,1,2.
     * @param resId ResourceId for Image that you want to set
     * @param leftMargin left margin for image button
     * @param rightMargin right margin for image button
     * @param listener component listener
     * @return Custom common dialog
     */
    public CommonDialog setCommonImageButton(int buttonNum, int resId, int leftMargin, int rightMargin,
                                             Component.ClickedListener listener) {
        if (mButtonComponent != null) {
            return this;
        }
        Image image;
        switch (buttonNum) {
            case 0 :
                image = (Image) baseImageButtonLayout.findComponentById(ResourceTable.Id_imgb0);
                break;
            case 1 :
                image = (Image) baseImageButtonLayout.findComponentById(ResourceTable.Id_imgb1);
                break;
            case 2 :
                image = (Image) baseImageButtonLayout.findComponentById(ResourceTable.Id_imgb2);
                break;
            default:
                return this;
        }
        image.setVisibility(0);
        image.setMarginsLeftAndRight(leftMargin, rightMargin);
        image.setPixelMap(resId);
        image.setClickedListener(listener);
        mImageButtonComponent = baseImageButtonLayout;
        return this;
    }

    @Override
    public void show() {
        DirectionalLayout componentContainer = new DirectionalLayout(context);
        int k = 0;
        if (mTitleComponent != null) {
            componentContainer.addComponent(mTitleComponent, k);
            k++;
        }
        if (mContentComponent != null) {
            componentContainer.addComponent(mContentComponent, k);
            k++;
        }
        if (mButtonComponent != null) {
            componentContainer.addComponent(mButtonComponent, k);
        } else if (mImageButtonComponent != null) {
            componentContainer.addComponent(mImageButtonComponent, k);
        }
        setComponentContainer(componentContainer);
        super.show();
    }
}
