/*
 * Copyright (C) 2015 PÂRIS Quentin
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.playonlinux.ui.impl.javafx;

import com.playonlinux.api.ui.Controller;
import com.playonlinux.injection.Scan;
import com.playonlinux.api.ui.UIMessageSender;
import com.playonlinux.api.ui.SetupWindow;
import com.playonlinux.ui.impl.javafx.setupwindow.SetupWindowJavaFXImplementation;

@Scan
public class ControllerJavaFXImplementation implements Controller {

    public void startApplication() {
        JavaFXApplication.launch(JavaFXApplication.class);
    }

    public SetupWindow createSetupWindowGUIInstance(String title) {
        return new SetupWindowJavaFXImplementation(title);
    }

    @Override
    public UIMessageSender createUIMessageSender() {
        return new UIMessageSenderJavaFXImplementation();
    }


}
