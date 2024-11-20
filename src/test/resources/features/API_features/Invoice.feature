@api
  Feature: User Story 2
    Scenario: Integrate Customer Webservice for Invoice Application
      Given I am an authorized customer of the Create customer REST API webservice,
      When I send a request to the customer webservice with the ‘POST’ HTTP method,
      Then the create customer  REST API should have status code 200
      And Respond Body should have id(randomly gen), "name" and "email" matching the POST request body