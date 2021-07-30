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

package com.github.hariprasanths.bounceviewsample;

import com.github.hariprasanths.bounceview.CustomDialog;
import ohos.aafwk.ability.delegation.AbilityDelegatorRegistry;
import ohos.agp.components.Component;
import ohos.agp.components.DirectionalLayout;
import ohos.agp.components.LayoutScatter;
import ohos.app.Context;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomDialogTest {
    Context context;
    Component mComponent;
    String string = "Hello";
    public Context setUpContext() {
        return AbilityDelegatorRegistry.getAbilityDelegator().getAppContext();
    }

    @Before
    public void setUp(){
        context = setUpContext();
        mComponent = LayoutScatter.getInstance(context)
                .parse(ResourceTable.Layout_custom_dialog,null,false);
    }

    @Test
    public void testGetSetTitleCustomComponent() {
        CustomDialog myCommonDialog = new CustomDialog(context);
        myCommonDialog.setTitleCustomComponent((DirectionalLayout) mComponent);
        assertEquals(myCommonDialog.getTitleCustomComponent(),mComponent);
    }

    @Test
    public void testGetTitleCustomComponentNullInitially() {
        CustomDialog myCommonDialog = new CustomDialog(context);
        assertNull(myCommonDialog.getTitleCustomComponent());
    }

    @Test
    public void testSetContentCustomComponent() {
        CustomDialog myCommonDialog = new CustomDialog(context);
        myCommonDialog.setContentCustomComponent(mComponent);
        assertEquals(myCommonDialog.getContentCustomComponent(),mComponent);
    }

    @Test
    public void testSetTitleText() {
        CustomDialog myCommonDialog = new CustomDialog(context);
        myCommonDialog.setTitleText(string);
        assertNotNull(myCommonDialog.getTitleCustomComponent());
    }

    @Test
    public void testSetTitleSubText() {
        CustomDialog myCommonDialog = new CustomDialog(context);
        myCommonDialog.setTitleSubText(string);
        assertNotNull(myCommonDialog.getTitleCustomComponent());
    }

    @Test
    public void testSetContentText() {
        CustomDialog myCommonDialog = new CustomDialog(context);
        myCommonDialog.setContentText(string);
        assertNotNull(myCommonDialog.getContentCustomComponent());
    }

    @Test
    public void testSetContentImage() {
        CustomDialog myCommonDialog = new CustomDialog(context);
        myCommonDialog.setContentImage(ResourceTable.Media_icon);
        assertNotNull(myCommonDialog.getContentCustomComponent());
    }

    @Test
    public void testSetCommonButton() {
        CustomDialog myCommonDialog = new CustomDialog(context);
        assertNotNull(myCommonDialog.setCommonButton(0, string, 0, 0, component -> { }));
    }

    @Test
    public void testSetCommonImageButton() {
        CustomDialog myCommonDialog = new CustomDialog(context);
        assertNotNull(myCommonDialog.setCommonImageButton(0, 0, 0, ResourceTable.Media_icon, component -> { }));
    }

    @Test
    public void testGetComponentContainer() {
        CustomDialog myCommonDialog = new CustomDialog(context);
        assertNull(myCommonDialog.getComponentContainer());
    }

    @Test
    public void testGetSetComponentContainer() {
        CustomDialog myCommonDialog = new CustomDialog(context);
        myCommonDialog.setComponentContainer(mComponent);
        assertEquals(myCommonDialog.getComponentContainer(),mComponent);
    }
}
