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

package com.playonlinux.ui.impl.javafx.common;

import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import static com.playonlinux.domain.Localisation.translate;

public class SimpleIconListWidget extends TreeView {
    private final TreeItem rootItem;

    public SimpleIconListWidget() {
        rootItem = new TreeItem();
        this.setRoot(rootItem);
        this.setShowRoot(false);
    }

    public void addItem(String itemName) {
        addItem(itemName, SimpleIconListWidget.class.getResource("playonlinux.png"));
    }


    public void addItem(String itemName, File iconPath) {
        try {
            addItem(itemName, new URL("file://"+iconPath.getAbsolutePath()));
        } catch (MalformedURLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(String.format(translate("Error while trying to load the icon %s."),
                    iconPath.getAbsolutePath()));
            alert.setContentText(String.format("The error was: %s", e));
            e.printStackTrace();
        }
    }

    public void addItem(String itemName, URL iconUrl) {
        TreeItem<SimpleIconListItem> treeItem = new TreeItem<>(new SimpleIconListItem(itemName, iconUrl));
        rootItem.getChildren().add(treeItem);
    }

    public void addChangeListener(SimpleIconChangeListener simpleIconChangeListener) {
        getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        simpleIconChangeListener.changed((
                                        (TreeItem<SimpleIconListItem>) newValue).getValue().getValue()
                        );
                    } else {
                        simpleIconChangeListener.changed(null);
                    }
                });
    }

    protected void clear() {
        rootItem.getChildren().clear();
    }

    public String getSelectedItemLabel() {
        TreeItem<SimpleIconListItem> item = (TreeItem<SimpleIconListItem>) this.getSelectionModel().getSelectedItem();
        return item.getValue().getValue();
    }

    private class SimpleIconListItem extends GridPane {
        private String itemName;

        SimpleIconListItem(String itemName, URL iconPath) {
            this.itemName = itemName;
            this.setPrefHeight(0.);

            SimpleIconListItemLabel simpleIconListItemLabel = new SimpleIconListItemLabel(itemName);

            ImageView iconImageView = new ImageView(new Image(iconPath.toExternalForm()));

            iconImageView.setFitHeight(16);
            iconImageView.setFitWidth(16);

            this.add(iconImageView, 0, 0);
            this.add(simpleIconListItemLabel, 1, 0);
        }

        public String getValue() {
            return itemName;
        }


        private class SimpleIconListItemLabel extends Pane {
            SimpleIconListItemLabel(String itemName) {
                Text simpleIconListItemLabelText = new Text(itemName);
                simpleIconListItemLabelText.setLayoutX(10);
                simpleIconListItemLabelText.setLayoutY(12);
                this.getChildren().add(simpleIconListItemLabelText);
            }
        }
    }

    public interface SimpleIconChangeListener {
        void changed(String newValue);
    }
}

