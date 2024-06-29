//Latest Coding (includes inheritance and polymorphism)

#include <iostream>
#include <string>

using namespace std;

class CarbonComponent {
public:
    virtual float calculateImpact() const = 0;
    virtual ~CarbonComponent() {} // Virtual destructor for polymorphic behavior
};

class TransportationComponent : public CarbonComponent {
private:
    string transportMethod;
public:
    TransportationComponent(const string& method) : transportMethod(method) {}

    float calculateImpact() const override {
        if (transportMethod == "car") return 2.0;
        if (transportMethod == "bus") return 1.5;
        if (transportMethod == "bike") return 0.0;
        return 0.0;
    }
};

class EnergyComponent : public CarbonComponent {
private:
    float energyUsage;
public:
    EnergyComponent(float usage) : energyUsage(usage) {}

    float calculateImpact() const override {
        return energyUsage;
    }
};

class DietaryComponent : public CarbonComponent {
private:
    string dietaryHabit;
public:
    DietaryComponent(const string& habit) : dietaryHabit(habit) {}

    float calculateImpact() const override {
        if (dietaryHabit == "vegetarian") return 1.0;
        if (dietaryHabit == "meat") return 2.5;
        return 1.5;
    }
};

class CarbonFootprintCalculator {
private:
    static const int MAX_COMPONENTS = 3;
    CarbonComponent* components[MAX_COMPONENTS];
    int numComponents;
public:
    CarbonFootprintCalculator() : numComponents(0) {}

    void addComponent(CarbonComponent* component) {
        if (numComponents < MAX_COMPONENTS) {
            components[numComponents++] = component;
        } else {
            cout << "Cannot add more components. Max capacity reached." << endl;
        }
    }

    float calculateTotalFootprint() const {
        float totalFootprint = 0.0;
        for (int i = 0; i < numComponents; ++i) {
            totalFootprint += components[i]->calculateImpact();
        }
        return totalFootprint;
    }
};

class User {
public:
    string username;
    string password;

    void registerUser() {
        cout << "Register a new user." << endl;
        cout << "Enter username: ";
        cin >> username;
        cout << "Enter password: ";
        cin >> password;
        // Store user data (could be to a file or database)
    }

    bool loginUser() {
        string inputUsername, inputPassword;
        cout << "Login." << endl;
        cout << "Enter username: ";
        cin >> inputUsername;
        cout << "Enter password: ";
        cin >> inputPassword;

        // Validate user (simplified for example)
        if (inputUsername == username && inputPassword == password) {
            return true;
        } else {
            cout << "Invalid login credentials." << endl;
            return false;
        }
    }
};

class Goal {
private:
    float reductionTarget;
public:
    void setGoal(float target) {
        reductionTarget = target;
        cout << "Goal set to reduce footprint by " << target << " kg CO2/day." << endl;
    }

    void checkProgress(float currentFootprint) {
        cout << "Current footprint: " << currentFootprint << " kg CO2/day." << endl;
        if (currentFootprint <= reductionTarget) {
            cout << "Goal achieved!" << endl;
        } else {
            cout << "Keep working towards your goal." << endl;
        }
    }
};

class EducationalResources {
public:
    void displayResources() {
        cout << endl << "Educational Resources:\n";
        cout << "1. Climate Change 101\n";
        cout << "2. Tips for Sustainable Living\n";
        // More resources can be added here
    }
};

class UserInterface {
public:
    string transport;
    float energyUsage;
    string dietaryHabit;

    void getUserInput() {
        cout << "----- Carbon Footprint Calculator -----" << endl;
        cout << "Enter your transport method: ";
        cin >> transport;
        cout << "Enter your energy usage (e.g., kWh per day): ";
        cin >> energyUsage;
        cout << "Enter your dietary habit (vegetarian/meat): ";
        cin >> dietaryHabit;
    }
};

class DisplayOutput {
public:
    void displayData(const string& username, float totalFootprint) const {
        cout << endl << "User Name: " << username << "\n";
        cout << "Total Carbon Footprint: " << totalFootprint << " kg CO2/day\n";
    }
};

int main() {
    User user;
    user.registerUser();
    if (user.loginUser()) {
        UserInterface userInterface;
        userInterface.getUserInput();

        CarbonFootprintCalculator calculator;

        TransportationComponent* transportComponent = new TransportationComponent(userInterface.transport);
        EnergyComponent* energyComponent = new EnergyComponent(userInterface.energyUsage);
        DietaryComponent* dietaryComponent = new DietaryComponent(userInterface.dietaryHabit);

        calculator.addComponent(transportComponent);
        calculator.addComponent(energyComponent);
        calculator.addComponent(dietaryComponent);

        float totalFootprint = calculator.calculateTotalFootprint();

        DisplayOutput displayOutput;
        displayOutput.displayData(user.username, totalFootprint);

        Goal goal;
        goal.setGoal(10.0); // Example target
        goal.checkProgress(totalFootprint);

        EducationalResources resources;
        resources.displayResources();

        delete transportComponent;
        delete energyComponent;
        delete dietaryComponent;
    }

    return 0;
}

