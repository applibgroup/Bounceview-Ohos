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

import static org.junit.Assert.assertNotNull;
import com.github.hariprasanths.bounceview.BounceView;
import ohos.aafwk.ability.delegation.AbilityDelegatorRegistry;
import ohos.agp.animation.Animator;
import ohos.agp.animation.AnimatorValue;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.TabList;
import ohos.app.Context;
import org.junit.Before;
import org.junit.Test;

public class BounceViewTest {
    Context context;
    public Context setUpContext() {
        return AbilityDelegatorRegistry.getAbilityDelegator().getAppContext();
    }

    @Before
    public void setUp() {
        context = setUpContext();
    }

    @Test
    public void testAddAnimToComponent() {
        Component component = new Button(context);
        assertNotNull(BounceView.addAnimTo(component));
    }

    @Test
    public void testTestAddAnimToTabList() {
        TabList tabList = new TabList(context);
        TabList.Tab tab = tabList.new Tab(context);
        tabList.addTab(tab);
        assertNotNull( BounceView.addAnimTo(tabList) );
    }

    @Test
    public void testSetScaleForPushInAnim() {
        Component component = new Button(context);
        assertNotNull(BounceView.addAnimTo(component));
        assertNotNull(BounceView.addAnimTo(component).setScaleForPushInAnim(0.5f,0.5f));
    }

    @Test
    public void testSetScaleForPopOutAnim() {
        Component component = new Button(context);
        assertNotNull(BounceView.addAnimTo(component));
        assertNotNull(BounceView.addAnimTo(component).setScaleForPopOutAnim(0.5f,0.5f));
    }

    @Test
    public void testSetPushInAnimDuration() {
        Component component = new Button(context);
        assertNotNull(BounceView.addAnimTo(component));
        assertNotNull(BounceView.addAnimTo(component).setPushInAnimDuration(200));
    }

    @Test
    public void testSetPopOutAnimDuration() {
        Component component = new Button(context);
        assertNotNull(BounceView.addAnimTo(component));
        assertNotNull(BounceView.addAnimTo(component).setPopOutAnimDuration(200));
    }

    @Test
    public void testSetInterpolatorPushIn() {
        Component component = new Button(context);
        assertNotNull(BounceView.addAnimTo(component));
        AnimatorValue animatorValue = new AnimatorValue();
        animatorValue.setCurveType(Animator.CurveType.ACCELERATE_DECELERATE);
        assertNotNull(BounceView.addAnimTo(component).setInterpolatorPushIn(animatorValue));
    }

    @Test
    public void testSetInterpolatorPopOut() {
        Component component = new Button(context);
        assertNotNull(BounceView.addAnimTo(component));
        AnimatorValue animatorValue = new AnimatorValue();
        animatorValue.setCurveType(Animator.CurveType.ACCELERATE_DECELERATE);
        assertNotNull(BounceView.addAnimTo(component).setInterpolatorPopOut(animatorValue));
    }
}