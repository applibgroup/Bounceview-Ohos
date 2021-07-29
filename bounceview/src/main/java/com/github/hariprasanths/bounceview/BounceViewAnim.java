package com.github.hariprasanths.bounceview;

import ohos.agp.animation.Animator;

/**
 * Created by hari on 7/6/18.
 */

public interface BounceViewAnim {

    BounceViewAnim setScaleForPushInAnim(float scaleX, float scaleY);

    BounceViewAnim setScaleForPopOutAnim(float scaleX, float scaleY);

    BounceViewAnim setPushInAnimDuration(int timeInMillis);

    BounceViewAnim setPopOutAnimDuration(int timeInMillis);

    BounceViewAnim setInterpolatorPushIn(Animator interpolatorPushIn);

    BounceViewAnim setInterpolatorPopOut(Animator interpolatorPopOut);

}
