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

import javafx.application.Platform;
import com.playonlinux.domain.CancelException;
import com.playonlinux.api.ui.UIMessageSender;
import com.playonlinux.utils.messages.Message;
import com.playonlinux.utils.messages.SynchroneousMessage;

import java.util.concurrent.CountDownLatch;

public class UIMessageSenderJavaFXImplementation<RETURN_TYPE> implements UIMessageSender<RETURN_TYPE> {
    public static void runAndWait(Runnable action) {
        if (action == null)
            throw new NullPointerException("action");

        // run synchronously on JavaFX thread
        if (Platform.isFxApplicationThread()) {
            action.run();
            return;
        }

        // queue on JavaFX thread and wait for completion
        final CountDownLatch doneLatch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                action.run();
            } finally {
                doneLatch.countDown();
            }
        });

        try {
            doneLatch.await();
        } catch (InterruptedException e) {
            // ignore exception
        }
    }

    @Override
    public RETURN_TYPE synchroneousSendAndGetResult(SynchroneousMessage<RETURN_TYPE> message) throws InterruptedException, CancelException {
        UIMessageSenderJavaFXImplementation.runAndWait(message);
        return message.getResponse();
    }

    public void synchroneousSend(Message message){
        UIMessageSenderJavaFXImplementation.runAndWait(message);
    }

    @Override
    public void asynchroneousSend(Message message) {
        Platform.runLater(message);
    }
}
