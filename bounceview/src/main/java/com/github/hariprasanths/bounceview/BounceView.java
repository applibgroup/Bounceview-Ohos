package com.github.hariprasanths.bounceview;

import ohos.agp.animation.Animator;
import ohos.agp.animation.AnimatorGroup;
import ohos.agp.animation.AnimatorProperty;
import ohos.agp.animation.AnimatorValue;
import ohos.agp.components.Component;
import ohos.agp.components.TabList;
import ohos.multimodalinput.event.TouchEvent;
import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by hari on 7/6/18.
 */

public class BounceView implements BounceViewAnim {
    public static final float PUSH_IN_SCALE_X = 0.9f;
    public static final float PUSH_IN_SCALE_Y = 0.9f;
    public static final float POP_OUT_SCALE_X = 1.1f;
    public static final float POP_OUT_SCALE_Y = 1.1f;
    public static final int PUSH_IN_ANIM_DURATION = 100;
    public static final int POP_OUT_ANIM_DURATION = 100;
    public static final Animator DEFAULT_INTERPOLATOR = null;
    private WeakReference<Component> mComponent;
    private WeakReference<CustomDialog> mDialog;
    private WeakReference<CustomPopupDialog> mPopup;
    private WeakReference<TabList> mTabList;
    private boolean isTouchInsideView = true;
    private float pushInScaleX;
    private float pushInScaleY;
    private float popOutScaleX;
    private float popOutScaleY;
    private int pushInAnimDuration;
    private int popOutAnimDuration;
    private Animator pushInInterpolator;
    private Animator popOutInterpolator = DEFAULT_INTERPOLATOR;

    private void checkFieldsValue() {
        pushInScaleX = PUSH_IN_SCALE_X;
        pushInScaleY = PUSH_IN_SCALE_Y;
        popOutScaleX = POP_OUT_SCALE_X;
        popOutScaleY = POP_OUT_SCALE_Y;
        pushInAnimDuration = PUSH_IN_ANIM_DURATION;
        popOutAnimDuration = POP_OUT_ANIM_DURATION;
        pushInInterpolator = DEFAULT_INTERPOLATOR;
        popOutInterpolator = DEFAULT_INTERPOLATOR;
    }

    private BounceView(Component component1) {
        checkFieldsValue();
        this.mComponent = new WeakReference<>(component1);
        if (this.mComponent.get() != null && this.mComponent.get().getClickedListener() == null) {
            this.mComponent.get().setClickedListener(component -> { });
        }
    }

    private BounceView(CustomDialog dialog) {
        checkFieldsValue();
        this.mDialog = new WeakReference<>(dialog);
    }

    private BounceView(CustomPopupDialog popup) {
        checkFieldsValue();
        this.mPopup = new WeakReference<>(popup);
    }

    private BounceView(TabList tabList) {
        checkFieldsValue();
        this.mTabList = new WeakReference<>(tabList);
    }

    /** Add animation to component.
     *
     * @param view here view is component which will get animate
     * @return BounceView Attached to view
     */
    public static BounceView addAnimTo(Component view) {
        BounceView bounceAnim = new BounceView(view);
        bounceAnim.setAnimToView();
        return bounceAnim;
    }

    /** Add animation to Custom Common Dialog.
     *
     * @param dialog takes Custom Common Dialog as a parameter
     */
    public static void addAnimTo(CustomDialog dialog) {
        BounceView bounceAnim = new BounceView(dialog);
        bounceAnim.setAnimToDialog();
    }

    /** Add animation to Custom Popup Dialog.
     *
     * @param popupWindow takes Custom Popup Dialog as a parameter
     */
    public static void addAnimTo(CustomPopupDialog popupWindow) {
        BounceView bounceAnim = new BounceView(popupWindow);
        bounceAnim.setAnimToPopup();
    }

    /** animate every tab of tabList given as parameter.
     *
     * @param tabList take tabList as input parameter
     * @return BounceView
     */
    public static BounceView addAnimTo(TabList tabList) {
        BounceView bounceAnim = new BounceView(tabList);
        bounceAnim.setAnimToTabList();
        return bounceAnim;
    }

    @Override
    public BounceViewAnim setScaleForPushInAnim(float scaleX, float scaleY) {
        this.pushInScaleX = scaleX;
        this.pushInScaleY = scaleY;
        return this;
    }

    @Override
    public BounceViewAnim setScaleForPopOutAnim(float scaleX, float scaleY) {
        this.popOutScaleX = scaleX;
        this.popOutScaleY = scaleY;
        return this;
    }

    @Override
    public BounceViewAnim setPushInAnimDuration(int timeInMillis) {
        this.pushInAnimDuration = timeInMillis;
        return this;
    }

    @Override
    public BounceViewAnim setPopOutAnimDuration(int timeInMillis) {
        this.popOutAnimDuration = timeInMillis;
        return this;
    }

