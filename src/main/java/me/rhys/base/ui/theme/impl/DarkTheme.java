package me.rhys.base.ui.theme.impl;

import me.rhys.base.ui.theme.Theme;
import me.rhys.base.util.render.ColorUtil;

public class DarkTheme extends Theme {

    public DarkTheme() {
        super(
                new WindowColors(
                        ColorUtil.rgba(45, 52, 54, 1.0f),
                        ColorUtil.rgba(38, 43, 43, 1.0f)
                ),
                new ButtonColors(
                        ColorUtil.rgba(38, 43, 43, 1.0f),
                        ColorUtil.rgba(255, 255, 255, 1.0f)
                ),
                new LabelColors(
                        ColorUtil.rgba(255, 255, 255, 1.0f)
                ),
                new CheckBoxColors(
                        ColorUtil.rgba(38, 43, 43, 1.0f),
                        ColorUtil.rgba(28, 33, 33, 1.0f),
                        ColorUtil.rgba(46, 204, 113, 1.0f)
                ),
                new SliderColors(
                        ColorUtil.rgba(38, 43, 43, 1.0f),
                        ColorUtil.rgba(46, 204, 113, 1.0f)
                )
        );
    }

}
