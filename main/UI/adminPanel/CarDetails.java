package main.UI.adminPanel;

import main.Application;
import main.model.car.Car;
import main.model.car.Transmission;
import main.functions.CarFunctions;
import main.exception.ValidationException;
import main.UI.BasePage;
import main.utils.PromptSwing;
import main.utils.Validator;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.function.BiConsumer;

public class CarDetails extends BasePage {
    private JPanel mainPanel;
    private JTextField plateNumberField;
    private JTextField brandField;
    private JTextField modelField;
    private JTextField seatField;
    private JComboBox transmissionComboBox;
    private JTextField priceField;
    private JButton proceedButton;
    private JButton backButton;
    private JLabel title;

    private final CarFunctions carFunctions;
    private final Mode mode;

    public CarDetails(Application application, CarFunctions carFunctions, Mode mode) {
        super(application);
        this.carFunctions = carFunctions;
        this.mode = mode;

        this.title.setText(mode.title);

        plateNumberField.setEnabled(mode.primaryKeyFieldEnabled);

        backButton.addActionListener(this::backToPreviousPage);
        initializeProceedButton(mode.proceedButtonName, mode.proceedAction);
    }


    /** This function is to initialize button*/
    private void initializeProceedButton(String proceedButtonName, BiConsumer<CarFunctions, Car> proceedAction) {
        proceedButton.setText(proceedButtonName);
        proceedButton.addActionListener(e -> addCar(proceedAction));
    }

    /** Validate car data and add car
     */
    private void addCar(BiConsumer<CarFunctions, Car> proceedAction) {
        try {
            validateData();

            Car car = retrieveCar();
            proceedAction.accept(carFunctions, car);

            onAddCarSuccess(car);
        } catch (ValidationException e) {
            onAddCarFail(e);
        }
    }

    /**
     * Error message if its fail
     **/
    private void onAddCarFail(ValidationException e) {
        PromptSwing.promptMessageError(e.getMessage());
    }

    private void onAddCarSuccess(Car car) {
        PromptSwing.promptMessageInfo(mode.successMessage);
        backToPreviousPage();
    }

    /** This function is to retrieve car data*/
    private Car retrieveCar() {
        return new Car(plateNumberField.getText(), brandField.getText(), modelField.getText(),
                Integer.parseInt(seatField.getText()), new BigDecimal(priceField.getText()),
                Transmission.valueOf(transmissionComboBox.getSelectedItem().toString()));
    }

    /** Error message in different  functions
     **/
    private void validateData() throws ValidationException {
        validateCarPlate(plateNumberField.getText());
        validateBrand(brandField.getText());
        validateModel(modelField.getText());
        validateSeats(seatField.getText());
        validatePrice(priceField.getText());
    }

    private void validatePrice(String text) throws ValidationException {
        Validator.validateIsCurrency(text, "Invalid price!");
    }

    private void validateSeats(String text) throws ValidationException {
        Validator.validateIsInteger(text, "Number of Seats is not number!");

    }

    private void validateModel(String text) throws ValidationException {
        Validator.validateNotEmpty(text, "Model cannot be empty!");
    }

    private void validateBrand(String text) throws ValidationException {
        Validator.validateNotEmpty(text, "Brand cannot empty!");
    }

    private void validateCarPlate(String text) throws ValidationException {
        Validator.validateNotEmpty(text, "Plate Number cannot be empty!");

        if (mode.equals(Mode.ADD) && carFunctions.findByCarPlate(text).isPresent())
            throw new ValidationException("Car already exists!");
    }

    @Override
    public JPanel getPanel() {
        return mainPanel;
    }

    @Override
    public void refreshData() {
        plateNumberField.setText("");
        brandField.setText("");
        modelField.setText("");
        seatField.setText("");
        priceField.setText("");
    }

    public void initializeCarPage(Car car) {
        plateNumberField.setText(car.getCarPlate());
        brandField.setText(car.getBrand());
        modelField.setText(car.getModel());
        seatField.setText(car.getSeats().toString());
        priceField.setText(car.getPrice().toString());
        transmissionComboBox.setSelectedIndex(car.getTransmission().ordinal());
    }

    public enum Mode {
        ADD("Add New Car", "Add", "Car added successfully!", (service, car) -> service.addCar(car), true),
        EDIT("Edit Car", "Edit", "Car Edited successfully!", (service, car) -> service.updateCar(car), false);

        private final String title;
        private final String proceedButtonName;
        private final BiConsumer<CarFunctions, Car> proceedAction;
        public final boolean primaryKeyFieldEnabled;
        public final String successMessage;


        Mode(String title, String proceedButtonName, String successMessage, BiConsumer<CarFunctions, Car> proceedAction, boolean primaryKeyFieldEnabled) {
            this.title = title;
            this.proceedButtonName = proceedButtonName;
            this.proceedAction = proceedAction;
            this.primaryKeyFieldEnabled = primaryKeyFieldEnabled;
            this.successMessage = successMessage;
        }
    }
}
