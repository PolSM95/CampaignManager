# Campaign Manager

All the Sprints are separated by different projects in order to visualize the changes inbetween versions. 
For compiling the projects you'll have to open them individually so the Maven dependecies will be imported.

#####I'll make a planning before starting every Spring in order to have my ideas clear:

##Sprint 1: 
- I'll create an Acceptance Test for testing the whole execution of the application and detect the Entities that I would have to implement (Outside-in TDD).
- There's a clear State pattern for the Campaigns so the state handling between Active and Pause is easier (States: Active / Pause / Finished). 
- Builder pattern for creating the different Click types and verify that the data introduced is valid (Click types: Premium / Normal). 
- Memento for serialization of the objects in order to create the DTOs and access them instead of the actual objects of our domain.

##Sprint 2: 
- I'll have to add a new feature when trying to charge a new Click so It'll check the instants between the new Click and the Clicks in the repository before updating the budget.
- I'll have to give format to the Click instant attribute in order to 

##Sprint 3:
- In order to make a different implementation of the updateBudget in Campaign for each type of Campaign, I'll have to use a Factory pattern (even if I don't have a input to decide what type must be initialized) or try to use Polimorphism.
- I'll have to encapsulate the new values for the Top Campaign so it can be changed without having the Shotgun Surgery smell. 

##Sprint 4:
- I'll have to add the ClickRepository to the CampaignService so we can evaluate what click is made by a bot and then I'll have to create the algorithm for each type of Campaign so It must behave as expected.
- It can be implemented with a Strategy pattern but the clicks can't be accessed from the Campaign.