    @Override
    public BounceViewAnim setInterpolatorPushIn(Animator interpolatorPushIn) {
        if (interpolatorPushIn == null) {
            AnimatorValue animatorValue = new AnimatorValue();
            animatorValue.setCurveType(Animator.CurveType.ACCELERATE_DECELERATE);
            interpolatorPushIn = animatorValue;
        }
        this.pushInInterpolator = interpolatorPushIn;
        return this;
    }

    @Override
    public BounceViewAnim setInterpolatorPopOut(Animator interpolatorPopOut) {
        if (interpolatorPopOut == null) {
            AnimatorValue animatorValue = new AnimatorValue();
            animatorValue.setCurveType(Animator.CurveType.ACCELERATE_DECELERATE);
            interpolatorPopOut = animatorValue;
        }
        this.popOutInterpolator = interpolatorPopOut;
        return this;
    }

    private void setAnimToView() {
        if (mComponent != null) {
            mComponent.get().setTouchEventListener((component, motionEvent) -> {
                int action = motionEvent.getAction();
                if (action == TouchEvent.PRIMARY_POINT_DOWN) {
                    isTouchInsideView = true;
                    startAnimScale(component, pushInScaleX, pushInScaleY, pushInAnimDuration, pushInInterpolator, 0);
                    return true;
                } else if (action == TouchEvent.PRIMARY_POINT_UP) {
                    if (isTouchInsideView) {
                        component.createAnimatorProperty().cancel();
                        startAnimScale(component, popOutScaleX, popOutScaleY,
                                popOutAnimDuration, popOutInterpolator, 0);
                        startAnimScale(component, 1f, 1f, popOutAnimDuration,
                                popOutInterpolator, popOutAnimDuration + 1);
                        return false;
                    }
                } else {
                    return checkAction(action, component, motionEvent);
                }
                return false;
            });
        }
    }

    private boolean checkAction(int action, Component component, TouchEvent motionEvent) {
        if (action == TouchEvent.CANCEL) {
            if (isTouchInsideView) {
                component.createAnimatorProperty().cancel();
                startAnimScale(component, 1f, 1f, popOutAnimDuration, DEFAULT_INTERPOLATOR, 0);
            }
            return true;
        } else if (action == TouchEvent.POINT_MOVE && isTouchInsideView) {
            int i = motionEvent.getIndex();
            float currentX = motionEvent.getPointerScreenPosition(i).getX();
            float currentY = motionEvent.getPointerScreenPosition(i).getY();
            float currentPosX = currentX + component.getLeft();
            float currentPosY = currentY + component.getTop();
            float viewLeft = component.getLeft();
            float viewTop = component.getTop();
            float viewRight = component.getRight();
            float viewBottom = component.getBottom();
            if (!(currentPosX > viewLeft && currentPosX < viewRight
                    && currentPosY > viewTop && currentPosY < viewBottom)) {
                isTouchInsideView = false;
                component.createAnimatorProperty().cancel();
                startAnimScale(component, 1f, 1f, popOutAnimDuration, DEFAULT_INTERPOLATOR, 0);
            }
            return true;
        }
        return false;
    }

    private void startAnimScale(Component component, float scaleX, float scaleY,
                                int animDuration,
                                Animator interpolator,
                                int startDelay) {
        if (interpolator == null) {
            AnimatorValue animatorValue = new AnimatorValue();
            animatorValue.setCurveType(Animator.CurveType.ACCELERATE_DECELERATE);
            interpolator = animatorValue;
        }
        AnimatorProperty animX = component.createAnimatorProperty();
        animX.scaleX(scaleX);
        animX.setDuration(animDuration);
        animX.setCurveType(interpolator.getCurveType());
        AnimatorProperty animY = component.createAnimatorProperty();
        animY.scaleY(scaleY);
        animY.setDuration(animDuration);
        animY.setCurveType(interpolator.getCurveType());
        AnimatorGroup animatorSet = new AnimatorGroup();
        animatorSet.runParallel(animX, animY);
        animatorSet.setDelay(startDelay);
        animatorSet.start();
    }

