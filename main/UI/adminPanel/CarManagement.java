package main.UI.adminPanel;

import main.Application;
import main.model.car.Car;
import main.functions.CarFunctions;
import main.UI.BasePage;
import main.UI.tableUI.CarTableUI;
import main.utils.PromptSwing;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class CarManagement extends BasePage {
    private JPanel mainPanel;
    private JTable carTable;
    private JButton addNewCarButton;
    private JButton backButton;
    private JButton editCarButton;
    private JButton removeCarButton;

    private CarTableUI tableModel;

    private final CarFunctions carFunctions;

    public CarManagement(Application application, CarFunctions carFunctions) {
        super(application);
        this.carFunctions = carFunctions;

        backButton.addActionListener(this::backToPreviousPage);
        addNewCarButton.addActionListener(e -> application.toAddCar());
        removeCarButton.addActionListener(this::removeCar);
        editCarButton.addActionListener(this::editCar);
    }

    /** This function is to edit car details
     */
    private void editCar(ActionEvent event) {
        int[] selectedRows = carTable.getSelectedRows();

        if (selectedRows.length != 1) {
            PromptSwing.promptMessageError("No car selected!");
            return;
        }

        Car car = tableModel.getModelAt(selectedRows[0]);

        application.toEditCar(car);
    }

    /**  Remove car
     */
    private void removeCar(ActionEvent event) {
        int[] selectedRows = carTable.getSelectedRows();

        if (selectedRows.length == 0) {
            PromptSwing.promptMessageError("No car selected!");
            return;
        }

        List<Car> carList = tableModel.getModelAt(selectedRows);
        carList.forEach(carFunctions::removeCar);
        carTable.clearSelection();
        PromptSwing.promptMessageInfo("Successfully deleted!");
        refreshData();
    }

    private void createUIComponents() {
        tableModel = new CarTableUI();
        carTable = new JTable(tableModel);
    }

    @Override
    public JPanel getPanel() {
        return mainPanel;
    }

    @Override
    public void refreshData() {
        tableModel.refreshData(carFunctions.getAllCars());
        carTable.addNotify();
    }

}
