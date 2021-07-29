# Bounceview-Ohos
It's an HarmonyOS library that provides customizable bounce animation for any view update

## Source
Inspired by https://github.com/hariprasanths/Bounceview-Android Version v0.2.0

## Screenshot

## Getting Started
<h4>In your build.gradle</h4>

## Installation Instructions
1.For using Bounceview-Ohos module in sample app, include the source code and add the below dependencies in entry/build.gradle to generate hap/support.har.
```groovy
	dependencies {
		implementation project(path: ':bounceview-ohos')
        implementation fileTree(dir: 'libs', include: ['*.har'])
        testImplementation 'junit:junit:4.13'
	}
```
2.For using Bounceview-Ohos in separate application using har file, add the har file in the entry/libs folder and add the dependencies in entry/build.gradle file.
```groovy
	dependencies {
		implementation fileTree(dir: 'libs', include: ['*.har'])
		testImplementation 'junit:junit:4.13'
	}
```
## Usage

<h5>Add animations to any views like so:</h5>

```java
Button button = (Button) findComponentById(ResourceTable.Id_button);
BounceView.addAnimTo(button);
```

### Use BounceView with dialogs:

```java
CustomDialog commonDialog = new CustomDialog(getContext());
DirectionalLayout customDialog = (DirectionalLayout) LayoutScatter.getInstance(getContext())
        .parse(ResourceTable.Layout_custom_dialog, null, false);
commonDialog.setContentCustomComponent(customDialog);
commonDialog.setAutoClosable(true);
commonDialog.show();
BounceView.addAnimTo(commonDialog);

PopupDialog
...
DirectionalLayout customDialogLayout = (DirectionalLayout) LayoutScatter.getInstance(getContext())
                    .parse(ResourceTable.Layout_custom_popup, null, false);
CustomPopupDialog popupDialog = new CustomPopupDialog(getContext(), null);
popupDialog.setCustomComponent(customDialogLayout);
popupDialog.setAutoClosable(true);
popupDialog.show();
BounceView.addAnimTo(popupDialog);

CustomDialog alertDialog = new CustomDialog(getContext());
alertDialog.setTitleText("Alert Dialog");
alertDialog.setContentText("Do you want to exit?");
alertDialog.setCommonButton(0, "No", 100, 0, component12 -> alertDialog.destroy());
alertDialog.setCommonButton(1, "Yes", 100, 0, component1 -> terminateAbility());
alertDialog.setSize(400, 300);
alertDialog.setAutoClosable(true);
alertDialog.show();
BounceView.addAnimTo(alertDialog);
```

### Some cool animations:

```java
//Bounce animation
BounceView.addAnimTo(button1)
        .setScaleForPopOutAnim(1.1f, 1.1f);

//Horizontal flip animation
BounceView.addAnimTo(button2)
        .setScaleForPopOutAnim(1f, 0f);

//Vertical flip animation
BounceView.addAnimTo(button3)
        .setScaleForPopOutAnim(0f, 1f);

//Flicker animation
BounceView.addAnimTo(button4)
        .setScaleForPopOutAnim(0f, 0f);
```

## Customize BounceView properties:

```java
Button button = (Button) findComponentById(ResourceTable.Id_button);
BounceView.addAnimTo(button)
        //Default push in scalex: 0.9f , scaley: 0.9f
        .setScaleForPushInAnim(BounceView.PUSH_IN_SCALE_X, BounceView.PUSH_IN_SCALE_Y)
        //Default pop out scalex: 1.1f, scaley: 1.1f
        .setScaleForPopOutAnim(BounceView.POP_OUT_SCALE_X, BounceView.POP_OUT_SCALE_Y)
        //Default push in anim duration: 100 (in milliseconds)
        .setPushInAnimDuration(BounceView.PUSH_IN_ANIM_DURATION)
        //Default pop out anim duration: 100 (in milliseconds)
        .setPopOutAnimDuration(BounceView.POP_OUT_ANIM_DURATION)
        //Default interpolator: Animator.CurveType.ACCELERATE_DECELERATE
        .setInterpolatorPushIn(BounceView.DEFAULT_INTERPOLATOR)
        .setInterpolatorPopOut(BounceView.DEFAULT_INTERPOLATOR);
```

## Show your support

Give a :star: if this project helped you!

## License

Copyright :copyright: 2018 [Hariprasanth S](https://github.com/hariprasanths)

This project is licensed under [the Apache License, Version 2.0](https://github.com/hariprasanths/Bounceview-Android/blob/master/LICENSE)
<br/>You may also obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0