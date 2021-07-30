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

import ohos.aafwk.ability.delegation.AbilityDelegatorRegistry;
import ohos.agp.components.Component;
import ohos.agp.components.LayoutScatter;
import ohos.agp.utils.Color;
import ohos.app.Context;
import com.github.hariprasanths.bounceview.CustomPopupDialog;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomPopupDialogTest {
    Context context;
    Component mComponent;
    CustomPopupDialog customPopupDialog;
    public Context setUpContext() {
        return AbilityDelegatorRegistry.getAbilityDelegator().getAppContext();
    }

    @Before
    public void setUp(){
        context = setUpContext();
        customPopupDialog = new CustomPopupDialog(context,null);
        mComponent = LayoutScatter.getInstance(context)
                .parse(ResourceTable.Layout_custom_popup,null,false);
    }

    @Test
    public void testGetCustomComponent() {
        assertNull(customPopupDialog.getCustomComponent());//null passed in constructor
    }

    @Test
    public void testSetCustomComponent() {
        customPopupDialog.setCustomComponent(mComponent);
        assertEquals(customPopupDialog.getCustomComponent(),mComponent);
    }

    @Test
    public void testSetText() {
        customPopupDialog.setText("Hello");
        assertNotNull(customPopupDialog.getBaseComponent());
    }

    @Test
    public void testSetBackColor() {
        customPopupDialog = new CustomPopupDialog(context,null);
        customPopupDialog.setBackColor(Color.RED);
        assertNotNull(customPopupDialog.getBaseComponent());
    }

    @Test
    public void testGetComponentsMethods() {
        assertNull(customPopupDialog.getCustomComponent());
        assertNull(customPopupDialog.getBaseComponent());
        customPopupDialog.setBackColor(Color.RED);
        assertNotNull(customPopupDialog.getBaseComponent());
        customPopupDialog.setCustomComponent(mComponent);
        assertEquals(customPopupDialog.getCustomComponent(),mComponent);
    }
}