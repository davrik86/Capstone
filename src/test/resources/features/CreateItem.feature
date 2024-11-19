@regression
Feature: Create Item Functionality for Invoice Application
  Scenario: testing create item functionality
  Given I am an external user of the Prime Tech Invoice Application,
  When I have logged in to the application,
  And I click on the ‘Items’ Menu Link,
  And I click on + Add Item,
  Then I enter the details for name, pc, price, description.
