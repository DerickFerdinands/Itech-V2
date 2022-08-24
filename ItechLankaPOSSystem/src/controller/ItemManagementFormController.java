package controller;

import Model.ItemDTO;
import Util.NotificationUtil;
import Util.ValidationUtil;
import View.TM.ItemTM;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import dao.Custom.ItemDAO;
import dao.Custom.impl.ItemCRUDController;
import entity.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;

import java.io.File;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ItemManagementFormController {
    private final LinkedHashMap<JFXTextField, Pattern> RegexMap = new LinkedHashMap<>();
    public JFXTextField txtCode;
    public JFXTextField txtQty;
    public JFXTextField txtBuyingPrice;
    public JFXButton btnAddItem;
    public JFXTextField txtSellingPrice;
    public JFXComboBox<String> cmbCategory;
    public JFXTextField txtName;
    public AnchorPane nchrPnImage;
    public JFXTextArea txtrDetails;
    public AnchorPane ItemMainPane;
    public TableView<ItemTM> tblItems;
    public TableColumn colCode;
    public TableColumn colName;
    public TableColumn colCategory;
    public TableColumn colQty;
    public TableColumn colBuyingPrice;
    public TableColumn colSellingPrice;
    public TableColumn colOption;
    public JFXTextField txtSearch;

    private final ItemDAO ItemCrudOps = new ItemCRUDController();

    public void ResetFormOnAction(ActionEvent actionEvent) {
        resetForm();
    }

    public void resetForm() {
        txtCode.clear();
        txtName.clear();
        cmbCategory.getSelectionModel().clearSelection();
        txtQty.clear();
        txtBuyingPrice.clear();
        txtSellingPrice.clear();
        txtrDetails.clear();
        btnAddItem.setDisable(true);
        tblItems.getSelectionModel().clearSelection();
        txtName.requestFocus();
        btnAddItem.setText("Add Item");

        resetFields(txtCode, txtName, cmbCategory, txtQty, txtBuyingPrice, txtSellingPrice);
    }


    public void addItemOnAction(ActionEvent actionEvent) {
        addOrUpdateItem();
    }

    public void initialize() {
        cmbCategory.getItems().addAll("CCTV", "SOLAR PANEL");
        RegexMap.put(txtName, Pattern.compile("^[A-z1-9 ,() ]+$"));
        RegexMap.put(txtQty, Pattern.compile("^[0-9]+$"));
        RegexMap.put(txtBuyingPrice, Pattern.compile("^[0-9]+[.0-9]{0,3}?$"));
        RegexMap.put(txtSellingPrice, Pattern.compile("^[0-9]+[.0-9]{0,3}?$"));

        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colBuyingPrice.setCellValueFactory(new PropertyValueFactory<>("buyingPrice"));
        colSellingPrice.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
        btnAddItem.setDisable(true);
        loadAllItems();

        tblItems.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (newValue instanceof ItemTM) {
                    setUpdateFields(newValue);
                }
            }
        });

    }

    private void setUpdateFields(ItemTM tm) {
        txtCode.setText(tm.getCode());
        txtName.setText(tm.getName());
        cmbCategory.getSelectionModel().select(tm.getCategory());
        txtQty.setText(tm.getQty() + "");
        txtBuyingPrice.setText(tm.getBuyingPrice() + "");
        txtSellingPrice.setText(tm.getSellingPrice() + "");
        txtrDetails.setText(tm.getDetail());
        btnAddItem.setDisable(false);
        btnAddItem.setText("Update Item");
        txtName.requestFocus();

    }

    private void loadAllItems() {
        try {
            ArrayList<Item> itemDTOList = ItemCrudOps.getAll();
            System.out.println(itemDTOList);
            ObservableList<ItemTM> obList = FXCollections.observableArrayList();

            for (Item itemDTO : itemDTOList) {
                System.out.println(itemDTO);
                obList.add(new ItemTM(itemDTO.getCode(), itemDTO.getName(), itemDTO.getCategory(), itemDTO.getBuyingPrice(), itemDTO.getSellingPrice(), itemDTO.getQty(), itemDTO.getDetails()));
            }

            tblItems.setItems(obList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void frequentFunctions() {
        loadAllItems();
        resetForm();
    }

    public void validateFields(KeyEvent keyEvent) {
        Object o = ValidationUtil.Validate(RegexMap, btnAddItem);

        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            if (o instanceof JFXTextField) {
                JFXTextField field = (JFXTextField) o;
                field.requestFocus();
            } else {
                addOrUpdateItem();
            }
        }
    }

    private void addOrUpdateItem() {

        try {
            if (cmbCategory.getSelectionModel().getSelectedItem() == null) {
                if (cmbCategory.getSelectionModel().getSelectedItem() == null) {
                    cmbCategory.getParent().setStyle("-fx-border-color: red;" + "-fx-border-width:1.5;" + "-fx-border-radius:  5;" + "-fx-background-radius:  5;");
                } else {
                    cmbCategory.getParent().setStyle("-fx-border-color: green;" + "-fx-border-width:1.5;" + "-fx-border-radius:  5;" + "-fx-background-radius:  5;");
                }

            } else {
                if (btnAddItem.getText().equals("Add Item")) {
                    if (ItemCrudOps.save(new Item(txtCode.getText(), txtName.getText(), cmbCategory.getValue(), BigDecimal.valueOf(Double.valueOf(txtBuyingPrice.getText())), BigDecimal.valueOf(Double.valueOf(txtSellingPrice.getText())), Integer.valueOf(txtQty.getText()), txtrDetails.getText()))) {
                        frequentFunctions();
                        NotificationUtil.playNotification(AnimationType.POPUP, "Item Successfully Added!", NotificationType.SUCCESS, Duration.millis(3000));
                    }
                } else {
                    if (ItemCrudOps.update(new Item(txtCode.getText(), txtName.getText(), cmbCategory.getValue(), BigDecimal.valueOf(Double.valueOf(txtBuyingPrice.getText())), BigDecimal.valueOf(Double.valueOf(txtSellingPrice.getText())), Integer.valueOf(txtQty.getText()), txtrDetails.getText()))) {
                        frequentFunctions();
                        NotificationUtil.playNotification(AnimationType.POPUP, "Item Updated Added!", NotificationType.SUCCESS, Duration.millis(3000));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetFields(Object... fields) {

        for (Object o : fields) {
            if (o instanceof JFXTextField) {
                JFXTextField field = (JFXTextField) o;
                field.getParent().setStyle("-fx-border-color :   #EDEDF0;" + "-fx-border-width:1.5;" + "-fx-border-radius:  5;" + "-fx-background-radius:  5;");
            } else if (o instanceof JFXComboBox) {
                JFXComboBox field = (JFXComboBox) o;
                field.getParent().setStyle("-fx-border-color :   #EDEDF0;" + "-fx-border-width:1.5;" + "-fx-border-radius:  5;" + "-fx-background-radius:  5;");
            } else if (o instanceof ImageView) {
                ImageView field = (ImageView) o;
                field.getParent().setStyle("-fx-border-color :   #EDEDF0;" + "-fx-border-width:1.5;" + "-fx-border-radius:  5;" + "-fx-background-radius:  5;");
            }
        }
    }

    public void searchMatchingItems(KeyEvent keyEvent) {
        String search = "%" + txtSearch.getText() + "%";

        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            try {
                ArrayList<Item> list = ItemCrudOps.getMatchingResults(search);
                ObservableList<ItemTM> obList = FXCollections.observableArrayList();
                for (Item c : list) {

                    obList.add(new ItemTM(c.getCode(), c.getName(), c.getCategory(),c.getSellingPrice(), c.getBuyingPrice(), c.getQty(), c.getDetails()));
                }
                tblItems.getItems().clear();
                tblItems.getItems().addAll(obList);
                tblItems.refresh();


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