    private void setAnimToDialog() {
        if (mDialog.get() != null) {
            Component component = mDialog.get().getComponentContainer();
            AnimatorProperty animatorProperty = component.createAnimatorProperty().setDuration(100)
                    .scaleX(1.04f).scaleY(1.04f).scaleXFrom(1).scaleYFrom(1)
                    .setCurveType(Animator.CurveType.ACCELERATE_DECELERATE);
            animatorProperty.setStateChangedListener(new Animator.StateChangedListener() {
                @Override
                public void onStart(Animator animator) {
                    //
                }

                @Override
                public void onStop(Animator animator) {
                    //
                }

                @Override
                public void onCancel(Animator animator) {
                    //
                }

                @Override
                public void onEnd(Animator animator) {
                    new Timer().schedule(
                            new TimerTask() {
                                @Override
                                public void run() {
                                    component.getContext().getUITaskDispatcher().asyncDispatch(() -> {
                                        AnimatorProperty animatorProperty1 = component.createAnimatorProperty()
                                                .scaleX(1).scaleY(1).scaleXFrom(1.04f).scaleYFrom(1.04f)
                                                .setDuration(100)
                                                .setCurveType(Animator.CurveType.ACCELERATE_DECELERATE);
                                        animatorProperty1.start();
                                    });
                                }
                            }, 200);
                }

                @Override
                public void onPause(Animator animator) {
                    //
                }

                @Override
                public void onResume(Animator animator) {
                    //
                }
            });
            animatorProperty.start();
        }
    }

    private void setAnimToPopup() {
        if (mPopup.get() != null) {
            Component component = mPopup.get().getCustomComponent();
            AnimatorProperty animatorProperty = component.createAnimatorProperty().setDuration(100)
                    .scaleX(1.04f).scaleY(1.04f).scaleXFrom(1).scaleYFrom(1)
                    .setCurveType(Animator.CurveType.ACCELERATE_DECELERATE);
            animatorProperty.setStateChangedListener(new Animator.StateChangedListener() {
                @Override
                public void onStart(Animator animator) {
                    //
                }

                @Override
                public void onStop(Animator animator) {
                    //
                }

                @Override
                public void onCancel(Animator animator) {
                    //
                }

                @Override
                public void onEnd(Animator animator) {
                    new Timer().schedule(
                            new TimerTask() {
                                @Override
                                public void run() {
                                    component.getContext().getUITaskDispatcher()
                                            .asyncDispatch(() -> component.createAnimatorProperty()
                                                    .scaleX(1).scaleY(1).scaleXFrom(1.04f).scaleYFrom(1.04f)
                                                    .setDuration(100).setCurveType(Animator.CurveType.ACCELERATE_DECELERATE)
                                                    .start());
                                }
                            }, 200);
                }

                @Override
                public void onPause(Animator animator) {
                    //
                }

                @Override
                public void onResume(Animator animator) {
                    //
                }
            });
            animatorProperty.start();
            mPopup.get().setCustomComponent(component);
            animatorProperty.start();
        }
    }

    private void setAnimToTabList() {
        if (mTabList.get() != null) {
            for (int i = 0; i < mTabList.get().getTabCount(); i++) {
                final TabList.Tab tab = mTabList.get().getTabAt(i);
                tab.setTouchEventListener((component, motionEvent) -> {
                    int action = motionEvent.getAction();
                    if (action == TouchEvent.PRIMARY_POINT_DOWN) {
                        isTouchInsideView = true;
                        startAnimScale(component, pushInScaleX, pushInScaleY,
                                pushInAnimDuration, pushInInterpolator, 0);
                        return true;

                    } else if (action == TouchEvent.PRIMARY_POINT_UP) {
                        if (isTouchInsideView) {
                            component.createAnimatorProperty().cancel();
                            startAnimScale(component, popOutScaleX, popOutScaleY, popOutAnimDuration,
                                    popOutInterpolator, 0);
                            startAnimScale(component, 1f, 1f, popOutAnimDuration,
                                    popOutInterpolator, popOutAnimDuration + 1);
                            tab.select();
                            return false;
                        }
                    } else {
                        return checkTabActions(action, component, motionEvent);
                    }
                    return false;
                });
            }
        }
    }

    private boolean checkTabActions(int action, Component component, TouchEvent motionEvent) {
        if (action == TouchEvent.CANCEL) {
            if (isTouchInsideView) {
                component.createAnimatorProperty().cancel();
                startAnimScale(component, 1f, 1f, popOutAnimDuration, DEFAULT_INTERPOLATOR, 0);
            }
            return true;
        } else if (action == TouchEvent.POINT_MOVE && isTouchInsideView) {
            int i1 = motionEvent.getIndex();
            float currentX = motionEvent.getPointerScreenPosition(i1).getX();
            float currentY = motionEvent.getPointerScreenPosition(i1).getY();
            float currentPosX = currentX + component.getLeft();
            float currentPosY = currentY + component.getTop();
            float viewLeft = component.getLeft();
            float viewTop = component.getTop();
            float viewRight = component.getRight();
            float viewBottom = component.getBottom();
            if (!(currentPosX > viewLeft && currentPosX < viewRight
                    && currentPosY > viewTop && currentPosY < viewBottom)) {
                isTouchInsideView = false;
                component.createAnimatorProperty().cancel();
                startAnimScale(component, 1f, 1f, popOutAnimDuration, DEFAULT_INTERPOLATOR, 0);
            }
            return true;
        }
        return false;
    }
}
